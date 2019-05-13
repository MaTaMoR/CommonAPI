package me.matamor.commonapi.storage.database.queries.builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
public class SimpleQuerySearch implements QuerySearch {

    @Getter
    private final Map<String, Object> values = new LinkedHashMap<>();

    private final QueryBuilder query;

    @Override
    public QueryBuilder query() {
        return this.query;
    }

    @Override
    public QuerySearch eq(String column, Object value) {
        Validate.notNull(column, "Column can't be null!");
        Validate.notNull(value, "Value can't be null!");

        this.values.put(column, value);

        return this;
    }
}
