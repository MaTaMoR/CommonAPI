package me.matamor.commonapi.storage.database.queries.builder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.matamor.commonapi.storage.database.DatabaseException;
import me.matamor.commonapi.storage.database.SQLDatabaseManager;
import me.matamor.commonapi.utils.Validate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SimpleQueryBuilder implements QueryBuilder {

    @Getter
    private final SQLDatabaseManager databaseManager;

    private String table;

    private QuerySearch search;

    @Override
    public QueryBuilder table(String table) {
        Validate.notNull(table, "Table can't be null!");

        this.table = table;

        return this;
    }

    @Override
    public QuerySearch where() {
        if (this.search == null) {
            this.search = new SimpleQuerySearch(this);
        }

        return this.search;
    }

    @Override
    public <T> T findOne(QueryLoader<T> loader) {
        try {
            try (ResultSet resultSet = search()) {
                if (resultSet.next()) {
                    return loader.deserialize(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't loadOrCreate data!", e);
        }
    }

    @Override
    public <T> List<T> findAll(QueryLoader<T> loader) {
        try {
            try (ResultSet resultSet = search()) {
                List<T> values = new ArrayList<>();

                while (resultSet.next()) {
                    values.add(loader.deserialize(resultSet));
                }

                return values;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't loadOrCreate data!", e);
        }
    }

    private ResultSet search() throws DatabaseException {
        Validate.notNull(this.table, "Table can't be null!");

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("SELECT * FROM ").append(this.table);

        if (this.search != null) {
            for (Map.Entry<String, Object> entry : this.search.getValues().entrySet()) {
                stringBuilder.append(" WHERE ").append(entry.getKey()).append(" = ").append(entry.getValue().toString());
            }
        }

        stringBuilder.append(";");

        try {
            try (Connection connection = this.databaseManager.getConnection()) {
                return connection.prepareStatement(stringBuilder.toString()).executeQuery();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't create statement!", e);
        }
    }
}
