package logictechcorp.netherex.entity.passive;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.ai.EntityAIFollowOwnerRideable;
import logictechcorp.netherex.entity.ai.EntityAIOwnerHurtByTargetRideable;
import logictechcorp.netherex.entity.ai.EntityAIOwnerHurtTargetRideable;
import logictechcorp.netherex.entity.ai.EntityAITargetNonTamedRideable;
import logictechcorp.netherex.handler.GuiHandler;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.init.NetherExItems;
import logictechcorp.netherex.init.NetherExLootTables;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityBonspider extends AbstractHorse
{
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntityBonspider.class, DataSerializers.BYTE);

    public EntityBonspider(World world)
    {
        super(world);
        this.setSize(1.35F, 0.95F);
        this.canGallop = false;
        this.isImmuneToFire = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
        this.tasks.addTask(2, new EntityAIFollowParent(this, 1.0D));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIFollowOwnerRideable(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTargetRideable(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTargetRideable(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAITargetNonTamedRideable<>(this, EntityMob.class, false, null));
    }

    @Override
    protected PathNavigate createNavigator(World world)
    {
        return new PathNavigateClimber(this, world);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double) this.getModifiedMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.getModifiedMovementSpeed());
        this.getEntityAttribute(JUMP_STRENGTH).setBaseValue(this.getModifiedJumpStrength());
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CLIMBING, (byte) 0);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        if(this.world.rand.nextInt(50) == 0)
        {
            EntityWitherSkeleton skeleton = new EntityWitherSkeleton(this.world);
            skeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            skeleton.onInitialSpawn(difficulty, null);
            this.world.spawnEntity(skeleton);
            skeleton.startRiding(this);
        }

        return super.onInitialSpawn(difficulty, data);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(!this.world.isRemote)
        {
            if(this.world.checkBlockCollision(this.getEntityBoundingBox().grow(0.0625D, 0.0D, 0.0625D)))
            {
                this.setBesideClimbableBlock(true);
            }
            else
            {
                this.setBesideClimbableBlock(false);
            }
        }
        else
        {
            if(this.dataManager.isDirty())
            {
                this.dataManager.setClean();
            }
        }

        ItemStack armorStack = this.horseChest.getStackInSlot(1);

        if(this.isArmor(armorStack))
        {
            armorStack.getItem().onHorseArmorTick(this.world, this, armorStack);
        }

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        if(!this.horseChest.getStackInSlot(1).isEmpty())
        {
            compound.setTag("ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if(compound.hasKey("ArmorItem", 10))
        {
            ItemStack stack = new ItemStack(compound.getCompoundTag("ArmorItem"));

            if(!stack.isEmpty() && this.isArmor(stack))
            {
                this.horseChest.setInventorySlotContents(1, stack);
            }
        }

        if(compound.hasKey("SaddleItem", 10))
        {
            this.horseChest.setInventorySlotContents(0, new ItemStack(compound.getCompoundTag("SaddleItem")));
        }

        this.updateHorseSlots();
    }

    @Override
    public double getMountedYOffset()
    {
        return this.height - 0.6D;
    }

    @Override
    protected float getModifiedMaxHealth()
    {
        return 16.0F + (float) this.rand.nextInt(13) + (float) this.rand.nextInt(13);
    }

    @Override
    protected double getModifiedJumpStrength()
    {
        return 0.5D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D;
    }

    @Override
    protected double getModifiedMovementSpeed()
    {
        return (0.5D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.25D;
    }

    @Override
    protected boolean handleEating(EntityPlayer player, ItemStack stack)
    {
        boolean changed = false;
        float health = 0.0F;
        int growth = 0;
        int temper = 0;
        Item item = stack.getItem();

        if(item == NetherExItems.ENOKI_MUSHROOM)
        {
            health = 1.0F;
            growth = 20;
            temper = 3;
        }
        else if(item == Items.NETHER_WART)
        {
            health = 2.0F;
            growth = 30;
            temper = 3;
        }
        else if(item == Item.getItemFromBlock(NetherExBlocks.ELDER_MUSHROOM))
        {
            health = 3.0F;
            growth = 60;
            temper = 3;
        }
        else if(item == NetherExItems.CONGEALED_MAGMA_CREAM)
        {
            health = 4.0F;
            growth = 60;
            temper = 5;

            if(this.isTame() && this.getGrowingAge() == 0 && !this.isInLove())
            {
                changed = true;
                this.setInLove(player);
            }
        }
        else if(item == Item.getItemFromBlock(Blocks.NETHER_WART))
        {
            health = 10.0F;
            growth = 240;
            temper = 10;

            if(this.isTame() && this.getGrowingAge() == 0 && !this.isInLove())
            {
                changed = true;
                this.setInLove(player);
            }
        }

        if(this.getHealth() < this.getMaxHealth() && health > 0.0F)
        {
            this.heal(health);
            changed = true;
        }

        if(this.isChild() && growth > 0)
        {
            this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + 0.5D + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, 0.0D, 0.0D, 0.0D);

            if(!this.world.isRemote)
            {
                this.addGrowth(growth);
            }

            changed = true;
        }

        if(temper > 0 && (changed || !this.isTame()) && this.getTemper() < this.getMaxTemper())
        {
            changed = true;

            if(!this.world.isRemote)
            {
                this.increaseTemper(temper);
            }
        }

        return changed;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.BONSPIDER;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        boolean flag = !stack.isEmpty();

        if(flag && stack.getItem() == Items.SPAWN_EGG)
        {
            return super.processInteract(player, hand);
        }
        else
        {
            if(!this.isChild())
            {
                if(this.isTame() && player.isSneaking())
                {
                    this.openGUI(player);
                    return true;
                }

                if(this.isBeingRidden())
                {
                    return super.processInteract(player, hand);
                }
            }

            if(flag)
            {
                if(this.handleEating(player, stack))
                {
                    if(!player.capabilities.isCreativeMode)
                    {
                        stack.shrink(1);
                    }

                    return true;
                }

                if(stack.interactWithEntity(player, this, hand))
                {
                    return true;
                }

                if(!this.isTame())
                {
                    this.makeMad();
                    return true;
                }
            }

            if(this.isChild())
            {
                return super.processInteract(player, hand);
            }
            else
            {
                this.mountTo(player);
                return true;
            }
        }
    }

    @Override
    public void openGUI(EntityPlayer player)
    {
        if(!this.world.isRemote && (!this.isBeingRidden() || this.isPassenger(player)) && this.isTame())
        {
            this.horseChest.setCustomName(this.getName());
            player.openGui(NetherEx.instance, GuiHandler.BONSPIDER_GUI_ID, this.world, (int) this.posX, (int) this.posY, (int) this.posZ);
        }
    }

    @Override
    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        if(otherAnimal == this)
        {
            return false;
        }
        else if(!this.isTame())
        {
            return false;
        }
        else if(!(otherAnimal instanceof EntityBonspider))
        {
            return false;
        }
        else
        {
            EntityBonspider bonspider = (EntityBonspider) otherAnimal;

            if(!bonspider.isTame())
            {
                return false;
            }
            else
            {
                return this.isInLove() && bonspider.isInLove();
            }
        }

    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        EntityBonspider bonspider = new EntityBonspider(this.world);
        this.setOffspringAttributes(ageable, bonspider);
        return bonspider;
    }

    @Override
    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    @Override
    public void setInWeb()
    {
    }

    @Override
    public boolean canBePushed()
    {
        return !this.isBeingRidden();
    }

    @Override
    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
        int damage = MathHelper.ceil((distance * 0.5F - 3.0F) * damageMultiplier);

        if(damage > 0)
        {
            this.attackEntityFrom(DamageSource.FALL, (float) damage);

            if(this.isBeingRidden())
            {
                for(Entity entity : this.getRecursivePassengers())
                {
                    entity.attackEntityFrom(DamageSource.FALL, (float) damage);
                }
            }

            IBlockState state = this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - (double) this.prevRotationYaw, this.posZ));
            Block block = state.getBlock();

            if(state.getMaterial() != Material.AIR && !this.isSilent())
            {
                SoundType blockSoundType = block.getSoundType();
                this.world.playSound(null, this.posX, this.posY, this.posZ, blockSoundType.getStepSound(), this.getSoundCategory(), blockSoundType.getVolume() * 0.5F, blockSoundType.getPitch() * 0.75F);
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if(flag)
        {
            this.applyEnchantments(this, entity);
        }

        return flag;
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 8;
    }

    public boolean isBesideClimbableBlock()
    {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing)
    {
        byte flag = this.dataManager.get(CLIMBING);

        if(climbing)
        {
            flag = (byte) (flag | 1);
        }
        else
        {
            flag = (byte) (flag & -2);
        }

        this.dataManager.set(CLIMBING, flag);
    }
}
