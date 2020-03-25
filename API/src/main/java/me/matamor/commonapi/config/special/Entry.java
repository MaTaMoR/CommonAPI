package me.matamor.commonapi.config.special;

import me.matamor.commonapi.utils.serializer.Serializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Entry {

    String value();

    @Deprecated
    Class<? extends Serializer> serializer() default Serializer.class;

}
