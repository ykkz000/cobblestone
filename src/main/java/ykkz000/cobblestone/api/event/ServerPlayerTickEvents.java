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
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Contains events that are triggered on the server player every tick.
 *
 * @author ykkz000
 */
public final class ServerPlayerTickEvents {
    private ServerPlayerTickEvents() {
    }

    /**
     * Called at the start of the server player tick.
     */
    public static final Event<ServerPlayerStartTick> START_SERVER_PLAYER_TICK = EventFactory.createArrayBacked(ServerPlayerStartTick.class,
            (listeners) -> (player) -> {
                for (ServerPlayerStartTick listener : listeners) {
                    listener.onStartTick(player);
                }
            });

    /**
     * Called at the end of the server player tick.
     */
    public static final Event<ServerPlayerEndTick> END_SERVER_PLAYER_TICK = EventFactory.createArrayBacked(ServerPlayerEndTick.class,
            (listeners) -> (player) -> {
                for (ServerPlayerEndTick listener : listeners) {
                    listener.onEndTick(player);
                }
            });

    @FunctionalInterface
    public interface ServerPlayerStartTick {
        /**
         * Called when the server player starts ticking.
         *
         * @param player Server player
         */
        void onStartTick(ServerPlayerEntity player);
    }

    @FunctionalInterface
    public interface ServerPlayerEndTick {
        /**
         * Called when the server player ends ticking.
         *
         * @param player Server player
         */
        void onEndTick(ServerPlayerEntity player);
    }
}
