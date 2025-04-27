package edu.moravian;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.ArrayList;
import java.util.List;

public class RedisManager extends MemoryStorage {
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private final Jedis jedis;

    public RedisManager() {
        try {
            jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        } catch (JedisException e) {
            throw new RuntimeException("Could not connect to Redis server", e);
        }
    }

    public void saveSession(String userId, QuestionManager session) {
    try {
        jedis.set(userId, String.valueOf(session.getCurrentQuestionIndex()));
        jedis.del(userId + ":roles");
        for (RoleEnum role : session.getSelectedRoles()) {
            jedis.sadd("session:" + userId + ":roles", role.name());
        }
        jedis.del("session:" + userId + ":questions");
        for (MultipleChoiceQuestion question : session.getQuestions()) {
            jedis.rpush("session:" + userId + ":questions", question.toString());
        }
    } catch (JedisException e) {
        throw new RuntimeException("Failed to save session for user: " + userId, e);
    }
}



    public QuestionManager loadSession(String userId) {
    try {
        String currentIndexStr = jedis.get("session:" + userId + ":currentIndex");
        if (currentIndexStr == null) return null;
        int currentIndex = Integer.parseInt(currentIndexStr);
        List<RoleEnum> selectedRoles = new ArrayList<>();
        for (String roleName : jedis.smembers("session:" + userId + ":roles")) {
            selectedRoles.add(RoleEnum.valueOf(roleName));
        }
        List<String> serializedQuestions = jedis.lrange("session:" + userId + ":questions", 0, -1);
        List<MultipleChoiceQuestion> questions = new ArrayList<>();
        for (String serializedQuestion : serializedQuestions) {
            questions.add(MultipleChoiceQuestion.fromString(serializedQuestion));
        }
        QuestionManager session = new QuestionManager(questions);
        session.setCurrentQuestionIndex(currentIndex);
        session.getSelectedRoles().addAll(selectedRoles);
        return session;
    } catch (JedisException | NumberFormatException e) {
        throw new RuntimeException("Failed to load session for user: " + userId, e);
    }
}

    public void deleteSession(String userId) {
        try {
            jedis.del("session:" + userId + ":currentIndex");
            jedis.del("session:" + userId + ":roles");
        } catch (JedisException e) {
            throw new RuntimeException("Failed to delete session for user: " + userId, e);
        }
    }

    public boolean hasSession(String userId) {
        try {
            return jedis.exists("session:" + userId + ":currentIndex");
        } catch (JedisException e) {
            throw new RuntimeException("Could not connect to Redis server", e);
        }
    }

    public void resetSessions() {
        try {
            jedis.flushAll();
        } catch (JedisException e) {
            throw new RuntimeException("Could not reset sessions", e);
        }
    }

    public void testConnection() {
        try {
            jedis.ping();
        } catch (JedisException e) {
            throw new RuntimeException("Could not connect to Redis server", e);
        }
    }
}