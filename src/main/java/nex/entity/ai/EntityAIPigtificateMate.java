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

package nex.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import nex.entity.passive.EntityPigtificate;
import nex.village.NetherVillage;
import nex.village.NetherVillageManager;

public class EntityAIPigtificateMate extends EntityAIBase
{
    private final EntityPigtificate pigtificate;
    private EntityPigtificate mate;
    private final World world;
    private int matingTimeout;
    NetherVillage village;

    public EntityAIPigtificateMate(EntityPigtificate pigtificateIn)
    {
        pigtificate = pigtificateIn;
        world = pigtificateIn.getEntityWorld();
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        if(pigtificate.getGrowingAge() != 0)
        {
            return false;
        }
        else if(pigtificate.getRNG().nextInt(1) != 0)
        {
            return false;
        }
        else
        {
            village = NetherVillageManager.getNetherVillages(world).getNearestVillage(new BlockPos(pigtificate), 0);

            if(village == null)
            {
                return false;
            }
            else if(checkSufficientDoorsPresentForNewVillager() && pigtificate.getWillingToMate(true))
            {
                Entity entity = world.findNearestEntityWithinAABB(EntityPigtificate.class, pigtificate.getEntityBoundingBox().expand(8.0D, 3.0D, 8.0D), pigtificate);

                if(entity == null)
                {
                    return false;
                }
                else
                {
                    mate = (EntityPigtificate) entity;
                    return mate.getGrowingAge() == 0 && mate.getWillingToMate(true);
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
        matingTimeout = 300;
        pigtificate.setMating(true);
    }

    @Override
    public void resetTask()
    {
        village = null;
        mate = null;
        pigtificate.setMating(false);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return matingTimeout >= 0 && checkSufficientDoorsPresentForNewVillager() && pigtificate.getGrowingAge() == 0 && pigtificate.getWillingToMate(false);
    }

    @Override
    public void updateTask()
    {
        --matingTimeout;
        pigtificate.getLookHelper().setLookPositionWithEntity(mate, 10.0F, 30.0F);

        if(pigtificate.getDistanceSqToEntity(mate) > 2.25D)
        {
            pigtificate.getNavigator().tryMoveToEntityLiving(mate, 0.25D);
        }
        else if(matingTimeout == 0 && mate.isMating())
        {
            giveBirth();
        }

        if(pigtificate.getRNG().nextInt(35) == 0)
        {
            world.setEntityState(pigtificate, (byte) 12);
        }
    }

    private boolean checkSufficientDoorsPresentForNewVillager()
    {
        if(!village.isMatingSeason())
        {
            return false;
        }
        else
        {
            int i = (int) ((double) ((float) village.getNumVillageFenceGates()) * 0.35D);
            return village.getNumPigtificates() < i;
        }
    }

    private void giveBirth()
    {
        EntityAgeable baby = pigtificate.createChild(mate);
        mate.setGrowingAge(6000);
        pigtificate.setGrowingAge(6000);
        mate.setWillingToMate(false);
        pigtificate.setWillingToMate(false);

        final BabyEntitySpawnEvent event = new BabyEntitySpawnEvent(pigtificate, mate, baby);
        if(MinecraftForge.EVENT_BUS.post(event) || event.getChild() == null)
        {
            return;
        }
        baby = event.getChild();
        baby.setGrowingAge(-24000);
        baby.setLocationAndAngles(pigtificate.posX, pigtificate.posY, pigtificate.posZ, 0.0F, 0.0F);
        world.spawnEntity(baby);
        world.setEntityState(baby, (byte) 12);
    }
}
