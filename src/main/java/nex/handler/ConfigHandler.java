/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex.handler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import nex.NetherEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
@Config(modid = NetherEx.MOD_ID, name = "NetherEx")
public class ConfigHandler
{
    private static Configuration config;
    public static Client client = new Client();
    public static Dimension dimension = new Dimension();
    public static Block block = new Block();
    public static PotionEffect potion_effect = new PotionEffect();
    public static Entity entity = new Entity();
    public static Biome biome = new Biome();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|ConfigHandler");

    public static class Client
    {
        public static Visual visual = new Visual();

        public static class Visual
        {
            public static boolean disableNetherFog = false;
        }
    }

    public static class Dimension
    {
        public static Nether nether = new Nether();

        public static class Nether
        {
            public static boolean generateSoulSand = false;
            public static boolean generateGravel = false;
            public static boolean isLavaInfinite = false;
            public static boolean enablePortalFix = true;
        }
    }

    public static class Block
    {
        public static Netherrack netherrack = new Netherrack();
        public static SoulSand soul_sand = new SoulSand();
        public static Magma magma = new Magma();
        public static Rime rime = new Rime();
        public static Thornstalk thornstalk = new Thornstalk();
        public static Hyphae hyphae = new Hyphae();

        public static class Netherrack
        {
            public static boolean allowAllShovelsToFlatten = false;
        }

        public static class SoulSand
        {
            public static boolean doesNetherwartUseNewGrowthSystem = true;
            public static boolean allowAllHoesToTill = false;
            public static boolean doesRequireIchor = true;
        }

        public static class Magma
        {
            public static boolean turnIntoLava = false;
        }

        public static class Rime
        {
            public static boolean canFreezeWater = true;
            public static boolean canFreezeLava = true;
            public static boolean canFreezeMobs = true;
        }

        public static class Thornstalk
        {
            public static boolean canDestroyItems = false;

            @Config.Comment("Mobs the Thornstalk shouldn't hurt")
            public static String[] blacklist = new String[]{
                    "minecraft:wither_skeleton",
                    "minecraft:zombie_pigman",
                    "nex:monster_spinout"
            };
        }

        public static class Hyphae
        {
            public static boolean doesSpread = false;
        }
    }

    public static class PotionEffect
    {
        public static Freeze freeze = new Freeze();
        public static Spore spore = new Spore();
        public static Lost lost = new Lost();

        public static class Freeze
        {
            @Config.Comment({"The higher the number, the rarer it is to thaw", "The lower the number, the more common it is to thaw"})
            @Config.RangeInt(min = 1, max = 2048)
            public static int chanceOfThawing = 1024;

            @Config.Comment("Mobs that shouldn't freeze")
            public static String[] blacklist = new String[]{
                    "minecraft:blaze",
                    "minecraft:ghast",
                    "minecraft:wither_skeleton",
                    "minecraft:polar_bear",
                    "nex:monster_wight",
                    "nex:monster_ember",
                    "nex:monster_spinout",
                    "nex:monster_bone_spider",
                    "nex:monster_brute"
            };
        }

        public static class Spore
        {
            @Config.Comment({"The higher the number, the rarer it is to spawn a Spore", "The lower the number, the more common it is to spawn a Spore"})
            @Config.RangeInt(min = 1, max = 256)
            public static int chanceOfSporeSpawning = 128;

            @Config.Comment("Mobs that shouldn't spawn Spores")
            public static String[] blacklist = new String[]{
                    "nex:monster_spore_creeper",
                    "nex:monster_spore",
                    "nex:neutral_mogus"
            };
        }

        public static class Lost
        {
            @Config.Comment({"The higher the number, the rarer it is to spawn a Ghastling", "The lower the number, the more common it is to spawn a Ghastling"})
            @Config.RangeInt(min = 1, max = 256)
            public static int chanceOfGhastlingSpawning = 256;
        }
    }

    public static class Entity
    {
        public static Ember ember = new Ember();
        public static Nethermite nethermite = new Nethermite();
        public static Spinout spinout = new Spinout();
        public static SporeCreeper spore_creeper = new SporeCreeper();
        public static Spore spore = new Spore();
        public static GhastQueen ghast_queen = new GhastQueen();
        public static Brute brute = new Brute();

