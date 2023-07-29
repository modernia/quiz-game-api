package io.modernia.question;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import messages.Messages;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Transactional
@ApplicationScoped
public class QuestionService {
    @Inject
    private QuestionRepository questionRepository;


    public Question createQuestion(Question question) {
        questionRepository.persist(question);
        return question;
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

    public Response index() {
        return Response.ok(questionRepository.listAll()).build();
    }

    public Response indexOne(Long id) {
        var question = questionRepository.findById(id);
        if(question == null) {
            return Response
                .status(NOT_FOUND)
                .entity(Messages.endpointMessage("Question not found", NOT_FOUND.getStatusCode()))
                .build();
        }
        return Response.ok(question).build();
    }


}
