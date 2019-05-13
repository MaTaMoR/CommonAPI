package me.matamor.commonapi.config.special;

public class ConfigurationException extends RuntimeException {

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Exception exception) {
        super(message, exception);
    }
}
