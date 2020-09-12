package me.matamor.commonapi.economy.mini;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Mini {

    private String folder;
    private String database;
    private String source;
    private boolean changed;
    private boolean caseSensitive;
    private Manager Database;
    private LinkedHashMap<String, Arguments> Indexes;
    private LinkedHashMap<String, Arguments> pushedIndexes;

    public Mini(final String folder, final String database) {
        this.changed = false;
        this.caseSensitive = false;
        this.database = database;
        this.folder = folder;
        this.Database = new Manager(this.folder, this.database, true);
        this.read();
    }

    public Mini(final String folder, final String database, final boolean caseSensitive) {
        this.changed = false;
        this.caseSensitive = false;
        this.caseSensitive = caseSensitive;
        this.database = database;
        this.folder = folder;
        this.Database = new Manager(this.folder, this.database, true);
        this.read();
    }

    public Mini(final File data) {
        this.changed = false;
        this.caseSensitive = false;
        this.database = data.getName();
        this.folder = data.getPath();
        this.Database = new Manager(this.folder, this.database, true);
        this.read();
    }

    public Mini(final File data, final boolean caseSensitive) {
        this.changed = false;
        this.caseSensitive = false;
        this.caseSensitive = caseSensitive;
        this.database = data.getName();
        this.folder = data.getPath();
        this.Database = new Manager(this.folder, this.database, true);
        this.read();
    }

    public static void main(final String[] args) {
        final Mini mini = new Mini(".", "mini");
        final Double amount = mini.getArguments("Nijikokun").getDouble("money");
        mini.setArgument("Nijikokun", "money", amount + 2.0, true);
        System.out.println(mini.getArguments("Nijikokun").getDouble("money"));
        System.out.println(mini.getIndices().toString());
    }

    private String[] trim(final String[] values) {
        for (int i = 0, length = values.length; i < length; ++i) {
            if (values[i] != null) {
                values[i] = values[i].trim();
            }
        }
        return values;
    }

    private void read() {
        this.read(true);
    }

    private void read(final boolean pushed) {
        (this.Database = new Manager(this.folder, this.database, true)).removeDuplicates();
        this.Database.read();
        this.Indexes = new LinkedHashMap<String, Arguments>();
        if (pushed) {
            this.pushedIndexes = new LinkedHashMap<String, Arguments>();
        }
        for (final String line : this.Database.getLines()) {
            if (line.trim().isEmpty()) {
                continue;
            }
            final String[] parsed = this.trim(line.trim().split(" "));
            if (parsed[0].contains(":")) {
                continue;
            }
            if (parsed[0].isEmpty()) {
                continue;
            }
            final Arguments entry = new Arguments(this.parseIndice(parsed[0]));
            for (final String item : parsed) {
                if (item.contains(":")) {
                    final String[] map = this.trim(item.split(":", 2));
                    final String key = map[0];
                    final String value = map[1];
                    if (key != null) {
                        entry.setValue(key, value);
                    }
                }
            }
            this.Indexes.put(this.parseIndice(parsed[0]), entry);
        }
    }

    public boolean hasIndex(final Object index) {
        return this.Indexes.containsKey(this.parseIndice(index));
    }

    public LinkedHashMap<String, Arguments> getIndices() {
        return this.Indexes;
    }

    public void addIndex(final Arguments entry) {
        this.addIndex(entry.getKey(), entry);
    }

    public void addIndex(final Object index, final Arguments entry) {
        this.pushedIndexes.put(this.parseIndice(index), entry);
        this.changed = true;
    }

    public boolean alterIndex(final Object original, final String updated) {
        return this.alterIndex(original, updated, true);
    }

    public boolean alterIndex(final Object original, final String updated, final boolean update) {
        if (!this.hasIndex(original) || this.hasIndex(updated)) {
            return false;
        }
        final Arguments data = this.Indexes.get(this.parseIndice(original));
        this.removeIndex(original);
        this.addIndex(updated, data);
        if (update) {
            this.update();
        }
        return true;
    }

    public void removeIndex(final Object key) {
        this.Database.remove(this.Indexes.get(this.parseIndice(key)).toString());
        this.read(false);
    }

    public Arguments getArguments(final Object key) {
        return this.Indexes.get(this.parseIndice(key));
    }

    public void setArgument(final String index, final Object key, final Object value) {
        this.setArgument(index, key, String.valueOf(value), false);
    }

    public void setArgument(final Object index, final Object key, final String value, final boolean save) {
        if (!this.hasIndex(index)) {
            return;
        }
        this.changed = true;
        final Arguments original = this.Indexes.get(this.parseIndice(index)).copy();
        original.setValue(this.parseIndice(key), value);
        this.pushedIndexes.put(this.parseIndice(index), original);
        if (save) {
            this.update();
        }
    }

    public void setArgument(final Object index, final Object key, final Object value, final boolean save) {
        String formatted = "";
        if (value instanceof int[]) {
            for (final int v : (int[])value) {
                formatted = v + ",";
            }
        }
        else if (value instanceof String[]) {
            for (final String v2 : (String[])value) {
                formatted = v2 + ",";
            }
        }
        else if (value instanceof Double[]) {
            for (final Double v3 : (Double[])value) {
                formatted = v3 + ",";
            }
        }
        else if (value instanceof Boolean[]) {
            for (final Boolean v4 : (Boolean[])value) {
                formatted = v4 + ",";
            }
        }
        else if (value instanceof Long[]) {
            for (final Long v5 : (Long[])value) {
                formatted = v5 + ",";
            }
        }
        else if (value instanceof Float[]) {
            for (final Float v6 : (Float[])value) {
                formatted = v6 + ",";
            }
        }
        else if (value instanceof Byte[]) {
            for (final Byte v7 : (Byte[])value) {
                formatted = v7 + ",";
            }
        }
        else if (value instanceof char[]) {
            for (final char v8 : (char[])value) {
                formatted = v8 + ",";
            }
        }
        else if (value instanceof ArrayList) {
            final ArrayList data = (ArrayList)value;
            for (final Object v9 : data) {
                formatted = v9 + ",";
            }
        }
        if (formatted.length() > 1) {
            formatted.substring(0, formatted.length() - 2);
        }
        else {
            formatted = String.valueOf(value);
        }
        this.setArgument(this.parseIndice(index), this.parseIndice(key), formatted, save);
    }

    public void update() {
        if (!this.changed) {
            return;
        }
        final LinkedList<String> lines = new LinkedList<String>();
        for (final String key : this.pushedIndexes.keySet()) {
            if (this.Indexes.containsKey(key) && !this.Indexes.get(key).toString().equals(this.pushedIndexes.get(key).toString())) {
                this.Database.remove(this.Indexes.get(key).toString());
            }
        }
        this.read(false);
        for (final String key : this.pushedIndexes.keySet()) {
            if (this.Indexes.containsKey(key)) {
                if (this.Indexes.get(key).toString().equals(this.pushedIndexes.get(key).toString())) {
                    continue;
                }
                this.Indexes.put(key, this.pushedIndexes.get(key));
                this.Database.append(this.Indexes.get(key).toString());
            }
            else {
                this.Database.append(this.pushedIndexes.get(key).toString());
            }
        }
        this.pushedIndexes.clear();
        this.read();
    }

    private String parseIndice(final Object key) {
        if (this.caseSensitive) {
            return String.valueOf(key);
        }
        return String.valueOf(key).toLowerCase();
    }
}