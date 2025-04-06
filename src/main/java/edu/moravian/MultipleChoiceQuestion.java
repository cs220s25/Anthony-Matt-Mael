package edu.moravian;

import edu.moravian.exceptions.NumberOutofRange;
import edu.moravian.exceptions.SessionAlreadyInProgressException;

import java.util.List;
import java.util.stream.Collectors;

public class MultipleChoiceQuestion {
    private int id;
    private String text;
    private List<String> options;
    public List<RoleEnum> optionRoles;
    private static String activePlayer = null;

    public MultipleChoiceQuestion(int id, String text, List<String> options, List<RoleEnum> optionRoles) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.optionRoles = optionRoles;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public RoleEnum getRoleForOption(int index) throws NumberOutofRange {
        if (index >= 0 && index < optionRoles.size()) {
            return optionRoles.get(index);
        }
        throw new NumberOutofRange();
    }




    /**
     * Converts the question to a string representation.
     */
    @Override
    public String toString() {
        String optionsString = String.join(",", options);
        String rolesString = optionRoles.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
        return id + "|" + text + "|" + optionsString + "|" + rolesString;
    }

    /**
     * Parses a question from a string representation.
     */
    public static MultipleChoiceQuestion fromString(String serialized) {
        String[] parts = serialized.split("\\|");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid serialized question format.");
        }

        int id = Integer.parseInt(parts[0]);
        String text = parts[1];
        List<String> options = List.of(parts[2].split(","));
        List<RoleEnum> optionRoles = List.of(parts[3].split(","))
                .stream()
                .map(RoleEnum::valueOf)
                .collect(Collectors.toList());

        return new MultipleChoiceQuestion(id, text, options, optionRoles);
    }
}