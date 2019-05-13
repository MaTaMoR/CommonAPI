package me.matamor.commonapi.economy;

import me.matamor.commonapi.economy.config.EconomyConfig;
import me.matamor.commonapi.economy.database.EconomyDatabase;

public interface EconomyPlugin {

    EconomyConfig getPluginConfig();

    EconomyDatabase getDatabase();

    Economy getEconomy();

}
