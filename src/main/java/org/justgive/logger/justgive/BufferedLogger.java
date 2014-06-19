package org.justgive.logger.justgive;

import org.justgive.logger.Level;
import org.justgive.logger.LoggingException;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * Copyright 2011 JustGive.org, 214 Sutter st, Suite #10,
 * San Francisco, Ca, 94808, U.S.A.  All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * JustGive.org ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with JustGive.
 *
 * CopyrightVersion 1.0
 *
 * -->
 *
 * @author Peter Cowan
 */
public class BufferedLogger
        extends JustGiveLogger
{
    private BufferedWriter bufferedLog;

    public BufferedLogger(BufferedWriter bufferedLog)
    {
        super();
        this.bufferedLog = bufferedLog;
    }

    protected void out(Level level) throws LoggingException
    {
        try {
            bufferedLog.write(getWriter().toString());
            bufferedLog.newLine();
        } catch (IOException e) {
            throw new LoggingException(e.getMessage());
        }
        initializeLogger();
    }
}
