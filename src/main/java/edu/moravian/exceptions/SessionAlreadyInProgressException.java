package edu.moravian.exceptions;

public class SessionAlreadyInProgressException extends RuntimeException
{
    public SessionAlreadyInProgressException()
    {
        super("Session already in progress");
    }
}
