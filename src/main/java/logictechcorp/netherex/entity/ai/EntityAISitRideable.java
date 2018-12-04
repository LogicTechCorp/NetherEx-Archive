package logictechcorp.netherex.entity.ai;

import logictechcorp.libraryex.util.EntityHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.AbstractHorse;

public class EntityAISitRideable extends EntityAIBase
{
    private final AbstractHorse rideable;
    private boolean sitting;

    public EntityAISitRideable(AbstractHorse rideable)
    {
        this.rideable = rideable;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute()
    {
        if(!this.rideable.isTame())
        {
            return false;
        }
        else if(this.rideable.isInWater())
        {
            return false;
        }
        else if(!this.rideable.onGround)
        {
            return false;
        }
        else
        {
            EntityLivingBase entity = (EntityLivingBase) EntityHelper.getFromUUID(this.rideable.getServer(), this.rideable.getOwnerUniqueId());

            if(entity == null)
            {
                return true;
            }
            else
            {
                return (!(this.rideable.getDistanceSq(entity) < 144.0D) || entity.getRevengeTarget() == null) && this.sitting;
            }
        }
    }

    @Override
    public void startExecuting()
    {
        this.rideable.getNavigator().clearPath();
        this.setSitting(true);
    }

    @Override
    public void resetTask()
    {
        this.setSitting(false);
    }

    public boolean isSitting()
    {
        return this.sitting;
    }

    public void setSitting(boolean sitting)
    {
        this.sitting = sitting;
    }
}
