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

package logictechcorp.netherex.entity.neutral;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExSoundEvents;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.entity.IEntityLivingData;
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
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
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
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        this.setRandomType();
        return super.onInitialSpawn(difficulty, data);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
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
            String type = this.getType() == Type.BROWN ? "brown" : this.getType() == Type.RED ? "red" : "white";
            return I18n.translateToLocal("entity." + NetherEx.MOD_ID + ":" + type + "_mogus.name");
        }
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return this.getType().getLootTable();
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

    public Type getType()
    {
        return Type.getFromOrdinal(this.dataManager.get(TYPE));
    }

    private void setRandomType()
    {
        this.setType(this.rand.nextInt(10) > 1 ? this.rand.nextInt(10) < 5 ? Type.BROWN : Type.RED : Type.WHITE);
    }

    public void setType(Type type)
    {
        this.dataManager.set(TYPE, type.ordinal());
    }

    public enum Type
    {
        BROWN(NetherExTextures.BROWN_MOGUS, NetherExLootTables.BROWN_MOGUS),
        RED(NetherExTextures.RED_MOGUS, NetherExLootTables.RED_MOGUS),
        WHITE(NetherExTextures.WHITE_MOGUS, NetherExLootTables.WHITE_MOGUS);

        ResourceLocation texture;
        ResourceLocation lootTable;

        Type(ResourceLocation texture, ResourceLocation lootTable)
        {
            this.texture = texture;
            this.lootTable = lootTable;
        }

        public ResourceLocation getTexture()
        {
            return this.texture;
        }

        public ResourceLocation getLootTable()
        {
            return this.lootTable;
        }

        static Type getFromOrdinal(int ordinal)
        {
            if(ordinal < 0 || ordinal >= values().length)
            {
                ordinal = 0;
            }

            return values()[ordinal];
        }
    }

}
