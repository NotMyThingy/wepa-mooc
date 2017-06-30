package wad;

import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@Points("31")
public class HelloSessionControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void helloSession() throws Throwable {
        MvcResult res = mockMvc.perform(get("/"))
                .andExpect(content().string("Hello there!"))
                .andReturn();

        mockMvc.perform(get("/").session((MockHttpSession) res.getRequest().getSession()))
                .andExpect(content().string("Hello again!"));

        mockMvc.perform(get("/").session((MockHttpSession) res.getRequest().getSession()))
                .andExpect(content().string("Hello again!"));

        mockMvc.perform(get("/"))
                .andExpect(content().string("Hello there!"))
                .andReturn();
    }

}
