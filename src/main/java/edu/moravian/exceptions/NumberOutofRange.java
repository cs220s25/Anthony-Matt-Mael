package edu.moravian.exceptions;

public class NumberOutofRange extends IllegalArgumentException
{
    public NumberOutofRange()
    {
        super("The number is out of range");
    }
}
