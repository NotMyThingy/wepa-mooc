package wad.notebook;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotebookController {
  private List<String> list;

  public NotebookController() {
    this.list = new ArrayList<>();
  }

  @RequestMapping("/")
  public String home(Model model,
          @RequestParam(required = false) String content) {
    if(content != null && !content.trim().isEmpty()) {
      this.list.add(content);

      if(this.list.size() > 10) {
        this.list.remove(0);
      }
    }

    model.addAttribute("list", this.list);
    return "index";
  }
}
