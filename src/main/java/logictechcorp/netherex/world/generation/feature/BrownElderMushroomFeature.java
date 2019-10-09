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
import net.minecraft.block.BlockState;
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

public class BrownElderMushroomFeature extends Feature<BigMushroomFeatureConfig>
{
    public BrownElderMushroomFeature(Function<Dynamic<?>, ? extends BigMushroomFeatureConfig> configFactory)
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
            BlockState state = world.getBlockState(pos);

            if(!state.isValidPosition(world, pos))
            {
                return false;
            }
            else
            {
                BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                for(int posYOffset = 0; posYOffset <= 1 + height; posYOffset++)
                {
                    int size = posYOffset <= 3 ? 0 : 3;

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

                BlockState brownElderMushroom = NetherExBlocks.BROWN_ELDER_MUSHROOM_CAP.get().getDefaultState().with(HugeMushroomBlock.UP, true).with(HugeMushroomBlock.DOWN, false);

                for(int posXOffset = -3; posXOffset <= 3; posXOffset++)
                {
                    for(int posZOffset = -3; posZOffset <= 3; posZOffset++)
                    {
                        boolean negativeX = posXOffset == -3;
                        boolean positiveX = posXOffset == 3;
                        boolean negativeZ = posZOffset == -3;
                        boolean positiveZ = posZOffset == 3;
                        boolean isXSize = negativeX || positiveX;
                        boolean isZSize = negativeZ || positiveZ;

                        if(!isXSize || !isZSize)
                        {
                            mutablePos.setPos(pos).move(posXOffset, height, posZOffset);

                            if(world.getBlockState(mutablePos).canBeReplacedByLeaves(world, mutablePos))
                            {
                                boolean west = negativeX || isZSize && posXOffset == -2;
                                boolean east = positiveX || isZSize && posXOffset == 2;
                                boolean north = negativeZ || isXSize && posZOffset == -2;
                                boolean south = positiveZ || isXSize && posZOffset == 2;
                                this.setBlockState(world, mutablePos, brownElderMushroom.with(HugeMushroomBlock.WEST, west).with(HugeMushroomBlock.EAST, east).with(HugeMushroomBlock.NORTH, north).with(HugeMushroomBlock.SOUTH, south));
                            }
                        }
                    }
                }

                BlockState elderMushroomStem = NetherExBlocks.ELDER_MUSHROOM_STEM.get().getDefaultState().with(HugeMushroomBlock.UP, false).with(HugeMushroomBlock.DOWN, false);

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
