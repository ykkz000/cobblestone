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

package ykkz000.cobblestone.mixin.core;

import net.fabricmc.api.Environment;
import net.minecraft.Bootstrap;
import org.objectweb.asm.Type;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ykkz000.cobblestone.api.core.annotation.AutoBootstrap;
import ykkz000.cobblestone.impl.core.ClassScanner;
import ykkz000.cobblestone.impl.core.CobblestoneBootstrap;

@Mixin(Bootstrap.class)
public abstract class BootstrapMixin {
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
                scanner.scan(mainClass.getPackage().getName(), mainClass, AutoBootstrap.class, classNode -> classNode.visibleAnnotations.stream()
                        .filter(annotationNode -> annotationNode.desc.equals(Type.getType(Environment.class).getDescriptor()))
                        .findAny().isEmpty(), Class::forName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
