package me.matamor.commonapi.utils.map;

public interface ExpirationListener<T> {

    boolean onExpiration(T value);

}
