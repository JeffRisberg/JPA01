package org.justgive.exceptions;

public class JustGiveFileException extends JustGiveException
{
    public JustGiveFileException(String message)
    {
		super(message);
    }

    public JustGiveFileException(Exception e)
    {
		super(e);
    }
}
