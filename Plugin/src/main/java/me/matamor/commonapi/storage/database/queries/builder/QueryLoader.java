package me.matamor.commonapi.storage.database.queries.builder;

import me.matamor.commonapi.storage.database.DatabaseException;

import java.sql.ResultSet;

public interface QueryLoader<T> {

    T deserialize(ResultSet resultSet) throws DatabaseException;

}
