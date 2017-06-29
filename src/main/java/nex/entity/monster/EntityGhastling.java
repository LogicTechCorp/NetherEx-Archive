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

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import nex.entity.ai.EntityAIGhastFly;
import nex.entity.ai.EntityAIGhastLookAround;
import nex.entity.ai.EntityAIGhastlingFireballAttack;
import nex.init.NetherExLootTables;
import nex.init.NetherExSoundEvents;

public class EntityGhastling extends EntityGhast
{
    public EntityGhastling(World world)
    {
        super(world);

        setSize(2.0F, 2.0F);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSoundEvents.ENTITY_AMBIENT_GHASTLING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSoundEvents.ENTITY_HURT_GHASTLING;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.ENTITY_DEATH_GHASTLING;
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAIGhastlingFireballAttack(this));
        tasks.addTask(1, new EntityAIGhastLookAround(this));
        tasks.addTask(2, new EntityAIGhastFly(this));
        targetTasks.addTask(0, new EntityAIFindEntityNearestPlayer(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
    }

    @Override
    public float getEyeHeight()
    {
        return 1.25F;
    }

    @Override
    public int getFireballStrength()
    {
        return 1;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.ENTITY_GHASTLING;
    }
}
