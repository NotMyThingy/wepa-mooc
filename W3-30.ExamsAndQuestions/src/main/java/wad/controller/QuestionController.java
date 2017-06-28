package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.service.QuestionService;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "questions";
    }

    @RequestMapping(value = "/questions", method = RequestMethod.POST)
    public String addQuestion(@RequestParam String title,
            @RequestParam String content) {
        questionService.addQuestion(title, content);
        return "redirect:/questions";
    }

}
