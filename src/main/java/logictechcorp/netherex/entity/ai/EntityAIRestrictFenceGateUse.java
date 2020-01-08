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

package logictechcorp.netherex.entity.ai;

import logictechcorp.libraryex.utility.WorldHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.village.PigtificateVillage;
import logictechcorp.netherex.village.PigtificateVillageFenceGateInfo;
import logictechcorp.netherex.village.PigtificateVillageManager;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIRestrictFenceGateUse extends EntityAIBase
{
    private final EntityCreature creature;
    private PigtificateVillageFenceGateInfo fenceGate;

    public EntityAIRestrictFenceGateUse(EntityCreature creature)
    {
        this.creature = creature;

        if(!(creature.getNavigator() instanceof PathNavigateGround))
        {
            throw new IllegalArgumentException("Unsupported mob type for EntityAIRestrictFenceGateUse");
        }
    }

    @Override
    public boolean shouldExecute()
    {
        World world = this.creature.getEntityWorld();

        if(WorldHelper.isDaytime(world))
        {
            return false;
        }
        else
        {
            BlockPos pos = new BlockPos(this.creature);
            PigtificateVillage village = NetherEx.PIGTIFICATE_VILLAGE_MANAGER.getVillageData(world, true).getNearestVillage(pos, 16);

            if(village == null)
            {
                return false;
            }
            else
            {
                this.fenceGate = village.getNearestFenceGate(pos);
                return this.fenceGate != null && (double) this.fenceGate.getDistanceToInsideBlockSq(pos) < 2.25D;
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return !WorldHelper.isDaytime(this.creature.getEntityWorld()) && (!this.fenceGate.isDetachedFromVillageFlag() && this.fenceGate.isInside(new BlockPos(this.creature)));
    }

    @Override
    public void startExecuting()
    {
        ((PathNavigateGround) this.creature.getNavigator()).setBreakDoors(false);
        ((PathNavigateGround) this.creature.getNavigator()).setEnterDoors(false);
    }

    @Override
    public void resetTask()
    {
        ((PathNavigateGround) this.creature.getNavigator()).setBreakDoors(true);
        ((PathNavigateGround) this.creature.getNavigator()).setEnterDoors(true);
        this.fenceGate = null;
    }

    @Override
    public void updateTask()
    {
        this.fenceGate.incrementOpenRestrictionCounter();
    }
}
