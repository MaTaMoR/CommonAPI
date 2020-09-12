package me.matamor.commonapi.nms.v1_8_R3.scoreboard;

import lombok.Getter;
import me.matamor.commonapi.scoreboard.nms.NMSScoreboard;
import me.matamor.commonapi.scoreboard.nms.NMSTeam;
import me.matamor.commonapi.utils.Reflections;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NMSTeamImpl implements NMSTeam {

    public static final Reflections.FieldAccessor<String> NAME_FIELD = Reflections.getField(PacketPlayOutScoreboardTeam.class, "a", String.class);
    public static final Reflections.FieldAccessor<String> DISPLAY_NAME_FIELD = Reflections.getField(PacketPlayOutScoreboardTeam.class, "b", String.class);
    public static final Reflections.FieldAccessor<String> PREFIX_FIELD = Reflections.getField(PacketPlayOutScoreboardTeam.class, "c", String.class);
    public static final Reflections.FieldAccessor<String> SUFFIX_FIELD = Reflections.getField(PacketPlayOutScoreboardTeam.class, "d", String.class);
    public static final Reflections.FieldAccessor<Collection> ENTRIES_FIELD = Reflections.getField(PacketPlayOutScoreboardTeam.class, "g", Collection.class);
    public static final Reflections.FieldAccessor<Integer> MODE_FIELD = Reflections.getField(PacketPlayOutScoreboardTeam.class, "h", int.class);
    public static final Reflections.FieldAccessor<Integer> FRIENDLY_FIRE_FIELD = Reflections.getField(PacketPlayOutScoreboardTeam.class, "i", int.class);

    @Getter
    private final NMSScoreboard scoreboard;

    @Getter
    private final String name;

    @Getter
    private String displayName;

    @Getter
    private String prefix;

    @Getter
    private String suffix;

    private boolean friendlyFire;

    private final List<String> entries;

    public NMSTeamImpl(NMSScoreboard scoreboard, String name) {
        this.scoreboard = scoreboard;
        this.name = name;
        this.displayName = name;
        this.prefix = "";
        this.suffix = "";
        this.friendlyFire = true;
        this.entries = new ArrayList<>();
    }

    @Override
    public void setDisplayName(@NotNull String displayName) {
        if (!this.displayName.equals(displayName)) {
            this.displayName = displayName;

            update();
        }
    }

    @Override
    public void setPrefix(@NotNull String prefix) {
        if (!this.prefix.equals(prefix)) {
            this.prefix = prefix;

            update();
        }
    }

    @Override
    public void setSuffix(@NotNull String suffix) {
        if (!this.suffix.equals(suffix)) {
            this.suffix = suffix;

            update();
        }
    }

    @Override
    public boolean allowFriendlyFire() {
        return this.friendlyFire;
    }

    @Override
    public void setAllowFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    @Override
    public void addEntry(@NotNull String entry) {
        if (!this.entries.contains(entry)) {
            this.entries.add(entry);
            update();
        }
    }

    @Override
    public void removeEntry(@NotNull String entry) {
        if (this.entries.remove(entry)) {
            update();
        }
    }

    @Override
    public @NotNull Collection<String> getEntries() {
        return Collections.unmodifiableCollection(this.entries);
    }

    @Override
    public void show() {

    }

    @Override
    public void show(@NotNull Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(createPacket(0));
    }

    @Override
    public void update() {
        //Mode'2' to update the Team!
        sendPacket(createPacket(2));
    }

    @Override
    public void update(@NotNull Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(createPacket(2));
    }

    @Override
    public void hide() {
        //Mode '1' to remove the Team!
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

    private PacketPlayOutScoreboardTeam createPacket(int mode) {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();

        NAME_FIELD.set(packet, this.name);
        DISPLAY_NAME_FIELD.set(packet, this.displayName);
        PREFIX_FIELD.set(packet, this.prefix);
        SUFFIX_FIELD.set(packet, this.suffix);
        ENTRIES_FIELD.set(packet, this.entries);
        FRIENDLY_FIRE_FIELD.set(packet, (this.friendlyFire ? 1 : 0));
        MODE_FIELD.set(packet, mode);

        return packet;
    }
}
