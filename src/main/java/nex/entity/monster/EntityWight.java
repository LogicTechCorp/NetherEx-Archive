/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import nex.init.NetherExEffects;
import nex.init.NetherExLootTables;
import nex.init.NetherExSoundEvents;

@SuppressWarnings("ConstantConditions")
public class EntityWight extends EntityMob
{
    public EntityWight(World world)
    {
        super(world);

        setSize(0.55F, 1.5F);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSoundEvents.ENTITY_AMBIENT_WIGHT;
    }

    @Override
    protected SoundEvent getHurtSound()
    {
        return NetherExSoundEvents.ENTITY_HURT_WIGHT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.ENTITY_DEATH_WIGHT;
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        tasks.addTask(2, new EntityAIWander(this, 1.0D, 5));
        tasks.addTask(3, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getAttackTarget() != null)
        {
            if(getAttackTarget().isPotionActive(NetherExEffects.FREEZE))
            {
                setAttackTarget(null);
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        if(entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isPotionActive(NetherExEffects.FREEZE))
        {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(NetherExEffects.FREEZE, 160, 0));
        }
        return super.attackEntityAsMob(entity);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        Block block = world.getBlockState((new BlockPos(this)).down()).getBlock();
        return world.getDifficulty() != EnumDifficulty.PEACEFUL && block != Blocks.MAGMA;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.ENTITY_WIGHT;
    }
}
