package edu.moravian;

import edu.moravian.exceptions.StorageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RedisTest {

    private RedisManager redisManager;

    @BeforeEach
    void setUp() {
        redisManager = new RedisManager();
        redisManager.resetSessions();
    }

    @AfterEach
    void tearDown() {
        redisManager.resetSessions();
    }

    @Test
    void testSaveAndLoadSession() throws StorageException {
        QuestionStorage storage = new MemoryStorage();
        List<MultipleChoiceQuestion> questions = QuestionLoader.loadQuestions();

        assertFalse(questions.isEmpty(), "Questions should not be empty for the test");

        storage.recordResponse("user123", 0, 1);
        storage.recordResponse("user123", 1, 2);

        assertEquals(2, storage.getAnsweredQuestionsCount("user123"), "Answered questions count should match");
    }

    @Test
    void testDeleteSession() {
        String userId = "user1";
        QuestionManager questionManager = new QuestionManager(QuestionLoader.loadQuestions());
        redisManager.saveSession(userId, questionManager);

        redisManager.deleteSession(userId);

        assertNull(redisManager.loadSession(userId), "Session should be null after deletion");
    }



    @Test
    void testResetSessions() {
        String userId1 = "user1";
        String userId2 = "user2";
        QuestionManager questionManager = new QuestionManager(QuestionLoader.loadQuestions());

        redisManager.saveSession(userId1, questionManager);
        redisManager.saveSession(userId2, questionManager);

        redisManager.resetSessions();

        assertNull(redisManager.loadSession(userId1), "Session 1 should be null after reset");
        assertNull(redisManager.loadSession(userId2), "Session 2 should be null after reset");
    }
}