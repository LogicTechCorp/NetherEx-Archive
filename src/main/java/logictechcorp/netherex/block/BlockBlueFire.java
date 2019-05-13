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
import logictechcorp.libraryex.block.builder.BlockProperties;
import logictechcorp.libraryex.utility.RandomHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.init.NetherExMobEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.MapColor;
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
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockBlueFire extends BlockMod
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UPPER = PropertyBool.create("up");

    public BlockBlueFire()
    {
        super(NetherEx.getResource("blue_fire"), new BlockProperties(Material.FIRE, MapColor.LIGHT_BLUE).lightLevel(1.0F).tickRandomly());
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
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random)
    {
        if(random.nextInt(24) == 0)
        {
            world.playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
        }

        if(!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && !this.canCatchFire(world, pos.down(), EnumFacing.UP))
        {
            if(this.canCatchFire(world, pos.west(), EnumFacing.EAST))
            {
                for(int i = 0; i < 2; i++)
                {
                    double posX = (double) pos.getX() + random.nextDouble() * 0.10000000149011612D;
                    double posY = (double) pos.getY() + random.nextDouble();
                    double posZ = (double) pos.getZ() + random.nextDouble();
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
                }
            }

            if(this.canCatchFire(world, pos.east(), EnumFacing.WEST))
            {
                for(int i = 0; i < 2; i++)
                {
                    double posX = (double) (pos.getX() + 1) - random.nextDouble() * 0.10000000149011612D;
                    double posY = (double) pos.getY() + random.nextDouble();
                    double posZ = (double) pos.getZ() + random.nextDouble();
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
                }
            }

            if(this.canCatchFire(world, pos.north(), EnumFacing.SOUTH))
            {
                for(int i = 0; i < 2; i++)
                {
                    double posX = (double) pos.getX() + random.nextDouble();
                    double posY = (double) pos.getY() + random.nextDouble();
                    double posZ = (double) pos.getZ() + random.nextDouble() * 0.10000000149011612D;
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
                }
            }

            if(this.canCatchFire(world, pos.south(), EnumFacing.NORTH))
            {
                for(int i = 0; i < 2; i++)
                {
                    double posX = (double) pos.getX() + random.nextDouble();
                    double posY = (double) pos.getY() + random.nextDouble();
                    double posZ = (double) (pos.getZ() + 1) - random.nextDouble() * 0.10000000149011612D;
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
                }
            }

            if(this.canCatchFire(world, pos.up(), EnumFacing.DOWN))
            {
                for(int i = 0; i < 2; i++)
                {
                    double posX = (double) pos.getX() + random.nextDouble();
                    double posY = (double) (pos.getY() + 1) - random.nextDouble() * 0.10000000149011612D;
                    double posZ = (double) pos.getZ() + random.nextDouble();
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        else
        {
            for(int i = 0; i < 3; i++)
            {
                double posX = (double) pos.getX() + random.nextDouble();
                double posY = (double) pos.getY() + random.nextDouble() * 0.5D + 0.5D;
                double posZ = (double) pos.getZ() + random.nextDouble();
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
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
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        if(world.getGameRules().getBoolean("doFireTick"))
        {
            if(!this.canPlaceBlockAt(world, pos))
            {
                world.setBlockToAir(pos);
            }

            boolean sideSolid = world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP);

            int age = state.getValue(AGE);

            if(!sideSolid && world.isRaining() && this.canDie(world, pos) && random.nextFloat() < 0.2F + (float) age * 0.03F)
            {
                world.setBlockToAir(pos);
            }
            else
            {
                if(age < 15)
                {
                    state = state.withProperty(AGE, age + random.nextInt(3) / 2);
                    world.setBlockState(pos, state, 4);
                }

                world.scheduleUpdate(pos, this, this.tickRate(world) + random.nextInt(10));

                if(!sideSolid)
                {
                    if(!this.canNeighborCatchFire(world, pos))
                    {
                        if(!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) || age > 3)
                        {
                            world.setBlockToAir(pos);
                        }

                        return;
                    }

                    if(!this.canCatchFire(world, pos.down(), EnumFacing.UP) && age == 15 && random.nextInt(4) == 0)
                    {
                        world.setBlockToAir(pos);
                        return;
                    }
                }

                boolean highHumidity = world.isBlockinHighHumidity(pos);
                int j = 0;

                if(highHumidity)
                {
                    j = -50;
                }

                this.tryCatchFire(world, pos, pos.east(), 300 + j, random, age, EnumFacing.WEST);
                this.tryCatchFire(world, pos, pos.west(), 300 + j, random, age, EnumFacing.EAST);
                this.tryCatchFire(world, pos, pos.down(), 250 + j, random, age, EnumFacing.UP);
                this.tryCatchFire(world, pos, pos.up(), 250 + j, random, age, EnumFacing.DOWN);
                this.tryCatchFire(world, pos, pos.north(), 300 + j, random, age, EnumFacing.SOUTH);
                this.tryCatchFire(world, pos, pos.south(), 300 + j, random, age, EnumFacing.NORTH);

                for(int posX = -1; posX <= 1; posX++)
                {
                    for(int posZ = -1; posZ <= 1; posZ++)
                    {
                        for(int posY = -1; posY <= 4; posY++)
                        {
                            if(posX != 0 || posY != 0 || posZ != 0)
                            {
                                int fireHeightChance = 100;

                                if(posY > 1)
                                {
                                    fireHeightChance += (posY - 1) * 100;
                                }

                                BlockPos blockPos = pos.add(posX, posY, posZ);
                                int encouragement = this.getNeighborEncouragement(world, blockPos);

                                if(encouragement > 0)
                                {
                                    int fireHumidityChance = (encouragement + 40 + world.getDifficulty().getId() * 7) / (age + 30);

                                    if(highHumidity)
                                    {
                                        fireHumidityChance /= 2;
                                    }

                                    if(fireHumidityChance > 0 && random.nextInt(fireHeightChance) <= fireHumidityChance && (!world.isRaining() || !this.canDie(world, blockPos)))
                                    {
                                        int stage = age + random.nextInt(5) / 4;

                                        if(stage > 15)
                                        {
                                            stage = 15;
                                        }

                                        world.setBlockState(blockPos, state.withProperty(AGE, stage), 3);
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
            if(ConfigHandler.blockConfig.blueFire.minEntityTicksAlight > ConfigHandler.blockConfig.blueFire.maxEntityTicksAlight)
            {
                int temp = ConfigHandler.blockConfig.blueFire.minEntityTicksAlight;
                ConfigHandler.blockConfig.blueFire.minEntityTicksAlight = ConfigHandler.blockConfig.blueFire.maxEntityTicksAlight;
                ConfigHandler.blockConfig.blueFire.maxEntityTicksAlight = temp;
            }

            int ticks = RandomHelper.getNumberInRange(ConfigHandler.blockConfig.blueFire.minEntityTicksAlight, ConfigHandler.blockConfig.blueFire.maxEntityTicksAlight, world.rand);
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(NetherExMobEffects.FIRE_BURNING, ticks));
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

    @Override
    public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return PathNodeType.DAMAGE_FIRE;
    }

    private boolean canCatchFire(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return world.getBlockState(pos).getBlock().isFlammable(world, pos, face);
    }

    private boolean canNeighborCatchFire(World world, BlockPos pos)
    {
        for(EnumFacing facing : EnumFacing.values())
        {
            if(this.canCatchFire(world, pos.offset(facing), facing.getOpposite()))
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
            int encouragement = 0;

            for(EnumFacing facing : EnumFacing.values())
            {
                encouragement = Math.max(world.getBlockState(pos.offset(facing)).getBlock().getFireSpreadSpeed(world, pos.offset(facing), facing.getOpposite()), encouragement);
            }

            return encouragement;
        }
    }

    private void tryCatchFire(World world, BlockPos firePos, BlockPos blockPos, int chance, Random random, int age, EnumFacing face)
    {
        int flammability = world.getBlockState(blockPos).getBlock().getFlammability(world, blockPos, face);

        if(random.nextInt(chance) < flammability)
        {
            IBlockState state = world.getBlockState(blockPos);

            if(random.nextInt(age + 10) < 5 && !world.isRainingAt(blockPos))
            {
                int stage = age + random.nextInt(5) / 4;

                if(stage > 15)
                {
                    stage = 15;
                }

                world.setBlockState(blockPos, this.getDefaultState().withProperty(AGE, stage), 3);
            }
            else
            {
                world.setBlockToAir(blockPos);
                world.setBlockToAir(firePos);
            }

            if(state.getBlock() == Blocks.TNT)
            {
                Blocks.TNT.onPlayerDestroy(world, blockPos, state.withProperty(BlockTNT.EXPLODE, true));
            }
        }
    }

    private boolean canDie(World world, BlockPos pos)
    {
        return world.isRainingAt(pos) || world.isRainingAt(pos.west()) || world.isRainingAt(pos.east()) || world.isRainingAt(pos.north()) || world.isRainingAt(pos.south());
    }
}
