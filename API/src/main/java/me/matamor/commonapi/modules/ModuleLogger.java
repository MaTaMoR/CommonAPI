package me.matamor.commonapi.modules;

import lombok.Getter;
import me.matamor.commonapi.modules.java.JavaModule;
import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ModuleLogger extends Logger {

    @Getter
    private final String prefix;

    public ModuleLogger(JavaModule context) {
        super(context.getClass().getCanonicalName(), null);

        this.prefix = "[" + context.getDescription().getName() + "]";

        setParent(Bukkit.getServer().getLogger());
        setLevel(Level.ALL);
    }

    @Override
    public void log(LogRecord logRecord) {
        logRecord.setMessage(this.prefix + logRecord.getMessage());
        super.log(logRecord);
    }
}