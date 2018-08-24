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

package nex.world;

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

import java.util.*;

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

    public ExplosionSpore(World world, Entity entity, double x, double y, double z, float size, boolean flaming, boolean smoking)
    {
        super(world, entity, x, y, z, size, flaming, smoking);

        this.world = world;
        exploder = entity;
        explosionX = x;
        explosionY = y;
        explosionZ = z;
        explosionSize = size;
        isFlaming = flaming;
        isSmoking = smoking;

        explosionRNG = new Random(world.getSeed());
        affectedBlockPositions = new ArrayList<>();
        playerKnockbackMap = new HashMap<>();
        position = new Vec3d(explosionX, explosionY, explosionZ);
    }

    @Override
    public void doExplosionA()
    {
        if(!world.isRemote)
        {
            Set<BlockPos> set = new HashSet<>();

            for(int x = 0; x < 16; x++)
            {
                for(int y = 0; y < 16; y++)
                {
                    for(int z = 0; z < 16; z++)
                    {
                        if(x == 0 || x == 15 || y == 0 || y == 15 || z == 0 || z == 15)
                        {
                            double posX = (double) ((float) x / 15.0F * 2.0F - 1.0F);
                            double posY = (double) ((float) y / 15.0F * 2.0F - 1.0F);
                            double posZ = (double) ((float) z / 15.0F * 2.0F - 1.0F);
                            double d3 = Math.sqrt(posX * posX + posY * posY + posZ * posZ);
                            posX = posX / d3;
                            posY = posY / d3;
                            posZ = posZ / d3;
                            float explosionSize = this.explosionSize * (0.7F + world.rand.nextFloat() * 0.6F);
                            double explosionX = this.explosionX;
                            double explosionY = this.explosionY;
                            double explosionZ = this.explosionZ;

                            for(float size = 0.3F; explosionSize > 0.0F; explosionSize -= 0.22500001F)
                            {
                                BlockPos explosionPos = new BlockPos(explosionX, explosionY, explosionZ);
                                IBlockState explodedBlock = world.getBlockState(explosionPos);

                                if(explodedBlock.getMaterial() != Material.AIR)
                                {
                                    float f2 = exploder != null ? exploder.getExplosionResistance(this, world, explosionPos, explodedBlock) : explodedBlock.getBlock().getExplosionResistance(world, explosionPos, (Entity) null, this);
                                    explosionSize -= (f2 + 0.3F) * 0.3F;
                                }

                                if(explosionSize > 0.0F && (exploder == null || exploder.canExplosionDestroyBlock(this, world, explosionPos, explodedBlock, explosionSize)))
                                {
                                    set.add(explosionPos);
                                }

                                explosionX += posX * 0.30000001192092896D;
                                explosionY += posY * 0.30000001192092896D;
                                explosionZ += posZ * 0.30000001192092896D;
                            }
                        }
                    }
                }
            }

            affectedBlockPositions.addAll(set);
            float f3 = explosionSize * 2.0F;
            int minX = MathHelper.floor(explosionX - (double) f3 - 1.0D);
            int maxX = MathHelper.floor(explosionX + (double) f3 + 1.0D);
            int minY = MathHelper.floor(explosionY - (double) f3 - 1.0D);
            int maxY = MathHelper.floor(explosionY + (double) f3 + 1.0D);
            int minZ = MathHelper.floor(explosionZ - (double) f3 - 1.0D);
            int maxZ = MathHelper.floor(explosionZ + (double) f3 + 1.0D);
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(exploder, new AxisAlignedBB((double) minX, (double) minY, (double) minZ, (double) maxX, (double) maxY, (double) maxZ));
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
        world.playSound(null, explosionX, explosionY, explosionZ, NetherExSoundEvents.SPORE_EXPLODE, SoundCategory.HOSTILE, 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);

        if(explosionSize >= 2.0F && isSmoking)
        {
            NetherEx.proxy.spawnParticle(world, NetherExParticleTypes.SPORE_EXPLOSION_HUGE.ordinal(), explosionX, explosionY, explosionZ, 1.0D, 0.0D, 0.0D);
        }
        else
        {
            NetherEx.proxy.spawnParticle(world, NetherExParticleTypes.SPORE_EXPLOSION_LARGE.ordinal(), explosionX, explosionY, explosionZ, 1.0D, 0.0D, 0.0D);
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
                    if(world.getBlockState(pos).getMaterial() == Material.AIR && world.getBlockState(pos.down()).isFullBlock() && explosionRNG.nextInt(ConfigHandler.entityConfig.sporeCreeper.sporeSpawnRarity) == 0)
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
