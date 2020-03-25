package me.matamor.commonapi.container.config;

import lombok.Getter;
import me.matamor.commonapi.config.IConfig;
import me.matamor.commonapi.container.ContainerEntry;
import me.matamor.commonapi.container.SimpleContainer;
import me.matamor.commonapi.utils.FileUtils;
import me.matamor.commonapi.utils.annotation.FilePath;

import java.util.Arrays;
import java.util.Collections;

public class ConfigContainer<T> extends SimpleContainer<T> {

    @Getter
    private final IConfig config;

    public ConfigContainer(Class<? extends ContainerEntry<T>> enumClass, Serializer<T> serializer) {
        super (FileUtils.getPlugin(enumClass), serializer,
                enumClass.isEnum() ? Arrays.asList(enumClass.getEnumConstants()) : Collections.emptyList());

        FilePath filePath = FileUtils.getFilePath(enumClass);

        this.config = new IConfig(getPlugin(), filePath.value(), (filePath.folder().isEmpty() ? null : filePath.folder()));

        load();
    }
}
