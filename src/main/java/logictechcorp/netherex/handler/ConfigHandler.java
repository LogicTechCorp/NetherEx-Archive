/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.handler;

import logictechcorp.netherex.NetherEx;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config.LangKey("config." + NetherEx.MOD_ID + ":title")
@Config(modid = NetherEx.MOD_ID, name = "NetherEx/" + NetherEx.MOD_ID, category = "netherex")
public class ConfigHandler
{
    @Config.Name("internal")
    @Config.LangKey("config." + NetherEx.MOD_ID + ":internal")
    public static InternalConfig internalConfig = new InternalConfig();

    @Config.Name("client")
    @Config.LangKey("config." + NetherEx.MOD_ID + ":client")
    public static ClientConfig clientConfig = new ClientConfig();

    @Config.Name("compatibility")
    @Config.LangKey("config." + NetherEx.MOD_ID + ":compatibility")
    public static CompatibilityConfig compatibilityConfig = new CompatibilityConfig();

    @Config.Name("dimension")
    @Config.LangKey("config." + NetherEx.MOD_ID + ":dimension")
    public static DimensionConfig dimensionConfig = new DimensionConfig();

    @Config.Name("block")
    @Config.LangKey("config." + NetherEx.MOD_ID + ":block")
    public static BlockConfig blockConfig = new BlockConfig();

    @Config.Name("potion_effect")
    @Config.LangKey("config." + NetherEx.MOD_ID + ":potionEffect")
    public static PotionEffectConfig potionEffectConfig = new PotionEffectConfig();

    @Config.Name("entity")
    @Config.LangKey("config." + NetherEx.MOD_ID + ":entity")
    public static EntityConfig entityConfig = new EntityConfig();

    @Config.Name("biome")
    @Config.LangKey("config." + NetherEx.MOD_ID + ":biome")
    public static BiomeConfig biomeConfig = new BiomeConfig();

    public static class InternalConfig
    {
        @Config.Name("recipes")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":internal.recipes")
        public Recipes recipes = new Recipes();

