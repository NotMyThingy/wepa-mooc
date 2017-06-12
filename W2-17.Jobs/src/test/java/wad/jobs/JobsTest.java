package wad.jobs;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;
import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.domain.FluentWebElement;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Points("17")
public class JobsTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void canAddJob() {
        addJob();
    }

    @Test
    public void canAddMultipleJobs() {
        addJobs(2);
    }

    @Test
    public void lastJobsAvailableInFirstTable() {
        List<String> jobs = addJobs(7);

        goTo("http://localhost:" + port + "/");

        for (int i = 0; i < jobs.size(); i++) {
            FluentWebElement table = findFirst("table");
            String tableText = table.getText();

            assertThat(tableText.contains(jobs.get(i)));
        }
    }

    @Test
    public void canMarkJobDone() {
        String job = markJobDone();

        assertThat(!findFirst("table").getText().contains(job));
        assertThat(find("table").get(1).getText().contains(job));

        Optional<FluentWebElement> jobRow = find("table").get(1).find("tr").stream().filter((r) -> r.getText().contains(job)).findFirst();
        assertThat(jobRow.isPresent());
        String jobRowText = jobRow.get().getText();
        assertThat(jobRowText.contains("Done"));
    }

    public String markJobDone() {
        String job = addJob();
        goTo("http://localhost:" + port + "/");
        FluentWebElement table = findFirst("table");
        List<FluentWebElement> elements = table.find("tr").stream().filter((row) -> row.getText().contains(job)).collect(Collectors.toList());

        assertTrue("Once a job has been inserted, it should be visible in the first table only once.", elements.size() == 1);

        FluentWebElement e = elements.get(0);
        e.find("form").submit();

        return job;
    }

    public List<String> addJobs(int count) {
        return IntStream.range(0, count).mapToObj(i -> addJob()).collect(Collectors.toList());
    }

    public String addJob() {

        goTo("http://localhost:" + port + "/");

        String job = "Job: " + UUID.randomUUID().toString();

        fill("input[name=name]").with(job);
        click("input[value='Add!']");

        assertThat(pageSource()).contains(job);

        return job;
    }
}
