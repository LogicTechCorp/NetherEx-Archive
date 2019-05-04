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
import logictechcorp.netherex.tileentity.TileEntityPossessedSoulSand;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPossessedSoulSand extends BlockTileEntity<TileEntityPossessedSoulSand>
{
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 7);
    protected static final AxisAlignedBB SOUL_SAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public BlockPossessedSoulSand()
    {
        super(NetherEx.getResource("possessed_soul_sand"), TileEntityPossessedSoulSand.class, new BlockProperties(Material.SAND, MapColor.BROWN).sound(SoundType.SAND).hardness(0.5F));
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos)
    {
        return SOUL_SAND_AABB;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(entity instanceof EntityLivingBase)
        {
            entity.setInWeb();
            entity.setSprinting(true);
        }

        TileEntity tileEntity = world.getTileEntity(pos);

        if(tileEntity instanceof TileEntityPossessedSoulSand)
        {
            if(entity instanceof EntityPlayer)
            {
                ((TileEntityPossessedSoulSand) tileEntity).consumeExperience((EntityPlayer) entity);
            }
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tileEntity = world.getTileEntity(pos);

        if(tileEntity instanceof TileEntityPossessedSoulSand)
        {
            ((TileEntityPossessedSoulSand) tileEntity).expelExperience();
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.SOUL_SAND);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if(meta > 7)
        {
            meta = 7;
        }
        else if(meta < 0)
        {
            meta = 0;
        }

        return this.getDefaultState().withProperty(STAGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(STAGE);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, STAGE);
    }

}
