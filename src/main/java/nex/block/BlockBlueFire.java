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
import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;

import java.util.Random;

public class BlockBlueFire extends BlockLibEx
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UPPER = PropertyBool.create("up");

    public BlockBlueFire()
    {
        super(NetherEx.instance, "blue_fire", Material.FIRE);
        this.setLightLevel(1.0F);
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(UPPER, false));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        if(rand.nextInt(24) == 0)
        {
            world.playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }

        if(!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && !this.canCatchFire(world, pos.down(), EnumFacing.UP))
        {
            if(this.canCatchFire(world, pos.west(), EnumFacing.EAST))
            {
                for(int j = 0; j < 2; ++j)
                {
                    double d3 = (double) pos.getX() + rand.nextDouble() * 0.10000000149011612D;
                    double d8 = (double) pos.getY() + rand.nextDouble();
                    double d13 = (double) pos.getZ() + rand.nextDouble();
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d3, d8, d13, 0.0D, 0.0D, 0.0D);
                }
            }

            if(this.canCatchFire(world, pos.east(), EnumFacing.WEST))
            {
                for(int k = 0; k < 2; ++k)
                {
                    double d4 = (double) (pos.getX() + 1) - rand.nextDouble() * 0.10000000149011612D;
                    double d9 = (double) pos.getY() + rand.nextDouble();
                    double d14 = (double) pos.getZ() + rand.nextDouble();
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d4, d9, d14, 0.0D, 0.0D, 0.0D);
                }
            }

            if(this.canCatchFire(world, pos.north(), EnumFacing.SOUTH))
            {
                for(int l = 0; l < 2; ++l)
                {
                    double d5 = (double) pos.getX() + rand.nextDouble();
                    double d10 = (double) pos.getY() + rand.nextDouble();
                    double d15 = (double) pos.getZ() + rand.nextDouble() * 0.10000000149011612D;
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d5, d10, d15, 0.0D, 0.0D, 0.0D);
                }
            }

            if(this.canCatchFire(world, pos.south(), EnumFacing.NORTH))
            {
                for(int i1 = 0; i1 < 2; ++i1)
                {
                    double d6 = (double) pos.getX() + rand.nextDouble();
                    double d11 = (double) pos.getY() + rand.nextDouble();
                    double d16 = (double) (pos.getZ() + 1) - rand.nextDouble() * 0.10000000149011612D;
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d6, d11, d16, 0.0D, 0.0D, 0.0D);
                }
            }

            if(this.canCatchFire(world, pos.up(), EnumFacing.DOWN))
            {
                for(int j1 = 0; j1 < 2; ++j1)
                {
                    double d7 = (double) pos.getX() + rand.nextDouble();
                    double d12 = (double) (pos.getY() + 1) - rand.nextDouble() * 0.10000000149011612D;
                    double d17 = (double) pos.getZ() + rand.nextDouble();
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        else
        {
            for(int i = 0; i < 3; ++i)
            {
                double d0 = (double) pos.getX() + rand.nextDouble();
                double d1 = (double) pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
                double d2 = (double) pos.getZ() + rand.nextDouble();
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
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
    public boolean isCollidable()
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public boolean requiresUpdates()
    {
        return false;
    }

    @Override
    public int tickRate(World world)
    {
        return 15;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(world.getGameRules().getBoolean("doFireTick"))
        {
            if(!this.canPlaceBlockAt(world, pos))
            {
                world.setBlockToAir(pos);
            }

            boolean flag = world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP);

            int i = state.getValue(AGE);

            if(!flag && world.isRaining() && this.canDie(world, pos) && rand.nextFloat() < 0.2F + (float) i * 0.03F)
            {
                world.setBlockToAir(pos);
            }
            else
            {
                if(i < 15)
                {
                    state = state.withProperty(AGE, i + rand.nextInt(3) / 2);
                    world.setBlockState(pos, state, 4);
                }

                world.scheduleUpdate(pos, this, this.tickRate(world) + rand.nextInt(10));

                if(!flag)
                {
                    if(!this.canNeighborCatchFire(world, pos))
                    {
                        if(!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) || i > 3)
                        {
                            world.setBlockToAir(pos);
                        }

                        return;
                    }

                    if(!this.canCatchFire(world, pos.down(), EnumFacing.UP) && i == 15 && rand.nextInt(4) == 0)
                    {
                        world.setBlockToAir(pos);
                        return;
                    }
                }

                boolean flag1 = world.isBlockinHighHumidity(pos);
                int j = 0;

                if(flag1)
                {
                    j = -50;
                }

                this.tryCatchFire(world, pos.east(), 300 + j, rand, i, EnumFacing.WEST);
                this.tryCatchFire(world, pos.west(), 300 + j, rand, i, EnumFacing.EAST);
                this.tryCatchFire(world, pos.down(), 250 + j, rand, i, EnumFacing.UP);
                this.tryCatchFire(world, pos.up(), 250 + j, rand, i, EnumFacing.DOWN);
                this.tryCatchFire(world, pos.north(), 300 + j, rand, i, EnumFacing.SOUTH);
                this.tryCatchFire(world, pos.south(), 300 + j, rand, i, EnumFacing.NORTH);

                for(int k = -1; k <= 1; ++k)
                {
                    for(int l = -1; l <= 1; ++l)
                    {
                        for(int i1 = -1; i1 <= 4; ++i1)
                        {
                            if(k != 0 || i1 != 0 || l != 0)
                            {
                                int j1 = 100;

                                if(i1 > 1)
                                {
                                    j1 += (i1 - 1) * 100;
                                }

                                BlockPos blockpos = pos.add(k, i1, l);
                                int k1 = this.getNeighborEncouragement(world, blockpos);

                                if(k1 > 0)
                                {
                                    int l1 = (k1 + 40 + world.getDifficulty().getId() * 7) / (i + 30);

                                    if(flag1)
                                    {
                                        l1 /= 2;
                                    }

                                    if(l1 > 0 && rand.nextInt(j1) <= l1 && (!world.isRaining() || !this.canDie(world, blockpos)))
                                    {
                                        int i2 = i + rand.nextInt(5) / 4;

                                        if(i2 > 15)
                                        {
                                            i2 = 15;
                                        }

                                        world.setBlockState(blockpos, state.withProperty(AGE, i2), 3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) || this.canNeighborCatchFire(world, pos);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        if(!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && !this.canNeighborCatchFire(world, pos))
        {
            world.setBlockToAir(pos);
        }
        else
        {
            world.scheduleUpdate(pos, this, this.tickRate(world) + world.rand.nextInt(10));
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        if(!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && !this.canNeighborCatchFire(world, pos))
        {
            world.setBlockToAir(pos);
        }
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(entity instanceof EntityItem)
        {
            entity.setDead();
        }
        else if(entity instanceof EntityLivingBase)
        {
            if(!entity.isImmuneToFire())
            {
                entity.attackEntityFrom(DamageSource.IN_FIRE, 4.0F);
            }
        }
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(AGE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if(!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && !this.canCatchFire(world, pos.down(), EnumFacing.UP))
        {
            return state.withProperty(NORTH, this.canCatchFire(world, pos.north(), EnumFacing.SOUTH))
                    .withProperty(EAST, this.canCatchFire(world, pos.east(), EnumFacing.WEST))
                    .withProperty(SOUTH, this.canCatchFire(world, pos.south(), EnumFacing.NORTH))
                    .withProperty(WEST, this.canCatchFire(world, pos.west(), EnumFacing.EAST))
                    .withProperty(UPPER, this.canCatchFire(world, pos.up(), EnumFacing.DOWN));
        }
        return this.getDefaultState();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AGE, NORTH, EAST, SOUTH, WEST, UPPER);
    }

    private boolean canCatchFire(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return world.getBlockState(pos).getBlock().isFlammable(world, pos, face);
    }

    private boolean canNeighborCatchFire(World world, BlockPos pos)
    {
        for(EnumFacing enumfacing : EnumFacing.values())
        {
            if(this.canCatchFire(world, pos.offset(enumfacing), enumfacing.getOpposite()))
            {
                return true;
            }
        }

        return false;
    }

    private int getNeighborEncouragement(World world, BlockPos pos)
    {
        if(!world.isAirBlock(pos))
        {
            return 0;
        }
        else
        {
            int i = 0;

            for(EnumFacing enumfacing : EnumFacing.values())
            {
                i = Math.max(world.getBlockState(pos.offset(enumfacing)).getBlock().getFireSpreadSpeed(world, pos.offset(enumfacing), enumfacing.getOpposite()), i);
            }

            return i;
        }
    }

    private void tryCatchFire(World world, BlockPos pos, int chance, Random random, int age, EnumFacing face)
    {
        int i = world.getBlockState(pos).getBlock().getFlammability(world, pos, face);

        if(random.nextInt(chance) < i)
        {
            IBlockState iblockstate = world.getBlockState(pos);

            if(random.nextInt(age + 10) < 5 && !world.isRainingAt(pos))
            {
                int j = age + random.nextInt(5) / 4;

                if(j > 15)
                {
                    j = 15;
                }

                world.setBlockState(pos, this.getDefaultState().withProperty(AGE, j), 3);
            }
            else
            {
                world.setBlockToAir(pos);
            }

            if(iblockstate.getBlock() == Blocks.TNT)
            {
                Blocks.TNT.onPlayerDestroy(world, pos, iblockstate.withProperty(BlockTNT.EXPLODE, true));
            }
        }
    }

    private boolean canDie(World world, BlockPos pos)
    {
        return world.isRainingAt(pos) || world.isRainingAt(pos.west()) || world.isRainingAt(pos.east()) || world.isRainingAt(pos.north()) || world.isRainingAt(pos.south());
    }

    @Override
    public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return PathNodeType.DAMAGE_FIRE;
    }
}
