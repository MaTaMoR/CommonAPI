package me.matamor.commonapi.events;

import me.matamor.commonapi.modules.Module;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ModuleEnableEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Module module;

    public ModuleEnableEvent(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
