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

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ykkz000.cobblestone.api.event.EntityTickEvents;

/**
 * Mixin for Entity. Allows developers to add more events to the entity.
 *
 * @author ykkz000
 */
@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "tick()V", at = @At("HEAD"))
    private void startTick(CallbackInfo ci) {
        EntityTickEvents.START_ENTITY_TICK.invoker().onStartTick((Entity) (Object) this);
    }

    @Inject(method = "tick()V", at = @At("RETURN"))
    private void endTick(CallbackInfo ci) {
        EntityTickEvents.END_ENTITY_TICK.invoker().onEndTick((Entity) (Object) this);
    }
}
