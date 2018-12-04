package logictechcorp.netherex.entity.ai;

import logictechcorp.libraryex.util.EntityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.AbstractHorse;

public class EntityAIOwnerHurtByTargetRideable extends EntityAITarget
{
    private final AbstractHorse rideable;
    private EntityLivingBase attacker;
    private int timestamp;

    public EntityAIOwnerHurtByTargetRideable(AbstractHorse rideable)
    {
        super(rideable, false);
        this.rideable = rideable;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        if(!this.rideable.isTame())
        {
            return false;
        }
        else
        {
            Entity entity = EntityHelper.getFromUUID(this.rideable.getServer(), this.rideable.getOwnerUniqueId());

            if(!(entity instanceof EntityLivingBase))
            {
                return false;
            }
            else
            {
                this.attacker = ((EntityLivingBase) entity).getRevengeTarget();
                int i = ((EntityLivingBase) entity).getRevengeTimer();
                return i != this.timestamp && this.isSuitableTarget(this.attacker, false) && EntityHelper.shouldAttackEntity(this.attacker, ((EntityLivingBase) entity));
            }
        }
    }

    @Override
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.attacker);

        if(this.rideable.getServer() != null && this.rideable.getOwnerUniqueId() != null)
        {
            Entity entity = this.rideable.getServer().getEntityFromUuid(this.rideable.getOwnerUniqueId());

            if(entity != null)
            {
                this.timestamp = ((EntityLivingBase) entity).getRevengeTimer();
            }
        }
        super.startExecuting();
    }
}
