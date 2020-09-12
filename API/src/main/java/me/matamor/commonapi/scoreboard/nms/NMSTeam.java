package me.matamor.commonapi.scoreboard.nms;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface NMSTeam extends NMSViewable {

    /**
     * Gets the Objective
     * @return the Objective, can't be null!
     */

    @NotNull
    NMSScoreboard getScoreboard();

    /**
     * Gets the Name
     * @return the Name, can't be null!
     */

    @NotNull
    String getName();

    /**
     * Gets the DisplayName
     * @return the DisplayName to be set, can't be null!
     */

    String getDisplayName();

    /**
     * Gets the DisplayName
     * @param displayName the DisplayName to be set, can't be null!
     */

    void setDisplayName(@NotNull String displayName);

    /**
     * Gets the Prefix
     * @return the Prefix, can't be null!
     */

    @NotNull
    String getPrefix();

    /**
     * Sets the Prefix
     * @param prefix the Prefix to be set, can't be null!
     */

    void setPrefix(@NotNull String prefix);

    /**
     * Gets the Suffix
     * @return the Suffix, can't be null!
     */

    @NotNull
    String getSuffix();

    /**
     * Sets the Suffix
     * @param suffix the Suffix to be set, can't be null!
     */

    void setSuffix(@NotNull String suffix);

    /**
     * Checks if FriendlyFire is allowed
     * @return true if FriendlyFire is allowed!
     */

    boolean allowFriendlyFire();

    /**
     * Sets the FriendlyFire
     * @param friendlyFire the FriendlyFire to be set!
     */

    void setAllowFriendlyFire(boolean friendlyFire);

    /**
     * Adds an Entry, it may be a Player or just a String!
     * @param entry the Entry to be added, can't be null!
     */

    void addEntry(@NotNull String entry);

    /**
     * Removes an Entry, it may be a Player or just a String!
     * @param entry the Entry to be removed, can't be null!
     */

    void removeEntry(@NotNull String entry);

    /**
     * Gets the Entries, may contain both Players and Strings!
     * @return a Collection of the Entries, can't be null!
     */

    @NotNull
    Collection<String> getEntries();

}
