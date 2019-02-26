package li.nicetry.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;

import static li.nicetry.app.service.UrlConverterRequest.OutputFormat.PDF;
import static li.nicetry.app.service.UrlConverterRequest.OutputFormat.SCREENSHOT;


@Service
public class UrlConverterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlConverterService.class);

	@Value("${urlToPdfService.url}")
	private String urlToPdfServiceUrl;

	@Value("${urlToPdfService.endpoint}")
	private String urlToPdfServiceEndpoint;

	@Autowired
	private RestTemplate restTemplate;

	public UrlConverterService() {
	}

	public byte[] toPdfAsBytes(URL url) {
		return convertToBytes(url, PDF);
	}

	public byte[] toScreenShotAsBytes(URL url) {
		return convertToBytes(url, SCREENSHOT);
	}

	private byte[] convertToBytes(URL url, UrlConverterRequest.OutputFormat outputFormat) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlToPdfServiceUrl + urlToPdfServiceEndpoint);
		URI uri = builder.build().encode().toUri();
		String requestUri = uri.toString();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

		UrlConverterRequest urlConverterRequest = new UrlConverterRequest();
		urlConverterRequest.setUrl(url.toString());
		urlConverterRequest.setOutput(outputFormat.getValue());
		HttpEntity<UrlConverterRequest> requestEntity = new HttpEntity<>(urlConverterRequest, headers);

		ResponseEntity<byte[]> response = restTemplate.exchange(requestUri, HttpMethod.POST, requestEntity, byte[].class);

		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			LOGGER.error(response.toString());

			return null;
		}
	}
}
