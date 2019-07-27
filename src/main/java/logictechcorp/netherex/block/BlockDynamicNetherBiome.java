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

package logictechcorp.netherex.block;

import logictechcorp.libraryex.api.world.biome.BiomeBlockType;
import logictechcorp.libraryex.api.world.biome.data.IBiomeData;
import logictechcorp.libraryex.block.BlockDynamic;
import logictechcorp.libraryex.block.property.BlockProperties;
import logictechcorp.netherex.api.NetherExAPI;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;

public abstract class BlockDynamicNetherBiome extends BlockDynamic
{
    public BlockDynamicNetherBiome(ResourceLocation registryName, TexturePlacement texturePlacement, BlockProperties properties)
    {
        super(registryName, texturePlacement, properties);
    }

    @Override
    public IBlockState getDynamicState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        Biome biome = world.getBiome(pos);
        IBiomeData biomeData = NetherExAPI.getInstance().getBiomeDataRegistry().getBiomeData(biome);

        if(biomeData != null)
        {
            return biomeData.getBiomeBlock(BiomeBlockType.CAVE_WALL_BLOCK);
        }

        return Blocks.NETHERRACK.getDefaultState();
    }
}