        public class Recipes
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":internal.recipes.writeRecipesToGlobalConfigFolder")
            public boolean writeRecipesToGlobalConfigFolder = false;
        }
    }

    public static class ClientConfig
    {
        @Config.Name("visual")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":client.visual")
        public Visual visual = new Visual();

        public class Visual
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":client.visual.disableNetherFog")
            public boolean disableNetherFog = true;
        }
    }

    public static class CompatibilityConfig
    {
        @Config.Name("biomesoplenty")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":compatibility.biomesOPlenty")
        public BiomesOPlenty biomesOPlenty = new BiomesOPlenty();

        public class BiomesOPlenty
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":compatibility.biomesOPlenty.enableCompatibility")
            public boolean enableCompatibility = true;
        }
    }

    public static class DimensionConfig
    {
        @Config.Name("nether")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":dimension.nether")
        public Nether nether = new Nether();

        public class Nether
        {
            @Config.RequiresMcRestart
            @Config.LangKey("config." + NetherEx.MOD_ID + ":dimension.nether.overrideNether")
            public boolean overrideNether = true;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":dimension.nether.generateSoulSand")
            public boolean generateSoulSand = false;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":dimension.nether.generateGravel")
            public boolean generateGravel = false;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":dimension.nether.isLavaInfinite")
            public boolean isLavaInfinite = false;
        }
    }

    public static class BlockConfig
    {
        @Config.Name("blue_fire")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":block.blueFire")
        public BlueFire blueFire = new BlueFire();

        @Config.Name("nether_portal")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":block.netherPortal")
        public NetherPortal netherPortal = new NetherPortal();

        @Config.Name("soul_sand")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":block.soulSand")
        public SoulSand soulSand = new SoulSand();

        @Config.Name("nether_wart")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":block.netherWart")
        public NetherWart netherWart = new NetherWart();

        @Config.Name("magma")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":block.magma")
        public Magma magma = new Magma();

        @Config.Name("rime")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":block.rime")
        public Rime rime = new Rime();

        @Config.Name("thornstalk")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":block.thornstalk")
        public Thornstalk thornstalk = new Thornstalk();

        @Config.Name("hyphae")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":block.hyphae")
        public Hyphae hyphae = new Hyphae();

        public class BlueFire
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.blueFire.minEntityTicksAlight")
            @Config.Comment({"The minimum amount of ticks Blue Fire can set entities alight", "A number is randomly chosen from minEntityTicksAlight to maxEntityTicksAlight"})
            @Config.RangeInt(min = 0)
            public int minEntityTicksAlight = 0;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.blueFire.maxEntityTicksAlight")
            @Config.Comment({"The maximum amount of ticks Blue Fire can set entities alight", "A number is randomly chosen from minEntityTicksAlight to maxEntityTicksAlight"})
            @Config.RangeInt(min = 1)
            public int maxEntityTicksAlight = 70;
        }

        public class NetherPortal
        {
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.netherPortal.pigmanSpawnRarity")
            @Config.Comment({"The higher the number, the rarer it is for Pigman to spawn", "The lower the number, the more common it is for Pigman to spawn", "If set to 0, Pigman won't spawn"})
            public int pigmanSpawnRarity = 2000;
        }

        public class SoulSand
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.soulSand.useLavaAndIchorToHydrate")
            public boolean useLavaAndIchorToHydrate = false;
        }

        public class NetherWart
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.netherWart.growLikeCrops")
            public boolean growLikeCrops = true;

            @Config.RequiresMcRestart
            @Config.RangeInt(min = 0)
            @Config.Comment({"The higher the number, the slower Nether Wart grows", "The lower the number, the faster Nether Wart grows", "If set to 0, Nether Wart growth will default to Vanilla speed"})
            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.netherWart.growthTickSpeed")
            public int growthTickSpeed = 0;
        }

        public class Magma
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.magma.turnIntoLavaWhenBroken")
            public boolean turnIntoLavaWhenBroken = false;
        }

        public class Rime
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.rime.canFreezeWater")
            public boolean canFreezeWater = true;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.rime.canFreezeLava")
            public boolean canFreezeLava = true;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.rime.canFreezeMobs")
            public boolean canFreezeMobs = true;
        }

        public class Thornstalk
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.thornstalk.canDestroyItems")
            public boolean canDestroyItems = false;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.thornstalk.mobBlacklist")
            @Config.Comment("Mobs Thornstalk won't hurt")
            public String[] mobBlacklist = new String[]{
                    "minecraft:wither_skeleton",
                    "minecraft:zombie_pigman",
                    NetherEx.MOD_ID + ":spinout"
            };
        }

        public class Hyphae
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":block.hyphae.shouldSpread")
            public boolean shouldSpread = false;
        }
    }

    public static class PotionEffectConfig
    {
        @Config.Name("freeze")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":potionEffect.freeze")
        public Freeze freeze = new Freeze();

        @Config.Name("spore")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":potionEffect.spore")
        public Spore spore = new Spore();

        @Config.Name("lost")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":potionEffect.lost")
        public Lost lost = new Lost();

        public class Freeze
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":potionEffect.freeze.thawRarity")
            @Config.Comment({"The higher the number, the rarer it is to thaw", "The lower the number, the more common it is to thaw"})
            @Config.RangeInt(min = 1)
            public int thawRarity = 1024;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":potionEffect.freeze.mobBlacklist")
            @Config.Comment("Mobs that won't freeze")
            public String[] mobBlacklist = new String[]{
                    "minecraft:blaze",
                    "minecraft:ghast",
                    "minecraft:wither_skeleton",
                    "minecraft:polar_bear",
                    NetherEx.MOD_ID + ":wight",
                    NetherEx.MOD_ID + ":ember",
                    NetherEx.MOD_ID + ":spinout",
                    NetherEx.MOD_ID + ":bone_spider",
                    NetherEx.MOD_ID + ":brute"
            };
        }

        public class Spore
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":potionEffect.spore.sporeSpawnRarity")
            @Config.Comment({"The higher the number, the rarer it is to spawn a Spore", "The lower the number, the more common it is to spawn a Spore"})
            @Config.RangeInt(min = 1)
            public int sporeSpawnRarity = 128;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":potionEffect.spore.mobBlacklist")
            @Config.Comment("Mobs that shouldn't spawn Spores")
            public String[] mobBlacklist = new String[]{
                    NetherEx.MOD_ID + ":spore_creeper",
                    NetherEx.MOD_ID + ":spore",
                    NetherEx.MOD_ID + ":mogus"
            };
        }

        public class Lost
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":potionEffect.lost.ghastlingSpawnRarity")
            @Config.Comment({"The higher the number, the rarer it is to spawn a Ghastling", "The lower the number, the more common it is to spawn a Ghastling"})
            @Config.RangeInt(min = 1)
            public int ghastlingSpawnRarity = 256;
        }
    }

    public static class EntityConfig
    {
        @Config.Name("ember")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.ember")
        public Ember ember = new Ember();

        @Config.Name("nethermite")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.nethermite")
        public Nethermite nethermite = new Nethermite();

        @Config.Name("spinout")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.spinout")
        public Spinout spinout = new Spinout();

        @Config.Name("spore_creeper")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.sporeCreeper")
        public SporeCreeper sporeCreeper = new SporeCreeper();

        @Config.Name("spore")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.spore")
        public Spore spore = new Spore();

        @Config.Name("brute")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.brute")
        public Brute brute = new Brute();

        @Config.Name("ghast")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.ghast")
        public Ghast ghast = new Ghast();

        @Config.Name("ghast_queen")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.ghastQueen")
        public GhastQueen ghastQueen = new GhastQueen();

        @Config.Name("wither_skeleton")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.witherSkeleton")
        public WitherSkeleton witherSkeleton = new WitherSkeleton();

        public class Ember
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.ember.setPlayerOnFireRarity")
            @Config.Comment({"The higher the number, the rarer it is to set a player on fire", "The lower the number, the more common it is to set a player on fire", "If set to 0, Embers won't set Players on fire"})
            @Config.RangeInt(min = 0)
            public int setPlayerOnFireRarity = 2;
        }

        public class Nethermite
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.nethermite.spawnRarity")
            @Config.Comment({"The higher the number, the rarer it is for a Nethermite to spawn", "The lower the number, the more common it is for a Nethermite to spawn", "If set to 0, Nethermites won't spawn"})
            @Config.RangeInt(min = 0)
            public int spawnRarity = 64;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.nethermite.blockWhitelist")
            @Config.Comment("Blocks the Nethermite will spawn from")
            public String[] blockWhitelist = new String[]{
                    "minecraft:quartz_ore",
                    NetherEx.MOD_ID + ":ore_quartz",
                    NetherEx.MOD_ID + ":ore_amethyst",
                    NetherEx.MOD_ID + ":ore_rime",
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
            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.spinout.spinTime")
            @Config.Comment({"The lower the number, the less time a Spinout spins", "The higher the number, the more time a Spinout spins"})
            @Config.RangeInt(min = 1)
            public int spinTime = 6;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.spinout.spinCooldown")
            @Config.Comment({"The lower the number, the less time a Spinout goes without spinning", "The higher the number, the more time a Spinout goes without spinning"})
            @Config.RangeInt(min = 1)
            public int spinCooldown = 2;
        }

        public class SporeCreeper
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.sporeCreeper.sporeSpawnRarity")
            @Config.Comment({"The higher the number, the rarer it is for a Spore Creeper to spawn a Spore on death", "The lower the number, the more common it is for a Spore Creeper to spawn a Spore on death"})
            @Config.RangeInt(min = 1)
            public int sporeSpawnRarity = 12;
        }

        public class Spore
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.spore.growthTime")
            @Config.Comment({"The lower the number, the less it takes a Spore to grow", "The higher the number, the more time it takes for a Spore to grow"})
            @Config.RangeInt(min = 1)
            public int growthTime = 60;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.spore.creeperSpawnAmount")
            @Config.Comment({"The lower the number, the less Spore Creepers spawn from a Spore", "The higher the number, the more Spore Creepers spawn from a Spore"})
            @Config.RangeInt(min = 1)
            public int creeperSpawnAmount = 3;
        }

        public class Brute
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.brute.chargeCooldown")
            @Config.Comment({"The lower the number, the less cooldown the Brute has after charging", "The higher the number, the more cooldown the Brute has after charging"})
            @Config.RangeInt(min = 1)
            public int chargeCooldown = 2;
        }

        public class Ghast
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.ghast.meatDropRarity")
            @Config.Comment({"The lower the number, the more common it is for Ghast Meat to drop", "The higher the number, the rarer it is for Ghast Meat to drop", "If set to 0, Ghast Meat does not drop"})
            @Config.RangeInt(min = 0)
            public int meatDropRarity = 1;
        }

        public class GhastQueen
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.ghastQueen.ghastlingSpawnCooldown")
            @Config.Comment({"The lower the number, the less cooldown the Ghast Queen has after spawning Ghastlings", "The higher the number, the more cooldown the Ghast Queen has after spawning Ghastlings"})
            @Config.RangeInt(min = 1)
            public int ghastlingSpawnCooldown = 10;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.ghastQueen.ghastlingSpawnAmount")
            @Config.Comment({"The lower the number, the less Ghastling spawn", "The higher the number, the more Ghastling spawn"})
            @Config.RangeInt(min = 1)
            public int ghastlingSpawnAmount = 4;
        }

        public class WitherSkeleton
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":entity.witherSkeleton.boneDropRarity")
            @Config.Comment({"The lower the number, the more common it is for a Bone to drop", "The higher the number, the rarer it is for a Bone drop", "If set to 0, Bones do not drop"})
            @Config.RangeInt(min = 0)
            public int boneDropRarity = 4;
        }
    }

    public static class BiomeConfig
    {
        @Config.Name("hell")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":biome.hell")
        public Hell hell = new Hell();

        @Config.Name("ruthless_sands")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":biome.ruthlessSands")
        public RuthlessSands ruthlessSands = new RuthlessSands();

        @Config.Name("fungi_forest")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":biome.fungiForest")
        public FungiForest fungiForest = new FungiForest();

        @Config.Name("torrid_wasteland")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":biome.torridWasteland")
        public TorridWasteland torridWasteland = new TorridWasteland();

        @Config.Name("arctic_abyss")
        @Config.LangKey("config." + NetherEx.MOD_ID + ":biome.arcticAbyss")
        public ArcticAbyss arcticAbyss = new ArcticAbyss();

        public class Hell
        {
        }

        public class RuthlessSands
        {
        }

        public class FungiForest
        {
        }

        public class TorridWasteland
        {
        }

        public class ArcticAbyss
        {
            @Config.LangKey("config." + NetherEx.MOD_ID + ":biome.arcticAbyss.canPlayersFreeze")
            @Config.Comment({"Whether or not players can freeze in the Arctic Abyss"})
            public boolean canPlayersFreeze = false;

            @Config.LangKey("config." + NetherEx.MOD_ID + ":biome.arcticAbyss.mobFreezeRarity")
            @Config.Comment({"The higher the number, the rarer it is for mobs to freeze in the Arctic Abyss biome", "The lower the number, the more common it is for mobs to freeze in the Arctic Abyss biome"})
            @Config.RangeInt(min = 1)
            public int mobFreezeRarity = 512;
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
                NetherEx.LOGGER.info("Configuration has been saved.");
            }
        }
    }
}
