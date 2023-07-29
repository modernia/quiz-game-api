package io.modernia.question;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class QuestionTest {

    QuestionService questionService;


    @BeforeEach
    void setUp() {
        var question = new Question();
        question.setQuestion("What is the meaning of life?");
        question.setAnswer("42");
        question.setCode(null);
        question.setChoices(List.of("42", "43", "44", "45"));
        questionService.createQuestion(question);
    }

    @Test
    void checkIfQuestionIsCreated() {
        given().when().get("/question").then().statusCode(200)
            .contentType("application/json")
            .body(isA(String.class));
    }


}
