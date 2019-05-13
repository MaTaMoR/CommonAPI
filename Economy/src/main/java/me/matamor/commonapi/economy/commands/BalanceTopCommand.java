package me.matamor.commonapi.economy.commands;

import com.google.common.util.concurrent.AtomicDouble;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.matamor.commonapi.commands.CommandArgs;
import me.matamor.commonapi.commands.ICommand;
import me.matamor.commonapi.commands.ICommandException;
import me.matamor.commonapi.economy.EconomyModule;
import me.matamor.commonapi.permissions.Permissions;
import me.matamor.commonapi.utils.CastUtils;
import me.matamor.commonapi.utils.Validate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

public class BalanceTopCommand extends ICommand<EconomyModule> {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("hh:mm:ss yyyy-mm-dd");

    private final BalanceTop balanceTop;

    public BalanceTopCommand(EconomyModule plugin) {
        super(plugin, "balancetop", new String[] { "baltop" });

        this.balanceTop = new BalanceTop(plugin);
    }

    @Override
    public void onCommand(CommandArgs commandArgs) throws ICommandException {
        if (commandArgs.length == 1 && commandArgs.getString(0).equalsIgnoreCase("force")) {
            ifFalse(Permissions.BALANCE_COMMAND_TOP_FORCE.hasPermission(commandArgs.getSender()), "&cNo tienes permisos para actualizar el top!");
            ifTrue(this.balanceTop.getUpdating().get(), "&cEl top ya se esta actualizando!");

            this.balanceTop.update(commandArgs);
        } else {
            ifTrue(this.balanceTop.getUpdating().get(), "&cEl top se esta actualizando!");

            if (this.balanceTop.expired()) {
                this.balanceTop.update(commandArgs);
            } else {
                int page = 1;

                if (commandArgs.length == 1) {
                    try {
                        page = commandArgs.getInt(0);
                    } catch (CastUtils.FormatException e) {
                        throw new ICommandException("&cPagina invalida: &4" + commandArgs.getString(0));
                    }
                }

                ifTrue(1 > page, "&cPagina invalida: &4" + page);

                this.balanceTop.sendTop(page - 1, commandArgs);
            }
        }
    }

    @RequiredArgsConstructor
    public static class BalanceTop {

        @Getter
        private final EconomyModule plugin;

        @Getter
        private final List<List<String>> topPages = Collections.synchronizedList(new ArrayList<>());

        @Getter
        private final AtomicDouble totalBalance = new AtomicDouble(0);

        @Getter
        private final AtomicLong lastUpdate = new AtomicLong(0);

        @Getter
        private final AtomicBoolean updating = new AtomicBoolean(false);

        public boolean expired() {
            long lastUpdate = this.lastUpdate.get();
            return lastUpdate == 0 || (System.currentTimeMillis() - lastUpdate) >= this.plugin.getPluginConfig().topDuration;
        }

        public int totalPages() {
            return this.topPages.size();
        }

        public void clear() {
            this.topPages.clear();
            this.totalBalance.set(0);
            this.lastUpdate.set(0);
        }

        public void update(CommandArgs commandArgs) {
            Validate.isFalse(this.updating.get(), "Top is already being updated!");

            //Set updating to lock for future calls
            this.updating.set(true);

            //Clear old Top
            clear();

            int totalEntries = this.plugin.getDatabase().countEntries();

            commandArgs.sendMessage("&eCreando top de economia de &6" + totalEntries + "&e usuarios...");

            //Set top update date
            this.lastUpdate.set(System.currentTimeMillis());

            //Load new update
            this.plugin.getDatabase().loadTopBalanceAsync(this::addEntry, (result, exception) -> {
                this.updating.set(false);

                if (result) {
                    //Send top to commandSender if online
                    sendTop(0, commandArgs);
                } else {
                    getPlugin().getLogger().log(Level.INFO, "No se pudo actualizar el top de economia!", exception);
                }
            });
        }

        public void sendTop(int pagePosition, CommandArgs commandSender) {
            //Send header
            this.plugin.getPluginConfig().topHeaderFormat.forEach(message -> commandSender.sendMessage(message
                    .replace("{date}", DATE_FORMAT.format(this.lastUpdate.get()))
                    .replace("{page}", String.valueOf(pagePosition + 1))
                    .replace("{total_pages}", String.valueOf(this.topPages.size()))));

            if (pagePosition == 0) {
                commandSender.sendMessage(this.plugin.getPluginConfig().topTotalFormat
                    .replace("{total}", this.plugin.formatMoney(this.totalBalance.get())));
            }

            //Send page
            List<String> page;

            if (this.topPages.isEmpty()) {
                page = Collections.emptyList();
            } else if (pagePosition >= this.topPages.size()) {
                page = this.topPages.get(this.topPages.size() - 1);
            } else {
                page = this.topPages.get(pagePosition);
            }

            page.forEach(commandSender::sendMessage);

            //Send foot
        }

        private void addEntry(BalanceTopEntry balanceTopEntry) {
            //Add balance
            this.totalBalance.addAndGet(balanceTopEntry.getBalance());

            List<String> page;

            //Get current page
            if (this.topPages.isEmpty()) {
                this.topPages.add((page = new ArrayList<>()));
            } else {
                page = this.topPages.get(this.topPages.size() - 1);
            }

            //Add entry to page
            page.add(this.plugin.getPluginConfig().topEntryFormat
                    .replace("{position}", String.valueOf(balanceTopEntry.getPosition()))
                    .replace("{name}", balanceTopEntry.getName())
                    .replace("{balance}", this.plugin.formatMoney(balanceTopEntry.getBalance())));

            //Add new page
            if (page.size() == this.plugin.getPluginConfig().topPageSize) {
                this.topPages.add(new ArrayList<>());
            }
        }
    }

    @AllArgsConstructor
    public static class BalanceTopEntry {

        @Getter
        private final int position;

        @Getter
        private final String name;

        @Getter
        private final double balance;

    }
}
