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
 * Replacement class for log4j PatternLayout, based on java.util.logging.
 * Stores configured format string for logging output to be written to a log file.
 */
public class PatternLayout extends Layout {
    /**
     * Specifies logging layout. Pattern will be specified as described for log4j with
     * the following restrictions:
     * <ul>
     *     <li>Only allowed modifiers have the format [-]n, where n is a maximum three-digit number,</li>
     *     <li>Only allowed conversions are d [date format], m [message], n [line separator], p [priority] and t [thread name],</li>
     *     <li>Only one date format specifier allowed,</li>
     *     <li>Format specifier (sequence following the conversion character, enclosed with {}) only allowed for date conversion.</li>
     * </ul>
     * In addition to the log4j conversions, %[<i>midifyer</i>]Y can be used to log the logger name-
     *
     * @param pattern   Pattern string, for example "%d{HH:mm:ss,SSS} %-5p [%t]: %Y: %m%n".
     */
    public PatternLayout(String pattern) {
        MyFormat = convertLog4j2JavaUtilLoggingLoggingFormat(pattern);
    }

    private String convertLog4j2JavaUtilLoggingLoggingFormat(String pattern) {
        String result = "";
        String remainder = pattern;
        MyDateFormat = null;
        for (int i = remainder.indexOf('%'); i >= 0; i = remainder.indexOf('%')) {
            result += remainder.substring(0, i + 1);
            remainder = remainder.substring(i + 1);
            if (remainder.length() < 1)
                break;
            switch (remainder.charAt(0)) {
                case '%':       // %% remains unchanged
                    result += "%";
                    remainder = remainder.substring(1);
                    break;
                default: {      // %[[-]n]{dmnpt}...
                    boolean minus;

                    if ((minus = remainder.charAt(0) == '-') && (remainder.length() < 3 || "0123456789".indexOf(remainder.charAt(1)) < 0))
                        throw new IllegalArgumentException("Invalid pattern: " + pattern);
                    // extract the modifier [[-]n]
                    String modifier = "";

                    for (int j = minus ? 1 : 0; j < remainder.length() - 1 && j < (minus ? 4 : 3); j++) {
                        if ("0123456789".indexOf(remainder.charAt(j)) < 0) {
                            modifier = remainder.substring(0, j);
                            remainder = remainder.substring(j);
                            break;
                        }
                    }
                    switch (remainder.charAt(0)) {
                        case 'd':   // Date/time, optionally followed by {pattern}, must not be specified more than once.
                        {
                            if (MyDateFormat != null)
                                throw new IllegalArgumentException("Invalid pattern: Multiple %d specifications: " + pattern);
                            if (remainder.length() > 1 && remainder.charAt(1) == '{') {
                                int j = remainder.indexOf('}');
                                if (j < 0)
                                    throw new IllegalArgumentException("Illegal date/time pattern specification: " + pattern);
                                MyDateFormat = new SimpleDateFormat(remainder.substring(2, j));
                                remainder = remainder.substring(j + 1);
                            }
                            else { // The default is as in SimpleLayout
                                MyDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss,SSS");
                                remainder = remainder.substring(1);
                            }
                            result += "1$" + modifier + "s";
                            break;
                        }
                        default: {
                            int index = "ptmYn".indexOf(remainder.charAt(0)) + 1;
                            if (index < 1)
                                throw new IllegalArgumentException("Illegal data type in pattern: " + pattern);
                            result += "023456".substring(index, index + 1) + "$s";
                            remainder = remainder.substring(1);
                        }
                    }
                }
            }
        }
        return result + remainder;
    }
}
