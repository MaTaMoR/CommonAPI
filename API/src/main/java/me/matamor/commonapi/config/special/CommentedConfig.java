package me.matamor.commonapi.config.special;

import com.google.common.io.Files;
import lombok.Getter;
import me.matamor.commonapi.config.IConfig;
import me.matamor.commonapi.utils.FileUtils;
import me.matamor.commonapi.utils.PrimitiveUtils;
import me.matamor.commonapi.utils.serializer.SerializationException;
import me.matamor.commonapi.utils.serializer.SerializationManager;
import me.matamor.commonapi.utils.serializer.Serializer;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CommentedConfig {

    @Getter
    private final Plugin plugin;

    @Getter
    private final File file;

    private final List<ConfigEntry> entries;

    public CommentedConfig(Plugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        this.entries = getEntries();
    }

    private List<ConfigEntry> getEntries() {
        Field[] fields = getClass().getDeclaredFields();
        List<ConfigEntry> entries = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Entry.class)) {
                Entry entry = field.getAnnotation(Entry.class);

                String[] comments = new String[] {};

                if (field.isAnnotationPresent(Comment.class)) {
                    comments = field.getAnnotation(Comment.class).value();
                }

                Serializer serializer = SerializationManager.getInstance().getSerializer(field.getType());
                entries.add(new ConfigEntry(this, field, entry.value(), comments, serializer));
            }
        }

        return entries;
    }

    public void load() throws ConfigurationException {
        try {
            File file = getFile();

            if (file.exists()) {
                IConfig config;

                try {
                    config = new IConfig(getPlugin(), file);
                } catch (Exception e) {
                    log(Level.SEVERE, "Couldn't load the config file!", e);
                    storeCurrentConfig();
                    return;
                }

                int anyError = 0;

                for (ConfigEntry configEntry : this.entries) {
                    Serializer<?> serializer = configEntry.getSerializer();
                    Object object = config.get(configEntry.getPath());

                    if (serializer != null && object != null){
                        try {
                            object = serializer.deserialize(object);
                        } catch (Exception e) {
                            if (e instanceof SerializationException) {
                                log(Level.SEVERE, "There was an exception while Deserializing the value '%s' on the path '%s': '%s'", object, configEntry.getPath(), e.getMessage());
                            } else {
                                log(Level.SEVERE, "There was an unhandled exception while Deserializing the value '%s' on the path '%s': '%s'", object, configEntry.getPath(), e);
                            }

                            anyError++;
                            continue;
                        }
                    }

                    if (object == null) {
                        log(Level.SEVERE, "Missing value in the config path '%s'", configEntry.getPath());
                        anyError++;
                    } else if (PrimitiveUtils.isInstance(configEntry.getType(), object)) {
                        configEntry.set(object);
                    } else {
                        log(Level.SEVERE, "Invalid value in the config path '%s': Needed '%s' / Value '%s'", configEntry.getPath(), configEntry.getType(), object.getClass());
                        anyError++;
                    }
                }

                if (anyError > 0) {
                    storeCurrentConfig();
                }
            } else {
                save();
            }
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Couldn't loadOrCreate config", e);
        }
    }

    public void save() throws ConfigurationException {
        try {
            File file = getFile();

            Files.createParentDirs(file);
            if (!file.exists()) {
                file.createNewFile();
            }

            IConfig config = new IConfig(getPlugin(), file);
            for (ConfigEntry configEntry : this.entries) {
                try {
                    config.set(configEntry.getPath(), configEntry.serialize());
                } catch (Exception e) {
                    if (e instanceof SerializationException) {
                        log(Level.SEVERE, "There was an exception while Serializing the value on the path '%s': '%s'", configEntry.getPath(), e.getMessage());
                    } else {
                        log(Level.SEVERE, "There was an unhandled exception while Deserializing the value on the path '%s': '%s'", configEntry.getPath(), e);
                    }
                }
            }

            config.save();

            Map<String, String> comments = new LinkedHashMap<>();
            for (ConfigEntry entry : entries) {
                if (entry.getComments().length == 0) continue;

                comments.put(entry.getPath(), fixComment(entry.getPath(), entry.getComments()));
            }

            saveComments(file, comments);
        } catch (IOException e) {
            throw new ConfigurationException("Couldn't handleRemoval config", e);
        }
    }

    private String fixComment(String path, String... commentLines) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder leadingSpaces = new StringBuilder();

        for (int n = 0; n < path.length(); n++) {
            if (path.charAt(n) == '.') {
                leadingSpaces.append("  ");
            }
        }

        for (String line : commentLines) {
            if (!line.isEmpty()) {
                line = leadingSpaces + "#" + line;
            } else {
                line = " ";
            }

            if (stringBuilder.length() > 0) {
                stringBuilder.append("\r\n");
            }

            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

    private void saveComments(File file, Map<String, String> comments) {
        if (!comments.isEmpty()) {
            String[] yamlContents = convertFileToString(file).split("[" + System.getProperty("line.separator") + "]");

            StringBuilder newContents = new StringBuilder();
            String currentPath = "";
            boolean commentedPath = false;
            boolean node;
            int depth = 0;

            for (String line : yamlContents) {
                if (line.contains(": ") || line.length() > 1 && line.charAt(line.length() - 1) == ':') {
                    commentedPath = false;
                    node = true;

                    int index = line.indexOf(": ");
                    if (index < 0) {
                        index = line.length() - 1;
                    }

                    if (currentPath.isEmpty()) {
                        currentPath = line.substring(0, index);
                    } else {
                        int whiteSpace = 0;
                        for (int n = 0; n < line.length(); n++) {
                            if (line.charAt(n) == ' ') {
                                whiteSpace++;
                            } else {
                                break;
                            }
                        }

                        if (whiteSpace / 2 > depth) {
                            currentPath += "." + line.substring(whiteSpace, index);
                            depth++;
                        } else if (whiteSpace / 2 < depth) {
                            int newDepth = whiteSpace / 2;
                            for (int i = 0; i < depth - newDepth; i++) {
                                currentPath = currentPath.replace(currentPath.substring(currentPath.lastIndexOf('.')), "");
                            }

                            int lastIndex = currentPath.lastIndexOf('.');
                            if (lastIndex < 0) {
                                currentPath = "";
                            } else {
                                currentPath = currentPath.replace(currentPath.substring(currentPath.lastIndexOf('.')), "");
                                currentPath += ".";
                            }

                            currentPath += line.substring(whiteSpace, index);
                            depth = newDepth;
                        } else {
                            int lastIndex = currentPath.lastIndexOf('.');
                            if (lastIndex < 0) {
                                currentPath = "";
                            } else {
                                currentPath = currentPath.replace(currentPath.substring(currentPath.lastIndexOf('.')), "");
                                currentPath += ".";
                            }

                            currentPath += line.substring(whiteSpace, index);
                        }
                    }

                } else {
                    node = false;
                }

                if (node) {
                    String comment = null;
                    if (!commentedPath) {
                        comment = comments.get(currentPath);
                    }

                    if (comment != null) {
                        line = comment + System.getProperty("line.separator") + line + System.getProperty("line.separator");
                        commentedPath = true;
                    } else {
                        line += System.getProperty("line.separator");
                    }
                }

                newContents.append(line).append(!node ? System.getProperty("line.separator") : "");
            }

            while (newContents.toString().startsWith(System.getProperty("line.separator"))) {
                newContents = new StringBuilder(newContents.toString().replaceFirst(System.getProperty("line.separator"), ""));
            }

            stringToFile(newContents.toString(), file);
        }
    }

    private String convertFileToString(File file) {
        if (file != null && file.exists() && file.canRead() && !file.isDirectory()) {
            char[] buffer = new char[1024];
            try (Writer writer = new StringWriter(); InputStreamReader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(file)), StandardCharsets.UTF_8)) {
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }

                return writer.toString();
            } catch (IOException e) {
                throw new ConfigurationException("Couldn't convert File to String", e);
            }
        }

        return "";
    }

    private void stringToFile(String source, File file) {
        try (OutputStreamWriter out = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)), StandardCharsets.UTF_8)) {
            out.write(source);
        } catch (IOException e) {
            throw new ConfigurationException("Couldn't handleRemoval from String to File", e);
        }
    }

    private void log(Level level, String message, Object... values) {
        this.plugin.getLogger().log(level, String.format(message, values));
    }

    private void storeCurrentConfig() {
        log(Level.SEVERE, "There was an error while loading config, generating a valid one...");

        File newFile = FileUtils.getAvailableFile(this.plugin.getDataFolder(), "old_config", ".yml");

        try {
            FileUtils.copy(this.file, newFile);
        } catch (IOException e) {
            getPlugin().getLogger().log(Level.SEVERE, "Couldn't save old config!", e);
        }

        save();

        log(Level.INFO, "Fixed config generated, old config has been saved to the file: " + newFile.getName());
    }
}
