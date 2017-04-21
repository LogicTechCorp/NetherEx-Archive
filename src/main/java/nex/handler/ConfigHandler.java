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
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Config.LangKey("config.nex:title")
@Config(modid = NetherEx.MOD_ID, name = "NetherEx/NetherEx", category = "nex")
public class ConfigHandler
{
    @Config.Name("client")
    public static Client client = new Client();

    @Config.Name("dimension")
    public static Dimension dimension = new Dimension();

    @Config.Name("block")
    public static Block block = new Block();

    @Config.Name("potion_effect")
    public static PotionEffect potionEffect = new PotionEffect();

    @Config.Name("entity")
    public static Entity entity = new Entity();

    @Config.Name("biome")
    public static Biome biome = new Biome();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|ConfigHandler");

    @Config.LangKey("config.nex:client")
    public static class Client
    {
        @Config.Name("visual")
        public Visual visual = new Visual();

        @Config.LangKey("config.nex:client.visual")
        public static class Visual
        {
            @Config.LangKey("config.nex:client.visual.disableNetherFog")
            public boolean disableNetherFog = true;
        }
    }

    @Config.LangKey("config.nex:dimension")
    public static class Dimension
    {
        @Config.Name("nether")
        public Nether nether = new Nether();

        @Config.LangKey("config.nex:dimension.nether")
        public static class Nether
        {
            @Config.LangKey("config.nex:dimension.nether.generateSoulSand")
            public boolean generateSoulSand = false;

            @Config.LangKey("config.nex:dimension.nether.generateGravel")
            public boolean generateGravel = false;

            @Config.LangKey("config.nex:dimension.nether.isLavaInfinite")
            public boolean isLavaInfinite = false;

            @Config.LangKey("config.nex:dimension.nether.enablePortalFix")
            public boolean enablePortalFix = true;
        }
    }

    @Config.LangKey("config.nex:block")
    public static class Block
    {
        @Config.Name("netherrack")
        public Netherrack netherrack = new Netherrack();

        @Config.Name("soul_sand")
        public SoulSand soulSand = new SoulSand();

        @Config.Name("magma")
        public Magma magma = new Magma();

        @Config.Name("rime")
        public Rime rime = new Rime();

        @Config.Name("thornstalk")
        public Thornstalk thornstalk = new Thornstalk();

        @Config.Name("hyphae")
        public Hyphae hyphae = new Hyphae();

        @Config.LangKey("config.nex:block.netherrack")
        public static class Netherrack
        {
            @Config.LangKey("config.nex:block.netherrack.allowAllShovelsToFlatten")
            public boolean allowAllShovelsToFlatten = false;
        }

        @Config.LangKey("config.nex:block.soulSand")
        public static class SoulSand
        {
            @Config.LangKey("config.nex:block.soulSand.doesNetherwartUseNewGrowthSystem")
            public boolean doesNetherwartUseNewGrowthSystem = true;

            @Config.LangKey("config.nex:block.soulSand.allowAllHoesToTill")
            public boolean allowAllHoesToTill = false;

            @Config.LangKey("config.nex:block.soulSand.doesRequireIchor")
            public boolean doesRequireIchor = true;
        }

        @Config.LangKey("config.nex:block.magma")
        public static class Magma
        {
            @Config.LangKey("config.nex:block.magma.turnIntoLava")
            public boolean turnIntoLava = false;
        }

        @Config.LangKey("config.nex:block.rime")
        public static class Rime
        {
            @Config.LangKey("config.nex:block.rime.canFreezeWater")
            public boolean canFreezeWater = true;

            @Config.LangKey("config.nex:block.rime.canFreezeLava")
            public boolean canFreezeLava = true;

            @Config.LangKey("config.nex:block.rime.canFreezeMobs")
            public boolean canFreezeMobs = true;
        }

        @Config.LangKey("config.nex:block.thornstalk")
        public static class Thornstalk
        {
            @Config.LangKey("config.nex:block.thornstalk.canDestroyItems")
            public boolean canDestroyItems = false;

            @Config.LangKey("config.nex:block.thornstalk.blacklist")
            @Config.Comment("Mobs the Thornstalk shouldn't hurt")
            public String[] blacklist = new String[] {
                    "minecraft:wither_skeleton",
                    "minecraft:zombie_pigman",
                    "nex:monster_spinout"
            };
        }

        @Config.LangKey("config.nex:block.hyphae")
        public static class Hyphae
        {
            @Config.LangKey("config.nex:block.hyphae.doesSpread")
            public boolean doesSpread = false;
        }
    }

    @Config.LangKey("config.nex:potionEffect")
    public static class PotionEffect
    {
        @Config.Name("freeze")
        public Freeze freeze = new Freeze();

        @Config.Name("spore")
        public Spore spore = new Spore();

        @Config.Name("lost")
        public Lost lost = new Lost();

