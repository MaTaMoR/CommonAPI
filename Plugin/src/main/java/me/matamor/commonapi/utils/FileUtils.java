package me.matamor.commonapi.utils;

import me.matamor.commonapi.modules.Module;
import me.matamor.commonapi.utils.annotation.FilePath;
import me.matamor.commonapi.utils.annotation.PluginClass;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    public static Plugin getPlugin(Class<?> clazz) {
        PluginClass pluginClass = clazz.getAnnotation(PluginClass.class);
        if (pluginClass == null) {
            throw new IllegalStateException("'" + clazz.getName() + "' PluginClass annotation is missing");
        }

        Class<?> plugin = pluginClass.value();

        if (JavaPlugin.class.isAssignableFrom(plugin)) {
            return JavaPlugin.getPlugin((Class<? extends JavaPlugin>) plugin);
        } else if (Module.class.isAssignableFrom(plugin)) {
            return Module.getModule((Class<? extends Module>) plugin);
        } else {
            throw new IllegalStateException("'" + clazz.getName() + "' Doesn't extend JavaPlugin");
        }
    }

    public static FilePath getFilePath(Class<?> clazz) {
        if (clazz.getAnnotation(FilePath.class) == null) {
            throw new IllegalStateException("'" + clazz.getName() + "' FilePath annotation is missing");
        }

        return clazz.getAnnotation(FilePath.class);
    }

    public static File getAvailableFolder(File fileContainer, String fileName) {
        String actualName = fileName;
            File file;
        int count = 1;
        while ((file = new File(fileContainer, actualName)).exists()) {
            actualName = fileName + " " + count++;
        }

        return file;
    }

    public static File getAvailableFile(File fileContainer, String fileName, String extension) {
        String actualName = fileName;
        File file;
        int count = 1;
        while ((file = new File(fileContainer, actualName + extension)).exists()) {
            actualName = fileName + " " + count++;
        }

        return file;
    }

    public static boolean deleteDirectory(File directory) {
        if (directory.exists()){
            File[] files = directory.listFiles();
            if (files != null){
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }

        return directory.delete();
    }

    public static void copy(File source, File target) throws IOException {
        List<String> ignore = Arrays.asList("uid.dat", "session.dat", "chests.yml");

        if (ignore.contains(source.getName())) {
            return;
        }

        if (source.isDirectory()) {
            if (!target.exists()) {
                target.mkdirs();
            }

            String[] files = source.list();

            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(target, file);

                copy(srcFile, destFile);
            }
        } else {
            try (InputStream in = new FileInputStream(source)) {
                try (OutputStream out = new FileOutputStream(target)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                }
            }
        }
    }
}
