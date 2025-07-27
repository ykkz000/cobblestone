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

package ykkz000.cobblestone.api.core.annotation;

import ykkz000.cobblestone.api.core.ConfigurationPreloader;
import ykkz000.cobblestone.api.core.ModBoot;

import java.lang.annotation.*;

/**
 * Annotation for classes that should be automatically loaded as configuration.
 *
 * @author ykkz000
 * @apiNote No need to specify the side.
 * @apiNote The time to load the class is when the {@link ModBoot#start()}. If you want to load the configuration earlier, you can use this annotation and call the method in the class{@link ConfigurationPreloader}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Configuration {
    /**
     * The paths of the configuration file. Must be json.
     *
     * @return The path (If the path starts with "classpath:", it will be loaded from the classpath, otherwise it will be loaded from the running directory)
     * @apiNote The priority of the path is from first to last.
     */
    String[] path();
}
