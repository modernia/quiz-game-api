package io.modernia.question;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import messages.Messages;

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

    public Response create(Question question) {
        if(question.getId() != null) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(Messages.endpointMessage("Question ID must be null", Response.Status.BAD_REQUEST.getStatusCode()))
                .build();
        }
        questionRepository.persist(question);

        return Response.status(Response.Status.CREATED).entity(question).build();
    }


}
