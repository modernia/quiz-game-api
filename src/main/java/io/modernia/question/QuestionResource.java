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
    public QuestionRepository questionRepository;

    @Inject
    public QuestionService questionService;

    @GET
    public Response index() {
        return questionService.index();
    }

    @GET
    @Path("/{id}")
    public Response indexOne(@PathParam("id") Long id) {
        return questionService.indexOne(id);
    }

    @POST
    public Response create(Question question) {
        return questionService.create(question);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Question question) {
        return questionService.update(id, question);
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
