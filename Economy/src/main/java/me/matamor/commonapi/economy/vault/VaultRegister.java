package me.matamor.commonapi.economy.vault;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.matamor.commonapi.economy.EconomyModule;
import me.matamor.commonapi.utils.Validate;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;

@RequiredArgsConstructor
public class VaultRegister {

    private final EconomyModule plugin;

    @Getter
    private VaultEconomy economy;

    public void register() {
        Validate.isFalse(isRegistered(), "VaultEconomy is already injected");

        this.economy = new VaultEconomy(this.plugin, this.plugin.getPluginConfig().vaultAccount);
        this.plugin.getServer().getServicesManager().register(Economy.class, this.economy, this.plugin, ServicePriority.Highest);
    }

    public boolean isRegistered() {
        if (this.economy == null) return false;

        RegisteredServiceProvider<Economy> serviceProvider = this.plugin.getServer().getServicesManager().getRegistration(Economy.class);
        return serviceProvider != null && serviceProvider.getProvider() != null && serviceProvider.getProvider() == this.economy;
    }

    public void unregister() {
        if (this.economy != null) {
            this.plugin.getServer().getServicesManager().unregister(Economy.class, this.economy);
        }
    }
}
