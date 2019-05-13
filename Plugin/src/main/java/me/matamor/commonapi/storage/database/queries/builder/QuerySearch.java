package me.matamor.commonapi.storage.database.queries.builder;

import java.util.Map;

public interface QuerySearch {

    QueryBuilder query();

    QuerySearch eq(String column, Object value);

    Map<String, Object> getValues();

}
