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
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import nex.village.PigtificateVillage;
import nex.village.PigtificateVillageFenceGateInfo;
import nex.village.PigtificateVillageManager;

public class EntityAIMoveInFenceGates extends EntityAIBase
{
    private final EntityCreature creature;
    private PigtificateVillageFenceGateInfo fenceGate;
    private int insidePosX = -1;
    private int insidePosZ = -1;

    public EntityAIMoveInFenceGates(EntityCreature creature)
    {
        this.creature = creature;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        World world = this.creature.getEntityWorld();
        BlockPos pos = new BlockPos(this.creature);

        if(!WorldHelper.isDaytime(world) || world.isRaining())
        {
            if(this.creature.getRNG().nextInt(50) != 0)
            {
                return false;
            }
            else if(this.insidePosX != -1 && this.creature.getDistanceSq((double) this.insidePosX, this.creature.posY, (double) this.insidePosZ) < 4.0D)
            {
                return false;
            }
            else
            {
                PigtificateVillage village = PigtificateVillageManager.getVillageData(world, true).getNearestVillage(pos, 14);

                if(village == null)
                {
                    return false;
                }
                else
                {
                    this.fenceGate = village.getFenceGateInfo(pos);
                    return this.fenceGate != null;
                }
            }
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return !this.creature.getNavigator().noPath();
    }

    @Override
    public void startExecuting()
    {
        this.insidePosX = -1;
        BlockPos pos = this.fenceGate.getInsidePos();
        int posX = pos.getX();
        int posY = pos.getY();
        int posZ = pos.getZ();

        if(this.creature.getDistanceSq(pos) > 256.0D)
        {
            Vec3d randomPos = RandomPositionGenerator.findRandomTargetBlockTowards(this.creature, 14, 3, new Vec3d((double) posX + 0.5D, (double) posY, (double) posZ + 0.5D));

            if(randomPos != null)
            {
                this.creature.getNavigator().tryMoveToXYZ(randomPos.x, randomPos.y, randomPos.z, 1.0D);
            }
        }
        else
        {
            this.creature.getNavigator().tryMoveToXYZ((double) posX + 0.5D, (double) posY, (double) posZ + 0.5D, 1.0D);
        }
    }

    @Override
    public void resetTask()
    {
        this.insidePosX = this.fenceGate.getInsidePos().getX();
        this.insidePosZ = this.fenceGate.getInsidePos().getZ();
        this.fenceGate = null;
    }
}
