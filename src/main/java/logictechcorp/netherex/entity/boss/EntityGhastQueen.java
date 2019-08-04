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

package logictechcorp.netherex.entity.boss;

import logictechcorp.netherex.block.BlockUrnOfSorrow;
import logictechcorp.netherex.entity.ai.EntityAIChangeFlyDirection;
import logictechcorp.netherex.entity.ai.EntityAIFlyRandomly;
import logictechcorp.netherex.entity.ai.EntityAIGhastQueenFireballAttack;
import logictechcorp.netherex.entity.monster.EntityGhastling;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExSoundEvents;
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

public class EntityGhastQueen extends EntityGhast
{
    private int cooldown;
    private int stage;

    private boolean[] stageStarted = new boolean[5];
    private boolean spawnGhastlings;

    private BlockPos urnPos;

    private final EntityAIBase fireballAttack = new EntityAIGhastQueenFireballAttack(this);

    private final BossInfoServer bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS);

    public EntityGhastQueen(World world)
    {
        super(world);
        this.experienceValue = 100;
        this.setSize(9.5F, 9.5F);
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
        this.tasks.addTask(1, new EntityAIChangeFlyDirection(this));
        this.tasks.addTask(2, new EntityAIFlyRandomly(this));
        this.targetTasks.addTask(0, new EntityAIFindEntityNearestPlayer(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(256.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(140.0D);
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

        if(this.cooldown == 0)
        {
            if(this.tasks.taskEntries.size() == 2)
            {
                this.tasks.addTask(0, this.fireballAttack);
            }

            if(this.stage < 4)
            {
                if(!this.stageStarted[this.stage] && this.getHealth() < this.getMaxHealth() - this.stage * 35.0D)
                {
                    this.spawnGhastlings = true;
                }

                if(!this.world.isRemote && this.spawnGhastlings)
                {
                    for(int i = 0; i < ConfigHandler.entityConfig.ghastQueen.ghastlingSpawnAmount; i++)
                    {
                        EntityGhastling ghastling = new EntityGhastling(this.world);
                        ghastling.setPosition(this.posX, this.posY - 1, this.posZ);
                        this.world.spawnEntity(ghastling);
                    }

                    this.stageStarted[this.stage] = true;
                    this.cooldown = ConfigHandler.entityConfig.ghastQueen.ghastlingSpawnCooldown * 20;
                    this.stage += 1;
                    this.spawnGhastlings = false;
                }
            }
        }
        else
        {
            if(this.tasks.taskEntries.size() == 3)
            {
                this.tasks.removeTask(this.fireballAttack);
            }

            this.cooldown -= 1;
        }

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void onDeath(DamageSource source)
    {
        if(this.urnPos != null)
        {
            this.world.setBlockToAir(this.urnPos);
            this.world.setBlockState(this.urnPos, NetherExBlocks.URN_OF_SORROW.getDefaultState().withProperty(BlockUrnOfSorrow.TYPE, BlockUrnOfSorrow.EnumType.EMPTY));
        }

        super.onDeath(source);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        if(this.urnPos != null)
        {
            compound.setInteger("UrnPosX", this.urnPos.getX());
            compound.setInteger("UrnPosY", this.urnPos.getY());
            compound.setInteger("UrnPosZ", this.urnPos.getZ());
        }

        compound.setInteger("Cooldown", this.cooldown);
        compound.setInteger("Stage", this.stage);

        for(int i = 0; i < this.stageStarted.length; i++)
        {
            compound.setBoolean("StageStarted" + i, this.stageStarted[i]);
        }

        compound.setBoolean("SpawnGhast", this.spawnGhastlings);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.urnPos = new BlockPos(compound.getInteger("UrnPosX"), compound.getInteger("UrnPosY"), compound.getInteger("UrnPosZ"));
        this.cooldown = compound.getInteger("Cooldown");
        this.stage = compound.getInteger("Stage");

        for(int i = 0; i < this.stageStarted.length; i++)
        {
            this.stageStarted[i] = compound.getBoolean("StageStarted" + i);
        }

        this.spawnGhastlings = compound.getBoolean("SpawnGhast");

        if(this.hasCustomName())
        {
            this.bossInfo.setName(this.getDisplayName());
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
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.GHAST_QUEEN;
    }

    public void setUrnPos(BlockPos pos)
    {
        this.urnPos = pos;
    }
}
