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

package ykkz000.cobblestone.api.core;

import ykkz000.cobblestone.api.core.annotation.Configuration;
import ykkz000.cobblestone.impl.core.ConfigurationFactory;

/**
 * Preload a configuration. You may only need to add this code to the configuration class that you want to preload:
 * <code>
 * <pre>
 *     static {
 *         ConfigurationPreloader.preLoadConfiguration();
 *     }
 * </pre>
 * </code>
 *
 * @author ykkz000
 * @see Configuration
 */
public final class ConfigurationPreloader {
    private static final ConfigurationFactory configurationFactory = new ConfigurationFactory();
    private static final StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * Preload a configuration.
     *
     * @apiNote This method must be called in a class annotated with {@link Configuration}.
     */
    public static void preLoadConfiguration() {
        Class<?> callerClass = stackWalker.getCallerClass();
        if (!callerClass.isAnnotationPresent(Configuration.class)) {
            throw new IllegalArgumentException("The caller class must be annotated with @Configuration");
        }
        configurationFactory.process(stackWalker.getCallerClass().getName());
    }
}
