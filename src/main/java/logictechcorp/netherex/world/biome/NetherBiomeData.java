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

import logictechcorp.libraryex.world.biome.BiomeData;
import logictechcorp.netherex.world.generation.surfacebuilder.NetherExSurfaceBuilders;
import logictechcorp.netherex.world.generation.surfacebuilder.NetherSurfaceBuilderWrapper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class NetherBiomeData extends BiomeData
{
    public NetherBiomeData(Biome biome, int generationWeight, boolean useDefaultEntities, boolean useDefaultCarvers, boolean useDefaultFeatures, boolean useDefaultStructures, boolean isSubBiome)
    {
        super(biome, generationWeight, useDefaultEntities, useDefaultCarvers, useDefaultFeatures, useDefaultStructures, isSubBiome);
    }

    @Override
    public void configureBiome()
    {
        ObfuscationReflectionHelper.setPrivateValue(Biome.class, this.biome, new ConfiguredSurfaceBuilder<>(NetherExSurfaceBuilders.NETHER_SURFACE_BUILDER_WRAPPER.get(), new NetherSurfaceBuilderWrapper.Config(this)), "field_201875_ar");
    }

    @Override
    public void resetBiome()
    {
        ObfuscationReflectionHelper.setPrivateValue(Biome.class, this.biome, ((NetherSurfaceBuilderWrapper.Config) this.biome.getSurfaceBuilder().config).getOriginalBuilder(), "field_201875_ar");
    }
}
