package me.matamor.commonapi.storage.database.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.utils.PrimitiveUtils;

@AllArgsConstructor
public enum ConnectionSettingsType {

    TYPE(String.class, "Type"),
    IP(String.class, "IP"),
    PORT(Integer.class, "Port"),
    DATABASE(String.class, "Database"),
    USERNAME(String.class, "Username"),
    PASSWORD(String.class, "Password");

    @Getter
    private final Class<?> targetClass;

    @Getter
    private final String key;

    public boolean isValid(Object object) {
        return PrimitiveUtils.isInstance(this.targetClass, object);
    }
}
