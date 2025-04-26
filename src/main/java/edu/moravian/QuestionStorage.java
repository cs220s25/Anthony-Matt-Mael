package edu.moravian;

import edu.moravian.exceptions.StorageException;

import java.util.List;


public interface QuestionStorage {
    /**
     * Loads all questions from the storage.
     *
     * @return a list of MultipleChoiceQuestion objects.
     */
    List<MultipleChoiceQuestion> loadQuestions() throws StorageException;

    /**
     * Adds a new question to the storage.
     *
     * @param question the question to add.
     */
    void addQuestion(MultipleChoiceQuestion question) throws StorageException;

    /**
     * Removes a question from the storage.
     *
     * @param question the question to remove.
     * @return true if the question was removed, false otherwise.
     */
    boolean removeQuestion(MultipleChoiceQuestion question) throws StorageException;

    boolean changeQuestion(MultipleChoiceQuestion question) throws StorageException;

    /**
     * Clears all questions from the storage.
     */
    void clearAllQuestions() throws StorageException;

    /**
     * Retrieves the total number of questions in the storage.
     *
     * @return the total number of questions.
     */
    int getTotalQuestions() throws StorageException;

    void recordResponse(String user123, int i, int i1) throws StorageException;

    int getAnsweredQuestionsCount(String userid) throws StorageException;
}
