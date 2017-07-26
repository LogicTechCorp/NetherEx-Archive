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
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.NetherEx;
import nex.util.BlockUtil;

import java.util.Random;

public class BiomeFeatureFluid extends BiomeFeature
{
    private IBlockState blockToSpawn;
    private IBlockState blockToTarget;
    private boolean hidden;

    public BiomeFeatureFluid(float genAttemptsIn, int minHeightIn, int maxHeightIn, IBlockState blockToSpawnIn, IBlockState blockToTargetIn, boolean hiddenIn)
    {
        super(genAttemptsIn, minHeightIn, maxHeightIn);

        blockToSpawn = blockToSpawnIn;
        blockToTarget = blockToTargetIn;
        hidden = hiddenIn;
        setRegistryName(NetherEx.MOD_ID + ":fluid");
    }

    @Override
    public BiomeFeatureFluid deserialize(JsonObject config)
    {
        float genAttempts = JsonUtils.getFloat(config, "genAttempts", 10.0F);
        int minHeight = JsonUtils.getInt(config, "minHeight", 32);
        int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

        IBlockState blockToSpawn = null;
        IBlockState blockToTarget = null;

        JsonObject blockToSpawnJson = JsonUtils.getJsonObject(config, "blockToSpawn", new JsonObject());
        JsonObject blockToTargetJson = JsonUtils.getJsonObject(config, "blockToTarget", new JsonObject());

        if(blockToSpawnJson.entrySet().size() > 0)
        {
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(blockToSpawnJson, "block")));

            if(block != null)
            {
                blockToSpawn = block.getDefaultState();
            }
        }
        if(blockToTargetJson.entrySet().size() > 0)
        {
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(blockToTargetJson, "block")));

            if(block != null)
            {
                blockToTarget = block.getDefaultState();
            }
        }

        JsonObject blockToSpawnProperties = JsonUtils.getJsonObject(blockToSpawnJson, "properties", new JsonObject());
        JsonObject blockToTargetProperties = JsonUtils.getJsonObject(blockToTargetJson, "properties", new JsonObject());

        if(blockToSpawnProperties.entrySet().size() > 0)
        {
            blockToSpawn = BlockUtil.getBlockWithProperties(blockToSpawn, JsonUtils.getJsonObject(blockToSpawnJson, "properties"));
        }
        if(blockToTargetProperties.entrySet().size() > 0)
        {
            blockToTarget = BlockUtil.getBlockWithProperties(blockToTarget, JsonUtils.getJsonObject(blockToTargetJson, "properties"));
        }

        boolean hidden = JsonUtils.getBoolean(config, "hidden", false);

        if(blockToSpawn != null && blockToTarget != null)
        {
            return new BiomeFeatureFluid(genAttempts, minHeight, maxHeight, blockToSpawn, blockToTarget, hidden);
        }

        return null;
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random rand)
    {
        if(world.getBlockState(pos.up()) != blockToTarget)
        {
            return false;
        }
        else if(!world.isAirBlock(pos) && world.getBlockState(pos) != blockToTarget)
        {
            return false;
        }
        else
        {
            int i = 0;

            if(world.getBlockState(pos.west()) == blockToTarget)
            {
                ++i;
            }

            if(world.getBlockState(pos.east()) == blockToTarget)
            {
                ++i;
            }

            if(world.getBlockState(pos.north()) == blockToTarget)
            {
                ++i;
            }

            if(world.getBlockState(pos.south()) == blockToTarget)
            {
                ++i;
            }

            if(world.getBlockState(pos.down()) == blockToTarget)
            {
                ++i;
            }

            int j = 0;

            if(world.isAirBlock(pos.west()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.east()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.north()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.south()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.down()))
            {
                ++j;
            }

            if(!hidden && i == 4 && j == 1 || i == 5)
            {
                world.setBlockState(pos, blockToSpawn, 2);
                world.immediateBlockTick(pos, blockToSpawn, rand);
            }

            return true;
        }
    }
}
