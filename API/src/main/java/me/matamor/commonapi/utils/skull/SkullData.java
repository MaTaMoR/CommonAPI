package me.matamor.commonapi.utils.skull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class SkullData {

    public enum SkullDataType {

        TEXTURE,
        URL,
        NAME,
        OFFLINE_PLAYER;

        public static SkullDataType getByName(String name) {
            for (SkullDataType type : values()) {
                if (type.name().equalsIgnoreCase(name)) {
                    return type;
                }
            }

            return null;
        }
    }

    @Getter
    private final SkullDataType type;

    @Getter
    private final String value;

    public void apply(ItemStack itemStack) {
        SkullReflection.setTexture(itemStack, this);
    }
}
