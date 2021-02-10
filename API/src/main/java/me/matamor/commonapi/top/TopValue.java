package me.matamor.commonapi.top;

import org.jetbrains.annotations.NotNull;

public interface TopValue {

    /**
     * Gets the ID of the TopEntry
     * @return if it's a player TopEntry this will be the Name
     */

    @NotNull
    String getId();

    /**
     * Gets the position of the TopEntry
     * @return the position
     */

    int getPosition();

    /***
     * Gets the value of the TopEntry
     * @return it usually is an Integer or Double
     */

    @NotNull
    String getValue();

}
