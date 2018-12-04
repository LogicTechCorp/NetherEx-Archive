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

package logictechcorp.netherex.entity.neutral;

import logictechcorp.netherex.entity.ai.EntityAIGoldGolemDefendVillage;
import logictechcorp.netherex.entity.ai.EntityAIGoldGolemLookAtPigtificate;
import logictechcorp.netherex.entity.monster.EntitySporeCreeper;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.village.PigtificateVillage;
import logictechcorp.netherex.village.PigtificateVillageManager;
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

public class EntityGoldGolem extends EntityGolem
{
    protected static final DataParameter<Boolean> PLAYER_CREATED = EntityDataManager.createKey(EntityGoldGolem.class, DataSerializers.BOOLEAN);

    PigtificateVillage village;

    private int homeCheckTimer;
    private int attackTimer;
    private int flowerHeldCounter;

    public EntityGoldGolem(World world)
    {
        super(world);
        this.isImmuneToFire = true;
        this.setSize(1.4F, 3.0F);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIGoldGolemLookAtPigtificate(this));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIGoldGolemDefendVillage(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, false, true, entity -> entity != null && IMob.VISIBLE_MOB_SELECTOR.apply((EntityLiving) entity) && !(entity instanceof EntitySporeCreeper)));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(PLAYER_CREATED, false);
    }

    @Override
    protected void updateAITasks()
    {
        if(this.homeCheckTimer-- <= 0)
        {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.village = PigtificateVillageManager.getVillageData(this.getEntityWorld(), true).getNearestVillage(new BlockPos(this), 32);

            if(this.village == null)
            {
                this.detachHome();
            }
            else
            {
                BlockPos blockpos = this.village.getCenter();
                this.setHomePosAndDistance(blockpos, (int) ((float) this.village.getRadius() * 0.6F));
            }
        }

        super.updateAITasks();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    protected int decreaseAirSupply(int air)
    {
        return air;
    }

    @Override
    protected void collideWithEntity(Entity entityIn)
    {
        if(entityIn instanceof IMob && !(entityIn instanceof EntitySporeCreeper) && this.getRNG().nextInt(20) == 0)
        {
            this.setAttackTarget((EntityLivingBase) entityIn);
        }

        super.collideWithEntity(entityIn);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if(this.attackTimer > 0)
        {
            this.attackTimer--;
        }

        if(this.flowerHeldCounter > 0)
        {
            this.flowerHeldCounter--;
        }

        if(this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D && this.rand.nextInt(5) == 0)
        {
            int posX = MathHelper.floor(this.posX);
            int posY = MathHelper.floor(this.posY - 0.20000000298023224D);
            int posZ = MathHelper.floor(this.posZ);
            IBlockState state = this.world.getBlockState(new BlockPos(posX, posY, posZ));

            if(state.getMaterial() != Material.AIR)
            {
                this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width, this.getEntityBoundingBox().minY + 0.1D, this.posZ + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width, 4.0D * ((double) this.rand.nextFloat() - 0.5D), 0.5D, ((double) this.rand.nextFloat() - 0.5D) * 4.0D, Block.getStateId(state));
            }
        }
    }

    @Override
    public boolean canAttackClass(Class<? extends EntityLivingBase> cls)
    {
        return (!this.isPlayerCreated() || !EntityPlayer.class.isAssignableFrom(cls)) && (cls != EntitySporeCreeper.class && super.canAttackClass(cls));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("PlayerCreated", this.isPlayerCreated());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setPlayerCreated(compound.getBoolean("PlayerCreated"));
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte) 4);
        boolean attack = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) (7 + this.rand.nextInt(15)));

        if(attack)
        {
            entity.motionY += 0.4000000059604645D;
            this.applyEnchantments(this, entity);
        }

        this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
        return attack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if(id == 4)
        {
            this.attackTimer = 10;
            this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
        }
        else if(id == 11)
        {
            this.flowerHeldCounter = 400;
        }
        else if(id == 34)
        {
            this.flowerHeldCounter = 0;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    public PigtificateVillage getVillage()
    {
        return this.village;
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer()
    {
        return this.attackTimer;
    }

    public void setHoldingFlower(boolean holding)
    {
        if(holding)
        {
            this.flowerHeldCounter = 400;
            this.world.setEntityState(this, (byte) 11);
        }
        else
        {
            this.flowerHeldCounter = 0;
            this.world.setEntityState(this, (byte) 34);
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
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.GOLD_GOLEM;
    }

    public int getFlowerHeldCounter()
    {
        return this.flowerHeldCounter;
    }

    public boolean isPlayerCreated()
    {
        return this.dataManager.get(PLAYER_CREATED);
    }

    public void setPlayerCreated(boolean playerCreated)
    {
        this.dataManager.set(PLAYER_CREATED, playerCreated);
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        if(!this.isPlayerCreated() && this.attackingPlayer != null && this.village != null)
        {
            this.village.modifyPlayerReputation(this.attackingPlayer.getUniqueID(), -5);
        }

        super.onDeath(cause);
    }
}
