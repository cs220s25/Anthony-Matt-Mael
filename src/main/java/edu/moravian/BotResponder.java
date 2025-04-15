

package edu.moravian;

import edu.moravian.exceptions.NoSessionDelete;
import edu.moravian.exceptions.NumberOutofRange;
import edu.moravian.exceptions.ServerError;
import edu.moravian.exceptions.SessionAlreadyInProgressException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BotResponder {
    private final Map<String, QuestionManager> activeSessions = new HashMap<>();
    private final RedisManager redisManager;

    public BotResponder(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public String respond(MessageReceivedEvent event, String userId, String message) {
        try {
            if (message.equals("!start")) {
                return handleStartCommand(userId);
            } else if (message.equals("!help")) {
                return BotResponses.helpMessage();
            } else if (message.equals("!delete")) {
                return handleDeleteCommand(userId);
            } else if (message.equals("!myrole")) {
                return handleMyRoleCommand(userId);

            }

            QuestionManager questionnaire = activeSessions.get(userId);

            if (questionnaire == null) {
                return BotResponses.noActiveQuestionnaire();
            }

            if (questionnaire.isComplete()) {
                String roleSummary = generateRoleSummary(questionnaire.getSelectedRoles());
                System.out.println("Returning final response: Based on your responses, your role is: " + roleSummary);
                return BotResponses.questionnaireCompleted(roleSummary);
            }

            boolean b = questionnaire.recordUserResponse(Integer.parseInt(message) - 1);
            if (b) {
                return handleQuestionResponse(userId, questionnaire, message);
            } else {
                return BotResponses.invalidResponse();
            }



        } catch (SessionAlreadyInProgressException e) {
            System.err.println("SessionAlreadyInProgressException: " + e.getMessage());
            return BotResponses.sessionAlreadyInProgress();
        } catch (NoSessionDelete e) {
            System.err.println("NoSessionDelete: " + e.getMessage());
            return BotResponses.noActiveQuestionnaire();
        } catch (NumberOutofRange e) {
            System.err.println("NumberOutofRange: " + e.getMessage());
            return BotResponses.invalidResponse();
        } catch (ServerError e) {
            System.err.println("ServerError: " + e.getMessage());
            return BotResponses.serverError();
        } catch (Exception e) {
            System.err.println("Unhandled exception: " + e.getMessage());
            e.printStackTrace();
            return BotResponses.serverError();
        }
    }

    private String handleMyRoleCommand(String userId) throws NoSessionDelete {
        QuestionManager questionnaire = activeSessions.get(userId);
        if (questionnaire == null) {
            return BotResponses.noActiveQuestionnaire();
        }

        String roleSummary = generateRoleSummary(questionnaire.getSelectedRoles());
        return BotResponses.displayRole(roleSummary);
    }

    private String handleStartCommand(String userId) throws SessionAlreadyInProgressException {
        if (activeSessions.containsKey(userId)) {
            throw new SessionAlreadyInProgressException();
        }

        QuestionManager questionnaire = new QuestionManager(QuestionLoader.loadQuestions());
        redisManager.saveSession(userId, questionnaire);
        activeSessions.put(userId, questionnaire);

        MultipleChoiceQuestion firstQuestion = questionnaire.getNextQuestion();
        StringBuilder options = new StringBuilder();
        for (int i = 0; i < firstQuestion.getOptions().size(); i++) {
            options.append(i + 1).append(". ").append(firstQuestion.getOptions().get(i)).append("\n");
        }
        return BotResponses.nextQuestion(firstQuestion.getText(), options.toString());

    }

    private String handleDeleteCommand(String userId) throws NoSessionDelete {
        if (activeSessions.containsKey(userId)) {
            activeSessions.remove(userId);
            redisManager.deleteSession(userId);
            return BotResponses.questionnaireDeleted();
        } else {
            return BotResponses.noActiveQuestionnaire();
        }

    }

    private String handleQuestionResponse(String userId, QuestionManager questionnaire, String message) throws
            NumberOutofRange {
        int answer = Integer.parseInt(message) - 1;
        boolean valid = questionnaire.recordUserResponse(answer);

        if (valid) {
            if (questionnaire.isComplete()) {
                activeSessions.remove(userId);
                String roleSummary = generateRoleSummary(questionnaire.getSelectedRoles());
                return BotResponses.questionnaireCompleted(roleSummary);
            }

            MultipleChoiceQuestion nextQuestion = questionnaire.getNextQuestion();
            StringBuilder options = new StringBuilder();
            for (int i = 0; i < nextQuestion.getOptions().size(); i++) {
                options.append(i + 1).append(". ").append(nextQuestion.getOptions().get(i)).append("\n");
            }
            return BotResponses.nextQuestion(nextQuestion.getText(), options.toString());
        } else {
            return BotResponses.invalidResponse();
        }
    }

    private String generateRoleSummary(List<RoleEnum> roles) {
        Map<RoleEnum, Long> roleCounts = roles.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        RoleEnum mostFrequentRole = roleCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return mostFrequentRole != null ? mostFrequentRole.getDescription() : "Unknown";
    }
}
