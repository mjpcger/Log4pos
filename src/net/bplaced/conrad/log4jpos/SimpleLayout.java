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

/**
 * Replacement class for log4j SimpleLayout, based on java.util.logging.
 * Stores standard format string for logging output to be written to a log file.
 */
public class SimpleLayout extends Layout {
    /**
     * Creates a standard format for logging. Standard format is of the form<br>
     * YYYY-MM-dd kk:mm:ss,SSS <i>LogLevel</i> [<i>ThreadName</i>]: <i>LoggerName</i>: <i>Message</i><br>
     */
    public SimpleLayout() {
        MyFormat = "%1$s %2$-5s [%3$s]: %4$s: %5$s\r\n";
        MyDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss,SSS");
    }
}
