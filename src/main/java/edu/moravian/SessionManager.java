package edu.moravian;

import edu.moravian.exceptions.NoSessionDelete;
import edu.moravian.exceptions.SessionAlreadyInProgressException;

public class SessionManager {
    private static String activeSessionPlayer = null;

    public void startSession(String playerName) throws SessionAlreadyInProgressException {
        if (activeSessionPlayer == null) {
            activeSessionPlayer = playerName;
            System.out.println("Session started for: " + playerName);
        } else {
            throw new SessionAlreadyInProgressException();
        }
    }
    

    public void endSession(String playerName) throws NoSessionDelete {
        if (activeSessionPlayer != null && activeSessionPlayer.equals(playerName)) {
            System.out.println("Session ended for: " + playerName);
            activeSessionPlayer = null;
        } else {
            throw new NoSessionDelete();
        }
    }

    public boolean isSessionActive() {
        return activeSessionPlayer != null;
    }

    public String getActiveSessionPlayer() {
        return activeSessionPlayer;
    }
}