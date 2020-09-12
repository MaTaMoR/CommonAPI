package me.matamor.commonapi.economy;

import lombok.Getter;
import me.matamor.commonapi.CommonAPI;
import me.matamor.commonapi.storage.identifier.Identifier;
import me.matamor.commonapi.utils.BukkitTaskHandler;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleEconomy implements Economy {

    private final Map<UUID, EconomyEntry> entries;

    @Getter
    private final EconomyModule plugin;

    @Getter
    private final BukkitTaskHandler taskHandler;

    public SimpleEconomy(EconomyModule plugin) {
        this.plugin = plugin;
        this.entries = new ConcurrentHashMap<>();

        //30 minutes
        int delay = 20 * 60 * 30;

        this.taskHandler = new BukkitTaskHandler() {
            @Override
            public BukkitTask createTask() {
                return new BukkitRunnable() {
                    @Override
                    public void run() {
                        cleanup();
                    }
                }.runTaskTimer(plugin, delay, delay);
            }
        };

        this.taskHandler.start();
    }

    @Override
    public EconomyEntry load(Identifier identifier) {
        return load(identifier, true);
    }

    @Override
    public EconomyEntry load(Identifier identifier, boolean cache) {
        EconomyEntry economyEntry = this.entries.get(identifier.getUUID());

        if (economyEntry == null) {
            economyEntry = this.plugin.getDatabase().load(identifier);

            if (economyEntry == null) {
                economyEntry = new SimpleEconomyEntry(this.plugin, identifier);
            }

            if (cache) {
                this.entries.put(identifier.getUUID(), economyEntry);
            }
        }

        return economyEntry;
    }

    @Override
    public void loadAll() {
        for (Player player : CommonAPI.getInstance().getServer().getOnlinePlayers()) {
            Identifier identifier = CommonAPI.getInstance().getIdentifierManager().getIdentifier(player.getUniqueId());

            //Load every online player
            load(identifier);
        }
    }

    @Override
    public EconomyEntry getEntry(UUID uuid) {
        return this.entries.get(uuid);
    }

    @Override
    public EconomyEntry getEntry(String name) {
        return this.entries.values().stream()
                .filter(e -> e.getIdentifier().getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void remove(UUID uuid) {
        this.entries.remove(uuid);
    }

    @Override
    public void unload(UUID uuid) {
        EconomyEntry economyEntry = this.entries.remove(uuid);
        if (economyEntry == null) return;

        this.plugin.getDatabase().save(economyEntry);
    }

    @Override
    public void unloadAll() {
        this.entries.values().forEach(entry -> this.plugin.getDatabase().save(entry));
        this.entries.clear();
    }

    private void cleanup() {
        this.entries.values().removeIf(entry -> entry.getIdentifier().getPlayer() == null);
    }
}
