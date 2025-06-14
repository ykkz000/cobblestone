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
import net.minecraft.util.Identifier;
import ykkz000.cobblestone.client.api.hud.draw.GuiContext;
import ykkz000.cobblestone.client.api.hud.layout.Layout;

import java.util.ArrayList;
import java.util.List;

/**
 * Panel element. This element can contain other elements.
 *
 * @author ykkz000
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class PanelElement extends BaseElement {
    protected final List<BaseElement> children = new ArrayList<>();
    protected Layout layout;

    public static BaseElement.Builder<PanelElement> builder() {
        return PanelElement::new;
    }

    protected PanelElement(Identifier id) {
        super(id);
        this.layout = Layout.absolute();
    }

    /**
     * Set the layout.
     *
     * @param layout Layout. If null, absolute layout will be used.
     */
    public void setLayout(Layout layout) {
        if (layout == null) {
            layout = Layout.absolute();
        }
        this.layout = layout;
    }

    public void addChild(BaseElement child) {
        for (PanelElement parent = this; parent != null; parent = parent.getParent()) {
            if (parent.getId().equals(child.getId())) {
                throw new IllegalArgumentException("Cannot add the descendant as a child");
            }
        }
        if (child.getParent() != null) {
            child.getParent().removeChild(child);
        }
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(BaseElement child) {
        children.remove(child);
        child.setParent(null);
    }

    public List<BaseElement> children() {
        return List.copyOf(children);
    }

    @Override
    public void render(GuiContext context) {
        layout.layoutChildren(this, children);
        children.forEach(e -> e.tryRender(context));
    }
}
