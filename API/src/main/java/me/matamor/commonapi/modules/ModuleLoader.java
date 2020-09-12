package me.matamor.commonapi.modules;

import me.matamor.commonapi.modules.exception.InvalidModuleException;
import me.matamor.commonapi.modules.java.JavaModule;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public interface ModuleLoader {

    @NotNull
    Module loadModule(@NotNull File var1) throws InvalidModuleException;

    @NotNull
    PluginDescriptionFile getModuleDescription(@NotNull File var1) throws InvalidModuleException;

    @NotNull
    Pattern[] getModuleFileFilters();

    @NotNull
    Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(@NotNull Listener var1, @NotNull Plugin var2);

    void enableModule(@NotNull Plugin var1);

    void disableModule(@NotNull Plugin var1);

    @Nullable
    Class<?> getClassByName(final String name);
}
