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
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExMobEffects;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockSoulGlass extends BlockMod
{
    public BlockSoulGlass()
    {
        super(NetherEx.getResource("soul_glass"), new BlockProperties(Material.GLASS, MapColor.AIR).sound(SoundType.GLASS).hardness(0.3F));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        return world.getBlockState(pos.offset(facing)).getBlock() != this && super.shouldSideBeRendered(state, world, pos, facing);
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
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean isActualState)
    {
        if(!(entity instanceof EntityPlayer))
        {
            super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entity, isActualState);
        }
        else
        {
            EntityPlayer player = (EntityPlayer) entity;

            if(!player.isSneaking() && !player.isPotionActive(NetherExMobEffects.SOUL_SUCKED))
            {
                super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entity, isActualState);
            }
        }
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            boolean sinking = player.getLookVec().y < -0.75D;

            NBTTagCompound playerData = player.getEntityData();
            EnumFacing shootDirection = null;

            if(!player.isPotionActive(NetherExMobEffects.SOUL_SUCKED))
            {
                playerData.removeTag("netherex:soul_shoot_direction");
            }
            if(playerData.hasKey("netherex:soul_shoot_direction"))
            {
                shootDirection = EnumFacing.byIndex(playerData.getInteger("netherex:soul_shoot_direction"));
            }

            if(player.isSneaking())
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
                        player.addPotionEffect(new PotionEffect(NetherExMobEffects.SOUL_SUCKED, 5, 0, false, false));
                    }
                    if(blockOneBack == this && blockTwoBack != this)
                    {
                        player.addPotionEffect(new PotionEffect(NetherExMobEffects.SOUL_SUCKED, 5, 1, false, false));
                    }
                    else if(blockOneBack == this && blockThreeBack != this)
                    {
                        player.addPotionEffect(new PotionEffect(NetherExMobEffects.SOUL_SUCKED, 5, 2, false, false));
                    }
                    else if(blockOneBack == this)
                    {
                        player.addPotionEffect(new PotionEffect(NetherExMobEffects.SOUL_SUCKED, 5, 3, false, false));
                    }
                }
                else
                {
                    if(shootDirection == null)
                    {
                        shootDirection = EnumFacing.UP;
                    }

                    Block blockUpOne = world.getBlockState(pos.up()).getBlock();
                    Block blockUpTwo = world.getBlockState(pos.up(2)).getBlock();
                    Block blockUpThree = world.getBlockState(pos.up(3)).getBlock();

                    if(blockUpOne != this)
                    {
                        player.addPotionEffect(new PotionEffect(NetherExMobEffects.SOUL_SUCKED, 5, 0, false, false));
                    }
                    if(blockUpOne == this && blockUpTwo != this)
                    {
                        player.addPotionEffect(new PotionEffect(NetherExMobEffects.SOUL_SUCKED, 5, 1, false, false));
                    }
                    else if(blockUpOne == this && blockUpThree != this)
                    {
                        player.addPotionEffect(new PotionEffect(NetherExMobEffects.SOUL_SUCKED, 5, 2, false, false));
                    }
                    else if(blockUpOne == this)
                    {
                        player.addPotionEffect(new PotionEffect(NetherExMobEffects.SOUL_SUCKED, 5, 3, false, false));
                        player.posY = player.prevPosY;
                    }

                    player.motionY /= 5.0D;
                }
            }
            else
            {
                PotionEffect effect = player.getActivePotionEffect(NetherExMobEffects.SOUL_SUCKED);
                int amplifier = 1;

                if(effect != null)
                {
                    amplifier = (effect.getAmplifier() + 1);
                }

                if(!sinking && shootDirection != null && shootDirection != EnumFacing.UP)
                {
                    EnumFacing playerDirection = shootDirection.getOpposite();

                    if(playerDirection == EnumFacing.NORTH)
                    {
                        if(player.motionZ < 0)
                        {
                            player.motionZ = -player.motionZ;
                        }
                        else if(player.motionZ == 0)
                        {
                            player.motionZ = 0.5D;
                        }
                    }
                    else if(playerDirection == EnumFacing.EAST)
                    {
                        if(player.motionX > 0)
                        {
                            player.motionX = -player.motionX;
                        }
                        else if(player.motionX == 0)
                        {
                            player.motionX = -0.5D;
                        }
                    }
                    else if(playerDirection == EnumFacing.SOUTH)
                    {
                        if(player.motionZ > 0)
                        {
                            player.motionZ = -player.motionZ;
                        }
                        else if(player.motionZ == 0)
                        {
                            player.motionZ = -0.5D;
                        }
                    }
                    else if(playerDirection == EnumFacing.WEST)
                    {
                        if(player.motionX < 0)
                        {
                            player.motionX = -player.motionX;
                        }
                        else if(player.motionX == 0)
                        {
                            player.motionX = 0.5D;
                        }
                    }

                    if(player.motionX > -6.0D && player.motionX < 6.0D)
                    {
                        player.motionX *= (1.15D * amplifier);
                    }

                    if(player.motionZ > -6.0D && player.motionZ < 6.0D)
                    {
                        player.motionZ *= (1.15D * amplifier);
                    }
                }
                else
                {
                    if(player.motionY <= 0)
                    {
                        player.motionY = 0.105D;
                    }

                    player.motionY += amplifier * 0.1D;
                }
            }

            if(shootDirection != null)
            {
                playerData.setInteger("netherex:soul_shoot_direction", shootDirection.getIndex());
            }
        }
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return true;
    }
}
