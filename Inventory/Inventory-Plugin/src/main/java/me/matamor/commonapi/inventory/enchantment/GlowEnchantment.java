package me.matamor.commonapi.inventory.enchantment;

import me.matamor.commonapi.inventory.InventoryModule;
import me.matamor.commonapi.utils.Reflections;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class GlowEnchantment extends EnchantmentWrapper {

    private static Enchantment glow;

    public static Enchantment getGlow() {
        //If the glow is not initialized yet try to do it!
        if (glow == null) {
            try {
                //Access the field and set it to 'true' so we can register new Enchantments!
                Reflections.FieldAccessor<Boolean> field = Reflections.getField(Enchantment.class, "acceptingNew", boolean.class);
                field.set(null, true);

                //Create our GlowEnchantment and register it!
                GlowEnchantment glowEnchantment = new GlowEnchantment("CustomAPIGlow");
                Enchantment.registerEnchantment(glowEnchantment);

                //Assign the GlowEnchantment to the global variable!
                glow = glowEnchantment;

                //Set the field back to false so no new Enchantments can be registered!
                field.set(null, false);
            } catch (Exception e) {
                InventoryModule.getInstance().getLogger().log(Level.SEVERE, "Couldn't register GlowEnchantment!", e);
            }
        }

        return glow;
    }

    public GlowEnchantment(@NotNull String name) {
        super(name);
    }

    @NotNull
    public String getName() {
        return "CustomAPIGlow";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    /** @deprecated */
    @Deprecated
    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment var1) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack var1) {
        return false;
    }

}