package wad.hellopaths;

import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloPathsController.class)
@Points("2")
public class HelloPathsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void helloPathsTest() throws Exception {
        this.mockMvc.perform(get("/hello"))
                .andExpect(content().string("Hello"));

        this.mockMvc.perform(get("/paths"))
                .andExpect(content().string("Paths"));

    }
}
