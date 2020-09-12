package me.matamor.commonapi.modules.exception;

public class InvalidModuleException extends RuntimeException {

    public InvalidModuleException(String message) {
        super(message);
    }

    public InvalidModuleException(String message, Exception exception) {
        super(message, exception);
    }

    public InvalidModuleException(Exception exception) {
        super(exception);
    }
}
