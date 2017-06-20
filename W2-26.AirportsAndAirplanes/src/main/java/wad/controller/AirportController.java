package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Airport;
import wad.repository.AirportRepository;

@Controller
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    @RequestMapping(value = "/airports", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("airports", airportRepository.findAll());
        return "airports";
    }

    @RequestMapping(value = "/airports", method = RequestMethod.POST)
    public String create(@ModelAttribute Airport airport) {
        airportRepository.save(airport);
        return "redirect:/airports";
    }
}
