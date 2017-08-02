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

import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.util.BlockUtil;

import java.util.Random;

public class EnhancedGeneratorScatter extends EnhancedGenerator
{
    private final IBlockState blockToSpawn;
    private final IBlockState blockToTarget;
    private final Placement placement;

    public static final EnhancedGeneratorScatter INSTANCE = new EnhancedGeneratorScatter(0, 0.0F, 0, 0, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), EnhancedGeneratorScatter.Placement.ON_GROUND);

    private EnhancedGeneratorScatter(int generationAttempts, float generationProbability, int minHeight, int maxHeight, IBlockState blockToSpawnIn, IBlockState blockToTargetIn, Placement placementIn)
    {
        super(generationAttempts, generationProbability, minHeight, maxHeight);

        blockToSpawn = blockToSpawnIn;
        blockToTarget = blockToTargetIn;
        placement = placementIn;
    }

    @Override
    public EnhancedGeneratorScatter deserializeConfig(JsonObject config)
    {
        int generationAttempts = JsonUtils.getInt(config, "generationAttempts", 10);
        float generationProbability = JsonUtils.getFloat(config, "generationProbability", 1.0F);
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

        Placement placement = Placement.getFromString(JsonUtils.getString(config, "placement"));

        if(blockToSpawn != null && blockToTarget != null)
        {
            return new EnhancedGeneratorScatter(generationAttempts, generationProbability, minHeight, maxHeight, blockToSpawn, blockToTarget, placement);
        }

        return null;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < 64; ++i)
        {
            BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if(world.isAirBlock(newPos) && world.getBlockState(newPos.down()) == blockToTarget)
            {
                if(blockToSpawn instanceof BlockBush)
                {
                    if(((BlockBush) blockToSpawn).canBlockStay(world, placement.offsetPos(pos), blockToSpawn))
                    {
                        world.setBlockState(placement.offsetPos(newPos), blockToSpawn, 2);
                    }
                }
                else
                {
                    world.setBlockState(placement.offsetPos(newPos), blockToSpawn, 2);
                }
            }
        }

        return true;
    }

    public enum Placement
    {
        ON_GROUND(null),
        IN_GROUND(EnumFacing.DOWN);

        EnumFacing offset;

        Placement(EnumFacing offsetIn)
        {
            offset = offsetIn;
        }

        public BlockPos offsetPos(BlockPos pos)
        {
            if(offset != null)
            {
                return pos.offset(offset);
            }
            else
            {
                return pos;
            }
        }

        public static Placement getFromString(String string)
        {
            if(!Strings.isNullOrEmpty(string))
            {
                for(Placement placement : values())
                {
                    if(placement.name().replace("_", "").equalsIgnoreCase(string))
                    {
                        return placement;
                    }
                }
            }

            return ON_GROUND;
        }
    }
}
