/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

import lex.item.ItemLibEx;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import nex.NetherEx;
import nex.block.BlockElderMushroom;

public class ItemWitherDust extends ItemLibEx
{
    public ItemWitherDust()
    {
        super(NetherEx.instance, "wither_dust");
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
            if(applyBoneMeal(stack, world, pos, player, hand))
            {
                if(!world.isRemote)
                {
                    world.playEvent(2005, pos, 0);
                }

                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.PASS;
        }
    }

    private static boolean applyBoneMeal(ItemStack stack, World world, BlockPos pos, EntityPlayer player, EnumHand hand)
    {
        IBlockState state = world.getBlockState(pos);

        int hook = ForgeEventFactory.onApplyBonemeal(player, world, pos, state, stack, hand);

        if(hook != 0)
        {
            return hook > 0;
        }
        if(state.getBlock() instanceof IGrowable)
        {
            IGrowable growable = (IGrowable) state.getBlock();

            if(growable.canGrow(world, pos, state, world.isRemote) || growable instanceof BlockElderMushroom)
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
