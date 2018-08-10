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

    private BlockPos urnPos;

    private final EntityAIBase fireballAttack = new EntityAIGhastQueenFireballAttack(this);

    private final BossInfoServer bossInfo = new BossInfoServer(getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS);

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

        if(cooldown == 0)
        {
            if(tasks.taskEntries.size() == 2)
            {
                tasks.addTask(0, fireballAttack);
            }

            if(stage < 4)
            {
                if(!stageStarted[stage] && getHealth() < getMaxHealth() - stage * 35.0D)
                {
                    spawnGhastlings = true;
                }

                if(!world.isRemote && spawnGhastlings)
                {
                    for(int i = 0; i < ConfigHandler.entityConfig.ghastQueen.ghastlingSpawnAmount; i++)
                    {
                        EntityGhastling ghastling = new EntityGhastling(world);
                        ghastling.setPosition(posX, posY - 1, posZ);
                        world.spawnEntity(ghastling);
                    }

                    stageStarted[stage] = true;
                    cooldown = ConfigHandler.entityConfig.ghastQueen.ghastlingSpawnCooldown * 20;
                    stage += 1;
                    spawnGhastlings = false;
                }
            }
        }
        else
        {
            if(tasks.taskEntries.size() == 3)
            {
                tasks.removeTask(fireballAttack);
            }

            cooldown -= 1;
        }

        bossInfo.setPercent(getHealth() / getMaxHealth());
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        if(urnPos != null)
        {
            world.setBlockToAir(urnPos);
            world.setBlockState(urnPos, NetherExBlocks.URN_OF_SORROW.getDefaultState().withProperty(BlockUrnOfSorrow.TYPE, BlockUrnOfSorrow.EnumType.EMPTY));
        }

        super.onDeath(cause);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        if(urnPos != null)
        {
            compound.setInteger("UrnPosX", urnPos.getX());
            compound.setInteger("UrnPosY", urnPos.getY());
            compound.setInteger("UrnPosZ", urnPos.getZ());
        }

        compound.setInteger("Cooldown", cooldown);
        compound.setInteger("Stage", stage);

        for(int i = 0; i < stageStarted.length; i++)
        {
            compound.setBoolean("StageStarted" + i, stageStarted[i]);
        }

        compound.setBoolean("SpawnGhast", spawnGhastlings);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        urnPos = new BlockPos(compound.getInteger("UrnPosX"), compound.getInteger("UrnPosY"), compound.getInteger("UrnPosZ"));
        cooldown = compound.getInteger("Cooldown");
        stage = compound.getInteger("Stage");

        for(int i = 0; i < stageStarted.length; i++)
        {
            stageStarted[i] = compound.getBoolean("StageStarted" + i);
        }

        spawnGhastlings = compound.getBoolean("SpawnGhast");

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

    public void setUrnPos(BlockPos pos)
    {
        urnPos = pos;
    }
}
