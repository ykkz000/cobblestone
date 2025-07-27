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

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ykkz000.cobblestone.api.core.annotation.Configuration;
import ykkz000.cobblestone.api.core.annotation.ConfigurationInstance;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * Configuration factory.
 *
 * @author ykkz000
 */
public class ConfigurationFactory {
    private final ObjectMapper objectMapper;

    public ConfigurationFactory() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Process a configuration class.
     * @param className Class name
     */
    @SneakyThrows
    public void process(String className) {
        Class<?> clazz = Class.forName(className);
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ConfigurationInstance.class))
                .toList();
        if (fields.size() != 1) {
            throw new IllegalArgumentException("The class " + clazz.getName() + " has " + fields.size() + " fields with @ConfigurationInstance");
        }
        Field field = fields.getFirst();
        if (!Modifier.isStatic(field.getModifiers())) {
            throw new IllegalArgumentException("The field " + field.getName() + " in " + clazz.getName() + " is not static");
        }
        field.setAccessible(true);
        if (field.get(null) != null) {
            return; // Ignore if the field is already filled.
        }
        List<String> paths = Arrays.stream(clazz.getAnnotation(Configuration.class).path()).toList();
        InputStream inputStream = paths.stream()
                .filter(path -> FileHelper.checkPath(clazz, path))
                .findFirst()
                .map(path -> FileHelper.getInputStreamFromPath(clazz, path))
                .orElseThrow(() -> new FileNotFoundException("Cannot find the configuration file for " + clazz.getName()));
        Object instance = this.objectMapper.readValue(inputStream, clazz);
        field.set(null, instance);
    }
}
