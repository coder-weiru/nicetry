package li.nicetry.app.service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import li.nicetry.app.AppConfig;
import li.nicetry.app.service.UrlToPdfService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AppConfig.class })
@ActiveProfiles("test")
@PropertySource("classpath:application.yml")
@Disabled
public class UrlToPdfServiceTest {

	@Autowired
	private UrlToPdfService urlToPdfService;

	@Test
	public final void testFetchPdfAsBytes() throws IOException {
		URL url = new URL("http://www.google.com");
		Files.write(Paths.get("google.png"), urlToPdfService.fetchPdfAsBytes(url));

	}

}
