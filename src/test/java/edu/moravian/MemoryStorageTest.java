package edu.moravian;

import edu.moravian.exceptions.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryStorageTest {

    private MemoryStorage memoryStorage;

    @BeforeEach
    void setUp() {
        memoryStorage = new MemoryStorage();
    }

    @Test
    void loadQuestionsReturnsEmptyListInitially() throws StorageException {
        List<MultipleChoiceQuestion> questions = memoryStorage.loadQuestions();
        assertTrue(questions.isEmpty());
    }

    @Test
    void addQuestionIncreasesTotalQuestions() throws StorageException {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(1, "What is 2+2?", List.of("3", "4", "5"),
                List.of(RoleEnum.Manager, RoleEnum.Strategist, RoleEnum.Speaker));
        memoryStorage.addQuestion(question);
        assertEquals(1, memoryStorage.getTotalQuestions());
    }


    @Test
    void clearAllQuestionsResetsTotalQuestions() throws StorageException {
        MultipleChoiceQuestion question1 = new MultipleChoiceQuestion(1, "What is 2+2?", List.of("3", "4", "5"),
                List.of(RoleEnum.Manager, RoleEnum.Strategist, RoleEnum.Speaker));
        MultipleChoiceQuestion question2 = new MultipleChoiceQuestion(2, "What is 3+3?", List.of("5", "6", "7"),
                List.of(RoleEnum.Manager, RoleEnum.Strategist, RoleEnum.Speaker));
        memoryStorage.addQuestion(question1);
        memoryStorage.addQuestion(question2);
        memoryStorage.clearAllQuestions();
        assertEquals(0, memoryStorage.getTotalQuestions());
    }

    @Test
    void recordResponseStoresUserResponse() throws StorageException {
        memoryStorage.recordResponse("user1", 0, 1);
        assertEquals(1, memoryStorage.getAnsweredQuestionsCount("user1"));
    }

    @Test
    void getAnsweredQuestionsCountReturnsZeroForNewUser() throws StorageException {
        assertEquals(0, memoryStorage.getAnsweredQuestionsCount("newUser"));
    }

    @Test
    void getAnsweredQuestionsCountReturnsCorrectCount() throws StorageException {
        memoryStorage.recordResponse("user1", 0, 1);
        memoryStorage.recordResponse("user1", 1, 2);
        assertEquals(2, memoryStorage.getAnsweredQuestionsCount("user1"));
    }
}