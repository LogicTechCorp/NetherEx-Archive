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

package nex.block;

import lex.block.BlockLibEx;
import lex.client.model.item.ItemModelHandler;
import lex.world.gen.feature.FeatureBigMushroom;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.init.NetherExBlocks;

import java.util.Random;

public class BlockElderMushroom extends BlockLibEx implements IPlantable, IGrowable
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    private static final AxisAlignedBB MUSHROOM_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.4000000059604645D, 0.699999988079071D);

    public BlockElderMushroom()
    {
        super(NetherEx.instance, "elder_mushroom", Material.PLANTS);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return MUSHROOM_AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(EnumType type : EnumType.values())
        {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        if(super.canPlaceBlockAt(world, pos))
        {
            if(pos.getY() >= 0 && pos.getY() < 256)
            {
                IBlockState soil = world.getBlockState(pos.down());
                return soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this) || world.getLight(pos) < 13;
            }
        }

        return false;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        IBlockState soil = world.getBlockState(pos.down());
        if(!soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this))
        {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Nether;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return this.getDefaultState();
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
    {
        IBlockState soil = world.getBlockState(pos.down());
        return !isClient && soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this);
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state)
    {
        return false;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state)
    {
        world.setBlockToAir(pos);

        WorldGenerator elderMushroom;

        if(state.getValue(TYPE) == EnumType.BROWN)
        {
            elderMushroom = new FeatureBigMushroom(1, 1.0F, false, pos.getY(), pos.up(12).getY(), NetherExBlocks.ELDER_MUSHROOM_CAP.getDefaultState().withProperty(TYPE, EnumType.BROWN), NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState(), world.getBlockState(pos.down()), FeatureBigMushroom.Shape.FLAT);
        }
        else
        {
            elderMushroom = new FeatureBigMushroom(1, 1.0F, false, pos.getY(), pos.up(12).getY(), NetherExBlocks.ELDER_MUSHROOM_CAP.getDefaultState().withProperty(TYPE, EnumType.RED), NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState(), world.getBlockState(pos.down()), FeatureBigMushroom.Shape.BULB);
        }

        if(!elderMushroom.generate(world, rand, pos))
        {
            world.setBlockState(pos, state);
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return this.getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(TYPE, EnumType.fromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel()
    {
        for(BlockElderMushroom.EnumType type : BlockElderMushroom.EnumType.values())
        {
            ItemModelHandler.registerBlockModel(this, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_" + this.getRegistryName().getPath(), type.getName()), "inventory");
        }
    }

    public enum EnumType implements IStringSerializable
    {
        BROWN,
        RED;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase();
        }

        public static EnumType getRandom(Random rand)
        {
            return values()[rand.nextInt(values().length)];
        }

        public static EnumType fromMeta(int meta)
        {
            if(meta < 0 || meta >= values().length)
            {
                meta = 0;
            }

            return values()[meta];
        }
    }
}
