package me.matamor.commonapi.nms.v1_8_R3.scoreboard;

import com.google.common.collect.ImmutableBiMap;
import net.minecraft.server.v1_8_R3.Scoreboard;
import org.bukkit.scoreboard.DisplaySlot;

public class ScoreboardTranslations {

    static final int MAX_DISPLAY_SLOT = 3;
    static ImmutableBiMap<DisplaySlot, String> SLOTS;

    static {
        SLOTS = ImmutableBiMap.of(DisplaySlot.BELOW_NAME, "belowName", DisplaySlot.PLAYER_LIST, "list", DisplaySlot.SIDEBAR, "sidebar");
    }

    private ScoreboardTranslations() {
    }

    static DisplaySlot toBukkitSlot(int i) {
        return SLOTS.inverse().get(Scoreboard.getSlotName(i));
    }

    static int fromBukkitSlot(DisplaySlot slot) {
        return Scoreboard.getSlotForName(SLOTS.get(slot));
    }

    static String getName(DisplaySlot slot) {
        return SLOTS.get(slot);
    }
}
