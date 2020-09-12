package me.matamor.commonapi.inventory.item;

import me.matamor.commonapi.inventory.amount.Amount;
import me.matamor.commonapi.utils.replacement.PlayerTextVariable;
import me.matamor.commonapi.utils.skull.SkullData;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface CustomItem extends Cloneable, ConfigurationSerializable {

    /**
     * Gets the Material
     * @return the Material of the CustomItem
     */

    @NotNull
    Material getMaterial();

    /**
     * Sets the Material of the CustomItem
     * @param material is the target Material, can't be null!
     */

    void setMaterial(@NotNull Material material);

    /**
     * Gets the DataValue
     * @return the DataValue of the CustomItem
     */

    short getDataValue();

    /**
     * Sets the DataValue of the CustomItem
     * @param dataValue the target DataValue to be set!
     */

    void setDataValue(short dataValue);

    @NotNull
    Amount getAmount();

    /**
     * Sets the Amount of the CustomItem
     * @param amount is the target Amount, can't be null!
     */


    void setAmount(@NotNull Amount amount);

    /**
     * Check if the CustomItem has a Name set
     * @return true if has a Name set!
     */

    boolean hasName();

    /**
     * Gets the Name
     * @return the name of the CustomItem, can be null!
     */

    @Nullable
    String getName();

    /**
     * Sets the Name of the CustomItem
     * @param name the target Name to be set, can be null!
     */

    void setName(@Nullable String name);

    /**
     * Sets the Lore of the CustomItem
     * @param lore is the target lore, can't be null!
     */

    void setLore(@NotNull String... lore);

    /**
     * Sets the Lore of the CustomItem
     * @param lore is the target lore, can't be null!
     */

    void setLore(@NotNull List<String> lore);

    /**
     * Check is the CustomItem has a Lore set
     * @return true if there is a Lore set
     */

    boolean hasLore();

    /**
     * Gets the Lore
     * @return the Lore of the CustomItem, can't be null!
     */

    @NotNull
    List<String> getLore();

    /**
     * Clears the Lore
     */

    void clearLore();

    /**
     * Sets the Enchantments of the CustomItem
     * @param enchantments the enchantments to be set
     */

    void setEnchantments(@NotNull Map<Enchantment, Integer> enchantments);

    /**
     * Checks if the CustomItem has any Enchantment
     * @return true if has any Enchantment
     */

    boolean hasEnchantments();

    /**
     * Adds an Enchantment
     * @param enchantment the target Enchantment to be added, can't be null, default level is 1!
     */

    void addEnchantment(@NotNull Enchantment enchantment);

    /**
     * Adds an Enchantment
     * @param enchantment the target Enchantment to be added, can't be null!
     * @param level the target Level for the target Enchantment, can't be lower than 1!
     */

    void addEnchantment(@NotNull Enchantment enchantment, int level);

    /**
     * Removes an Enchantment
     * @param enchantment the target Enchantment to be removed, can't be null!
     */

    void removeEnchantment(@NotNull Enchantment enchantment);

    /**
     * Gets the Level of an Enchantment
     * @param enchantment the target Enchantment, can't be null!
     * @return the level of the target Enchantment, if there isn't one set, returns -1!
     */

    int getEnchantment(@NotNull Enchantment enchantment);

    /**
     * Returns the Enchantments of the CustomItem
     * @return a map of the enchantments, can't be null!
     */

    @NotNull
    Map<Enchantment, Integer> getEnchantments();

    /**
     * Clears the Enchantments of the CustomItem
     */

    void clearEnchantments();

    /**
     * Sets the ItemGlow, will only affect the Item if there are no Enchantments set!
     * @param itemGlow the target ItemGlow to be set!
     */

    void setItemGlow(boolean itemGlow);

    /**
     * Gets the ItemGlow
     * @return true if ItemGlow is active!
     */

    boolean isItemGlow();

    /**
     * Sets if the Attributes will be removed
     * @param removeAttributes the target RemoveAttributes to be set!
     */

    void setRemoveAttributes(boolean removeAttributes);

    /**
     * Checks if Attributes will be removed
     * @return true if enabled
     */

    boolean isRemoveAttributes();

    /**
     * Sets if a Potion will be splash, only works on 1.8, with higher versions you need to use
     * @see CustomItem#setMaterial(Material) and set it to Material.SPLASH_POTION
     * @param splash the target Splash to be set!
     */

    void setSplash(boolean splash);

    /**
     * Gets the Splash
     * @return true if Splash is actiave!
     */

    boolean isSplash();

    /**
     * Sets the Color of the CustomItem, only works if the item is Colorable
     * @param color the target Color to be set, can be null!
     */

    void setColor(@Nullable Color color);

    /**
     * Checks if the CustomItem has one Color set
     * @return true if there is a Color set
     */

    boolean hasColor();

    /**
     * Gets the Color
     * @return the Color of the CustomItem, can be null!
     */

    @Nullable
    Color getColor();

    /**
     * Sets the SkullOwner, only works if Material is SKULL_ITEM!
     * @param skullOwner the target SkullOwner to be set!
     */

    void setSkullOwner(SkullData skullOwner);

    /**
     * Sets the SkullOwner of the CustomItem
     * @param name is the target Name of the SkullOwner
     * @param uuid is the target UUID of the SkullOwner
     */

    void setSkullOwner(@NotNull String name, @NotNull UUID uuid);

    /**
     * Checks if the CustomItem has a SkullOwner set
     * @return true if there is a SkullOwner set
     */

    boolean hasSkullData();

    /**
     * Gets the SkullOwner
     * @return the SkullOwner of the CustomItem, can be null!
     */

    @Nullable
    SkullData getSkullOwner();

    /**
     * Sets the FireWork effect, only works if Material is FIREWORK!
     * @param fireworkEffect the target FireWork to be set, can be null!
     */

    void setFireworkEffect(@Nullable FireworkEffect fireworkEffect);

    /**
     * Checks if the CustomItem has a FireworkEffect set!
     * @return true if there is one set!
     */

    boolean hasFireworkEffect();

    /**
     * Gets the FireworkEffect
     * @return the FireworkEffect of the CustomItem, can be null!
     */

    @Nullable
    FireworkEffect getFireworkEffect();

    /**
     * Sets the BasePotionType of the CustomItem
     * @param basePotionType the target BasePotionType to be set, can be null!
     */

    void setBasePotionType(PotionType basePotionType);

    /**
     * Checks if there is Base PotionType set!
     * @return true if there is one set!
     */

    boolean hasBasePotionType();

    /**
     * Gets the BasePotionType
     * @return the BasePotionType of the CustomItem, can be null!
     */

    @Nullable
    PotionType getBasePotionType();


    /**
     * Sets the PotionEffects of the CustomItem
     * @param effects the target PotionEffects to be set, can't be null!
     */

    void setPotionEffects(@NotNull PotionEffect... effects);

    /**
     * Sets the PotionEffects of the CustomItem
     * @param effects the target PotionEffects to be set, can't be null!
     */

    void setPotionEffects(@NotNull List<PotionEffect> effects);

    /**
     * Add a PotionEffect to the CustomItem
     * @param effect the target PotionEffect to be added, can't be null!
     */

    void addPotionEffect(@NotNull PotionEffect effect);

    /**
     * Removes a PotionEffect of the CustomItem
     * @param effect the target PotionEffect to be removed, can't be null!
     */

    void removePotionEffect(@NotNull PotionEffect effect);

    /**
     * Check is the CustomItem has any PotionEffect set
     * @return true if there is any set!
     */

    boolean hasPotionEffects();

    /**
     * Gets the PotionEffects
     * @return returns the PotionEffects of the CustomItem!
     */

    List<PotionEffect> getPotionEffects();

    /**
     * Clears the PotionEffects
     */

    void clearPotionEffects();

    /**
     * Sets the BannerBaseColor of the CustomItem, will only work if Material is BANNER!
     * @param bannerBaseColor the target BannerBaseColor to be set, can be null!
     */

    void setBannerBaseColor(@Nullable DyeColor bannerBaseColor);

    /**
     * Checks if the CustomItem has a Banner base Color set!
     * @return true if has one set!
     */

    boolean hasBannerBaseColor();

    /**
     * Gets the BannerBaseColor
     * @return the BannerBaseColor of the CustomItem, can be null!
     */

    @Nullable
    DyeColor getBannerBaseColor();

    /**
     * Adds a Pattern to the CustomItem
     * @param pattern the target Pattern to be added, can't be null!
     */

    void addBannerPattern(@NotNull Pattern pattern);

    /**
     * Checks if the CustomItem has any Banner Pattern set!
     * @return true if has any set!
     */

    boolean hasBannerPatterns();

    /**
     * Checks if the CustomItem has a specific Banner Pattern set
     * @param pattern the target Banner Pattern to be checked, can't be null!
     * @return true if the CustomItem has the target Banner Pattern!
     */

    boolean hasBannerPattern(@NotNull Pattern pattern);

    /**
     * Sets the Banner Patterns of the CustomItem
     * @param bannerPatterns the target Banner Patterns to be set, can't be null!
     */

    void setBannerPatterns(@NotNull List<Pattern> bannerPatterns);

    /**
     * Returns the Banner Patterns
     * @return the Banner Patterns of the CustomItem!
     */

    List<Pattern> getBannerPatterns();

    /**
     * Clears the Banner Patterns
     */

    void clearBannerPatterns();
    
    /**
     * Adds ItemFlags
     * @param itemFlags the target ItemFlags to be added, can't be null!
     */

    void addItemFlags(@NotNull ItemFlag... itemFlags);

    /**
     * Adds ItemFlags
     * @param itemFlags the target ItemFlags to be added, can't be null!
     */

    void addItemFlags(@NotNull List<ItemFlag> itemFlags);

    /**
     * Set the ItemFlags
     * @param itemFlags the target ItemFlags to be set, can't be null!
     */

    void setItemFlags(@NotNull ItemFlag... itemFlags);

    /**
     * Set the ItemFlags
     * @param itemFlags the target ItemFlags to be set, can't be null!
     */

    void setItemFlags(@NotNull List<ItemFlag> itemFlags);
    
    /**
     * Checks if there is any ItemFlag set!
     * @return true if there is any set!
     */

    boolean hasItemFlags();

    /**
     * Checks if ItemFlags are set
     * @param itemFlags the target ItemFlags to be checked, can't be null!
     * @return true if has all of them!
     */

    boolean hasItemFlags(@NotNull ItemFlag... itemFlags);

    /**
     * Checks if ItemFlags are set
     * @param itemFlags the target ItemFlags to be checked, can't be null!
     * @return true if has all of them!
     */

    boolean hasItemFlags(@NotNull List<ItemFlag> itemFlags);

    /**
     * Removes ItemFlags
     * @param itemFlags the target ItemFlags to be removed, can't be null!
     */

    void removeItemFlags(@NotNull ItemFlag... itemFlags);

    /**
     * Removes ItemFlags
     * @param itemFlags the target ItemFlags to be removed, can't be null!
     */

    void removeItemFlags(@NotNull List<ItemFlag> itemFlags);

    /**
     * Gets the ItemFlags
     * @return the ItemFlags of the CustomItem!
     */

    List<ItemFlag> getItemFlags();

    /**
     * Clears the ItemFlags
     */

    void clearItemFlags();
    

    /**
     * Registers a LocalVariable of the CustomItem
     * @param textVariable is the target LocalVariable, can't be null!
     */

    void registerLocalVariable(@NotNull PlayerTextVariable textVariable);

    /**
     * Unregisters a LocalVariable of the CustomItem
     * @param textVariable is the target LocalVariable, can't be null!
     */

    void unregisterLocalVariable(@NotNull PlayerTextVariable textVariable);

    /**
     * Gets the registered LocalVariables
     * @return the registered LocalVariables
     */

    List<PlayerTextVariable> getLocalVariables();

    /**
     * Checks if there is any registered LocalVariable
     * @return true if there is any registered LocalVariable
     */

    boolean hasLocalVariables();

    /**
     * Clears the LocalVariables
     */

    void clearLocalVariables();

    /**
     * Creates a Builder of the CustomItem
     * @return the Builder of the CustomItem
     */

    CustomItemBuilder builder();

    /**
     * Builds the CustomItem into an ItemStack
     * @return the built ItemStack
     */
    
    @NotNull
    ItemStack build();

    /**
     * Builds the CustomItem into an ItemStack
     * @param player used to replace the Variables
     * @return the built ItemStack
     */
    
    @NotNull
    ItemStack build(Player player);

    /**
     * Clones the CustomItem
     * @return the cloned CustomItem
     */
    
    @NotNull
    CustomItem clone();

    interface CustomItemBuilder {

        CustomItemBuilder setMaterial(Material material);

        CustomItemBuilder setAmount(Amount amount);

        CustomItemBuilder setDataValue(short dataValue);

        CustomItemBuilder setName(String name);

        CustomItemBuilder setLore(String... lore);

        CustomItemBuilder setLore(List<String> lore);

        CustomItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments);

        CustomItemBuilder addEnchantment(Enchantment enchantment);

        CustomItemBuilder addEnchantment(Enchantment enchantment, int level);

        CustomItemBuilder setColor(Color color);

        CustomItemBuilder setSkullOwner(String name, UUID uuid);

        CustomItemBuilder setSkullData(SkullData skullData);

        CustomItemBuilder setFireworkEffect(FireworkEffect fireworkEffect);

        CustomItemBuilder setItemGlow(boolean itemGlow);

        CustomItemBuilder setRemoveAttributes(boolean removeAttributes);

        CustomItemBuilder setSplash(boolean splash);

        CustomItemBuilder setPotionEffects(PotionEffect... effects);

        CustomItemBuilder setPotionEffects(List<PotionEffect> effects);

        @Deprecated
        CustomItemBuilder addPotionEffects(PotionEffect effect);

        @Deprecated
        CustomItemBuilder addPotionEffect(PotionEffect effect);

        CustomItemBuilder registerLocalVariable(PlayerTextVariable inventoryVariable);

        CustomItemBuilder setBannerBaseColor(DyeColor bannerBaseColor);


        CustomItemBuilder addBannerPattern(Pattern pattern);

        CustomItemBuilder setBannerPatterns(List<Pattern> pattern);

        CustomItemBuilder addItemFlags(ItemFlag... itemFlags);

        CustomItemBuilder addItemFlags(List<ItemFlag> itemFlags);

        CustomItemBuilder setItemFlags(ItemFlag... itemFlags);

        CustomItemBuilder setItemFlags(List<ItemFlag> itemFlags);

        CustomItem build();

        ItemStack toItemStack();

        ItemStack toItemStack(Player player);
    }
}
