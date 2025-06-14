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

package ykkz000.cobblestone.api.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.minecraft.util.Identifier;

/**
 * Interface for classes that can provide extended data.
 *
 * @author ykkz000
 */
public interface ExtraDataProvider {
    /**
     * Get extra data.
     *
     * @param key   Identifier of the data
     * @param clazz Class of the data
     * @param <T>   Type of the data
     * @return The data
     * @throws JsonProcessingException if the data cannot be parsed
     */
    <T> T getExtraData(Identifier key, Class<T> clazz) throws JsonProcessingException;

    /**
     * Set extra data.
     *
     * @param key  Identifier of the data
     * @param data Data
     * @param <T>  Type of the data
     */
    <T> void setExtraData(Identifier key, T data);
}
