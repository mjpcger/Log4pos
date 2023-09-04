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

/**
 * Replacement class for log4j Level, based on java.util.logging.
 */
public class Level {
    private static class MyLevel extends java.util.logging.Level {
        private MyLevel(String name, int level) {
            super(name, level);
        }
    }

    /**
     * Level constant to switch off logging.
     */
    public static final Level OFF = new Level(java.util.logging.Level.OFF);

    /**
     * Level constant to allow logging of fatal error log messages.
     */
    public static final Level FATAL = new Level(new MyLevel("FATAL", java.util.logging.Level.SEVERE.intValue()));

    /**
     * Level constant to allow logging of error log messages.
     */
    public static final Level ERROR = new Level(new MyLevel("ERROR", java.util.logging.Level.WARNING.intValue()));

    /**
     * Level constant to allow logging of fatal error and warning log messages.
     */
    public static final Level WARN = new Level(new MyLevel("WARN", java.util.logging.Level.INFO.intValue()));

    /**
     * Level constant to allow logging of fatal error, warning and informational log messages.
     */
    public static final Level INFO = new Level(new MyLevel("INFO", java.util.logging.Level.CONFIG.intValue()));

    /**
     * Level constant to allow logging of fatal error, warning, informational and debug log messages.
     */
    public static final Level DEBUG = new Level(new MyLevel("DEBUG", java.util.logging.Level.FINE.intValue()));

    /**
     * Level constant to allow logging of fatal error, warning, informational, debug and trace log messages.
     */
    public static final Level TRACE = new Level(new MyLevel("TRACE", java.util.logging.Level.FINER.intValue()));

    /**
     * Level constant to allow logging of all log messaged.
     */
    public static final Level ALL = new Level(java.util.logging.Level.ALL);

    /**
     * Logging level for Logger. One of the predefined values.
     */
    java.util.logging.Level LoggingLevel;

    @Override
    public String toString() {
        return LoggingLevel.toString();
    }

    private Level(java.util.logging.Level value) {
        LoggingLevel = value;
    }
}
