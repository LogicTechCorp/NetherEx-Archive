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

package nex.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.entity.ai.EntityAISporeCreeperSwell;
import nex.init.NetherExItems;
import nex.init.NetherExLootTables;
import nex.init.NetherExSoundEvents;
import nex.world.ExplosionSpore;

import java.util.Collection;

public class EntitySporeCreeper extends EntityMob
{
    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(EntitySporeCreeper.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(EntitySporeCreeper.class, DataSerializers.BOOLEAN);

    private int lastIgnitionTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    private int explosionRadius = 2;

    public EntitySporeCreeper(World world)
    {
        super(world);
        isImmuneToFire = true;
        setSize(0.6F, 1.7F);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSoundEvents.SPORE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.SPORE_DEATH;
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAISporeCreeperSwell(this));
        tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        tasks.addTask(3, new EntityAIWanderAvoidWater(this, 0.8D));
        tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(5, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPigZombie.class, true));
        targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public int getMaxFallHeight()
    {
        return getAttackTarget() == null ? 3 : 3 + (int) (getHealth() - 1.0F);
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
        super.fall(distance, damageMultiplier);
        timeSinceIgnited = (int) ((float) timeSinceIgnited + distance * 1.5F);

        if(timeSinceIgnited > fuseTime - 5)
        {
            timeSinceIgnited = fuseTime - 5;
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(STATE, -1);
        dataManager.register(IGNITED, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setShort("Fuse", (short) fuseTime);
        compound.setByte("ExplosionRadius", (byte) explosionRadius);
        compound.setBoolean("ignited", hasIgnited());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if(compound.hasKey("Fuse", 99))
        {
            fuseTime = compound.getShort("Fuse");
        }

        if(compound.hasKey("ExplosionRadius", 99))
        {
            explosionRadius = compound.getByte("ExplosionRadius");
        }

        if(compound.getBoolean("ignited"))
        {
            ignite();
        }
    }

    @Override
    public void onUpdate()
    {
        if(isEntityAlive())
        {
            lastIgnitionTime = timeSinceIgnited;

            if(hasIgnited())
            {
                setCreeperState(1);
            }

            int i = getCreeperState();

            if(i > 0 && timeSinceIgnited == 0)
            {
                playSound(NetherExSoundEvents.SPORE_WARN, 1.0F, 0.5F);
            }

            timeSinceIgnited += i;

            if(timeSinceIgnited < 0)
            {
                timeSinceIgnited = 0;
            }

            if(timeSinceIgnited >= fuseTime)
            {
                timeSinceIgnited = fuseTime;
                explode();
            }
        }

        super.onUpdate();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase)
        {
            if(((EntityLivingBase) source.getTrueSource()).getHeldItemMainhand().getItem() == NetherExItems.GOLDEN_WITHER_BONE_SWORD)
            {
                amount *= 2.0F;
            }
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        return true;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.SPORE_CREEPER;
    }

    @SideOnly(Side.CLIENT)
    public float getFlashIntensity(float partialTicks)
    {
        return ((float) lastIgnitionTime + (float) (timeSinceIgnited - lastIgnitionTime) * partialTicks) / (float) (fuseTime - 2);
    }

    public int getCreeperState()
    {
        return dataManager.get(STATE);
    }

    public void setCreeperState(int state)
    {
        dataManager.set(STATE, state);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(stack.getItem() == Items.FLINT_AND_STEEL)
        {
            world.playSound(player, posX, posY, posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, getSoundCategory(), 1.0F, rand.nextFloat() * 0.4F + 0.8F);
            player.swingArm(hand);

            if(!world.isRemote)
            {
                ignite();
                stack.damageItem(1, player);
                return true;
            }
        }

        return super.processInteract(player, hand);
    }

    private void explode()
    {
        dead = true;
        Explosion explosion = new ExplosionSpore(world, this, posX, posY, posZ, (float) explosionRadius, true, world.getGameRules().getBoolean("mobGriefing"));
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        setDead();
        spawnLingeringCloud();
    }

    private void spawnLingeringCloud()
    {
        Collection<PotionEffect> effects = getActivePotionEffects();

        if(!effects.isEmpty())
        {
            EntityAreaEffectCloud cloud = new EntityAreaEffectCloud(world, posX, posY, posZ);
            cloud.setRadius(2.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setDuration(cloud.getDuration() / 2);
            cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());

            for(PotionEffect effect : effects)
            {
                cloud.addEffect(new PotionEffect(effect));
            }

            world.spawnEntity(cloud);
        }
    }

    private boolean hasIgnited()
    {
        return dataManager.get(IGNITED);
    }

    private void ignite()
    {
        dataManager.set(IGNITED, true);
    }
}
