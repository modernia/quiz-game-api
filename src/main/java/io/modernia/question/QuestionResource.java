package io.modernia.question;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import messages.Messages;

import java.util.Arrays;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static jakarta.ws.rs.core.Response.Status.OK;


@Path("/question")
@Transactional
public class QuestionResource {

    @Inject
    private QuestionRepository questionRepository;

    @GET
    public Response index() {
        return Response.ok(questionRepository.listAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response indexOne(@PathParam("id") Long id) {
        var question = questionRepository.findById(id);
        if(question == null) {
            return Response
                .status(NOT_FOUND)
                .entity(Messages.endpointMessage("Question not found", NOT_FOUND.getStatusCode()))
                .build();
        }
        return Response.ok(question).build();
    }

    @POST
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

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Question question) {
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

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        var question = questionRepository.findById(id);
        if(question == null) {
            return Response
                .status(NOT_FOUND)
                .entity(Messages.endpointMessage("Question not found", NOT_FOUND.getStatusCode()))
                .build();
        }
        questionRepository.delete(question);
        return Response.ok(Messages.endpointMessage("Question successfully deleted", OK.getStatusCode())).build();
    }

    @POST
    @Path("/various")
    public Response createVarious(Question[] questions) {
        Arrays.stream(questions).parallel().forEach(question -> {
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
