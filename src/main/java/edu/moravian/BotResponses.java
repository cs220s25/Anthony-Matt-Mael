package edu.moravian;

public class BotResponses {

   public static String helpMessage() {
    return """
            **Available Commands:**
            - `!start`: Start your questionnaire.
            - `!delete`: Delete your current questionnaire session.
            - `!myrole`: Display your current role.
            - `!help`: Show this help message.
            """;
}



    public static String noActiveQuestionnaire() {
        return "No active questionnaire. Type `!start` to begin.";
    }



    public static String displayRole(String roleSummary) {
        return "Your current role is: **" + roleSummary + "**";
    }


    public static String questionnaireDeleted() {
        return "Your questionnaire session has been deleted.";
    }

    public static String invalidResponse() {
        return "Invalid input. Please respond with a number corresponding to an option.";
    }

    public static String questionnaireCompleted(String role) {
        return "Based on your responses, your role is: **" + role + "**";
    }

    public static String nextQuestion(String questionText, String options) {
        return "**" + questionText + "**\n" + options;
    }


    public static String serverError() {
        return "Uh oh! Something went wrong on our end. Please try again later.";
    }

    public static String sessionAlreadyInProgress() {
        return "You already have an active questionnaire session. Type `!delete` to delete it.";
    }



}
