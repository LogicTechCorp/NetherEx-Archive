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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class HyphaeBlock extends Block
{
    public HyphaeBlock()
    {
        super(Block.Properties.create(Material.ROCK, DyeColor.LIGHT_GRAY).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F).tickRandomly());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random random)
    {
        if(random.nextInt(10) == 0)
        {
            world.addParticle(ParticleTypes.MYCELIUM, ((float) pos.getX() + random.nextFloat()), ((float) pos.getY() + 1.1F), ((float) pos.getZ() + random.nextFloat()), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random)
    {
        if(!world.isRemote)
        {
            if(world.isAreaLoaded(pos, 3) && world.getLight(pos.up()) >= 9)
            {
                for(int i = 0; i < 4; i++)
                {
                    BlockPos randomPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

                    if(world.getBlockState(randomPos).getBlock() == NetherExBlocks.LIVELY_NETHERRACK)
                    {
                        world.setBlockState(randomPos, state);
                    }
                }
            }
        }
    }
}
