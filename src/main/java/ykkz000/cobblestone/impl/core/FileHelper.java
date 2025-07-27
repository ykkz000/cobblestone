/*
 * Cobblestone API
 * Copyright (C) 2025  ykkz000
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ykkz000.cobblestone.impl.core;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * File helper.
 *
 * @author ykkz000
 */
public final class FileHelper {
    private static final String CLASSPATH_PREFIX = "classpath:";

    /**
     * Check if a path is valid.
     *
     * @param clazz Class
     * @param path  Path
     * @return True if the path is valid, false otherwise
     */
    public static boolean checkPath(Class<?> clazz, String path) {
        if (path.startsWith(CLASSPATH_PREFIX)) {
            return clazz.getResource(path.substring(CLASSPATH_PREFIX.length())) != null;
        } else {
            File file = new File(path);
            return file.exists() && file.isFile() && file.canRead();
        }
    }

    /**
     * Get an input stream from a path.
     *
     * @param clazz Class
     * @param path  Path
     * @return The input stream
     */
    @SneakyThrows
    public static InputStream getInputStreamFromPath(Class<?> clazz, String path) {
        if (!checkPath(clazz, path)) {
            throw new FileNotFoundException("The path " + path + " is invalid");
        }
        if (path.startsWith(CLASSPATH_PREFIX)) {
            return clazz.getResourceAsStream(path.substring(CLASSPATH_PREFIX.length()));
        } else {
            return new FileInputStream(path);
        }
    }
}
