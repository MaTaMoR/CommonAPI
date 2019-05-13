package me.matamor.commonapi;

import lombok.Getter;
import me.matamor.commonapi.commands.cmds.defaults.MainCommand;
import me.matamor.commonapi.custominventories.events.InventoryEvents;
import me.matamor.commonapi.custominventories.utils.BungeeCord;
import me.matamor.commonapi.custominventories.utils.Settings;
import me.matamor.commonapi.custominventories.utils.server.ServerManager;
import me.matamor.commonapi.listeners.DataListener;
import me.matamor.commonapi.modules.Module;
import me.matamor.commonapi.modules.ModuleLoader;
import me.matamor.commonapi.modules.ModuleManager;
import me.matamor.commonapi.storage.DataHandler;
import me.matamor.commonapi.storage.InstanceProviderManager;
import me.matamor.commonapi.storage.data.DataManager;
import me.matamor.commonapi.storage.data.SimpleDataManager;
import me.matamor.commonapi.storage.database.settings.ConnectionSettingsManager;
import me.matamor.commonapi.storage.entries.DataEntries;
import me.matamor.commonapi.storage.entries.DataStorageManager;
import me.matamor.commonapi.storage.identifier.IdentifierManager;
import me.matamor.commonapi.storage.identifier.IdentifierSingleDatabaseManager;
import me.matamor.commonapi.storage.identifier.SimpleIdentifierManager;
import me.matamor.commonapi.utils.serializer.SerializationManager;
import me.matamor.commonapi.utils.serializer.TimeSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public class CommonAPI extends JavaPlugin implements DataHandler, ModuleManager {

    private final AtomicBoolean loaded = new AtomicBoolean(false);

    @Getter
    private static CommonAPI instance;

    @Getter
    private SerializationManager serializationManager;

    @Getter
    private ConnectionSettingsManager connectionSettingsManager;

    @Getter
    private InstanceProviderManager instanceProviderManager;

    @Getter
    private DataEntries dataEntries;

    @Getter
    private DataStorageManager storageManager;

    @Getter
    private IdentifierSingleDatabaseManager identifierDatabase;

    @Getter
    private IdentifierManager identifierManager;

    @Getter
    private DataManager dataManager;

    @Getter
    private ServerManager serverManager;

    @Getter
    private BungeeCord bungeeCord;

    @Getter
    private ModuleLoader moduleLoader;

    private Set<Module> modules;

    @Override
    public void onEnable() {
        instance = this;

        this.serializationManager = new SerializationManager();

        registerSerializers();

        //Load config and the config converter!

        registerEvents(new DataListener(this));

        //Initialize the data!
        initializeData();

        //Load all the PlayerData!
        this.dataManager.loadAll();

        //Load inventories API
        initializeInventoryAPI();

        MainCommand mainCommand = new MainCommand(this);
        mainCommand.register();

        initializeModules();

        //This is done to prevent players from joining the server when it's not completely loaded!
        getServer().getScheduler().scheduleSyncDelayedTask(this, () -> getServer().getScheduler().runTaskLater(this, () -> this.loaded.set(true), 20 * 5));
    }

    @Override
    public void onDisable() {
        if (this.moduleLoader != null && this.modules != null) {
            for (Module module : this.modules) {
                try {
                    this.moduleLoader.disableModule(module);
                } catch (Exception e) {
                    getLogger().log(Level.SEVERE, "[ModuleManager] Couldn't disable the module: " + module.getName(), e);
                }
            }
        }

        if (this.serverManager != null) {
            this.serverManager.stop();
            this.serverManager.clear();
        }

        for (Player player : getServer().getOnlinePlayers()) {
            player.closeInventory();
        }

        if (this.dataManager != null) {
            this.dataManager.unloadAll();
        }

        if (this.storageManager != null) {
            this.storageManager.unload();
        }
    }

    @Override
    public CommonAPI getPlugin() {
        return this;
    }

    public boolean isLoaded() {
        return this.loaded.get();
    }

    private void initializeData() {
        //Load connection settings
        this.connectionSettingsManager = new ConnectionSettingsManager(this);
        this.connectionSettingsManager.load();

        //Create data storage
        this.instanceProviderManager = new InstanceProviderManager(this);

        this.dataEntries = new DataEntries(this);
        this.storageManager = new DataStorageManager();

        //Register storage
        this.identifierDatabase = new IdentifierSingleDatabaseManager(this, true);
        this.storageManager.registerStorage(this.identifierDatabase);

        //Load all the Identifiers from online players!
        this.identifierManager = new SimpleIdentifierManager(this.identifierDatabase);
        this.identifierManager.loadAll();

        //Initialize the data manager but don't loadOrCreate it yet!
        this.dataManager = new SimpleDataManager(this);
    }

    private void registerSerializers() {
        this.serializationManager.register(new TimeSerializer());
    }

    private void initializeInventoryAPI() {
        Settings.load(this);

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new InventoryEvents(this), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        bungeeCord = new BungeeCord(this);
        serverManager = new ServerManager(this);
    }

    private void initializeModules() {
        try {
            this.moduleLoader = new ModuleLoader(this);

            Set<Module> modules = new HashSet<>();

            for (Module module : this.moduleLoader.loadModules()) {
                try {
                    this.moduleLoader.enableModule(module);
                    modules.add(module);
                } catch (Exception e) {
                    getLogger().log(Level.SEVERE, "[ModuleManager] Couldn't loadOrCreate the module: " + module.getName(), e);
                }
            }

            this.modules = Collections.unmodifiableSet(modules);
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "[ModuleManager] There was an error while loading the modules:", e);
        }
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public Module getModule(String moduleName) {
        return this.modules.stream().filter(m -> m.getName().equals(moduleName)).findFirst().orElse(null);
    }

    @Override
    public boolean isEnabled(String moduleName) {
        return getModule(moduleName) != null;
    }

    @Override
    public Collection<Module> getModules() {
        return this.modules;
    }

}
