package wad.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.domain.Exam;
import wad.domain.Question;
import wad.repository.ExamRepository;
import wad.repository.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ExamRepository examRepository;

    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    @Transactional
    public void addQuestion(String title, String content) {
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        questionRepository.save(question);
    }

    public List<Question> findAllNotListed(Long id) {
        Exam exam = examRepository.findOne(id);
        List<Question> questions = new ArrayList<>();
        for (Question question : questionRepository.findAll()) {
            if (!exam.getQuestions().contains(question)) {
                questions.add(question);
            }
        }

        return questions;
    }
}
