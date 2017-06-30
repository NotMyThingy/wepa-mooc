package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.service.OrderService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("orders", orderService.list());
        return "orders";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public String order(@RequestParam String name, @RequestParam String address) {
        orderService.placeOrder(name, address);
        return "redirect:/orders";
    }
}
