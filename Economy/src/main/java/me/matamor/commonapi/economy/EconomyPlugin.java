package me.matamor.commonapi.economy;

import me.matamor.commonapi.economy.config.EconomyConfig;
import me.matamor.commonapi.economy.database.EconomySQLDatabase;

public interface EconomyPlugin {

    EconomyConfig getPluginConfig();

    EconomySQLDatabase getDatabase();

    Economy getEconomy();

}
