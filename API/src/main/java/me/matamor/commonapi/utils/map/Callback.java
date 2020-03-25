package me.matamor.commonapi.utils.map;

public interface Callback<T> {

    void done(T result, Exception exception);

}
