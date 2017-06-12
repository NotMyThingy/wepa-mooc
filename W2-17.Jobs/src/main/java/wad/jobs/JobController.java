package wad.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("jobs", this.jobRepository.findAll().stream().filter(i -> !i.isDone()).toArray());
        model.addAttribute("pastJobs", this.jobRepository.findAll().stream().filter(i -> i.isDone()).toArray());
//    
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addJob(@RequestParam String name) {
        if (!name.trim().isEmpty()) {
            this.jobRepository.save(new Job(name));
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/jobs/{id}", method = RequestMethod.POST)
    public String jobDone(@PathVariable Long id) {
        this.jobRepository.getOne(id).setDone(true);
        this.jobRepository.flush();

        return "redirect:/";
    }

}
