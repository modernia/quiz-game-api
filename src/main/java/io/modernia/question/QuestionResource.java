package io.modernia.question;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/question")
public class QuestionResource {

    @Inject
    private QuestionRepository questionRepository;

    @GET
    public Response getQuestion() {
        return Response.ok(questionRepository.listAll()).build();
    }
}
