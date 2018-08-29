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
        this.exploder = entity;
        this.explosionX = x;
        this.explosionY = y;
        this.explosionZ = z;
        this.explosionSize = size;
        this.isFlaming = flaming;
        this.isSmoking = smoking;

        this.explosionRNG = new Random(world.getSeed());
        this.affectedBlockPositions = new ArrayList<>();
        this.playerKnockbackMap = new HashMap<>();
        this.position = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);
    }

    @Override
    public void doExplosionA()
    {
        if(!this.world.isRemote)
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
                            float explosionSize = this.explosionSize * (0.7F + this.world.rand.nextFloat() * 0.6F);
                            double explosionX = this.explosionX;
                            double explosionY = this.explosionY;
                            double explosionZ = this.explosionZ;

                            for(float size = 0.3F; explosionSize > 0.0F; explosionSize -= 0.22500001F)
                            {
                                BlockPos explosionPos = new BlockPos(explosionX, explosionY, explosionZ);
                                IBlockState explodedBlock = this.world.getBlockState(explosionPos);

                                if(explodedBlock.getMaterial() != Material.AIR)
                                {
                                    float f2 = this.exploder != null ? this.exploder.getExplosionResistance(this, this.world, explosionPos, explodedBlock) : explodedBlock.getBlock().getExplosionResistance(this.world, explosionPos, null, this);
                                    explosionSize -= (f2 + 0.3F) * 0.3F;
                                }

                                if(explosionSize > 0.0F && (this.exploder == null || this.exploder.canExplosionDestroyBlock(this, this.world, explosionPos, explodedBlock, explosionSize)))
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

            this.affectedBlockPositions.addAll(set);
            float f3 = this.explosionSize * 2.0F;
            int minX = MathHelper.floor(this.explosionX - (double) f3 - 1.0D);
            int maxX = MathHelper.floor(this.explosionX + (double) f3 + 1.0D);
            int minY = MathHelper.floor(this.explosionY - (double) f3 - 1.0D);
            int maxY = MathHelper.floor(this.explosionY + (double) f3 + 1.0D);
            int minZ = MathHelper.floor(this.explosionZ - (double) f3 - 1.0D);
            int maxZ = MathHelper.floor(this.explosionZ + (double) f3 + 1.0D);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB((double) minX, (double) minY, (double) minZ, (double) maxX, (double) maxY, (double) maxZ));
            ForgeEventFactory.onExplosionDetonate(this.world, this, list, f3);
            Vec3d vec3d = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);

            for(Entity entity : list)
            {
                if(!entity.isImmuneToExplosions())
                {
                    double d12 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double) f3;

                    if(d12 <= 1.0D)
                    {
                        double d5 = entity.posX - this.explosionX;
                        double d7 = entity.posY + (double) entity.getEyeHeight() - this.explosionY;
                        double d9 = entity.posZ - this.explosionZ;
                        double d13 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

                        if(d13 != 0.0D)
                        {
                            d5 = d5 / d13;
                            d7 = d7 / d13;
                            d9 = d9 / d13;
                            double d14 = (double) this.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
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
                                    this.playerKnockbackMap.put(entityplayer, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
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
        this.world.playSound(null, this.explosionX, this.explosionY, this.explosionZ, NetherExSoundEvents.SPORE_EXPLODE, SoundCategory.HOSTILE, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

        if(this.explosionSize >= 2.0F && this.isSmoking)
        {
            NetherEx.proxy.spawnParticle(this.world, NetherExParticleTypes.SPORE_EXPLOSION_HUGE.ordinal(), this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }
        else
        {
            NetherEx.proxy.spawnParticle(this.world, NetherExParticleTypes.SPORE_EXPLOSION_LARGE.ordinal(), this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }

        if(!this.world.isRemote)
        {
            if(this.isSmoking)
            {
                for(BlockPos pos : this.affectedBlockPositions)
                {
                    IBlockState iblockstate = this.world.getBlockState(pos);
                    Block block = iblockstate.getBlock();

                    if(iblockstate.getMaterial() != Material.AIR)
                    {
                        if(block.canDropFromExplosion(this))
                        {
                            block.dropBlockAsItemWithChance(this.world, pos, this.world.getBlockState(pos), 1.0F / this.explosionSize, 0);
                        }

                        block.onBlockExploded(this.world, pos, this);
                    }
                }
            }

            if(this.isFlaming)
            {
                for(BlockPos pos : this.affectedBlockPositions)
                {
                    if(this.world.getBlockState(pos).getMaterial() == Material.AIR && this.world.getBlockState(pos.down()).isFullBlock() && this.explosionRNG.nextInt(ConfigHandler.entityConfig.sporeCreeper.sporeSpawnRarity) == 0)
                    {
                        EntitySpore spore = new EntitySpore(this.world, 0);
                        spore.setPosition((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D);
                        this.world.spawnEntity(spore);
                    }
                }
            }
        }
    }

    @Override
    public Map<EntityPlayer, Vec3d> getPlayerKnockbackMap()
    {
        return this.playerKnockbackMap;
    }

    @Override
    public EntityLivingBase getExplosivePlacedBy()
    {
        return this.exploder == null ? null : (this.exploder instanceof EntityLivingBase ? (EntityLivingBase) this.exploder : null);
    }

    @Override
    public void clearAffectedBlockPositions()
    {
        this.affectedBlockPositions.clear();
    }

    @Override
    public List<BlockPos> getAffectedBlockPositions()
    {
        return this.affectedBlockPositions;
    }

    @Override
    public Vec3d getPosition()
    {
        return this.position;
    }
}
