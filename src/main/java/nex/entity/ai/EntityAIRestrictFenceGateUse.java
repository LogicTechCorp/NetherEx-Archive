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

package nex.entity.ai;

import lex.util.WorldHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.village.PigtificateVillage;
import nex.village.PigtificateVillageFenceGateInfo;
import nex.village.PigtificateVillageManager;

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
        World world = creature.getEntityWorld();

        if(WorldHelper.isDaytime(world))
        {
            return false;
        }
        else
        {
            BlockPos pos = new BlockPos(creature);
            PigtificateVillage village = PigtificateVillageManager.getVillageData(world, true).getNearestVillage(pos, 16);

            if(village == null)
            {
                return false;
            }
            else
            {
                fenceGate = village.getNearestFenceGate(pos);
                return fenceGate != null && (double) fenceGate.getDistanceToInsideBlockSq(pos) < 2.25D;
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return !WorldHelper.isDaytime(creature.getEntityWorld()) && (!fenceGate.isDetachedFromVillageFlag() && fenceGate.isInside(new BlockPos(creature)));
    }

    @Override
    public void startExecuting()
    {
        ((PathNavigateGround) creature.getNavigator()).setBreakDoors(false);
        ((PathNavigateGround) creature.getNavigator()).setEnterDoors(false);
    }

    @Override
    public void resetTask()
    {
        ((PathNavigateGround) creature.getNavigator()).setBreakDoors(true);
        ((PathNavigateGround) creature.getNavigator()).setEnterDoors(true);
        fenceGate = null;
    }

    @Override
    public void updateTask()
    {
        fenceGate.incrementOpenRestrictionCounter();
    }
}
