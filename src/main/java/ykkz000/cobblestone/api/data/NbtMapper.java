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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.minecraft.nbt.NbtCompound;
import ykkz000.cobblestone.impl.data.NbtJsonConverter;

/**
 * Mapper between NBT and Object. Use JSON as the intermediate format.
 *
 * @author ykkz000
 * @apiNote No support for BYTE_ARRAY, INT_ARRAY and LONG_ARRAY in NBT. No support for BIG_INTEGER and BIG_DECIMAL in JSON.
 */
public final class NbtMapper {
    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    /**
     * Read an object from NBT.
     *
     * @param nbt   NBT
     * @param clazz Class of the object
     * @param <T>   Type of the object
     * @return The object
     * @throws JsonProcessingException if the object cannot be read
     */
    public static <T> T readFromNbt(NbtCompound nbt, Class<T> clazz) throws JsonProcessingException {
        return readFromNbt(nbt, clazz, DEFAULT_OBJECT_MAPPER);
    }

    /**
     * Read an object from NBT.
     *
     * @param nbt          NBT
     * @param clazz        Class of the object
     * @param objectMapper Object mapper
     * @param <T>          Type of the object
     * @return The object
     * @throws JsonProcessingException if the object cannot be read
     */
    public static <T> T readFromNbt(NbtCompound nbt, Class<T> clazz, ObjectMapper objectMapper) throws JsonProcessingException {
        ObjectNode objectNode = (ObjectNode) NbtJsonConverter.nbt2json(nbt);
        return objectMapper.treeToValue(objectNode, clazz);
    }

    /**
     * Write an object to NBT.
     *
     * @param object Object
     * @return NBT
     */
    public static NbtCompound writeToNbt(Object object) {
        return writeToNbt(object, DEFAULT_OBJECT_MAPPER);
    }

    /**
     * Write an object to NBT.
     *
     * @param object       Object
     * @param objectMapper Object mapper
     * @return NBT
     */
    public static NbtCompound writeToNbt(Object object, ObjectMapper objectMapper) {
        return (NbtCompound) NbtJsonConverter.json2nbt(objectMapper.valueToTree(object));
    }
}
