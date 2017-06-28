package wad.controller;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.text.SimpleDateFormat;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.domain.Exam;
import wad.domain.Question;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Points("30")
public class X_ExamsAndQuestionsTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void canAddQuestion() throws Throwable {
        addQuestion();
    }

    @Test
    public void canAddExam() throws Throwable {
        addExam();
    }

    @Test
    public void addQuestionToExamAndShowExam() throws Throwable {
        Question q = addQuestion();
        Exam e = addExam();

        goTo("http://localhost:" + port + "/exams");
        find(By.partialLinkText(e.getSubject())).click();

        assertThat(pageSource()).contains(e.getSubject());
        assertThat(pageSource()).contains(q.getTitle());
        assertThat(pageSource()).doesNotContain(q.getContent());

        FluentWebElement el = find(By.tagName("li")).stream().filter(f -> f.getTextContent().contains(q.getTitle())).findFirst().get();

        el.find(By.tagName("form")).submit();

        assertThat(pageSource()).contains(e.getSubject());
        assertThat(pageSource()).contains(q.getTitle());
        assertThat(pageSource()).contains(q.getContent());

    }

    public Question addQuestion() {

        goTo("http://localhost:" + port + "/questions");

        Question question = new Question();
        question.setTitle("Title: " + UUID.randomUUID().toString().substring(0, 6));
        question.setContent("Content: " + UUID.randomUUID().toString().substring(0, 6));

        fill("input[name=title]").with(question.getTitle());
        fill("textarea[name=content]").with(question.getContent());
        click("input[value='Add']");

        assertThat(pageSource()).contains(question.getTitle());
        assertThat(pageSource()).contains(question.getContent());

        return question;
    }

    public Exam addExam() {

        goTo("http://localhost:" + port + "/exams");

        Exam exam = new Exam();
        exam.setSubject("Subject: " + UUID.randomUUID().toString().substring(0, 6));
        exam.setExamDate(DateTestUtils.getRandomDateBetween(2015, 2021));

        String examDateParam = new SimpleDateFormat("yyyy-MM-dd").format(exam.getExamDate());

        fill("input[name=subject]").with(exam.getSubject());
        fill("input[name=examDate]").with(examDateParam);
        click("input[value='Add']");

        assertThat(pageSource()).contains(exam.getSubject());
        assertThat(pageSource()).contains(examDateParam);

        return exam;
    }

}
