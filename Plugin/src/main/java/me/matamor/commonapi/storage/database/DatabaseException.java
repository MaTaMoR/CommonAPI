package me.matamor.commonapi.storage.database;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Exception exception) {
        super(message, exception);
    }
}
