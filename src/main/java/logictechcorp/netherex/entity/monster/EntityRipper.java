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

package logictechcorp.netherex.entity.monster;

import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityRipper extends EntityMob
{
    private static final DataParameter<Integer> MOOD = EntityDataManager.createKey(EntityRipper.class, DataSerializers.VARINT);

    public EntityRipper(World world)
    {
        super(world);
        this.setSize(1.75F, 1.9F);
        this.isImmuneToFire = true;
        this.experienceValue = 5;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(0, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D, 60));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 1, true, true, null));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(MOOD, Mood.CALM.ordinal());
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(!this.world.isRemote)
        {
            if(this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
            {
                this.setDead();
            }

            if(this.getAttackTarget() == null)
            {
                this.setMood(Mood.CALM);
            }
        }
    }

    @Override
    protected void updateAITasks()
    {
        if(!this.hasHome())
        {
            this.setHomePosAndDistance(new BlockPos(this), 16);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        this.setMood(Mood.ENRAGED);
        return super.attackEntityAsMob(entity);
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }

    @Override
    public boolean isOnLadder()
    {
        return false;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.RIPPER;
    }

    public Mood getMood()
    {
        return Mood.getFromOrdinal(this.dataManager.get(MOOD));
    }

    @Override
    public void setInWeb()
    {
    }

    @Override
    public void setSprinting(boolean sprinting)
    {
    }

    @Override
    public void setAttackTarget(EntityLivingBase livingEntity)
    {
        if(livingEntity != null && !livingEntity.isSneaking())
        {
            if(this.getMood() != Mood.ENRAGED)
            {
                this.setMood(Mood.FOCUSED);
            }
            super.setAttackTarget(livingEntity);
        }
    }

    public void setMood(Mood mood)
    {
        this.dataManager.set(MOOD, mood.ordinal());
    }

    public enum Mood
    {
        CALM(NetherExTextures.RIPPER_CALM),
        FOCUSED(NetherExTextures.RIPPER_FOCUSED),
        ENRAGED(NetherExTextures.RIPPER_ENRAGED);

        ResourceLocation texture;

        Mood(ResourceLocation texture)
        {
            this.texture = texture;
        }

        public ResourceLocation getTexture()
        {
            return this.texture;
        }

        static Mood getFromOrdinal(int ordinal)
        {
            if(ordinal < 0 || ordinal >= values().length)
            {
                ordinal = 0;
            }

            return values()[ordinal];
        }
    }
}
