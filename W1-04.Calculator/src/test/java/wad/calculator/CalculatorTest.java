package wad.calculator;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(CalculatorController.class)
@Points("4")
public class CalculatorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addTest() throws Exception {
        Random rand = new Random();
        
        for (int i = 0; i < 5; i++) {
            int first = rand.nextInt(1000000) - 500000;
            int second = rand.nextInt(1000000) - 500000;
            
            this.mockMvc.perform(get("/add?first=" + first + "&second=" + second))
                    .andExpect(content().string("" + (first + second)));
        }
    }

    @Test
    public void multiplyTest() throws Exception {
        Random rand = new Random();
        
        for (int i = 0; i < 5; i++) {
            int first = rand.nextInt(1000000) - 500000;
            int second = rand.nextInt(1000000) - 500000;
            
            this.mockMvc.perform(get("/multiply?first=" + first + "&second=" + second))
                    .andExpect(content().string("" + (first * second)));
        }
    }

    @Test
    public void sumTest() throws Exception {
        Random rand = new Random();
        char[] paramKeys = "abcdefghijklmn".toCharArray();

        for (int i = 0; i < 10; i++) {
            int keys = rand.nextInt(paramKeys.length - 3) + 2;
            StringBuilder sb = new StringBuilder();
            int sum = 0;
            for (int j = 0; j < keys; j++) {
                int value = rand.nextInt(10000) - 5000;

                if (j >= 1) {
                    sb.append("&");
                }

                sb.append(paramKeys[j]).append("=").append(value);
                sum += value;
            }

            
            this.mockMvc.perform(get("/sum?" + sb.toString()))
                    .andExpect(content().string("" + sum));
        }
    }
}
