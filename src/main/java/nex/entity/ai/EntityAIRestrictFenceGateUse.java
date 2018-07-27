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

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.BlockPos;
import nex.village.PigtificateVillage;
import nex.village.PigtificateVillageFenceGateInfo;
import nex.village.PigtificateVillageManager;

public class EntityAIRestrictFenceGateUse extends EntityAIBase
{
    private final EntityCreature entityObj;
    private PigtificateVillageFenceGateInfo fenceGate;

    public EntityAIRestrictFenceGateUse(EntityCreature creatureIn)
    {
        entityObj = creatureIn;

        if(!(creatureIn.getNavigator() instanceof PathNavigateGround))
        {
            throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
        }
    }

    @Override
    public boolean shouldExecute()
    {
        if(entityObj.world.isDaytime())
        {
            return false;
        }
        else
        {
            BlockPos blockpos = new BlockPos(entityObj);
            PigtificateVillage village = PigtificateVillageManager.getNetherVillages(entityObj.getEntityWorld(), true).getNearestVillage(blockpos, 16);

            if(village == null)
            {
                return false;
            }
            else
            {
                fenceGate = village.getNearestFenceGate(blockpos);
                return fenceGate != null && (double) fenceGate.getDistanceToInsideBlockSq(blockpos) < 2.25D;
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return !entityObj.world.isDaytime() && (!fenceGate.getIsDetachedFromVillageFlag() && fenceGate.isInsideSide(new BlockPos(entityObj)));
    }

    @Override
    public void startExecuting()
    {
        ((PathNavigateGround) entityObj.getNavigator()).setBreakDoors(false);
        ((PathNavigateGround) entityObj.getNavigator()).setEnterDoors(false);
    }

    @Override
    public void resetTask()
    {
        ((PathNavigateGround) entityObj.getNavigator()).setBreakDoors(true);
        ((PathNavigateGround) entityObj.getNavigator()).setEnterDoors(true);
        fenceGate = null;
    }

    @Override
    public void updateTask()
    {
        fenceGate.incrementFenceGateOpeningRestrictionCounter();
    }
}
