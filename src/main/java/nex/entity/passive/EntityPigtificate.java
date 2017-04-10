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

package nex.entity.passive;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.init.NetherExLootTables;

public class EntityPigtificate extends EntityAgeable
{
    private static final DataParameter<Integer> PROFESSION = EntityDataManager.createKey(EntityPigtificate.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> CAREER = EntityDataManager.createKey(EntityPigtificate.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(EntityPigtificate.class, DataSerializers.VARINT);

    public EntityPigtificate(World world)
    {
        super(world);

        setSize(0.6F, 2.2F);
        setRandomProfession();
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        tasks.addTask(2, new EntityAIWander(this, 0.6D));
        tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.6D));
        tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(PROFESSION, 0);
        dataManager.register(CAREER, 0);
        dataManager.register(LEVEL, 0);
    }

    @Override
    protected void updateAITasks()
    {
        super.updateAITasks();

        if(!hasHome())
        {
            setHomePosAndDistance(new BlockPos(this), 48);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Profession", getProfession());
        compound.setInteger("Career", getCareer());
        compound.setInteger("Level", getLevel());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setProfession(compound.getInteger("Profession"));
        setCareer(compound.getInteger("Career"));
        setLevel(compound.getInteger("Level"));
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        EntityPigtificate pigtificate = new EntityPigtificate(world);
        pigtificate.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(pigtificate)), null);
        return pigtificate;
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player)
    {
        return false;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
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
            String type = getProfession() == 0 ? "farmer" : getProfession() == 1 ? "butcher" : "blacksmith";
            return I18n.format("entity." + entityName + "." + type + ".name");
        }
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return getProfession() == 0 ? NetherExLootTables.ENTITY_PIGTIFICATE_FARMER : getProfession() == 1 ? NetherExLootTables.ENTITY_PIGTIFICATE_BUTCHER : NetherExLootTables.ENTITY_PIGTIFICATE_BLACKSMITH;
    }

    public int getProfession()
    {
        return dataManager.get(PROFESSION);
    }

    public int getCareer()
    {
        return dataManager.get(CAREER);
    }

    public int getLevel()
    {
        return dataManager.get(LEVEL);
    }

    private void setRandomProfession()
    {
        WeightedRandom.Item farmer = new WeightedRandom.Item(10);
        WeightedRandom.Item butcher = new WeightedRandom.Item(10);
        WeightedRandom.Item blacksmith = new WeightedRandom.Item(5);
        WeightedRandom.Item item = WeightedRandom.getRandomItem(rand, Lists.newArrayList(farmer, butcher, blacksmith));
        setProfession(item == farmer ? 0 : item == butcher ? 1 : 2);
    }

    public void setProfession(int profession)
    {
        if(profession < 0)
        {
            profession = 0;
        }
        else if(profession > 2)
        {
            profession = 2;
        }

        dataManager.set(PROFESSION, profession);
    }

    public void setCareer(int career)
    {
        if(career < 0)
        {
            career = 0;
        }

        dataManager.set(CAREER, career);
    }

    public void setLevel(int level)
    {
        if(level < 0)
        {
            level = 0;
        }

        dataManager.set(LEVEL, level);
    }
}
