package me.matamor.commonapi.storage.database;

import me.matamor.commonapi.storage.StorageException;

public class DatabaseException extends StorageException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Exception exception) {
        super(message, exception);
    }
}
