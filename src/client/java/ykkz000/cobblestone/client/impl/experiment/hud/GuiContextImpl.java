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

package ykkz000.cobblestone.client.impl.experiment.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import ykkz000.cobblestone.client.api.experiment.hud.draw.GuiContext;

/**
 * GUI context implementation.
 *
 * @param drawContext Draw context
 * @author ykkz000
 */
@Environment(EnvType.CLIENT)
public record GuiContextImpl(DrawContext drawContext) implements GuiContext {
    @Override
    public void pushMatrix() {
        drawContext.getMatrices().push();
    }

    @Override
    public void translate(double x, double y) {
        drawContext.getMatrices().translate(x, y, 0);
    }

    @Override
    public void popMatrix() {
        drawContext.getMatrices().pop();
    }
}
