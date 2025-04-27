package edu.moravian;

import java.util.ArrayList;
import java.util.List;

public class QuestionLoader {
    public static List<MultipleChoiceQuestion> loadQuestions() {
        List<MultipleChoiceQuestion> questions = new ArrayList<>();
        questions.add(new MultipleChoiceQuestion(
                1,
                "What do you prefer?",
                List.of("Organizing tasks", "Solving problems", "Talking to people", "Testing and documenting"),
                List.of(RoleEnum.Manager, RoleEnum.Strategist, RoleEnum.Speaker, RoleEnum.Debugger)
        ));
        questions.add(new MultipleChoiceQuestion(
                2,
                "How do you approach a new project?",
                List.of("Create a detailed plan", "Dive in and debug", "Explain solutions clearly to others", "Get everyone in the group on a task"),
                List.of(RoleEnum.Strategist, RoleEnum.Debugger, RoleEnum.Speaker, RoleEnum.Manager)
        ));
        questions.add(new MultipleChoiceQuestion(
                3,
                "What is your leadership style?",
                List.of("Lead from the front", "Strategize from behind the scenes", "Delegate and motivate others", "Document and debug"),
                List.of(RoleEnum.Manager, RoleEnum.Strategist, RoleEnum.Speaker, RoleEnum.Debugger)
        ));
        questions.add(new MultipleChoiceQuestion(
                4,
                "How do you approach solving problems?",
                List.of("Map out different aspects of the problem", "Dive in and debug", "Explain solutions clearly to others",
                        "Take the problem head on and have everyone on the team do their assigned roles"),
                List.of(RoleEnum.Strategist, RoleEnum.Debugger, RoleEnum.Speaker, RoleEnum.Manager)
        ));
        questions.add(new MultipleChoiceQuestion(
                5,
                "What's your favorite part of programming?",
                List.of("Fixing errors and updating the team", "Presenting and explaining the code to peers",
                        "Leading the team in one direction", "Planning out the project"),
                List.of(RoleEnum.Debugger, RoleEnum.Speaker, RoleEnum.Manager, RoleEnum.Strategist)
        ));
        return questions;
    }
}

