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
import java.util.logging.FileHandler;

/**
 * Base class for FileAppender and DailyRollingFileAppender, based on java.util.logging.
 */
public abstract class Appender {
    FileHandler MyHandler;
    Layout MyLayout;
    SimpleDateFormat MyDateFormat;
    String MyPath;
    String MyCurrentPostfix;

    /**
     * For DateFormat initialization in case of rolling file appenders. Initializes
     * SimpleDateFormat object for file name generation and time check.
     * @param pattern   Pattern as described for class SimpleDateFormat.
     * @throws IllegalArgumentException If pattern is invalid.
     */
    Appender(String pattern) {
        MyDateFormat = pattern != null ? new SimpleDateFormat(pattern) : null;
    }
}
