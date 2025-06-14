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

package ykkz000.cobblestone.impl.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.minecraft.nbt.*;

import java.util.Iterator;

/**
 * Converter between NBT and JSON.
 *
 * @author ykkz000
 * @apiNote No support for BYTE_ARRAY, INT_ARRAY and LONG_ARRAY in NBT. No support for BIG_INTEGER and BIG_DECIMAL in JSON.
 */
public class NbtJsonConverter {
    private static final JsonNodeFactory NODE_FACTORY = JsonNodeFactory.instance;

    /**
     * Convert NBT to JSON.
     *
     * @param nbt NBT
     * @return JSON
     */
    public static JsonNode nbt2json(NbtElement nbt) {
        return switch (nbt.getType()) {
            case NbtElement.END_TYPE -> null;
            case NbtElement.BYTE_TYPE -> NODE_FACTORY.numberNode(((NbtByte) nbt).byteValue());
            case NbtElement.SHORT_TYPE -> NODE_FACTORY.numberNode(((NbtShort) nbt).shortValue());
            case NbtElement.INT_TYPE -> NODE_FACTORY.numberNode(((NbtInt) nbt).intValue());
            case NbtElement.LONG_TYPE -> NODE_FACTORY.numberNode(((NbtLong) nbt).longValue());
            case NbtElement.FLOAT_TYPE -> NODE_FACTORY.numberNode(((NbtFloat) nbt).floatValue());
            case NbtElement.DOUBLE_TYPE -> NODE_FACTORY.numberNode(((NbtDouble) nbt).doubleValue());
            case NbtElement.STRING_TYPE -> NODE_FACTORY.textNode(nbt.asString());
            case NbtElement.LIST_TYPE -> {
                NbtList nbtList = (NbtList) nbt;
                ArrayNode arrayNode = NODE_FACTORY.arrayNode();
                for (NbtElement element : nbtList) {
                    arrayNode.add(nbt2json(element));
                }
                yield arrayNode;
            }
            case NbtElement.COMPOUND_TYPE -> {
                NbtCompound nbtCompound = (NbtCompound) nbt;
                ObjectNode objectNode = NODE_FACTORY.objectNode();
                for (String key : nbtCompound.getKeys()) {
                    NbtElement nbtElement = nbtCompound.get(key);
                    if (nbtElement == null || nbtElement.getType() == NbtElement.END_TYPE) {
                        continue;
                    }
                    objectNode.set(key, nbt2json(nbtElement));
                }
                yield objectNode;
            }
            default -> throw new UnsupportedOperationException("Unsupported NBT type: " + nbt.getType());
        };
    }

    /**
     * Convert JSON to NBT.
     *
     * @param json JSON
     * @return NBT
     */
    public static NbtElement json2nbt(JsonNode json) {
        return switch (json.getNodeType()) {
            case NULL -> null;
            case BOOLEAN -> NbtByte.of((byte) (json.asBoolean() ? 1 : 0));
            case NUMBER -> switch (json.numberType()) {
                case INT -> NbtInt.of(json.intValue());
                case LONG -> NbtLong.of(json.longValue());
                case FLOAT -> NbtFloat.of(json.floatValue());
                case DOUBLE -> NbtDouble.of(json.doubleValue());
                default -> throw new UnsupportedOperationException("Unsupported number type: " + json.numberType());
            };
            case STRING -> NbtString.of(json.asText());
            case ARRAY -> {
                NbtList nbtList = new NbtList();
                for (JsonNode element : json) {
                    nbtList.add(json2nbt(element));
                }
                yield nbtList;
            }
            case OBJECT -> {
                NbtCompound nbtCompound = new NbtCompound();
                for (Iterator<String> it = json.fieldNames(); it.hasNext(); ) {
                    String key = it.next();
                    JsonNode value = json.get(key);
                    if (value == null) {
                        continue;
                    }
                    nbtCompound.put(key, json2nbt(value));
                }
                yield nbtCompound;
            }
            default -> throw new UnsupportedOperationException("Unsupported JSON type: " + json.getNodeType());
        };
    }
}
