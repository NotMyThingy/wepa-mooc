package wad.controller;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
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
import wad.domain.Question;

@RunWith(SpringRunner.class)
@SpringBootTest
@Points("30")
public class QuestionControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void canCreateAndListQuestions() throws Throwable {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            questions.add(createQuestion());
        }

        MvcResult res = mockMvc.perform(get("/questions"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("questions"))
                .andExpect(view().name("questions"))
                .andReturn();

        Collection<Question> returnedQuestions = (Collection<Question>) res.getModelAndView().getModel().get("questions");

        assertTrue("Verify that once new questions have been posted, they are also added to the response. Verify also their parameters etc.", returnedQuestions.containsAll(questions));
    }

    private Question createQuestion() throws Throwable {
        Question q = new Question();
        q.setTitle(UUID.randomUUID().toString().substring(0, 6));
        q.setContent(UUID.randomUUID().toString().substring(0, 6));

        mockMvc.perform(
                post("/questions").param("title", q.getTitle()).param("content", q.getContent())
        ).andExpect(status().is3xxRedirection());

        return q;
    }

}