        public static class Ember
        {
            @Config.Comment({"The higher the number, the rarer it is to set a player on fire", "The lower the number, the more common it is to set a player on fire"})
            @Config.RangeInt(min = 1, max = 128)
            public static int chanceOfSettingPlayerOnFire = 4;
        }

        public static class Nethermite
        {
            @Config.Comment({"The higher the number, the rarer it is for a Nethermite to spawn", "The lower the number, the more common it is for a Nethermite to spawn"})
            @Config.RangeInt(min = 1, max = 128)
            public static int chanceOfSpawning = 64;

            @Config.Comment("Blocks the Nethermite should spawn from")
            public static String[] whitelist = new String[]{
                    "minecraft:quartz_ore",
                    "nex:ore_quartz",
                    "tconstruct:ore",
                    "nethermetals:nether_coal_ore",
                    "nethermetals:nether_redstone_ore",
                    "nethermetals:nether_diamond_ore",
                    "nethermetals:nether_emerald_ore",
                    "nethermetals:nether_gold_ore",
                    "nethermetals:nether_iron_ore",
                    "nethermetals:nether_lapis_ore",
                    "nethermetals:nether_antimony_ore",
                    "nethermetals:nether_bismuth_ore",
                    "nethermetals:nether_copper_ore",
                    "nethermetals:nether_lead_ore",
                    "nethermetals:nether_mercury_ore",
                    "nethermetals:nether_nickel_ore",
                    "nethermetals:nether_platnium_ore",
                    "nethermetals:nether_silver_ore",
                    "nethermetals:nether_tin_ore",
                    "nethermetals:nether_zinc_ore",
                    "nethermetals:nether_aluminum_ore",
                    "nethermetals:nether_cadmium_ore",
                    "nethermetals:nether_chromium_ore",
                    "nethermetals:nether_iridium_ore",
                    "nethermetals:nether_magnesium_ore",
                    "nethermetals:nether_magnanese_ore",
                    "nethermetals:nether_osmium_ore",
                    "nethermetals:nether_plutonium_ore",
                    "nethermetals:nether_rutile_ore",
                    "nethermetals:nether_tantalum_ore",
                    "nethermetals:nether_titanium_ore",
                    "nethermetals:nether_tungsten_ore",
                    "nethermetals:nether_uramium_ore",
                    "nethermetals:nether_zirconium_ore"
            };
        }

        public static class Spinout
        {
            @Config.Comment({"The lower the number, the less time a Spinout spins", "The higher the number, the more time a Spinout spins"})
            @Config.RangeInt(min = 1, max = 512)
            public static int spinTime = 6;

            @Config.Comment({"The lower the number, the less time a Spinout goes without spinning", "The higher the number, the more time a Spinout goes without spinning"})
            @Config.RangeInt(min = 1, max = 512)
            public static int spinCooldown = 2;
        }

        public static class SporeCreeper
        {
            @Config.Comment({"The higher the number, the rarer it is for s Spore Creeper to spawn a Spore on death", "The lower the number, the more common it is for a Spore Creeper to spawn a Spore on death"})
            @Config.RangeInt(min = 1, max = 128)
            public static int chanceOfSporeSpawning = 12;
        }

        public static class Spore
        {
            @Config.Comment({"The lower the number, the less it takes a Spore to grow", "The higher the number, the more time it takes for a Spore to grow"})
            @Config.RangeInt(min = 1, max = 512)
            public static int growthTime = 60;

            @Config.Comment({"The lower the number, the less Spore Creeper spawn from a Spore", "The higher the number, the more Spore Creeper spawn from a Spore"})
            @Config.RangeInt(min = 1, max = 128)
            public static int creeperSpawns = 3;
        }

        public static class GhastQueen
        {
            @Config.Comment({"The lower the number, the less cooldown the Ghast Queen has after spawning Ghastlings", "The higher the number, the more cooldown the Ghast Queen has after spawning Ghastlings"})
            @Config.RangeInt(min = 1, max = 512)
            public static int ghastlingSpawnCooldown = 10;

