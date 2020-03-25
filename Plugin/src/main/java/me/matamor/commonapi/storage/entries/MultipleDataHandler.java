package me.matamor.commonapi.storage.entries;

import me.matamor.commonapi.storage.DataHandler;
import me.matamor.commonapi.storage.DataProvider;
import me.matamor.commonapi.storage.identifier.SimpleIdentifier;
import me.matamor.commonapi.utils.map.Callback;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface MultipleDataHandler {

    DataHandler getHandler();

    SimpleIdentifier getIdentifier();

    UUID getUUID();

    Player getPlayer();

    //Register Entries with a Storage like a ConnectionHandler

    <T extends DataEntry> T registerData(DataProvider<T> dataProvider);

    default <T extends DataEntry> void registerDataAsync(DataProvider<T> dataProvider, final Callback<T> callback) {
        getHandler().getPlugin().getServer().getScheduler().runTaskAsynchronously(getHandler().getPlugin(), () -> {
            T dataEntry = registerData(dataProvider);

            if (callback != null) {
                callback.done(dataEntry, null);
            }
        });
    }

    //Register Instances, just to handle the instance in the PlayerData, nothing complex

    Object registerInstance(Class<?> clazz, Object instance);

    //Set data

    <T> T setStoredData(String key, T data);

    //Check if the PlayerData contains the DataValue of the provided class

    boolean hasData(Class<?> clazz);

    //Check if the PlayerData contains the Instance of the provided class

    boolean hasInstance(Class<?> clazz);

    //Check if the PlayerData contains a Stored object with the provided key

    boolean hasStoredData(String key);

    //Get the DataValue registered of the provided class

    <T extends DataEntry> T getData(Class<T> clazz);

    //Get the Instance of the provided class

    <T> T getInstance(Class<T> instance);

    //Get stored data

    Object getStoredData(String key);

    //Get stored data casted to provided class

    <T> T getStoredData(String key, Class<T> clazz);

    //Get the RegisteredData of the provided class

    RegisteredData getRegisteredData(Class<?> clazz);

    //Unregister the DataValue of the provided class

    void unregister(Class<?> clazz);

    default void unregisterAsync(Class<?> clazz) {
        getHandler().getPlugin().getServer().getScheduler().runTaskAsynchronously(getHandler().getPlugin(), () -> unregister(clazz));
    }

    //Unregister the Instance of the provided class

    void unregisterInstance(Class<?> clazz);

    //Remove stored data

    void removeStoredData(String key);

    //Removes the DataValue of the provided class

    void removeData(Class<?> clazz);

    //Removes the DataValue of the provided class, also purges it stored data

    void purgeData(Class<?> clazz);

    default void purgeDataAsync(Class<?> clazz) {
        getHandler().getPlugin().getServer().getScheduler().runTaskAsynchronously(getHandler().getPlugin(), () -> purgeData(clazz));
    }

    //Saves the data of every DataValue registered

    void saveData();

    void saveDataAsync();

}
