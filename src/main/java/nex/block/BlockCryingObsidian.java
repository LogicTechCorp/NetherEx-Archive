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
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.init.NetherExBlocks;

@SuppressWarnings("ConstantConditions")
public class BlockCryingObsidian extends BlockNetherEx
{
    public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);

    public BlockCryingObsidian()
    {
        super("block_obsidian_crying", Material.ROCK);

        setHardness(25.0F);
        setResistance(1000.0F);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(player.getHeldItem(hand).getItem() == Items.FLINT_AND_STEEL)
        {
            BlockPos offset1 = pos.offset(facing);
            BlockPos offset2 = pos.offset(facing, 2);
            boolean canSpawnPortal = false;

            if(world.getBlockState(offset1).getMaterial().isReplaceable() && world.getBlockState(offset2).getMaterial().isReplaceable())
            {
                canSpawnPortal = true;
            }

            if(canSpawnPortal)
            {
                if(player.getHorizontalFacing().getAxis() == EnumFacing.Axis.X && (facing == EnumFacing.DOWN || facing == EnumFacing.UP))
                {
                    world.setBlockState(pos, NetherExBlocks.BLOCK_OBSIDIAN_CRYING.getStateFromMeta(2));
                    world.setBlockState(offset1, NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(2));
                    world.setBlockState(offset2, NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(2));
                    return true;
                }
                else if(facing != EnumFacing.DOWN && facing != EnumFacing.UP)
                {
                    world.setBlockState(pos, NetherExBlocks.BLOCK_OBSIDIAN_CRYING.getStateFromMeta(1));
                    world.setBlockState(offset1, NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(1));
                    world.setBlockState(offset2, NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(1));
                    return true;
                }
                else if(player.getHorizontalFacing().getAxis() == EnumFacing.Axis.Z && (facing == EnumFacing.DOWN || facing == EnumFacing.UP))
                {
                    world.setBlockState(pos, NetherExBlocks.BLOCK_OBSIDIAN_CRYING.getStateFromMeta(0));
                    world.setBlockState(offset1, NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(0));
                    world.setBlockState(offset2, NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(0));
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        EnumFacing.Axis axis = state.getValue(AXIS);

        if(axis == EnumFacing.Axis.X || axis == EnumFacing.Axis.Z)
        {
            IBlockState up1 = world.getBlockState(pos.up());
            IBlockState up2 = world.getBlockState(pos.up(2));

            if(up1.getBlock() == NetherExBlocks.BLOCK_PORTAL_NETHER && up2.getBlock() == NetherExBlocks.BLOCK_PORTAL_NETHER)
            {
                if(up1 == NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(0) && up2 == NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(0))
                {
                    world.setBlockToAir(pos.up());
                    world.setBlockToAir(pos.up(2));
                }
                else if(up1 == NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(2) && up2 == NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(2))
                {
                    world.setBlockToAir(pos.up());
                    world.setBlockToAir(pos.up(2));
                }
            }
        }
        else
        {
            for(EnumFacing facing : EnumFacing.HORIZONTALS)
            {
                if(world.getBlockState(pos.offset(facing)) == NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(1))
                {
                    world.setBlockToAir(pos.offset(facing));
                    world.setBlockToAir(pos.offset(facing, 2));
                }
            }
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(AXIS, meta == 0 ? EnumFacing.Axis.X : meta == 1 ? EnumFacing.Axis.Y : EnumFacing.Axis.Z);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        EnumFacing.Axis axis = state.getValue(AXIS);
        return axis == EnumFacing.Axis.X ? 0 : axis == EnumFacing.Axis.Y ? 1 : 2;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AXIS);
    }
}
