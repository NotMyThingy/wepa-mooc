package wad.todoapplication;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoApplicationController {

    private Map<String, TodoItem> items;

    public TodoApplicationController() {
        this.items = new TreeMap<>();
        TodoItem item = new TodoItem("Imuroi");
        items.put(item.getIdentifier(), item);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("items", this.items.values());
        return "index";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getItem(Model model, @PathVariable String id) {
        TodoItem item = this.items.get(id);
        item.setChecked(item.getChecked() + 1);
        model.addAttribute("item", item);
        return "todoitem";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteItem(@PathVariable String id) {
        this.items.remove(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addItem(@RequestParam String name) {
        if (name != null && !name.trim().isEmpty()) {
            TodoItem item = new TodoItem(name);
            this.items.put(item.getIdentifier(), item);
        }

        return "redirect:/";
    }

}
