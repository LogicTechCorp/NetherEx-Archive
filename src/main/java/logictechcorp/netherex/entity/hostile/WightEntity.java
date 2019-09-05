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

import logictechcorp.netherex.potion.NetherExEffects;
import logictechcorp.netherex.utility.NetherExSounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class WightEntity extends MonsterEntity
{
    public WightEntity(EntityType<? extends MonsterEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<WightEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        return world.getDifficulty() != Difficulty.PEACEFUL && block != Blocks.MAGMA_BLOCK;

    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSounds.ENTITY_WIGHT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSounds.ENTITY_WIGHT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSounds.ENTITY_WIGHT_DEATH;
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1.0D, 5));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    public void tick()
    {
        super.tick();

        if(this.getAttackTarget() != null)
        {
            if(this.getAttackTarget().isPotionActive(NetherExEffects.FROZEN))
            {
                this.setAttackTarget(null);
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        if(entity instanceof LivingEntity && !((LivingEntity) entity).isPotionActive(NetherExEffects.FROZEN))
        {
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(NetherExEffects.FROZEN, 160, 0));
        }
        return super.attackEntityAsMob(entity);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }
}