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
import net.minecraft.entity.*;
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

public class SalamanderEntity extends MonsterEntity
{
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(SalamanderEntity.class, DataSerializers.VARINT);

    public SalamanderEntity(EntityType<? extends MonsterEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<SalamanderEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random)
    {
        return world.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.3F));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
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
    public boolean attackEntityAsMob(Entity entity)
    {
        if(entity instanceof LivingEntity)
        {
            if(this.getVariant() == Variant.ORANGE)
            {
                entity.setFire(4);
            }
            else
            {
                entity.setFire(8);
            }
        }
        return super.attackEntityAsMob(entity);
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
            String type = this.getVariant() == Variant.ORANGE ? "orange" : "black";
            return new TranslationTextComponent("entity." + NetherEx.MOD_ID + ":" + type + "_salamander.name");
        }
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSoundEvents.ENTITY_SALAMANDER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSoundEvents.ENTITY_SALAMANDER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.ENTITY_SALAMANDER_DEATH.get();
    }

    public Variant getVariant()
    {
        return Variant.getFromOrdinal(this.dataManager.get(TYPE));
    }

    private void setRandomVariant()
    {
        this.setVariant(this.rand.nextInt(10) != 0 ? Variant.ORANGE : Variant.BLACK);
    }

    public void setVariant(Variant variant)
    {
        this.dataManager.set(TYPE, variant.ordinal());
    }

    public enum Variant
    {
        ORANGE(new ResourceLocation(NetherEx.MOD_ID, "textures/entity/salamander/orange_salamander.png"), new ResourceLocation(NetherEx.MOD_ID, "entities/salamander/orange_salamander")),
        BLACK(new ResourceLocation(NetherEx.MOD_ID, "textures/entity/salamander/black_salamander.png"), new ResourceLocation(NetherEx.MOD_ID, "entities/salamander/black_salamander"));

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