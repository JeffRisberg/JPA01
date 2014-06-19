package org.justgive.exceptions;

public class JustGiveDatabaseUpdateObjectException extends JustGiveDatabaseWriteException 
{
    public JustGiveDatabaseUpdateObjectException(String message) 
    {
		super(message);
    }
}
