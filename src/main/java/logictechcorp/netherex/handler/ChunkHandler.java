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

package logictechcorp.netherex.handler;

import logictechcorp.libraryex.event.world.ChunkGenerateEvent;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.utility.BlightHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class ChunkHandler
{
    @SubscribeEvent
    public static void onChunkGenerate(ChunkGenerateEvent event)
    {
        World world = event.getWorld();
        Chunk chunk = event.getChunk();

        if(!world.isRemote)
        {
            if(world.provider.getDimension() == DimensionType.NETHER.getId())
            {
                boolean collapsed = false;

                for(byte biomeId : chunk.getBiomeArray())
                {
                    Biome biome = Biome.getBiomeForId(biomeId);

                    if(biome == NetherExBiomes.REGROWTHS_COLLAPSE)
                    {
                        collapsed = true;
                    }
                }

                World overworld = DimensionManager.getWorld(DimensionType.OVERWORLD.getId());

                if(overworld != null && collapsed)
                {
                    ChunkPos chunkPos = chunk.getPos();
                    BlockPos blockPos = new BlockPos(chunkPos.getXStart() * 8, 0, chunkPos.getZStart() * 8);
                    BlightHelper.addBlightToChunk(overworld, blockPos);
                }
            }
        }
    }
}
