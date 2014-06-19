package org.justgive.exceptions;

public class JustGiveFileReadException extends JustGiveFileException
{
    public JustGiveFileReadException(String message)
    {
		super(message);
    }

    public JustGiveFileReadException(Exception e)
    {
		super(e);
    }
}
