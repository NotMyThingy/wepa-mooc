package wad.controller;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.assertTrue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import wad.domain.Exam;

@RunWith(SpringRunner.class)
@SpringBootTest
@Points("30")
public class ExamControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void canCreateAndListExams() throws Throwable {
        List<Exam> exams = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            exams.add(createExam());
        }

        MvcResult res = mockMvc.perform(get("/exams"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("exams"))
                .andExpect(view().name("exams"))
                .andReturn();

        Collection<Exam> returnedExams = (Collection<Exam>) res.getModelAndView().getModel().get("exams");

        assertTrue("Verify that once new questions have been posted, they are also added to the response. Verify also their parameters etc.", returnedExams.containsAll(exams));
    }

    @Test
    public void canViewSingleExam() throws Throwable {
        for (int i = 0; i < 3; i++) {
            createExam();
        }

        MvcResult res = mockMvc.perform(get("/exams"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("exams"))
                .andExpect(view().name("exams"))
                .andReturn();

        Collection<Exam> exams = (Collection<Exam>) res.getModelAndView().getModel().get("exams");

        for (Exam exam : exams) {

            res = mockMvc.perform(get("/exams/" + exam.getId()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attributeExists("exam"))
                    .andExpect(view().name("exam"))
                    .andReturn();

            Exam e = (Exam) res.getModelAndView().getModel().get("exam");

            assertEquals(exam.getSubject(), e.getSubject());
            assertEquals(exam.getExamDate(), e.getExamDate());
        }
    }

    private Exam createExam() throws Throwable {
        Exam e = new Exam();
        e.setSubject(UUID.randomUUID().toString().substring(0, 6));

        LocalDate randomExamDate = DateTestUtils.getRandomLocalDateBetween(2015, 2021);
        e.setExamDate(Date.from(randomExamDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        String examDateParam = randomExamDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        mockMvc.perform(
                post("/exams").param("subject", e.getSubject()).param("examDate", examDateParam)
        ).andExpect(status().is3xxRedirection());

        return e;
    }

}
