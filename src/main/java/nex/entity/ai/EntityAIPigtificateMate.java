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

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import nex.entity.passive.EntityPigtificate;
import nex.village.PigtificateVillage;
import nex.village.PigtificateVillageManager;

public class EntityAIPigtificateMate extends EntityAIBase
{
    private final EntityPigtificate pigtificate;
    private EntityPigtificate mate;
    private final World world;
    private int matingTimeout;
    private PigtificateVillage village;

    public EntityAIPigtificateMate(EntityPigtificate pigtificate)
    {
        this.pigtificate = pigtificate;
        this.world = pigtificate.getEntityWorld();
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        if(this.pigtificate.getGrowingAge() != 0)
        {
            return false;
        }
        else if(this.pigtificate.getRNG().nextInt(1) != 0)
        {
            return false;
        }
        else
        {
            this.village = PigtificateVillageManager.getVillageData(this.world, true).getNearestVillage(new BlockPos(this.pigtificate), 0);

            if(this.village == null)
            {
                return false;
            }
            else if(this.doesVillageHaveRoom() && this.pigtificate.getWillingToMate(true))
            {
                EntityPigtificate entity = this.world.findNearestEntityWithinAABB(EntityPigtificate.class, this.pigtificate.getEntityBoundingBox().expand(8.0D, 3.0D, 8.0D), this.pigtificate);

                if(entity == null)
                {
                    return false;
                }
                else
                {
                    this.mate = entity;
                    return this.mate.getGrowingAge() == 0 && this.mate.getWillingToMate(true);
                }
            }
            else
            {
                return false;
            }
        }
    }

    @Override
    public void startExecuting()
    {
        this.matingTimeout = 300;
        this.pigtificate.setMating(true);
    }

    @Override
    public void resetTask()
    {
        this.village = null;
        this.mate = null;
        this.pigtificate.setMating(false);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.matingTimeout >= 0 && this.doesVillageHaveRoom() && this.pigtificate.getGrowingAge() == 0 && this.pigtificate.getWillingToMate(false);
    }

    @Override
    public void updateTask()
    {
        this.matingTimeout--;
        this.pigtificate.getLookHelper().setLookPositionWithEntity(this.mate, 10.0F, 30.0F);

        if(this.pigtificate.getDistanceSq(this.mate) > 2.25D)
        {
            this.pigtificate.getNavigator().tryMoveToEntityLiving(this.mate, 0.25D);
        }
        else if(this.matingTimeout == 0 && this.mate.isMating())
        {
            this.giveBirth();
        }

        if(this.pigtificate.getRNG().nextInt(35) == 0)
        {
            this.world.setEntityState(this.pigtificate, (byte) 12);
        }
    }

    private boolean doesVillageHaveRoom()
    {
        if(!this.village.isMatingSeason())
        {
            return false;
        }
        else
        {
            return this.village.getPigtificateAmount() < (this.village.getVillageFenceGateAmount() * 0.35D);
        }
    }

    private void giveBirth()
    {
        EntityAgeable baby = this.pigtificate.createChild(this.mate);
        this.mate.setGrowingAge(6000);
        this.pigtificate.setGrowingAge(6000);
        this.mate.setWillingToMate(false);
        this.pigtificate.setWillingToMate(false);

        BabyEntitySpawnEvent event = new BabyEntitySpawnEvent(this.pigtificate, this.mate, baby);

        if(MinecraftForge.EVENT_BUS.post(event) || event.getChild() == null)
        {
            return;
        }

        baby = event.getChild();
        baby.setGrowingAge(-24000);
        baby.setLocationAndAngles(this.pigtificate.posX, this.pigtificate.posY, this.pigtificate.posZ, 0.0F, 0.0F);
        this.world.spawnEntity(baby);
        this.world.setEntityState(baby, (byte) 12);
    }
}
