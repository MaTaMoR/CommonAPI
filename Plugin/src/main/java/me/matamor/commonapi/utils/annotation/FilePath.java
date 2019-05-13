package me.matamor.commonapi.utils.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FilePath {

    String value();

    String folder() default "";

}
