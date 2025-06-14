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

package ykkz000.cobblestone.api.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

/**
 * Utility methods for entities.
 *
 * @author ykkz000
 */
public final class EntityUtils {
    private EntityUtils() {
    }

    /**
     * Removes an attribute modifier from an entity.
     *
     * @param entity     Living entity
     * @param attribute  Attribute to remove the modifier from
     * @param identifier Identifier of the modifier
     */
    public static void removeAttributeModifier(LivingEntity entity, RegistryEntry<EntityAttribute> attribute, Identifier identifier) {
        EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(attribute);
        if (entityAttributeInstance == null) {
            return;
        }
        entityAttributeInstance.removeModifier(identifier);
    }

    /**
     * Refreshes an attribute modifier on an entity.
     *
     * @param entity     Living entity
     * @param attribute  Attribute to refresh the modifier on
     * @param persistent Permanent modifier if true, temporary modifier if false
     * @param modifier   Attribute modifier
     */
    public static void refreshAttributeModifier(LivingEntity entity, RegistryEntry<EntityAttribute> attribute, boolean persistent, EntityAttributeModifier modifier) {
        EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(attribute);
        if (entityAttributeInstance == null) {
            return;
        }
        entityAttributeInstance.removeModifier(modifier.id());
        if (persistent) {
            entityAttributeInstance.addPersistentModifier(modifier);
        } else {
            entityAttributeInstance.addTemporaryModifier(modifier);
        }
    }
}
