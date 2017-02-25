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

package nex.entity.boss;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import nex.entity.ai.EntityAIGhastFly;
import nex.entity.ai.EntityAIGhastLookAround;
import nex.entity.ai.EntityAIGhastQueenFireballAttack;
import nex.entity.monster.EntityGhastling;
import nex.handler.ConfigHandler;
import nex.init.NetherExSoundEvents;

public class EntityGhastQueen extends EntityGhast
{
    private static final DataParameter<Integer> COOLDOWN = EntityDataManager.createKey(EntityGhastQueen.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> GHAST_SPAWNING_STAGE = EntityDataManager.createKey(EntityGhastQueen.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> GHAST_SPAWNING_STAGE_STARTED = EntityDataManager.createKey(EntityGhastQueen.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SHOULD_SPAWN_GHAST = EntityDataManager.createKey(EntityGhastQueen.class, DataSerializers.BOOLEAN);

    private final EntityAIBase fireballAttack = new EntityAIGhastQueenFireballAttack(this);

    private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS));

    public EntityGhastQueen(World world)
    {
        super(world);

        setSize(9.5F, 9.5F);
        experienceValue = 100;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSoundEvents.ENTITY_AMBIENT_GHAST_QUEEN;
    }

    @Override
    protected SoundEvent getHurtSound()
    {
        return NetherExSoundEvents.ENTITY_HURT_GHAST_QUEEN;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.ENTITY_DEATH_GHAST_QUEEN;
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(1, new EntityAIGhastLookAround(this));
        tasks.addTask(2, new EntityAIGhastFly(this));
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
    public float getEyeHeight()
    {
        return 3.75F;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getCooldown() == 0)
        {
            if(tasks.taskEntries.size() == 2)
            {
                tasks.addTask(0, fireballAttack);
            }

            if(getGhastSpawningStage() < 4)
            {
                if(!getGhastSpawnStageStarted() && getHealth() < getMaxHealth() - getGhastSpawningStage() * 35.0D)
                {
                    setShouldSpawnGhast(true);
                }

                if(!world.isRemote && shouldSpawnGhast())
                {
                    for(int i = 0; i < ConfigHandler.Entity.GhastQueen.ghastlingSpawns; i++)
                    {
                        EntityGhastling ghastling = new EntityGhastling(world);
                        ghastling.setPosition(posX, posY - 1, posZ);
                        world.spawnEntity(ghastling);
                    }

                    setGhastSpawnStageStarted(getGhastSpawningStage() + 1);
                    setCooldown(ConfigHandler.Entity.GhastQueen.ghastlingSpawnCooldown * 20);
                    setGhastSpawningStage(getGhastSpawningStage() + 1);
                    setShouldSpawnGhast(false);
                }
            }
        }
        else
        {
            tasks.removeTask(fireballAttack);
            setCooldown(getCooldown() - 1);
        }

        bossInfo.setPercent(getHealth() / getMaxHealth());
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

        if(hasCustomName())
        {
            bossInfo.setName(getDisplayName());
        }
    }

    @Override
    public boolean isNonBoss()
    {
        return false;
    }

    @Override
    public int getFireballStrength()
    {
        return 3;
    }

    @Override
    protected void despawnEntity()
    {
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        bossInfo.removePlayer(player);
    }

    @Override
    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
        bossInfo.setName(getDisplayName());
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
