package wad.controller;

import javax.validation.Valid;
import wad.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.service.MessageService;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ModelAttribute("message")
    private Message getMessage() {
        return new Message();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("messages", messageService.list());
        return "messages";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Message message,
            BindingResult result) {
        if (result.hasErrors()) {
            return "messages";
        }

        messageService.addMessage(message);
        return "redirect:/messages";
    }
}
