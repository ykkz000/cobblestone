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

package ykkz000.cobblestone.client.mixin.core;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.ClientBootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ykkz000.cobblestone.api.core.annotation.AutoBootstrap;
import ykkz000.cobblestone.impl.core.ASMHelper;
import ykkz000.cobblestone.impl.core.ClassScanner;
import ykkz000.cobblestone.impl.core.CobblestoneBootstrap;

/**
 * Mixin for ClientBootstrap. This mixin is used to auto bootstrap mods.
 *
 * @author ykkz000
 */
@Mixin(ClientBootstrap.class)
@Environment(EnvType.CLIENT)
public abstract class ClientBootstrapMixin {
    @Shadow
    private static volatile boolean initialized;

    @Inject(method = "initialize()V", at = @At("HEAD"))
    private static void initialize(CallbackInfo ci) {
        if (initialized) {
            return;
        }
        ClassScanner scanner = new ClassScanner();
        CobblestoneBootstrap.MOD_MAIN_CLASSES.forEach(mainClass -> {
            try {
                scanner.scan(mainClass.getPackage().getName(), mainClass, AutoBootstrap.class,
                        classNode -> ASMHelper.checkAnnotationValue(classNode, Environment.class, "value", EnvType.CLIENT.name()), Class::forName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
