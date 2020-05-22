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

package logictechcorp.netherex.world.biome.provider;

import logictechcorp.libraryex.world.biome.BiomeData;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.world.generation.layer.NetherLayerUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Set;

public class NetherBiomeProvider extends OverworldBiomeProvider
{
    public NetherBiomeProvider(OverworldBiomeProviderSettings biomeProviderSettings)
    {
        super(biomeProviderSettings);
        ObfuscationReflectionHelper.setPrivateValue(OverworldBiomeProvider.class, this, NetherLayerUtil.createLayers(biomeProviderSettings.getSeed(), biomeProviderSettings.getWorldType(), biomeProviderSettings.getGeneratorSettings()), "field_201543_c");
    }

    @Override
    public boolean hasStructure(Structure<?> structureIn)
    {
        return this.hasStructureCache.computeIfAbsent(structureIn, structure -> {
            for(BiomeData data : NetherEx.BIOME_DATA_MANAGER.getBiomeData().values()) {
                if (data.hasStructure(structure)) {
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public Set<BlockState> getSurfaceBlocks()
    {
        if(this.topBlocksCache.isEmpty())
        {
            this.topBlocksCache.add(Blocks.NETHERRACK.getDefaultState());
        }

        return this.topBlocksCache;
    }
}
