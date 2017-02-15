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

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import nex.entity.ai.EntityAIGhastQueenFireballAttack;
import nex.entity.ai.EntityAIGhastQueenFly;
import nex.entity.ai.EntityAIGhastQueenLookAround;
import nex.handler.ConfigHandler;

public class EntityGhastQueen extends EntityGhast
{
    private static final DataParameter<Integer> COOLDOWN = EntityDataManager.createKey(EntityGhastQueen.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> GHAST_SPAWNING_STAGE = EntityDataManager.createKey(EntityGhastQueen.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> GHAST_SPAWNING_STAGE_STARTED = EntityDataManager.createKey(EntityGhastQueen.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SHOULD_SPAWN_GHAST = EntityDataManager.createKey(EntityGhastQueen.class, DataSerializers.BOOLEAN);

    private static boolean ghastSpawnStageStarted[] = new boolean[4];

    public EntityGhastQueen(World world)
    {
        super(world);

        setSize(6.0F, 6.0F);
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAIGhastQueenLookAround(this));
        tasks.addTask(1, new EntityAIGhastQueenFly(this));
        tasks.addTask(2, new EntityAIGhastQueenFireballAttack(this));
        targetTasks.addTask(0, new EntityAIFindEntityNearestPlayer(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(140.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(COOLDOWN, 0);
        dataManager.register(GHAST_SPAWNING_STAGE, 0);
        dataManager.register(GHAST_SPAWNING_STAGE_STARTED, 0);
        dataManager.register(SHOULD_SPAWN_GHAST, false);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getCooldown() == 0)
        {
            if(getGhastSpawningStage() < 4)
            {
                System.out.println("Queen Health: " + getHealth());
                System.out.println("Ghast Spawning Stage: " + getGhastSpawningStage());
                System.out.println("Ghast Spawning Stage Started: " + getGhastSpawnStageStarted());

                if(!getGhastSpawnStageStarted() && getHealth() < getMaxHealth() - getGhastSpawningStage() * 35.0D)
                {
                    setShouldSpawnGhast(true);
                }

                if(!world.isRemote && shouldSpawnGhast())
                {
                    for(int i = 0; i < ConfigHandler.Entity.GhastQueen.ghastSpawns; i++)
                    {
                        EntityGhast ghast = new EntityGhast(world);
                        ghast.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
                        ghast.setHealth(20.0F);
                        ghast.setPosition(posX, posY - 1, posZ);
                        ghast.setAttackTarget(getAttackTarget());
                        world.spawnEntity(ghast);

                        System.out.println("Ghastling Health: " + ghast.getHealth());
                    }

                    setGhastSpawnStageStarted(getGhastSpawningStage() + 1);
                    setCooldown(ConfigHandler.Entity.GhastQueen.attackCooldown * 20);
                    setGhastSpawningStage(getGhastSpawningStage() + 1);
                    setShouldSpawnGhast(false);
                }
            }
        }
        else
        {
            setAttackTarget(null);
            setCooldown(getCooldown() - 1);
        }
    }

    @Override
    public int getFireballStrength()
    {
        return 3;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Cooldown", getCooldown());
        compound.setInteger("GhastSpawningStage", getGhastSpawningStage());
        compound.setBoolean("GhastSpawnStageStarted", getGhastSpawnStageStarted());
        compound.setBoolean("SpawnGhast", shouldSpawnGhast());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setCooldown(compound.getInteger("Cooldown"));
        setGhastSpawningStage(compound.getInteger("GhastSpawningStage"));
        setGhastSpawnStageStarted(compound.getInteger("GhastSpawnStageStarted"));
        setShouldSpawnGhast(compound.getBoolean("SpawnGhast"));
    }
    
    @Override
    public String getName()
    {
        if(hasCustomName())
        {
            return getCustomNameTag();
        }
        else
        {
            String entityName = EntityList.getEntityString(this);

            if(entityName == null)
            {
                entityName = "generic";
            }

            return I18n.format("entity." + entityName + ".name");
        }
    }

    private int getCooldown()
    {
        return getDataManager().get(COOLDOWN);
    }

    private int getGhastSpawningStage()
    {
        return getDataManager().get(GHAST_SPAWNING_STAGE);
    }

    private boolean shouldSpawnGhast()
    {
        return getDataManager().get(SHOULD_SPAWN_GHAST);
    }

    private boolean getGhastSpawnStageStarted()
    {
        return getDataManager().get(GHAST_SPAWNING_STAGE).intValue() != getDataManager().get(GHAST_SPAWNING_STAGE_STARTED).intValue();
    }

    private void setCooldown(int amount)
    {
        getDataManager().set(COOLDOWN, amount);
    }

    private void setGhastSpawningStage(int stage)
    {
        getDataManager().set(GHAST_SPAWNING_STAGE, stage);
    }

    private void setShouldSpawnGhast(boolean spawnGhast)
    {
        getDataManager().set(SHOULD_SPAWN_GHAST, spawnGhast);
    }

    private void setGhastSpawnStageStarted(int started)
    {
        getDataManager().set(GHAST_SPAWNING_STAGE_STARTED, started);
    }
}
