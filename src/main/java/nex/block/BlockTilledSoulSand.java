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

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.handler.ConfigHandler;
import nex.init.NetherExBlocks;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BlockTilledSoulSand extends BlockNetherEx
{
    public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
    private static final AxisAlignedBB FARMLAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

    public BlockTilledSoulSand()
    {
        super("block_sand_soul_tilled", Material.GROUND);

        setSoundType(SoundType.SAND);
        setLightOpacity(255);
        setHardness(0.6F);
        setTickRandomly(true);
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
        return FARMLAND_AABB;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        int moisture = state.getValue(MOISTURE);

        if(!hasFluid(world, pos))
        {
            if(moisture > 0)
            {
                world.setBlockState(pos, state.withProperty(MOISTURE, moisture - 1), 2);
            }
            else if(!hasCrops(world, pos))
            {
                turnToSoulSand(world, pos);
            }
        }
        else if(moisture < 7)
        {
            world.setBlockState(pos, state.withProperty(MOISTURE, 7), 2);
        }
        else if(moisture == 7)
        {
            world.scheduleUpdate(pos.up(), world.getBlockState(pos.up()).getBlock(), 600);
            world.scheduleUpdate(pos, this, 600);
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
    {
        return plantable.getPlantType(world, pos.offset(direction)) == EnumPlantType.Nether;
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance)
    {
        if(!world.isRemote && entity.canTrample(world, this, pos, fallDistance))
        {
            turnToSoulSand(world, pos);
        }

        super.onFallenUpon(world, pos, entity, fallDistance);
    }

    private void turnToSoulSand(World world, BlockPos pos)
    {
        world.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
        AxisAlignedBB boundingBox = Blocks.SOUL_SAND.getDefaultState().getCollisionBoundingBox(world, pos).offset(pos);

        for(Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, boundingBox))
        {
            entity.setPosition(entity.posX, boundingBox.maxY, entity.posZ);
        }
    }

    private boolean hasCrops(World world, BlockPos pos)
    {
        net.minecraft.block.Block block = world.getBlockState(pos.up()).getBlock();
        return block instanceof IPlantable && canSustainPlant(world.getBlockState(pos), world, pos, EnumFacing.UP, (IPlantable) block);
    }

    private boolean hasFluid(World world, BlockPos pos)
    {
        net.minecraft.block.Block block = ConfigHandler.block.soulSand.doesRequireIchor ? NetherExBlocks.FLUID_ICHOR : Blocks.LAVA;

        for(BlockPos.MutableBlockPos mutablePos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4)))
        {
            if(world.getBlockState(mutablePos).getBlock() == block)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, net.minecraft.block.Block block, BlockPos fromPos)
    {
        super.neighborChanged(state, world, pos, block, fromPos);

        if(world.getBlockState(pos.up()).getMaterial().isSolid())
        {
            turnToSoulSand(world, pos);
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        switch(side)
        {
            case UP:
                return true;
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                IBlockState testState = blockAccess.getBlockState(pos.offset(side));
                net.minecraft.block.Block block = testState.getBlock();
                return !testState.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH && block != NetherExBlocks.BLOCK_SAND_SOUL_TILLED;
            default:
                return super.shouldSideBeRendered(state, blockAccess, pos, side);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.SOUL_SAND);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(MOISTURE, meta & 7);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(MOISTURE);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, MOISTURE);
    }
}
