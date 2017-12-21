package org.justgive.logger.justgive;

import org.justgive.logger.Level;
import org.justgive.logger.LoggingException;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 */
public class BufferedLogger extends JustGiveLogger {
    private BufferedWriter bufferedLog;

    public BufferedLogger(BufferedWriter bufferedLog) {
        super();
        this.bufferedLog = bufferedLog;
    }

    protected void out(Level level) throws LoggingException {
        try {
            bufferedLog.write(getWriter().toString());
            bufferedLog.newLine();
        } catch (IOException e) {
            throw new LoggingException(e.getMessage());
        }
        initializeLogger();
    }
}
