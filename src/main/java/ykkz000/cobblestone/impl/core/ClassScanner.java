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

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileInputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class scanner.
 *
 * @author ykkz000
 */
public class ClassScanner {
    /**
     * Scan classes in a package with an annotation and call a callback for each class.
     *
     * @param basePackage   Base package
     * @param clazz         Class of the class loader
     * @param annotation    Annotation
     * @param checkCallback Check callback
     * @param callback      Callback
     * @throws Exception if an error occurs
     */
    public void scan(String basePackage, Class<?> clazz, Class<?> annotation, ClassScannerCheckCallback checkCallback, ClassScannerCallback callback) throws Exception {
        String basePackagePath = basePackage.replaceAll("\\.", "/");
        Enumeration<URL> dirs = clazz.getClassLoader().getResources(basePackagePath);
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                File file = new File(URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8));
                scanBeansInDirectory(file, annotation, checkCallback, callback);
            } else if ("jar".equals(protocol)) {
                JarURLConnection jarUrlConnection = (JarURLConnection) url.openConnection();
                JarFile jarFile = jarUrlConnection.getJarFile();
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String entryName = entry.getName();
                    if (entryName.endsWith(".class") && entryName.startsWith(basePackagePath)) {
                        ClassReader classReader = new ClassReader(jarFile.getInputStream(entry));
                        ClassNode classNode = new ClassNode();
                        classReader.accept(classNode, 0);
                        processClass(classNode, annotation, checkCallback, callback);
                    }
                }
            }
        }
    }

    private void scanBeansInDirectory(File parent, Class<?> annotation, ClassScannerCheckCallback checkCallback, ClassScannerCallback callback) throws Exception {
        File[] files = parent.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                scanBeansInDirectory(file, annotation, checkCallback, callback);
            } else if (file.canRead()) {
                String filePath = file.getPath();
                if (filePath.endsWith(".class")) {
                    ClassReader classReader = new ClassReader(new FileInputStream(file));
                    ClassNode classNode = new ClassNode();
                    classReader.accept(classNode, 0);
                    processClass(classNode, annotation, checkCallback, callback);
                }
            }
        }
    }

    private void processClass(ClassNode classNode, Class<?> annotation, ClassScannerCheckCallback checkCallback, ClassScannerCallback callback) throws Exception {
        if (ASMHelper.checkAnnotation(classNode, annotation) && (checkCallback == null || checkCallback.checkClass(classNode))) {
            callback.onClassScanned(Type.getObjectType(classNode.name).getClassName());
        }
    }

    @FunctionalInterface
    public interface ClassScannerCallback {
        void onClassScanned(String className) throws Exception;
    }

    @FunctionalInterface
    public interface ClassScannerCheckCallback {
        boolean checkClass(ClassNode classNode) throws Exception;
    }
}
