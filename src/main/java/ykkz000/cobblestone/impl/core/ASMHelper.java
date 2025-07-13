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

package ykkz000.cobblestone.impl.core;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

/**
 * ASM helper.
 *
 * @author ykkz000
 */
public final class ASMHelper {
    private ASMHelper() {
    }

    /**
     * Check if a class has an annotation.
     *
     * @param classNode  Class node
     * @param annotation Annotation class
     * @return True if the class has the annotation, false otherwise
     */
    public static boolean checkAnnotation(ClassNode classNode, Class<?> annotation) {
        return classNode.visibleAnnotations != null && classNode.visibleAnnotations.stream()
                .anyMatch(annotationNode -> annotationNode.desc.equals(Type.getType(annotation).getDescriptor()));
    }

    /**
     * Check if a class has an annotation with a specific value.
     *
     * @param classNode  Class node
     * @param annotation Annotation class
     * @param key        Key
     * @param value      Value
     * @return True if the class has the annotation with the value, false otherwise
     */
    public static boolean checkAnnotationValue(ClassNode classNode, Class<?> annotation, String key, String value) {
        return classNode.visibleAnnotations != null && classNode.visibleAnnotations.stream()
                .filter(annotationNode -> annotationNode.desc.equals(Type.getType(annotation).getDescriptor()))
                .map(annotationNode -> {
                    for (int i = 0; i < annotationNode.values.size(); i += 2) {
                        if (key.equals(annotationNode.values.get(i)) && value.equals(annotationNode.values.get(i + 1))) {
                            return true;
                        }
                    }
                    return false;
                }).anyMatch(b -> true);
    }
}
