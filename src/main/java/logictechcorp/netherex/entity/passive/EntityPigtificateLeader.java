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

package logictechcorp.netherex.entity.passive;

import logictechcorp.netherex.entity.ai.EntityAIPigtificateInteract;
import logictechcorp.netherex.entity.ai.EntityAIPigtificateLookAtTradePlayer;
import logictechcorp.netherex.entity.ai.EntityAIPigtificateTradePlayer;
import logictechcorp.netherex.village.PigtificateProfessionLeader;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityPigtificateLeader extends EntityPigtificate
{
    public EntityPigtificateLeader(World world)
    {
        super(world);
    }

    public EntityPigtificateLeader(World world, PigtificateProfessionLeader profession)
    {
        super(world, profession);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPigtificateTradePlayer(this));
        this.tasks.addTask(1, new EntityAIPigtificateLookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(2, new EntityAIPigtificateInteract(this));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    public boolean isWillingToMate(boolean updateFirst)
    {
        return false;
    }
}
