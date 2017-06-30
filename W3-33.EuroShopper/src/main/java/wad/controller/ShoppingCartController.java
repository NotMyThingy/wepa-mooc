package wad.controller;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Item;
import wad.service.ShoppingCartService;

@Controller
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    //GET /cart asettaa model-olion "items"-nimiseen attribuuttiin ostoskorin sisällön
    //(aiempi getItems()). Pyynnön vastauksena käyttäjälle näytetään sivu,
    //joka luodaan polussa /src/main/resources/templates/cart.html olevasta näkymästä.
    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart(Model model) {
        model.addAttribute("items", shoppingCartService.findAll());
        return "cart";
    }

    // POST /cart/items/{id} lisää ostoskoriin yhden {id}-tunnuksella tietokannasta
    // löytyvän Item-olion. Pyyntö ohjataan osoitteeseen /cart.
    @RequestMapping(value = "/cart/items/{id}", method = RequestMethod.POST)
    public String add(@PathVariable Long id) {
        shoppingCartService.addItem(id);
        return "redirect:/cart";
    }

}
