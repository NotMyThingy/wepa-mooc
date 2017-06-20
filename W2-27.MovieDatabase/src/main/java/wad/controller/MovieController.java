package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.service.MovieService;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("movies", movieService.list());
        return "movies";
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    public String add(@RequestParam(value = "name") String name, 
                      @RequestParam(value = "lengthInMinutes") int lengthInMinutes) {
        movieService.add(name, lengthInMinutes);
        return "redirect:/movies";
    }

    @RequestMapping(value = "/movies/{movieId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "movieId") Long movieId) {
        movieService.remove(movieId);
        return "redirect:/movies";
    }

}
