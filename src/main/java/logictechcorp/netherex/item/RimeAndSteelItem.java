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

package logictechcorp.netherex.item;

import logictechcorp.netherex.block.NetherExBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RimeAndSteelItem extends Item
{
    public RimeAndSteelItem(Item.Properties properties)
    {
        super(properties);

        DispenserBlock.registerDispenseBehavior(this, new OptionalDispenseBehavior()
        {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                this.successful = true;

                World world = source.getWorld();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));

                if(world.isAirBlock(pos))
                {
                    world.setBlockState(pos, NetherExBlocks.BLUE_FIRE.get().getDefaultState());

                    if(stack.attemptDamageItem(1, world.rand, null))
                    {
                        stack.setCount(0);
                    }
                }
                else if(world.getBlockState(pos).getBlock() == Blocks.TNT)
                {
                    Blocks.TNT.onPlayerDestroy(world, pos, Blocks.TNT.getDefaultState().with(TNTBlock.UNSTABLE, true));
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
                else
                {
                    this.successful = false;
                }

                return stack;
            }
        });
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        BlockPos pos = context.getPos();
        Direction direction = context.getFace();

        pos = pos.offset(direction);
        ItemStack stack = player.getHeldItem(hand);

        if(!player.canPlayerEdit(pos, direction, stack))
        {
            return ActionResultType.FAIL;
        }
        else
        {
            if(world.isAirBlock(pos))
            {
                world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                world.setBlockState(pos, NetherExBlocks.BLUE_FIRE.get().getDefaultState(), 11);
            }

            stack.damageItem(1, player, entity -> entity.sendBreakAnimation(hand));
            return ActionResultType.SUCCESS;
        }
    }
}