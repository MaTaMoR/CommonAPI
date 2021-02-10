package me.matamor.commonapi.top;

import java.util.Map;

public interface Top {

    /**
     * Gets the limit of TopEntries to be updated
     * @return the limit
     */

    int getLimit();

    /**
     * Checks if the Top has a limit
     * @return if limit is -1 will return false!
     */

    boolean hasLimit();

    /**
     * Sets the Top limit
     * @param limit -1 to disable the limit
     */

    void setLimit(int limit);


    /**
     * Gets the delay between each top update
     * @return the delay
     */

    long getDelay();

    /**
     * Sets the delay between each top update
     * @param delay the new delay
     */

    void setDelay(long delay);

    /**
     * Gets the TopValues sorted
     * @return the TopValues
     */

    Map<Integer, TopValue> getValues();

    /**
     * Gets the TopEntries sorted
     * @return the TopEntries
     */

    Map<Integer, TopEntry> getEntries();

    /**
     * fetch the data
     */

    void fetchData();
}
