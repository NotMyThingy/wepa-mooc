package wad.todoapplication;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
@Points("14")
public class TodoApplicationTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void canAddItem() {
        addItem();
    }

    @Test
    public void canAddMultipleItems() {
        addItems(5);
    }

    @Test
    public void canMarkItemDone() {
        List<String> items = addItems(5);

        for (int i = 0; i < 20; i++) {
            goTo("http://localhost:" + port + "/");

            try {
                click("input[value='Done!']");
            } catch (Throwable t) {
            }

        }

        goTo("http://localhost:" + port + "/");
        items.stream().forEach(i -> assertThat(pageSource()).doesNotContain(i));
    }

    @Test
    public void canAccessIndividualPages() {
        List<String> items = addItems(5);

        items.stream().forEach(i -> {
            goTo("http://localhost:" + port + "/");
            find("a", withText(i)).click();

            items.stream().filter(s -> !s.equals(i)).forEach(s -> assertThat(pageSource()).doesNotContain(s));
        });
    }

    public List<String> addItems(int count) {
        return IntStream.range(0, count).mapToObj(i -> addItem()).collect(Collectors.toList());
    }

    public String addItem() {

        goTo("http://localhost:" + port + "/");

        String itemName = "Item: " + UUID.randomUUID().toString();

        fill("input[name=name]").with(itemName);
        click("input[value='Add!']");

        assertThat(pageSource()).contains(itemName);

        return itemName;
    }
}
