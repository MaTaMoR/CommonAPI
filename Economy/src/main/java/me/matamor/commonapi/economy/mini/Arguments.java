package me.matamor.commonapi.economy.mini;

import java.util.*;

public class Arguments {

    private String key;
    private LinkedHashMap<String, String> values;
    private boolean caseSensitive;

    public Arguments(final Object key) {
        this.key = this.parseKey(key);
        this.values = new LinkedHashMap<String, String>();
        this.caseSensitive = false;
    }

    public Arguments(final Object key, final boolean caseSensitive) {
        this.key = this.parseKey(key);
        this.values = new LinkedHashMap<String, String>();
        this.caseSensitive = caseSensitive;
    }

    public String getKey() {
        return this.parseKey(this.key);
    }

    private String encode(final String data) {
        return data.trim().replace(" ", "}+{");
    }

    private String decode(final String data) {
        return data.replace("}+{", " ").trim();
    }

    public boolean hasKey(final Object key) {
        return this.values.containsKey(this.parseKey(key));
    }

    public void setValue(final String key, final Object value) {
        this.values.put(this.encode(this.parseKey(key)), this.encode(String.valueOf(value)));
    }

    public String getValue(final String key) {
        return this.decode(this.values.get(this.parseKey(key)));
    }

    public Integer getInteger(final String key) throws NumberFormatException {
        return Integer.valueOf(this.getValue(key));
    }

    public Double getDouble(final String key) throws NumberFormatException {
        return Double.valueOf(this.getValue(key));
    }

    public Long getLong(final String key) throws NumberFormatException {
        return Long.valueOf(this.getValue(key));
    }

    public Float getFloat(final String key) throws NumberFormatException {
        return Float.valueOf(this.getValue(key));
    }

    public Short getShort(final String key) throws NumberFormatException {
        return Short.valueOf(this.getValue(key));
    }

    public Boolean getBoolean(final String key) {
        return Boolean.valueOf(this.getValue(key));
    }

    public String[] getArray(final String key) {
        final String value = this.getValue(key);
        if (value == null || !value.contains(",")) {
            return null;
        }
        if (value.split(",") == null) {
            return null;
        }
        return this.trim(value.split(","));
    }

    private String[] trim(final String[] values) {
        for (int i = 0, length = values.length; i < length; ++i) {
            if (values[i] != null) {
                values[i] = values[i].trim();
            }
        }
        return values;
    }

    private String parseKey(final Object key) {
        if (this.caseSensitive) {
            return String.valueOf(key);
        }
        return String.valueOf(key).toLowerCase();
    }

    private List trim(final List<String> values) {
        final List<String> trimmed = new ArrayList<>();
        for (int i = 0, length = values.size(); i < length; ++i) {
            String v = values.get(i);
            if (v != null) {
                v = v.trim();
            }
            trimmed.add(v);
        }
        return trimmed;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.key).append(" ");
        for (final String k : this.values.keySet()) {
            sb.append(k).append(":").append(this.values.get(k)).append(" ");
        }
        return sb.toString().trim();
    }

    public Arguments copy() {
        final Arguments copy = new Arguments(this.key);
        for (final String k : this.values.keySet()) {
            copy.values.put(k, this.values.get(k));
        }
        return copy;
    }
}
