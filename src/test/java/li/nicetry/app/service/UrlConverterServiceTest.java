package li.nicetry.app.service;

import li.nicetry.app.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AppConfig.class })
@ActiveProfiles("test")
@PropertySource("classpath:application.yml")
public class UrlConverterServiceTest {

	@Autowired
	private UrlConverterService urlConverterService;

	@Value("${urlToPdfService.url}")
	private String urlToPdfServiceUrl;

	@Value("${urlToPdfService.endpoint}")
	private String urlToPdfServiceEndpoint;

	@Autowired
	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;

	@Before
	public void setUp() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	public void toPdfAsBytes_shouldInvokeUrlToPdfServiceEndpoint() throws Exception {
		String testUrl = "http://test.url";

		mockServer.expect(requestTo(urlToPdfServiceUrl + urlToPdfServiceEndpoint))
				.andExpect(method(HttpMethod.POST))
				.andExpect(header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.output", equalTo("pdf")))
				.andExpect(jsonPath("$.url", equalTo(testUrl)))
				.andRespond(withSuccess("OK".getBytes(StandardCharsets.UTF_8), MediaType.APPLICATION_OCTET_STREAM));

		urlConverterService.toPdfAsBytes(new URL(testUrl));

		mockServer.verify();
	}

	@Test
	public void toScreenShotAsBytes_shouldInvokeUrlToPdfServiceEndpoint() throws Exception {
		String testUrl = "http://test.url";

		mockServer.expect(requestTo(urlToPdfServiceUrl + urlToPdfServiceEndpoint))
				.andExpect(method(HttpMethod.POST))
				.andExpect(header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.output", equalTo("screenshot")))
				.andExpect(jsonPath("$.url", equalTo(testUrl)))
				.andRespond(withSuccess("OK".getBytes(StandardCharsets.UTF_8), MediaType.APPLICATION_OCTET_STREAM));

		urlConverterService.toScreenShotAsBytes(new URL(testUrl));

		mockServer.verify();
	}

}
