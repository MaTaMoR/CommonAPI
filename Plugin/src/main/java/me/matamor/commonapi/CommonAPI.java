package me.matamor.commonapi;

import lombok.Getter;
import me.matamor.commonapi.defaults.MainCommand;
import me.matamor.commonapi.listeners.DataListener;
import me.matamor.commonapi.modules.Module;
import me.matamor.commonapi.modules.java.JavaModuleLoader;
import me.matamor.commonapi.modules.java.SimpleModuleManager;
import me.matamor.commonapi.storage.DataHandler;
import me.matamor.commonapi.storage.InstanceProviderManager;
import me.matamor.commonapi.storage.SimpleInstanceProviderManager;
import me.matamor.commonapi.storage.data.DataManager;
import me.matamor.commonapi.storage.data.SimpleDataManager;
import me.matamor.commonapi.storage.database.settings.ConnectionSettingsManager;
import me.matamor.commonapi.storage.entries.DataEntries;
import me.matamor.commonapi.storage.entries.DataStorageManager;
import me.matamor.commonapi.storage.identifier.IdentifierDatabase;
import me.matamor.commonapi.storage.identifier.IdentifierManager;
import me.matamor.commonapi.storage.identifier.SimpleIdentifierDatabase;
import me.matamor.commonapi.storage.identifier.SimpleIdentifierManager;
import me.matamor.commonapi.utils.Pair;
import me.matamor.commonapi.utils.Reflections;
import me.matamor.commonapi.utils.StringUtils;
import me.matamor.commonapi.utils.ULocation;
import me.matamor.commonapi.utils.serializer.SerializationManager;
import me.matamor.commonapi.utils.serializer.TimeSerializer;
import me.matamor.commonapi.utils.serializer.ULocationSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public class CommonAPI extends JavaPlugin implements DataHandler {

    private final AtomicBoolean loaded = new AtomicBoolean(false);

    @Getter
    private static CommonAPI instance;

    @Getter
    private ConnectionSettingsManager connectionSettingsManager;

    @Getter
    private InstanceProviderManager instanceProviderManager;

    @Getter
    private DataEntries dataEntries;

    @Getter
    private DataStorageManager storageManager;

    @Getter
    private IdentifierDatabase identifierDatabase;

    @Getter
    private IdentifierManager identifierManager;

    @Getter
    private DataManager dataManager;

    @Getter
    private SimpleModuleManager moduleManager;

    @Override
    public void onEnable() {
        instance = this;

        CommonAPIPlugin.setPlugin(this);

        getLogger().log(Level.INFO, "Registering default serializers...");

        registerSerializers();

        //Load config and the config converter!

        getLogger().log(Level.INFO, "Registering data listener...");

        registerEvents(new DataListener(this));

        getLogger().log(Level.INFO, "Initializing data storage...");

        //Initialize the data!
        initializeData();

        //Load all the PlayerData!
        this.dataManager.loadAll();

        MainCommand mainCommand = new MainCommand(this);
        mainCommand.register();

        getLogger().log(Level.INFO, "Initializing Modules...");

        initializeModules();

        //This is done to prevent players from joining the server when it's not completely loaded!
        getServer().getScheduler().scheduleSyncDelayedTask(this, () -> getServer().getScheduler().runTaskLater(this, () -> this.loaded.set(true), 20));
    }

    @Override
    public void onDisable() {
        instance = null;
        CommonAPIPlugin.removePlugin();

        if (this.moduleManager != null) {
            this.moduleManager.disableModules();
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
        this.instanceProviderManager = new SimpleInstanceProviderManager(this);

        this.dataEntries = new DataEntries(this);
        this.storageManager = new DataStorageManager();

        //Register storage
        this.identifierDatabase = new SimpleIdentifierDatabase(this, true);
        this.storageManager.registerStorage(this.identifierDatabase);

        //Load all the Identifiers from online players!
        this.identifierManager = new SimpleIdentifierManager(this.identifierDatabase);
        this.identifierManager.loadAll();

        //Initialize the data manager but don't loadOrCreate it yet!
        this.dataManager = new SimpleDataManager(this);
    }

    private void registerSerializers() {
        SerializationManager.getInstance().register(Long.class, new TimeSerializer());
        SerializationManager.getInstance().register(long.class, new TimeSerializer());
        SerializationManager.getInstance().register(ULocation.class, new ULocationSerializer());
    }

    private void initializeModules() {
        try {
            this.moduleManager = new SimpleModuleManager(getServer());
            this.moduleManager.registerInterface(JavaModuleLoader.class);

            File folder = new File(getDataFolder(), "Modules");
            if (folder.exists()) {
                Module[] loadedModules = this.moduleManager.loadModules(folder);

                if (loadedModules.length > 0) {
                    //This part is a try to replace the map into something that will use the Modules class loaders aswell
                    Reflections.FieldAccessor<Map> fieldAccessor = Reflections.getField(getClassLoader().getClass(), "classes", Map.class);
                    Map<String, Class<?>> newClasses = new ConcurrentHashMap<String, Class<?>>() {

                        @Override
                        public Class<?> get(Object key) {
                            Class<?> clazz = super.get(key);

                            if (clazz == null && key instanceof String) {
                                clazz = getModuleManager().findClass((String) key);
                            }

                            return clazz;
                        }
                    };

                    Map<?, ?> map = fieldAccessor.get(getClassLoader());
                    map.entrySet().stream()
                            .filter(e -> e.getKey() instanceof String && e.getValue() instanceof Class)
                            .map(e -> new Pair<String, Class<?>>((String) e.getKey(), (Class<?>) e.getValue()))
                            .forEach(e -> newClasses.put(e.getKey(), e.getValue()));

                    fieldAccessor.set(getClassLoader(), newClasses);
                }

                getLogger().log(Level.INFO, "[ModuleManager] Modules loaded: " + StringUtils.toString(Module::getName, loadedModules));

                for (Module module : loadedModules) {
                    try {
                        this.moduleManager.enableModule(module);

                        getLogger().log(Level.INFO, "[ModuleManager] Enabled '" + module.getName() + "' module!");
                    } catch (Exception e) {
                        getLogger().log(Level.SEVERE, "[ModuleManager] Couldn't enable '" + module.getName() + "' module!");
                    }
                }
            } else {
                getLogger().log(Level.INFO, "[ModuleManager] Modules folder is missing!");
            }
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "[ModuleManager] There was an error while loading the modules:", e);
        }
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }
}
