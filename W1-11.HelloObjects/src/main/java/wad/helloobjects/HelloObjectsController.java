package wad.helloobjects;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloObjectsController {

    private List<Item> items;

    public HelloObjectsController() {
        this.items = new ArrayList<>();
        this.items.add(new Item("Wizard hat", "pointy"));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("list", this.items);

        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addItem(@RequestParam(required = false) String name,
                          @RequestParam(required = false) String type) {
        if (name != null && !name.trim().isEmpty() && type != null && !type.trim().isEmpty()) {
            this.items.add(new Item(name, type));
        }

        return "redirect:/";
    }

}
