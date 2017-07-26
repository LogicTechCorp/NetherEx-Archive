/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.world.gen.feature;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.NetherEx;
import nex.util.BlockUtil;

import java.util.Random;

public class BiomeFeatureCluster extends BiomeFeature
{
    private IBlockState blockToSpawn;
    private IBlockState blockToHangFrom;

    public BiomeFeatureCluster(float genAttemptsIn, int minHeightIn, int maxHeightIn, IBlockState blockToSpawnIn, IBlockState blockToHangFromIn)
    {
        super(genAttemptsIn, minHeightIn, maxHeightIn);

        blockToSpawn = blockToSpawnIn;
        blockToHangFrom = blockToHangFromIn;
        setRegistryName(NetherEx.MOD_ID + ":cluster");
    }

    @Override
    public BiomeFeatureCluster deserialize(JsonObject config)
    {
        float genAttempts = JsonUtils.getFloat(config, "genAttempts", 10.0F);
        int minHeight = JsonUtils.getInt(config, "minHeight", 32);
        int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

        IBlockState blockToSpawn = null;
        IBlockState blockToHangFrom = null;

        JsonObject blockToSpawnJson = JsonUtils.getJsonObject(config, "blockToSpawn", new JsonObject());
        JsonObject blockToHangFromJson = JsonUtils.getJsonObject(config, "blockToHangFrom", new JsonObject());

        if(blockToSpawnJson.entrySet().size() > 0)
        {
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(blockToSpawnJson, "block")));

            if(block != null)
            {
                blockToSpawn = block.getDefaultState();
            }
        }
        if(blockToHangFromJson.entrySet().size() > 0)
        {
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(blockToHangFromJson, "block")));

            if(block != null)
            {
                blockToHangFrom = block.getDefaultState();
            }
        }

        JsonObject blockToSpawnProperties = JsonUtils.getJsonObject(blockToSpawnJson, "properties", new JsonObject());
        JsonObject blockToHangFromProperties = JsonUtils.getJsonObject(blockToHangFromJson, "properties", new JsonObject());

        if(blockToSpawnProperties.entrySet().size() > 0)
        {
            blockToSpawn = BlockUtil.getBlockWithProperties(blockToSpawn, JsonUtils.getJsonObject(blockToSpawnJson, "properties"));
        }
        if(blockToHangFromProperties.entrySet().size() > 0)
        {
            blockToHangFrom = BlockUtil.getBlockWithProperties(blockToHangFrom, JsonUtils.getJsonObject(blockToHangFromJson, "properties"));
        }

        if(blockToSpawn != null && blockToHangFrom != null)
        {
            return new BiomeFeatureCluster(genAttempts, minHeight, maxHeight, blockToSpawn, blockToHangFrom);
        }

        return null;
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random rand)
    {
        if(!world.isAirBlock(pos))
        {
            return false;
        }
        else if(world.getBlockState(pos.up()) != blockToHangFrom)
        {
            return false;
        }
        else
        {
            world.setBlockState(pos, blockToSpawn, 3);

            for(int i = 0; i < 1500; ++i)
            {
                BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), -rand.nextInt(12), rand.nextInt(8) - rand.nextInt(8));

                if(world.isAirBlock(newPos))
                {
                    int j = 0;

                    for(EnumFacing facing : EnumFacing.values())
                    {
                        if(world.getBlockState(newPos.offset(facing)).getBlock() == blockToSpawn.getBlock())
                        {
                            ++j;
                        }

                        if(j > 1)
                        {
                            break;
                        }
                    }

                    if(j == 1)
                    {
                        world.setBlockState(newPos, blockToSpawn, 3);
                    }
                }
            }

            return true;
        }
    }
}
