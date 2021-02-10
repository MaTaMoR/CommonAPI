package me.matamor.commonapi.top;

import me.matamor.commonapi.utils.ULocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TopEntry {

    /**
     * Gets the Top this TopEntry belongs to
     * @return the Top
     */

    @NotNull
    Top getTop();

    /**
     * Gets the Location of the TopEntry
     * @return the Location
     */

    @NotNull
    ULocation getLocation();

    /**
     * Gets the position this TopEntry will show
     * @return the position
     */

    int position();

    /**
     * Gets the current TopValue
     * @return the TopValue
     */

    @Nullable
    TopValue getValue();

    /**
     * Checks if the TopEntry has a TopValue
     * @return true if there is a TopValue
     */

    boolean hasValue();

    /**
     * Sets the TopValue of the TopEntry
     * @param topValue the new TopValue
     */

    void setValue(@Nullable TopValue topValue);

    /**
     * Updates it's physical form with the TopValue
     * this is called automatically when TopEntry.setValue(TopEntry.class) is used!
     */

    void update();

}
