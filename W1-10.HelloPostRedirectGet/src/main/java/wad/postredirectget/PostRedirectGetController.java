package wad.postredirectget;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostRedirectGetController {

    private List<String> list;

    public PostRedirectGetController() {
        this.list = new ArrayList<>();
    }

    @RequestMapping("/")
    public String home(Model model) {
      model.addAttribute("list", this.list);
      return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String post(@RequestParam(required = false) String data) {
      if(data != null && !data.trim().isEmpty()) {
        this.list.add(data);
      }

      return "redirect:/";
    }

}
