package nex.entity.ai;

import lex.util.EntityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.AbstractHorse;

public class EntityAIOwnerHurtTargetRideable extends EntityAITarget
{
    private AbstractHorse rideable;
    private EntityLivingBase attacker;
    private int timestamp;

    public EntityAIOwnerHurtTargetRideable(AbstractHorse rideable)
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
                this.attacker = ((EntityLivingBase) entity).getLastAttackedEntity();
                int i = ((EntityLivingBase) entity).getLastAttackedEntityTime();
                return i != this.timestamp && this.isSuitableTarget(this.attacker, false) && EntityHelper.shouldAttackEntity(this.attacker, ((EntityLivingBase) entity));
            }
        }
    }

    @Override
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.attacker);
        Entity entity = EntityHelper.getFromUUID(this.rideable.getServer(), this.rideable.getOwnerUniqueId());

        if(entity instanceof EntityLivingBase)
        {
            this.timestamp = ((EntityLivingBase) entity).getLastAttackedEntityTime();
        }

        super.startExecuting();
    }
}
