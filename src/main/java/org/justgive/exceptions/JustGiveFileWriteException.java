package org.justgive.exceptions;

public class JustGiveFileWriteException extends JustGiveFileException
{
    public JustGiveFileWriteException(String message)
    {
		super(message);
    }

    public JustGiveFileWriteException(Exception e)
    {
		super(e);
    }
}
