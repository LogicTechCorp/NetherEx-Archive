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

package logictechcorp.netherex.world.generation.feature;

import logictechcorp.netherex.NetherEx;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NetherExFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, NetherEx.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURE_OVERRIDES = new DeferredRegister<>(ForgeRegistries.FEATURES, "minecraft");

    public static final RegistryObject<Feature<BigMushroomFeatureConfig>> BROWN_ELDER_MUSHROOM = FEATURES.register("brown_elder_mushroom", () -> new BrownElderMushroomFeature(BigMushroomFeatureConfig::deserialize));
    public static final RegistryObject<Feature<BigMushroomFeatureConfig>> RED_ELDER_MUSHROOM = FEATURES.register("red_elder_mushroom", () -> new RedElderMushroomFeature(BigMushroomFeatureConfig::deserialize));
    public static final RegistryObject<Feature<NoFeatureConfig>> ENOKI_MUSHROOM = FEATURES.register("enoki_mushroom", () -> new EnokiMushroomFeature(NoFeatureConfig::deserialize));

    static
    {
        FEATURE_OVERRIDES.register("glowstone_blob", () -> new GlowstoneBlobFeatureOverride(NoFeatureConfig::deserialize));
        FEATURE_OVERRIDES.register("nether_spring", () -> new NetherSpringOverride(NetherSpringOverride::deserialize));
    }
}
