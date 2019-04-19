/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
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

package logictechcorp.netherex.world.biome.data;

import logictechcorp.libraryex.world.biome.data.impl.BiomeDataConfigurable;
import logictechcorp.netherex.NetherEx;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeDataNetherEx extends BiomeDataConfigurable
{
    public BiomeDataNetherEx(Biome biome, int biomeGenerationWeight, boolean generateBiome, boolean generateDefaultBiomeFeatures)
    {
        super(biome, biomeGenerationWeight, generateBiome, generateDefaultBiomeFeatures);
    }

    public BiomeDataNetherEx(ResourceLocation biomeRegistryName, int biomeGenerationWeight, boolean generateBiome, boolean generateDefaultBiomeFeatures)
    {
        super(ForgeRegistries.BIOMES.getValue(biomeRegistryName), biomeGenerationWeight, generateBiome, generateDefaultBiomeFeatures);
    }

    public BiomeDataNetherEx(ResourceLocation biomeRegistryName)
    {
        super(biomeRegistryName);
    }

    public BiomeDataNetherEx(Biome biome)
    {
        super(biome);
    }

    @Override
    public String getRelativeSaveFile()
    {
        return "config/" + NetherEx.MOD_ID + "/biomes/" + this.biome.getRegistryName().toString().replace(":", "/") + ".json";
    }
}
