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

package logictechcorp.netherex.block;

import logictechcorp.libraryex.block.BlockTileEntity;
import logictechcorp.libraryex.block.builder.BlockProperties;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.tileentity.TileEntityHauntedSoulSand;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHauntedSoulSand extends BlockTileEntity<TileEntityHauntedSoulSand>
{
    protected static final AxisAlignedBB SOUL_SAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public BlockHauntedSoulSand()
    {
        super(NetherEx.getResource("haunted_soul_sand"), TileEntityHauntedSoulSand.class, new BlockProperties(Material.SAND, MapColor.BROWN));
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return SOUL_SAND_AABB;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        TileEntity tileEntity = world.getTileEntity(pos);

        if(tileEntity instanceof TileEntityHauntedSoulSand)
        {

            if(entity instanceof EntityPlayer)
            {
                ((TileEntityHauntedSoulSand) tileEntity).drainExperience((EntityPlayer) entity);
            }
        }

        entity.motionX *= 0.4D;
        entity.motionZ *= 0.4D;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tileEntity = world.getTileEntity(pos);

        if(tileEntity instanceof TileEntityHauntedSoulSand)
        {
            ((TileEntityHauntedSoulSand) tileEntity).expelExperience();
        }

        super.breakBlock(world, pos, state);
    }
}
