package me.matamor.commonapi.requirements;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class SimpleRequirement implements Requirement {

    @Getter
    private final String requirementMessage;

}
