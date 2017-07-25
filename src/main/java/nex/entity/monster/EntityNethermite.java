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
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.init.NetherExLootTables;
import nex.init.NetherExSoundEvents;

public class EntityNethermite extends EntityMob
{
    public EntityNethermite(World world)
    {
        super(world);

        setSize(0.4F, 0.3F);
        isImmuneToFire = true;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSoundEvents.ENTITY_AMBIENT_NETHERMITE;
    }

    @Override
    protected SoundEvent getHurtSound()
    {
        return NetherExSoundEvents.ENTITY_HURT_NETHERMITE;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.ENTITY_DEATH_NETHERMITE;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block block)
    {
        playSound(SoundEvents.ENTITY_SILVERFISH_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        tasks.addTask(2, new EntityAIWander(this, 1.0D));
        tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(4, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.5D);
    }

    @Override
    public void onUpdate()
    {
        renderYawOffset = rotationYaw;
        super.onUpdate();
    }

    @Override
    public void setRenderYawOffset(float offset)
    {
        rotationYaw = offset;
        super.setRenderYawOffset(offset);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.ENTITY_NETHERMITE;
    }
}
