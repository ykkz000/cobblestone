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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import ykkz000.cobblestone.client.api.hud.HudSystem;
import ykkz000.cobblestone.client.api.hud.layout.Layout;
import ykkz000.cobblestone.client.api.hud.layout.Position;
import ykkz000.cobblestone.client.api.hud.layout.Size;

/**
 * Base class for all elements.
 *
 * @author ykkz000
 */
@Getter
@Setter
public abstract class BaseElement {
    protected final Identifier id;
    @Setter(AccessLevel.PROTECTED)
    protected PanelElement parent;
    protected Layout.Alignment horizontalAlignment;
    protected Layout.Alignment verticalAlignment;
    protected Position position;
    protected Size size;
    protected boolean visibility;
    protected Position truePosition;

    protected BaseElement(Identifier id) {
        this.id = id;
        this.parent = null;
    }

    /**
     * Remove the element.
     *
     * @see HudSystem#removeElementById(Identifier)
     */
    public void remove() {
        HudSystem.removeElementById(id);
    }

    /**
     * Try to render the element. If the element is not visible, nothing will happen.
     *
     * @param context Draw context
     * @see #render(DrawContext)
     */
    public void tryRender(DrawContext context) {
        if (visibility) {
            context.getMatrices().push();
            context.getMatrices().translate(position.getX(), position.getY(), 0);
            render(context);
            context.getMatrices().pop();
        }
    }

    /**
     * Render the element. This method will be called by {@link #tryRender(DrawContext)}.
     *
     * @param context Draw context
     * @apiNote The draw context's matrix will be translated to the element's position before this method is called.
     */
    public abstract void render(DrawContext context);

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BaseElement other)) {
            return false;
        }
        return id.equals(other.id);
    }

    @FunctionalInterface
    public interface Builder<T extends BaseElement> {
        /**
         * Build an element.
         *
         * @param id Element id
         * @return Element
         */
        T build(Identifier id);
    }
}
