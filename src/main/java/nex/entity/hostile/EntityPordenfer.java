package nex.entity.hostile;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityPordenfer extends EntityMob
{
    public EntityPordenfer(World worldIn)
    {
        super(worldIn);

        setSize(0.75F, 0.55F);
        stepHeight = 0.5F;
        isImmuneToFire = true;
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        tasks.addTask(2, new EntityAIWander(this, 1.0D));
        tasks.addTask(3, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && super.getCanSpawnHere();
    }

    @Override
    protected Item getDropItem()
    {
        return Items.BONE;
    }
}
