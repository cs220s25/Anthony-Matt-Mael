package edu.moravian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionManagerTest {

    private QuestionManager questionManager;
    private List<MultipleChoiceQuestion> questions;

    @BeforeEach
    public void setUp() {
        questions = Arrays.asList(
            new MultipleChoiceQuestion( 1,
                "What is your leadership style?",
                Arrays.asList("Lead from the front", "Strategize from behind the scenes", "Delegate and motivate others", "Document and debug"),
                Arrays.asList(RoleEnum.Manager, RoleEnum.Strategist, RoleEnum.Speaker, RoleEnum.Debugger)
            ),
            new MultipleChoiceQuestion( 1,
                "How do you approach solving problems?",
                Arrays.asList("Create detailed plans", "Dive in and debug", "Explain solutions clearly to others", "Document and test"),
                Arrays.asList(RoleEnum.Strategist, RoleEnum.Debugger, RoleEnum.Speaker)
            )
        );
        questionManager = new QuestionManager(questions);
    }

    @Test
    public void testGetNextQuestion() {
        assertEquals(questions.get(0), questionManager.getNextQuestion());
        assertEquals(questions.get(1), questionManager.getNextQuestion());
        assertNull(questionManager.getNextQuestion());
    }

    @Test
    public void testRecordUserResponse() {
        questionManager.getNextQuestion();
        questionManager.recordUserResponse(0);
        assertEquals(RoleEnum.Manager, questionManager.determineUserRole());
    }

    @Test
    public void testDetermineUserRole() {
        questionManager.getNextQuestion();
        questionManager.recordUserResponse(1);
        questionManager.getNextQuestion();
        questionManager.recordUserResponse(2);
        assertEquals(RoleEnum.Strategist, questionManager.determineUserRole());
    }

    @Test
    public void testIsComplete() {
        assertFalse(questionManager.isComplete());
        questionManager.getNextQuestion();
        questionManager.getNextQuestion();
        assertTrue(questionManager.isComplete());
    }

    @Test
    public void testGetCurrentQuestionIndex() {
        assertEquals(0, questionManager.getCurrentQuestionIndex());
        questionManager.getNextQuestion();
        assertEquals(1, questionManager.getCurrentQuestionIndex());
    }

    @Test
    public void testNoResponsesRecorded() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, questionManager::determineUserRole);
        assertEquals("No responses recorded yet.", exception.getMessage());
    }
}