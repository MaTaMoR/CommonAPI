package me.matamor.commonapi.permissions;

import me.matamor.commonapi.utils.Validate;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class IPermissionManager {

    private static final Map<String, IPermission> permissions = new LinkedHashMap<>();

    public static IPermission register(IPermission permission) {
        Validate.notNull(permission, "IPermission can't be null!");
        Validate.isFalse(permissions.containsKey(permission.getPermission()), "The IPermission '" + permission.getPermission() + "' is already registered!");

        permissions.put(permission.getPermission(), permission);

        return permission;
    }

    public static IPermission getPermission(String permission) {
        return permissions.get(permission);
    }

    public static Collection<IPermission> getPermissions() {
        return permissions.values();
    }
}
