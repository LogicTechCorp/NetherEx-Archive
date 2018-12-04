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

import com.google.common.collect.Lists;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExSoundEvents;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMogus extends EntityMob
{
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityMogus.class, DataSerializers.VARINT);

    public EntityMogus(World world)
    {
        super(world);
        this.isImmuneToFire = true;
        this.setSize(0.35F, 0.45F);
        this.setRandomType();
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSoundEvents.MOGUS_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSoundEvents.MOGUS_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.MOGUS_DEATH;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.45D, true));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.45D));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(TYPE, 0);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public String getName()
    {
        if(this.hasCustomName())
        {
            return this.getCustomNameTag();
        }
        else
        {
            String entityName = EntityList.getEntityString(this);
            String type = this.getType() == 0 ? "brown" : this.getType() == 1 ? "red" : "white";
            return I18n.translateToLocal("entity." + entityName + "." + type + ".name");
        }
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return this.getType() == 0 ? NetherExLootTables.BROWN_MOGUS : this.getType() == 1 ? NetherExLootTables.RED_MOGUS : NetherExLootTables.WHITE_MOGUS;
    }

    public int getType()
    {
        return this.dataManager.get(TYPE);
    }

    private void setRandomType()
    {
        WeightedRandom.Item brown = new WeightedRandom.Item(10);
        WeightedRandom.Item red = new WeightedRandom.Item(10);
        WeightedRandom.Item white = new WeightedRandom.Item(2);
        WeightedRandom.Item item = WeightedRandom.getRandomItem(this.rand, Lists.newArrayList(brown, red, white));
        this.setType(item == brown ? 0 : item == red ? 1 : 2);
    }

    public void setType(int id)
    {
        if(id < 0)
        {
            id = 0;
        }
        else if(id > 2)
        {
            id = 2;
        }

        this.dataManager.set(TYPE, id);
    }
}
