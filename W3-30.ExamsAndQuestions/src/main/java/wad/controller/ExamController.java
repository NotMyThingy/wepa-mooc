package wad.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.service.ExamService;
import wad.service.QuestionService;

@Controller
public class ExamController {

    @Autowired
    private ExamService examService;
    
    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/exams", method = RequestMethod.GET)
    public String listExams(Model model) {
        model.addAttribute("exams", examService.findAll());
        return "exams";
    }

    @RequestMapping(value = "/exams/{id}", method = RequestMethod.GET)
    public String viewExam(Model model, @PathVariable Long id) {
        model.addAttribute("exam", examService.findOne(id));
        model.addAttribute("questions", questionService.findAll());
        return "exam";
    }

    @RequestMapping(value = "/exams", method = RequestMethod.POST)
    public String addExam(@RequestParam String subject,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date examDate) {
        examService.addNewExam(subject, examDate);
        return "redirect:/exams";
    }

    @RequestMapping(value = "/exams/{examId}/questions/{questionId}", method = RequestMethod.POST)
    @Transactional
    public String addQuestionToExam(@PathVariable Long examId, @PathVariable Long questionId) {
        examService.addNewQuestion(examId, questionId);
        return "redirect:/exams/" + examId;
    }
}
