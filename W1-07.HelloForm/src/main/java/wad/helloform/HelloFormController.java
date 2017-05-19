package wad.helloform;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloFormController {

    @RequestMapping("/")
    public String home(Model model,
            @RequestParam(required = false, defaultValue = "Hello world!") String content) {
        model.addAttribute("content", content);
        return "index";
    }
}
