package org.justgive.logger.bin;

import org.justgive.logger.Level;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.logger.LoggingException;
import org.justgive.logger.justgive.UnixLogger;
import org.justgive.logger.log4j.Log4jLogger;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Jan 9, 2008
 * Time: 1:35:54 PM
 */
public class LogTest
{
	public static void main(String[] args)
		throws LoggingException
	{
		//Logger jgLog = LoggerFactory.getInstance();
		//Logger jgLog = new UpperCaseWrapper(LoggerFactory.getInstance());
		//Logger jgLog = new UpperCaseWrapper(new HyphenWrapper(new LabelWrapper(new SystemOutLogger())));
		//Logger cnLog = new HyphenWrapper(new LabelWrapper(new SystemErrLogger()));
		Logger l4Log = new Log4jLogger(LogTest.class.getName());



		System.out.println("Log4jLogger");
		System.out.println("---------------------------------------");
		LogTest.testLogger(l4Log);
		System.out.println("---------------------------------------\n\n");
		Logger dfLog = LoggerFactory.getLogger(LogTest.class);
		System.out.println("SystemOutLogger");
		System.out.println("UpperCaseWrapper");
		System.out.println("DefaultLogger");
		System.out.println("---------------------------------------");
		LogTest.testLogger(dfLog);

		Logger unixLog = new UnixLogger();
		System.out.println("UnixLogger");
		System.out.println("---------------------------------------");
		LogTest.testLogger(unixLog);

	}

	private static void testLogger(Logger jgLog)
	{
		System.out.println("Logger Levels");
		System.out.println(Level.TRACE + ": " + Level.TRACE.toInt());
		System.out.println(Level.DEBUG + ": " + Level.DEBUG.toInt());
		System.out.println(Level.INFO + ": " + Level.INFO.toInt());
		System.out.println(Level.WARN + ": " + Level.WARN.toInt());
		System.out.println(Level.ERROR + ": " + Level.ERROR.toInt());
		System.out.println(Level.FATAL + ": " + Level.FATAL.toInt());
		System.out.println(Level.OFF + ": " + Level.OFF.toInt());

		//jgLog.setThreshold(Level.TRACE);
		System.out.println(Level.TRACE);
		jgLog.log("hey, i am logging"); //log will output the message no matter what
		jgLog.trace("hey, i am tracing"); //the rest will output based on the threshold Level
		jgLog.debug("hey, i am debugging");
		jgLog.info("hey, i am infoing");
		jgLog.warn("hey, i am warning");
		jgLog.error("hey, i am erring");
		jgLog.fatal("hey, i am fataling");

		//jgLog.setThreshold(Level.WARN);
		System.out.println(Level.WARN);
		jgLog.log("hey, i am logging");
		jgLog.trace("hey, i am tracing");
		jgLog.debug("hey, i am debugging");
		jgLog.info("hey, i am infoing");
		jgLog.warn("hey, i am warning");
		jgLog.error("hey, i am erring");
		jgLog.fatal("hey, i am fataling");

		//jgLog.setThreshold(Level.TRACE);
		System.out.println(Level.TRACE);
		try { throw new LoggingException("hey, i am logging"); }
		catch (LoggingException e) { jgLog.log(e); }//log will output the message no matter what
		try { throw new LoggingException("hey, i am tracing"); }
		catch (LoggingException e) { jgLog.trace(e); }//the rest will output based on the threshold Level
		try { throw new LoggingException("hey, i am debugging"); }
		catch (LoggingException e) { jgLog.debug(e); }
		try { throw new LoggingException("hey, i am infoing"); }
		catch (LoggingException e) { jgLog.info(e); }
		try { throw new LoggingException("hey, i am warning"); }
		catch (LoggingException e) { jgLog.warn(e); }
		try { throw new LoggingException("hey, i am erring"); }
		catch (LoggingException e) { jgLog.error(e); }
		try { throw new LoggingException("hey, i am fataling"); }
		catch (LoggingException e) { jgLog.fatal(e); }
	}
}
