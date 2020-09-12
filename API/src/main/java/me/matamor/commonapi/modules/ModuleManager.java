package me.matamor.commonapi.modules;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public interface ModuleManager {

    void registerInterface(@NotNull Class<? extends ModuleLoader> loader) throws IllegalArgumentException;

    Module[] loadModules(@NotNull File directory);

    Module loadModule(@NotNull File file);

    @Nullable
    Module getModule(@NotNull String name);;

    @NotNull
    Module[] getModules();

    boolean isModuleEnabled(@NotNull String name);

    boolean isModuleEnabled(@Nullable Module module);

    void enableModule(@NotNull final Module plugin);

    void disableModules();

    void disableModule(@NotNull final Module plugin);
}
