package me.matamor.commonapi.utils;

import me.matamor.commonapi.config.IConfig;

public interface BasicLoadable {

    IConfig getConfig();

    void load();

}
