package wad.postredirectget;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Points("10")
public class PostRedirectGetTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void postRedirectedToGet() {
        goTo("http://localhost:" + port + "/");

        String data = "TEST: " + UUID.randomUUID().toString();

        fill("input[type=text]").with(data);
        click("input");

        webDriver.navigate().refresh();
        webDriver.navigate().refresh();

        assertThat(pageSource()).containsOnlyOnce(data);
    }

}
