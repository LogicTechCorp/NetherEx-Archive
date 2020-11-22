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

import logictechcorp.libraryex.block.BlockMod;
import logictechcorp.libraryex.block.property.BlockProperties;
import logictechcorp.libraryex.utility.CollectionHelper;
import logictechcorp.libraryex.utility.EntityHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.NetherExConfig;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockThornstalk extends BlockMod
{
    public static final PropertyEnum<EnumPart> PART = PropertyEnum.create("part", EnumPart.class);
    protected static final AxisAlignedBB THORNSTALK_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public BlockThornstalk()
    {
        super(NetherEx.getResource("thornstalk"), new BlockProperties(Material.PLANTS, MapColor.BROWN).sound(SoundType.PLANT));
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
        return THORNSTALK_AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(entity instanceof EntityPlayer)
        {
            entity.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }
        else if(entity instanceof EntityLivingBase && !CollectionHelper.contains(NetherExConfig.block.thornstalk.mobBlacklist, EntityHelper.getEntityLocation((EntityLivingBase) entity)))
        {
            entity.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }
        else if(entity instanceof EntityItem && NetherExConfig.block.thornstalk.canDestroyItems)
        {
            entity.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighbor, BlockPos fromPos)
    {
        Block blockUp = world.getBlockState(pos.up()).getBlock();
        IBlockState blockDown = world.getBlockState(pos.down());

        if(!((blockUp == Blocks.AIR || blockUp == this) && (blockDown.isSideSolid(world, pos.down(), EnumFacing.UP) || blockDown.getBlock() == this)))
        {
            world.destroyBlock(pos, true);
        }
        else
        {
            world.setBlockState(pos, this.getActualState(state, world, pos), 2);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();
        Block blockDown = world.getBlockState(pos.down()).getBlock();
        Block blockDown2 = world.getBlockState(pos.down(2)).getBlock();
        Block blockDown3 = world.getBlockState(pos.down(3)).getBlock();

        return !(blockDown == this && blockDown2 == this && blockDown3 == this) && block == Blocks.AIR && (blockDown == this || blockDown == Blocks.SOUL_SAND);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        IBlockState stateUp = world.getBlockState(pos.up());

        if(stateUp.getBlock() != this)
        {
            return state.withProperty(PART, EnumPart.TOP);
        }
        else if(stateUp == state.withProperty(PART, EnumPart.TOP))
        {
            return state.withProperty(PART, EnumPart.MIDDLE);
        }
        else
        {
            return state.withProperty(PART, EnumPart.BOTTOM);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(PART, EnumPart.fromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(PART).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, PART);
    }

    public void generate(World world, Random random, BlockPos pos)
    {
        int height = random.nextInt(3) + 1;

        if(height == 1)
        {
            world.setBlockState(pos, this.getDefaultState().withProperty(PART, EnumPart.TOP), 2);
        }
        else if(height == 2)
        {
            world.setBlockState(pos.up(), this.getDefaultState().withProperty(PART, EnumPart.TOP), 2);
            world.setBlockState(pos, this.getDefaultState().withProperty(PART, EnumPart.MIDDLE), 2);
        }
        else
        {
            world.setBlockState(pos.up(2), this.getDefaultState().withProperty(PART, EnumPart.TOP), 2);
            world.setBlockState(pos.up(), this.getDefaultState().withProperty(PART, EnumPart.MIDDLE), 2);
            world.setBlockState(pos, this.getDefaultState().withProperty(PART, EnumPart.BOTTOM), 2);
        }
    }

    @Override
    public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return PathNodeType.DAMAGE_CACTUS;
    }

    public enum EnumPart implements IStringSerializable
    {
        TOP,
        MIDDLE,
        BOTTOM;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase();
        }

        public static EnumPart fromMeta(int meta)
        {
            if(meta < 0 || meta >= values().length)
            {
                meta = 0;
            }

            return values()[meta];
        }
    }
}
