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
import logictechcorp.netherex.utility.NetherExSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class MogusEntity extends MonsterEntity
{
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(MogusEntity.class, DataSerializers.VARINT);

    public MogusEntity(EntityType<? extends MonsterEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<MogusEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random)
    {
        return world.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.45D, true));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.45D));
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setCallsForHelp());
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    protected void registerData()
    {
        super.registerData();
        this.dataManager.register(TYPE, 0);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT compound)
    {
        this.setRandomVariant();
        return super.onInitialSpawn(world, difficulty, reason, spawnData, compound);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public ITextComponent getName()
    {
        if(this.hasCustomName())
        {
            return this.getCustomName();
        }
        else
        {
            String type = this.getVariant() == Variant.BROWN ? "brown" : this.getVariant() == Variant.RED ? "red" : "white";
            return new TranslationTextComponent("entity." + NetherEx.MOD_ID + "." + type + "_mogus.name");
        }
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return this.getVariant().getLootTable();
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSoundEvents.ENTITY_MOGUS_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSoundEvents.ENTITY_MOGUS_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.ENTITY_MOGUS_DEATH.get();
    }

    public Variant getVariant()
    {
        return Variant.getFromOrdinal(this.dataManager.get(TYPE));
    }

    private void setRandomVariant()
    {
        this.setVariant(this.rand.nextInt(10) > 1 ? this.rand.nextInt(10) < 5 ? Variant.BROWN : Variant.RED : Variant.WHITE);
    }

    public void setVariant(Variant variant)
    {
        this.dataManager.set(TYPE, variant.ordinal());
    }

    public enum Variant
    {
        BROWN(new ResourceLocation(NetherEx.MOD_ID, "textures/entity/mogus/brown_mogus.png"), new ResourceLocation(NetherEx.MOD_ID, "entities/mogus/brown_mogus.json")),
        RED(new ResourceLocation(NetherEx.MOD_ID, "textures/entity/mogus/red_mogus.png"), new ResourceLocation(NetherEx.MOD_ID, "entities/mogus/red_mogus.json")),
        WHITE(new ResourceLocation(NetherEx.MOD_ID, "textures/entity/mogus/white_mogus.png"), new ResourceLocation(NetherEx.MOD_ID, "entities/mogus/white_mogus.json"));

        ResourceLocation texture;
        ResourceLocation lootTable;

        Variant(ResourceLocation texture, ResourceLocation lootTable)
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

        static Variant getFromOrdinal(int ordinal)
        {
            if(ordinal < 0 || ordinal >= values().length)
            {
                ordinal = 0;
            }

            return values()[ordinal];
        }
    }

}
