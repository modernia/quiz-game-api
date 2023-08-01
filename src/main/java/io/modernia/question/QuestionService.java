package io.modernia.question;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import messages.Messages;

import java.util.Arrays;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static jakarta.ws.rs.core.Response.Status.OK;

@Transactional
@ApplicationScoped
public class QuestionService {
    @Inject
    private QuestionRepository questionRepository;

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

    public Response update(Long id, Question question) {
        var questionToUpdate = questionRepository.findById(id);
        if(questionToUpdate == null) {
            return Response
                .status(NOT_FOUND)
                .entity(Messages.endpointMessage("Question not found", NOT_FOUND.getStatusCode()))
                .build();
        }
        questionToUpdate.setQuestion(question.getQuestion());
        questionToUpdate.setAnswer(question.getAnswer());
        questionToUpdate.setChoices(question.getChoices());
        questionRepository.persist(questionToUpdate);
        return Response.ok(questionToUpdate).build();
    }

    public Response delete(Long id) {
        var question = questionRepository.deleteById(id);
        if(!question) {
            return Response
                .status(NOT_FOUND)
                .entity(Messages.endpointMessage("Question not found", NOT_FOUND.getStatusCode()))
                .build();
        }
        return Response.ok(Messages.endpointMessage("Question successfully deleted", OK.getStatusCode())).build();
    }

    public Response createVarious(Question[] questions) {
        Arrays.stream(questions).forEach(question -> {
            if(question.getId() == null) {
                questionRepository.persist(question);
            }
        });
        return Response
            .status(Response.Status.CREATED)
            .entity(Messages.endpointMessage("Questions successfully created", Response.Status.CREATED.getStatusCode()))
            .build();
    }

}
