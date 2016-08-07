package nex.entity.neutral;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import nex.entity.hostile.EntityWight;

public class EntityMogus extends EntityMob
{
    private static final DataParameter<Integer> MOGUS_TYPE = EntityDataManager.createKey(EntityWight.class, DataSerializers.VARINT);

    public EntityMogus(World worldIn)
    {
        super(worldIn);

        setSize(0.35F, 0.45F);
        stepHeight = 0.5F;
        isImmuneToFire = true;

        setRandomType();
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackMelee(this, 1.45D, true));
        tasks.addTask(1, new EntityAIPanic(this, 1.45D));
        tasks.addTask(2, new EntityAIWander(this, 1.0D));
        tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(4, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(MOGUS_TYPE, 0);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Type", getType());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        setType(compound.getInteger("Type"));
        super.readFromNBT(compound);
    }

    @Override
    protected Item getDropItem()
    {
        return getType() == 0 ? Item.getItemFromBlock(Blocks.RED_MUSHROOM) : Item.getItemFromBlock(Blocks.BROWN_MUSHROOM);
    }

    public int getType()
    {
        return this.dataManager.get(MOGUS_TYPE);
    }

    private void setRandomType()
    {
        this.dataManager.set(MOGUS_TYPE, rand.nextInt(2));
    }

    public void setType(int id)
    {
        if(id < 0)
        {
            id = 0;
        }
        else if(id > 1)
        {
            id = 1;
        }

        this.dataManager.set(MOGUS_TYPE, id);
    }
}