        @Config.LangKey("config.nex:potionEffect.freeze")
        public static class Freeze
        {
            @Config.LangKey("config.nex:potionEffect.freeze.chanceOfThawing")
            @Config.Comment({"The higher the number, the rarer it is to thaw", "The lower the number, the more common it is to thaw"})
            @Config.RangeInt(min = 1, max = 2048)
            public int chanceOfThawing = 1024;

            @Config.LangKey("config.nex:potionEffect.freeze.blacklist")
            @Config.Comment("Mobs that shouldn't freeze")
            public String[] blacklist = new String[] {
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

        @Config.LangKey("config.nex:potionEffect.spore")
        public static class Spore
        {
            @Config.LangKey("config.nex:potionEffect.spore.chanceOfSporeSpawning")
            @Config.Comment({"The higher the number, the rarer it is to spawn a Spore", "The lower the number, the more common it is to spawn a Spore"})
            @Config.RangeInt(min = 1, max = 256)
            public int chanceOfSporeSpawning = 128;

            @Config.LangKey("config.nex:potionEffect.spore.blacklist")
            @Config.Comment("Mobs that shouldn't spawn Spores")
            public String[] blacklist = new String[] {
                    "nex:monster_spore_creeper",
                    "nex:monster_spore",
                    "nex:neutral_mogus"
            };
        }

        @Config.LangKey("config.nex:potionEffect.lost")
        public static class Lost
        {
            @Config.LangKey("config.nex:potionEffect.lost.chanceOfGhastlingSpawning")
            @Config.Comment({"The higher the number, the rarer it is to spawn a Ghastling", "The lower the number, the more common it is to spawn a Ghastling"})
            @Config.RangeInt(min = 1, max = 256)
            public int chanceOfGhastlingSpawning = 256;
        }
    }

    @Config.LangKey("config.nex:entity")
    public static class Entity
    {
        @Config.Name("ember")
        public Ember ember = new Ember();

        @Config.Name("nethermite")
        public Nethermite nethermite = new Nethermite();

        @Config.Name("spinout")
        public Spinout spinout = new Spinout();

        @Config.Name("spore_creeper")
        public SporeCreeper sporeCreeper = new SporeCreeper();

        @Config.Name("client")
        public Spore spore = new Spore();

        @Config.Name("ghast_queen")
        public GhastQueen ghastQueen = new GhastQueen();

        @Config.Name("brute")
        public Brute brute = new Brute();

        @Config.LangKey("config.nex:entity.ember")
        public static class Ember
        {
            @Config.LangKey("config.nex:entity.ember.chanceOfSettingPlayerOnFire")
            @Config.Comment({"The higher the number, the rarer it is to set a player on fire", "The lower the number, the more common it is to set a player on fire"})
            @Config.RangeInt(min = 1, max = 128)
            public int chanceOfSettingPlayerOnFire = 4;
        }

        @Config.LangKey("config.nex:entity.nethermite")
        public static class Nethermite
        {
            @Config.LangKey("config.nex:entity.nethermite.chanceOfSpawning")
            @Config.Comment({"The higher the number, the rarer it is for a Nethermite to spawn", "The lower the number, the more common it is for a Nethermite to spawn"})
            @Config.RangeInt(min = 1, max = 128)
            public int chanceOfSpawning = 64;

