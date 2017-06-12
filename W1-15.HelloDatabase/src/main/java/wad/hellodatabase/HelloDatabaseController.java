package wad.hellodatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloDatabaseController {

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addName(@RequestParam(required = false) String name) {
        this.itemRepository.save(new Item(name));

        return "redirect:/";
    }

}
