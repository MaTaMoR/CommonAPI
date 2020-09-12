package me.matamor.commonapi.inventory;

import lombok.Getter;
import me.matamor.commonapi.inventory.config.InventoryConfig;
import me.matamor.commonapi.inventory.utils.BungeeCord;
import me.matamor.commonapi.inventory.utils.server.ServerManager;
import me.matamor.commonapi.modules.java.JavaModule;
import me.matamor.commonapi.nbt.NBTUtils;
import me.matamor.commonapi.utils.NMSClassLoader;

public class InventoryModule extends JavaModule {

    @Getter
    private static InventoryModule instance;

    @Getter
    private NBTUtils entityController;

    @Getter
    private InventoryConfig pluginConfig;

    @Getter
    private ServerManager serverManager;

    @Getter
    private BungeeCord bungeeCord;

    @Override
    public void onEnable() {
        if (!setupController()) {
            throw new RuntimeException("Couldn't find a valid HologramEntity controller!");
        }

        instance = this;

        this.pluginConfig = new InventoryConfig(this);
        this.pluginConfig.load();

        this.serverManager = new ServerManager(this);

        this.bungeeCord = new BungeeCord(this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private boolean setupController() {
        NMSClassLoader<NBTUtils> hologramController = new NMSClassLoader<>(this, "me.matamor.commonapi.nms.{version}.nbt.NBTTagUtils");
        if (hologramController.load()) {
            this.entityController = hologramController.getValue();
        }

        return this.entityController != null;
    }
}
