package me.matamor.commonapi;

import com.google.common.base.Charsets;
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
import me.matamor.commonapi.storage.SimpleInstanceProviderManager;
import me.matamor.commonapi.storage.data.DataManager;
import me.matamor.commonapi.storage.data.SimpleDataManager;
import me.matamor.commonapi.storage.database.settings.ConnectionSettingsManager;
import me.matamor.commonapi.storage.entries.DataEntries;
import me.matamor.commonapi.storage.entries.DataStorageManager;
import me.matamor.commonapi.storage.identifier.IdentifierManager;
import me.matamor.commonapi.storage.identifier.IdentifierDatabase;
import me.matamor.commonapi.storage.identifier.SimpleIdentifierDatabase;
import me.matamor.commonapi.storage.identifier.SimpleIdentifierManager;
import me.matamor.commonapi.utils.ULocation;
import me.matamor.commonapi.utils.serializer.SerializationManager;
import me.matamor.commonapi.utils.serializer.TimeSerializer;
import me.matamor.commonapi.utils.serializer.ULocationSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private ServerManager serverManager;

    @Getter
    private BungeeCord bungeeCord;

    @Getter
    private ModuleLoader moduleLoader;

    private Set<Module> modules;

    @Override
    public void onEnable() {
        instance = this;

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


    public class PlayerChangeNamePacket {

        private String player;

        private String name;

        public PlayerChangeNamePacket() {

        }

        public PlayerChangeNamePacket(String player, String name) {
            this.player = player;
            this.name = name;
        }

        public void serialize(ByteArrayOutputStream byteArray) throws IOException {
            //Escribimos los bytes del nombre del jugador

            byte[] playerArray = this.player.getBytes(Charsets.UTF_8);

            byteArray.write(playerArray.length); //Hace falta escribir el tamaño del texto para luego saber cuanto leer
            byteArray.write(playerArray);

            byte[] nameArray = this.player.getBytes(Charsets.UTF_8);

            //Escribimos los bytes del nuevo nombre del jugador
            byteArray.write(nameArray.length);
            byteArray.write(nameArray);
        }

        public void deserialize(ByteArrayInputStream byteArray) throws IOException {
            //Leemos primero el tamaño del texto que esperamos para asi luego cargarlo bien
            int playerLength = byteArray.read();

            byte[] playerArray = new byte[playerLength];
            byteArray.read(playerArray, 0, playerLength);

            this.player = new String(playerArray, Charsets.UTF_8);

            int nameLength = byteArray.read();

            byte[] nameArray = new byte[nameLength];
            byteArray.readNBytes(nameArray, 0, nameLength);

            this.name = new String(nameArray, Charsets.UTF_8);
        }
    }










    
}
