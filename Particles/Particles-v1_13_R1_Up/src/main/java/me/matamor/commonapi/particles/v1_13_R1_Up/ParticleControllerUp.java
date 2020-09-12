package me.matamor.commonapi.particles.v1_13_R1_Up;

import me.matamor.commonapi.particles.ParticleController;
import me.matamor.commonapi.particles.ParticleEffect;
import me.matamor.commonapi.utils.container.DataContainer;
import net.minecraft.server.v1_13_R2.Material;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleControllerUp implements ParticleController {

    @Override
    public void playExplosionNormal(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.EXPLOSION_NORMAL, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playExplosionLarge(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.EXPLOSION_LARGE, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playExplosionHuge(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.EXPLOSION_HUGE, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playFireworksSpark(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0.7);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0.7);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0.7);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.FIREWORKS_SPARK, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playWaterBubble(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.WATER_BUBBLE, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playWaterSplash(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.WATER_SPLASH, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playWaterWake(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.WATER_WAKE, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playSuspended(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.SUSPENDED, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playSuspendedDepth(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.SUSPENDED_DEPTH, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playCrit(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.CRIT, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playCritMagic(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 5);

        for (Player player : target) {
            player.spawnParticle(Particle.CRIT_MAGIC, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playSmokeNormal(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 15);

        for (Player player : target) {
            player.spawnParticle(Particle.SMOKE_LARGE, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playSmokeLarge(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 15);

        for (Player player : target) {
            player.spawnParticle(Particle.SMOKE_LARGE, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playSpell(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        int amount = container.getInt(ParticleEffect.SPEED, 6);

        int speed = container.getInt(ParticleEffect.SPEED, -1);
        if (speed < 0 || speed > 255) {
            speed = randomColor();
        }

        for (Player player : target) {
            player.spawnParticle(Particle.SPELL, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playSpellInstant(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        int amount = container.getInt(ParticleEffect.SPEED, 6);

        int speed = container.getInt(ParticleEffect.SPEED, -1);
        if (speed < 0 || speed > 255) {
            speed = randomColor();
        }

        for (Player player : target) {
            player.spawnParticle(Particle.SPELL_INSTANT, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playSpellMob(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        int amount = container.getInt(ParticleEffect.SPEED, 6);

        int speed = container.getInt(ParticleEffect.SPEED, -1);
        if (speed < 0 || speed > 255) {
            speed = randomColor();
        }

        for (Player player : target) {
            player.spawnParticle(Particle.SPELL_MOB, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playSpellMobAmbient(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        int amount = container.getInt(ParticleEffect.SPEED, 6);

        int speed = container.getInt(ParticleEffect.SPEED, -1);
        if (speed < 0 || speed > 255) {
            speed = randomColor();
        }

        for (Player player : target) {
            player.spawnParticle(Particle.SPELL_MOB_AMBIENT, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playSpellWitch(Location location, DataContainer container, List<Player> target) {
        float offsetX = (float) container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        float offsetY = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0);
        float offsetZ = (float) container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        int amount = container.getInt(ParticleEffect.SPEED, 6);

        int speed = container.getInt(ParticleEffect.SPEED, -1);
        if (speed < 0 || speed > 255) {
            speed = randomColor();
        }

        for (Player player : target) {
            player.spawnParticle(Particle.SPELL_WITCH, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playDripWater(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.DRIP_WATER, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playDripLava(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.DRIP_LAVA, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playVillagerAngry(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0.5);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0.5);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0.5);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.VILLAGER_ANGRY, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playVillagerHappy(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0.5);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0.5);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0.5);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.VILLAGER_HAPPY, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playTownAura(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.TOWN_AURA, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playNote(Location location, DataContainer container, List<Player> target) {
        double color = container.getInt(ParticleEffect.NOTE_COLOR, -1);
        if (color < 0 || color > 24) {
            color = randomColor(24);
        }

        color = color / 24D;

        for (Player player : target) {
            player.spawnParticle(Particle.NOTE, location, 0, color, 0, 0, 1);
        }
    }

    @Override
    public void playPortal(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.PORTAL, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playEnchantmentTable(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 2);
        int amount = container.getInt(ParticleEffect.SPEED, 20);

        for (Player player : target) {
            player.spawnParticle(Particle.ENCHANTMENT_TABLE, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playFlame(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.FLAME, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playLava(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.LAVA, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playFootstep(Location location, DataContainer container, List<Player> target) {
        for (Player player : target) {
            player.playEffect(location, Effect.STEP_SOUND, Material.WOOD);
        }
    }

    @Override
    public void playCloud(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        double speed = container.getDouble(ParticleEffect.SPEED, 0.05);
        int amount = container.getInt(ParticleEffect.SPEED, 20);

        for (Player player : target) {
            player.spawnParticle(Particle.CLOUD, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playRedstone(Location location, DataContainer container, List<Player> target) {
        int amount = container.getInt(ParticleEffect.SPEED, 1);

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

        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(colorRed, colorGreen, colorBlue), 1);

        for (Player player : target) {
            player.spawnParticle(Particle.REDSTONE, location, amount, dustOptions);
        }
    }

    @Override
    public void playSnowball(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.SNOWBALL, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playSlime(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0.3);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0.3);
        double speed = container.getDouble(ParticleEffect.SPEED, 0.05);
        int amount = container.getInt(ParticleEffect.SPEED, 20);

        for (Player player : target) {
            player.spawnParticle(Particle.SLIME, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playHeart(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.CLOUD, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playBarrier(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.BARRIER, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playItemCrack(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.ITEM_CRACK, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playBlockCrack(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.BLOCK_CRACK, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playBlockDust(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.BLOCK_DUST, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playWaterDrop(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0.2);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0.2);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 10);

        for (Player player : target) {
            player.spawnParticle(Particle.WATER_DROP, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playMobAppearance(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.MOB_APPEARANCE, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playDragonBreath(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.DRAGON_BREATH, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playEndRod(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.END_ROD, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playDamageIndicator(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.DAMAGE_INDICATOR, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    @Override
    public void playSweepAttack(Location location, DataContainer container, List<Player> target) {
        double offsetX = container.getDouble(ParticleEffect.OFFSET_X, 0);
        double offsetY = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double offsetZ = container.getDouble(ParticleEffect.OFFSET_Z, 0);
        double speed = container.getDouble(ParticleEffect.SPEED, 0);
        int amount = container.getInt(ParticleEffect.SPEED, 1);

        for (Player player : target) {
            player.spawnParticle(Particle.SWEEP_ATTACK, location, amount, offsetX, offsetY, offsetZ, speed);
        }
    }

    private int randomColor() {
        return randomColor(255);
    }

    private int randomColor(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }
}

