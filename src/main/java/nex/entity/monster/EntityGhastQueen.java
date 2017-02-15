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
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import nex.handler.ConfigHandler;

public class EntityGhastQueen extends EntityGhast
{
    private static int cooldown;
    private static int ghastSpawningStage;
    private static boolean spawnGhast;

    public EntityGhastQueen(World world)
    {
        super(world);

        setSize(6.0F, 6.0F);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getCooldown() == 0)
        {
            if(getGhastSpawningStage() < 3)
            {
                if(getHealth() < getMaxHealth() - getGhastSpawningStage() * 25.0D)
                {
                    if(!world.isRemote && shouldSpawnGhast())
                    {
                        for(int i = 0; i < ConfigHandler.Entity.GhastQueen.ghastSpawns; i++)
                        {
                            EntityGhast ghast = new EntityGhast(world);
                            ghast.setPosition(posX, posY, posZ);
                            ghast.setAttackTarget(getAttackTarget());
                            world.spawnEntity(ghast);
                        }

                        setCooldown(ConfigHandler.Entity.GhastQueen.attackCooldown * 20);
                        setGhastSpawningStage(getGhastSpawningStage() + 1);
                        setShouldSpawnGhast(false);
                    }
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
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("CoolDown", getCooldown());
        compound.setInteger("GhastSpawningStage", getGhastSpawningStage());
        compound.setBoolean("SpawnGhast", shouldSpawnGhast());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setCooldown(compound.getInteger("Cooldown"));
        setGhastSpawningStage(compound.getInteger("GhastSpawningStage"));
        setShouldSpawnGhast(compound.getBoolean("SpawnGhast"));
    }
    
    @Override
    public int getFireballStrength()
    {
        return 3;
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
        return cooldown;
    }

    private int getGhastSpawningStage()
    {
        return ghastSpawningStage;
    }

    private boolean shouldSpawnGhast()
    {
        return spawnGhast;
    }

    private void setCooldown(int cooldownIn)
    {
        cooldown = cooldownIn;
    }

    private void setGhastSpawningStage(int ghastSpawningStageIn)
    {
        ghastSpawningStage = ghastSpawningStageIn;
    }

    private void setShouldSpawnGhast(boolean spawnGhastIn)
    {
        spawnGhast = spawnGhastIn;
    }
}
