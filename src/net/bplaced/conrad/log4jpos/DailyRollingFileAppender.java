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
import java.util.logging.FileHandler;

/**
 * Replacement class for log4j DailyRollingFileAppender, based on java.util.logging.
 */
public class DailyRollingFileAppender extends Appender {
    /**
     * Creates a rolling file appender. Logging date/time string, formatted via pattern, will be
     * appended to the path to build the log file name. If the log file name changes, a new FileHandler
     * object will be created for logging via java.util.logging.
     * @param layout        Layout to be used. See Layout for details.
     * @param path          A file path. Keep in mind not to use pattern as described in FileHandler
     *                      documentation.
     * @param pattern       Pattern as described for class SimpleDateFormat.
     * @throws IOException  If FileHandler cannot be created.
     * @throws NullPointerException If pattern is null.
     * @throws IllegalArgumentException If pattern is invalid.
     */
    public DailyRollingFileAppender(Layout layout, String path, String pattern) throws IOException {
        super(pattern);
        MyPath = path;
        MyCurrentPostfix = MyDateFormat.format(new Date());
        MyHandler = getFileHandler(path + MyCurrentPostfix);
        MyLayout = layout;
    }
}
