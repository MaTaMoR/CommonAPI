package me.matamor.commonapi.modules;

import org.bukkit.plugin.Plugin;

public interface Module extends Plugin {

    ModuleLoader getModuleLoader();

    ClassLoader getClassLoader();

}
