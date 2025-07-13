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

package ykkz000.cobblestone.client.api.core;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ykkz000.cobblestone.client.impl.core.CobblestoneClientBootstrap;

/**
 * The bootstrap class for mods. You may only need to add this code to your mod's main class for client side:
 * <code>
 * <pre>
 *     public void onInitialize() {
 *         new ClientModBoot(Your.class).start();
 *     }
 * </pre>
 * </code>
 *
 * @author ykkz000
 */
@Environment(EnvType.CLIENT)
public class ClientModBoot {
    private final Class<?> mainClass;

    public ClientModBoot(Class<?> mainClass) {
        this.mainClass = mainClass;
    }

    /**
     * Start the mod.
     */
    public void start() {
        CobblestoneClientBootstrap.MOD_CLIENT_MAIN_CLASSES.add(mainClass);
    }
}
