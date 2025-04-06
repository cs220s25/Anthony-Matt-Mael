package edu.moravian;

public enum RoleEnum {
    Manager,
    Strategist,
    Speaker,
    Debugger;

    public String getDescription() {
        return switch (this) {
            case Manager -> "You are a manager. You are a leader who is responsible for the work of a group of people.";
            case Strategist -> "You are a strategist. You are a person who is skilled in planning for the future.";
            case Speaker ->
                    "You are a speaker. You are a person who is skilled in communicating information to others.";
            case Debugger ->
                    "You are a debugger. You are a person who is skilled in finding and fixing problems in computer programs.";
        };
    }
}

