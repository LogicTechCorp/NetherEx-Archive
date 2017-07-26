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

package nex.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import nex.world.gen.GenerationStage;
import nex.world.gen.feature.BiomeFeature;
import nex.world.gen.feature.BiomeFeatureManager;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class WorldGenUtil
{
    public static BlockPos getSuitableGroundPos(World world, BlockPos pos, BlockPos structureSize, float percentage)
    {
        label_while:
        while(pos.getY() > 32)
        {
            float sizeX = structureSize.getX();
            float sizeY = structureSize.getY();
            float sizeZ = structureSize.getZ();

            int topBlocks = 0;

            for(int x = 0; x <= MathHelper.abs(sizeX); x++)
            {
                for(int z = 0; z <= MathHelper.abs(sizeZ); z++)
                {
                    int posX = (int) (sizeX > 0 ? sizeX - x : sizeX + x);
                    int posZ = (int) (sizeZ > 0 ? sizeZ - z : sizeZ + z);

                    BlockPos newPos = pos.add(posX, 0, posZ);
                    IBlockState state = world.getBlockState(newPos);

                    if(!world.getBlockState(newPos).getMaterial().isReplaceable() && !world.getBlockState(newPos.down()).getMaterial().isReplaceable() && world.getBlockState(newPos.up()).getMaterial().isReplaceable())
                    {
                        topBlocks++;
                    }
                    else if(state != Blocks.AIR.getDefaultState())
                    {
                        pos = pos.down();
                        continue label_while;
                    }
                }
            }

            int replaceableBlocks = 0;

            if(topBlocks >= MathHelper.abs(sizeX * sizeZ) * percentage)
            {
                for(int y = 1; y < sizeY; y++)
                {
                    for(int x = 0; x <= MathHelper.abs(sizeX); x++)
                    {
                        for(int z = 0; z <= MathHelper.abs(sizeZ); z++)
                        {
                            int posX = (int) (sizeX > 0 ? sizeX - x : sizeX + x);
                            int posZ = (int) (sizeZ > 0 ? sizeZ - z : sizeZ + z);

                            BlockPos newPos = pos.add(posX, y, posZ);
                            IBlockState state = world.getBlockState(newPos);

                            if(world.getBlockState(newPos).getMaterial().isReplaceable())
                            {
                                replaceableBlocks++;
                            }
                            else if(state != Blocks.AIR.getDefaultState())
                            {
                                pos = pos.down();
                                continue label_while;
                            }
                        }
                    }
                }
            }

            if(replaceableBlocks > MathHelper.abs(sizeX * sizeY * sizeZ) * 0.875F)
            {
                return pos;
            }

            pos = pos.down();
        }

        return BlockPos.ORIGIN;
    }

    public static BlockPos getSuitableWallPos(World world, BlockPos pos, BlockPos structureSize, float percentage)
    {
        while(pos.getY() > 32)
        {
            float sizeX = structureSize.getX();
            float sizeZ = structureSize.getZ();
            float sizeY = structureSize.getY();

            int wallBlocks = 0;

            for(int x = 0; x <= MathHelper.abs(sizeX); x++)
            {
                for(int z = 0; z <= MathHelper.abs(sizeZ); z++)
                {
                    for(int y = 0; y <= sizeY; y++)
                    {
                        int posX = (int) (sizeX > 0 ? sizeX - x : sizeX + x);
                        int posZ = (int) (sizeZ > 0 ? sizeZ - z : sizeZ + z);

                        BlockPos newPos = pos.add(posX, y, posZ);

                        if(!world.getBlockState(newPos).getMaterial().isReplaceable())
                        {
                            wallBlocks++;
                        }
                    }
                }
            }

            if(wallBlocks >= MathHelper.abs(sizeX * sizeY * sizeZ) * percentage)
            {
                return pos;
            }

            pos = pos.down();
        }

        return BlockPos.ORIGIN;
    }

    public static BlockPos getSuitableCeilingPos(World world, BlockPos pos, BlockPos structureSize)
    {
        label_while:
        while(pos.getY() < 128)
        {
            float sizeX = structureSize.getX();
            float sizeZ = structureSize.getZ();
            float sizeY = structureSize.getY();

            int ceilingBlocks = 0;
            int replaceableBlocks = 0;

            for(int x = 0; x <= MathHelper.abs(sizeX); x++)
            {
                for(int z = 0; z <= MathHelper.abs(sizeZ); z++)
                {
                    for(int y = 0; y <= sizeY; y++)
                    {
                        int posX = (int) (sizeX > 0 ? sizeX - x : sizeX + x);
                        int posZ = (int) (sizeZ > 0 ? sizeZ - z : sizeZ + z);

                        BlockPos newPos = pos.add(posX, -y, posZ);

                        if(y == 0)
                        {
                            if(world.getBlockState(newPos).isSideSolid(world, newPos, EnumFacing.DOWN))
                            {
                                ceilingBlocks++;
                            }
                            else
                            {
                                pos = pos.up();
                                continue label_while;
                            }
                        }
                        else
                        {
                            if(world.getBlockState(newPos).getBlock().isReplaceable(world, newPos))
                            {
                                replaceableBlocks++;
                            }
                            else
                            {
                                pos = pos.up();
                                continue label_while;
                            }

                        }
                    }
                }
            }

            if(replaceableBlocks >= MathHelper.abs(sizeX * (sizeY - 1) * sizeZ) && ceilingBlocks >= MathHelper.abs(sizeX * sizeZ))
            {
                return pos.add(0, -sizeY, 0);
            }

            pos = pos.up();
        }

        return BlockPos.ORIGIN;
    }

    public static BlockPos getSuitableAirPos(World world, BlockPos pos, BlockPos structureSize)
    {
        label_while:
        while(pos.getY() > 32)
        {
            float sizeX = structureSize.getX();
            float sizeZ = structureSize.getZ();
            float sizeY = structureSize.getY();

            int replaceableBlocks = 0;

            for(int x = 0; x <= MathHelper.abs(sizeX); x++)
            {
                for(int z = 0; z <= MathHelper.abs(sizeZ); z++)
                {
                    for(int y = 0; y <= sizeY; y++)
                    {
                        int posX = (int) (sizeX > 0 ? sizeX - x : sizeX + x);
                        int posZ = (int) (sizeZ > 0 ? sizeZ - z : sizeZ + z);

                        BlockPos newPos = pos.add(posX, y, posZ);

                        if(world.getBlockState(newPos).getMaterial().isReplaceable())
                        {
                            replaceableBlocks++;
                        }
                        else
                        {
                            pos = pos.down();
                            continue label_while;
                        }
                    }
                }
            }

            if(replaceableBlocks >= MathHelper.abs(sizeX * sizeY * sizeZ))
            {
                return pos;
            }

            pos = pos.down();
        }

        return BlockPos.ORIGIN;
    }

    public static void generateFeature(World world, int chunkX, int chunkZ, Random rand, GenerationStage generationStage)
    {
        BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
        generateFeature(world, pos, rand, generationStage);
    }

    public static void generateFeature(World world, BlockPos pos, Random rand, GenerationStage generationStage)
    {
        if(world.provider.getDimension() == DimensionType.NETHER.getId())
        {
            Biome biome = world.getBiome(pos.add(16, 0, 16));

            for(BiomeFeature feature : BiomeFeatureManager.getBiomeFeatures(biome, generationStage))
            {
                for(int genAttempts = 0; genAttempts < feature.getGenAttempts(rand); genAttempts++)
                {
                    feature.generate(world, pos.add(rand.nextInt(16) + 8, RandomUtil.getNumberInRange(feature.getMinHeight(), feature.getMaxHeight(), rand), rand.nextInt(16) + 8), rand);
                }
            }
        }
    }
}
