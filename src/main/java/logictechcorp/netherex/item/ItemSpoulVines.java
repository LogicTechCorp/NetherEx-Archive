/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
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

import logictechcorp.libraryex.item.ItemBlockMod;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpoulVines extends ItemBlockMod
{
    public ItemSpoulVines()
    {
        super(NetherExBlocks.SPOUL_VINES, NetherExBlocks.getDefaultItemBlockProperties());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing facing, EntityPlayer player, ItemStack stack)
    {
        Block block = world.getBlockState(pos).getBlock();

        if(!block.isReplaceable(world, pos))
        {
            pos = pos.offset(facing);
        }

        BlockPos posUp = pos.up();
        return facing == EnumFacing.DOWN && world.getBlockState(posUp).isSideSolid(world, posUp, facing);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if(!block.isReplaceable(world, pos))
        {
            pos = pos.offset(facing);
        }

        ItemStack stack = player.getHeldItem(hand);

        if(!stack.isEmpty() && player.canPlayerEdit(pos, facing, stack))
        {
            int meta = this.getMetadata(stack.getMetadata());
            IBlockState stateForPlacement = this.block.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, player, hand);

            if(this.placeBlockAt(stack, player, world, pos, facing, hitX, hitY, hitZ, stateForPlacement))
            {
                stateForPlacement = world.getBlockState(pos);
                SoundType soundtype = stateForPlacement.getBlock().getSoundType(stateForPlacement, world, pos, player);
                world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                stack.shrink(1);
            }

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }
}
