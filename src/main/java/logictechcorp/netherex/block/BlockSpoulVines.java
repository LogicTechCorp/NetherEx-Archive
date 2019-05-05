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

import logictechcorp.libraryex.block.BlockMod;
import logictechcorp.libraryex.block.builder.BlockProperties;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockSpoulVines extends BlockMod implements IShearable
{
    public static final PropertyBool GROW_FRUIT = PropertyBool.create("grow_fruit");

    public BlockSpoulVines()
    {
        super(NetherEx.getResource("spoul_vines"), new BlockProperties(Material.PLANTS, MapColor.BROWN).hardness(4.0F).tickRandomly());
        this.setLightOpacity(1);
        this.setDefaultState(this.blockState.getBaseState().withProperty(GROW_FRUIT, true));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        if(random.nextInt(50) == 0)
        {
            BlockPos posDown = pos.down();

            if(world.getBlockState(posDown).getBlock().isReplaceable(world, posDown))
            {
                world.setBlockState(posDown, NetherExBlocks.SPOUL_FRUIT.getDefaultState());
            }
        }
    }

    @Override
    public List<ItemStack> onSheared(ItemStack stack, IBlockAccess world, BlockPos pos, int fortune)
    {
        return NonNullList.from(new ItemStack(this));
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing facing)
    {
        BlockPos posUp = pos.up();
        return facing == EnumFacing.DOWN && world.getBlockState(posUp).isSideSolid(world, posUp, facing) && this.canPlaceBlockAt(world, pos);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return world.getBlockState(pos.up()).getBlock() == NetherExBlocks.SPOUL_SHROOM_CAP;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isShearable(ItemStack stack, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return true;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.AIR;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(GROW_FRUIT, meta != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(GROW_FRUIT) ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, GROW_FRUIT);
    }
}
