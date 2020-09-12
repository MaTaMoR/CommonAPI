package me.matamor.commonapi.utils.replacement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public abstract class SimplePlayerTextVariable implements PlayerTextVariable {

    public abstract String getReplacement(@Nullable Player player);

    @Getter
    private final String variable;

    @Override
    public String replace(@NotNull String text, @Nullable Player value) {
        String replacement = getReplacement(value);
        return (replacement == null ? text : text.replace(this.variable, replacement));
    }
}
