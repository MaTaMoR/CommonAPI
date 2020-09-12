package me.matamor.commonapi.particles.v1_12_R1_Down;

import me.matamor.commonapi.particles.ParticleController;
import me.matamor.commonapi.particles.ParticleEffect;
import me.matamor.commonapi.utils.container.DataContainer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleControllerDown implements ParticleController {

    @Override
    public void playExplosionNormal(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.EXPLOSION_NORMAL.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playExplosionLarge(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.EXPLOSION_LARGE.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playExplosionHuge(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.EXPLOSION_HUGE.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playFireworksSpark(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.7);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0.7);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.7);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.FIREWORKS_SPARK.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playWaterBubble(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.WATER_BUBBLE.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playWaterSplash(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.WATER_SPLASH.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playWaterWake(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.WATER_WAKE.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playSuspended(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.SUSPENDED.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playSuspendedDepth(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.SUSPENDED_DEPTH.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playCrit(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.CRIT.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playCritMagic(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 5);

        Particles.CRIT_MAGIC.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playSmokeNormal(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 15);

        Particles.SMOKE_NORMAL.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playSmokeLarge(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 15);

        Particles.SMOKE_LARGE.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playSpell(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        int amount = container.getInt(ParticleEffect.AMOUNT, 6);

        int speed = container.getInt(ParticleEffect.SPEED, -1);
        if (speed < 0 || speed > 255) {
            speed = randomColor();
        }

        Particles.SPELL.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playSpellInstant(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        int amount = container.getInt(ParticleEffect.AMOUNT, 6);

        int speed = container.getInt(ParticleEffect.SPEED, -1);
        if (speed < 0 || speed > 255) {
            speed = randomColor();
        }

        Particles.SPELL_INSTANT.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playSpellMob(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        int amount = container.getInt(ParticleEffect.AMOUNT, 6);

        int speed = container.getInt(ParticleEffect.SPEED, -1);
        if (speed < 0 || speed > 255) {
            speed = randomColor();
        }

        Particles.SPELL_MOB.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playSpellMobAmbient(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        int amount = container.getInt(ParticleEffect.AMOUNT, 6);

        int speed = container.getInt(ParticleEffect.SPEED, -1);
        if (speed < 0 || speed > 255) {
            speed = randomColor();
        }

        Particles.SPELL_MOB_AMBIENT.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playSpellWitch(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        int amount = container.getInt(ParticleEffect.AMOUNT, 6);

        int speed = container.getInt(ParticleEffect.SPEED, -1);
        if (speed < 0 || speed > 255) {
            speed = randomColor();
        }

        Particles.SPELL_WITCH.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playDripWater(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.DRIP_WATER.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playDripLava(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.DRIP_LAVA.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playVillagerAngry(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.5);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0.5);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.5);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.VILLAGER_ANGRY.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playVillagerHappy(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.5);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0.5);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.5);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.VILLAGER_HAPPY.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playTownAura(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.TOWN_AURA.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playNote(Location location, DataContainer container, List<Player> target) {
        int color = container.getInt(ParticleEffect.NOTE_COLOR, -1);
        if (color < 0 || color > 24) {
            color = randomColor(24);
        }

        Particles.NoteColor noteColor = new Particles.NoteColor(color);

        Particles.NOTE.display(noteColor, location, target);
    }

    @Override
    public void playPortal(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.PORTAL.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playEnchantmentTable(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 2);
        int amount = container.getInt(ParticleEffect.AMOUNT, 20);

        Particles.ENCHANTMENT_TABLE.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playFlame(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.FLAME.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playLava(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.LAVA.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playFootstep(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 1);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 1);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 3);

        Particles.FOOTSTEP.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playCloud(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0.05);
        int amount = container.getInt(ParticleEffect.AMOUNT, 20);

        Particles.CLOUD.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playRedstone(Location location, DataContainer container, List<Player> target) {
        int colorRed = container.getInt(ParticleEffect.COLOR_RED, -1);
        int colorGreen = container.getInt(ParticleEffect.COLOR_GREEN, -1);
        int colorBlue = container.getInt(ParticleEffect.COLOR_BLUE, -1);

        if (colorRed < 0 || colorRed > 255) {
            colorRed = randomColor();
        }

        if (colorGreen < 0 || colorGreen > 255) {
            colorGreen = randomColor();
        }

        if (colorBlue < 0 ||colorBlue > 255) {
            colorBlue = randomColor();
        }

        Particles.ParticleColor color = new Particles.OrdinaryColor(colorRed, colorGreen, colorBlue);

        Particles.REDSTONE.display(color, location, target);
    }

    @Override
    public void playSnowball(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.SNOWBALL.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playSlime(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0.05);
        int amount = container.getInt(ParticleEffect.AMOUNT, 20);

        Particles.SLIME.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playHeart(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.HEART.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playBarrier(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.BARRIER.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playItemCrack(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        String blockMaterial = container.getString(ParticleEffect.ITEM_MATERIAL, "DIRT");
        Material material = Material.matchMaterial(blockMaterial);
        if (material == null) {
            material = Material.DIRT;
        }

        int blockData = container.getInt(ParticleEffect.ITEM_DATA, 0);

        Particles.ITEM_CRACK.display(new Particles.ItemData(material, (byte) blockData), offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playBlockCrack(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        String blockMaterial = container.getString(ParticleEffect.BLOCK_MATERIAL, "DIRT");
        Material material = Material.matchMaterial(blockMaterial);
        if (material == null) {
            material = Material.DIRT;
        }

        int blockData = container.getInt(ParticleEffect.BLOCK_DATA, 0);

        Particles.BLOCK_DUST.display(new Particles.BlockData(material, (byte) blockData), offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playBlockDust(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        String blockMaterial = container.getString(ParticleEffect.BLOCK_MATERIAL, "DIRT");
        Material material = Material.matchMaterial(blockMaterial);
        if (material == null) {
            material = Material.DIRT;
        }

        int blockData = container.getInt(ParticleEffect.BLOCK_DATA, 0);

        Particles.BLOCK_DUST.display(new Particles.BlockData(material, (byte) blockData), offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playWaterDrop(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.2);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.2);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 10);

        Particles.WATER_DROP.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playMobAppearance(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.MOB_APPEARANCE.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playDragonBreath(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.DRAGON_BREATH.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playEndRod(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.END_ROD.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playDamageIndicator(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.DAMAGE_INDICATOR.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    @Override
    public void playSweepAttack(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Y, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float speed = (float) container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.AMOUNT, 1);

        Particles.SWEEP_ATTACK.display(offsetX, offsetY, offsetZ, speed, amount, location, target);
    }

    private int randomColor() {
        return randomColor(255);
    }

    private int randomColor(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }
}
