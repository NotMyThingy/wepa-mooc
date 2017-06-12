package wad.tododatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoDatabaseController {

    @Autowired
    private ItemReposity itemReposity;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("items", itemReposity.findAll());
        return "index";
    }

    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
    public String getItem(Model model, @PathVariable Long itemId) {
        Item item = itemReposity.findOne(itemId);
        item.setChecked(item.getChecked() + 1);
        itemReposity.save(item);

        model.addAttribute("item", item);
        return "item";
    }

    @RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
    public String deleteItem(@PathVariable Long itemId) {
        itemReposity.delete(itemId);
        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addItem(@RequestParam String name) {
        if (!name.trim().isEmpty()) {
            itemReposity.save(new Item(name));
        }

        return "redirect:/";
    }
}
