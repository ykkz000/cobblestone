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

package ykkz000.cobblestone.client.api.hud.layout;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ykkz000.cobblestone.client.api.hud.element.BaseElement;
import ykkz000.cobblestone.client.api.hud.element.PanelElement;

import java.util.List;

/**
 * Interface for layout.
 *
 * @author ykkz000
 * @apiNote Layout will calculate the true position of the children before rendering the children every frame.
 */
@FunctionalInterface
@Environment(EnvType.CLIENT)
public interface Layout {
    /**
     * Get an absolute layout.
     *
     * @return Absolute layout
     */
    static Layout absolute() {
        return (parent, children) -> {
            for (BaseElement child : children) {
                child.setTruePosition(child.getPosition());
            }
        };
    }

    /**
     * Get a relative layout.
     *
     * @return Relative layout
     * @apiNote If the child's horizontal alignment is START, the child's x will be the same as the child's x.If the child's horizontal alignment is END, the child's x will be the same as the parent's width minus the child's x.<br/>And the same for vertical alignment.<br/>This layout does not support center alignment.
     */
    static Layout relative() {
        return (parent, children) -> {
            for (BaseElement child : children) {
                int x = switch (child.getHorizontalAlignment()) {
                    case START -> child.getPosition().x();
                    case CENTER -> throw new IllegalStateException("Relative layout does not support center alignment");
                    case END -> parent.getSize().width() - child.getPosition().x();
                };
                int y = switch (child.getVerticalAlignment()) {
                    case START -> child.getPosition().y();
                    case CENTER -> throw new IllegalStateException("Relative layout does not support center alignment");
                    case END -> parent.getSize().height() - child.getPosition().y();
                };
                child.setTruePosition(new Position(x, y));
            }
        };
    }

    /**
     * Get a horizontal layout.
     *
     * @return Horizontal layout
     */
    static Layout horizontal() {
        return (parent, children) -> {
            int x = 0;
            for (BaseElement child : children) {
                int y = switch (child.getVerticalAlignment()) {
                    case START -> 0;
                    case CENTER -> (parent.getSize().height()- child.getSize().height()) / 2;
                    case END -> parent.getSize().height() - child.getSize().height();
                };
                child.setTruePosition(new Position(x, y));
                x += child.getSize().width();
            }
        };
    }

    /**
     * Get a vertical layout.
     *
     * @return Vertical layout
     */
    static Layout vertical() {
        return (parent, children) -> {
            int y = 0;
            for (BaseElement child : children) {
                int x = switch (child.getHorizontalAlignment()) {
                    case START -> 0;
                    case CENTER -> (parent.getSize().width() - child.getSize().width()) / 2;
                    case END -> parent.getSize().width() - child.getSize().width();
                };
                child.setTruePosition(new Position(x, y));
                y += child.getSize().height();
            }
        };
    }

    /**
     * Get a grid layout.
     *
     * @param rows    Rows
     * @param columns Columns1
     * @return Grid layout
     */
    static Layout grid(int rows, int columns) {
        return (parent, children) -> {
            int deltaX = parent.getSize().width() / columns;
            int deltaY = parent.getSize().height() / rows;
            for (int i = 0; i < children.size(); i++) {
                BaseElement child = children.get(i);
                int x = (i % columns) * deltaX + switch (child.getHorizontalAlignment()) {
                    case START -> 0;
                    case CENTER -> (deltaX - child.getSize().width()) / 2;
                    case END -> deltaX - child.getSize().width();
                };
                int y = (i / columns) * deltaY + switch (child.getVerticalAlignment()) {
                    case START -> 0;
                    case CENTER -> (deltaY - child.getSize().height()) / 2;
                    case END -> deltaY - child.getSize().height();
                };
                child.setTruePosition(new Position(x, y));
            }
        };
    }

    /**
     * Layout the children of the panel.
     *
     * @param parent   Parent panel
     * @param children Children
     */
    void layoutChildren(PanelElement parent, List<BaseElement> children);

    /**
     * Alignment.
     *
     * @author ykkz000
     */
    enum Alignment {
        /**
         * Start alignment.
         */
        START,
        /**
         * Center alignment.
         */
        CENTER,
        /**
         * End alignment.
         */
        END
    }
}
