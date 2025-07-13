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

import lombok.AllArgsConstructor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.ClientBootstrap;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ykkz000.cobblestone.api.core.annotation.AutoBootstrap;
import ykkz000.cobblestone.client.impl.core.CobblestoneClientBootstrap;
import ykkz000.cobblestone.impl.core.ClassScanner;

import java.util.Optional;

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
        CobblestoneClientBootstrap.MOD_CLIENT_MAIN_CLASSES.forEach(mainClass -> {
            try {
                scanner.scan(mainClass.getPackage().getName(), mainClass, AutoBootstrap.class, classNode -> {
                    Optional<AnnotationNode> environmentNodeOptional = classNode.visibleAnnotations.stream()
                            .filter(annotationNode -> annotationNode.desc.equals(Type.getType(Environment.class).getDescriptor()))
                            .findAny();
                    if (environmentNodeOptional.isEmpty()) {
                        return false;
                    }
                    AnnotationNode environmentNode = environmentNodeOptional.get();
                    @AllArgsConstructor
                    class LambdaBoolean {
                        boolean value;
                    }
                    LambdaBoolean shouldBootstrap = new LambdaBoolean(false);
                    environmentNode.accept(new AnnotationVisitor(Opcodes.ASM9) {
                        @Override
                        public void visitEnum(String name, String descriptor, String value) {
                            if ("value".equals(name) && EnvType.CLIENT.name().equals(value)) {
                                shouldBootstrap.value = true;
                            }
                            super.visitEnum(name, descriptor, value);
                        }
                    });
                    return shouldBootstrap.value;
                }, Class::forName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
