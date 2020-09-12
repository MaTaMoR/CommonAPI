package me.matamor.commonapi.nms.v1_8_R3.scoreboard;

import lombok.Getter;
import me.matamor.commonapi.scoreboard.nms.NMSObjective;
import me.matamor.commonapi.scoreboard.nms.NMSScore;
import me.matamor.commonapi.scoreboard.nms.NMSScoreboard;
import me.matamor.commonapi.scoreboard.nms.NMSViewable;
import me.matamor.commonapi.utils.Reflections;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class NMSObjectiveImpl implements NMSObjective {

    public static final Reflections.FieldAccessor<String> NAME_DISPLAY_FIELD = Reflections.getField(PacketPlayOutScoreboardDisplayObjective.class, "b", String.class);
    public static final Reflections.FieldAccessor<Integer> MODE_DISPLAY_FIELD = Reflections.getField(PacketPlayOutScoreboardDisplayObjective.class, "a", int.class);

    public static final Reflections.FieldAccessor<String> NAME_FIELD = Reflections.getField(PacketPlayOutScoreboardObjective.class, "a", String.class);
    public static final Reflections.FieldAccessor<String> DISPLAY_NAME_FIELD = Reflections.getField(PacketPlayOutScoreboardObjective.class, "b", String.class);
    public static final Reflections.FieldAccessor<Object> ACTION_FIELD = Reflections.getField(PacketPlayOutScoreboardObjective.class, "c", Object.class);
    public static final Reflections.FieldAccessor<Integer> EXTRA_FIELD = Reflections.getField(PacketPlayOutScoreboardObjective.class, "d", int.class);

    private final IScoreboardCriteria.EnumScoreboardHealthDisplay[] ENUM_ACTION = IScoreboardCriteria.EnumScoreboardHealthDisplay.values();

    @Getter
    private final NMSScoreboard scoreboard;

    @Getter
    private final DisplaySlot displaySlot;

    @Getter
    private final String name;

    @Getter
    private String displayName;

    private final Map<String, NMSScore> scores = new LinkedHashMap<>();

    public NMSObjectiveImpl(NMSScoreboard scoreboard, DisplaySlot displaySlot) {
        this.scoreboard = scoreboard;
        this.displaySlot = displaySlot;
        this.name = displaySlot.name();
        this.displayName = name;
    }

    @Override
    public void setDisplayName(@NotNull String displayName) {
        if (!this.displayName.equals(displayName)) {
            this.displayName = displayName;

            update();
        }
    }

    @Override
    public @NotNull NMSScore getScore(@NotNull String entry) {
        return this.scores.computeIfAbsent(entry, k -> {
            NMSScore newScore = new NMSScoreImpl(this, k, 0);
            newScore.show();
            return newScore;
        });
    }

    @Override
    public void removeScore(@NotNull String entry) {
        NMSScore nmsScore = this.scores.remove(entry);
        if (nmsScore != null) {
            nmsScore.hide();
        }
    }

    @Override
    public @NotNull Collection<NMSScore> getScores() {
        return Collections.unmodifiableCollection(this.scores.values());
    }

    @Override
    public void clearScores() {
        this.scores.values().forEach(NMSViewable::hide);
        this.scores.clear();
    }

    @Override
    public void show() {

    }

    @Override
    public void show(@NotNull Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(createPacket(0));
        craftPlayer.getHandle().playerConnection.sendPacket(createDisplayPacket());

        this.scores.values().forEach(e -> e.show(player));
    }

    @Override
    public void update() {
        sendPacket(createPacket(2));
    }

    @Override
    public void update(@NotNull Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(createPacket(2));
    }

    @Override
    public void hide() {
        sendPacket(createPacket(1));
    }

    @Override
    public void hide(@NotNull Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(createPacket(1));
    }

    private void sendPacket(Packet<?> packet) {
        Collection<Player> players = this.scoreboard.getViewers();

        if (players.size() > 0) {
            for (Player player : players) {
                CraftPlayer craftPlayer = (CraftPlayer) player;

                craftPlayer.getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    private PacketPlayOutScoreboardObjective createPacket(int mode) {
        PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective();

        NAME_FIELD.set(packet, this.name);
        DISPLAY_NAME_FIELD.set(packet, this.displayName);
        ACTION_FIELD.set(packet, ENUM_ACTION[0]);
        EXTRA_FIELD.set(packet, mode);

        return packet;
    }
    private PacketPlayOutScoreboardDisplayObjective createDisplayPacket() {
        PacketPlayOutScoreboardDisplayObjective packet = new PacketPlayOutScoreboardDisplayObjective();

        NAME_DISPLAY_FIELD.set(packet, this.name);
        MODE_DISPLAY_FIELD.set(packet, ScoreboardTranslations.fromBukkitSlot(this.displaySlot));

        return packet;
    }
}
