package wad.hellofirebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloFirebaseController {

    @Autowired
    private FirebaseService firebaseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String get(Model model) {
        model.addAttribute("items", firebaseService.list());
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String add(@RequestParam String name) {
        firebaseService.add(new FirebaseItem(name));
        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{itemId}", method = RequestMethod.DELETE)
    public String remove(@PathVariable String itemId) {
        firebaseService.delete(itemId);
        return "redirect:/";
    }

    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET)
    public String item(Model model, @PathVariable String itemId) {
        model.addAttribute("item", firebaseService.get(itemId));
        return "item";
    }

    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.POST)
    public String edit(@PathVariable String itemId, @RequestParam String name) {
        firebaseService.delete(itemId);
        firebaseService.add(new FirebaseItem(name));
        return "redirect:/";
    }
}
