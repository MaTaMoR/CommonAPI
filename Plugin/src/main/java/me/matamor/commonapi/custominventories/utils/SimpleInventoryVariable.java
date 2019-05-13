package me.matamor.commonapi.custominventories.utils;

import lombok.Getter;
import me.matamor.commonapi.utils.Validate;

public abstract class SimpleInventoryVariable implements InventoryVariable {

    @Getter
    private final String variable;

    public SimpleInventoryVariable(String variable) {
        Validate.notNull(variable, "InventoryVariable can't be null!");

        this.variable = variable;
    }
}
