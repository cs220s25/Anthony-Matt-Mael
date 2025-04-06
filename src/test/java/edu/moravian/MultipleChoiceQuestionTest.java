package edu.moravian;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleChoiceQuestionTest {

    @Test
    public void testGetText() {
        MultipleChoiceQuestion question = createSampleQuestion();
        assertEquals("What is your leadership style?", question.getText());
    }

    @Test
    public void testGetOptions() {
        MultipleChoiceQuestion question = createSampleQuestion();
        List<String> expectedOptions = Arrays.asList(
            "Lead from the front",
            "Strategize from behind the scenes",
            "Delegate and motivate others"
        );
        assertEquals(expectedOptions, question.getOptions());
    }

    @Test
    public void testGetRoleForOptionValidIndex() {
        MultipleChoiceQuestion question = createSampleQuestion();
        assertEquals(RoleEnum.Manager, question.getRoleForOption(0));
        assertEquals(RoleEnum.Strategist, question.getRoleForOption(1));
        assertEquals(RoleEnum.Speaker, question.getRoleForOption(2));
    }

    @Test
    public void testConstructorValidInputs() {
        MultipleChoiceQuestion question = createSampleQuestion();
        assertNotNull(question);
        assertEquals("What is your leadership style?", question.getText());
        assertEquals(3, question.getOptions().size());
        assertEquals(3, question.optionRoles.size());
    }


    private MultipleChoiceQuestion createSampleQuestion() {
        return new MultipleChoiceQuestion(
            1,
            "What is your leadership style?",
            Arrays.asList(
                "Lead from the front",
                "Strategize from behind the scenes",
                "Delegate and motivate others"
            ),
            Arrays.asList(
                RoleEnum.Manager,
                RoleEnum.Strategist,
                RoleEnum.Speaker
            )
        );
    }
}
