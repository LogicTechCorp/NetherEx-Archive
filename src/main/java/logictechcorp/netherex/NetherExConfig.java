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

package logictechcorp.netherex;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class NetherExConfig
{
    private static final ForgeConfigSpec BLOCK_SPEC;
    private static final ForgeConfigSpec EFFECT_SPEC;
    private static final ForgeConfigSpec ENTITY_SPEC;
    private static final ForgeConfigSpec NETHER_SPEC;
    public static final BlockConfig BLOCK;
    public static final EffectConfig EFFECT;
    public static final EntityConfig ENTITY;
    public static final NetherConfig NETHER;

    static
    {
        Pair<BlockConfig, ForgeConfigSpec> blockSpecPair = new ForgeConfigSpec.Builder().configure(BlockConfig::new);
        BLOCK_SPEC = blockSpecPair.getRight();
        BLOCK = blockSpecPair.getLeft();

        Pair<EffectConfig, ForgeConfigSpec> effectSpecPair = new ForgeConfigSpec.Builder().configure(EffectConfig::new);
        EFFECT_SPEC = effectSpecPair.getRight();
        EFFECT = effectSpecPair.getLeft();

        Pair<EntityConfig, ForgeConfigSpec> entitySpecPair = new ForgeConfigSpec.Builder().configure(EntityConfig::new);
        ENTITY_SPEC = entitySpecPair.getRight();
        ENTITY = entitySpecPair.getLeft();

        Pair<NetherConfig, ForgeConfigSpec> netherSpecPair = new ForgeConfigSpec.Builder().configure(NetherConfig::new);
        NETHER_SPEC = netherSpecPair.getRight();
        NETHER = netherSpecPair.getLeft();
    }

    static void registerConfigs()
    {
        try
        {
            Files.createDirectory(Paths.get(FMLPaths.CONFIGDIR.get().toAbsolutePath().toString(), "netherex"));
        }
        catch(IOException ignored)
        {
        }

        ModLoadingContext loadingContext = ModLoadingContext.get();
        loadingContext.registerConfig(ModConfig.Type.COMMON, BLOCK_SPEC, "netherex/block-config.toml");
        loadingContext.registerConfig(ModConfig.Type.COMMON, EFFECT_SPEC, "netherex/effect-config.toml");
        loadingContext.registerConfig(ModConfig.Type.COMMON, ENTITY_SPEC, "netherex/entity-config.toml");
        loadingContext.registerConfig(ModConfig.Type.COMMON, NETHER_SPEC, "netherex/nether-config.toml");
    }

    public static class BlockConfig
    {
        //Rime settings
        public final ForgeConfigSpec.BooleanValue rimeCanFreezeWater;
        public final ForgeConfigSpec.BooleanValue rimeCanFreezeLava;
        public final ForgeConfigSpec.BooleanValue rimeCanFreezeEntities;

        BlockConfig(ForgeConfigSpec.Builder builder)
        {
            //Start block config
            builder.comment("Block configuration settings")
                    .push("block");

            //Rime config
            builder.comment("Rime configuration settings")
                    .push("rime");
            this.rimeCanFreezeWater = builder
                    .comment("Whether or not rime can freeze water.")
                    .define("canFreezeWater", true);
            this.rimeCanFreezeLava = builder
                    .comment("Whether or not rime can freeze lava.")
                    .define("canFreezeLava", true);
            this.rimeCanFreezeEntities = builder
                    .comment("Whether or not rime can freeze entities.")
                    .define("canFreezeLava", true);
            builder.pop();

            //End block config
            builder.pop();
        }
    }

    public static class EffectConfig
    {
        //Infested settings
        public final ForgeConfigSpec.BooleanValue infestedEffectCanPlayersSpawnSpores;
        public final ForgeConfigSpec.IntValue infestedEffectSporeSpawnChance;
        public final ForgeConfigSpec.ConfigValue<List<String>> infestedEffectEntityBlacklist;

        //Frozen settings
        public final ForgeConfigSpec.BooleanValue frozenEffectCanPlayersFreeze;
        public final ForgeConfigSpec.IntValue frozenEffectThawChance;
        public final ForgeConfigSpec.ConfigValue<List<String>> frozenEffectEntityBlacklist;

        EffectConfig(ForgeConfigSpec.Builder builder)
        {
            //Start effect config
            builder.comment("Effect configuration settings");
            builder.push("effect");

            //Infested effect config
            builder.comment("Infested effect configuration settings");
            builder.push("infested");
            this.infestedEffectCanPlayersSpawnSpores = builder
                    .comment("Whether or not players can be spawn spores when they have the Infested effect.")
                    .define("canPlayersSpawnSpores", false);
            this.infestedEffectSporeSpawnChance = builder
                    .comment("The chance that a Spore will spawn.")
                    .defineInRange("sporeSpawnChance", 128, 1, Integer.MAX_VALUE);
            this.infestedEffectEntityBlacklist = builder
                    .comment("Entities that shouldn't spawn spores when they have the Infested effect.")
                    .define("entityBlacklist", Arrays.asList(NetherEx.MOD_ID + ":spore", NetherEx.MOD_ID + ":spore_creeper", NetherEx.MOD_ID + ":mogus"));
            builder.pop();

            //Frozen effect config
            builder.comment("Frozen effect configuration settings");
            builder.push("frozen");
            this.frozenEffectCanPlayersFreeze = builder
                    .comment("Whether or not players can be frozen when they have the Frozen effect.")
                    .define("canPlayersFreeze", false);
            this.frozenEffectThawChance = builder
                    .comment("The chance that a frozen entity will thaw.")
                    .defineInRange("thawChance", 1024, 1, Integer.MAX_VALUE);
            this.frozenEffectEntityBlacklist = builder
                    .comment("Entities that shouldn't freeze when they have the Frozen effect.")
                    .define("entityBlacklist", Arrays.asList(NetherEx.MOD_ID + ":wight", NetherEx.MOD_ID + ":coolmar_spider"));
            builder.pop();

            //End effect config
            builder.pop();
        }
    }

    public static class EntityConfig
    {
        //Spore settings
        public final ForgeConfigSpec.IntValue sporeGrowthTime;
        public final ForgeConfigSpec.IntValue sporeHatchAmount;

        //Spinout settings
        public final ForgeConfigSpec.IntValue spinoutSpinTime;
        public final ForgeConfigSpec.IntValue spinoutSpinCooldown;

        EntityConfig(ForgeConfigSpec.Builder builder)
        {
            //Start entity config
            builder.comment("Entity configuration settings")
                    .push("entity");

            //Spore config
            builder.comment("Spore configuration settings")
                    .push("spore");
            this.sporeGrowthTime = builder
                    .comment("The number of seconds it takes to grown into the next stage.")
                    .defineInRange("growthTime", 60, 1, Integer.MAX_VALUE);
            this.sporeHatchAmount = builder
                    .comment("The number of Spore Creepers that can be hatched.")
                    .defineInRange("hatchAmount", 3, 1, Integer.MAX_VALUE);
            builder.pop();

            //Spinout config
            builder.comment("Spinout configuration settings")
                    .push("spinout");
            this.spinoutSpinTime = builder
                    .comment("The number of seconds a spin will last.")
                    .defineInRange("spinTime", 6, 1, Integer.MAX_VALUE);
            this.spinoutSpinCooldown = builder
                    .comment("The number of seconds between each spin.")
                    .defineInRange("spinCooldown", 2, 1, Integer.MAX_VALUE);
            builder.pop();

            //End entity config
            builder.pop();
        }
    }

    public static class NetherConfig
    {
        //Biome pack settings
        public final ForgeConfigSpec.BooleanValue biomePackUseDefaultBiomePack;
        public final ForgeConfigSpec.BooleanValue biomePackUseNetherExBiomePack;
        public final ForgeConfigSpec.BooleanValue biomePackUseBOPBiomePack;

        NetherConfig(ForgeConfigSpec.Builder builder)
        {
            //Start nether config
            builder.comment("Nether configuration settings")
                    .push("nether");

            //Start biome pack config
            builder.comment("Biome pack configuration settings")
                    .push("biome_packs");
            this.biomePackUseDefaultBiomePack = builder
                    .comment("Use the default biome pack.")
                    .define("useDefaultBiomePack", true);
            this.biomePackUseNetherExBiomePack = builder
                    .comment("Use the NetherEx biome pack.")
                    .define("useNetherExBiomePack", true);
            this.biomePackUseBOPBiomePack = builder
                    .comment("Use the Biomes O' Plenty biome pack.")
                    .define("useBOPBiomePack", true);
            builder.pop();

            //End nether config
            builder.pop();
        }
    }
}
