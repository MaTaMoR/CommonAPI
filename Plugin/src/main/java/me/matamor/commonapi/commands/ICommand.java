package me.matamor.commonapi.commands;

import lombok.Getter;
import lombok.Setter;
import me.matamor.commonapi.permissions.IPermission;
import me.matamor.commonapi.utils.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ICommand<T extends Plugin> {

    private static final CommandMap COMMANDS = (CommandMap) Reflections.getMethod(Bukkit.getServer().getClass(), "getCommandMap").invoke(Bukkit.getServer());

    public abstract void onCommand(CommandArgs commandArgs) throws ICommandException;

    private final Map<String, ICommand> children = new LinkedHashMap<>();

    @Getter
    private final T plugin;

    @Getter
    private ICommand parent;

    @Getter
    private final String name;

    @Getter
    private final String description;

    @Getter
    private final String[] aliases;

    @Getter @Setter
    private IPermission permission;

    @Getter @Setter
    private String permissionMessage = "&cYou don't have permissions to use this command!";

    @Getter @Setter
    private ICommand helpCommand;

    @Getter @Setter
    private boolean onlyInGame = false;

    @Getter
    private boolean registered = false;

    public ICommand(T plugin, String name, String[] aliases) {
        this(plugin, name, "", aliases);
    }

    public ICommand(T plugin, String name, String description, String[] aliases) {
        this(plugin, name, description, null, aliases);
    }

    public ICommand(T plugin, String name, String description, ICommand parent, String[] aliases) {
        this.plugin = plugin;
        this.name = name;
        this.description = description.toLowerCase();
        this.aliases = aliases;

        if (parent != null) {
            this.parent = parent;
        }
    }

    public String getUsage() {
        if (this.parent == null) {
            return "/" + this.name;
        } else {
            return getParent().getArguments() + " " + getName();
        }
    }

    public String getArguments() {
        return "";
    }

    public boolean hasPermission() {
        return this.permission != null;
    }

    public boolean checkPermission(CommandSender sender) {
        return this.permission == null || this.permission.hasPermission(sender);
    }

    public void setParent(ICommand parent) {
        if (this.parent != null) {
            this.parent.removeChild(getName());
        }

        this.parent = parent;

        if (this.parent != null) {
            this.parent.children.put(getName(), this);
        }
    }

    public void addChild(ICommand... subCommands) {
        for (ICommand subCommand : subCommands) {
            subCommand.setParent(this);
        }
    }

    public ICommand getChild(String childName) {
        for (ICommand child : this.children.values()) {
            if (child.getName().equalsIgnoreCase(childName)) {
                return child;
            }

            for (String alias : child.getAliases()) {
                if (alias.equalsIgnoreCase(childName)) {
                    return child;
                }
            }
        }

        return null;
    }

    public void removeChild(String child) {
        ICommand command = this.children.get(child);
        if (command != null) {
            command.setParent(null);
        }
    }

    protected void ifTrue(boolean value, String message, Object... objects) throws ICommandException {
        if (value) {
            throw new ICommandException(String.format(message, objects));
        }
    }

    protected void ifFalse(boolean value, String message, Object... objects) throws ICommandException {
        if (!value) {
            throw new ICommandException(String.format(message, objects));
        }
    }

    protected void notNull(Object object, String message, Object... objects) throws ICommandException {
        if (object == null) {
            throw new ICommandException(String.format(message, objects));
        }
    }

    protected void throwMessage(String message, Object... objects) throws ICommandException {
        throw new ICommandException(String.format(message, objects));
    }

    public Collection<ICommand> getChildren() {
        return this.children.values();
    }

    private boolean executeCommand(CommandArgs commandArgs) {
        if (!commandArgs.isPlayer() && this.onlyInGame) {
            commandArgs.sendMessage("&cThis command can only be used by a player in-game!");
        } else {
            if (commandArgs.length == 0 || this.children.isEmpty()) {
                if (checkPermission(commandArgs.getSender())) {
                    try {
                        if (this.helpCommand == null) {
                            onCommand(commandArgs);
                        } else {
                            this.helpCommand.onCommand(commandArgs);
                        }
                    } catch (ICommandException e) {
                        commandArgs.sendMessage("&4" + e.getMessage());
                    }
                } else {
                    commandArgs.sendMessage("&4You don't have permission to use this command!");
                }
            } else {
                String childName = commandArgs.getString(0);
                ICommand child = getChild(childName);

                if (child == null) {
                    commandArgs.sendMessage("&cThe sub command &4" + childName + "&c doesn't exist!");
                } else {
                    child.executeCommand(new CommandArgs(commandArgs.getSender(), commandArgs.getLabel() + " " + child.getName(), args(commandArgs.getArgs(), 1)));
                }
            }
        }

        return true;
    }

    public boolean register() {
        boolean result = !this.registered && COMMANDS.register(getName(), new BukkitCommand(getName(), getDescription(), getArguments(), Arrays.asList(getAliases())) {
            @Override
            public boolean execute(CommandSender sender, String label, String[] args) {
                return executeCommand(new CommandArgs(sender, "/" + label, args));
            }
        });

        this.registered = result;
        return result;
    }

    protected String[] args(String[] args, int start) {
        return Arrays.copyOfRange(args, start, args.length);
    }
}
