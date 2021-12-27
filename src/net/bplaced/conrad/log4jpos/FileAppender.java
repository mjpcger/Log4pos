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
import java.util.logging.FileHandler;

/**
 * Replacement class for log4j FileAppender, based on java.util.logging.
 */
public class FileAppender extends Appender {
    /**
     * Creates a normal file appender. Uses FileHandler object for logging via java.util.logging.
     * @param layout        Layout to be used. See Layout for details.
     * @param path          A file path.
     * @throws IOException  If FileHandler cannot be created.
     */
    public FileAppender(Layout layout, String path) throws IOException {
        super(null);
        MyHandler = getFileHandler(path);
        MyLayout = layout;
        MyPath = path;
    }
}
