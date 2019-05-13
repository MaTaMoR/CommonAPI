package me.matamor.commonapi.custominventories.icons;

import lombok.Getter;
import lombok.Setter;
import me.matamor.commonapi.custominventories.actions.ActionHandler;
import me.matamor.commonapi.custominventories.enums.ClickType;
import me.matamor.commonapi.requirements.Requirement;
import me.matamor.commonapi.utils.Validate;

import java.util.*;


public abstract class SimpleInventoryIcon implements InventoryIcon {

    private final Set<ActionHandler> actions = new HashSet<>();

    @Getter
    private final ClickType clickType;

    @Getter @Setter
    private Requirement requirement;

    @Getter
    private String requirementMessage = "&cYou can't use this icon!";

    @Setter
    private boolean visibilityRestricted;

    public SimpleInventoryIcon() {
        this(ClickType.BOTH_CLICKS);
    }

    public SimpleInventoryIcon(ClickType clickType) {
        Validate.notNull(clickType, "ClickType can't be null!");

        this.clickType = clickType;
    }

    @Override
    public boolean hasRequirement() {
        return this.requirement != null;
    }

    @Override
    public void setRequirementMessage(String requirementMessage) {
        Validate.notNull(requirementMessage, "Requirement message can't be null!");

        this.requirementMessage = requirementMessage;
    }

    @Override
    public boolean hasVisibilityRestricted() {
        return this.visibilityRestricted;
    }

    @Override
    public InventoryIcon addClickAction(ActionHandler... actionHandlers) {
        Validate.notNull(actionHandlers, "ActionHandlers can't be null!");

        this.actions.addAll(Arrays.asList(actionHandlers));

        return this;
    }

    @Override
    public void removeClickAction(ActionHandler... actionHandlers) {
        Validate.notNull(actionHandlers, "ActionHandler can't be null!");

        this.actions.removeAll(Arrays.asList(actionHandlers));
    }

    @Override
    public Collection<ActionHandler> getClickActions() {
        return Collections.unmodifiableSet(this.actions);
    }
}
