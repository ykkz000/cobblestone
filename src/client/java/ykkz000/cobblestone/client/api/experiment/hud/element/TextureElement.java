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

package ykkz000.cobblestone.client.api.experiment.hud.element;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import ykkz000.cobblestone.client.api.experiment.hud.draw.DrawUtils;
import ykkz000.cobblestone.client.api.experiment.hud.draw.GuiContext;
import ykkz000.cobblestone.client.api.experiment.hud.layout.Position;

/**
 * Texture element. This element will draw a texture.
 *
 * @author ykkz000
 * @apiNote Texture size should be the same as the size of the element. And also uv will be (0, 0).
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Environment(EnvType.CLIENT)
public class TextureElement extends BaseElement {
    /**
     * Texture id. Default is null. If null, nothing will be drawn.
     */
    private Identifier texture;

    /**
     * Get a builder.
     *
     * @return Builder
     */
    public static BaseElement.Builder<TextureElement> builder() {
        return TextureElement::new;
    }

    protected TextureElement(Identifier id) {
        super(id);
    }

    @Override
    public void render(GuiContext context) {
        if (texture == null) {
            return;
        }
        DrawUtils.drawTexture(context, texture, Position.DEFAULT_POSITION, size);
    }
}
