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

package logictechcorp.netherex.world.biome.design;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.world.generation.layer.GenLayerNetherBiome;
import logictechcorp.netherex.world.generation.layer.GenLayerNetherSubBiome;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.*;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class BiomeProviderNetherEx extends BiomeProvider
{
    public BiomeProviderNetherEx(World world)
    {
        super();
        GenLayer[] genLayers = this.createGenLayers(world.getSeed());
        ObfuscationReflectionHelper.setPrivateValue(BiomeProvider.class, this, genLayers[0], "field_76944_d", "genBiomes");
        ObfuscationReflectionHelper.setPrivateValue(BiomeProvider.class, this, genLayers[1], "field_76945_e", "biomeIndexLayer");
    }

    private GenLayer[] createGenLayers(long worldSeed)
    {
        GenLayer baseLayer = new GenLayerIsland(1L);
        GenLayer biomeLayer = new GenLayerNetherBiome(200L, baseLayer);
        GenLayer subBiomeLayer = new GenLayerRiverInit(100L, biomeLayer);
        biomeLayer = GenLayerZoom.magnify(1000L, biomeLayer, 2);
        subBiomeLayer = GenLayerZoom.magnify(1000L, subBiomeLayer, 2);
        baseLayer = new GenLayerNetherSubBiome(1000L, biomeLayer, subBiomeLayer);
        baseLayer = GenLayerZoom.magnify(1000L, baseLayer, GenLayer.getModdedBiomeSize(NetherEx.WORLD_TYPE, 3));
        baseLayer = new GenLayerSmooth(1000L, baseLayer);
        GenLayer zoomedLayer = new GenLayerVoronoiZoom(10L, baseLayer);
        baseLayer.initWorldGenSeed(worldSeed);
        zoomedLayer.initWorldGenSeed(worldSeed);
        return new GenLayer[]{baseLayer, zoomedLayer};
    }
}
