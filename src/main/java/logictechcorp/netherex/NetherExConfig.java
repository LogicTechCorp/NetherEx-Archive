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
    private static final ForgeConfigSpec ENTITY_SPEC;
    private static final ForgeConfigSpec EFFECT_SPEC;
    public static final Entity ENTITY;
    public static final Effect EFFECT;

    static
    {
        Pair<Entity, ForgeConfigSpec> entitySpecPair = new ForgeConfigSpec.Builder().configure(Entity::new);
        ENTITY_SPEC = entitySpecPair.getRight();
        ENTITY = entitySpecPair.getLeft();

        Pair<Effect, ForgeConfigSpec> effectSpecPair = new ForgeConfigSpec.Builder().configure(Effect::new);
        EFFECT_SPEC = effectSpecPair.getRight();
        EFFECT = effectSpecPair.getLeft();
    }

    public static void registerConfigs()
    {
        try
        {
            Files.createDirectory(Paths.get(FMLPaths.CONFIGDIR.get().toAbsolutePath().toString(), "netherex"));
        }
        catch (IOException ignored)
        {
            NetherEx.LOGGER.error("Failed to create netherex config directory.");
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NetherExConfig.ENTITY_SPEC, "netherex/entity-config.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NetherExConfig.EFFECT_SPEC, "netherex/effect-config.toml");
    }

    public static class Entity
    {
        //Spore settings
        public final ForgeConfigSpec.IntValue sporeGrowthTime;
        public final ForgeConfigSpec.IntValue sporeHatchAmount;

        //Spinout settings
        public final ForgeConfigSpec.IntValue spinoutSpinTime;
        public final ForgeConfigSpec.IntValue spinoutSpinCooldown;

        Entity(ForgeConfigSpec.Builder builder)
        {
            //Entity config
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

            builder.pop();
        }
    }

    public static class Effect
    {
        //Infested settings
        public final ForgeConfigSpec.IntValue infestedEffectSporeSpawnChance;
        public final ForgeConfigSpec.ConfigValue<List<String>> infestedEffectEntityBlacklist;

        Effect(ForgeConfigSpec.Builder builder)
        {
            //Effect config
            builder.comment("Effect configuration settings");
            builder.push("effect");

            //Infested effect config
            builder.comment("Infested configuration settings");
            builder.push("infested");
            this.infestedEffectSporeSpawnChance = builder
                    .comment("The chance that a Spore will spawn.")
                    .defineInRange("sporeSpawnChance", 128, 1, Integer.MAX_VALUE);
            this.infestedEffectEntityBlacklist = builder
                    .comment("Entities that shouldn't spawn spores when they have the Infested effect")
                    .define("entityBlacklist", Arrays.asList(NetherEx.MOD_ID + ":spore", NetherEx.MOD_ID + ":spore_creeper", NetherEx.MOD_ID + ":mogus"));
            builder.pop();

            builder.pop();
        }
    }
}
