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

package ykkz000.cobblestone.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;

/**
 * Contains events that are triggered on the entity every tick.
 *
 * @author ykkz000
 */
public final class EntityTickEvents {
    private EntityTickEvents() {
    }

    /**
     * Called at the start of the entity tick.
     */
    public static final Event<EntityStartTick> START_ENTITY_TICK = EventFactory.createArrayBacked(EntityStartTick.class,
            (listeners) -> (entity) -> {
                for (EntityStartTick listener : listeners) {
                    listener.onStartTick(entity);
                }
            });
    /**
     * Called at the end of the entity tick.
     */
    public static final Event<EntityEndTick> END_ENTITY_TICK = EventFactory.createArrayBacked(EntityEndTick.class,
            (listeners) -> (entity) -> {
                for (EntityEndTick listener : listeners) {
                    listener.onEndTick(entity);
                }
            });


    @FunctionalInterface
    public interface EntityStartTick {
        /**
         * Called when the entity starts ticking.
         *
         * @param entity Entity
         */
        void onStartTick(Entity entity);
    }

    @FunctionalInterface
    public interface EntityEndTick {
        /**
         * Called when the entity ends ticking.
         *
         * @param entity Entity
         */
        void onEndTick(Entity entity);
    }
}
