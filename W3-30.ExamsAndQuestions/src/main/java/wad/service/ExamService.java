package wad.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.domain.Exam;
import wad.repository.ExamRepository;
import wad.repository.QuestionRepository;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    QuestionRepository questionRepository;

    public List<Exam> findAll() {
        return examRepository.findAll();
    }

    public Object findOne(Long id) {
        return examRepository.findOne(id);
    }

    @Transactional
    public void addNewExam(String subject, Date examDate) {
        Exam exam = new Exam();
        exam.setSubject(subject);
        exam.setExamDate(examDate);
        examRepository.save(exam);
    }

    public void addNewQuestion(Long examId, Long questionId) {
        examRepository
                .findOne(examId)
                .getQuestions()
                .add(questionRepository.findOne(questionId));
    }

}
