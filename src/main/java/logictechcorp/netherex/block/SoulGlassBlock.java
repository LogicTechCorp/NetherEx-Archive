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

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.potion.NetherExEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SoulGlassBlock extends GlassBlock
{
    public static final String SHOOT_DIRECTION_KEY = NetherEx.MOD_ID + ":soul_shoot_direction";

    public SoulGlassBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity)
    {
        if(entity instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) entity;
            boolean sinking = player.getLookVec().y < -0.75D;

            CompoundNBT playerData = player.getPersistentData();
            Direction shootDirection = null;

            if(!player.isPotionActive(NetherExEffects.SOUL_SUCKED.get()))
            {
                playerData.remove(SHOOT_DIRECTION_KEY);
            }
            if(playerData.contains(SHOOT_DIRECTION_KEY))
            {
                shootDirection = Direction.byIndex(playerData.getInt(SHOOT_DIRECTION_KEY));
            }

            if(player.isShiftKeyDown())
            {
                if(!sinking)
                {
                    if(shootDirection == null)
                    {
                        shootDirection = player.getHorizontalFacing().getOpposite();
                    }

                    Block blockOneBack = world.getBlockState(pos.offset(shootDirection)).getBlock();
                    Block blockTwoBack = world.getBlockState(pos.offset(shootDirection, 2)).getBlock();
                    Block blockThreeBack = world.getBlockState(pos.offset(shootDirection, 3)).getBlock();

                    if(blockOneBack != this)
                    {
                        player.addPotionEffect(new EffectInstance(NetherExEffects.SOUL_SUCKED.get(), 5, 0, false, false));
                    }
                    if(blockOneBack == this && blockTwoBack != this)
                    {
                        player.addPotionEffect(new EffectInstance(NetherExEffects.SOUL_SUCKED.get(), 5, 1, false, false));
                    }
                    else if(blockOneBack == this && blockThreeBack != this)
                    {
                        player.addPotionEffect(new EffectInstance(NetherExEffects.SOUL_SUCKED.get(), 5, 2, false, false));
                    }
                    else if(blockOneBack == this)
                    {
                        player.addPotionEffect(new EffectInstance(NetherExEffects.SOUL_SUCKED.get(), 5, 3, false, false));
                    }
                }
                else
                {
                    if(shootDirection == null)
                    {
                        shootDirection = Direction.UP;
                    }

                    Block blockUpOne = world.getBlockState(pos.up()).getBlock();
                    Block blockUpTwo = world.getBlockState(pos.up(2)).getBlock();
                    Block blockUpThree = world.getBlockState(pos.up(3)).getBlock();

                    if(blockUpOne != this)
                    {
                        player.addPotionEffect(new EffectInstance(NetherExEffects.SOUL_SUCKED.get(), 5, 0, false, false));
                    }
                    if(blockUpOne == this && blockUpTwo != this)
                    {
                        player.addPotionEffect(new EffectInstance(NetherExEffects.SOUL_SUCKED.get(), 5, 1, false, false));
                    }
                    else if(blockUpOne == this && blockUpThree != this)
                    {
                        player.addPotionEffect(new EffectInstance(NetherExEffects.SOUL_SUCKED.get(), 5, 2, false, false));
                    }
                    else if(blockUpOne == this)
                    {
                        player.addPotionEffect(new EffectInstance(NetherExEffects.SOUL_SUCKED.get(), 5, 3, false, false));
                        player.setPosition(player.getPosX(), player.prevPosY, player.getPosZ());
                    }

                    player.setMotion(player.getMotion().mul(1.0D, 0.25D, 1.0D));
                }
            }
            else
            {
                EffectInstance effect = player.getActivePotionEffect(NetherExEffects.SOUL_SUCKED.get());
                int amplifier = 1;

                if(effect != null)
                {
                    amplifier = (effect.getAmplifier() + 1);
                }

                Vec3d motion = player.getMotion();

                if(!sinking && shootDirection != null && shootDirection != Direction.UP)
                {
                    Direction playerDirection = shootDirection.getOpposite();

                    if(playerDirection == Direction.NORTH)
                    {
                        if(motion.getZ() < 0)
                        {
                            player.setMotion(motion.mul(1.0D, 1.0D, -1.0D));
                        }
                        else if(motion.getZ() == 0)
                        {
                            player.setMotion(new Vec3d(motion.getX(), motion.getY(), 0.5D));
                        }
                    }
                    else if(playerDirection == Direction.EAST)
                    {
                        if(motion.getX() > 0)
                        {
                            player.setMotion(motion.mul(-1.0D, 1.0D, 1.0D));
                        }
                        else if(motion.getX() == 0)
                        {
                            player.setMotion(new Vec3d(-0.5D, motion.getY(), motion.getZ()));
                        }
                    }
                    else if(playerDirection == Direction.SOUTH)
                    {
                        if(motion.getZ() > 0)
                        {
                            player.setMotion(motion.mul(1.0D, 1.0D, -1.0D));
                        }
                        else if(motion.getZ() == 0)
                        {
                            player.setMotion(new Vec3d(motion.getX(), motion.getY(), -0.5D));
                        }
                    }
                    else if(playerDirection == Direction.WEST)
                    {
                        if(motion.getX() < 0)
                        {
                            player.setMotion(motion.mul(-1.0D, 1.0D, 1.0D));
                        }
                        else if(motion.getX() == 0)
                        {
                            player.setMotion(new Vec3d(0.5D, motion.getY(), motion.getZ()));
                        }
                    }

                    if(motion.getX() > -6.0D && motion.getX() < 6.0D)
                    {
                        player.setMotion(new Vec3d((motion.getX() * 1.15D * amplifier), motion.getY(), motion.getZ()));
                    }

                    if(motion.getZ() > -6.0D && motion.getZ() < 6.0D)
                    {
                        player.setMotion(new Vec3d(motion.getX(), motion.getY(), (motion.getZ() * 1.15D * amplifier)));
                    }
                }
                else
                {
                    if(motion.getY() <= 0)
                    {
                        player.setMotion(new Vec3d(motion.getX(), 0.105D, motion.getZ()));
                    }
                    if(motion.getY() < 1.315D)
                    {
                        player.setMotion(new Vec3d(motion.getX(), (motion.getY() + (amplifier * 0.1D)), motion.getZ()));
                    }
                }
            }

            if(shootDirection != null)
            {
                playerData.putInt(SHOOT_DIRECTION_KEY, shootDirection.getIndex());
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        Entity entity = context.getEntity();

        if(entity instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) entity;

            if(player.isShiftKeyDown() || player.isPotionActive(NetherExEffects.SOUL_SUCKED.get()))
            {
                return VoxelShapes.empty();
            }
        }

        return super.getCollisionShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader world, BlockPos pos)
    {
        return VoxelShapes.fullCube();
    }
}
