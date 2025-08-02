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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import ykkz000.cobblestone.client.api.experiment.hud.HudSystem;
import ykkz000.cobblestone.client.api.experiment.hud.draw.GuiContext;
import ykkz000.cobblestone.client.api.experiment.hud.layout.Layout;
import ykkz000.cobblestone.client.api.experiment.hud.layout.Position;
import ykkz000.cobblestone.client.api.experiment.hud.layout.Size;

/**
 * Base class for all elements.
 *
 * @author ykkz000
 */
@Getter
@Setter
@Environment(EnvType.CLIENT)
public abstract class BaseElement {
    /**
     * Element id.
     */
    @NonNull
    protected final Identifier id;
    /**
     * Parent panel. Default is null.
     */
    @Setter(AccessLevel.PROTECTED)
    protected PanelElement parent;
    /**
     * Horizontal alignment of the element. Default is START.
     */
    @NonNull
    protected Layout.Alignment horizontalAlignment;
    /**
     * Vertical alignment of the element. Default is START.
     */
    @NonNull
    protected Layout.Alignment verticalAlignment;
    /**
     * Position of the element. Default is {@link Position#DEFAULT_POSITION}.
     */
    @NonNull
    protected Position position;
    /**
     * Size of the element. Default is {@link Size#DEFAULT_SIZE}.
     */
    @NonNull
    protected Size size;
    /**
     * Visibility of the element. Default is true.
     */
    protected boolean visibility;
    /**
     * True position of the element. Default is {@link Position#DEFAULT_POSITION}.
     *
     * @apiNote This field is calculated by the layout before rendering the element. So in most time your manual changes will not affect this field.
     */
    @NonNull
    protected Position truePosition;

    protected BaseElement(@NonNull Identifier id) {
        this.id = id;
        this.parent = null;
        this.horizontalAlignment = Layout.Alignment.START;
        this.verticalAlignment = Layout.Alignment.START;
        this.position = Position.DEFAULT_POSITION;
        this.size = Size.DEFAULT_SIZE;
        this.visibility = true;
        this.truePosition = Position.DEFAULT_POSITION;
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
     * @param context GUI context
     * @see #render(GuiContext)
     */
    public void tryRender(GuiContext context) {
        if (visibility) {
            context.pushMatrix();
            context.translate(position.x(), position.y());
            render(context);
            context.popMatrix();
        }
    }

    /**
     * Render the element. This method will be called by {@link #tryRender(GuiContext)}.
     *
     * @param context GUI context
     * @apiNote The GUI context's matrix will be translated to the element's position before this method is called.
     */
    public abstract void render(GuiContext context);

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
