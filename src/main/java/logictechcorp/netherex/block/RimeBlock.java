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

import logictechcorp.libraryex.utility.RandomHelper;
import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.potion.NetherExEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class RimeBlock extends Block
{
    public RimeBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random)
    {
        for(int x = -1; x < 2; x++)
        {
            for(int z = -1; z < 2; z++)
            {
                for(int y = -1; y < 2; y++)
                {
                    this.freezeSurroundings(world, pos.add(x, y, z), world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + x, pos.getY() + y, pos.getZ() + z)));
                }
            }
        }

        world.getPendingBlockTicks().scheduleTick(pos, this, RandomHelper.getNumberInRange(20, 40, world.getRandom()));
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        world.getPendingBlockTicks().scheduleTick(pos, this, RandomHelper.getNumberInRange(20, 40, world.getRandom()));
    }

    private void freezeSurroundings(World world, BlockPos pos, List<LivingEntity> entities)
    {
        BlockState state = world.getBlockState(pos);

        if(state == Blocks.WATER.getDefaultState() && NetherExConfig.BLOCK.rimeCanFreezeWater.get())
        {
            world.setBlockState(pos, Blocks.ICE.getDefaultState(), 3);
        }
        else if(state == Blocks.LAVA.getDefaultState() && NetherExConfig.BLOCK.rimeCanFreezeLava.get())
        {
            world.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState(), 3);
        }

        for(LivingEntity entity : entities)
        {
            boolean canFreeze = !(entity instanceof PlayerEntity) && NetherExConfig.EFFECT.frozenEffectEntityBlacklist.get().contains(entity.getType().getRegistryName().toString());

            if(canFreeze && NetherExConfig.BLOCK.rimeCanFreezeEntities.get())
            {
                entity.addPotionEffect(new EffectInstance(NetherExEffects.FROZEN.get(), 300, 0));
            }
        }
    }
}
