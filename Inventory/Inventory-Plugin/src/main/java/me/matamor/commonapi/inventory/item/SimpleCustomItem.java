package me.matamor.commonapi.inventory.item;

import lombok.Getter;
import lombok.Setter;
import me.matamor.commonapi.inventory.amount.Amount;
import me.matamor.commonapi.inventory.amount.AmountUtil;
import me.matamor.commonapi.inventory.amount.SimpleAmount;
import me.matamor.commonapi.inventory.enchantment.GlowEnchantment;
import me.matamor.commonapi.nms.NMSVersion;
import me.matamor.commonapi.utils.CastUtils;
import me.matamor.commonapi.utils.ConfigUtils;
import me.matamor.commonapi.utils.StringUtils;
import me.matamor.commonapi.utils.Validate;
import me.matamor.commonapi.utils.potion.PotionUtils;
import me.matamor.commonapi.utils.replacement.PlayerTextVariable;
import me.matamor.commonapi.utils.replacement.PlayerVariables;
import me.matamor.commonapi.utils.skull.SkullData;
import me.matamor.commonapi.utils.skull.SkullReflection;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@SerializableAs("CustomItem")
public class SimpleCustomItem implements CustomItem, Cloneable, ConfigurationSerializable {

    @Getter
    private Material material;

    @Getter
    private short dataValue;

    @Getter
    private Amount amount;

    @Getter
    @Setter
    private String name;
    private final List<String> lore = new ArrayList<>();
    private final Map<Enchantment, Integer> enchantments = new HashMap<>();

    @Getter
    @Setter
    private boolean itemGlow;

    @Getter
    @Setter
    private boolean removeAttributes;

    @Getter
    @Setter
    private boolean splash;

    @Getter
    @Setter
    private Color color;

    @Getter
    @Setter
    private SkullData skullOwner;

    @Getter
    @Setter
    private FireworkEffect fireworkEffect;

    @Getter
    private PotionType basePotionType;

    private final List<PotionEffect> effects = new ArrayList<>();

    private final List<PlayerTextVariable> localVariables = new ArrayList<>();

    @Getter
    @Setter
    private DyeColor bannerBaseColor;

    private List<Pattern> bannerPatterns = new ArrayList<>();

    private List<ItemFlag> itemFlags = new ArrayList<>();

    public SimpleCustomItem(Material material) {
        this(material, new SimpleAmount(1), (short) 0);
    }

    public SimpleCustomItem(Material material, Amount amount) {
        this(material, amount, (short) 0);
    }

    public SimpleCustomItem(@NotNull Material material, @NotNull Amount amount, short dataValue) {
        this.material = material;
        this.amount = amount;
        this.dataValue = dataValue;
    }

    /**
     * Sets the Material of the CustomItem
     * @param material is the target Material, can't be null!
     */

    @Override
    public void setMaterial(@NotNull Material material) {
        Validate.isFalse(material == Material.AIR, "Material can't be Air");

        this.material = material;
    }

    /**
     * Sets the Amount of the CustomItem
     * @param amount is the target Amount, can't be null!
     */

    @Override
    public void setAmount(@NotNull Amount amount) {
        this.amount = amount;
    }

    /**
     * Sets the DataValue of the CustomItem
     * @param dataValue is the target DataValue, can't be null!
     */

    @Override
    public void setDataValue(short dataValue) {
        if (0 > dataValue) {
            dataValue = 0;
        }

        this.dataValue = dataValue;
    }

    /**
     * Registers a LocalVariable of the CustomItem
     * @param textVariable is the target LocalVariable, can't be null!
     */

    @Override
    public void registerLocalVariable(@NotNull PlayerTextVariable textVariable) {
        this.localVariables.add(textVariable);
    }

    /**
     * Unregisters a LocalVariable of the CustomItem
     * @param textVariable is the target LocalVariable, can't be null!
     */

    @Override
    public void unregisterLocalVariable(@NotNull PlayerTextVariable textVariable) {
        this.localVariables.remove(textVariable);
    }

    /**
     * Gets the registered LocalVariables
     * @return the registered LocalVariables
     */

    @Override
    public List<PlayerTextVariable> getLocalVariables() {
        return this.localVariables;
    }

    /**
     * Checks if there is any registered LocalVariable
     * @return true if there is any registered LocalVariable
     */

    @Override
    public boolean hasLocalVariables() {
        return this.localVariables.size() > 0;
    }

    /**
     * Clears the LocalVariables
     */

    @Override
    public void clearLocalVariables() {
        this.localVariables.clear();
    }

    /**
     * Check is the CustomItem has a Name set
     * @return true if there is a Name set
     */

    @Override
    public boolean hasName() {
        return this.name != null;
    }

    /**
     * Sets the Lore of the CustomItem
     * @param lore is the target lore, can't be null!
     */

    @Override
    public void setLore(@NotNull String... lore) {
        setLore(Arrays.asList(lore));
    }

    /**
     * Sets the Lore of the CustomItem
     * @param lore is the target lore, can't be null!
     */

    @Override
    public void setLore(@NotNull List<String> lore) {
        this.lore.clear();
        this.lore.addAll(lore);
    }

    /**
     * Check is the CustomItem has a Lore set
     * @return true if there is a Lore set
     */

    @Override
    public boolean hasLore() {
        return !this.lore.isEmpty();
    }

