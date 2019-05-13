package me.matamor.commonapi.storage.database.queries;

import me.matamor.commonapi.storage.database.DatabaseManager;
import me.matamor.commonapi.utils.BasicLoadable;

public interface Queries extends BasicLoadable {

    DatabaseManager getDatabaseManager();

    void checkFile();

    String getCreate();

    String getInsert();

    String getSelect();

    String getDelete();

    String getCreate(String key);

    String getInsert(String key);

    String getSelect(String key);

    String getDelete(String key);

    String getQuery(String path);

}
