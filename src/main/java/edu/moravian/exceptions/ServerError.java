package edu.moravian.exceptions;

public class ServerError extends RuntimeException
{
    public ServerError(String s)
    {

        super("Server error" + s);
    }
}
