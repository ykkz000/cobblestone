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

package ykkz000.cobblestone.mixin.event;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ykkz000.cobblestone.api.event.ServerPlayerTickEvents;

/**
 * Mixin for ServerPlayerEntity. Allows developers to add more events to the server player entity.
 *
 * @author ykkz000
 */
@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "tick()V", at = @At("HEAD"))
    private void startTick(CallbackInfo ci) {
        ServerPlayerTickEvents.START_SERVER_PLAYER_TICK.invoker().onStartTick((ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "tick()V", at = @At("RETURN"))
    private void endTick(CallbackInfo ci) {
        ServerPlayerTickEvents.END_SERVER_PLAYER_TICK.invoker().onEndTick((ServerPlayerEntity) (Object) this);
    }
}
