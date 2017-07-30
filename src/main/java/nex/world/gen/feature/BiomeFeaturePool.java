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
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.util.BlockUtil;

import java.util.Random;

public class BiomeFeaturePool extends BiomeFeatureNetherEx
{
    private final IBlockState blockToSpawn;
    private final IBlockState blockToSurround;

    public BiomeFeaturePool()
    {
        this(0.0F, 0, 0, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState());
    }

    private BiomeFeaturePool(float genAttemptsIn, int minHeightIn, int maxHeightIn, IBlockState blockToSpawnIn, IBlockState blockToSurroundIn)
    {
        super("pool", genAttemptsIn, minHeightIn, maxHeightIn);

        blockToSpawn = blockToSpawnIn;
        blockToSurround = blockToSurroundIn;
    }

    @Override
    public BiomeFeaturePool deserialize(JsonObject config)
    {
        float genAttempts = JsonUtils.getFloat(config, "genAttempts", 10.0F);
        int minHeight = JsonUtils.getInt(config, "minHeight", 32);
        int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

        IBlockState blockToSpawn = null;
        IBlockState blockToSurround = null;

        JsonObject blockToSpawnJson = JsonUtils.getJsonObject(config, "blockToSpawn", new JsonObject());
        JsonObject blockToSurroundJson = JsonUtils.getJsonObject(config, "blockToSurround", new JsonObject());

        if(blockToSpawnJson.entrySet().size() > 0)
        {
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(blockToSpawnJson, "block")));

            if(block != null)
            {
                blockToSpawn = block.getDefaultState();
            }
        }
        if(blockToSurroundJson.entrySet().size() > 0)
        {
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(blockToSurroundJson, "block")));

            if(block != null)
            {
                blockToSurround = block.getDefaultState();
            }
        }

        JsonObject blockToSpawnProperties = JsonUtils.getJsonObject(blockToSpawnJson, "properties", new JsonObject());
        JsonObject blockToSurroundProperties = JsonUtils.getJsonObject(blockToSurroundJson, "properties", new JsonObject());

        if(blockToSpawnProperties.entrySet().size() > 0)
        {
            blockToSpawn = BlockUtil.getBlockWithProperties(blockToSpawn, JsonUtils.getJsonObject(blockToSpawnJson, "properties"));
        }
        if(blockToSurroundProperties.entrySet().size() > 0)
        {
            blockToSurround = BlockUtil.getBlockWithProperties(blockToSurround, JsonUtils.getJsonObject(blockToSurroundJson, "properties"));
        }

        if(blockToSpawn != null && blockToSurround != null)
        {
            return new BiomeFeaturePool(genAttempts, minHeight, maxHeight, blockToSpawn, blockToSurround);
        }

        return null;

    }

    @Override
    public boolean generate(World world, BlockPos pos, Random rand)
    {
        for(pos = pos.add(-8, 0, -8); pos.getY() > getMinHeight() && world.isAirBlock(pos); pos = pos.down())
        {

        }

        if(pos.getY() <= 4 || pos.getY() < getMinHeight())
        {
            return false;
        }
        else
        {
            pos = pos.down(4);
            boolean[] hasSpace = new boolean[2048];
            int i = rand.nextInt(4) + 4;

            for(int j = 0; j < i; ++j)
            {
                double d0 = rand.nextDouble() * 6.0D + 3.0D;
                double d1 = rand.nextDouble() * 4.0D + 2.0D;
                double d2 = rand.nextDouble() * 6.0D + 3.0D;
                double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for(int l = 1; l < 15; ++l)
                {
                    for(int i1 = 1; i1 < 15; ++i1)
                    {
                        for(int j1 = 1; j1 < 7; ++j1)
                        {
                            double d6 = ((double) l - d3) / (d0 / 2.0D);
                            double d7 = ((double) j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double) i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;

                            if(d9 < 1.0D)
                            {
                                hasSpace[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            for(int k1 = 0; k1 < 16; ++k1)
            {
                for(int l2 = 0; l2 < 16; ++l2)
                {
                    for(int k = 0; k < 8; ++k)
                    {
                        boolean flag = !hasSpace[(k1 * 16 + l2) * 8 + k] && (k1 < 15 && hasSpace[((k1 + 1) * 16 + l2) * 8 + k] || k1 > 0 && hasSpace[((k1 - 1) * 16 + l2) * 8 + k] || l2 < 15 && hasSpace[(k1 * 16 + l2 + 1) * 8 + k] || l2 > 0 && hasSpace[(k1 * 16 + (l2 - 1)) * 8 + k] || k < 7 && hasSpace[(k1 * 16 + l2) * 8 + k + 1] || k > 0 && hasSpace[(k1 * 16 + l2) * 8 + (k - 1)]);

                        if(flag)
                        {
                            Material material = world.getBlockState(pos.add(k1, k, l2)).getMaterial();

                            if(k >= 4 && material.isLiquid())
                            {
                                return false;
                            }

                            if(k < 4 && !material.isSolid() && world.getBlockState(pos.add(k1, k, l2)) != blockToSpawn)
                            {
                                return false;
                            }
                        }
                    }
                }
            }

            for(int l1 = 0; l1 < 16; ++l1)
            {
                for(int i3 = 0; i3 < 16; ++i3)
                {
                    for(int i4 = 0; i4 < 8; ++i4)
                    {
                        if(hasSpace[(l1 * 16 + i3) * 8 + i4])
                        {
                            world.setBlockState(pos.add(l1, i4, i3), i4 >= 4 ? Blocks.AIR.getDefaultState() : blockToSpawn, 2);
                        }
                    }
                }
            }

            for(int j2 = 0; j2 < 16; ++j2)
            {
                for(int k3 = 0; k3 < 16; ++k3)
                {
                    for(int k4 = 0; k4 < 8; ++k4)
                    {
                        boolean flag1 = !hasSpace[(j2 * 16 + k3) * 8 + k4] && (j2 < 15 && hasSpace[((j2 + 1) * 16 + k3) * 8 + k4] || j2 > 0 && hasSpace[((j2 - 1) * 16 + k3) * 8 + k4] || k3 < 15 && hasSpace[(j2 * 16 + k3 + 1) * 8 + k4] || k3 > 0 && hasSpace[(j2 * 16 + (k3 - 1)) * 8 + k4] || k4 < 7 && hasSpace[(j2 * 16 + k3) * 8 + k4 + 1] || k4 > 0 && hasSpace[(j2 * 16 + k3) * 8 + (k4 - 1)]);

                        if(flag1 && (k4 < 4 || rand.nextInt(2) != 0) && world.getBlockState(pos.add(j2, k4, k3)).getMaterial().isSolid())
                        {
                            world.setBlockState(pos.add(j2, k4, k3), blockToSurround, 2);
                        }
                    }
                }
            }

            return true;
        }
    }
}
