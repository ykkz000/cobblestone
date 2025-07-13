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

package ykkz000.cobblestone.client.api.hud.element;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import ykkz000.cobblestone.client.api.hud.draw.DrawUtils;
import ykkz000.cobblestone.client.api.hud.draw.GuiContext;
import ykkz000.cobblestone.client.api.hud.layout.Position;
import ykkz000.cobblestone.client.api.hud.layout.Size;

/**
 * Text element.
 *
 * @author ykkz000
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Environment(EnvType.CLIENT)
public class TextElement extends BaseElement {
    /**
     * Text of the element. Default is empty string.
     */
    @NonNull
    protected String text;
    /**
     * Color of the text. Default is 0xFFFFFFFF.
     */
    protected int color;
    /**
     * Whether to draw the shadow. Default is false.
     */
    protected boolean shadow;

    /**
     * Get a builder.
     *
     * @return Builder
     */
    public static BaseElement.Builder<TextElement> builder() {
        return TextElement::new;
    }

    protected TextElement(Identifier id) {
        super(id);
        this.text = "";
        this.color = 0xFFFFFFFF;
        this.shadow = false;
    }

    @Override
    public void render(GuiContext context) {
        DrawUtils.drawText(context, text, Position.DEFAULT_POSITION, color, shadow);
    }

    /**
     * Adjust the height to the perfect height.
     */
    public void adjustToPerfectHeight() {
        size = new Size(size.width(), DrawUtils.fontHeight());
    }

    /**
     * Adjust the width to the perfect width.
     */
    public void adjustToPerfectWidth() {
        size = new Size(DrawUtils.stringWidth(text), size.height());
    }

    /**
     * Adjust the size to the perfect size.
     */
    public void adjustToPerfectSize() {
        size = new Size(DrawUtils.stringWidth(text), DrawUtils.fontHeight());
    }
}
