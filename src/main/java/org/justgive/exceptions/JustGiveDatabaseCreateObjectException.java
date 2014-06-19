package org.justgive.exceptions;

public class JustGiveDatabaseCreateObjectException extends JustGiveDatabaseWriteException 
{
    public JustGiveDatabaseCreateObjectException(String message) 
    {
		super(message);
    }
}
