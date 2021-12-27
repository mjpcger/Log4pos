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
 * For testing only
 */
public class Main {
    public static void main(String[] args) {
        Logger log = Logger.getLogger("Main");
        log.setLevel(Level.DEBUG);
        try {
            Layout layout = new PatternLayout("%d{HH:mm:ss,SSS} %-5p [%t]: %Y: %m%n");
            log.addAppender(new DailyRollingFileAppender(layout, "logfile.log", ".yyyyMMdd-kkmm"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.log(Level.INFO, "Info-Nachricht");
        log.log(Level.DEBUG, "Debug-Nachricht");
        log.log(Level.TRACE, "Trace-Nachricht");
    }
}
