package edu.moravian;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BotResponderTest {

    private BotResponder createResponderWithNewSession() {
        RedisManager redisManager = new RedisManager();
        return new BotResponder(redisManager);
    }

    @Test
    void startCommandWithExistingSession() {
        BotResponder responder = createResponderWithNewSession();
        responder.respond(null, "user123", "!start");

        String response = responder.respond(null, "user123", "!start");
        assertEquals(BotResponses.sessionAlreadyInProgress(), response);
    }

    @Test
    void deleteCommandWithNoActiveSession() {
        BotResponder responder = createResponderWithNewSession();
        String response = responder.respond(null, "user123", "!delete");
        assertEquals(BotResponses.noActiveQuestionnaire(), response);
    }

    @Test
    void handleQuestionResponseOutOfRange() {
        BotResponder responder = createResponderWithNewSession();
        responder.respond(null, "user123", "!start");

        String response = responder.respond(null, "user123", "8");
        assertEquals(BotResponses.invalidResponse(), response);
    }

    @Test
    void handleMyRoleCommandWithNoActiveSession() {
        BotResponder responder = createResponderWithNewSession();
        String response = responder.respond(null, "user123", "!myrole");
        assertEquals(BotResponses.noActiveQuestionnaire(), response);
    }


}