    /**
     * Gets the Lore
     * @return the Lore of the CustomItem
     */

    @Override
    public List<String> getLore() {
        return this.lore;
    }

    /**
     * Clears the Lore
     */

    @Override
    public void clearLore() {
        this.lore.clear();
    }

    /**
     * Sets the Enchantments of the CustomItem
     * @param enchantments the enchantments to be set
     */

    @Override
    public void setEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        this.enchantments.clear();
        this.enchantments.putAll(enchantments);
    }

    /**
     * Checks if the CustomItem has any Enchantment
     * @return true if has any Enchantment
     */

    @Override
    public boolean hasEnchantments() {
        return !this.enchantments.isEmpty();
    }

    /**
     * Adds an Enchantment
     * @param enchantment the target Enchantment to be added, can't be null, default level is 1!
     */

    @Override
    public void addEnchantment(@NotNull Enchantment enchantment) {
        addEnchantment(enchantment, 1);
    }

    /**
     * Adds an Enchantment
     * @param enchantment the target Enchantment to be added, can't be null!
     * @param level the target Level for the target Enchantment, can't be lower than 1!
     */

    @Override
    public void addEnchantment(@NotNull Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, (Math.max(level, 1)));
    }

    /**
     * Removes an Enchantment
     * @param enchantment the target Enchantment to be removed, can't be null!
     */

    @Override
    public void removeEnchantment(@NotNull Enchantment enchantment) {
        this.enchantments.remove(enchantment);
    }

    /**
     * Gets the Level of an Enchantment
     * @param enchantment the target Enchantment, can't be null!
     * @return the level of the target Enchantment, if there isn't one set, returns -1!
     */

    @Override
    public int getEnchantment(@NotNull Enchantment enchantment) {
        return (this.enchantments.getOrDefault(enchantment, -1));
    }

    /**
     * Returns the Enchantments of the CustomItem
     * @return a map of the enchantments!
     */

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        return this.enchantments;
    }

    /**
     * Clears the Enchantments of the CustomItem
     */

    @Override
    public void clearEnchantments() {
        this.enchantments.clear();
    }

    /**
     * Checks if the CustomItem has one Color set
     * @return true if there is a Color set
     */

    @Override
    public boolean hasColor() {
        return this.color != null;
    }

    /**
     * Sets the SkullOwner of the CustomItem
     * @param name is the target Name of the SkullOwner
     * @param uuid is the target UUID of the SkullOwner
     */

    @Override
    public void setSkullOwner(@NotNull String name, @NotNull UUID uuid) {
        this.skullOwner = new SkullData(SkullData.SkullDataType.OFFLINE_PLAYER, name + ":" + uuid.toString());
    }

    /**
     * Checks if the CustomItem has a SkullOwner set
     * @return true if there is a SkullOwner set
     */

    @Override
    public boolean hasSkullData() {
        return this.skullOwner != null;
    }

    /**
     * Checks if the CustomItem has a FireworkEffect set!
     * @return true if there is one set!
     */

    @Override
    public boolean hasFireworkEffect() {
        return this.fireworkEffect != null;
    }

    /**
     * Checks if there is Base PotionType set!
     * @return true if there is one set!
     */

    @Override
    public boolean hasBasePotionType() {
        return this.basePotionType != null;
    }

    /**
     * Sets the BasePotion type of the CustomItem
     * @param basePotionType the target PotionType to be set, can't be null!
     */

    @Override
    public void setBasePotionType(@NotNull PotionType basePotionType) {
        this.basePotionType = basePotionType;
    }

    /**
     * Sets the PotionEffects of the CustomItem
     * @param effects the target PotionEffects to be set, can't be null!
     */

    @Override
    public void setPotionEffects(@NotNull PotionEffect... effects) {
        setPotionEffects(Arrays.asList(effects));
    }

    /**
     * Sets the PotionEffects of the CustomItem
     * @param effects the target PotionEffects to be set, can't be null!
     */

    @Override
    public void setPotionEffects(@NotNull List<PotionEffect> effects) {
        this.effects.addAll(effects);
    }

    /**
     * Add a PotionEffect to the CustomItem
     * @param effect the target PotionEffect to be added, can't be null!
     */

    @Override
    public void addPotionEffect(@NotNull PotionEffect effect) {
        this.effects.add(effect);
    }

    /**
     * Removes a PotionEffect of the CustomItem
     * @param effect the target PotionEffect to be removed, can't be null!
     */

    @Override
    public void removePotionEffect(@NotNull PotionEffect effect) {
        this.effects.remove(effect);
    }

    /**
     * Check is the CustomItem has any PotionEffect set
     * @return true if there is any set!
     */

    @Override
    public boolean hasPotionEffects() {
        return this.effects.size() > 0;
    }

    /**
     * Gets the PotionEffects
     * @return returns the PotionEffects of the CustomItem!
     */

    @Override
    public List<PotionEffect> getPotionEffects() {
        return this.effects;
    }

    /**
     * Clears the PotionEffects
     */

    @Override
    public void clearPotionEffects() {
        this.effects.clear();
    }

    /**
     * Checks if the CustomItem has a Banner base Color set!
     * @return true if has one set!
     */

    @Override
    public boolean hasBannerBaseColor() {
        return this.bannerBaseColor != null;
    }

    /**
     * Adds a Pattern to the CustomItem
     * @param pattern the target Pattern to be added, can't be null!
     */

    @Override
    public void addBannerPattern(@NotNull Pattern pattern) {
        this.bannerPatterns.add(pattern);
    }

    /**
     * Checks if the CustomItem has any Banner Pattern set!
     * @return true if has any set!
     */

    @Override
    public boolean hasBannerPatterns() {
        return !this.bannerPatterns.isEmpty();
    }

    /**
     * Checks if the CustomItem has a specific Banner Pattern set
     * @param pattern the target Banner Pattern to be checked, can't be null!
     * @return true if the CustomItem has the target Banner Pattern!
     */

    @Override
    public boolean hasBannerPattern(@NotNull Pattern pattern) {
        return this.bannerPatterns.contains(pattern);
    }

    /**
     * Sets the Banner Patterns of the CustomItem
     * @param bannerPatterns the target Banner Patterns to be set, can't be null!
     */

    @Override
    public void setBannerPatterns(@NotNull List<Pattern> bannerPatterns) {
        this.bannerPatterns.clear();
        this.bannerPatterns.addAll(bannerPatterns);
    }

    /**
     * Returns the Banner Patterns
     * @return the Banner Patterns of the CustomItem!
     */

    @Override
    public List<Pattern> getBannerPatterns() {
        return this.bannerPatterns;
    }

    /**
     * Clears the Banner Patterns
     */

    @Override
    public void clearBannerPatterns() {
        this.bannerPatterns.clear();
    }

    /**
     * Adds ItemFlags
     * @param itemFlags the target ItemFlags to be added, can't be null!
     */

    @Override
    public void addItemFlags(@NotNull ItemFlag... itemFlags) {
        addItemFlags(Arrays.asList(itemFlags));
    }

    /**
     * Adds ItemFlags
     * @param itemFlags the target ItemFlags to be added, can't be null!
     */

    @Override
    public void addItemFlags(@NotNull List<ItemFlag> itemFlags) {
        this.itemFlags.addAll(itemFlags);
    }

    /**
     * Set the ItemFlags
     * @param itemFlags the target ItemFlags to be set, can't be null!
     */

    @Override
    public void setItemFlags(@NotNull ItemFlag... itemFlags) {
        setItemFlags(Arrays.asList(itemFlags));
    }

    /**
     * Set the ItemFlags
     * @param itemFlags the target ItemFlags to be set, can't be null!
     */

    @Override
    public void setItemFlags(@NotNull List<ItemFlag> itemFlags) {
        this.itemFlags.clear();
        this.itemFlags.addAll(itemFlags);
    }

    /**
     * Checks if there is any ItemFlag set!
     * @return true if there is any set!
     */

    @Override
    public boolean hasItemFlags() {
        return this.itemFlags.size() > 0;
    }

    /**
     * Checks if ItemFlags are set
     * @param itemFlags the target ItemFlags to be checked, can't be null!
     * @return true if has all of them!
     */

    @Override
    public boolean hasItemFlags(@NotNull ItemFlag... itemFlags) {
        return hasItemFlags(Arrays.asList(itemFlags));
    }

    /**
     * Checks if ItemFlags are set
     * @param itemFlags the target ItemFlags to be checked, can't be null!
     * @return true if has all of them!
     */

    @Override
    public boolean hasItemFlags(@NotNull List<ItemFlag> itemFlags) {
        return this.itemFlags.containsAll(itemFlags);
    }

    /**
     * Removes ItemFlags
     * @param itemFlags the target ItemFlags to be removed, can't be null!
     */

    @Override
    public void removeItemFlags(@NotNull ItemFlag... itemFlags) {
        removeItemFlags(Arrays.asList(itemFlags));
    }

    /**
     * Removes ItemFlags
     * @param itemFlags the target ItemFlags to be removed, can't be null!
     */

    @Override
    public void removeItemFlags(@NotNull List<ItemFlag> itemFlags) {
        this.itemFlags.removeAll(itemFlags);
    }

    /**
     * Gets the ItemFlags
     * @return the ItemFlags of the CustomItem!
     */

    @Override
    public List<ItemFlag> getItemFlags() {
        return this.itemFlags;
    }

    /**
     * Clears the ItemFlags
     */

    @Override
    public void clearItemFlags() {
        this.itemFlags.clear();
    }

    protected String calculateName(Player player) {
        if (hasName()) {
            String name = this.name;

            for (PlayerTextVariable inventoryVariable : getLocalVariables()) {
                name = inventoryVariable.replace(name, player);
            }

            name = PlayerVariables.replace(name, player);

            if (name == null || name.isEmpty()) {
                return ChatColor.WHITE.toString();
            } else {
                return name;
            }
        }

        return null;
    }

    protected List<String> calculateLore(Player player) {
        List<String> output = new ArrayList<>();

        if (hasLore()) {
            for (String line : this.lore) {
                for (PlayerTextVariable inventoryVariable : getLocalVariables()) {
                    line = inventoryVariable.replace(line, player);
                }

                line = PlayerVariables.replace(line, player);

                if (line != null) {
                    output.add(line);
                }
            }
        }

        return output;
    }

    @Override
    public CustomItemBuilder builder() {
        return new SimpleCustomItemBuilder(this);
    }

    @Override
    public ItemStack build() {
        return build(null);
    }

    @Override
    public ItemStack build(Player player) {
        ItemStack itemStack = (this.material == null ? new ItemStack(Material.BEDROCK) : new ItemStack(this.material, this.amount.getAmount(), this.dataValue));

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            if (hasName()) {
                itemMeta.setDisplayName(StringUtils.color(calculateName(player)));
            }

            if (hasLore()) {
                itemMeta.setLore(StringUtils.color(calculateLore(player)));
            }

            if (hasColor() && itemMeta instanceof LeatherArmorMeta) {
                ((LeatherArmorMeta) itemMeta).setColor(this.color);
            }

            if (hasFireworkEffect() && itemMeta instanceof FireworkEffectMeta) {
                ((FireworkEffectMeta) itemMeta).setEffect(this.fireworkEffect);
            }

            if (hasEnchantments()) {
                if (itemMeta instanceof EnchantmentStorageMeta) {
                    EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) itemMeta;

                    this.enchantments.forEach((k, v) -> storageMeta.addStoredEnchant(k, v, true));
                } else {
                    this.enchantments.forEach((k, v) -> itemMeta.addEnchant(k, v, true));
                }
            }

            if (this.itemFlags.size() > 0) {
                this.itemFlags.forEach(itemMeta::addItemFlags);
            }

            if (hasPotionEffects() && itemMeta instanceof PotionMeta) {
                PotionMeta potionMeta = (PotionMeta) itemMeta;

                if (hasBasePotionType()) {
                    //If the Version is 1.8 we gotta do something different!
                    if (NMSVersion.isBetween(NMSVersion.v1_8_R1, NMSVersion.v1_8_R3)) {
                        Potion potion = new Potion(this.basePotionType);
                        potion.setSplash(this.splash);

                        potion.apply(itemStack);
                    } else {
                        potionMeta.setBasePotionData(new PotionData(this.basePotionType));
                    }
                }

                for (PotionEffect effect : getPotionEffects()) {
                    potionMeta.addCustomEffect(effect, true);
                }

                if (!isRemoveAttributes() && this.basePotionType == null) {
                    List<String> lore = (itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>());

                    //noinspection ConstantConditions
                    this.effects.forEach(e -> lore.add(PotionUtils.getDescription(e)));

                    itemMeta.setLore(lore);
                }
            }

            if (itemMeta instanceof BannerMeta) {
                BannerMeta bannerMeta = (BannerMeta) itemMeta;

                if (hasBannerBaseColor()) {
                    bannerMeta.setBaseColor(this.bannerBaseColor);
                }

                if (hasBannerPatterns()) {
                    this.bannerPatterns.forEach(bannerMeta::addPattern);
                }
            }

            itemStack.setItemMeta(itemMeta);

            if (hasSkullData() && itemMeta instanceof SkullMeta) {
                this.skullOwner.apply(itemStack);
            }

            if (this.itemGlow && this.enchantments.isEmpty()) {
                itemStack.addEnchantment(GlowEnchantment.getGlow(), 1);

                ItemMeta meta = itemStack.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(meta);
            }

            if (this.removeAttributes) {
                ItemMeta meta = itemStack.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemStack.setItemMeta(meta);
            }
        }

        return itemStack;
    }

    @Override
    public SimpleCustomItem clone() {
        SimpleCustomItem cloned = new SimpleCustomItem(getMaterial(), getAmount(), getDataValue());

        if (hasName()) {
            cloned.setName(getName());
        }

        if (hasLore()) {
            cloned.setLore(getLore());
        }

        if (hasEnchantments()) {
            cloned.setEnchantments(getEnchantments());
        }

        if (hasColor()) {
            cloned.setColor(getColor());
        }

        if (hasSkullData()) {
            cloned.setSkullOwner(getSkullOwner());
        }

        if (hasFireworkEffect()) {
            cloned.setFireworkEffect(getFireworkEffect());
        }

        if (hasPotionEffects()) {
            cloned.setPotionEffects(getPotionEffects());
        }

        if (isSplash()) {
            cloned.setSplash(true);
        }

        if (hasBannerBaseColor()) {
            cloned.setBannerBaseColor(getBannerBaseColor());
        }

        if (hasBannerPatterns()) {
            cloned.setBannerPatterns(getBannerPatterns());
        }

        if (hasLocalVariables()) {
            getLocalVariables().forEach(cloned::registerLocalVariable);
        }

        if (isRemoveAttributes()) {
            cloned.setRemoveAttributes(true);
        }

        if (isItemGlow()) {
            cloned.setItemGlow(true);
        }

        if (hasItemFlags()) {
            cloned.setItemFlags(getItemFlags());
        }

        return cloned;
    }

    @Override
    public Map<String, Object> serialize() {
        return serialize(this);
    }

    public static class SimpleCustomItemBuilder implements CustomItemBuilder {

        private SimpleCustomItem customItem;

        private SimpleCustomItemBuilder(SimpleCustomItem customItem) {
            this.customItem = customItem;
        }

        @Override
        public SimpleCustomItemBuilder setMaterial(Material material) {
            this.customItem.setMaterial(material);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setAmount(Amount amount) {
            this.customItem.setAmount(amount);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setDataValue(short dataValue) {
            this.customItem.setDataValue(dataValue);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setName(String name) {
            this.customItem.setName(name);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setLore(String... lore) {
            this.customItem.setLore(lore);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setLore(List<String> lore) {
            this.customItem.setLore(lore);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments) {
            this.customItem.setEnchantments(enchantments);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder addEnchantment(Enchantment enchantment) {
            this.customItem.addEnchantment(enchantment);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder addEnchantment(Enchantment enchantment, int level) {
            this.customItem.addEnchantment(enchantment, level);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setColor(Color color) {
            this.customItem.setColor(color);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setSkullOwner(String name, UUID uuid) {
            this.customItem.setSkullOwner(name, uuid);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setSkullData(SkullData skullData) {
            this.customItem.setSkullOwner(skullData);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setFireworkEffect(FireworkEffect fireworkEffect) {
            this.customItem.setFireworkEffect(fireworkEffect);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setItemGlow(boolean itemGlow) {
            this.customItem.setItemGlow(itemGlow);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setRemoveAttributes(boolean removeAttributes) {
            this.customItem.setRemoveAttributes(removeAttributes);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setSplash(boolean splash) {
            this.customItem.setSplash(splash);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setPotionEffects(PotionEffect... effects) {
            this.customItem.setPotionEffects(effects);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setPotionEffects(List<PotionEffect> effects) {
            this.customItem.setPotionEffects(effects);
            return this;
        }

        @Override
        @Deprecated
        public SimpleCustomItemBuilder addPotionEffects(PotionEffect effect) {
            this.customItem.addPotionEffect(effect);
            return this;
        }

        @Override
        @Deprecated
        public SimpleCustomItemBuilder addPotionEffect(PotionEffect effect) {
            this.customItem.addPotionEffect(effect);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder registerLocalVariable(PlayerTextVariable inventoryVariable) {
            this.customItem.registerLocalVariable(inventoryVariable);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setBannerBaseColor(DyeColor bannerBaseColor) {
            this.customItem.setBannerBaseColor(bannerBaseColor);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder addBannerPattern(Pattern pattern) {
            this.customItem.addBannerPattern(pattern);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setBannerPatterns(List<Pattern> pattern) {
            this.customItem.setBannerPatterns(pattern);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder addItemFlags(ItemFlag... itemFlags) {
            this.customItem.addItemFlags(itemFlags);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder addItemFlags(List<ItemFlag> itemFlags) {
            this.customItem.addItemFlags(itemFlags);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setItemFlags(ItemFlag... itemFlags) {
            this.customItem.setItemFlags(itemFlags);
            return this;
        }

        @Override
        public SimpleCustomItemBuilder setItemFlags(List<ItemFlag> itemFlags) {
            this.customItem.setItemFlags(itemFlags);
            return this;
        }

        @Override
        public SimpleCustomItem build() {
            return this.customItem;
        }

        @Override
        public ItemStack toItemStack() {
            return this.customItem.build();
        }

        @Override
        public ItemStack toItemStack(Player player) {
            return this.customItem.build(player);
        }
    }

    public static SimpleCustomItemBuilder builder(Material material) {
        return builder(material, 1);
    }

    public static SimpleCustomItemBuilder builder(Material material, int amount) {
        return builder(material, amount, (short) 0);
    }

    public static SimpleCustomItemBuilder builder(Material material, int amount, short dataValue) {
        return builder(material, new SimpleAmount(amount), dataValue);
    }

    public static SimpleCustomItemBuilder builder(Material material, Amount amount, short dataValue) {
        return new SimpleCustomItemBuilder(new SimpleCustomItem(material, amount, dataValue));
    }

    public static SimpleCustomItemBuilder builder(SimpleCustomItem customItem) {
        return new SimpleCustomItemBuilder(customItem);
    }

    private static class Node {

        public static final String
                MATERIAL = "Material",
                AMOUNT = "Amount",
                NAME = "DisplayName",
                LORE = "Lore",
                ENCHANT = "Enchantments",
                COLOR = "Color",
                SKULL_OWNER = "Skull-Owner",
                SPLASH = "Potion.Splash",
                EFFECTS = "Potion.Effects",
                GLOW = "Glow",
                REMOVE_ATTRIBUTES = "RemoveAttributes",
                BANNER_BASE_COLOR = "BannerBaseColor",
                BANNER_PATTERNS = "BannerPatterns",
                ITEM_FLAGS = "ItemFlags",
                FIREWORK_EFFECT = "FireworkEffect";
    }

    public static Map<String, Object> serialize(SimpleCustomItem customItem) {
        Validate.notNull(customItem, "CustomItem can't be null");

        Map<String, Object> serialized = new LinkedHashMap<>();

        StringBuilder material = new StringBuilder();

        if (customItem.getMaterial() == null) {
            material.append(Material.BEDROCK.name());
        } else {
            material.append(customItem.getMaterial().name());
        }

        if (customItem.getDataValue() > 0) {
            material.append(":").append(customItem.getDataValue());
        }

        serialized.put(Node.MATERIAL, material.toString());
        serialized.put(Node.AMOUNT, AmountUtil.serialize(customItem.getAmount()));

        if (customItem.hasName()) {
            serialized.put(Node.NAME, (customItem.getName() == null ? "" : customItem.getName()));
        }

        if (customItem.hasLore()) {
            serialized.put(Node.LORE, customItem.getLore());
        }

        if (customItem.hasEnchantments()) {
            serialized.put(Node.ENCHANT, serializeEnchantments(customItem.getEnchantments()));
        }

        if (customItem.hasColor()) {
            serialized.put(Node.COLOR, customItem.getColor().serialize());
        }

        if (customItem.hasSkullData()) {
            Map<String, Object> skullData = new HashMap<>();

            skullData.put("Value", customItem.getSkullOwner().getValue());
            skullData.put("Type", customItem.getSkullOwner().getType().name());

            serialized.put(Node.SKULL_OWNER, skullData);
        }

        if (customItem.hasFireworkEffect()) {
            serialized.put(Node.FIREWORK_EFFECT, customItem.getFireworkEffect().serialize());
        }

        if (customItem.hasPotionEffects()) {
            serialized.put(Node.SPLASH, customItem.isSplash());
            serialized.put(Node.EFFECTS, serializeEffects(customItem.getPotionEffects()));
        }

        if (customItem.hasBannerBaseColor()) {
            serialized.put(Node.BANNER_BASE_COLOR, customItem.getBannerBaseColor().name());
        }

        if (customItem.hasBannerPatterns()) {
            serialized.put(Node.BANNER_PATTERNS, serializePatterns(customItem.getBannerPatterns()));
        }

        if (customItem.isItemGlow()) {
            serialized.put(Node.GLOW, true);
        }

        if (customItem.isRemoveAttributes()) {
            serialized.put(Node.REMOVE_ATTRIBUTES, true);
        }

        if (customItem.hasItemFlags()) {
            serialized.put(Node.ITEM_FLAGS, serializeItemFlags(customItem.getItemFlags()));
        }

        return serialized;
    }

    public static SimpleCustomItem deserialize(ConfigurationSection section) {
        Material material = null;
        short itemValue = 0;

        if (section.isString(Node.MATERIAL)) {
            String materialData = section.getString(Node.MATERIAL);

            //noinspection ConstantConditions
            if (materialData.contains(":")) {
                String[] args = materialData.split(":");

                try {
                    itemValue = Short.parseShort(args[1]);
                } catch (NumberFormatException ignored) {

                }

                materialData = args[0];
            }

            material = Material.matchMaterial(materialData);
        }

        if (material == null) {
            material = Material.BEDROCK;
        }

        Amount amount = new SimpleAmount(1);

        if (section.isSet(Node.AMOUNT)) {
            amount = AmountUtil.deserialize(CastUtils.asString(section.get(Node.AMOUNT)));
        }

        String name = section.getString(Node.NAME);
        List<String> lore = section.getStringList(Node.LORE);

        Map<Enchantment, Integer> enchantments = deserializeEnchantment(section.getStringList(Node.ENCHANT));

        Color color = section.getColor(Node.COLOR);

        SkullData skullData = null;

        if (section.isSet(Node.SKULL_OWNER)) {
            Map<String, Object> map = ConfigUtils.asMap(section.get(Node.SKULL_OWNER));

            if (map != null && map.containsKey("Type") && map.containsKey("Value")) {
                SkullData.SkullDataType type = SkullData.SkullDataType.getByName((String) map.get("Type"));
                String value = (String) map.get("Value");

                skullData = new SkullData(type, value);
            }
        }

        FireworkEffect fireworkEffect = null;

        if (section.isSet(Node.FIREWORK_EFFECT)) {
            Map<String, Object> map = ConfigUtils.asMap(section.get(Node.FIREWORK_EFFECT));

            fireworkEffect = (FireworkEffect) FireworkEffect.deserialize(map);
        }

        boolean glow = section.getBoolean(Node.GLOW);
        boolean removeAttributes = section.getBoolean(Node.REMOVE_ATTRIBUTES);
        boolean splash = section.getBoolean(Node.SPLASH);

        List<PotionEffect> effects = null;

        if (section.isSet(Node.EFFECTS)) {
            effects = deserializeEffects(section.getStringList(Node.EFFECTS));
        }

        DyeColor bannerBaseColor = null;

        if (section.isSet(Node.BANNER_BASE_COLOR)) {
            bannerBaseColor = DyeColor.valueOf(section.getString(Node.BANNER_BASE_COLOR));
        }

        List<Pattern> bannerPatterns = null;

        if (section.isSet(Node.BANNER_PATTERNS)) {
            bannerPatterns = deserializePatterns(section.getStringList(Node.BANNER_PATTERNS));
        }

        List<ItemFlag> itemFlags = null;

        if (section.isSet(Node.ITEM_FLAGS)) {
            itemFlags = deserializeItemFlags(section.getStringList(Node.ITEM_FLAGS));
        }

        //Build CustomItem with all the data deserialized.

        SimpleCustomItem customItem = new SimpleCustomItem(material, amount, itemValue);

        if (name != null) {
            customItem.setName(name);
        }

        customItem.setLore(lore);

        customItem.setEnchantments(enchantments);

        if (color != null) {
            customItem.setColor(color);
        }

        if (skullData != null) {
            customItem.setSkullOwner(skullData);
        }

        if (fireworkEffect != null) {
            customItem.setFireworkEffect(fireworkEffect);
        }

        customItem.setItemGlow(glow);
        customItem.setRemoveAttributes(removeAttributes);
        customItem.setSplash(splash);

        if (effects != null) {
            customItem.setPotionEffects(effects);
        }

        if (bannerBaseColor != null) {
            customItem.setBannerBaseColor(bannerBaseColor);
        }

        if (bannerPatterns != null) {
            customItem.setBannerPatterns(bannerPatterns);
        }

        if (itemFlags != null) {
            customItem.setItemFlags(itemFlags);
        }

        return customItem;
    }

    @SuppressWarnings("Unchecked")
    public static SimpleCustomItem deserialize(Map<String, Object> section) {
        Material material = null;
        short itemValue = 0;

        if (section.containsKey(Node.MATERIAL)) {
            String materialData = CastUtils.asString(section.get(Node.MATERIAL));

            if (materialData.contains(":")) {
                String[] args = materialData.split(":");

                try {
                    itemValue = Short.parseShort(args[1]);
                } catch (NumberFormatException ignored) {

                }

                materialData = args[0];
            }

            material = Material.matchMaterial(materialData);
        }

        Amount amount = new SimpleAmount(1);

        if (section.containsKey(Node.AMOUNT)) {
            amount = AmountUtil.deserialize(CastUtils.asString(section.get(Node.AMOUNT)));
        }

        String name = null;

        if (section.containsKey(Node.NAME)) {
            name = CastUtils.asString(section.get(Node.NAME));
        }

        List<String> lore = null;

        if (section.containsKey(Node.LORE)) {
            lore = (List<String>) section.get(Node.LORE);
        }

        Map<Enchantment, Integer> enchantments = null;

        if (section.containsKey(Node.ENCHANT)) {
            enchantments = deserializeEnchantment((List<String>) section.get(Node.ENCHANT));
        }

        Color color = null;

        if (section.containsKey(Node.COLOR)) {
            color = Color.deserialize((Map<String, Object>) section.get(Node.COLOR));
        }

        SkullData skullData = null;

        if (section.containsKey(Node.SKULL_OWNER)) {
            Map<String, Object> map = ConfigUtils.asMap(section.get(Node.SKULL_OWNER));

            if (map != null && map.containsKey("Type") && map.containsKey("Value")) {
                SkullData.SkullDataType type = SkullData.SkullDataType.getByName((String) map.get("Type"));
                String value = (String) map.get("Value");

                skullData = new SkullData(type, value);
            }
        }

        FireworkEffect fireworkEffect = null;

        if (section.containsKey(Node.FIREWORK_EFFECT)) {
            Map<String, Object> map = ConfigUtils.asMap(section.get(Node.FIREWORK_EFFECT));

            fireworkEffect = (FireworkEffect) FireworkEffect.deserialize(map);
        }

        boolean glow = false;

        if (section.containsKey(Node.GLOW)) {
            glow = CastUtils.asBoolean(section.get(Node.GLOW));
        }

        boolean removeAttributes = false;

        if (section.containsKey(Node.REMOVE_ATTRIBUTES)) {
            removeAttributes = CastUtils.asBoolean(section.get(Node.REMOVE_ATTRIBUTES));
        }

        boolean splash = false;

        if (section.containsKey(Node.SPLASH)) {
            splash = CastUtils.asBoolean(section.get(Node.SPLASH));
        }

        List<PotionEffect> effects = null;

        if (section.containsKey(Node.EFFECTS)) {
            effects = deserializeEffects((List<String>) section.get(Node.EFFECTS));
        }

        if (section.containsKey(Node.EFFECTS)) {
            effects = deserializeEffects((List<String>) section.get(Node.EFFECTS));
        }

        DyeColor bannerBaseColor = null;

        if (section.containsKey(Node.BANNER_BASE_COLOR)) {
            bannerBaseColor = DyeColor.valueOf(CastUtils.asString(section.get(Node.BANNER_BASE_COLOR)));
        }

        List<Pattern> bannerPatterns = null;

        if (section.containsKey(Node.BANNER_PATTERNS)) {
            bannerPatterns = deserializePatterns((List<String>) section.get(Node.BANNER_PATTERNS));
        }

        List<ItemFlag> itemFlags = null;

        if (section.containsKey(Node.ITEM_FLAGS)) {
            itemFlags = deserializeItemFlags((List<String>) section.get(Node.ITEM_FLAGS));
        }

        //Build CustomItem with all the data deserialized.

        SimpleCustomItem customItem = new SimpleCustomItem(material, amount, itemValue);

        if (name != null) {
            customItem.setName(name);
        }

        if (lore != null) {
            customItem.setLore(lore);
        }

        if (enchantments != null) {
            customItem.setEnchantments(enchantments);
        }

        if (color != null) {
            customItem.setColor(color);
        }

        if (skullData != null) {
            customItem.setSkullOwner(skullData);
        }

        if (fireworkEffect != null) {
            customItem.setFireworkEffect(fireworkEffect);
        }

        customItem.setItemGlow(glow);
        customItem.setRemoveAttributes(removeAttributes);
        customItem.setSplash(splash);

        if (effects != null) {
            customItem.setPotionEffects(effects);
        }

        if (bannerBaseColor != null) {
            customItem.setBannerBaseColor(bannerBaseColor);
        }

        if (bannerPatterns != null) {
            customItem.setBannerPatterns(bannerPatterns);
        }

        if (itemFlags != null) {
            customItem.setItemFlags(itemFlags);
        }

        return customItem;
    }


    private static List<String> serializeEnchantments(Map<Enchantment, Integer> enchants) {
        List<String> serialized = new ArrayList<>();

        for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
            serialized.add(entry.getKey().getName() + ":" + entry.getValue());
        }

        return serialized;
    }

    private static Map<Enchantment, Integer> deserializeEnchantment(List<String> serialized) {
        Map<Enchantment, Integer> deserialized = new HashMap<>();

        for (String entry : serialized) {
            int level = 1;

            if (entry.contains(":")) {
                String[] args = entry.split(":");

                try {
                    level = Integer.parseInt(args[1]);
                } catch (NumberFormatException ignored) {

                }

                entry = args[0];
            }

            Enchantment enchantment = Enchantment.getByName(entry);
            if (enchantment == null) continue;

            deserialized.put(enchantment, level);
        }


        return deserialized;
    }

    private static List<String> serializeEffects(List<PotionEffect> potionEffects) {
        return potionEffects.stream().map(p -> p.getType().getName() + ":" + p.getAmplifier() + "-" + p.getDuration()).collect(Collectors.toList());
    }

    private static List<PotionEffect> deserializeEffects(List<String> serialized) {
        List<PotionEffect> deserialized = new ArrayList<>();

        for (String entry : serialized) {

            int amplifier = 0;
            int seconds = 0;

            if (entry.contains("-")) {
                String[] args = entry.split("-");

                try {
                    seconds = Integer.parseInt(args[1]);
                } catch (NumberFormatException ignored) {

                }

                entry = args[0];
            }

            if (entry.contains(":")) {
                String[] args = entry.split(":");

                try {
                    amplifier = Integer.parseInt(args[1]);
                } catch (NumberFormatException ignored) {

                }

                entry = args[0];
            }

            PotionEffectType effectType = PotionEffectType.getByName(entry);
            if (effectType == null) continue;

            deserialized.add(new PotionEffect(effectType, seconds, amplifier));
        }

        return deserialized;
    }

    private static List<String> serializePatterns(List<Pattern> patterns) {
        return patterns.stream().map(p -> p.getColor().toString() + ":" + p.getPattern().getIdentifier()).collect(Collectors.toList());
    }

    private static List<Pattern> deserializePatterns(List<String> serialized) {
        List<Pattern> patterns = new ArrayList<>();

        for (String string : serialized) {
            String[] split = string.split(":");

            if (split.length == 2) {
                DyeColor color = DyeColor.valueOf(split[0]);
                PatternType type = PatternType.getByIdentifier(split[1]);

                patterns.add(new Pattern(color, type));
            }
        }

        return patterns;
    }

    public static List<String> serializeItemFlags(List<ItemFlag> itemFlags) {
        return itemFlags.stream().map(Enum::name).collect(Collectors.toList());
    }

    public static List<ItemFlag> deserializeItemFlags(List<String> serialized) {
        List<ItemFlag> patterns = new ArrayList<>();

        for (String string : serialized) {
            try {
                patterns.add(ItemFlag.valueOf(string));
            } catch (IllegalArgumentException ignored) {

            }
        }

        return patterns;
    }

    private static final String[] RESERVED_KEYS = {"ench", "CustomPotionEffects"};

    public static SimpleCustomItem toCustomItem(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        SimpleCustomItem customItem = new SimpleCustomItem(itemStack.getType(), new SimpleAmount(itemStack.getAmount()), itemStack.getDurability());

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.hasDisplayName()) {
            customItem.setName(itemMeta.getDisplayName());
        }

        if (itemMeta.hasLore()) {
            customItem.setLore(itemMeta.getLore());
        }

        if (itemMeta instanceof LeatherArmorMeta) {
            customItem.setColor(((LeatherArmorMeta) itemMeta).getColor());
        }

        if (itemMeta instanceof FireworkEffectMeta) {
            customItem.setFireworkEffect(((FireworkEffectMeta) itemMeta).getEffect());
        }

        if (itemMeta instanceof SkullMeta) {
            String texture = SkullReflection.getTexture(itemStack);
            if (texture == null) {
                SkullMeta skullMeta = (SkullMeta) itemMeta;

                if (skullMeta.hasOwner()) {
                    customItem.setSkullOwner(new SkullData(SkullData.SkullDataType.NAME, skullMeta.getOwner()));
                }
            } else {
                customItem.setSkullOwner(new SkullData(SkullData.SkullDataType.TEXTURE,  texture));
            }
        }

        if (itemMeta instanceof EnchantmentStorageMeta) {
            customItem.setEnchantments(((EnchantmentStorageMeta) itemMeta).getStoredEnchants());
        } else {
            customItem.setEnchantments(itemStack.getEnchantments());
        }

        if (itemMeta instanceof PotionMeta) {
            PotionMeta potionMeta = (PotionMeta) itemMeta;

            if (NMSVersion.isBetween(NMSVersion.v1_8_R1, NMSVersion.v1_8_R3)) {
                Potion potion = Potion.fromItemStack(itemStack);

                customItem.setSplash(potion.isSplash());
            } else {
                customItem.setSplash(itemStack.getType().name().equals("SPLASH_POTION"));
            }

            for (PotionEffect effect : potionMeta.getCustomEffects()) {
                customItem.addPotionEffect(effect);
            }
        }

        if (itemMeta instanceof BannerMeta) {
            BannerMeta bannerMeta = (BannerMeta) itemMeta;

            customItem.setBannerBaseColor(bannerMeta.getBaseColor());

            for (Pattern pattern : bannerMeta.getPatterns()) {
                customItem.addBannerPattern(pattern);
            }
        }

        return customItem;
    }
}
