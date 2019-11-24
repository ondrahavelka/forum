package cz.interview.exam.check.forum;

import cz.interview.exam.check.forum.controller.ForumController;
import cz.interview.exam.check.forum.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ForumControllerIT {


    @Autowired
    private ForumController controller;

    @Test
    void testExtractUser() throws Exception {
        User user = controller.extractUser(1);
        assertNotNull(user);
        assertNotNull(user.getPosts());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUsername());
        assertEquals(1, user.getId());
        assertEquals(10, user.getPosts().size());
    }
}

