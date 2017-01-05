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
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.init.NetherExBlocks;
import nex.world.NetherExTeleporter;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BlockNetherPortal extends BlockNetherEx
{
    private static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);
    private static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D);
    private static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D);
    private static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.0D, 0.4375D, 0.0D, 1.0D, 0.5625D, 1.0D);

    public BlockNetherPortal()
    {
        super("block_portal_nether", Material.PORTAL);

        setSoundType(SoundType.GLASS);
        setLightLevel(0.75F);
        setHardness(-1);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return world.getBlockState(pos.offset(side)).getBlock() != this && super.shouldSideBeRendered(state, world, pos, side);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        if(rand.nextInt(100) == 0)
        {
            world.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        for(int i = 0; i < 4; ++i)
        {
            double d0 = (double) ((float) pos.getX() + rand.nextFloat());
            double d1 = (double) ((float) pos.getY() + rand.nextFloat());
            double d2 = (double) ((float) pos.getZ() + rand.nextFloat());
            double d3 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            if(world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this)
            {
                d0 = (double) pos.getX() + 0.5D + 0.25D * (double) j;
                d3 = (double) (rand.nextFloat() * 2.0F * (float) j);
            }
            else
            {
                d2 = (double) pos.getZ() + 0.5D + 0.25D * (double) j;
                d5 = (double) (rand.nextFloat() * 2.0F * (float) j);
            }

            world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }
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
        switch(state.getValue(AXIS))
        {
            case X:
                return X_AABB;
            case Y:
            default:
                return Y_AABB;
            case Z:
                return Z_AABB;
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(!world.isRemote && !entity.isDead && entity.isNonBoss())
        {
            int fromDimensionId = entity.dimension;
            int toDimensionId = fromDimensionId == -1 ? 0 : -1;

            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance().getServer();
            WorldServer toWorldServer = server.worldServerForDimension(toDimensionId);

            if(toDimensionId != -1 && server.getAllowNether() || toDimensionId == -1)
            {
                entity.lastPortalPos = pos;
                entity.lastPortalVec = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
                entity.teleportDirection = getMetaFromState(state) == 1 ? EnumFacing.SOUTH : EnumFacing.NORTH;

                if(entity.portalCounter == 100)
                {
                    toWorldServer.worldTeleporter = new NetherExTeleporter(toWorldServer);
                    entity.changeDimension(toDimensionId);
                    toWorldServer.worldTeleporter = new Teleporter(toWorldServer);
                }
                else if(entity.portalCounter == 0)
                {
                    entity.portalCounter = 400;
                }
            }
        }
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state)
    {
        return ItemStack.EMPTY;
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

    public boolean trySpawnPortal(World world, BlockPos pos, Entity entity, EnumFacing facing, boolean forceSpawn)
    {
        BlockPos offset1 = pos.offset(facing);
        BlockPos offset2 = pos.offset(facing, 2);
        boolean canSpawnPortal = false;

        if(world.getBlockState(offset1).getMaterial().isReplaceable() && world.getBlockState(offset2).getMaterial().isReplaceable())
        {
            canSpawnPortal = true;
        }

        if(canSpawnPortal || forceSpawn)
        {
            int meta = 0;

            if(entity.getHorizontalFacing().getAxis() == EnumFacing.Axis.X && (facing == EnumFacing.DOWN || facing == EnumFacing.UP))
            {
                meta = 2;
            }
            else if(facing != EnumFacing.DOWN && facing != EnumFacing.UP)
            {
                meta = 1;
            }
            else if(entity.getHorizontalFacing().getAxis() == EnumFacing.Axis.Z && (facing == EnumFacing.DOWN || facing == EnumFacing.UP))
            {
                meta = 0;
            }

            world.setBlockState(pos, NetherExBlocks.BLOCK_OBSIDIAN_CRYING.getStateFromMeta(meta));
            world.setBlockState(offset1, NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(meta));
            world.setBlockState(offset2, NetherExBlocks.BLOCK_PORTAL_NETHER.getStateFromMeta(meta));
            return true;
        }

        return false;
    }
}
