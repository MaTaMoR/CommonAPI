package me.matamor.commonapi.storage.identifier.listener;

import me.matamor.commonapi.storage.identifier.Identifier;

public interface IdentifierListener {

    void onLoad(Identifier identifier);

    void onUnload(Identifier identifier);

}
