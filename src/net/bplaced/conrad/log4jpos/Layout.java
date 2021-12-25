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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Replacement class for log4j Layout, based on java.util.logging. Base class of PatternLayout
 * and SimpleLayout.
 */
public class Layout {
    /**
     * Extension class for SimpleFormatter to allow log4j-type formatting and file rolling.
     */
    class MyFormatter extends SimpleFormatter {
        /**
         * Message formatter. Uses method checkPattern of its Logger to check if the current
         * record matches the current logfile. If not, returns an empty string. Otherwise the
         * specified format will be used to form the log message written via java.util.logging.
         * @param record    LogRecord, see java.util.logging for details.
         * @return          formatted log string or an empty string.
         */
        @Override
        public String format(LogRecord record) {
            if (MyLogger != null && MyLogger.checkPattern(record.getMillis())) {
                String threadName = Thread.currentThread().getName();
                String msg = String.format(MyFormat, MyDateFormat.format(new Date(record.getMillis())),
                        record.getLevel().getName(), threadName, record.getMessage(), record.getLoggerName(), "\r\n");
                return msg;
            }
            return "";
        }
    }

    /**
     * Internal use method, used to set the logger for file pattern check in format method of MyFormatter.
     * @param logger    Logger to be used for file pattern check.
     */
    void setLogger(Logger logger) {
        MyLogger = logger;
    }

    SimpleFormatter MyFormatter = new MyFormatter();
    String MyFormat = null;
    Logger MyLogger = null;
    SimpleDateFormat MyDateFormat = null;
}
