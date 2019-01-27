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

import logictechcorp.libraryex.block.BlockTileEntity;
import logictechcorp.libraryex.block.builder.BlockBuilder;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.tileentity.TileEntityNetherReactorCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class BlockNetherReactorCore extends BlockTileEntity<TileEntityNetherReactorCore>
{
    public static final PropertyBool ACTIVATED = PropertyBool.create("activated");

    public BlockNetherReactorCore()
    {
        super(NetherEx.getResource("nether_reactor_core"), TileEntityNetherReactorCore.class, new BlockBuilder(Material.IRON, MapColor.LIGHT_BLUE).hardness(2.0F).resistance(2.0F).creativeTab(NetherEx.instance.getCreativeTab()));
        this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVATED, false));
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntityNetherReactorCore reactorCore = (TileEntityNetherReactorCore) world.getTileEntity(pos);

        if(reactorCore == null)
        {
            return false;
        }

        ItemStackHandler inventory = reactorCore.getInventory();
        ItemStack inventoryStack = inventory.getStackInSlot(0);
        ItemStack heldStack = player.getHeldItem(hand);

        if(!player.isSneaking())
        {
            if(inventoryStack.isEmpty() && !heldStack.isEmpty() && reactorCore.acceptsItemStack(heldStack))
            {
                inventory.setStackInSlot(0, heldStack.splitStack(1));
            }
        }
        else
        {
            if(!inventoryStack.isEmpty())
            {
                if(!world.isRemote)
                {
                    reactorCore.spawnItemStack(world, pos.up(), inventory.getStackInSlot(0));
                }

                inventory.setStackInSlot(0, ItemStack.EMPTY);
            }
        }

        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        TileEntityNetherReactorCore reactorCore = (TileEntityNetherReactorCore) world.getTileEntity(pos);

        if(reactorCore == null)
        {
            return;
        }

        ItemStackHandler inventory = reactorCore.getInventory();
        ItemStack inventoryStack = inventory.getStackInSlot(0);

        if (!inventoryStack.isEmpty() && block != this && world.isBlockPowered(pos))
        {
            world.setBlockState(pos, state.withProperty(ACTIVATED, true), 2);
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return  !state.getValue(ACTIVATED) ? 5 : 15;
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
        return !state.getValue(ACTIVATED) ? 2.0F : -1.0F;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return this.getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ACTIVATED, meta != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ACTIVATED) ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ACTIVATED);
    }

}
