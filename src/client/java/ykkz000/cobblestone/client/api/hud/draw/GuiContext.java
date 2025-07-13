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

package ykkz000.cobblestone.client.api.hud.draw;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * Interface for GUI context. The API will not change with Minecraft version.
 *
 * @author ykkz000
 * @apiNote Please do not implement this interface by yourself. If you need to use the implementation, use or extend {@link ykkz000.cobblestone.client.impl.hud.GuiContextImpl}.
 * @see ykkz000.cobblestone.client.impl.hud.GuiContextImpl
 */
@Environment(EnvType.CLIENT)
public interface GuiContext {
    void pushMatrix();
    void translate(double x, double y);
    void popMatrix();
}
