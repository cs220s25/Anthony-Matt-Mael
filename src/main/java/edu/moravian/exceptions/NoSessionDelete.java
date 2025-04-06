package edu.moravian.exceptions;

public class NoSessionDelete extends RuntimeException
{
    public NoSessionDelete()
    {
        super("No session to delete");
    }
}
