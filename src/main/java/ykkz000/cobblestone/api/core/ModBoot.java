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

package ykkz000.cobblestone.api.core;

import lombok.SneakyThrows;
import net.fabricmc.api.*;
import ykkz000.cobblestone.api.core.annotation.AutoBootstrap;
import ykkz000.cobblestone.api.core.annotation.Configuration;
import ykkz000.cobblestone.impl.core.ASMHelper;
import ykkz000.cobblestone.impl.core.ClassScanner;
import ykkz000.cobblestone.impl.core.ConfigurationFactory;

/**
 * The bootstrap class for mods. You may only need to add this code to your mod's main class on every side:
 * <code>
 * <pre>
 *     public void onInitialize() {
 *         new ModBoot(ThisMain.class).start();
 *     }
 *
 *     public void onInitializeClient() {
 *        new ModBoot(ThisClientMain.class).start();
 *     }
 *
 *     public void onInitializeServer() {
 *         new ModBoot(ThisDedicatedServerMain.class).start();
 *     }
 * </pre>
 * </code>
 *
 * @author ykkz000
 */
public class ModBoot {
    private final ClassScanner classScanner;
    private final ConfigurationFactory configurationFactory;
    private final Class<?> mainClass;
    private final EnvType envType;

    public ModBoot(Class<?> mainClass) {
        this.classScanner = new ClassScanner();
        this.configurationFactory = new ConfigurationFactory();
        this.mainClass = mainClass;
        if (ModInitializer.class.isAssignableFrom(mainClass)) {
            this.envType = null;
        } else if (ClientModInitializer.class.isAssignableFrom(mainClass)) {
            this.envType = EnvType.CLIENT;
        } else if (DedicatedServerModInitializer.class.isAssignableFrom(mainClass)) {
            this.envType = EnvType.SERVER;
        } else {
            throw new IllegalArgumentException("The main class must implement ModInitializer, ClientModInitializer or DedicatedServerModInitializer");
        }
    }

    /**
     * Start the mod.
     */
    @SneakyThrows
    public void start() {
        classScanner.scan(this.mainClass.getPackage().getName(), this.mainClass, Configuration.class,
                null, configurationFactory::process);
        classScanner.scan(this.mainClass.getPackage().getName(), this.mainClass, AutoBootstrap.class,
                classNode -> switch (this.envType) {
                    case null -> !ASMHelper.checkAnnotation(classNode, Environment.class);
                    case CLIENT, SERVER ->
                            ASMHelper.checkAnnotationValue(classNode, Environment.class, "value", this.envType.name());
                }, Class::forName);
    }
}
