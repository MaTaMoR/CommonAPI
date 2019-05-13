package me.matamor.commonapi.storage;

import me.matamor.commonapi.storage.data.PlayerData;

public interface InstanceProvider<T> {

    T newInstance(PlayerData playerData);

}
