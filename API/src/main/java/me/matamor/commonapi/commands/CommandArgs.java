package me.matamor.commonapi.commands;

import lombok.Getter;
import me.matamor.commonapi.utils.CastUtils;
import me.matamor.commonapi.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.IntStream;

public class CommandArgs {

    public final int length;

    @Getter
    private final ICommand<?> command;

    @Getter
    private final CommandSender sender;

    @Getter
    private final String label;

    @Getter
    private final String[] args;

    public CommandArgs(ICommand<?> command, CommandSender sender, String label, String[] args) {
        this.command = command;
        this.sender = sender;
        this.label = label;
        this.args = args;
        this.length = args.length;
    }

    public boolean isPlayer() {
        return this.sender instanceof Player;
    }

    public Player getPlayer() {
        return (Player) this.sender;
    }

    public void sendMessage(String message) {
        StringUtils.sendMessage(this.sender, StringUtils.color(message));
    }

    public void sendMessage(String message, Object... objects) {
        sendMessage(String.format(message, objects));
    }

    public void sendMessage(CommandSender commandSender, String message) {
        commandSender.sendMessage(StringUtils.color(message));
    }

    public void sendMessage(CommandSender commandSender, String message, Object... objects) {
        sendMessage(commandSender, String.format(message, objects));
    }

    public int getInt(int position) {
        return CastUtils.asInt(getString(position));
    }

    public double getDouble(int position) {
        return CastUtils.asDouble(getString(position));
    }

    public boolean getBoolean(int position) {
        return CastUtils.asBoolean(getString(position));
    }

    public String getString(int position) {
        if (position > this.length) {
            return null;
        }

        return this.args[position];
    }

    public Player getPlayer(int position) {
        String string = getString(position);
        if (string == null) return null;

        return Bukkit.getPlayer(string);
    }

    public Player getPlayerExact(int position) {
        String string = getString(position);
        if (string == null) return null;

        return Bukkit.getPlayerExact(string);
    }

    public String join() {
        return join(" ", 0);
    }

    public String join(String split, int start) {
        StringBuilder stringBuilder = new StringBuilder();

        IntStream.range(start, this.length).forEach(i -> stringBuilder.append(this.args[i]).append(split));

        return stringBuilder.toString().trim();
    }

    public String getLabel() {
        String arguments = this.command.getArguments();
        return this.label + (arguments.isEmpty() ? "" : " " + arguments);
    }
}
