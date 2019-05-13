package me.matamor.commonapi.economy;

import lombok.Getter;
import me.matamor.commonapi.CommonAPI;
import me.matamor.commonapi.economy.commands.*;
import me.matamor.commonapi.economy.config.EconomyConfig;
import me.matamor.commonapi.economy.database.EconomyDatabase;
import me.matamor.commonapi.economy.listener.EconomyListener;
import me.matamor.commonapi.economy.vault.VaultRegister;
import me.matamor.commonapi.modules.Module;
import me.matamor.commonapi.storage.identifier.Identifier;
import me.matamor.commonapi.storage.identifier.listener.IdentifierListener;

import java.text.DecimalFormat;
import java.util.logging.Level;

public class EconomyModule extends Module implements EconomyPlugin {

    @Getter
    private static EconomyModule instance;

    @Getter
    private EconomyConfig pluginConfig;

    @Getter EconomyDatabase database;

    @Getter
    private Economy economy;

    @Getter
    private VaultRegister vaultRegister;

    @Override
    public void onEnable() {
        instance = this;

        this.pluginConfig = new EconomyConfig(this);
        this.pluginConfig.load();

        this.database = new EconomyDatabase(this);
        if (!this.database.loadDatabase()) {
            getLogger().log(Level.SEVERE, "Couldn't loadOrCreate economy database! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //Register database in the API
        CommonAPI.getInstance().getStorageManager().registerStorage(this.database);

        this.economy = new SimpleEconomy(this);
        this.economy.loadAll();

        //Load EconomyEntry when the Identifier is loaded!
        CommonAPI.getInstance().getIdentifierManager().registerListener(getName(), new IdentifierListener() {
            @Override
            public void onLoad(Identifier identifier) {
                getEconomy().load(identifier);
            }

            @Override
            public void onUnload(Identifier identifier) {
                getEconomy().unload(identifier.getUUID());
            }
        });

        //Inject vault!
        if (this.pluginConfig.registerVault) {
            if (getServer().getPluginManager().isPluginEnabled("Vault")) {
                this.vaultRegister = new VaultRegister(this); //If we do it on a different class we won't throw an exception if Vault is not found
                this.vaultRegister.register();

                if (!this.vaultRegister.isRegistered()) {
                    getLogger().log(Level.SEVERE, "Couldn't register Vault dependency!");
                    setEnabled(false);
                } else {
                    getLogger().log(Level.INFO, "Vault registered!");
                }
            } else {
                getLogger().log(Level.SEVERE, "Vault register is enabled but Vault is missing.");
                setEnabled(false);
            }
        }

        EconomyCommand economyCommand = new EconomyCommand(this);
        economyCommand.register();

        getServer().getPluginManager().registerEvents(new EconomyListener(this), this);
    }

    @Override
    public void onDisable() {
        if (this.economy != null) {
            this.economy.unloadAll();
        }

        instance = null;
    }

    private static final DecimalFormat FORMAT = new DecimalFormat("###,##0.00");

    public String formatMoney(double money) {
        return this.pluginConfig.moneyFormat.replace("{money}", FORMAT.format(money));
    }
}
