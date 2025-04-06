package edu.moravian;

import edu.moravian.exceptions.StorageException;

import java.util.ArrayList;
import java.util.List;

import java.util.*;

public class MemoryStorage implements QuestionStorage {
    private List<MultipleChoiceQuestion> questions;
    private int totalQuestions;
    private Map<String, Map<Integer, Integer>> userResponses;

    public MemoryStorage() {
        questions = new ArrayList<>();
        totalQuestions = 0;
        userResponses = new HashMap<>();
    }

    @Override
    public List<MultipleChoiceQuestion> loadQuestions() throws StorageException {
        return questions;
    }

    @Override
    public void addQuestion(MultipleChoiceQuestion question) throws StorageException {
        questions.add(question);
        totalQuestions++;
    }

    @Override
    public boolean removeQuestion(MultipleChoiceQuestion question) throws StorageException {
        return questions.remove(question);
    }

    @Override
    public void clearAllQuestions() throws StorageException {
        questions.clear();
        totalQuestions = 0;
        userResponses.clear();
    }

    @Override
    public int getTotalQuestions() throws StorageException {
        return totalQuestions;
    }

    @Override
    public void recordResponse(String userId, int questionIndex, int response) throws StorageException {
        userResponses.putIfAbsent(userId, new HashMap<>());

        userResponses.get(userId).put(questionIndex, response);
    }

    @Override
    public int getAnsweredQuestionsCount(String userid) throws StorageException {
        return userResponses.getOrDefault(userid, Collections.emptyMap()).size();
    }
}