package li.nicetry.app.service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class UrlToPdfService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlToPdfService.class);

	@Value("${urlToPdfService.url}")
	private String urlToPdfServiceUrl;

	@Value("${urlToPdfService.endpoint}")
	private String urlToPdfServiceEndpoint;

	@Autowired
	private RestTemplate restTemplate;

	public UrlToPdfService() {
	}

	public byte[] fetchPdfAsBytes(URL url) throws IOException {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlToPdfServiceUrl + urlToPdfServiceEndpoint);
		URI uri = builder.build().encode().toUri();
		String requestUri = uri.toString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

		UrlToPdfRequest urlToPdfRequest = new UrlToPdfRequest();
		urlToPdfRequest.setUrl(url.toString());
		HttpEntity<UrlToPdfRequest> requestEntity = new HttpEntity<>(urlToPdfRequest, headers);

		ResponseEntity<byte[]> response = restTemplate.exchange(requestUri, HttpMethod.POST, requestEntity,
				byte[].class);

		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}

		return null;
	}

}
