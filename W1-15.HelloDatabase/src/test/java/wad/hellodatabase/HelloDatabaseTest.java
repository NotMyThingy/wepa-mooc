package wad.hellodatabase;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Points("15")
public class HelloDatabaseTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    public List<String> addItems(int count) {
        return IntStream.range(0, count).mapToObj(i -> addItem()).collect(Collectors.toList());
    }

    public String addItem() {

        goTo("http://localhost:" + port + "/");

        String itemName = "Item: " + UUID.randomUUID().toString();

        fill("input[name=name]").with(itemName);
        click("input[value='Add!']");

        assertThat(pageSource()).contains(itemName);

        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM Item").stream().filter(m -> m.values().stream().filter(v -> v.equals(itemName)).count() > 0).count());

        return itemName;
    }
}
