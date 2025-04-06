package edu.moravian;

import edu.moravian.exceptions.NumberOutofRange;
import edu.moravian.exceptions.ServerError;

import java.util.ArrayList;
import java.util.List;

public class QuestionManager {

    private final List<MultipleChoiceQuestion> questions;
    private int currentQuestionIndex = 0;
    private final List<RoleEnum> selectedRoles = new ArrayList<>();

    public QuestionManager(List<MultipleChoiceQuestion> questions) throws ServerError {
        if (questions == null || questions.isEmpty()) {
            throw new IllegalArgumentException("Questions list cannot be null or empty.");
        }
        this.questions = new ArrayList<>(questions);
    }

    public MultipleChoiceQuestion getNextQuestion() throws ServerError {
        if (!isComplete()) {
            System.out.println("Fetching question index: " + currentQuestionIndex);
            return questions.get(currentQuestionIndex++);
        }
        System.out.println("No more questions. Questionnaire is complete.");
        return null;
    }

    public boolean recordUserResponse(int optionIndex) throws NumberOutofRange {
        if (optionIndex < 1 || optionIndex > 4) {
            BotResponses.invalidResponse();
        }
        if (currentQuestionIndex > 0) {
            RoleEnum role = questions.get(currentQuestionIndex - 1).getRoleForOption(optionIndex);
            selectedRoles.add(role);
            System.out.println("Recorded response: " + optionIndex + " -> Role: " + role);
            return true;
        }
        return false;
    }

    public RoleEnum determineUserRole() throws ServerError {
        if (selectedRoles.isEmpty()) {
            throw new IllegalStateException("No responses recorded yet.");
        }

        int[] roleCounts = new int[RoleEnum.values().length];
        for (RoleEnum role : selectedRoles) {
            roleCounts[role.ordinal()]++;
        }

        RoleEnum mostFrequentRole = null;
        int maxCount = 0;
        for (int i = 0; i < roleCounts.length; i++) {
            if (roleCounts[i] > maxCount) {
                maxCount = roleCounts[i];
                mostFrequentRole = RoleEnum.values()[i];
            }
        }

        return mostFrequentRole;
    }

    public boolean isComplete() throws ServerError {
        boolean complete = currentQuestionIndex >= questions.size();
        System.out.println("Questionnaire complete status: " + complete);
        return complete;
    }

    public List<RoleEnum> getSelectedRoles() throws ServerError {
        return new ArrayList<>(selectedRoles);
    }

    public int getCurrentQuestionIndex() throws ServerError {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentIndex) throws ServerError {
        if (currentIndex < 0 || currentIndex >= questions.size()) {
            throw new IllegalArgumentException("Invalid question index.");
        }
        currentQuestionIndex = currentIndex;
    }

    public MultipleChoiceQuestion[] getQuestions() throws ServerError {
        return questions.toArray(new MultipleChoiceQuestion[0]);
    }

}