package me.matamor.commonapi.permissions;

public class Permissions extends IPermissionManager {

    public static final IPermission ALL = register(new IPermission("commonapi.*"));

    public static final IPermission COMMAND_ALL = register(new IPermission("commonapi.commands.*", ALL));

    public static final IPermission ECONOMY_COMMAND_ALL = register(new IPermission("commonapi.commands.economy.*", COMMAND_ALL));
    public static final IPermission BALANCE_COMMAND_BALANCE_OTHER = register(new IPermission("commonapi.commands.economy.balance.other", ECONOMY_COMMAND_ALL));
    public static final IPermission BALANCE_COMMAND_PAY = register(new IPermission("commonapi.commands.economy.pay", ECONOMY_COMMAND_ALL));
    public static final IPermission BALANCE_COMMAND_ECO = register(new IPermission("commonapi.commands.economy.eco", ECONOMY_COMMAND_ALL));
    public static final IPermission BALANCE_COMMAND_TOP = register(new IPermission("commonapi.commands.economy.top", ECONOMY_COMMAND_ALL));
    public static final IPermission BALANCE_COMMAND_TOP_FORCE = register(new IPermission("commonapi.commands.economy.top.force", ECONOMY_COMMAND_ALL));


}