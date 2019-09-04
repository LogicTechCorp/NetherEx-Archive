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

package logictechcorp.netherex.entity.hostile;

import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.utility.NetherExSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class SpinoutEntity extends MonsterEntity
{
    private int spinCounter;
    private int spinCooldown;

    private boolean spinning;

    private final Goal attackMelee = new MeleeAttackGoal(this, 1.0D, true);
    private final Goal wander = new RandomWalkingGoal(this, 1.0D, 1);

    public SpinoutEntity(EntityType<? extends MonsterEntity> entityType, World world)
    {
        super(entityType, world);
        this.setPathPriority(PathNodeType.LAVA, 8.0F);
    }

    public static boolean canSpawn(EntityType<SpinoutEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random)
    {
        return world.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    public void tick()
    {
        super.tick();

        if(this.spinCooldown == 0)
        {
            this.spinCounter++;

            if(!this.spinning)
            {
                this.spinning = true;
            }

            this.goalSelector.addGoal(1, this.attackMelee);
            this.goalSelector.addGoal(2, this.wander);

            if(this.isSilent())
            {
                this.setSilent(false);
            }
        }
        if(this.spinCounter >= NetherExConfig.ENTITY.spinoutSpinTime.get() * 20)
        {
            this.spinCounter = 0;
            this.spinning = false;
            this.spinCooldown = NetherExConfig.ENTITY.spinoutSpinCooldown.get() * 20;
        }
        if(this.spinCooldown > 0)
        {
            this.spinCooldown--;

            this.goalSelector.removeGoal(this.attackMelee);
            this.goalSelector.removeGoal(this.wander);

            if(!this.isSilent())
            {
                this.setSilent(true);
            }

            this.getNavigator().clearPath();
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        compound.putInt("SpinCounter", this.spinCounter);
        compound.putInt("SpinCooldown", this.spinCooldown);
        compound.putBoolean("Spinning", this.spinning);
    }

    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        this.spinCounter = compound.getInt("SpinCounter");
        this.spinCooldown = compound.getInt("SpinCooldown");
        this.spinning = compound.getBoolean("Spinning");
    }

    @Override
    public void playSound(SoundEvent sound, float volume, float pitch)
    {
        if(!this.isSilent() || sound != NetherExSounds.ENTITY_SPINOUT_AMBIENT)
        {
            this.world.playSound(null, this.posX, this.posY, this.posZ, sound, this.getSoundCategory(), volume, pitch);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return !(source.isProjectile() && this.isSpinning()) && super.attackEntityFrom(source, amount);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public boolean isSpinning()
    {
        return this.spinning;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSounds.ENTITY_SPINOUT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSounds.ENTITY_SPINOUT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSounds.ENTITY_SPINOUT_DEATH;
    }
}
