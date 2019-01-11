/*
 * LibraryEx
 * Copyright (c) 2017-2018 by MineEx
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
 *
 */

package logictechcorp.netherex.util;

import logictechcorp.libraryex.util.BlockHelper;
import logictechcorp.netherex.capability.CapabilityBlightChunkData;
import logictechcorp.netherex.capability.IBlightChunkData;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class BlightHelper
{
    public static boolean convertBlock(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if(!state.getMaterial().isReplaceable() && !state.getMaterial().isLiquid() && state.isFullBlock() && state.isFullCube() && state.isOpaqueCube())
        {
            if(BlockHelper.isOreDict("grass", block))
            {
                return world.setBlockState(pos, NetherExBlocks.HYPHAE.getDefaultState());
            }
            else if(BlockHelper.isOreDict("dirt", block))
            {
                return world.setBlockState(pos, NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState());
            }
            else if(BlockHelper.isOreDict("stone", block))
            {
                return world.setBlockState(pos, NetherExBlocks.BASALT.getDefaultState());
            }
            else if(block instanceof BlockOre || BlockHelper.oreDictNameContains(state, "ore"))
            {
                return world.setBlockState(pos, NetherExBlocks.ARDITE_ORE.getDefaultState());
            }
            else if(BlockHelper.isOreDict("logWood", block))
            {
                return world.setBlockState(pos, Blocks.HARDENED_CLAY.getDefaultState());
            }
            else if(BlockHelper.isOreDict("treeLeaves", block))
            {
                return world.setBlockState(pos, Blocks.MAGMA.getDefaultState());
            }
            else if(BlockHelper.isOreDict("sand", block))
            {
                return world.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
            }
        }

        return false;
    }

    public static void convertBiome(World world, BlockPos pos)
    {
        if(world.getBiome(pos) != NetherExBiomes.BLIGHTS_ASCENSION)
        {
            int localChunkX = pos.getX() & 15;
            int localChunkZ = pos.getZ() & 15;
            Chunk chunk = world.getChunk(pos);
            chunk.getBiomeArray()[localChunkZ << 4 | localChunkX] = (byte) Biome.getIdForBiome(NetherExBiomes.BLIGHTS_ASCENSION);
            chunk.setModified(true);
        }
    }

    public static boolean addBlightToChunk(World world, BlockPos blockPos)
    {
        if(world.provider.getDimension() == DimensionType.OVERWORLD.getId() && world.getBiome(blockPos) != NetherExBiomes.BLIGHTS_ASCENSION)
        {
            IBlightChunkData data = world.getCapability(CapabilityBlightChunkData.INSTANCE, null);

            if(data != null)
            {
                ChunkPos chunkPos = new ChunkPos(blockPos);

                if(!data.hasChunk(chunkPos))
                {
                    data.addChunk(chunkPos);
                }

                return true;
            }
        }

        return false;
    }
}
