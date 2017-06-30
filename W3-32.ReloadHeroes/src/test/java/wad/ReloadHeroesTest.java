package wad;

import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.assertj.core.api.Java6Assertions.assertThat;
import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Points("32")
public class ReloadHeroesTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;

    }

    @LocalServerPort
    private Integer port;

    @Autowired
    private ReloadStatusRepository reloadStatusRepository;

    @Test
    public void reloadsIncrementOnPageload() throws Throwable {
        webDriver.manage().deleteAllCookies();

        goTo("http://localhost:" + port);
        String username = find(By.id("name")).getText();
        int reloads = Integer.parseInt(find(By.id("reloads")).getText());

        assertTrue(reloads == 1);

        goTo("http://localhost:" + port);
        assertThat(find(By.id("name")).getText()).contains(username);
        assertThat(find(By.id("reloads")).getText()).contains("" + (reloads + 1));

        assertNotNull(reloadStatusRepository.findByName(username));
        assertTrue(2 == reloadStatusRepository.findByName(username).getReloads());
    }

    @Test
    public void pageShowsContentFromDatabase() throws Throwable {
        webDriver.manage().deleteAllCookies();

        goTo("http://localhost:" + port);
        String username = find(By.id("name")).getText();
        int reloads = Integer.parseInt(find(By.id("reloads")).getText());

        assertTrue(reloads == 1);

        ReloadStatus reloadStatus = reloadStatusRepository.findByName(username);
        reloadStatus.setReloads(41);
        reloadStatusRepository.save(reloadStatus);

        goTo("http://localhost:" + port);
        assertNotNull(reloadStatusRepository.findByName(username));
        assertThat(find(By.id("reloads")).getText()).contains("42");
    }

}
