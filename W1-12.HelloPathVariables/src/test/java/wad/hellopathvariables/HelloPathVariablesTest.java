package wad.hellopathvariables;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.HashMap;
import java.util.Map;
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
@Points("12")
public class HelloPathVariablesTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void defaultGivesHat() {
        goTo("http://localhost:" + port + "/");
        assertThat(pageSource()).contains("Hat");
    }

    @Test
    public void allHatsAvailable() {
        Map<String, Item> items = new HashMap<>();
        items.put("default", new Item("Hat", "default"));
        items.put("ascot", new Item("Ascot cap", "hat"));
        items.put("balaclava", new Item("Balaclava", "hat"));
        items.put("bicorne", new Item("Bicorne", "hat"));
        items.put("busby", new Item("Busby", "hat"));
        items.put("capotain", new Item("Capotain", "hat"));
        items.put("homburg", new Item("Homburg", "hat"));
        items.put("montera", new Item("Montera", "hat"));

        items.keySet().stream().forEach(s -> {
            goTo("http://localhost:" + port + "/" + s);
            assertThat(pageSource()).contains(items.get(s).getName());

            items.keySet().stream().forEach(i -> {
                if (s.equals(i)) {
                    return;
                }

                assertThat(pageSource()).doesNotContain(items.get(i).getName());
            });
        });

    }
}
