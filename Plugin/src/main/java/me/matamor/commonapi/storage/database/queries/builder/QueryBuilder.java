package me.matamor.commonapi.storage.database.queries.builder;

import me.matamor.commonapi.storage.database.SQLDatabaseManager;

import java.util.List;

public interface QueryBuilder {

    SQLDatabaseManager getDatabaseManager();

    QueryBuilder table(String table);

    QuerySearch where();

    <T> T findOne(QueryLoader<T> loader);

    <T> List<T> findAll(QueryLoader<T> loader);

}