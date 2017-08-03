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
    @Config.LangKey("config.nex:client")
    public static Client client = new Client();

    @Config.Name("dimension")
    @Config.LangKey("config.nex:dimension")
    public static Dimension dimension = new Dimension();

    @Config.Name("block")
    @Config.LangKey("config.nex:block")
    public static Block block = new Block();

    @Config.Name("potion_effect")
    @Config.LangKey("config.nex:potionEffect")
    public static PotionEffect potionEffect = new PotionEffect();

    @Config.Name("entity")
    @Config.LangKey("config.nex:entity")
    public static Entity entity = new Entity();

    @Config.Name("biome")
    @Config.LangKey("config.nex:biome")
    public static Biome biome = new Biome();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|ConfigHandler");

    public static class Client
    {
        @Config.Name("visual")
        @Config.LangKey("config.nex:client.visual")
        public Visual visual = new Visual();

        public class Visual
        {
            @Config.LangKey("config.nex:client.visual.disableNetherFog")
            public boolean disableNetherFog = true;
        }
    }

    public static class Dimension
    {
        @Config.Name("nether")
        @Config.LangKey("config.nex:dimension.nether")
        public Nether nether = new Nether();

        public class Nether
        {
            @Config.LangKey("config.nex:dimension.nether.generateSoulSand")
            public boolean generateSoulSand = false;

            @Config.LangKey("config.nex:dimension.nether.generateGravel")
            public boolean generateGravel = false;

            @Config.LangKey("config.nex:dimension.nether.isLavaInfinite")
            public boolean isLavaInfinite = false;
        }
    }

    public static class Block
    {
        @Config.Name("nether_portal")
        @Config.LangKey("config.nex:block.netherPortal")
        public NetherPortal netherPortal = new NetherPortal();

        @Config.Name("netherrack")
        @Config.LangKey("config.nex:block.netherrack")
        public Netherrack netherrack = new Netherrack();

        @Config.Name("soul_sand")
        @Config.LangKey("config.nex:block.soulSand")
        public SoulSand soulSand = new SoulSand();

        @Config.Name("magma")
        @Config.LangKey("config.nex:block.magma")
        public Magma magma = new Magma();

        @Config.Name("rime")
        @Config.LangKey("config.nex:block.rime")
        public Rime rime = new Rime();

        @Config.Name("thornstalk")
        @Config.LangKey("config.nex:block.thornstalk")
        public Thornstalk thornstalk = new Thornstalk();

        @Config.Name("hyphae")
        @Config.LangKey("config.nex:block.hyphae")
        public Hyphae hyphae = new Hyphae();

        public class NetherPortal
        {
            @Config.LangKey("config.nex:block.netherrack.allowPigmanSpawning")
            public boolean allowPigmanSpawning = true;

            @Config.RangeInt(min = 4, max = 2048)
            @Config.LangKey("config.nex:block.netherrack.pigmanSpawnRarity")
            @Config.Comment({"The higher the number, the rarer it is for Pigman to spawn", "The lower the number, the more common it is for Pigman to spawn"})
            public int pigmanSpawnRarity = 2000;
        }

        public class Netherrack
        {
            @Config.LangKey("config.nex:block.netherrack.allowAllShovelsToFlatten")
            public boolean allowAllShovelsToFlatten = false;
        }

        public class SoulSand
        {
            @Config.LangKey("config.nex:block.soulSand.doesNetherwartUseNewGrowthSystem")
            public boolean doesNetherwartUseNewGrowthSystem = true;

            @Config.LangKey("config.nex:block.soulSand.allowAllHoesToTill")
            public boolean allowAllHoesToTill = false;

            @Config.LangKey("config.nex:block.soulSand.doesRequireIchor")
            public boolean doesRequireIchor = true;
        }

        public class Magma
        {
            @Config.LangKey("config.nex:block.magma.turnIntoLava")
            public boolean turnIntoLava = false;
        }

        public class Rime
        {
            @Config.LangKey("config.nex:block.rime.canFreezeWater")
            public boolean canFreezeWater = true;

            @Config.LangKey("config.nex:block.rime.canFreezeLava")
            public boolean canFreezeLava = true;

            @Config.LangKey("config.nex:block.rime.canFreezeMobs")
            public boolean canFreezeMobs = true;
        }

        public class Thornstalk
        {
            @Config.LangKey("config.nex:block.thornstalk.canDestroyItems")
            public boolean canDestroyItems = false;

            @Config.LangKey("config.nex:block.thornstalk.blacklist")
            @Config.Comment("Mobs the Thornstalk shouldn't hurt")
            public String[] blacklist = new String[]{
                    "minecraft:wither_skeleton",
                    "minecraft:zombie_pigman",
                    "nex:monster_spinout"
            };
        }

        public class Hyphae
        {
            @Config.LangKey("config.nex:block.hyphae.doesSpread")
            public boolean doesSpread = false;
        }
    }

    public static class PotionEffect
    {
        @Config.Name("freeze")
        @Config.LangKey("config.nex:potionEffect.freeze")
        public Freeze freeze = new Freeze();

        @Config.Name("spore")
        @Config.LangKey("config.nex:potionEffect.spore")
        public Spore spore = new Spore();

        @Config.Name("lost")
        @Config.LangKey("config.nex:potionEffect.lost")
        public Lost lost = new Lost();

        public class Freeze
        {
            @Config.LangKey("config.nex:potionEffect.freeze.chanceOfThawing")
            @Config.Comment({"The higher the number, the rarer it is to thaw", "The lower the number, the more common it is to thaw"})
            @Config.RangeInt(min = 1, max = 2048)
            public int chanceOfThawing = 1024;

            @Config.LangKey("config.nex:potionEffect.freeze.blacklist")
            @Config.Comment("Mobs that shouldn't freeze")
            public String[] blacklist = new String[]{
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

        public class Spore
        {
            @Config.LangKey("config.nex:potionEffect.spore.chanceOfSporeSpawning")
            @Config.Comment({"The higher the number, the rarer it is to spawn a Spore", "The lower the number, the more common it is to spawn a Spore"})
            @Config.RangeInt(min = 1, max = 256)
            public int chanceOfSporeSpawning = 128;

            @Config.LangKey("config.nex:potionEffect.spore.blacklist")
            @Config.Comment("Mobs that shouldn't spawn Spores")
            public String[] blacklist = new String[]{
                    "nex:monster_spore_creeper",
                    "nex:monster_spore",
                    "nex:neutral_mogus"
            };
        }

        public class Lost
        {
            @Config.LangKey("config.nex:potionEffect.lost.chanceOfGhastlingSpawning")
            @Config.Comment({"The higher the number, the rarer it is to spawn a Ghastling", "The lower the number, the more common it is to spawn a Ghastling"})
            @Config.RangeInt(min = 1, max = 256)
            public int chanceOfGhastlingSpawning = 256;
        }
    }

    public static class Entity
    {
        @Config.Name("ember")
        @Config.LangKey("config.nex:entity.ember")
        public Ember ember = new Ember();

        @Config.Name("nethermite")
        @Config.LangKey("config.nex:entity.nethermite")
        public Nethermite nethermite = new Nethermite();

        @Config.Name("spinout")
        @Config.LangKey("config.nex:entity.spinout")
        public Spinout spinout = new Spinout();

        @Config.Name("spore_creeper")
        @Config.LangKey("config.nex:entity.sporeCreeper")
        public SporeCreeper sporeCreeper = new SporeCreeper();

        @Config.Name("spore")
        @Config.LangKey("config.nex:entity.spore")
        public Spore spore = new Spore();

        @Config.Name("brute")
        @Config.LangKey("config.nex:entity.brute")
        public Brute brute = new Brute();

        @Config.Name("ghast_queen")
        @Config.LangKey("config.nex:entity.ghastQueen")
        public GhastQueen ghastQueen = new GhastQueen();

        public class Ember
        {
            @Config.LangKey("config.nex:entity.ember.chanceOfSettingPlayerOnFire")
            @Config.Comment({"The higher the number, the rarer it is to set a player on fire", "The lower the number, the more common it is to set a player on fire"})
            @Config.RangeInt(min = 1, max = 256)
            public int chanceOfSettingPlayerOnFire = 1;
        }

        public class Nethermite
        {
            @Config.LangKey("config.nex:entity.nethermite.chanceOfSpawning")
            @Config.Comment({"The higher the number, the rarer it is for a Nethermite to spawn", "The lower the number, the more common it is for a Nethermite to spawn"})
            @Config.RangeInt(min = 1, max = 256)
            public int chanceOfSpawning = 64;

            @Config.LangKey("config.nex:entity.nethermite.whitelist")
            @Config.Comment("Blocks the Nethermite should spawn from")
            public String[] whitelist = new String[]{
                    "minecraft:quartz_ore",
                    "nex:ore_quartz",
                    "nex:ore_amethyst",
                    "nex:ore_rime",
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

        public class Spinout
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

        public class SporeCreeper
        {
            @Config.LangKey("config.nex:entity.sporeCreeper.chanceOfSporeSpawning")
            @Config.Comment({"The higher the number, the rarer it is for s Spore Creeper to spawn a Spore on death", "The lower the number, the more common it is for a Spore Creeper to spawn a Spore on death"})
            @Config.RangeInt(min = 1, max = 256)
            public int chanceOfSporeSpawning = 12;
        }

        public class Spore
        {
            @Config.LangKey("config.nex:entity.spore.growthTime")
            @Config.Comment({"The lower the number, the less it takes a Spore to grow", "The higher the number, the more time it takes for a Spore to grow"})
            @Config.RangeInt(min = 1, max = 512)
            public int growthTime = 60;

            @Config.LangKey("config.nex:entity.spore.creeperSpawns")
            @Config.Comment({"The lower the number, the less Spore Creeper spawn from a Spore", "The higher the number, the more Spore Creeper spawn from a Spore"})
            @Config.RangeInt(min = 1, max = 256)
            public int creeperSpawns = 3;
        }

        public class Brute
        {
            @Config.LangKey("config.nex:entity.brute.chargeCooldown")
            @Config.Comment({"The lower the number, the less cooldown the Brute has after charging", "The higher the number, the more cooldown the Brute has after charging"})
            @Config.RangeInt(min = 1, max = 512)
            public int chargeCooldown = 2;
        }

        public class GhastQueen
        {
            @Config.LangKey("config.nex:entity.ghastQueen.ghastlingSpawnCooldown")
            @Config.Comment({"The lower the number, the less cooldown the Ghast Queen has after spawning Ghastlings", "The higher the number, the more cooldown the Ghast Queen has after spawning Ghastlings"})
            @Config.RangeInt(min = 1, max = 512)
            public int ghastlingSpawnCooldown = 10;

            @Config.LangKey("config.nex:entity.ghastQueen.ghastlingSpawns")
            @Config.Comment({"The lower the number, the less Ghastling spawn", "The higher the number, the more Ghastling spawn"})
            @Config.RangeInt(min = 1, max = 256)
            public int ghastlingSpawns = 4;
        }
    }

    public static class Biome
    {
        @Config.Name("hell")
        @Config.LangKey("config.nex:biome.hell")
        public Hell hell = new Hell();

        @Config.Name("ruthless_sands")
        @Config.LangKey("config.nex:biome.ruthlessSands")
        public RuthlessSands ruthlessSands = new RuthlessSands();

        @Config.Name("fungi_forest")
        @Config.LangKey("config.nex:biome.fungiForest")
        public FungiForest fungiForest = new FungiForest();

        @Config.Name("torrid_wasteland")
        @Config.LangKey("config.nex:biome.torridWasteland")
        public TorridWasteland torridWasteland = new TorridWasteland();

        @Config.Name("arctic_abyss")
        @Config.LangKey("config.nex:biome.arcticAbyss")
        public ArcticAbyss arcticAbyss = new ArcticAbyss();

        public class Hell
        {
        }

        public class RuthlessSands
        {
        }

        public class FungiForest
        {
            @Config.LangKey("config.nex:biome.fungiForest.generateElderMushrooms")
            public boolean generateElderMushrooms = true;

            @Config.LangKey("config.nex:biome.fungiForest.elderMushroomRarity")
            @Config.Comment({"The lower the number, the rarer Elder Mushrooms are", "The higher the number, the more common Elder Mushrooms are"})
            @Config.RangeInt(min = 1, max = 256)
            public int elderMushroomRarity = 32;
        }

        public class TorridWasteland
        {
        }

        public class ArcticAbyss
        {
            @Config.LangKey("config.nex:biome.arcticAbyss.chanceOfFreezing")
            @Config.Comment({"The higher the number, the rarer it is for mobs to Freeze in the Arctic Abyss biome", "The lower the number, the more common it is for mobs to Freeze in the Arctic Abyss biome"})
            @Config.RangeInt(min = 1, max = 2048)
            public int chanceOfFreezing = 512;

        }
    }

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
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
