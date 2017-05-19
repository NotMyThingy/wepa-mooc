package wad.hellorequestparams;

import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloRequestParamsController.class)
@Points("3")
public class HelloRequestParamsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void requestParamsTest() throws Exception {
        this.mockMvc.perform(get("/hello?param=value"))
                .andExpect(content().string("Hello value"));

        this.mockMvc.perform(get("/hello?param=test"))
                .andExpect(content().string("Hello test"));

        this.mockMvc.perform(get("/params?aaa=aaa&bbb=bbb&ccc=ccc"))
                .andExpect(content().string(containsString("aaa")))
                .andExpect(content().string(containsString("bbb")))
                .andExpect(content().string(containsString("ccc")));
    }

}
