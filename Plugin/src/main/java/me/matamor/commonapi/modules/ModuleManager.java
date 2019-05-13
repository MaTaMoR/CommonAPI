package me.matamor.commonapi.modules;

import java.util.Collection;

public interface ModuleManager {

    Module getModule(String name);

    boolean isEnabled(String name);

    Collection<Module> getModules();

}