            @Config.LangKey("config.nex:entity.nethermite.whitelist")
            @Config.Comment("Blocks the Nethermite should spawn from")
            public String[] whitelist = new String[] {
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

        @Config.LangKey("config.nex:entity.spinout")
        public static class Spinout
        {
            @Config.LangKey("config.nex:entity.spinout.spinTime")
            @Config.Comment({"The lower the number, the less time a Spinout spins", "The higher the number, the more time a Spinout spins"})
            @Config.RangeInt(min = 1, max = 512)
            public int spinTime = 6;

            @Config.LangKey("config.nex:entity.spinout.spinCooldown")
            @Config.Comment({"The lower the number, the less time a Spinout goes without spinning", "The higher the number, the more time a Spinout goes without spinning"})
            @Config.RangeInt(min = 1, max = 512)
            public int spinCooldown = 2;
        }

        @Config.LangKey("config.nex:entity.sporeCreeper")
        public static class SporeCreeper
        {
            @Config.LangKey("config.nex:entity.sporeCreeper.chanceOfSporeSpawning")
            @Config.Comment({"The higher the number, the rarer it is for s Spore Creeper to spawn a Spore on death", "The lower the number, the more common it is for a Spore Creeper to spawn a Spore on death"})
            @Config.RangeInt(min = 1, max = 128)
            public int chanceOfSporeSpawning = 12;
        }

        @Config.LangKey("config.nex:entity.spore")
        public static class Spore
        {
            @Config.LangKey("config.nex:entity.spore.growthTime")
            @Config.Comment({"The lower the number, the less it takes a Spore to grow", "The higher the number, the more time it takes for a Spore to grow"})
            @Config.RangeInt(min = 1, max = 512)
            public int growthTime = 60;

            @Config.LangKey("config.nex:entity.spore.creeperSpawns")
            @Config.Comment({"The lower the number, the less Spore Creeper spawn from a Spore", "The higher the number, the more Spore Creeper spawn from a Spore"})
            @Config.RangeInt(min = 1, max = 128)
            public int creeperSpawns = 3;
        }

        @Config.LangKey("config.nex:entity.brute")
        public static class Brute
        {
            @Config.LangKey("config.nex:entity.brute.chargeCooldown")
            @Config.Comment({"The lower the number, the less cooldown the Brute has after charging", "The higher the number, the more cooldown the Brute has after charging"})
            @Config.RangeInt(min = 1, max = 512)
            public int chargeCooldown = 12;
        }

        @Config.LangKey("config.nex:entity.ghastQueen")
        public static class GhastQueen
        {
            @Config.LangKey("config.nex:entity.ghastQueen.ghastlingSpawnCooldown")
            @Config.Comment({"The lower the number, the less cooldown the Ghast Queen has after spawning Ghastlings", "The higher the number, the more cooldown the Ghast Queen has after spawning Ghastlings"})
            @Config.RangeInt(min = 1, max = 512)
            public int ghastlingSpawnCooldown = 10;

            @Config.LangKey("config.nex:entity.ghastQueen.ghastlingSpawns")
            @Config.Comment({"The lower the number, the less Ghastling spawn", "The higher the number, the more Ghastling spawn"})
            @Config.RangeInt(min = 1, max = 128)
            public int ghastlingSpawns = 4;
        }
    }

    @Config.LangKey("config.nex:biome")
    public static class Biome
    {
        @Config.Name("hell")
        public Hell hell = new Hell();

        @Config.Name("ruthless_sands")
        public RuthlessSands ruthlessSands = new RuthlessSands();

        @Config.Name("fungi_forest")
        public FungiForest fungiForest = new FungiForest();

        @Config.Name("torrid_wasteland")
        public TorridWasteland torridWasteland = new TorridWasteland();

        @Config.Name("arctic_abyss")
        public ArcticAbyss arcticAbyss = new ArcticAbyss();

        @Config.LangKey("config.nex:biome.hell")
        public static class Hell
        {
            @Config.LangKey("config.nex:biome.hell.generateBiome")
            public boolean generateBiome = true;

            @Config.LangKey("config.nex:biome.hell.generateLavaSprings")
            public boolean generateLavaSprings = true;

            @Config.LangKey("config.nex:biome.hell.generateFire")
            public boolean generateFire = true;

            @Config.LangKey("config.nex:biome.hell.generateGlowstonePass1")
            public boolean generateGlowstonePass1 = true;

            @Config.LangKey("config.nex:biome.hell.generateGlowstonePass2")
            public boolean generateGlowstonePass2 = true;

            @Config.LangKey("config.nex:biome.hell.generateBrownMushrooms")
            public boolean generateBrownMushrooms = true;

            @Config.LangKey("config.nex:biome.hell.generateRedMushrooms")
            public boolean generateRedMushrooms = true;

            @Config.LangKey("config.nex:biome.hell.generateQuartzOre")
            public boolean generateQuartzOre = true;

            @Config.LangKey("config.nex:biome.hell.generateAmethystOre")
            public boolean generateAmethystOre = true;

            @Config.LangKey("config.nex:biome.hell.generateMagma")
            public boolean generateMagma = true;

            @Config.LangKey("config.nex:biome.hell.generateLavaTraps")
            public boolean generateLavaTraps = true;

            @Config.LangKey("config.nex:biome.hell.generateCrypts")
            public boolean generateCrypts = true;

            @Config.LangKey("config.nex:biome.hell.generateGraves")
            public boolean generateGraves = true;

            @Config.LangKey("config.nex:biome.hell.generateGraveyards")
            public boolean generateGraveyards = true;

            @Config.LangKey("config.nex:biome.hell.generateSarcophagus")
            public boolean generateSarcophagus = true;

            @Config.LangKey("config.nex:biome.hell.generateMausoleums")
            public boolean generateMausoleums = true;

            @Config.LangKey("config.nex:biome.hell.generatePrisons")
            public boolean generatePrisons = true;

            @Config.LangKey("config.nex:biome.hell.generateVillages")
            public boolean generateVillages = true;

            @Config.LangKey("config.nex:biome.hell.biomeRarity")
            @Config.Comment({"The lower the number, the rarer the Hell is", "The higher the number, the more common the Hell biome is"})
            @Config.RangeInt(min = 1, max = 128)
            public int biomeRarity = 10;

