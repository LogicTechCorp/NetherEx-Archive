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

package logictechcorp.netherex.world.biome;

import logictechcorp.libraryex.LibraryEx;
import logictechcorp.libraryex.world.biome.BiomeData;
import logictechcorp.libraryex.world.generation.feature.BiomeDataFeatureWrapper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.world.generation.surfacebuilder.NetherSurfaceBuilderWrapper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NetherBiomeData extends BiomeData
{
    private static final RegistryObject<Feature<BiomeDataFeatureWrapper.Config>> featureWrapper = RegistryObject.of(LibraryEx.MOD_ID + ":biome_data_feature_wrapper", () -> (Class<Feature<?>>) (Object)Feature.class);
    private static final RegistryObject<SurfaceBuilder<NetherSurfaceBuilderWrapper.Config>> surfaceBuilderWrapper = RegistryObject.of(NetherEx.MOD_ID + ":nether_surface_builder_wrapper", () -> (Class<SurfaceBuilder<?>>) (Object)SurfaceBuilder.class);

    public NetherBiomeData(Biome biome, int generationWeight, boolean useDefaultFeatures, boolean isSubBiome)
    {
        super(biome, generationWeight, useDefaultFeatures, isSubBiome);
    }

    @Override
    public void configureBiome()
    {
        ObfuscationReflectionHelper.setPrivateValue(Biome.class, this.biome, new ConfiguredSurfaceBuilder<>(surfaceBuilderWrapper.orElse(null), new NetherSurfaceBuilderWrapper.Config(this)), "field_201875_ar");

        for(GenerationStage.Decoration stage : GenerationStage.Decoration.values())
        {
            this.defaultFeatures.computeIfAbsent(stage, k -> new ArrayList<>());
            List<ConfiguredFeature<?>> features = this.biome.getFeatures(stage);

            for(ConfiguredFeature feature : features)
            {
                this.defaultFeatures.get(stage).add(feature);
            }

            if(!this.useDefaultFeatures)
            {
                features.clear();
            }
        }

        for(GenerationStage.Decoration stage : GenerationStage.Decoration.values())
        {
            List<ConfiguredFeature<?>> features = this.customFeatures.get(stage);

            if(features != null)
            {
                this.biome.addFeature(stage, Biome.createDecoratedFeature(featureWrapper.orElse(null), new BiomeDataFeatureWrapper.Config(features), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
            }
        }
    }

    @Override
    public void resetBiome()
    {
        ObfuscationReflectionHelper.setPrivateValue(Biome.class, this.biome, ((NetherSurfaceBuilderWrapper.Config) this.biome.getSurfaceBuilder().config).getOriginalBuilder(), "field_201875_ar");

        for(GenerationStage.Decoration stage : GenerationStage.Decoration.values())
        {
            Iterator<ConfiguredFeature<?>> features = this.biome.getFeatures(stage).iterator();

            while(features.hasNext())
            {
                ConfiguredFeature<?> feature = features.next();
                IFeatureConfig featureConfig = feature.config;

                if(featureConfig instanceof DecoratedFeatureConfig)
                {
                    DecoratedFeatureConfig decoratedFeatureConfig = (DecoratedFeatureConfig) featureConfig;

                    if(decoratedFeatureConfig.feature.feature instanceof BiomeDataFeatureWrapper)
                    {
                        features.remove();
                    }
                }
            }
        }

        if(!this.useDefaultFeatures)
        {
            for(GenerationStage.Decoration stage : GenerationStage.Decoration.values())
            {
                this.defaultFeatures.computeIfAbsent(stage, k -> new ArrayList<>());
                List<ConfiguredFeature<?>> features = this.defaultFeatures.get(stage);

                for(ConfiguredFeature feature : features)
                {
                    this.biome.addFeature(stage, feature);
                }
            }
        }
    }
}
