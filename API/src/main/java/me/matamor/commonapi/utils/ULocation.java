package me.matamor.commonapi.utils;

// Universe Location

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;

@RequiredArgsConstructor
public class ULocation {

    @Getter
    private final String worldName;

    @Getter
    private final double x;

    @Getter
    private final double y;

    @Getter
    private final double z;

    @Getter
    private final float yaw;

    @Getter
    private final float pitch;

    public ULocation(Location location) {
        this(location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public int getBlockX() {
        return locToBlock(this.x);
    }

    public int getBlockY() {
        return locToBlock(this.y);
    }

    public int getBlockZ() {
        return locToBlock(this.z);
    }

    public ULocation add(double x, double y, double z) {
        return new ULocation(this.worldName, this.x + x, this.y + y, this.z + z, this.yaw, this.pitch);
    }

    public ULocation subtract(double x, double y, double z) {
        return new ULocation(this.worldName, this.x - x, this.y - y, this.z - z, this.yaw, this.pitch);
    }

    public ULocation center() {
        return new ULocation(this.worldName, getBlockX() + 0.5, y, getBlockZ() + 0.5, this.yaw, this.pitch);
    }

    /*

        Will return null for the World is also null!

     */

    public Location toLocation() {
        World world = Bukkit.getServer().getWorld(this.worldName);
        if (world == null) {
            return null;
        }

        return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    private static int locToBlock(double loc) {
        return NumberConversions.floor(loc);
    }
}
