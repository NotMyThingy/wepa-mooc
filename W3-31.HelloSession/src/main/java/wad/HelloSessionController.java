package wad;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloSessionController {

    @RequestMapping("*")
    @ResponseBody
    public String sayHello(HttpSession session) {
        if (session.getAttribute("isChecked") != null) {
            return "Hello again!";
        }

        boolean isChecked = true;
        session.setAttribute("isChecked", isChecked);
        
        return "Hello there!";
    }
}
