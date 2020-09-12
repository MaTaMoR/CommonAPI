package me.matamor.commonapi.utils.replacement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TextVariable<T> {

    /*

        Returns the input text with the variable replaced using the value to replace it!!

     */

    String replace(@NotNull String text, @Nullable T value);

}
