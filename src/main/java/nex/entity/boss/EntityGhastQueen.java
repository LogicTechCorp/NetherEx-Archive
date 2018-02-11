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

package nex.entity.boss;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import nex.block.BlockUrnOfSorrow;
import nex.entity.ai.EntityAIGhastFly;
import nex.entity.ai.EntityAIGhastLookAround;
import nex.entity.ai.EntityAIGhastQueenFireballAttack;
import nex.entity.monster.EntityGhastling;
import nex.handler.ConfigHandler;
import nex.init.NetherExBlocks;
import nex.init.NetherExLootTables;
import nex.init.NetherExSoundEvents;

public class EntityGhastQueen extends EntityGhast
{
    private int cooldown;
    private int stage;

    private boolean[] stageStarted = new boolean[5];
    private boolean spawnGhastlings;

    private BlockPos urnPos = BlockPos.ORIGIN;

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
        return NetherExSoundEvents.GHAST_QUEEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSoundEvents.GHAST_QUEEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.GHAST_QUEEN_DEATH;
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
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(256.0D);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(140.0D);
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

            if(getStage() < 4)
            {
                if(!getStageStarted(getStage()) && getHealth() < getMaxHealth() - getStage() * 35.0D)
                {
                    setShouldSpawnGhastlings(true);
                }

                if(!getEntityWorld().isRemote && shouldSpawnGhastlings())
                {
                    for(int i = 0; i < ConfigHandler.entityConfig.ghastQueen.ghastlingSpawns; i++)
                    {
                        EntityGhastling ghastling = new EntityGhastling(getEntityWorld());
                        ghastling.setPosition(getPosition().getX(), getPosition().getY() - 1, getPosition().getZ());
                        getEntityWorld().spawnEntity(ghastling);
                    }

                    setStageStarted(getStage(), true);
                    setCooldown(ConfigHandler.entityConfig.ghastQueen.ghastlingSpawnCooldown * 20);
                    setStage(getStage() + 1);
                    setShouldSpawnGhastlings(false);
                }
            }
        }
        else
        {
            if(tasks.taskEntries.size() == 3)
            {
                tasks.removeTask(fireballAttack);
            }
            setCooldown(getCooldown() - 1);
        }

        bossInfo.setPercent(getHealth() / getMaxHealth());
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        if(getUrnPos() != BlockPos.ORIGIN)
        {
            getEntityWorld().setBlockToAir(getUrnPos());
            getEntityWorld().setBlockState(getUrnPos(), NetherExBlocks.URN_OF_SORROW.getDefaultState().withProperty(BlockUrnOfSorrow.TYPE, BlockUrnOfSorrow.EnumType.EMPTY));
        }

        super.onDeath(cause);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setIntArray("UrnPos", new int[]{getUrnPos().getX(), getUrnPos().getY(), getUrnPos().getZ()});
        compound.setInteger("Cooldown", getCooldown());
        compound.setInteger("Stage", getStage());

        for(int i = 0; i < stageStarted.length; i++)
        {
            compound.setBoolean("StageStarted" + i, getStageStarted(i));
        }

        compound.setBoolean("SpawnGhast", shouldSpawnGhastlings());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setUrnPos(new BlockPos(compound.getIntArray("UrnPos")[0], compound.getIntArray("UrnPos")[1], compound.getIntArray("UrnPos")[2]));
        setCooldown(compound.getInteger("Cooldown"));
        setStage(compound.getInteger("Stage"));

        for(int i = 0; i < stageStarted.length; i++)
        {
            setStageStarted(i, compound.getBoolean("StageStarted" + i));
        }

        setShouldSpawnGhastlings(compound.getBoolean("SpawnGhast"));

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
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.GHAST_QUEEN;
    }

    private BlockPos getUrnPos()
    {
        return urnPos;
    }

    private int getCooldown()
    {
        return cooldown;
    }

    private int getStage()
    {
        return stage;
    }

    private boolean shouldSpawnGhastlings()
    {
        return spawnGhastlings;
    }

    private boolean getStageStarted(int i)
    {
        return stageStarted[i];
    }

    public void setUrnPos(BlockPos pos)
    {
        urnPos = pos;
    }

    private void setCooldown(int i)
    {
        cooldown = i;
    }

    private void setStage(int i)
    {
        stage = i;
    }

    private void setShouldSpawnGhastlings(boolean bool)
    {
        spawnGhastlings = bool;
    }

    private void setStageStarted(int i, boolean bool)
    {
        stageStarted[i] = bool;
    }
}
