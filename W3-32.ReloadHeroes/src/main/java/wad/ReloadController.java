package wad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReloadController {

    @Autowired
    private ReloadStatusService reloadStatusService;

    @RequestMapping("*")
    public String reload(Model model) {
        model.addAttribute("status", reloadStatusService.reload());
        model.addAttribute("scores", reloadStatusService.getTopList());
        return "index";
    }
}
