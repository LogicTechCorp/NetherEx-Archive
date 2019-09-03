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

package logictechcorp.netherex.world.generation.feature;

import com.mojang.datafixers.Dynamic;
import logictechcorp.netherex.block.NetherExBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class RedElderMushroomFeature extends Feature<BigMushroomFeatureConfig>
{
    public RedElderMushroomFeature(Function<Dynamic<?>, ? extends BigMushroomFeatureConfig> configFactory)
    {
        super(configFactory);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, BigMushroomFeatureConfig config)
    {
        int height = random.nextInt(3) + 4;

        if(random.nextInt(9) == 0)
        {
            height *= 2;
        }

        int posY = pos.getY();

        if(posY >= 1 && (posY + height + 1) < world.getWorld().getDimension().getHeight())
        {
            Block downBlock = world.getBlockState(pos.down()).getBlock();

            if(downBlock != NetherExBlocks.HYPHAE && downBlock != Blocks.MYCELIUM)
            {
                return false;
            }
            else
            {
                BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                for(int posYOffset = 0; posYOffset <= height; posYOffset++)
                {
                    int size = 0;

                    if(posYOffset < height && posYOffset >= height - 3)
                    {
                        size = 2;
                    }
                    else if(posYOffset == height)
                    {
                        size = 1;
                    }

                    for(int posXOffset = -size; posXOffset <= size; posXOffset++)
                    {
                        for(int posZOffset = -size; posZOffset <= size; posZOffset++)
                        {
                            BlockState adjustedState = world.getBlockState(mutablePos.setPos(pos).move(posXOffset, posYOffset, posZOffset));

                            if(!adjustedState.isAir(world, mutablePos) && !adjustedState.isIn(BlockTags.LEAVES))
                            {
                                return false;
                            }
                        }
                    }
                }

                BlockState redElderMushroom = NetherExBlocks.RED_ELDER_MUSHROOM_CAP.getDefaultState().with(HugeMushroomBlock.DOWN, false);

                for(int posYOffset = height - 3; posYOffset <= height; posYOffset++)
                {
                    int size = posYOffset < height ? 2 : 1;

                    for(int posXOffset = -size; posXOffset <= size; posXOffset++)
                    {
                        for(int posZOffset = -size; posZOffset <= size; posZOffset++)
                        {
                            boolean negativeX = posXOffset == -size;
                            boolean positiveX = posXOffset == size;
                            boolean negativeZ = posZOffset == -size;
                            boolean positiveZ = posZOffset == size;
                            boolean isXSize = negativeX || positiveX;
                            boolean isZSize = negativeZ || positiveZ;

                            if(posYOffset >= height || isXSize != isZSize)
                            {
                                mutablePos.setPos(pos).move(posXOffset, posYOffset, posZOffset);

                                if(world.getBlockState(mutablePos).canBeReplacedByLeaves(world, mutablePos))
                                {
                                    this.setBlockState(world, mutablePos, redElderMushroom.with(HugeMushroomBlock.UP, posYOffset >= (height - 1)).with(HugeMushroomBlock.WEST, posXOffset < 0).with(HugeMushroomBlock.EAST, posXOffset > 0).with(HugeMushroomBlock.NORTH, posZOffset < 0).with(HugeMushroomBlock.SOUTH, posZOffset > 0));
                                }
                            }
                        }
                    }
                }

                BlockState elderMushroomStem = NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState().with(HugeMushroomBlock.UP, false).with(HugeMushroomBlock.DOWN, false);

                for(int posYOffset = 0; posYOffset < height; posYOffset++)
                {
                    mutablePos.setPos(pos).move(Direction.UP, posYOffset);

                    if(world.getBlockState(mutablePos).canBeReplacedByLeaves(world, mutablePos))
                    {
                        if(config.planted)
                        {
                            world.setBlockState(mutablePos, elderMushroomStem, 3);
                        }
                        else
                        {
                            this.setBlockState(world, mutablePos, elderMushroomStem);
                        }
                    }
                }

                return true;
            }
        }
        else
        {
            return false;
        }
    }
}
