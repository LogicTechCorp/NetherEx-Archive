package nex.entity.projectile;

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
import nex.init.NetherExBlocks;
import nex.init.NetherExEffects;

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
                            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(NetherExEffects.BLUE_FIRE, 100));
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
