package cz.interview.exam.check.forum.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.interview.exam.check.forum.model.Post;
import cz.interview.exam.check.forum.model.User;
import cz.interview.exam.check.forum.model.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class ForumService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${users.url}")
    private String userUrl;

    @Value("${post.url}")
    private String postUrl;

    public User extractUser(int userId) throws InterruptedException, ExecutionException, IOException, UserNotFoundException {
        return getUsers(userId);
    }

    private User getUsers(int userId) throws IOException, InterruptedException, ExecutionException, UserNotFoundException {
        CompletableFuture<HttpResponse<String>> userResponse = getHttpResponseCompletableFuture(userId, userUrl);
        CompletableFuture<HttpResponse<String>> postsResponse = getHttpResponseCompletableFuture(userId, postUrl);
        HttpResponse<String> userHttpResponse = userResponse.get();
        HttpResponse<String> postsHttpResponse = postsResponse.get();
        validateStatusCode(userId, userHttpResponse);
        validateStatusCode(userId, postsHttpResponse);
        User user = objectMapper.readValue(userHttpResponse.body(), User.class);
        List<Post> post = objectMapper.readValue(postsHttpResponse.body(), new TypeReference<>() {
        });
        user.setPosts(post);

        return user;
    }

    private void validateStatusCode(int userId, HttpResponse<String> userHttpResponse) throws UserNotFoundException {
        if (HttpStatus.valueOf(userHttpResponse.statusCode()).is4xxClientError()) {
            throw new UserNotFoundException(String.format("User id %d and his posts was not found", userId));
        }
    }

    private CompletableFuture<HttpResponse<String>> getHttpResponseCompletableFuture(int userId, String userUrl) {
        var userRequest = HttpRequest.newBuilder().GET().uri(URI.create(userUrl + userId)).build();
        return HttpClient.newHttpClient().sendAsync(userRequest, HttpResponse.BodyHandlers.ofString());
    }


}
