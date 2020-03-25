package me.matamor.commonapi.storage.database.queries;

import me.matamor.commonapi.storage.database.SQLDatabaseManager;
import me.matamor.commonapi.utils.BasicLoadable;

public interface Queries extends BasicLoadable {

    SQLDatabaseManager getDatabaseManager();

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