            @Config.Comment({"The lower the number, the less Ghastling spawn", "The higher the number, the more Ghastling spawn"})
            @Config.RangeInt(min = 1, max = 128)
            public static int ghastlingSpawns = 4;
        }

        public static class Brute
        {
            @Config.Comment({"The lower the number, the less cooldown the Brute has after charging", "The higher the number, the more cooldown the Brute has after charging"})
            @Config.RangeInt(min = 1, max = 512)
            public static int chargeCooldown = 12;
        }
    }

    public static class Biome
    {
        public static Hell hell = new Hell();
        public static RuthlessSands ruthless_sands = new RuthlessSands();
        public static FungiForest fungi_forest = new FungiForest();
        public static TorridWasteland torrid_wasteland = new TorridWasteland();
        public static ArcticAbyss arctic_abyss = new ArcticAbyss();

        public static class Hell
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateFire = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateBrownMushrooms = true;
            public static boolean generateRedMushrooms = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateMagma = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateCrypts = true;
            public static boolean generateGraves = true;
            public static boolean generateGraveyards = true;
            public static boolean generateSarcophagus = true;
            public static boolean generateMausoleums = true;
            public static boolean generatePrisons = true;
            public static boolean generateVillages = true;

            @Config.Comment({"The lower the number, the rarer the Hell is", "The higher the number, the more common the Hell biome is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int biomeRarity = 10;

            @Config.Comment({"The lower the number, the rarer Lava Springs are", "The higher the number, the more common Lava Springs are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int lavaSpringRarity = 8;

            @Config.Comment({"The lower the number, the rarer Fire is", "The higher the number, the more common Fire is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int fireRarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int glowstonePass2Rarity = 10;

            @Config.LangKey("config.nex:biome.hell.quartzOreRarity")
            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number, the rarer Magma is", "The higher the number, the more common Magma is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int magmaRarity = 4;

            @Config.Comment({"The lower the number, the rarer Lava Traps are", "The higher the number, the more common Lava Traps are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int lavaTrapRarity = 16;

            @Config.Comment({"The higher the number, the rarer Crypts are", "The lower the number, the more common Crypts are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int cryptRarity = 32;

            @Config.Comment({"The higher the number, the rarer Graves are", "The lower the number, the more common Graves are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int graveRarity = 16;

            @Config.Comment({"The higher the number, the rarer Graveyards are", "The lower the number, the more common Graveyards are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int graveyardRarity = 64;

            @Config.Comment({"The higher the number, the rarer Sarcophagus are", "The lower the number, the more common Sarcophagus are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int sarcophagusRarity = 48;

            @Config.Comment({"The higher the number, the rarer Mausoleums are", "The lower the number, the more common Mausoleums are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int mausoleumRarity = 64;

            @Config.Comment({"The higher the number, the rarer Prisons are", "The lower the number, the more common Prisons are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int prisonRarity = 50;

            @Config.Comment({"The higher the number, the rarer Villages are", "The lower the number, the more common Villages are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int villageRarity = 1;
        }

        public static class RuthlessSands
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateThornstalk = true;
            public static boolean generateCrypts = true;
            public static boolean generateGraves = true;
            public static boolean generateGraveyards = true;
            public static boolean generateSarcophagus = true;
            public static boolean generateAltars = true;
            public static boolean generateWaypoints = true;

            @Config.Comment({"The lower the number, the rarer The Ruthless Sands is", "The higher the number, the more common the Ruthless Sands biome is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int biomeRarity = 8;

            @Config.Comment({"The lower the number, the rarer Lava Springs are", "The higher the number, the more common Lava Springs are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int lavaSpringRarity = 8;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number, the rarer Lava Traps are", "The higher the number, the more common Lava Traps are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int lavaTrapRarity = 16;

            @Config.Comment({"The lower the number, the rarer Thornstalk is", "The higher the number, the more common Thornstalk is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int thornstalkRarity = 10;

            @Config.Comment({"The higher the number, the rarer Crypts are", "The lower the number, the more common Crypts are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int cryptRarity = 32;

            @Config.Comment({"The higher the number, the rarer Graves are", "The lower the number, the more common Graves are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int graveRarity = 16;

            @Config.Comment({"The higher the number, the rarer Graveyards are", "The lower the number, the more common Graveyards are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int graveyardRarity = 64;

            @Config.Comment({"The higher the number, the rarer Sarcophagus are", "The lower the number, the more common Sarcophagus are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int sarcophagusRarity = 48;

            @Config.Comment({"The higher the number, the rarer Altars are", "The lower the number, the more common Altars are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int altarRarity = 48;

            @Config.Comment({"The higher the number, the rarer Waypoints are", "The lower the number, the more common Waypoints are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int waypointRarity = 32;

        }

        public static class FungiForest
        {
            public static boolean generateBiome = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateElderMushrooms = true;
            public static boolean generateEnokiMushrooms = true;
            public static boolean generateCrypts = true;
            public static boolean generateGraves = true;
            public static boolean generateGraveyards = true;
            public static boolean generateSarcophagus = true;
            public static boolean generateTemples = true;
            public static boolean generateCastles = true;

            @Config.Comment({"The lower the number, the rarer the Fungi Forest biome is", "The higher the number, the more common the Fungi Forest biome is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int biomeRarity = 4;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number, the rarer Elder Mushrooms are", "The higher the number, the more common Elder Mushrooms are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int elderMushroomRarity = 32;

            @Config.Comment({"The lower the number, the rarer Enoki Mushrooms are", "The higher the number, the more common Enoki Mushrooms are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int enokiMushroomRarity = 4;

            @Config.Comment({"The higher the number, the rarer Crypts are", "The lower the number, the more common Crypts are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int cryptRarity = 32;

            @Config.Comment({"The higher the number, the rarer Graves are", "The lower the number, the more common Graves are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int graveRarity = 16;

            @Config.Comment({"The higher the number, the rarer Graveyards are", "The lower the number, the more common Graveyards are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int graveyardRarity = 64;

            @Config.Comment({"The higher the number, the rarer Sarcophagus are", "The lower the number, the more common Sarcophagus are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int sarcophagusRarity = 48;

            @Config.Comment({"The higher the number, the rarer Temples are", "The lower the number, the more common Temples are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int templeRarity = 32;

            @Config.Comment({"The higher the number, the rarer Castles are", "The lower the number, the more common Castles are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int castleRarity = 64;
        }

        public static class TorridWasteland
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateFire = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateBasalt = true;
            public static boolean generateMagma = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateLavaPits = true;
            public static boolean generateCrypts = true;
            public static boolean generateGraves = true;
            public static boolean generateGraveyards = true;
            public static boolean generateSarcophagus = true;
            public static boolean generatePyramids = true;

            @Config.Comment({"The lower the number, the rarer the Torrid Wasteland biome is", "The higher the number, the more common the Torrid Wateland biome is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int biomeRarity = 6;

            @Config.Comment({"The lower the number, the rarer Lava Springs are", "The higher the number, the more common Lava Springs are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int lavaSpringRarity = 24;

            @Config.Comment({"The lower the number, the rarer Fire is", "The higher the number, the more common Fire is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int fireRarity = 32;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number, the rarer Basalt is", "The higher the number, the more common Basalt is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int basaltRarity = 12;

            @Config.Comment({"The lower the number, the rarer Magma is", "The higher the number, the more common Magma is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int magmaRarity = 12;

            @Config.Comment({"The lower the number, the rarer Lava Traps are", "The higher the number, the more common Lava Traps are is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int lavaTrapRarity = 48;

            @Config.Comment({"The lower the number, the rarer Lava Pis are", "The higher the number, the more common Lava Pits are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int lavaPitRarity = 8;

            @Config.Comment({"The higher the number, the rarer Crypts are", "The lower the number, the more common Crypts are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int cryptRarity = 32;

            @Config.Comment({"The higher the number, the rarer Graves are", "The lower the number, the more common Graves are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int graveRarity = 16;

            @Config.Comment({"The higher the number, the rarer Graveyards are", "The lower the number, the more common Graveyards are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int graveyardRarity = 64;

            @Config.Comment({"The higher the number, the rarer Sarcophagus are", "The lower the number, the more common Sarcophagus are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int sarcophagusRarity = 48;

            @Config.Comment({"The higher the number, the rarer Pyramids are", "The lower the number, the more common Pyramids are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int pyramidRarity = 2;
        }

        public static class ArcticAbyss
        {
            public static boolean generateBiome = true;
            public static boolean generateFire = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateRimeOre = true;
            public static boolean generateIchorPits = true;
            public static boolean generateCrypts = true;
            public static boolean generateGraves = true;
            public static boolean generateGraveyards = true;
            public static boolean generateSarcophagus = true;
            public static boolean generateLighthouses = true;
            public static boolean generateSpecimen = true;
            public static boolean generateTemples = true;
            public static boolean generateFossils = true;

            @Config.Comment({"The lower the number, the rarer the Arctic Abyss biome is", "The higher the number, the more common the Arctic Abyss biome is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int biomeRarity = 1;

            @Config.Comment({"The lower the number, the rarer Fire is", "The higher the number, the more common Fire is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int fireRarity = 5;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number, the rarer Rime Ore is", "The higher the number, the more common Rime Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public static int rimeOreRarity = 16;

            @Config.Comment({"The higher the number, the rarer Ichor Pits are", "The lower the number, the more common Ichor Pits are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int ichorPitRarity = 16;

            @Config.Comment({"The higher the number, the rarer it is for mobs to Freeze in the Arctic Abyss biome", "The lower the number, the more common it is for mobs to Freeze in the Arctic Abyss biome"})
            @Config.RangeInt(min = 1, max = 2048)
            public static int chanceOfFreezing = 512;

            @Config.Comment({"The higher the number, the rarer Crypts are", "The lower the number, the more common Crypts are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int cryptRarity = 32;

            @Config.Comment({"The higher the number, the rarer Graves are", "The lower the number, the more common Graves are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int graveRarity = 16;

            @Config.Comment({"The higher the number, the rarer Graveyards are", "The lower the number, the more common Graveyards are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int graveyardRarity = 64;

            @Config.Comment({"The higher the number, the rarer Sarcophagus are", "The lower the number, the more common Sarcophagus are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int sarcophagusRarity = 48;

            @Config.Comment({"The higher the number, the rarer Lighthouses are", "The lower the number, the more common Lighthouses are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int lighthouseRarity = 48;

            @Config.Comment({"The higher the number, the rarer Specimen are", "The lower the number, the more common Specimen are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int specimenRarity = 32;

            @Config.Comment({"The higher the number, the rarer Specimen are", "The lower the number, the more common Specimen are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int templeRarity = 64;

            @Config.Comment({"The higher the number, the rarer Fossils are", "The lower the number, the more common Fossils are"})
            @Config.RangeInt(min = 1, max = 128)
            public static int fossilRarity = 64;
        }
    }

    public static Configuration getConfig()
    {
        if(config == null)
        {
            try
            {
                MethodHandle CONFIGS = MethodHandles.lookup().unreflectGetter(ReflectionHelper.findField(ConfigManager.class, "CONFIGS"));
                Map<String, Configuration> configsMap = (Map<String, Configuration>) CONFIGS.invokeExact();

                String fileName = "NetherEx.cfg";
                Optional<Map.Entry<String, Configuration>> entryOptional = configsMap.entrySet().stream().filter(entry -> fileName.equals(new File(entry.getKey()).getName())).findFirst();

                entryOptional.ifPresent(stringConfigurationEntry -> config = stringConfigurationEntry.getValue());
            }
            catch(Throwable ignored)
            {
            }
        }

        return config;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if(event.getModID().equals(NetherEx.MOD_ID))
        {
            ConfigManager.load(NetherEx.MOD_ID, Config.Type.INSTANCE);
            LOGGER.info("Configuration has been saved.");
        }
    }
}
