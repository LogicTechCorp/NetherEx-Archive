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

import logictechcorp.libraryex.item.ItemMod;
import logictechcorp.libraryex.utility.BlockHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.init.NetherExItems;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockTNT;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRimeAndSteel extends ItemMod
{
    public ItemRimeAndSteel()
    {
        super(NetherEx.getResource("rime_and_steel"), NetherExItems.getDefaultItemProperties().copy().maxDamage(64));

        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new Bootstrap.BehaviorDispenseOptional()
        {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                this.successful = true;

                World world = source.getWorld();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().getValue(BlockDispenser.FACING));

                if(world.isAirBlock(pos))
                {
                    world.setBlockState(pos, NetherExBlocks.BLUE_FIRE.getDefaultState());

                    if(stack.attemptDamageItem(1, world.rand, null))
                    {
                        stack.setCount(0);
                    }
                }
                else if(world.getBlockState(pos).getBlock() == Blocks.TNT)
                {
                    Blocks.TNT.onPlayerDestroy(world, pos, Blocks.TNT.getDefaultState().withProperty(BlockTNT.EXPLODE, true));
                    world.setBlockToAir(pos);
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
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        pos = pos.offset(facing);
        ItemStack stack = player.getHeldItem(hand);

        if(!player.canPlayerEdit(pos, facing, stack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            if(world.isAirBlock(pos))
            {
                world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                world.setBlockState(pos, NetherExBlocks.BLUE_FIRE.getDefaultState(), 11);

                for(EnumFacing checkFacing : EnumFacing.values())
                {
                    if(BlockHelper.isOreDict(world.getBlockState(pos.offset(checkFacing)).getBlock(), "obsidian"))
                    {
                        NetherExBlocks.NETHER_PORTAL.trySpawnPortal(world, pos);
                    }
                }
            }

            stack.damageItem(1, player);
            return EnumActionResult.SUCCESS;
        }
    }

}
