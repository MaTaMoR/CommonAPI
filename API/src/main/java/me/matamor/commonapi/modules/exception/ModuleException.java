package me.matamor.commonapi.modules.exception;

public class ModuleException extends RuntimeException {

    public ModuleException(String message) {
        super(message);
    }

    public ModuleException(String message, Exception exception) {
        super(message, exception);
    }

    public ModuleException(Exception exception) {
        super(exception);
    }
}
