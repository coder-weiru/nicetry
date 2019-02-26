package li.nicetry.app.service.integration;

import li.nicetry.app.AppConfig;
import li.nicetry.app.service.UrlConverterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppConfig.class})
@ActiveProfiles("test")
@PropertySource("classpath:application.yml")
public class UrlConverterServiceIntegrationTest {

    @Autowired
    private UrlConverterService urlConverterService;

    @Test
    public final void testFetchPdfAsBytes() throws IOException {
        URL url = new URL("https://www.cnn.com/");
        Files.write(Paths.get("cnn.pdf"), urlConverterService.toPdfAsBytes(url));
    }

    @Test
    public final void testFetchScreenShotAsBytes() throws IOException {
        URL url = new URL("https://www.cnn.com/");
        Files.write(Paths.get("cnn.png"), urlConverterService.toScreenShotAsBytes(url));
    }

}
