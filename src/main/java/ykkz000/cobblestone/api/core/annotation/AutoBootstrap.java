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

import java.lang.annotation.*;

/**
 * Annotation for classes that should be automatically bootstrapped.
 * <p>You may use this annotation on those classes that define Items, Blocks, Entities, etc.</p>
 *
 * @author ykkz000
 * @apiNote You can use this annotation on any side. And if your class only be used on one side, you can use {@link net.fabricmc.api.Environment} to specify the side.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Documented
public @interface AutoBootstrap {
}
