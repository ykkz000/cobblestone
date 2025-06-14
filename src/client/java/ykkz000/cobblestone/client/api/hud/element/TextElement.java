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
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import ykkz000.cobblestone.client.api.hud.layout.Size;

/**
 * Text element.
 *
 * @author ykkz000
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TextElement extends BaseElement {
    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
    protected String text = "";
    protected int color = 0xFFFFFF;
    protected boolean shadow = false;

    public static BaseElement.Builder<TextElement> builder() {
        return TextElement::new;
    }

    protected TextElement(Identifier id) {
        super(id);
    }

    @Override
    public void render(DrawContext context) {
        context.drawText(CLIENT.textRenderer, text, position.getX(), position.getY(), color, shadow);
    }

    public void adjustToPerfectHeight() {
        size = new Size(size.getWidth(), CLIENT.textRenderer.fontHeight);
    }

    public void adjustToPerfectWidth() {
        size = new Size(CLIENT.textRenderer.getWidth(text), size.getHeight());
    }

    public void adjustToPerfectSize() {
        size = new Size(CLIENT.textRenderer.getWidth(text), CLIENT.textRenderer.fontHeight);
    }
}
