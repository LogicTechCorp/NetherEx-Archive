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

package nex.item;

import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWitherDust extends ItemNetherEx
{
    public ItemWitherDust()
    {
        super("item_dust_wither");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(!player.canPlayerEdit(pos.offset(facing), facing, stack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            if(applyBoneMeal(stack, world, pos, player))
            {
                if(!world.isRemote)
                {
                    spawnBonemealParticles(world, pos, 0);
                }

                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.PASS;
        }
    }

    @SideOnly(Side.CLIENT)
    public static void spawnBonemealParticles(World world, BlockPos pos, int amount)
    {
        if(amount == 0)
        {
            amount = 15;
        }

        IBlockState state = world.getBlockState(pos);

        if(state.getMaterial() != Material.AIR)
        {
            for(int i = 0; i < amount; ++i)
            {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                world.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, (double) ((float) pos.getX() + itemRand.nextFloat()), (double) pos.getY() + (double) itemRand.nextFloat() * state.getBoundingBox(world, pos).maxY, (double) ((float) pos.getZ() + itemRand.nextFloat()), d0, d1, d2);
            }
        }
        else
        {
            for(int i1 = 0; i1 < amount; ++i1)
            {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                world.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, (double) ((float) pos.getX() + itemRand.nextFloat()), (double) pos.getY() + (double) itemRand.nextFloat() * 1.0f, (double) ((float) pos.getZ() + itemRand.nextFloat()), d0, d1, d2);
            }
        }
    }

    private static boolean applyBoneMeal(ItemStack stack, World world, BlockPos pos, EntityPlayer player)
    {
        IBlockState state = world.getBlockState(pos);

        int hook = ForgeEventFactory.onApplyBonemeal(player, world, pos, state, stack);

        if(hook != 0)
        {
            return hook > 0;
        }
        if(state.getBlock() instanceof IGrowable)
        {
            IGrowable growable = (IGrowable) state.getBlock();

            if(growable.canGrow(world, pos, state, world.isRemote))
            {
                if(!world.isRemote)
                {
                    if(growable.canUseBonemeal(world, world.rand, pos, state))
                    {
                        growable.grow(world, world.rand, pos, state);
                    }

                    stack.shrink(1);
                }

                return true;
            }
        }

        return false;
    }
}