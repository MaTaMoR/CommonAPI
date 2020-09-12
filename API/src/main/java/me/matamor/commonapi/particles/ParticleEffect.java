package me.matamor.commonapi.particles;

import lombok.Getter;
import me.matamor.commonapi.utils.Validate;
import me.matamor.commonapi.utils.container.DataContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public enum ParticleEffect {

    EXPLOSION_NORMAL {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playExplosionNormal(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by exploding ghast fireballs and wither skulls:
     * <ul>
     * <li>It looks like a gray ball which is fading away
     * <li>The speed value slightly influences the size of this particle effect
     * </ul>
     */
    EXPLOSION_LARGE {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playExplosionLarge(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by exploding tnt and creepers:
     * <ul>
     * <li>It looks like a crowd of gray balls which are fading away
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    EXPLOSION_HUGE {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playExplosionHuge(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by launching fireworks:
     * <ul>
     * <li>It looks like a white star which is sparkling
     * <li>The speed value influences the velocity at which the particle flies off
     * </ul>
     */
    FIREWORKS_SPARK {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playFireworksSpark(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by swimming entities and arrows in water:
     * <ul>
     * <li>It looks like a bubble
     * <li>The speed value influences the velocity at which the particle flies off
     * </ul>
     */
    WATER_BUBBLE {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playWaterBubble(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by swimming entities and shaking wolves:
     * <ul>
     * <li>It looks like a blue drop
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    WATER_SPLASH {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playWaterSplash(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed on water when fishing:
     * <ul>
     * <li>It looks like a blue droplet
     * <li>The speed value influences the velocity at which the particle flies off
     * </ul>
     */
    WATER_WAKE {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playWaterWake(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by water:
     * <ul>
     * <li>It looks like a tiny blue square
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    SUSPENDED {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSuspended(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by air when close to bedrock and the in the void:
     * <ul>
     * <li>It looks like a tiny gray square
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    SUSPENDED_DEPTH {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSuspendedDepth(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when landing a critical hit and by arrows:
     * <ul>
     * <li>It looks like a light brown cross
     * <li>The speed value influences the velocity at which the particle flies off
     * </ul>
     */
    CRIT {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playCrit(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when landing a hit with an enchanted weapon:
     * <ul>
     * <li>It looks like a cyan star
     * <li>The speed value influences the velocity at which the particle flies off
     * </ul>
     */
    CRIT_MAGIC {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playCritMagic(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by primed tnt {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, torches {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, droppers {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, dispensers {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, end portals {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, brewing stands and monster spawners:
     * <ul>
     * <li>It looks like a little gray cloud
     * <li>The speed value influences the velocity at which the particle flies off
     * </ul>
     */
    SMOKE_NORMAL {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSmokeNormal(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by fire {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, minecarts with furnace and blazes:
     * <ul>
     * <li>It looks like a large gray cloud
     * <li>The speed value influences the velocity at which the particle flies off
     * </ul>
     */
    SMOKE_LARGE {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSmokeLarge(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when splash potions or bottles o' enchanting hit something:
     * <ul>
     * <li>It looks like a white swirl
     * <li>The speed value causes the particle to only move upwards when set to 0
     * <li>Only the motion on the y-axis can be controlled {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, the motion on the x- and z-axis are multiplied by 0.1 when setting the values to 0
     * </ul>
     */
    SPELL {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSpell(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when instant splash potions hit something:
     * <ul>
     * <li>It looks like a white cross
     * <li>The speed value causes the particle to only move upwards when set to 0
     * <li>Only the motion on the y-axis can be controlled {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, the motion on the x- and z-axis are multiplied by 0.1 when setting the values to 0
     * </ul>
     */
    SPELL_INSTANT {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSpellInstant(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by entities with active potion effects:
     * <ul>
     * <li>It looks like a colored swirl
     * <li>The speed value causes the particle to be colored black when set to 0
     * <li>The particle color gets lighter when increasing the speed and darker when decreasing the speed
     * </ul>
     */
    SPELL_MOB {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSpellMob(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by entities with active potion effects applied through a beacon:
     * <ul>
     * <li>It looks like a transparent colored swirl
     * <li>The speed value causes the particle to be always colored black when set to 0
     * <li>The particle color gets lighter when increasing the speed and darker when decreasing the speed
     * </ul>
     */
    SPELL_MOB_AMBIENT {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSpellMobAmbient(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by witches:
     * <ul>
     * <li>It looks like a purple cross
     * <li>The speed value causes the particle to only move upwards when set to 0
     * <li>Only the motion on the y-axis can be controlled {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, the motion on the x- and z-axis are multiplied by 0.1 when setting the values to 0
     * </ul>
     */
    SPELL_WITCH {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSpellWitch(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by blocks beneath a water source:
     * <ul>
     * <li>It looks like a blue drip
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    DRIP_WATER {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playDripWater(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by blocks beneath a lava source:
     * <ul>
     * <li>It looks like an orange drip
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    DRIP_LAVA {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playDripLava(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when attacking a villager in a village:
     * <ul>
     * <li>It looks like a cracked gray heart
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    VILLAGER_ANGRY {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playVillagerAngry(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when using bone meal and trading with a villager in a village:
     * <ul>
     * <li>It looks like a green star
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    VILLAGER_HAPPY {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playVillagerHappy(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by mycelium:
     * <ul>
     * <li>It looks like a tiny gray square
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    TOWN_AURA {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playTownAura(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by note blocks:
     * <ul>
     * <li>It looks like a colored note
     * <li>The speed value causes the particle to be colored green when set to 0
     * </ul>
     */
    NOTE {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playNote(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by nether portals {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, endermen {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, ender pearls {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, eyes of ender {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, ender chests and dragon eggs:
     * <ul>
     * <li>It looks like a purple cloud
     * <li>The speed value influences the spread of this particle effect
     * </ul>
     */
    PORTAL {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playPortal(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by enchantment tables which are nearby bookshelves:
     * <ul>
     * <li>It looks like a cryptic white letter
     * <li>The speed value influences the spread of this particle effect
     * </ul>
     */
    ENCHANTMENT_TABLE {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playEnchantmentTable(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by torches {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, active furnaces {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, magma cubes and monster spawners:
     * <ul>
     * <li>It looks like a tiny flame
     * <li>The speed value influences the velocity at which the particle flies off
     * </ul>
     */
    FLAME {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playFlame(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by lava:
     * <ul>
     * <li>It looks like a spark
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    LAVA {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playLava(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is currently unused:
     * <ul>
     * <li>It looks like a transparent gray square
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    FOOTSTEP {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playFootstep(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when a mob dies:
     * <ul>
     * <li>It looks like a large white cloud
     * <li>The speed value influences the velocity at which the particle flies off
     * </ul>
     */
    CLOUD {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playCloud(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by redstone ore {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, powered redstone {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            
        }
    }, redstone torches and redstone repeaters:
     * <ul>
     * <li>It looks like a tiny colored cloud
     * <li>The speed value causes the particle to be colored red when set to 0
     * </ul>
     */
    REDSTONE {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playRedstone(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when snowballs hit a block:
     * <ul>
     * <li>It looks like a little piece with the snowball texture
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    SNOWBALL {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSnowball(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is currently unused:
     * <ul>
     * <li>It looks like a tiny white cloud
     * <li>The speed value influences the velocity at which the particle flies off
     * </ul>
     */
    SNOW_SHOVEL {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSnowball(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by slimes:
     * <ul>
     * <li>It looks like a tiny part of the slimeball icon
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    SLIME {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSlime(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when breeding and taming animals:
     * <ul>
     * <li>It looks like a red heart
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    HEART {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playHeart(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by barriers:
     * <ul>
     * <li>It looks like a red box with a slash through it
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    BARRIER {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playBarrier(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when breaking a tool or eggs hit a block:
     * <ul>
     * <li>It looks like a little piece with an item texture
     * </ul>
     */
    ITEM_CRACK {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playItemCrack(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when breaking blocks or sprinting:
     * <ul>
     * <li>It looks like a little piece with a block texture
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    BLOCK_CRACK {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playBlockCrack(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when falling:
     * <ul>
     * <li>It looks like a little piece with a block texture
     * </ul>
     */
    BLOCK_DUST {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playBlockDust(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed when rain hits the ground:
     * <ul>
     * <li>It looks like a blue droplet
     * <li>The speed value has no influence on this particle effect
     * </ul>
     */
    WATER_DROP {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playWaterDrop(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is currently unused:
     * <ul>
     * <li>It has no visual effect
     * </ul>
     */
    /**
     * A particle effect which is displayed by elder guardians:
     * <ul>
     * <li>It looks like the shape of the elder guardian
     * <li>The speed value has no influence on this particle effect
     * <li>The offset values have no influence on this particle effect
     * </ul>
     */
    MOB_APPEARANCE {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playMobAppearance(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by ender dragon:
     * <ul>
     * <li>It looks like witch spell
     * <li>The speed value has no influence on this particle effect
     * <li>The offset values have no influence on this particle effect
     * </ul>
     */
    DRAGON_BREATH {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playDragonBreath(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by skulker bullets and end rods:
     * <ul>
     * <li>It looks like slow falling snow
     * <li>The speed value has no influence on this particle effect
     * <li>The offset values have no influence on this particle effect
     * </ul>
     */
    END_ROD {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playEndRod(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by mobs when damaged:
     * <ul>
     * <li>It looks like small hearts
     * <li>The speed value has no influence on this particle effect
     * <li>The offset values have no influence on this particle effect
     * </ul>
     */
    DAMAGE_INDICATOR {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playDamageIndicator(location, DataContainer, target);
        }
    },
    /**
     * A particle effect which is displayed by swinging swords:
     * <ul>
     * <li>It looks like a swinging sword
     * <li>The speed value has no influence on this particle effect
     * <li>The offset values have no influence on this particle effect
     * </ul>
     */
    SWEEP_ATTACK  {
        @Override
        public void playParticle(Location location, DataContainer DataContainer, List<Player> target) {
            getParticleController().playSweepAttack(location, DataContainer, target);
        }
    };
    
    public abstract void playParticle(Location location, DataContainer DataContainer, List<Player> target);

    @Getter
    private static ParticleController particleController;

    public static void setParticleController(ParticleController particleController) {
        Validate.notNull(particleController, "ParticleController can't be null!");
        Validate.isTrue(ParticleEffect.particleController == null, "ParticleController is already set!");

        ParticleEffect.particleController = particleController;
    }

    public static ParticleController getParticleController() {
        Validate.notNull(ParticleEffect.particleController, "ParticleController is not set!");

        return ParticleEffect.particleController;
    }

    public static final String
            OFFSET_X = "Offset.X",
            OFFSET_Y = "Offset.Y",
            OFFSET_Z = "Offset.Z",
            SPEED = "Speed",
            AMOUNT = "Amount",
            COLOR_RED = "Color.Red",
            COLOR_GREEN = "Color.Green",
            COLOR_BLUE = "Color.Blue",
            NOTE_COLOR = "Note",
            BLOCK_MATERIAL = "Block.Material",
            BLOCK_DATA = "Block.Data",
            ITEM_MATERIAL = "Item.Material",
            ITEM_DATA = "Item.Data";
}
