package io.modernia.question;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
@ApplicationScoped
public class QuestionService {
    @Inject
    private QuestionRepository questionRepository;


    public Question createQuestion(Question question) {
        questionRepository.persist(question);
        return question;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.listAll();
    }
}