            @Config.LangKey("config.nex:biome.hell.lavaSpringRarity")
            @Config.Comment({"The lower the number, the rarer Lava Springs are", "The higher the number, the more common Lava Springs are"})
            @Config.RangeInt(min = 1, max = 128)
            public int lavaSpringRarity = 8;

            @Config.LangKey("config.nex:biome.hell.fireRarity")
            @Config.Comment({"The lower the number, the rarer Fire is", "The higher the number, the more common Fire is"})
            @Config.RangeInt(min = 1, max = 128)
            public int fireRarity = 10;

            @Config.LangKey("config.nex:biome.hell.glowstonePass1Rarity")
            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public int glowstonePass1Rarity = 10;

            @Config.LangKey("config.nex:biome.hell.glowstonePass2Rarity")
            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public int glowstonePass2Rarity = 10;

            @Config.LangKey("config.nex:biome.hell.quartzOreRarity")
            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public int quartzOreRarity = 16;

            @Config.LangKey("config.nex:biome.hell.amethystOreRarity")
            @Config.Comment({"The lower the number, the rarer Amethyst Ore is", "The higher the number, the more common Amethyst Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public int amethystOreRarity = 8;

            @Config.LangKey("config.nex:biome.hell.magmaRarity")
            @Config.Comment({"The lower the number, the rarer Magma is", "The higher the number, the more common Magma is"})
            @Config.RangeInt(min = 1, max = 128)
            public int magmaRarity = 4;

            @Config.LangKey("config.nex:biome.hell.lavaTrapRarity")
            @Config.Comment({"The lower the number, the rarer Lava Traps are", "The higher the number, the more common Lava Traps are"})
            @Config.RangeInt(min = 1, max = 128)
            public int lavaTrapRarity = 16;

            @Config.LangKey("config.nex:biome.hell.cryptRarity")
            @Config.Comment({"The higher the number, the rarer Crypts are", "The lower the number, the more common Crypts are"})
            @Config.RangeInt(min = 1, max = 128)
            public int cryptRarity = 64;

            @Config.LangKey("config.nex:biome.hell.graveRarity")
            @Config.Comment({"The higher the number, the rarer Graves are", "The lower the number, the more common Graves are"})
            @Config.RangeInt(min = 1, max = 128)
            public int graveRarity = 24;

            @Config.LangKey("config.nex:biome.hell.graveyardRarity")
            @Config.Comment({"The higher the number, the rarer Graveyards are", "The lower the number, the more common Graveyards are"})
            @Config.RangeInt(min = 1, max = 128)
            public int graveyardRarity = 64;

            @Config.LangKey("config.nex:biome.hell.sarcophagusRarity")
            @Config.Comment({"The higher the number, the rarer Sarcophagus are", "The lower the number, the more common Sarcophagus are"})
            @Config.RangeInt(min = 1, max = 128)
            public int sarcophagusRarity = 76;

            @Config.LangKey("config.nex:biome.hell.mausoleumRarity")
            @Config.Comment({"The higher the number, the rarer Mausoleums are", "The lower the number, the more common Mausoleums are"})
            @Config.RangeInt(min = 1, max = 128)
            public int mausoleumRarity = 64;

            @Config.LangKey("config.nex:biome.hell.prisonRarity")
            @Config.Comment({"The higher the number, the rarer Prisons are", "The lower the number, the more common Prisons are"})
            @Config.RangeInt(min = 1, max = 128)
            public int prisonRarity = 50;

            @Config.LangKey("config.nex:biome.hell.villageRarity")
            @Config.Comment({"The higher the number, the rarer Villages are", "The lower the number, the more common Villages are"})
            @Config.RangeInt(min = 1, max = 128)
            public int villageRarity = 8;
        }

        @Config.LangKey("config.nex:biome.ruthlessSands.")
        public static class RuthlessSands
        {
            @Config.LangKey("config.nex:biome.ruthlessSands.generateBiome")
            public boolean generateBiome = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateLavaSprings")
            public boolean generateLavaSprings = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateGlowstonePass1")
            public boolean generateGlowstonePass1 = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateGlowstonePass2")
            public boolean generateGlowstonePass2 = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateQuartzOre")
            public boolean generateQuartzOre = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateLavaTraps")
            public boolean generateLavaTraps = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateThornstalk")
            public boolean generateThornstalk = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateCrypts")
            public boolean generateCrypts = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateGraves")
            public boolean generateGraves = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateGraveyards")
            public boolean generateGraveyards = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateSarcophagus")
            public boolean generateSarcophagus = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateAltars")
            public boolean generateAltars = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.generateWaypoints")
            public boolean generateWaypoints = true;

            @Config.LangKey("config.nex:biome.ruthlessSands.biomeRarity")
            @Config.Comment({"The lower the number, the rarer The Ruthless Sands is", "The higher the number, the more common the Ruthless Sands biome is"})
            @Config.RangeInt(min = 1, max = 128)
            public int biomeRarity = 8;

            @Config.LangKey("config.nex:biome.ruthlessSands.lavaSpringRarity")
            @Config.Comment({"The lower the number, the rarer Lava Springs are", "The higher the number, the more common Lava Springs are"})
            @Config.RangeInt(min = 1, max = 128)
            public int lavaSpringRarity = 8;

            @Config.LangKey("config.nex:biome.ruthlessSands.glowstonePass1Rarity")
            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public int glowstonePass1Rarity = 10;

            @Config.LangKey("config.nex:biome.ruthlessSands.glowstonePass2Rarity")
            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public int glowstonePass2Rarity = 10;

            @Config.LangKey("config.nex:biome.ruthlessSands.quartzOreRarity")
            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public int quartzOreRarity = 16;

            @Config.LangKey("config.nex:biome.ruthlessSands.lavaTrapRarity")
            @Config.Comment({"The lower the number, the rarer Lava Traps are", "The higher the number, the more common Lava Traps are"})
            @Config.RangeInt(min = 1, max = 128)
            public int lavaTrapRarity = 16;

            @Config.LangKey("config.nex:biome.ruthlessSands.thornstalkRarity")
            @Config.Comment({"The lower the number, the rarer Thornstalk is", "The higher the number, the more common Thornstalk is"})
            @Config.RangeInt(min = 1, max = 128)
            public int thornstalkRarity = 10;

            @Config.LangKey("config.nex:biome.ruthlessSands.cryptRarity")
            @Config.Comment({"The higher the number, the rarer Crypts are", "The lower the number, the more common Crypts are"})
            @Config.RangeInt(min = 1, max = 128)
            public int cryptRarity = 64;

            @Config.LangKey("config.nex:biome.ruthlessSands.graveRarity")
            @Config.Comment({"The higher the number, the rarer Graves are", "The lower the number, the more common Graves are"})
            @Config.RangeInt(min = 1, max = 128)
            public int graveRarity = 24;

            @Config.LangKey("config.nex:biome.ruthlessSands.graveyardRarity")
            @Config.Comment({"The higher the number, the rarer Graveyards are", "The lower the number, the more common Graveyards are"})
            @Config.RangeInt(min = 1, max = 128)
            public int graveyardRarity = 64;

            @Config.LangKey("config.nex:biome.ruthlessSands.sarcophagusRarity")
            @Config.Comment({"The higher the number, the rarer Sarcophagus are", "The lower the number, the more common Sarcophagus are"})
            @Config.RangeInt(min = 1, max = 128)
            public int sarcophagusRarity = 76;

            @Config.LangKey("config.nex:biome.ruthlessSands.altarRarity")
            @Config.Comment({"The higher the number, the rarer Altars are", "The lower the number, the more common Altars are"})
            @Config.RangeInt(min = 1, max = 128)
            public int altarRarity = 64;

            @Config.LangKey("config.nex:biome.ruthlessSands.waypointRarity")
            @Config.Comment({"The higher the number, the rarer Waypoints are", "The lower the number, the more common Waypoints are"})
            @Config.RangeInt(min = 1, max = 128)
            public int waypointRarity = 32;
        }

        @Config.LangKey("config.nex:biome.fungiForest.")
        public static class FungiForest
        {
            @Config.LangKey("config.nex:biome.fungiForest.generateBiome")
            public boolean generateBiome = true;

            @Config.LangKey("config.nex:biome.fungiForest.generateGlowstonePass1")
            public boolean generateGlowstonePass1 = true;

            @Config.LangKey("config.nex:biome.fungiForest.generateGlowstonePass2")
            public boolean generateGlowstonePass2 = true;

            @Config.LangKey("config.nex:biome.fungiForest.generateQuartzOre")
            public boolean generateQuartzOre = true;

            @Config.LangKey("config.nex:biome.fungiForest.generateElderMushrooms")
            public boolean generateElderMushrooms = true;

            @Config.LangKey("config.nex:biome.fungiForest.generateEnokiMushrooms")
            public boolean generateEnokiMushrooms = true;

            @Config.LangKey("config.nex:biome.fungiForest.generateCrypts")
            public boolean generateCrypts = true;

            @Config.LangKey("config.nex:biome.fungiForest.generateGraves")
            public boolean generateGraves = true;

            @Config.LangKey("config.nex:biome.fungiForest.generateGraveyards")
            public boolean generateGraveyards = true;

            @Config.LangKey("config.nex:biome.fungiForest.generateSarcophagus")
            public boolean generateSarcophagus = true;

            @Config.LangKey("config.nex:biome.fungiForest.generateTemples")
            public boolean generateTemples = true;

            @Config.LangKey("config.nex:biome.fungiForest.generateCastles")
            public boolean generateCastles = true;

            @Config.LangKey("config.nex:biome.fungiForest.biomeRarity")
            @Config.Comment({"The lower the number, the rarer the Fungi Forest biome is", "The higher the number, the more common the Fungi Forest biome is"})
            @Config.RangeInt(min = 1, max = 128)
            public int biomeRarity = 4;

            @Config.LangKey("config.nex:biome.fungiForest.glowstonePass1Rarity")
            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public int glowstonePass1Rarity = 10;

            @Config.LangKey("config.nex:biome.fungiForest.glowstonePass2Rarity")
            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public int glowstonePass2Rarity = 10;

            @Config.LangKey("config.nex:biome.fungiForest.quartzOreRarity")
            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public int quartzOreRarity = 16;

            @Config.LangKey("config.nex:biome.fungiForest.elderMushroomRarity")
            @Config.Comment({"The lower the number, the rarer Elder Mushrooms are", "The higher the number, the more common Elder Mushrooms are"})
            @Config.RangeInt(min = 1, max = 128)
            public int elderMushroomRarity = 32;

            @Config.LangKey("config.nex:biome.fungiForest.enokiMushroomRarity")
            @Config.Comment({"The lower the number, the rarer Enoki Mushrooms are", "The higher the number, the more common Enoki Mushrooms are"})
            @Config.RangeInt(min = 1, max = 128)
            public int enokiMushroomRarity = 4;

            @Config.LangKey("config.nex:biome.fungiForest.cryptRarity")
            @Config.Comment({"The higher the number, the rarer Crypts are", "The lower the number, the more common Crypts are"})
            @Config.RangeInt(min = 1, max = 128)
            public int cryptRarity = 64;

            @Config.LangKey("config.nex:biome.fungiForest.graveRarity")
            @Config.Comment({"The higher the number, the rarer Graves are", "The lower the number, the more common Graves are"})
            @Config.RangeInt(min = 1, max = 128)
            public int graveRarity = 16;

            @Config.LangKey("config.nex:biome.fungiForest.graveyardRarity")
            @Config.Comment({"The higher the number, the rarer Graveyards are", "The lower the number, the more common Graveyards are"})
            @Config.RangeInt(min = 1, max = 128)
            public int graveyardRarity = 64;

            @Config.LangKey("config.nex:biome.fungiForest.sarcophagusRarity")
            @Config.Comment({"The higher the number, the rarer Sarcophagus are", "The lower the number, the more common Sarcophagus are"})
            @Config.RangeInt(min = 1, max = 128)
            public int sarcophagusRarity = 76;

            @Config.LangKey("config.nex:biome.fungiForest.templeRarity")
            @Config.Comment({"The higher the number, the rarer Temples are", "The lower the number, the more common Temples are"})
            @Config.RangeInt(min = 1, max = 128)
            public int templeRarity = 32;

            @Config.LangKey("config.nex:biome.fungiForest.castleRarity")
            @Config.Comment({"The higher the number, the rarer Castles are", "The lower the number, the more common Castles are"})
            @Config.RangeInt(min = 1, max = 128)
            public int castleRarity = 128;
        }

        @Config.LangKey("config.nex:biome.torridWasteland.")
        public static class TorridWasteland
        {
            @Config.LangKey("config.nex:biome.torridWasteland.generateBiome")
            public boolean generateBiome = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateLavaSprings")
            public boolean generateLavaSprings = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateFire")
            public boolean generateFire = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateGlowstonePass1")
            public boolean generateGlowstonePass1 = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateGlowstonePass2")
            public boolean generateGlowstonePass2 = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateQuartzOre")
            public boolean generateQuartzOre = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateBasalt")
            public boolean generateBasalt = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateMagma")
            public boolean generateMagma = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateLavaTraps")
            public boolean generateLavaTraps = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateLavaPits")
            public boolean generateLavaPits = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateCrypts")
            public boolean generateCrypts = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateGraves")
            public boolean generateGraves = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateGraveyards")
            public boolean generateGraveyards = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generateSarcophagus")
            public boolean generateSarcophagus = true;

            @Config.LangKey("config.nex:biome.torridWasteland.generatePyramids")
            public boolean generatePyramids = true;

            @Config.LangKey("config.nex:biome.torridWasteland.biomeRarity")
            @Config.Comment({"The lower the number, the rarer the Torrid Wasteland biome is", "The higher the number, the more common the Torrid Wateland biome is"})
            @Config.RangeInt(min = 1, max = 128)
            public int biomeRarity = 6;

            @Config.LangKey("config.nex:biome.torridWasteland.lavaSpringRarity")
            @Config.Comment({"The lower the number, the rarer Lava Springs are", "The higher the number, the more common Lava Springs are"})
            @Config.RangeInt(min = 1, max = 128)
            public int lavaSpringRarity = 24;

            @Config.LangKey("config.nex:biome.torridWasteland.fireRarity")
            @Config.Comment({"The lower the number, the rarer Fire is", "The higher the number, the more common Fire is"})
            @Config.RangeInt(min = 1, max = 128)
            public int fireRarity = 32;

            @Config.LangKey("config.nex:biome.torridWasteland.glowstonePass1Rarity")
            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public int glowstonePass1Rarity = 10;

            @Config.LangKey("config.nex:biome.torridWasteland.glowstonePass2Rarity")
            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public int glowstonePass2Rarity = 10;

            @Config.LangKey("config.nex:biome.torridWasteland.quartzOreRarity")
            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public int quartzOreRarity = 16;

            @Config.LangKey("config.nex:biome.torridWasteland.basaltRarity")
            @Config.Comment({"The lower the number, the rarer Basalt is", "The higher the number, the more common Basalt is"})
            @Config.RangeInt(min = 1, max = 128)
            public int basaltRarity = 12;

            @Config.LangKey("config.nex:biome.torridWasteland.magmaRarity")
            @Config.Comment({"The lower the number, the rarer Magma is", "The higher the number, the more common Magma is"})
            @Config.RangeInt(min = 1, max = 128)
            public int magmaRarity = 12;

            @Config.LangKey("config.nex:biome.torridWasteland.lavaTrapRarity")
            @Config.Comment({"The lower the number, the rarer Lava Traps are", "The higher the number, the more common Lava Traps are is"})
            @Config.RangeInt(min = 1, max = 128)
            public int lavaTrapRarity = 48;

            @Config.LangKey("config.nex:biome.torridWasteland.lavaPitRarity")
            @Config.Comment({"The lower the number, the rarer Lava Pis are", "The higher the number, the more common Lava Pits are"})
            @Config.RangeInt(min = 1, max = 128)
            public int lavaPitRarity = 8;

            @Config.LangKey("config.nex:biome.torridWasteland.cryptRarity")
            @Config.Comment({"The higher the number, the rarer Crypts are", "The lower the number, the more common Crypts are"})
            @Config.RangeInt(min = 1, max = 128)
            public int cryptRarity = 64;

            @Config.LangKey("config.nex:biome.torridWasteland.graveRarity")
            @Config.Comment({"The higher the number, the rarer Graves are", "The lower the number, the more common Graves are"})
            @Config.RangeInt(min = 1, max = 128)
            public int graveRarity = 24;

            @Config.LangKey("config.nex:biome.torridWasteland.graveyardRarity")
            @Config.Comment({"The higher the number, the rarer Graveyards are", "The lower the number, the more common Graveyards are"})
            @Config.RangeInt(min = 1, max = 128)
            public int graveyardRarity = 64;

            @Config.LangKey("config.nex:biome.torridWasteland.sarcophagusRarity")
            @Config.Comment({"The higher the number, the rarer Sarcophagus are", "The lower the number, the more common Sarcophagus are"})
            @Config.RangeInt(min = 1, max = 128)
            public int sarcophagusRarity = 76;

            @Config.LangKey("config.nex:biome.torridWasteland.pyramidRarity")
            @Config.Comment({"The higher the number, the rarer Pyramids are", "The lower the number, the more common Pyramids are"})
            @Config.RangeInt(min = 1, max = 128)
            public int pyramidRarity = 4;
        }

        @Config.LangKey("config.nex:biome.arcticAbyss.")
        public static class ArcticAbyss
        {
            @Config.LangKey("config.nex:biome.arcticAbyss.generateBiome")
            public boolean generateBiome = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateFire")
            public boolean generateFire = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateGlowstonePass1")
            public boolean generateGlowstonePass1 = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateGlowstonePass2")
            public boolean generateGlowstonePass2 = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateQuartzOre")
            public boolean generateQuartzOre = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateRimeOre")
            public boolean generateRimeOre = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateIchorPits")
            public boolean generateIchorPits = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateCrypts")
            public boolean generateCrypts = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateGraves")
            public boolean generateGraves = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateGraveyards")
            public boolean generateGraveyards = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateSarcophagus")
            public boolean generateSarcophagus = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateFossils")
            public boolean generateFossils = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generatePrisons")
            public boolean generatePrisons = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateChainIslands")
            public boolean generateChainIslands = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateHangingChains")
            public boolean generateHangingChains = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.generateFallenChains")
            public boolean generateFallenChains = true;

            @Config.LangKey("config.nex:biome.arcticAbyss.biomeRarity")
            @Config.Comment({"The lower the number, the rarer the Arctic Abyss biome is", "The higher the number, the more common the Arctic Abyss biome is"})
            @Config.RangeInt(min = 1, max = 128)
            public int biomeRarity = 1;

            @Config.LangKey("config.nex:biome.arcticAbyss.fireRarity")
            @Config.Comment({"The lower the number, the rarer Fire is", "The higher the number, the more common Fire is"})
            @Config.RangeInt(min = 1, max = 128)
            public int fireRarity = 5;

            @Config.LangKey("config.nex:biome.arcticAbyss.glowstonePass1Rarity")
            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public int glowstonePass1Rarity = 10;

            @Config.LangKey("config.nex:biome.arcticAbyss.glowstonePass2Rarity")
            @Config.Comment({"The lower the number, the rarer Glowstone is", "The higher the number, the more common Glowstone is"})
            @Config.RangeInt(min = 1, max = 128)
            public int glowstonePass2Rarity = 10;

            @Config.LangKey("config.nex:biome.arcticAbyss.quartzOreRarity")
            @Config.Comment({"The lower the number, the rarer Quartz Ore is", "The higher the number, the more common Quartz Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public int quartzOreRarity = 16;

            @Config.LangKey("config.nex:biome.arcticAbyss.rimeOreRarity")
            @Config.Comment({"The lower the number, the rarer Rime Ore is", "The higher the number, the more common Rime Ore is"})
            @Config.RangeInt(min = 1, max = 128)
            public int rimeOreRarity = 16;

            @Config.LangKey("config.nex:biome.arcticAbyss.ichorPitRarity")
            @Config.Comment({"The higher the number, the rarer Ichor Pits are", "The lower the number, the more common Ichor Pits are"})
            @Config.RangeInt(min = 1, max = 128)
            public int ichorPitRarity = 16;

            @Config.LangKey("config.nex:biome.arcticAbyss.chanceOfFreezing")
            @Config.Comment({"The higher the number, the rarer it is for mobs to Freeze in the Arctic Abyss biome", "The lower the number, the more common it is for mobs to Freeze in the Arctic Abyss biome"})
            @Config.RangeInt(min = 1, max = 2048)
            public int chanceOfFreezing = 512;

            @Config.LangKey("config.nex:biome.arcticAbyss.cryptRarity")
            @Config.Comment({"The higher the number, the rarer Crypts are", "The lower the number, the more common Crypts are"})
            @Config.RangeInt(min = 1, max = 128)
            public int cryptRarity = 64;

            @Config.LangKey("config.nex:biome.arcticAbyss.graveRarity")
            @Config.Comment({"The higher the number, the rarer Graves are", "The lower the number, the more common Graves are"})
            @Config.RangeInt(min = 1, max = 128)
            public int graveRarity = 24;

            @Config.LangKey("config.nex:biome.arcticAbyss.graveyardRarity")
            @Config.Comment({"The higher the number, the rarer Graveyards are", "The lower the number, the more common Graveyards are"})
            @Config.RangeInt(min = 1, max = 128)
            public int graveyardRarity = 64;

            @Config.LangKey("config.nex:biome.arcticAbyss.sarcophagusRarity")
            @Config.Comment({"The higher the number, the rarer Sarcophagus are", "The lower the number, the more common Sarcophagus are"})
            @Config.RangeInt(min = 1, max = 128)
            public int sarcophagusRarity = 76;

            @Config.LangKey("config.nex:biome.arcticAbyss.fossilRarity")
            @Config.Comment({"The higher the number, the rarer Fossils are", "The lower the number, the more common Fossils are"})
            @Config.RangeInt(min = 1, max = 128)
            public int fossilRarity = 64;

            @Config.LangKey("config.nex:biome.arcticAbyss.prisonRarity")
            @Config.Comment({"The higher the number, the rarer Prions are", "The lower the number, the more common Prisons are"})
            @Config.RangeInt(min = 1, max = 128)
            public int prisonRarity = 4;

            @Config.LangKey("config.nex:biome.arcticAbyss.chainIslandRarity")
            @Config.Comment({"The higher the number, the rarer Chain Islands are", "The lower the number, the more common Chain Islands are"})
            @Config.RangeInt(min = 1, max = 128)
            public int chainIslandRarity = 3;

            @Config.LangKey("config.nex:biome.arcticAbyss.hangingChainRarity")
            @Config.Comment({"The higher the number, the rarer Hanging Chains are", "The lower the number, the more common Hanging Chains are"})
            @Config.RangeInt(min = 1, max = 128)
            public int hangingChainRarity = 24;

            @Config.LangKey("config.nex:biome.arcticAbyss.fallenChainRarity")
            @Config.Comment({"The higher the number, the rarer Fallen Chains are", "The lower the number, the more common Fallen Chains are"})
            @Config.RangeInt(min = 1, max = 128)
            public int fallenChainRarity = 12;
        }
    }

    @Mod.EventBusSubscriber
    public static class ConfigSyncHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if(event.getModID().equals(NetherEx.MOD_ID))
            {
                ConfigManager.sync(NetherEx.MOD_ID, Config.Type.INSTANCE);
                LOGGER.info("Configuration has been saved.");
            }
        }
    }
}
