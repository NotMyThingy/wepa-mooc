package wad.helloindividualpages;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloIndividualPagesController {

    private Map<String, Item> items;

    public HelloIndividualPagesController() {
        this.items = new TreeMap<>();
        Item item = new Item("Wizard hat", "pointy");
        this.items.put(item.getIdentifier(), item);
    }

    @RequestMapping(value = "/{item}", method = RequestMethod.GET)
    public String getItem(Model model, @PathVariable String item) {
        model.addAttribute("item", this.items.get(item));
        return "single";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addItem(@RequestParam String name, @RequestParam String type) {
        if (!name.trim().isEmpty() && !type.trim().isEmpty()) {
            Item item = new Item(name, type);
            this.items.put(item.getIdentifier(), item);
        }

        return "redirect:/";
    }

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("items", this.items.values());
        return "index";
    }

}
