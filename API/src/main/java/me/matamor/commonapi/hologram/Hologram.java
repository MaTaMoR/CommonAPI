package me.matamor.commonapi.hologram;

import me.matamor.commonapi.hologram.lines.HologramLine;
import me.matamor.commonapi.hologram.lines.ItemLine;
import me.matamor.commonapi.hologram.lines.TextLine;
import me.matamor.commonapi.hologram.lines.updating.Animation;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public interface Hologram {

    Location getLocation();

    World getWorld();

    double getX();

    double getY();

    double getZ();

    void teleport(Location location);

    void teleport(World world, double x, double y, double z);

    TextLine addTextLine(String text);

    TextLine addTextLine(int index, String text);

    TextLine addUpdatingTextLine(Animation<String> animation);

    TextLine addUpdatingTextLine(long delay, Animation<String> animation);

    TextLine addUpdatingTextLine(int index, Animation<String> animation);

    TextLine addUpdatingTextLine(int index, long delay, Animation<String> animation);

    ItemLine addItemLine(ItemStack item);

    ItemLine addItemLine(int index, ItemStack item);

    ItemLine addUpdatingItemLine(Animation<ItemStack> animation);

    ItemLine addUpdatingItemLine(long delay, Animation<ItemStack> animation);

    ItemLine addUpdatingItemLine(int index, Animation<ItemStack> animation);

    ItemLine addUpdatingItemLine(int index, long delay, Animation<ItemStack> animation);

    HologramLine getLine(int index);

    void removeLine(int index);

    int size();

    double getHeight();

    void hide();

    void clearLines();

    void delete();

    boolean isDeleted();

}
