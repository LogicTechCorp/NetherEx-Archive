package nex.entity.monster;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHellhound extends EntityWolf implements IMob
{
    public EntityHellhound(World world)
    {
        super(world);
        this.setSize(1.0F, 1.3F);
        this.setAngry(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    @Override
    protected void initEntityAI()
    {
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new AIAvoidEntity(this, EntityLlama.class, 24.0F, 1.5D, 1.5D));
        this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(9, new EntityAIBeg(this, 8.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(5, new EntityAITargetNonTamed(this, EntityCreature.class, false, (Predicate<Entity>) entity -> !(entity instanceof IMob)));
        this.targetTasks.addTask(6, new EntityAINearestAttackableTarget(this, AbstractSkeleton.class, false));
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }
    }

    @Override
    public void setTamed(boolean tamed)
    {
        super.setTamed(tamed);

        if(tamed)
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        }
        else
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        }

        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack)
    {
        return false;
    }

    class AIAvoidEntity<T extends Entity> extends EntityAIAvoidEntity<T>
    {
        private final EntityHellhound hellhound;

        public AIAvoidEntity(EntityHellhound hellhound, Class<T> clsToAvoid, float avoidDistance, double farSpeed, double nearSpeed)
        {
            super(hellhound, clsToAvoid, avoidDistance, farSpeed, nearSpeed);
            this.hellhound = hellhound;
        }

        @Override
        public boolean shouldExecute()
        {
            if(super.shouldExecute() && this.closestLivingEntity instanceof EntityLlama)
            {
                return !this.hellhound.isTamed() && this.avoidLlama((EntityLlama) this.closestLivingEntity);
            }
            else
            {
                return false;
            }
        }

        private boolean avoidLlama(EntityLlama llama)
        {
            return llama.getStrength() >= EntityHellhound.this.rand.nextInt(5);
        }

        @Override
        public void startExecuting()
        {
            EntityHellhound.this.setAttackTarget(null);
            super.startExecuting();
        }

        @Override
        public void updateTask()
        {
            EntityHellhound.this.setAttackTarget(null);
            super.updateTask();
        }
    }
}

