package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Aircraft;
import wad.repository.AircraftRepository;
import wad.repository.AirportRepository;
import wad.service.AirportService;

@Controller
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private AirportService airportService;

    @RequestMapping(value = "/aircrafts", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("aircrafts", aircraftRepository.findAll());
        model.addAttribute("airports", airportRepository.findAll());

        return "aircrafts";
    }

    @RequestMapping(value = "/aircrafts", method = RequestMethod.POST)
    public String create(@ModelAttribute Aircraft aircraft) {
        aircraftRepository.save(aircraft);
        return "redirect:/aircrafts";
    }

    @RequestMapping(value = "/aircrafts/{aircraftId}/airports", method = RequestMethod.POST)
    @Transactional
    public String assignAirport(
            @PathVariable(value = "aircraftId") Long aircraftId, 
            @RequestParam(value = "airportId") Long airportId) {

        airportService.assignAirport(aircraftId, airportId);
        return "redirect:/aircrafts";
    }

}
