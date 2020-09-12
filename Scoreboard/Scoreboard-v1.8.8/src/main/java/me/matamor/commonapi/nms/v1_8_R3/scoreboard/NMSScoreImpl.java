package me.matamor.commonapi.nms.v1_8_R3.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.scoreboard.nms.NMSObjective;
import me.matamor.commonapi.scoreboard.nms.NMSScore;
import me.matamor.commonapi.utils.Reflections;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@AllArgsConstructor
public class NMSScoreImpl implements NMSScore {

    public static final Reflections.FieldAccessor<String> OBJECTIVE_FIELD = Reflections.getField(PacketPlayOutScoreboardScore.class, "b", String.class);
    public static final Reflections.FieldAccessor<String> ENTRY_FIELD = Reflections.getField(PacketPlayOutScoreboardScore.class, "a", String.class);
    public static final Reflections.FieldAccessor<Integer> VALUE_FIELD = Reflections.getField(PacketPlayOutScoreboardScore.class, "c", int.class);
    public static final Reflections.FieldAccessor<Object> ACTION_FIELD = Reflections.getField(PacketPlayOutScoreboardScore.class, "d", Object.class);

    private static final PacketPlayOutScoreboardScore.EnumScoreboardAction[] ENUM_SCOREBOARD_ACTION = PacketPlayOutScoreboardScore.EnumScoreboardAction.values();

    @Getter
    private final NMSObjective objective;

    @Getter
    private final String entry;

    @Getter
    private int score;

    @Override
    public void setScore(int score) {
        if (this.score != score) {
            this.score = score;

            update();
        }
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
        //Mode '0' to update the score!
        sendPacket(createPacket(0));
    }

    @Override
    public void update(@NotNull Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(createPacket(0));
    }

    @Override
    public void hide() {
        //Mode '1' to remove the score!
        sendPacket(createPacket(1));
    }

    @Override
    public void hide(@NotNull Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(createPacket(1));
    }

    private void sendPacket(Packet<?> packet) {
        Collection<Player> players = this.objective.getScoreboard().getViewers();

        if (players.size() > 0) {
            for (Player player : players) {
                CraftPlayer craftPlayer = (CraftPlayer) player;

                craftPlayer.getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    private PacketPlayOutScoreboardScore createPacket(int mode) {
        PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore();

        OBJECTIVE_FIELD.set(packet, this.objective.getName());
        ENTRY_FIELD.set(packet, this.entry);
        VALUE_FIELD.set(packet, this.score);
        ACTION_FIELD.set(packet, ENUM_SCOREBOARD_ACTION[mode]);

        return packet;
    }
}
