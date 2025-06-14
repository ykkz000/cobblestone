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

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import ykkz000.cobblestone.client.api.hud.layout.Position;
import ykkz000.cobblestone.client.api.hud.layout.Size;
import ykkz000.cobblestone.client.impl.hud.GuiContextImpl;

/**
 * Utils for drawing. The APIs will not change with Minecraft version.
 *
 * @author ykkz000
 */
public final class DrawUtils {
    private DrawUtils() {
    }

    /**
     * Get the height of the font.
     *
     * @return Height of the font
     */
    public static int fontHeight() {
        return MinecraftClient.getInstance().textRenderer.fontHeight;
    }

    /**
     * Get the width of the text.
     *
     * @param text Text
     * @return Width of the text
     */
    public static int stringWidth(String text) {
        return MinecraftClient.getInstance().textRenderer.getWidth(text);
    }

    /**
     * Draws a text.
     *
     * @param context  GUI context
     * @param text     Text
     * @param position Position of the text
     * @param color    Color of the text
     * @param shadow   Whether to draw the shadow
     */
    public static void drawText(GuiContext context, String text, Position position, int color, boolean shadow) {
        ((GuiContextImpl) context).drawContext().drawText(MinecraftClient.getInstance().textRenderer, text, position.x(), position.y(), color, shadow);
    }

    /**
     * Draws a textured rectangle from a gui texture.
     *
     * @param context   GUI context
     * @param textureId Texture id
     * @param position  Position of the rectangle
     * @param size      Size of the rectangle
     */
    public static void drawTexture(GuiContext context, Identifier textureId, Position position, Size size) {
        drawTexture(context, textureId, position, size, size);
    }

    /**
     * Draws a textured rectangle from a region in a gui texture.
     *
     * <p>The width and height of the region are the same as the dimensions of the rectangle. (And also uv position = (0, 0))
     *
     * @param context     GUI context
     * @param textureId   Texture id
     * @param position    Position of the rectangle
     * @param size        Size of the rectangle
     * @param textureSize Size of the texture
     */
    public static void drawTexture(GuiContext context, Identifier textureId, Position position, Size size, Size textureSize) {
        drawTexture(context, textureId, position, size, Position.DEFAULT_POSITION, textureSize);
    }

    /**
     * Draws a textured rectangle from a region in a gui texture.
     *
     * <p>The width and height of the region are the same as the dimensions of the rectangle.
     *
     * @param context     GUI context
     * @param textureId   Texture id
     * @param position    Position of the rectangle
     * @param size        Size of the rectangle
     * @param uv          Position of the region in the texture
     * @param textureSize Size of the texture
     */
    public static void drawTexture(GuiContext context, Identifier textureId, Position position, Size size, Position uv, Size textureSize) {
        ((GuiContextImpl) context).drawContext().drawGuiTexture(RenderLayer::getGuiTextured, textureId, textureSize.width(), textureSize.height(), uv.x(), uv.y(), position.x(), position.y(), size.width(), size.height());
    }

    /**
     * Draws an item.
     *
     * @param context  GUI context
     * @param itemId   Item id
     * @param position Position of the item
     */
    public static void drawItem(GuiContext context, Identifier itemId, Position position) {
        drawItem(context, Registries.ITEM.get(itemId), position);
    }

    /**
     * Draws an item.
     *
     * @param context  GUI context
     * @param item     Item
     * @param position Position of the item
     */
    public static void drawItem(GuiContext context, Item item, Position position) {
        drawItem(context, item.getDefaultStack(), position);
    }

    /**
     * Draws an item.
     *
     * @param context   GUI context
     * @param itemStack Item stack
     * @param position  Position of the item
     */
    public static void drawItem(GuiContext context, ItemStack itemStack, Position position) {
        ((GuiContextImpl) context).drawContext().drawItem(itemStack, position.x(), position.y());
    }
}
