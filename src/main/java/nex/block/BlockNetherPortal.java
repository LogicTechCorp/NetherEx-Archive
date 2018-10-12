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

import com.google.common.collect.Queues;
import lex.block.BlockLibEx;
import lex.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.handler.ConfigHandler;
import nex.init.NetherExBlocks;
import nex.world.TeleporterNetherEx;

import java.util.Queue;
import java.util.Random;

/**
 * A block that allows for teleportation between any dimension and the nether
 * <p>
 * Based on code written by Alz454 here:
 * https://github.com/enhancedportals/enhancedportals/blob/1647357d3cbed1289a653347e2107d92a2875a65/src/main/java/enhanced/portals/portal/PortalUtils.java
 */
public class BlockNetherPortal extends BlockLibEx
{
    public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);

    private static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
    private static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.0D, 0.375D, 0.0D, 1.0D, 0.625D, 1.0D);
    private static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);

    public BlockNetherPortal()
    {
        super(NetherEx.instance, "nether_portal", Material.PORTAL);
        this.setSoundType(SoundType.GLASS);
        this.setLightLevel(0.75F);
        this.setTickRandomly(true);
        this.setHardness(-1F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return world.getBlockState(pos.offset(side)).getBlock() != this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
    {
        if(rand.nextInt(100) == 0)
        {
            world.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        for(int i = 0; i < 4; i++)
        {
            double posX = (double) ((float) pos.getX() + rand.nextFloat());
            double posY = (double) ((float) pos.getY() + rand.nextFloat());
            double posZ = (double) ((float) pos.getZ() + rand.nextFloat());
            double speedX = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            double speedY = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            double speedZ = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            int multiplier = rand.nextInt(2) * 2 - 1;

            if(world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this)
            {
                posX = (double) pos.getX() + 0.5D + 0.25D * (double) multiplier;
                speedX = (double) (rand.nextFloat() * 2.0F * (float) multiplier);
            }
            else
            {
                posZ = (double) pos.getZ() + 0.5D + 0.25D * (double) multiplier;
                speedZ = (double) (rand.nextFloat() * 2.0F * (float) multiplier);
            }

            world.spawnParticle(EnumParticleTypes.PORTAL, posX, posY, posZ, speedX, speedY, speedZ);
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
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        EnumFacing.Axis axis = state.getValue(AXIS);

        if(axis == EnumFacing.Axis.X)
        {
            if(world.isAirBlock(pos.down()) || world.isAirBlock(pos.up()) || world.isAirBlock(pos.north()) || world.isAirBlock(pos.south()))
            {
                world.setBlockToAir(pos);
            }
        }
        else if(axis == EnumFacing.Axis.Y)
        {
            if(world.isAirBlock(pos.north()) || world.isAirBlock(pos.south()) || world.isAirBlock(pos.west()) || world.isAirBlock(pos.east()))
            {
                world.setBlockToAir(pos);
            }
        }
        else if(axis == EnumFacing.Axis.Z)
        {
            if(world.isAirBlock(pos.down()) || world.isAirBlock(pos.up()) || world.isAirBlock(pos.west()) || world.isAirBlock(pos.east()))
            {
                world.setBlockToAir(pos);
            }
        }

        if(world.provider.isSurfaceWorld() && world.getGameRules().getBoolean("doMobSpawning") && ConfigHandler.blockConfig.netherPortal.pigmanSpawnRarity > 0)
        {
            if(rand.nextInt(ConfigHandler.blockConfig.netherPortal.pigmanSpawnRarity) < world.getDifficulty().getId())
            {
                int posY = pos.getY();
                BlockPos blockPos;

                for(blockPos = pos; !world.getBlockState(blockPos).isSideSolid(world, blockPos, EnumFacing.UP) && blockPos.getY() > 0; blockPos = blockPos.down())
                {

                }

                if(posY > 0 && !world.getBlockState(blockPos.up()).isNormalCube())
                {
                    Entity entity = ItemMonsterPlacer.spawnCreature(world, EntityList.getKey(EntityPigZombie.class), (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 1.1D, (double) blockPos.getZ() + 0.5D);

                    if(entity != null)
                    {
                        entity.timeUntilPortal = entity.getPortalCooldown();
                    }
                }
            }
        }
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
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        world.scheduleUpdate(pos, this, 2);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        EnumFacing.Axis axis = state.getValue(AXIS);

        if(axis == EnumFacing.Axis.X)
        {
            if(pos.west().equals(fromPos) || pos.east().equals(fromPos))
            {
                if(world.getBlockState(fromPos).getBlock() == this)
                {
                    world.setBlockToAir(fromPos);
                }
            }
        }
        else if(axis == EnumFacing.Axis.Y)
        {
            if(pos.down().equals(fromPos) || pos.up().equals(fromPos))
            {
                if(world.getBlockState(fromPos).getBlock() == this)
                {
                    world.setBlockToAir(fromPos);
                }
            }
        }
        else if(axis == EnumFacing.Axis.Z)
        {
            if(pos.north().equals(fromPos) || pos.south().equals(fromPos))
            {
                if(world.getBlockState(fromPos).getBlock() == this)
                {
                    world.setBlockToAir(fromPos);
                }
            }
        }

        world.scheduleUpdate(pos, this, 1);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(!entity.isRiding() && !entity.isBeingRidden() && entity.isNonBoss())
        {
            if(entity.timeUntilPortal > 0)
            {
                entity.timeUntilPortal = entity.getPortalCooldown();
            }
            else
            {
                if(!world.isRemote)
                {
                    WorldServer worldServer = (WorldServer) world;
                    MinecraftServer minecraftServer = worldServer.getMinecraftServer();

                    if(minecraftServer != null)
                    {
                        PlayerList playerList = minecraftServer.getPlayerList();
                        int dimension = entity.dimension == DimensionType.NETHER.getId() ? DimensionType.OVERWORLD.getId() : DimensionType.NETHER.getId();
                        ITeleporter teleporter = TeleporterNetherEx.getTeleporterForWorld(minecraftServer, dimension);

                        entity.setPortal(pos);
                        entity.timeUntilPortal = entity.getPortalCooldown();

                        if(entity instanceof EntityPlayerMP)
                        {
                            playerList.transferPlayerToDimension((EntityPlayerMP) entity, dimension, teleporter);
                        }
                        else
                        {
                            playerList.transferEntityToWorld(entity, entity.dimension, worldServer, minecraftServer.getWorld(dimension), teleporter);
                        }
                    }
                }
            }
        }
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch(rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch(state.getValue(AXIS))
                {
                    case X:
                        return state.withProperty(AXIS, EnumFacing.Axis.Z);
                    case Y:
                        return state.withProperty(AXIS, EnumFacing.Axis.Y);
                    case Z:
                        return state.withProperty(AXIS, EnumFacing.Axis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return getMetaForAxis(state.getValue(AXIS));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AXIS, EnumFacing.Axis.values()[meta]);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AXIS);
    }

    public static int getMetaForAxis(EnumFacing.Axis axis)
    {
        return axis == EnumFacing.Axis.X ? 0 : axis == EnumFacing.Axis.Y ? 1 : 2;
    }

    public boolean trySpawnPortal(World world, BlockPos pos)
    {
        for(EnumFacing.Axis axis : EnumFacing.Axis.values())
        {
            for(EnumFacing facing : EnumFacing.values())
            {
                Queue<BlockPos> portalBlocks = this.findPortalBlocks(world, pos.offset(facing), axis);

                if(portalBlocks.size() > 0)
                {
                    for(BlockPos newPos : portalBlocks)
                    {
                        world.setBlockState(newPos, this.getDefaultState().withProperty(AXIS, axis));
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private Queue<BlockPos> findPortalBlocks(World world, BlockPos pos, EnumFacing.Axis axis)
    {
        Queue<BlockPos> portalBlocks = Queues.newArrayDeque();
        Queue<BlockPos> toProcess = Queues.newArrayDeque();
        int chances = 0;

        toProcess.add(pos);

        while(!toProcess.isEmpty())
        {
            BlockPos newPos = toProcess.remove();

            if(!portalBlocks.contains(newPos))
            {
                if(world.isAirBlock(newPos) || world.getBlockState(newPos).getBlock() == NetherExBlocks.BLUE_FIRE)
                {
                    int neighborBlocks = this.getNeighborBlocks(world, newPos, portalBlocks, axis);

                    if(neighborBlocks < 2)
                    {
                        if(chances < 40)
                        {
                            chances++;
                            neighborBlocks += 2;
                        }
                        else
                        {
                            return Queues.newArrayDeque();
                        }
                    }
                    if(neighborBlocks >= 2)
                    {
                        portalBlocks.add(newPos);
                        this.addNeighborBlocks(newPos, axis, toProcess);
                    }
                    else if(!this.isPortalPart(world, newPos))
                    {
                        return Queues.newArrayDeque();
                    }
                }
                else if(!this.isPortalPart(world, newPos))
                {
                    return Queues.newArrayDeque();
                }
            }
        }

        return portalBlocks;
    }

    private int getNeighborBlocks(World world, BlockPos pos, Queue<BlockPos> portalBlocks, EnumFacing.Axis axis)
    {
        int sides = 0;
        Queue<BlockPos> neighbors = Queues.newArrayDeque();

        this.addNeighborBlocks(pos, axis, neighbors);

        for(BlockPos newPos : neighbors)
        {
            if(portalBlocks.contains(newPos) || this.isPortalPart(world, newPos))
            {
                sides++;
            }
        }

        return sides;
    }

    private void addNeighborBlocks(BlockPos pos, EnumFacing.Axis axis, Queue<BlockPos> neighbors)
    {
        for(EnumFacing facing : EnumFacing.values())
        {
            if(axis == EnumFacing.Axis.X && (facing == EnumFacing.EAST || facing == EnumFacing.WEST))
            {
                continue;
            }
            else if(axis == EnumFacing.Axis.Y && (facing == EnumFacing.DOWN || facing == EnumFacing.UP))
            {
                continue;
            }
            else if(axis == EnumFacing.Axis.Z && (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH))
            {
                continue;
            }

            neighbors.add(pos.offset(facing));
        }
    }

    private boolean isPortalPart(World world, BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();
        return BlockHelper.isOreDict("obsidian", block) || block == NetherExBlocks.BLUE_FIRE || block == this;
    }
}
