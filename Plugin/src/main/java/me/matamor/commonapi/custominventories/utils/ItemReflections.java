package me.matamor.commonapi.custominventories.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.matamor.commonapi.utils.FastOfflinePlayer;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public final class ItemReflections {

    private ItemReflections() { }

    private static final Reflections.FieldAccessor<GameProfile> PROFILE_FIELD = Reflections.getField(Reflections.getCraftBukkitClass("inventory.CraftMetaSkull"), "profile", GameProfile.class);

    public static void setTexture(ItemStack itemStack, SkullData data) {
        if (itemStack.getItemMeta() instanceof SkullMeta) {
            SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

            if (data.getType() == SkullData.SkullDataType.NAME) {
                meta.setOwner(data.getValue());
            } else if (data.getType() == SkullData.SkullDataType.OFFLINE_PLAYER) {
                String[] args = data.getValue().split(":");
                meta.setOwningPlayer(new FastOfflinePlayer(args[0], UUID.fromString(args[1])));
            } else if (data.getType() == SkullData.SkullDataType.URL) {
                GameProfile profile = new GameProfile(UUID.randomUUID(), null);

                byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", data.getValue()).getBytes());
                profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

                PROFILE_FIELD.set(meta, profile);
            } else if (data.getType() == SkullData.SkullDataType.TEXTURE) {
                GameProfile profile = new GameProfile(UUID.randomUUID(), null);

                profile.getProperties().put("textures", new Property("textures", data.getValue()));

                PROFILE_FIELD.set(meta, profile);
            }

            itemStack.setItemMeta(meta);
        }
    }

    public static String getTexture(ItemStack itemStack) {
        if (itemStack.getItemMeta() instanceof SkullMeta) {
            GameProfile profile = PROFILE_FIELD.get(itemStack.getItemMeta());

            if (profile == null || profile.getProperties().get("textures") == null) {
                return null;
            }

            for (Property property : profile.getProperties().get("textures")) {
                if (property.getName().equals("textures")) {
                    return property.getValue();
                }
            }
        }

        return null;
    }
}
