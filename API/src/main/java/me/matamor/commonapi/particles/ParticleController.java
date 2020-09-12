package me.matamor.commonapi.particles;

import me.matamor.commonapi.utils.container.DataContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface ParticleController {

    void playExplosionNormal(Location location, DataContainer container, List<Player> target);

    void playExplosionLarge(Location location, DataContainer container, List<Player> target);

    void playExplosionHuge(Location location, DataContainer container, List<Player> target);

    void playFireworksSpark(Location location, DataContainer container, List<Player> target);

    void playWaterBubble(Location location, DataContainer container, List<Player> target);

    void playWaterSplash(Location location, DataContainer container, List<Player> target);

    void playWaterWake(Location location, DataContainer container, List<Player> target);

    void playSuspended(Location location, DataContainer container, List<Player> target);

    void playSuspendedDepth(Location location, DataContainer container, List<Player> target);

    void playCrit(Location location, DataContainer container, List<Player> target);

    void playCritMagic(Location location, DataContainer container, List<Player> target);

    void playSmokeNormal(Location location, DataContainer container, List<Player> target);

    void playSmokeLarge(Location location, DataContainer container, List<Player> target);

    void playSpell(Location location, DataContainer container, List<Player> target);

    void playSpellInstant(Location location, DataContainer container, List<Player> target);

    void playSpellMob(Location location, DataContainer container, List<Player> target);

    void playSpellMobAmbient(Location location, DataContainer container, List<Player> target);

    void playSpellWitch(Location location, DataContainer container, List<Player> target);

    void playDripWater(Location location, DataContainer container, List<Player> target);

    void playDripLava(Location location, DataContainer container, List<Player> target);

    void playVillagerAngry(Location location, DataContainer container, List<Player> target);

    void playVillagerHappy(Location location, DataContainer container, List<Player> target);

    void playTownAura(Location location, DataContainer container, List<Player> target);

    void playNote(Location location, DataContainer container, List<Player> target);

    void playPortal(Location location, DataContainer container, List<Player> target);

    void playEnchantmentTable(Location location, DataContainer container, List<Player> target);

    void playFlame(Location location, DataContainer container, List<Player> target);

    void playLava(Location location, DataContainer container, List<Player> target);

    void playFootstep(Location location, DataContainer container, List<Player> target);

    void playCloud(Location location, DataContainer container, List<Player> target);

    void playRedstone(Location location, DataContainer container, List<Player> target);

    void playSnowball(Location location, DataContainer container, List<Player> target);

    void playSlime(Location location, DataContainer container, List<Player> target);

    void playHeart(Location location, DataContainer container, List<Player> target);

    void playBarrier(Location location, DataContainer container, List<Player> target);

    void playItemCrack(Location location, DataContainer container, List<Player> target);

    void playBlockCrack(Location location, DataContainer container, List<Player> target);

    void playBlockDust(Location location, DataContainer container, List<Player> target);

    void playWaterDrop(Location location, DataContainer container, List<Player> target);

    void playMobAppearance(Location location, DataContainer container, List<Player> target);

    void playDragonBreath(Location location, DataContainer container, List<Player> target);

    void playEndRod(Location location, DataContainer container, List<Player> target);

    void playDamageIndicator(Location location, DataContainer container, List<Player> target);

    void playSweepAttack(Location location, DataContainer container, List<Player> target);

}
