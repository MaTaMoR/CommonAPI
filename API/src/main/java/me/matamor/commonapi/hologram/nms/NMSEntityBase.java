package me.matamor.commonapi.hologram.nms;

public interface NMSEntityBase {

    // Sets if the entity should tick or not.
    void setLockTick(boolean lock);

    // Sets the location through NMS.
    void setLocationNMS(double x, double y, double z);

    // Returns if the entity is dead through NMS.
    boolean isDeadNMS();

    // Kills the entity through NMS.
    void killEntityNMS();

    // The entity ID.
    int getIdNMS();

    // Returns the bukkit entity.
    org.bukkit.entity.Entity getBukkitEntityNMS();

}
