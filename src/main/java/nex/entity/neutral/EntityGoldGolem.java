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

package nex.entity.neutral;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.entity.ai.EntityAIGoldGolemDefendVillage;
import nex.entity.ai.EntityAIGoldGolemLookAtPigtificate;
import nex.entity.monster.EntitySporeCreeper;
import nex.init.NetherExLootTables;
import nex.village.NetherVillage;
import nex.village.NetherVillageManager;

public class EntityGoldGolem extends EntityGolem
{
    protected static final DataParameter<Boolean> PLAYER_CREATED = EntityDataManager.createKey(EntityGoldGolem.class, DataSerializers.BOOLEAN);

    NetherVillage village;

    private int homeCheckTimer;
    private int attackTimer;
    private int holdFlowerTick;

    public EntityGoldGolem(World world)
    {
        super(world);
        setSize(1.4F, 3.0F);
        isImmuneToFire = true;
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
        tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
        tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        tasks.addTask(5, new EntityAIGoldGolemLookAtPigtificate(this));
        tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        tasks.addTask(8, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIGoldGolemDefendVillage(this));
        targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, false, true, entity -> entity != null && IMob.VISIBLE_MOB_SELECTOR.apply((EntityLiving) entity) && !(entity instanceof EntitySporeCreeper)));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(PLAYER_CREATED, false);
    }

    @Override
    protected void updateAITasks()
    {
        if(--homeCheckTimer <= 0)
        {
            homeCheckTimer = 70 + rand.nextInt(50);
            village = NetherVillageManager.getNetherVillages(getEntityWorld()).getNearestVillage(new BlockPos(this), 32);

            if(village == null)
            {
                detachHome();
            }
            else
            {
                BlockPos blockpos = village.getCenter();
                setHomePosAndDistance(blockpos, (int) ((float) village.getVillageRadius() * 0.6F));
            }
        }

        super.updateAITasks();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    protected int decreaseAirSupply(int air)
    {
        return air;
    }

    @Override
    protected void collideWithEntity(Entity entityIn)
    {
        if(entityIn instanceof IMob && !(entityIn instanceof EntitySporeCreeper) && getRNG().nextInt(20) == 0)
        {
            setAttackTarget((EntityLivingBase) entityIn);
        }

        super.collideWithEntity(entityIn);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if(attackTimer > 0)
        {
            --attackTimer;
        }

        if(holdFlowerTick > 0)
        {
            --holdFlowerTick;
        }

        if(motionX * motionX + motionZ * motionZ > 2.500000277905201E-7D && rand.nextInt(5) == 0)
        {
            int i = MathHelper.floor(posX);
            int j = MathHelper.floor(posY - 0.20000000298023224D);
            int k = MathHelper.floor(posZ);
            IBlockState iblockstate = world.getBlockState(new BlockPos(i, j, k));

            if(iblockstate.getMaterial() != Material.AIR)
            {
                world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX + ((double) rand.nextFloat() - 0.5D) * (double) width, getEntityBoundingBox().minY + 0.1D, posZ + ((double) rand.nextFloat() - 0.5D) * (double) width, 4.0D * ((double) rand.nextFloat() - 0.5D), 0.5D, ((double) rand.nextFloat() - 0.5D) * 4.0D, new int[]{Block.getStateId(iblockstate)});
            }
        }
    }

    @Override
    public boolean canAttackClass(Class<? extends EntityLivingBase> cls)
    {
        return isPlayerCreated() && EntityPlayer.class.isAssignableFrom(cls) ? false : (cls == EntitySporeCreeper.class ? false : super.canAttackClass(cls));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("PlayerCreated", isPlayerCreated());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setPlayerCreated(compound.getBoolean("PlayerCreated"));
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        attackTimer = 10;
        world.setEntityState(this, (byte) 4);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) (7 + rand.nextInt(15)));

        if(flag)
        {
            entityIn.motionY += 0.4000000059604645D;
            applyEnchantments(this, entityIn);
        }

        playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if(id == 4)
        {
            attackTimer = 10;
            playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
        }
        else if(id == 11)
        {
            holdFlowerTick = 400;
        }
        else if(id == 34)
        {
            holdFlowerTick = 0;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    public NetherVillage getVillage()
    {
        return village;
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer()
    {
        return attackTimer;
    }

    public void setHoldingFlower(boolean holding)
    {
        if(holding)
        {
            holdFlowerTick = 400;
            world.setEntityState(this, (byte) 11);
        }
        else
        {
            holdFlowerTick = 0;
            world.setEntityState(this, (byte) 34);
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return SoundEvents.ENTITY_IRONGOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_IRONGOLEM_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.GOLD_GOLEM;
    }

    public int getHoldFlowerTick()
    {
        return holdFlowerTick;
    }

    public boolean isPlayerCreated()
    {
        return dataManager.get(PLAYER_CREATED);
    }

    public void setPlayerCreated(boolean playerCreated)
    {
        dataManager.set(PLAYER_CREATED, playerCreated);
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        if(!isPlayerCreated() && attackingPlayer != null && village != null)
        {
            village.modifyPlayerReputation(attackingPlayer.getUniqueID(), -5);
        }

        super.onDeath(cause);
    }
}
