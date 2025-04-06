package edu.moravian;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionLoaderTest {

    @Test
    public void testLoadQuestions() {
        List<MultipleChoiceQuestion> questions = QuestionLoader.loadQuestions();

        assertEquals(5, questions.size());

        MultipleChoiceQuestion question1 = questions.get(0);
        assertEquals(1, question1.getId());
        assertEquals("What do you prefer?", question1.getText());
        assertEquals(
            List.of("Organizing tasks", "Solving problems", "Talking to people", "Testing and documenting"),
            question1.getOptions()
        );
        assertEquals(RoleEnum.Manager, question1.getRoleForOption(0));
        assertEquals(RoleEnum.Strategist, question1.getRoleForOption(1));
        assertEquals(RoleEnum.Speaker, question1.getRoleForOption(2));
        assertEquals(RoleEnum.Debugger, question1.getRoleForOption(3));

        MultipleChoiceQuestion question2 = questions.get(1);
        assertEquals(2, question2.getId());
        assertEquals("How do you approach a new project?", question2.getText());
        assertEquals(
            List.of("Create a detailed plan", "Dive in and debug", "Explain solutions clearly to others", "Get everyone in the group on a task"),
            question2.getOptions()
        );
        assertEquals(RoleEnum.Strategist, question2.getRoleForOption(0));
        assertEquals(RoleEnum.Debugger, question2.getRoleForOption(1));
        assertEquals(RoleEnum.Speaker, question2.getRoleForOption(2));
        assertEquals(RoleEnum.Manager, question2.getRoleForOption(3));

        MultipleChoiceQuestion question3 = questions.get(2);
        assertEquals(3, question3.getId());
        assertEquals("What is your leadership style?", question3.getText());
        assertEquals(
            List.of("Lead from the front", "Strategize from behind the scenes", "Delegate and motivate others", "Document and debug"),
            question3.getOptions()
        );
        assertEquals(RoleEnum.Manager, question3.getRoleForOption(0));
        assertEquals(RoleEnum.Strategist, question3.getRoleForOption(1));
        assertEquals(RoleEnum.Speaker, question3.getRoleForOption(2));
        assertEquals(RoleEnum.Debugger, question3.getRoleForOption(3));

        MultipleChoiceQuestion question4 = questions.get(3);
        assertEquals(4, question4.getId());
        assertEquals("How do you approach solving problems?", question4.getText());
        assertEquals(
            List.of("Map out different aspects of the problem", "Dive in and debug", "Explain solutions clearly to others",
                    "Take the problem head on and have everyone on the team do their assigned roles"),
            question4.getOptions()
        );
        assertEquals(RoleEnum.Strategist, question4.getRoleForOption(0));
        assertEquals(RoleEnum.Debugger, question4.getRoleForOption(1));
        assertEquals(RoleEnum.Speaker, question4.getRoleForOption(2));
        assertEquals(RoleEnum.Manager, question4.getRoleForOption(3));
    }

    @Test
    public void testQuestionsAreDistinct() {
        List<MultipleChoiceQuestion> questions = QuestionLoader.loadQuestions();

        assertEquals(5, questions.stream().map(MultipleChoiceQuestion::getId).distinct().count());
    }

    @Test
    public void testAllOptionsHaveRoles() {
        List<MultipleChoiceQuestion> questions = QuestionLoader.loadQuestions();

        for (MultipleChoiceQuestion question : questions) {
            assertEquals(question.getOptions().size(), question.optionRoles.size());
        }
    }
}
