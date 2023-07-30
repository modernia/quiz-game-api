package io.modernia.question;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/question")
@Transactional
public class QuestionResource {
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
        return questionService.delete(id);
    }

    @POST
    @Path("/various")
    public Response createVarious(Question[] questions) {
        return questionService.createVarious(questions);
    }
}
