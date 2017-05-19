package wad.hellomodel;

import fi.helsinki.cs.tmc.edutestutils.Points;
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
@Points("6")
public class HelloModelTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void bothShownOnPage() {
        goTo("http://localhost:" + port + "/?title=HelloWorld&person=Robot");
        assertThat(pageSource()).doesNotContain("Girl");
        assertThat(pageSource()).doesNotContain("SuperStory");
        assertThat(pageSource()).contains("Robot");
        assertThat(pageSource()).contains("HelloWorld");

        goTo("http://localhost:" + port + "/?title=SuperStory&person=Girl");
        assertThat(pageSource()).doesNotContain("Robot");
        assertThat(pageSource()).doesNotContain("HelloWorld");
        assertThat(pageSource()).contains("Girl");
        assertThat(pageSource()).contains("SuperStory");
    }
}
