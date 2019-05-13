package me.matamor.commonapi.storage.database.queries.builder;

import me.matamor.commonapi.storage.database.DatabaseManager;

import java.util.List;

public interface QueryBuilder {

    DatabaseManager getDatabaseManager();

    QueryBuilder table(String table);

    QuerySearch where();

    <T> T findOne(QueryLoader<T> loader);

    <T> List<T> findAll(QueryLoader<T> loader);

}