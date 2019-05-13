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

package logictechcorp.netherex.entity.projectile;

import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.init.NetherExMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityBlueFireball extends EntityFireball
{
    public EntityBlueFireball(World world)
    {
        super(world);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityBlueFireball(World world, EntityLivingBase shooter, double accelerationX, double accelerationY, double accelerationZ)
    {
        super(world, shooter, accelerationX, accelerationY, accelerationZ);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityBlueFireball(World world, double x, double y, double z, double accelerationX, double accelerationY, double accelerationZ)
    {
        super(world, x, y, z, accelerationX, accelerationY, accelerationZ);
        this.setSize(0.3125F, 0.3125F);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if(!this.world.isRemote)
        {
            Entity entity = result.entityHit;

            if(entity != null)
            {
                if(!entity.isImmuneToFire())
                {
                    boolean attacked = entity.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 5.0F);

                    if(attacked)
                    {
                        this.applyEnchantments(this.shootingEntity, entity);

                        if(entity instanceof EntityLivingBase)
                        {
                            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(NetherExMobEffects.FIRE_BURNING, 100));
                        }
                    }
                }
            }
            else
            {
                boolean mobGriefingEnabled = true;

                if(this.shootingEntity instanceof EntityLiving)
                {
                    mobGriefingEnabled = ForgeEventFactory.getMobGriefingEvent(this.world, this.shootingEntity);
                }

                if(mobGriefingEnabled)
                {
                    BlockPos pos = result.getBlockPos().offset(result.sideHit);

                    if(this.world.isAirBlock(pos))
                    {
                        this.world.setBlockState(pos, NetherExBlocks.BLUE_FIRE.getDefaultState());
                    }
                }
            }

            this.setDead();
        }
    }

    @Override
    protected boolean isFireballFiery()
    {
        return false;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }
}
