package me.matamor.commonapi.storage.identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.custominventories.utils.BasicTaskHandler;
import me.matamor.commonapi.storage.StorageException;
import me.matamor.commonapi.storage.identifier.listener.IdentifierListener;
import me.matamor.commonapi.utils.Validate;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
public class SimpleIdentifierManager implements IdentifierManager {

    private final Map<UUID, Identifier> entries = new ConcurrentHashMap<>();

    private final Map<String, IdentifierListener> listeners = new ConcurrentHashMap<>();

    @Getter
    private final IdentifierSingleDatabaseManager database;

    @Getter
    private final BasicTaskHandler taskHandler;

    public SimpleIdentifierManager(IdentifierSingleDatabaseManager database) {
        this.database = database;

        //30 minutes
        int delay = 20 * 60 * 30;

        this.taskHandler = new BasicTaskHandler() {
            @Override
            public BukkitTask createTask() {
                return new BukkitRunnable() {
                    @Override
                    public void run() {
                        cleanup();
                    }
                }.runTaskTimer(database.getPlugin(), delay, delay);
            }
        };

        this.taskHandler.start();
    }

    @Override
    public Identifier load(int id) {
        Identifier identifier = getIdentifier(id);

        if (identifier == null) {
            //Load from database
            identifier = this.database.loadById(id);

            if (identifier != null) {
                this.entries.put(identifier.getUUID(), identifier);

                //Call listeners now that the identifier is loaded
                callLoad(identifier);
            }
        }

        return identifier;
    }

    @Override
    public Identifier load(String name) {
        Identifier identifier = getIdentifier(name);

        if (identifier == null) {
            //Load from database
            identifier = this.database.loadByName(name);

            if (identifier != null) {
                this.entries.put(identifier.getUUID(), identifier);

                //Call listeners now that the identifier is loaded
                callLoad(identifier);
            }
        }

        return identifier;
    }

    @Override
    public Identifier load(UUID uuid) {
        Identifier identifier = getIdentifier(uuid);

        if (identifier == null) {
            //Load from database
            identifier = this.database.load(uuid);

            if (identifier != null) {
                this.entries.put(identifier.getUUID(), identifier);

                //Call listeners now that the identifier is loaded
                callLoad(identifier);
            }
        }

        return identifier;
    }

    @Override
    public Identifier loadOrCreate(UUID uuid, String name) {
        Identifier identifier = this.entries.get(uuid);

        if (identifier == null) {
            //Load from database

            try {
                identifier = this.database.loadOrCreate(uuid, name);
            } catch (SQLException e) {
                throw new StorageException("Couldn't loadOrCreate Identifier! [" + uuid + ", " + name + "]", e);
            }

            this.entries.put(uuid, identifier);

            //Call listeners now that the identifier is loaded
            callLoad(identifier);
        }

        return identifier;
    }

    @Override
    public void register(Identifier identifier) {
        this.entries.put(identifier.getUUID(), identifier);
    }

    @Override
    public void loadAll() {
        for (Player player : this.database.getPlugin().getServer().getOnlinePlayers()) {
            loadOrCreate(player.getUniqueId(), player.getName());
        }
    }

    @Override
    public Identifier getIdentifier(int id) {
        return this.entries.values().stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Identifier getIdentifier(UUID uuid) {
        return this.entries.get(uuid);
    }

    @Override
    public Identifier getIdentifier(String name) {
        return this.entries.values().stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public Collection<Identifier> getIdentifiers() {
        return Collections.unmodifiableCollection(this.entries.values());
    }

    @Override
    public Identifier remove(UUID uuid) {
        Identifier identifier = this.entries.remove(uuid);
        if (identifier != null) {
            callUnload(identifier);
        }

        return identifier;
    }

    @Override
    public void unloadAll() {
        this.entries.values().forEach(this::callUnload);
        this.entries.clear();
    }

    @Override
    public void registerListener(String name, IdentifierListener listener) {
        Validate.isFalse(this.listeners.containsKey(name), "There is already a listener registered with the name '" + name + "'");

        this.listeners.put(name, listener);
    }

    @Override
    public IdentifierListener getListener(String name) {
        return this.listeners.get(name);
    }

    @Override
    public void removeListener(String name) {
        this.listeners.remove(name);
    }

    @Override
    public Collection<Map.Entry<String, IdentifierListener>> getListeners() {
        return this.listeners.entrySet();
    }

    private void callLoad(Identifier identifier) {
        for (IdentifierListener listener : this.listeners.values()) {
            try {
                listener.onLoad(identifier);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void callUnload(Identifier identifier) {
        for (IdentifierListener listener : this.listeners.values()) {
            try {
                listener.onUnload(identifier);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void cleanup() {
        Iterator<Identifier> iterator = this.entries.values().iterator();

        while (iterator.hasNext()) {
            Identifier identifier = iterator.next();

            if (identifier.getPlayer() == null) {
                iterator.remove();
                callUnload(identifier);
            }
        }
    }
}
