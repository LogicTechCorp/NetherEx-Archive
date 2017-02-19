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

package nex.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.tileentity.TileEntitySummoningAltar;

public class BlockSummoningAltar extends BlockTileEntity<TileEntitySummoningAltar>
{
    public BlockSummoningAltar()
    {
        super("tile_altar_summoning", Material.ROCK, TileEntitySummoningAltar.class);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntitySummoningAltar altar = (TileEntitySummoningAltar) world.getTileEntity(pos);

        if(altar == null)
        {
            return false;
        }

        if(!player.isSneaking())
        {
            if(altar.getInventory().getStackInSlot(0).isEmpty() && !player.getHeldItemMainhand().isEmpty())
            {
                altar.getInventory().setStackInSlot(0, player.getHeldItemMainhand().splitStack(1));
            }
        }
        else
        {
            if(!altar.getInventory().getStackInSlot(0).isEmpty())
            {
                if(!world.isRemote)
                {
                    altar.spawnItemStack(world, pos.up(), altar.getInventory().getStackInSlot(0));
                }
                altar.getInventory().setStackInSlot(0, ItemStack.EMPTY);
            }
        }

        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }
}
