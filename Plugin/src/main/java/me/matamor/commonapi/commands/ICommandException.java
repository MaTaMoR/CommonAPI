package me.matamor.commonapi.commands;

public class ICommandException extends Exception {

    public ICommandException(String message) {
        super(message);
    }

    public ICommandException(String message, Exception exception) {
        super(message, exception);
    }
}
