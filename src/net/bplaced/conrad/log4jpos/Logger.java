/*
 * Copyright 2018 Martin Conrad
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package net.bplaced.conrad.log4jpos;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Handler;

import static net.bplaced.conrad.log4jpos.Appender.*;

/**
 * Replacement class for log4j logger, based on java.util.logging. Restrictions:
 * <ul>
 *     <li>Only methods getLogger, setLevel, removeAllAppenders, addAppender and log as specified
 *     inside this document are supported.</li>
 *     <li>Only one Appender (FileAppender or DailyRollingFileAppender) per Logger is supported.</li>
 * </ul>
 */
public class Logger {
    private java.util.logging.Logger Me;
    net.bplaced.conrad.log4jpos.Level MyLevel = net.bplaced.conrad.log4jpos.Level.OFF;
    net.bplaced.conrad.log4jpos.Appender MyAppender = null;
    boolean RenewAppender = false;

    private Logger(String name) {
        (Me = java.util.logging.Logger.getLogger(name)).setUseParentHandlers(false);
    }

    @Override
    public void finalize() {
        removeAllAppenders();
    }

    /**
     * Remove and close all FileHandler objects bound to the Logger.
     */
    synchronized public void removeAllAppenders() {
        Handler[] handlers = Me.getHandlers();
        for (Handler handler : handlers) {
            Me.removeHandler(handler);
            handler.close();
        }
        releaseFileHandler(MyAppender.MyHandler);
        MyAppender = null;
    }

    /**
     * Adds appender to the logger. Only one Appender is allowed for a Logger.
     * @param appender  Appender to be used by the logger.
     */
    synchronized public void addAppender(Appender appender) {
        appender.MyHandler.setLevel(MyLevel.LoggingLevel);
        appender.MyHandler.setFormatter(appender.MyLayout.MyFormatter);
        Me.setLevel(MyLevel.LoggingLevel);
        appender.MyLayout.setLogger(this);
        Me.addHandler(appender.MyHandler);
        MyAppender = appender;
    }

    /**
     * Logger class factory.
     * @param name  Logger name.
     * @return      The logger object to be used for later loggin requests.
     */
    public static Logger getLogger(String name) {
        return new Logger(name);
    }

    /**
     * Sets logging level of the logger.
     * @param level One of the predefined Level constants.
     */
    synchronized public void setLevel(Level level) {
        MyLevel = level;
        Me.setLevel(level.LoggingLevel);
        if (MyAppender != null)
            MyAppender.MyHandler.setLevel(level.LoggingLevel);
    }

    /**
     * Writes a logging message to the log file if the given level is not below the level of the logger.
     * @param level     One of the predefined Level constants.
     * @param message   Message to be written.
     * @throws NullPointerException If no Appender has been added to the logger previously.
     */
    synchronized public void log(Level level, String message) {
        if (!level.equals(Level.OFF)) {
            synchronized (MyAppender.MyHandler) {
                Me.log(level.LoggingLevel, message);
            }
            while (RenewAppender) {
                RenewAppender = false;
                try {
                    Me.removeHandler(MyAppender.MyHandler);
                    MyAppender.MyHandler.close();
                    releaseFileHandler(MyAppender.MyHandler);
                    Appender newone = new DailyRollingFileAppender(MyAppender.MyLayout, MyAppender.MyPath, MyAppender.MyDateFormat.toPattern());
                    MyAppender = null;
                    addAppender(newone);
                } catch (IOException e) {
                    // IO error: let it "as is", clear pattern to enter not-rolling FileAppender
                    e.printStackTrace();
                    MyAppender.MyDateFormat = null;
                }
                synchronized (MyAppender.MyHandler) {
                    Me.log(level.LoggingLevel, message);
                }
            }
        }
    }

    /**
     * Checks whether the log file must be changed for the current log data. It depends on the contents
     * of the log file pattern and the time stamp of the last logging message or the logging start.<br>
     * This method will be used by the format message of formatter derived from SimpleFormatter to verify
     * whether a message must be logged.
     *
     * @param millis    Time stamp of log data.
     * @return          True if a log file change is not necessary.
     */
    boolean checkPattern(long millis) {
        if (MyAppender.MyDateFormat != null) {
            String newpostfix = MyAppender.MyDateFormat.format(new Date(millis));
            if (!MyAppender.MyCurrentPostfix.equals(newpostfix)) {
                return !(RenewAppender = true);
            }
        }
        return true;
    }
}
