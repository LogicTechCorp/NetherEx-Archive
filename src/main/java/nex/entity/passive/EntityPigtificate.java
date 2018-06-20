/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

import lex.village.Trade;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.entity.ai.*;
import nex.init.NetherExBlocks;
import nex.init.NetherExItems;
import nex.init.NetherExSoundEvents;
import nex.village.NetherExVillage;
import nex.village.NetherExVillageManager;
import nex.village.Pigtificate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public class EntityPigtificate extends EntityAgeable implements INpc, IMerchant
{
    private static final DataParameter<Integer> CAREER = EntityDataManager.createKey(EntityPigtificate.class, DataSerializers.VARINT);

    private int profession;
    private int careerLevel;

    private int randomTickDivider;

    NetherExVillage village;

    private boolean needsInitialization;
    private boolean willingToMate;
    private boolean mating;
    private boolean playing;
    private boolean additionalTasksSet;
    private boolean lookingForHome;

    private int timeUntilRestock;

    private EntityPlayer customer;
    private MerchantRecipeList tradeList;
    private UUID lastCustomer;

    private final InventoryBasic inventory;

    public EntityPigtificate(World world)
    {
        super(world);

        inventory = new InventoryBasic("Items", false, 8);
        isImmuneToFire = true;
        ((PathNavigateGround) getNavigator()).setBreakDoors(true);
        setCanPickUpLoot(true);
        setSize(0.6F, 1.95F);
        setRandomProfession();
    }

    public EntityPigtificate(World world, Pigtificate.Career career)
    {
        super(world);

        inventory = new InventoryBasic("Items", false, 8);
        isImmuneToFire = true;
        ((PathNavigateGround) getNavigator()).setBreakDoors(true);
        setCanPickUpLoot(true);
        setSize(0.6F, 1.95F);
        setProfession(career.getProfession().ordinal());
        setCareer(career.ordinal());
    }


    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSoundEvents.PIGTIFICATE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSoundEvents.PIGTIFICATE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.PIGTIFICATE_DEATH;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if(id == 12)
        {
            spawnParticles(EnumParticleTypes.HEART);
        }
        else if(id == 13)
        {
            spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
        }
        else if(id == 14)
        {
            spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticles(EnumParticleTypes particleType)
    {
        for(int i = 0; i < 5; ++i)
        {
            double d0 = rand.nextGaussian() * 0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            world.spawnParticle(particleType, posX + (double) (rand.nextFloat() * width * 2.0F) - (double) width, posY + 1.0D + (double) (rand.nextFloat() * height), posZ + (double) (rand.nextFloat() * width * 2.0F) - (double) width, d0, d1, d2, new int[0]);
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingData)
    {
        setAdditionalAITasks();
        populateTradeList();
        return super.onInitialSpawn(difficulty, livingData);
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        tasks.addTask(1, new EntityAIPigtificateTradePlayer(this));
        tasks.addTask(1, new EntityAIPigtificateLookAtTradePlayer(this));
        tasks.addTask(2, new EntityAIMoveInFenceGates(this));
        tasks.addTask(3, new EntityAIRestrictFenceGateUse(this));
        tasks.addTask(4, new EntityAIUseFenceGate(this, true));
        tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        tasks.addTask(6, new EntityAIPigtificateMate(this));
        tasks.addTask(7, new EntityAIPigtificateFollowGoldGolem(this));
        tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        tasks.addTask(9, new EntityAIPigtificateInteract(this));
        tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
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
        dataManager.register(CAREER, 0);
    }

    @Override
    public float getEyeHeight()
    {
        return isChild() ? 0.81F : 1.62F;
    }

    @Override
    protected void updateAITasks()
    {
        if(randomTickDivider-- <= 0)
        {
            BlockPos blockpos = new BlockPos(this);
            NetherExVillageManager.getNetherVillages(getWorld(), true).addToVillagerPositionList(blockpos);
            randomTickDivider = 70 + rand.nextInt(50);
            village = NetherExVillageManager.getNetherVillages(getWorld(), true).getNearestVillage(blockpos, 32);

            if(village == null)
            {
                detachHome();
            }
            else
            {
                BlockPos blockpos1 = village.getCenter();
                setHomePosAndDistance(blockpos1, village.getVillageRadius());

                if(lookingForHome)
                {
                    lookingForHome = false;
                    village.setDefaultPlayerReputation(5);
                }
            }
        }

        if(!isTrading() && timeUntilRestock > 0)
        {
            --timeUntilRestock;

            if(timeUntilRestock <= 0)
            {
                if(needsInitialization)
                {
                    for(MerchantRecipe merchantrecipe : tradeList)
                    {
                        if(merchantrecipe.isRecipeDisabled())
                        {
                            merchantrecipe.increaseMaxTradeUses(rand.nextInt(6) + rand.nextInt(6) + 2);
                        }
                    }

                    populateTradeList();
                    needsInitialization = false;

                    if(village != null && lastCustomer != null)
                    {
                        world.setEntityState(this, (byte) 14);
                        village.modifyPlayerReputation(lastCustomer, 1);
                    }
                }

                addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0));
            }
        }

        super.updateAITasks();
    }

    @Override
    protected void updateEquipmentIfNeeded(EntityItem itemEntity)
    {
        ItemStack stack = itemEntity.getItem();
        Item item = stack.getItem();

        if(canPickupItem(item))
        {
            ItemStack stack1 = inventory.addItem(stack);

            if(stack1.isEmpty())
            {
                itemEntity.setDead();
            }
            else
            {
                stack.setCount(stack1.getCount());
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Profession", getProfession());
        compound.setInteger("Career", getCareer());
        compound.setInteger("CareerLevel", getCareerLevel());
        compound.setBoolean("Willing", willingToMate);

        if(tradeList != null)
        {
            compound.setTag("Trades", tradeList.getRecipiesAsTags());
        }

        NBTTagList list = new NBTTagList();

        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack stack = inventory.getStackInSlot(i);

            if(!stack.isEmpty())
            {
                list.appendTag(stack.writeToNBT(new NBTTagCompound()));
            }
        }

        compound.setTag("Inventory", list);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setProfession(compound.getInteger("Profession"));
        setCareer(compound.getInteger("Career"));
        setCareerLevel(compound.getInteger("CareerLevel"));
        setWillingToMate(compound.getBoolean("Willing"));

        if(compound.hasKey("Trades", 10))
        {
            NBTTagCompound trades = compound.getCompoundTag("Trades");
            tradeList = new MerchantRecipeList(trades);
        }

        NBTTagList list = compound.getTagList("Inventory", 10);

        for(int i = 0; i < list.tagCount(); ++i)
        {
            ItemStack stack = new ItemStack(list.getCompoundTagAt(i));

            if(!stack.isEmpty())
            {
                inventory.addItem(stack);
            }
        }

        setCanPickUpLoot(true);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(stack.getItem() == Items.NAME_TAG)
        {
            stack.interactWithEntity(player, this, hand);
            return true;
        }
        else if(!holdingSpawnEggOfClass(stack, getClass()) && isEntityAlive() && !isTrading() && !isChild())
        {
            if(tradeList == null)
            {
                populateTradeList();
            }

            if(!world.isRemote && !tradeList.isEmpty())
            {
                setCustomer(player);
                player.displayVillagerTradeGui(this);
            }
            else if(tradeList.isEmpty())
            {
                return super.processInteract(player, hand);
            }

            return true;
        }
        else
        {
            return super.processInteract(player, hand);
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        EntityPigtificate pigtificate = new EntityPigtificate(world);
        pigtificate.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(pigtificate)), null);
        return pigtificate;
    }

    @Override
    public void setRevengeTarget(EntityLivingBase livingBase)
    {
        super.setRevengeTarget(livingBase);

        if(village != null && livingBase != null)
        {
            village.addOrRenewAggressor(livingBase);

            if(livingBase instanceof EntityPlayer)
            {
                int i = -1;

                if(isChild())
                {
                    i = -3;
                }

                village.modifyPlayerReputation(livingBase.getUniqueID(), i);

                if(isEntityAlive())
                {
                    world.setEntityState(this, (byte) 13);
                }
            }
        }
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        if(village != null)
        {
            Entity entity = cause.getTrueSource();

            if(entity != null)
            {
                if(entity instanceof EntityPlayer)
                {
                    village.modifyPlayerReputation(entity.getUniqueID(), -2);
                }
                else if(entity instanceof IMob)
                {
                    village.endMatingSeason();
                }
            }
            else
            {
                EntityPlayer entityplayer = world.getClosestPlayerToEntity(this, 16.0D);

                if(entityplayer != null)
                {
                    village.endMatingSeason();
                }
            }
        }

        super.onDeath(cause);
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
    public boolean replaceItemInInventory(int inventorySlot, ItemStack stack)
    {
        if(super.replaceItemInInventory(inventorySlot, stack))
        {
            return true;
        }
        else
        {
            int i = inventorySlot - 300;

            if(i >= 0 && i < inventory.getSizeInventory())
            {
                inventory.setInventorySlotContents(i, stack);
                return true;
            }
            else
            {
                return false;
            }
        }
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
            return I18n.translateToLocal("entity." + entityName + "." + Pigtificate.Career.getFromIndex(getCareer()).name().toLowerCase() + ".name");
        }
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return Pigtificate.Career.getFromIndex(getCareer()).getLootTable();
    }

    @Override
    public void setCustomer(EntityPlayer player)
    {
        customer = player;
    }

    @Override
    public EntityPlayer getCustomer()
    {
        return customer;
    }

    @Override
    public MerchantRecipeList getRecipes(EntityPlayer player)
    {
        if(tradeList == null)
        {
            populateTradeList();
        }

        return tradeList;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setRecipes(MerchantRecipeList tradeList)
    {

    }

    @Override
    public void useRecipe(MerchantRecipe recipe)
    {
        recipe.incrementToolUses();
        livingSoundTime = -getTalkInterval();
        int i = 3 + rand.nextInt(4);

        if(recipe.getToolUses() == 1 || rand.nextInt(5) == 0)
        {
            timeUntilRestock = 40;
            needsInitialization = true;
            willingToMate = true;

            if(getCustomer() != null)
            {
                lastCustomer = getCustomer().getUniqueID();
            }
            else
            {
                lastCustomer = null;
            }

            i += 5;
        }
        if(recipe.getRewardsExp())
        {
            world.spawnEntity(new EntityXPOrb(world, posX, posY + 0.5D, posZ, i));
        }
    }

    @Override
    public void verifySellingItem(ItemStack stack)
    {

    }

    @Override
    public World getWorld()
    {
        return world;
    }

    @Override
    public BlockPos getPos()
    {
        return new BlockPos(this);
    }

    private void populateTradeList()
    {
        if(getCareerLevel() != 0)
        {
            setCareerLevel(getCareerLevel() + 1);
        }
        else
        {
            setCareerLevel(1);
        }

        if(tradeList == null)
        {
            tradeList = new MerchantRecipeList();
        }

        List<Trade> trades = Pigtificate.Career.getFromIndex(getCareer()).getTrades().get(getCareerLevel());

        if(trades != null && trades.size() > 0)
        {
            Collections.shuffle(trades, rand);

            if(getCareerLevel() == 1 && trades.size() > 1)
            {
                for(Trade trade : trades.subList(0, 2))
                {
                    tradeList.add(trade.randomize());
                }
            }
            else
            {
                tradeList.add(trades.get(0).randomize());
            }
        }
    }

    public boolean isTrading()
    {
        return customer != null;
    }

    public boolean isMating()
    {
        return mating;
    }

    private boolean canPickupItem(Item itemIn)
    {
        return itemIn == Item.getItemFromBlock(NetherExBlocks.ELDER_MUSHROOM) || itemIn == NetherExItems.ENOKI_MUSHROOM;
    }

    private boolean hasEnoughItems(int multiplier)
    {
        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack stack = inventory.getStackInSlot(i);

            if(!stack.isEmpty())
            {
                if((stack.getItem() == Item.getItemFromBlock(NetherExBlocks.ELDER_MUSHROOM) && stack.getCount() >= 4 * multiplier) || (stack.getItem() == NetherExItems.ENOKI_MUSHROOM && stack.getCount() >= 32 * multiplier))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean canAbandonItems()
    {
        return hasEnoughItems(2);
    }

    public boolean wantsMoreFood()
    {
        boolean flag = getProfession() == 0;
        return flag ? !hasEnoughItems(5) : !hasEnoughItems(1);
    }

    public int getProfession()
    {
        return profession;
    }

    public int getCareer()
    {
        return dataManager.get(CAREER);
    }

    public int getCareerLevel()
    {
        return careerLevel;
    }

    public boolean getWillingToMate(boolean updateFirst)
    {
        if(!willingToMate && updateFirst && hasEnoughItems(1))
        {
            boolean flag = false;

            for(int i = 0; i < inventory.getSizeInventory(); ++i)
            {
                ItemStack stack = inventory.getStackInSlot(i);

                if(!stack.isEmpty())
                {
                    if(stack.getItem() == Item.getItemFromBlock(NetherExBlocks.ELDER_MUSHROOM) && stack.getCount() >= 4)
                    {
                        flag = true;
                        inventory.decrStackSize(i, 3);
                    }
                    else if(stack.getItem() == NetherExItems.ENOKI_MUSHROOM && stack.getCount() >= 24)
                    {
                        flag = true;
                        inventory.decrStackSize(i, 12);
                    }
                }

                if(flag)
                {
                    world.setEntityState(this, (byte) 18);
                    willingToMate = true;
                    break;
                }
            }
        }

        return willingToMate;
    }

    public boolean isPlaying()
    {
        return playing;
    }

    public InventoryBasic getInventory()
    {
        return inventory;
    }

    protected void setRandomProfession()
    {
        setProfession(Pigtificate.Profession.getRandom(rand, false).ordinal());
        setRandomCareer();
    }

    protected void setRandomCareer()
    {
        setCareer(Pigtificate.Career.getRandomCareer(Pigtificate.Profession.getFromIndex(getProfession()), rand).ordinal());
    }

    public void setProfession(int i)
    {
        if(i < 0)
        {
            i = 0;
        }
        else if(i > Pigtificate.Profession.values().length)
        {
            i = Pigtificate.Profession.values().length;
        }

        profession = i;
    }

    public void setCareer(int i)
    {
        if(i < 0)
        {
            i = 0;
        }

        dataManager.set(CAREER, i);
    }

    public void setCareerLevel(int i)
    {
        if(i < 0)
        {
            i = 0;
        }

        careerLevel = i;
    }

    private void setAdditionalAITasks()
    {
        if(!additionalTasksSet)
        {
            additionalTasksSet = true;

            if(isChild())
            {
                tasks.addTask(8, new EntityAIPigtificatePlay(this, 0.32D));
            }
        }
    }

    public void setWillingToMate(boolean willingToMateIn)
    {
        willingToMate = willingToMateIn;
    }

    public void setMating(boolean matingIn)
    {
        mating = matingIn;
    }

    public void setPlaying(boolean playingIn)
    {
        playing = playingIn;
    }
}
