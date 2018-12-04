package logictechcorp.netherex.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.AbstractHorse;

public class EntityAITargetNonTamedRideable<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>
{
    private final AbstractHorse rideable;

    public EntityAITargetNonTamedRideable(AbstractHorse rideable, Class<T> classTarget, boolean checkSight, Predicate<? super T> targetSelector)
    {
        super(rideable, classTarget, 10, checkSight, false, targetSelector);
        this.rideable = rideable;
    }

    @Override
    public boolean shouldExecute()
    {
        return !this.rideable.isTame() && super.shouldExecute();
    }
}
