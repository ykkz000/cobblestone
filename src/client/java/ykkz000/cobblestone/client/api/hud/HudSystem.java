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

package ykkz000.cobblestone.client.api.hud;

import lombok.NonNull;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import ykkz000.cobblestone.client.api.hud.draw.GuiContext;
import ykkz000.cobblestone.client.api.hud.element.BaseElement;
import ykkz000.cobblestone.client.api.hud.element.PanelElement;
import ykkz000.cobblestone.client.api.hud.layout.Layout;
import ykkz000.cobblestone.client.api.hud.layout.Position;
import ykkz000.cobblestone.client.api.hud.layout.Size;

import java.util.HashMap;
import java.util.Map;

/**
 * HUD system.
 *
 * @author ykkz000
 */
public final class HudSystem {
    private static final Identifier ROOT_ID = Identifier.of("cobblestone", "root");
    private static final Map<Identifier, BaseElement> ELEMENTS = new HashMap<>();
    private static final RootPanelElement ROOT = createElement(ROOT_ID, RootPanelElement.rootbBuilder());

    private HudSystem() {
    }

    /**
     * Get the root panel.
     *
     * @return Root panel
     * @apiNote The root panel cannot be removed. And also, the layout, position, size and visibility of the root panel cannot be changed. The layout will always be relative layout. The position will always be {@link Position#DEFAULT_POSITION}. The size will always be {@link Size#DEFAULT_SIZE}. The visibility will always be true.
     */
    public static PanelElement getRootPanel() {
        return ROOT;
    }

    /**
     * Create an element.
     *
     * @param id      Element id
     * @param builder Element builder
     * @param <T>     Element type
     * @return Element
     */
    public static <T extends BaseElement> T createElement(Identifier id, BaseElement.Builder<T> builder) {
        if (ELEMENTS.containsKey(id)) {
            throw new IllegalArgumentException("Element with id " + id + " already exists");
        }
        T element = builder.build(id);
        ELEMENTS.put(id, element);
        return element;
    }

    /**
     * Find an element by id.
     *
     * @param id Element id
     * @return Element
     */
    public static BaseElement findElementById(Identifier id) {
        return ELEMENTS.get(id);
    }

    /**
     * Remove an element by id.
     *
     * @param id Element id
     */
    public static void removeElementById(Identifier id) {
        if (ROOT_ID.equals(id)) {
            throw new IllegalArgumentException("Cannot remove root element");
        }
        BaseElement element = ELEMENTS.get(id);
        if (element instanceof PanelElement panel) {
            panel.children().forEach(e -> removeElementById(e.getId()));
        }
        if (element.getParent() != null) {
            element.getParent().removeChild(element);
        }
        ELEMENTS.remove(id);
    }

    /**
     * Render the HUD.
     *
     * @param context     GUI context
     * @param tickCounter Render tick counter
     * @apiNote This method is called automatically by the game.
     */
    public static void render(GuiContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        ROOT.setRootSize(new Size(client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight()));
        ROOT.tryRender(context);
    }

    private static class RootPanelElement extends PanelElement {
        static BaseElement.Builder<RootPanelElement> rootbBuilder() {
            return RootPanelElement::new;
        }

        protected RootPanelElement(Identifier id) {
            super(id);
            position = Position.DEFAULT_POSITION;
            size = Size.DEFAULT_SIZE;
            visibility = true;
            layout = Layout.relative();
        }

        @Override
        public void setLayout(@NonNull Layout layout) {
            throw new UnsupportedOperationException("Cannot set layout for root panel");
        }

        @Override
        public void setPosition(@NonNull Position position) {
            throw new UnsupportedOperationException("Cannot set position for root panel");
        }

        @Override
        public void setSize(@NonNull Size size) {
            throw new UnsupportedOperationException("Cannot set size for root panel");
        }

        @Override
        public void setVisibility(boolean visibility) {
            throw new UnsupportedOperationException("Cannot set visibility for root panel");
        }

        private void setRootSize(@NonNull Size size) {
            this.size = size;
        }
    }
}
