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

package nex.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import nex.NetherEx;
import nex.entity.monster.EntitySpore;
import nex.entity.monster.EntitySporeCreeper;
import nex.handler.ConfigHandler;
import nex.init.NetherExEffects;
import nex.init.NetherExParticleTypes;
import nex.init.NetherExSoundEvents;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ExplosionSpore extends Explosion
{
    private final World world;

    private final Entity exploder;

    private final double explosionX;
    private final double explosionY;
    private final double explosionZ;

    private final float explosionSize;

    private final boolean isFlaming;
    private final boolean isSmoking;

    private final Random explosionRNG;

    private final List<BlockPos> affectedBlockPositions;
    private final Map<EntityPlayer, Vec3d> playerKnockbackMap;
    private final Vec3d position;

    public ExplosionSpore(World worldIn, Entity entity, double x, double y, double z, float size, boolean flaming, boolean smoking)
    {
        super(worldIn, entity, x, y, z, size, flaming, smoking);

        world = worldIn;
        exploder = entity;
        explosionX = x;
        explosionY = y;
        explosionZ = z;
        explosionSize = size;
        isFlaming = flaming;
        isSmoking = smoking;

        explosionRNG = new Random(worldIn.getSeed());
        affectedBlockPositions = Lists.newArrayList();
        playerKnockbackMap = Maps.newHashMap();
        position = new Vec3d(explosionX, explosionY, explosionZ);
    }

    @Override
    public void doExplosionA()
    {
        if(!world.isRemote)
        {
            Set<BlockPos> set = Sets.newHashSet();

            for(int j = 0; j < 16; ++j)
            {
                for(int k = 0; k < 16; ++k)
                {
                    for(int l = 0; l < 16; ++l)
                    {
                        if(j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15)
                        {
                            double d0 = (double) ((float) j / 15.0F * 2.0F - 1.0F);
                            double d1 = (double) ((float) k / 15.0F * 2.0F - 1.0F);
                            double d2 = (double) ((float) l / 15.0F * 2.0F - 1.0F);
                            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                            d0 = d0 / d3;
                            d1 = d1 / d3;
                            d2 = d2 / d3;
                            float f = explosionSize * (0.7F + world.rand.nextFloat() * 0.6F);
                            double d4 = explosionX;
                            double d6 = explosionY;
                            double d8 = explosionZ;

                            for(float f1 = 0.3F; f > 0.0F; f -= 0.22500001F)
                            {
                                BlockPos blockpos = new BlockPos(d4, d6, d8);
                                IBlockState iblockstate = world.getBlockState(blockpos);

                                if(iblockstate.getMaterial() != Material.AIR)
                                {
                                    float f2 = exploder != null ? exploder.getExplosionResistance(this, world, blockpos, iblockstate) : iblockstate.getBlock().getExplosionResistance(world, blockpos, (Entity) null, this);
                                    f -= (f2 + 0.3F) * 0.3F;
                                }

                                if(f > 0.0F && (exploder == null || exploder.canExplosionDestroyBlock(this, world, blockpos, iblockstate, f)))
                                {
                                    set.add(blockpos);
                                }

                                d4 += d0 * 0.30000001192092896D;
                                d6 += d1 * 0.30000001192092896D;
                                d8 += d2 * 0.30000001192092896D;
                            }
                        }
                    }
                }
            }

            affectedBlockPositions.addAll(set);
            float f3 = explosionSize * 2.0F;
            int k1 = MathHelper.floor(explosionX - (double) f3 - 1.0D);
            int l1 = MathHelper.floor(explosionX + (double) f3 + 1.0D);
            int i2 = MathHelper.floor(explosionY - (double) f3 - 1.0D);
            int i1 = MathHelper.floor(explosionY + (double) f3 + 1.0D);
            int j2 = MathHelper.floor(explosionZ - (double) f3 - 1.0D);
            int j1 = MathHelper.floor(explosionZ + (double) f3 + 1.0D);
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(exploder, new AxisAlignedBB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
            ForgeEventFactory.onExplosionDetonate(world, this, list, f3);
            Vec3d vec3d = new Vec3d(explosionX, explosionY, explosionZ);

            for(Entity entity : list)
            {
                if(!entity.isImmuneToExplosions())
                {
                    double d12 = entity.getDistance(explosionX, explosionY, explosionZ) / (double) f3;

                    if(d12 <= 1.0D)
                    {
                        double d5 = entity.posX - explosionX;
                        double d7 = entity.posY + (double) entity.getEyeHeight() - explosionY;
                        double d9 = entity.posZ - explosionZ;
                        double d13 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

                        if(d13 != 0.0D)
                        {
                            d5 = d5 / d13;
                            d7 = d7 / d13;
                            d9 = d9 / d13;
                            double d14 = (double) world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
                            double d10 = (1.0D - d12) * d14;

                            if(!(entity instanceof EntitySporeCreeper) && !(entity instanceof EntitySpore))
                            {
                                entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f3 + 1.0D)));
                            }
                            if(entity instanceof EntityLivingBase)
                            {
                                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(NetherExEffects.SPORE, 2400, 0));
                            }

                            double d11 = d10;

                            if(entity instanceof EntityLivingBase)
                            {
                                d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase) entity, d10);
                            }

                            entity.motionX += d5 * d11;
                            entity.motionY += d7 * d11;
                            entity.motionZ += d9 * d11;

                            if(entity instanceof EntityPlayer)
                            {
                                EntityPlayer entityplayer = (EntityPlayer) entity;

                                if(!entityplayer.isSpectator() && (!entityplayer.isCreative() || !entityplayer.capabilities.isFlying))
                                {
                                    playerKnockbackMap.put(entityplayer, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void doExplosionB(boolean spawnParticles)
    {
        world.playSound(null, explosionX, explosionY, explosionZ, NetherExSoundEvents.ENTITY_EXPLODE_SPORE, SoundCategory.HOSTILE, 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);

        if(explosionSize >= 2.0F && isSmoking)
        {
            NetherEx.proxy.spawnParticle(world, explosionX, explosionY, explosionZ, 1.0D, 0.0D, 0.0D, NetherExParticleTypes.SPORE_EXPLOSION_HUGE);
        }
        else
        {
            NetherEx.proxy.spawnParticle(world, explosionX, explosionY, explosionZ, 1.0D, 0.0D, 0.0D, NetherExParticleTypes.SPORE_EXPLOSION_LARGE);
        }

        if(!world.isRemote)
        {
            if(isSmoking)
            {
                for(BlockPos pos : affectedBlockPositions)
                {
                    IBlockState iblockstate = world.getBlockState(pos);
                    Block block = iblockstate.getBlock();

                    if(iblockstate.getMaterial() != Material.AIR)
                    {
                        if(block.canDropFromExplosion(this))
                        {
                            block.dropBlockAsItemWithChance(world, pos, world.getBlockState(pos), 1.0F / explosionSize, 0);
                        }

                        block.onBlockExploded(world, pos, this);
                    }
                }
            }

            if(isFlaming)
            {
                for(BlockPos pos : affectedBlockPositions)
                {
                    if(world.getBlockState(pos).getMaterial() == Material.AIR && world.getBlockState(pos.down()).isFullBlock() && explosionRNG.nextInt(ConfigHandler.entity.sporeCreeper.chanceOfSporeSpawning) == 0)
                    {
                        EntitySpore spore = new EntitySpore(world, 0);
                        spore.setPosition((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D);
                        world.spawnEntity(spore);
                    }
                }
            }
        }
    }

    @Override
    public Map<EntityPlayer, Vec3d> getPlayerKnockbackMap()
    {
        return playerKnockbackMap;
    }

    @Override
    public EntityLivingBase getExplosivePlacedBy()
    {
        return exploder == null ? null : (exploder instanceof EntityLivingBase ? (EntityLivingBase) exploder : null);
    }

    @Override
    public void clearAffectedBlockPositions()
    {
        affectedBlockPositions.clear();
    }

    @Override
    public List<BlockPos> getAffectedBlockPositions()
    {
        return affectedBlockPositions;
    }

    @Override
    public Vec3d getPosition()
    {
        return position;
    }
}
