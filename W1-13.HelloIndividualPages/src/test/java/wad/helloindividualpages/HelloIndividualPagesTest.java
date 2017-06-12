package wad.helloindividualpages;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.fluentlenium.adapter.FluentTest;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Points("13")
public class HelloIndividualPagesTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();
    private List<Item> items = new ArrayList<>();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void addItem() {
        items.addAll(addItems(1));
    }

    @Test
    public void addMultipleItems() {
        addItems(5).stream().forEach(i -> {
            assertThat(pageSource()).containsOnlyOnce(i.getName());
            assertThat(pageSource()).containsOnlyOnce(i.getType());
            items.add(i);
        });
    }

    @Test
    public void addItemAndVisitPage() {
        addItems(1).stream().forEach(i -> {
            goTo("http://localhost:" + port + "/");
            click(find("a", withText(i.getName())));

            assertThat(pageSource()).contains(i.getName());
            assertThat(pageSource()).contains(i.getType());

            items.stream().forEach(s -> assertThat(pageSource()).doesNotContain(s.getName()));
        });
    }

    @Test
    public void addMultipleItemsAndVisitEachPage() {
        addItems(1).stream().forEach(i -> {
            goTo("http://localhost:" + port + "/");
            click(find("a", withText(i.getName())));

            assertThat(pageSource()).contains(i.getName());
            assertThat(pageSource()).contains(i.getType());

            items.stream().forEach(s -> assertThat(pageSource()).doesNotContain(s.getName()));
        });
    }

    private List<Item> addItems(int count) {
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            goTo("http://localhost:" + port + "/");

            String name = "NAME: " + UUID.randomUUID().toString();
            String type = "TYPE: " + UUID.randomUUID().toString();

            items.add(new Item(name, type));

            fill("input[name=name]").with(name);
            fill("input[name=type]").with(type);
            click("input[type=submit]");

            webDriver.navigate().refresh();

            assertThat(pageSource()).containsOnlyOnce(name);
            assertThat(pageSource()).containsOnlyOnce(type);
        }

        return items;
    }

}
