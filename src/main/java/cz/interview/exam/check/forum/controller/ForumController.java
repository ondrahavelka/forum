package cz.interview.exam.check.forum.controller;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cz.interview.exam.check.forum.model.RestApiErrorResponse;
import cz.interview.exam.check.forum.model.User;
import cz.interview.exam.check.forum.model.UserNotFoundException;
import cz.interview.exam.check.forum.service.ForumService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
public class ForumController implements GraphQLQueryResolver {

    private final ForumService forumService;

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @ExceptionHandler
    public ResponseEntity<RestApiErrorResponse> handleException(Exception e) {
        return ExceptionHandlerRest.processException(e);
    }

    @GetMapping(path = "/api/user/{userId}", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @ResponseBody
    public User extractUser(@PathVariable final int userId) throws InterruptedException, ExecutionException, IOException, UserNotFoundException {
        return forumService.extractUser(userId);
    }
}
