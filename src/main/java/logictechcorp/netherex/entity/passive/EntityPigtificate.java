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

package logictechcorp.netherex.entity.passive;

import logictechcorp.libraryex.village.ITrader;
import logictechcorp.libraryex.village.Trade;
import logictechcorp.netherex.entity.ai.*;
import logictechcorp.netherex.init.*;
import logictechcorp.netherex.village.PigtificateProfession;
import logictechcorp.netherex.village.PigtificateVillage;
import logictechcorp.netherex.village.PigtificateVillageManager;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class EntityPigtificate extends EntityAgeable implements ITrader
{
    private PigtificateProfession profession;
    private PigtificateProfession.Career career;
    private int careerLevel;
    private int tickCounter;
    private int timeUntilRestock;

    private PigtificateVillage village;

    private boolean needsInitialization;
    private boolean willingToMate;
    private boolean mating;
    private boolean playing;
    private boolean additionalTasksSet;
    private boolean lookingForHome;

    private EntityPlayer customer;
    private MerchantRecipeList trades;
    private UUID lastCustomer;

    private final InventoryBasic inventory;

    public EntityPigtificate(World world)
    {
        super(world);
        this.inventory = new InventoryBasic("Items", false, 8);
        this.isImmuneToFire = true;
        this.setCanPickUpLoot(true);
        this.setSize(0.6F, 1.95F);
    }

    public EntityPigtificate(World world, PigtificateProfession profession)
    {
        super(world);
        this.profession = profession;
        this.inventory = new InventoryBasic("Items", false, 8);
        this.isImmuneToFire = true;
        this.setCanPickUpLoot(true);
        this.setSize(0.6F, 1.95F);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(1, new EntityAIPigtificateTradePlayer(this));
        this.tasks.addTask(1, new EntityAIPigtificateLookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveInFenceGates(this));
        this.tasks.addTask(3, new EntityAIRestrictFenceGateUse(this));
        this.tasks.addTask(4, new EntityAIUseFenceGate(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(6, new EntityAIPigtificateMate(this));
        this.tasks.addTask(7, new EntityAIPigtificateFollowGoldGolem(this));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(9, new EntityAIPigtificateInteract(this));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    @Override
    protected void updateAITasks()
    {
        if(this.tickCounter-- <= 0)
        {
            BlockPos blockPos = new BlockPos(this);
            PigtificateVillageManager.getVillageData(this.world, true).addPigtificate(blockPos);
            this.tickCounter = 70 + this.rand.nextInt(50);
            this.village = PigtificateVillageManager.getVillageData(this.world, true).getNearestVillage(blockPos, 32);

            if(this.village == null)
            {
                this.detachHome();
            }
            else
            {
                BlockPos villagePos = this.village.getCenter();
                this.setHomePosAndDistance(villagePos, this.village.getRadius());

                if(this.lookingForHome)
                {
                    this.lookingForHome = false;
                    this.village.setDefaultPlayerReputation(5);
                }
            }
        }

        if(!this.isTrading() && this.timeUntilRestock > 0)
        {
            this.timeUntilRestock--;

            if(this.timeUntilRestock <= 0)
            {
                if(this.needsInitialization)
                {
                    for(MerchantRecipe trade : this.trades)
                    {
                        if(trade.isRecipeDisabled())
                        {
                            trade.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                        }
                    }

                    this.populateTradeList();
                    this.needsInitialization = false;

                    if(this.village != null && this.lastCustomer != null)
                    {
                        this.world.setEntityState(this, (byte) 14);
                        this.village.modifyPlayerReputation(this.lastCustomer, 1);
                    }
                }

                this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0));
            }
        }

        super.updateAITasks();
    }

    @Override
    protected void updateEquipmentIfNeeded(EntityItem item)
    {
        ItemStack stack = item.getItem();

        if(this.canPickupItem(stack.getItem()))
        {
            ItemStack modifiedStack = this.inventory.addItem(stack);

            if(modifiedStack.isEmpty())
            {
                item.setDead();
            }
            else
            {
                stack.setCount(modifiedStack.getCount());
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setString("Profession", this.career.getProfession().getName().toString());
        compound.setInteger("Career", this.career.getId());
        compound.setInteger("CareerLevel", this.careerLevel);
        compound.setBoolean("Willing", this.willingToMate);

        if(this.trades != null)
        {
            compound.setTag("Trades", this.trades.getRecipiesAsTags());
        }

        NBTTagList list = new NBTTagList();

        for(int i = 0; i < this.inventory.getSizeInventory(); i++)
        {
            ItemStack stack = this.inventory.getStackInSlot(i);

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
        this.setProfession(NetherExRegistries.PIGTIFICATE_PROFESSIONS.getValue(new ResourceLocation(compound.getString("Profession"))));
        this.setCareer(this.profession.getCareer(compound.getInteger("Career")));
        this.setCareerLevel(compound.getInteger("CareerLevel"));
        this.setWillingToMate(compound.getBoolean("Willing"));

        if(compound.hasKey("Trades", 10))
        {
            NBTTagCompound trades = compound.getCompoundTag("Trades");
            this.trades = new MerchantRecipeList(trades);
        }

        NBTTagList list = compound.getTagList("Inventory", 10);

        for(int i = 0; i < list.tagCount(); i++)
        {
            ItemStack stack = new ItemStack(list.getCompoundTagAt(i));

            if(!stack.isEmpty())
            {
                this.inventory.addItem(stack);
            }
        }

        this.setCanPickUpLoot(true);
    }

    @Override
    protected PathNavigate createNavigator(World world)
    {
        return new PathNavigatePigtificate(this, world);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingData)
    {
        this.setAdditionalAITasks();
        this.populateTradeList();
        return super.onInitialSpawn(difficulty, livingData);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        EntityPigtificate profession = new EntityPigtificate(this.world);
        profession.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(profession)), null);
        return profession;
    }

    @Override
    public void onDeath(DamageSource source)
    {
        if(this.village != null)
        {
            Entity entity = source.getTrueSource();

            if(entity != null)
            {
                if(entity instanceof EntityPlayer)
                {
                    this.village.modifyPlayerReputation(entity.getUniqueID(), -2);
                }
                else if(entity instanceof IMob)
                {
                    this.village.endMatingSeason();
                }
            }
            else
            {
                EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 16.0D);

                if(entityplayer != null)
                {
                    this.village.endMatingSeason();
                }
            }
        }

        super.onDeath(source);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if(id == 12)
        {
            this.spawnParticles(EnumParticleTypes.HEART);
        }
        else if(id == 13)
        {
            this.spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
        }
        else if(id == 14)
        {
            this.spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticles(EnumParticleTypes particleType)
    {
        for(int i = 0; i < 5; i++)
        {
            double x = this.rand.nextGaussian() * 0.02D;
            double y = this.rand.nextGaussian() * 0.02D;
            double z = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(particleType, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + 1.0D + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, x, y, z);
        }
    }

    @Override
    public void useRecipe(MerchantRecipe recipe)
    {
        recipe.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        int i = 3 + this.rand.nextInt(4);

        if(recipe.getToolUses() == 1 || this.rand.nextInt(5) == 0)
        {
            this.timeUntilRestock = 40;
            this.needsInitialization = true;
            this.willingToMate = true;

            if(this.getCustomer() != null)
            {
                this.lastCustomer = this.getCustomer().getUniqueID();
            }
            else
            {
                this.lastCustomer = null;
            }

            i += 5;
        }
        if(recipe.getRewardsExp())
        {
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY + 0.5D, this.posZ, i));
        }
    }

    @Override
    public void verifySellingItem(ItemStack stack)
    {

    }

    private void populateTradeList()
    {
        if(this.careerLevel != 0)
        {
            this.setCareerLevel(this.careerLevel + 1);
        }
        else
        {
            this.career = this.profession.getRandomCareer(this.rand);
            this.careerLevel = 1;
        }

        if(this.trades == null)
        {
            this.trades = new MerchantRecipeList();
        }

        List<Trade> trades = this.career.getTrades(this.careerLevel);

        if(trades != null && trades.size() > 0)
        {
            Collections.shuffle(trades, this.rand);

            if(this.careerLevel == 1 && trades.size() > 1)
            {
                for(Trade trade : trades.subList(0, 2))
                {
                    this.trades.add(trade.randomize(this.rand));
                }
            }
            else
            {
                this.trades.add(trades.get(0).randomize(this.rand));
            }
        }
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
        else if(!this.holdingSpawnEggOfClass(stack, this.getClass()) && this.isEntityAlive() && !this.isTrading() && !this.isChild())
        {
            if(this.trades == null)
            {
                this.populateTradeList();
            }

            if(!this.world.isRemote && !this.trades.isEmpty())
            {
                this.setCustomer(player);
                player.displayVillagerTradeGui(this);
            }
            else if(this.trades.isEmpty())
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
    public boolean replaceItemInInventory(int slot, ItemStack stack)
    {
        if(super.replaceItemInInventory(slot, stack))
        {
            return true;
        }
        else
        {
            int i = slot - 300;

            if(i >= 0 && i < this.inventory.getSizeInventory())
            {
                this.inventory.setInventorySlotContents(i, stack);
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    private boolean canPickupItem(Item item)
    {
        return item == Item.getItemFromBlock(NetherExBlocks.BROWN_ELDER_MUSHROOM) || item == Item.getItemFromBlock(NetherExBlocks.RED_ELDER_MUSHROOM) || item == NetherExItems.ENOKI_MUSHROOM;
    }

    public boolean canAbandonItems()
    {
        return this.hasEnoughItems(2);
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

    private boolean hasEnoughItems(int multiplier)
    {
        for(int i = 0; i < this.inventory.getSizeInventory(); i++)
        {
            ItemStack stack = this.inventory.getStackInSlot(i);

            if(!stack.isEmpty())
            {
                if(((stack.getItem() == Item.getItemFromBlock(NetherExBlocks.BROWN_ELDER_MUSHROOM) || stack.getItem() == Item.getItemFromBlock(NetherExBlocks.RED_ELDER_MUSHROOM)) && stack.getCount() >= 4 * multiplier) || (stack.getItem() == NetherExItems.ENOKI_MUSHROOM && stack.getCount() >= 32 * multiplier))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean wantsMoreFood()
    {
        boolean wantsMore = this.profession == NetherExPigtificates.LEADER;
        return wantsMore ? !this.hasEnoughItems(5) : !this.hasEnoughItems(1);
    }

    public boolean isTrading()
    {
        return this.customer != null;
    }

    public boolean isMating()
    {
        return this.mating;
    }

    public boolean isWillingToMate(boolean updateFirst)
    {
        if(!this.willingToMate && updateFirst && this.hasEnoughItems(1))
        {
            boolean flag = false;

            for(int i = 0; i < this.inventory.getSizeInventory(); i++)
            {
                ItemStack stack = this.inventory.getStackInSlot(i);

                if(!stack.isEmpty())
                {
                    if((stack.getItem() == Item.getItemFromBlock(NetherExBlocks.BROWN_ELDER_MUSHROOM) || stack.getItem() == Item.getItemFromBlock(NetherExBlocks.RED_ELDER_MUSHROOM)) && stack.getCount() >= 4)
                    {
                        flag = true;
                        this.inventory.decrStackSize(i, 3);
                    }
                    else if(stack.getItem() == NetherExItems.ENOKI_MUSHROOM && stack.getCount() >= 24)
                    {
                        flag = true;
                        this.inventory.decrStackSize(i, 12);
                    }
                }

                if(flag)
                {
                    this.world.setEntityState(this, (byte) 18);
                    this.willingToMate = true;
                    break;
                }
            }
        }

        return this.willingToMate;
    }

    public boolean isPlaying()
    {
        return this.playing;
    }

    @Override
    public boolean useAlternateTexture()
    {
        return false;
    }

    @Override
    public World getWorld()
    {
        return this.world;
    }

    @Override
    public BlockPos getPos()
    {
        return new BlockPos(this);
    }

    @Override
    public float getEyeHeight()
    {
        return this.isChild() ? 0.81F : 1.62F;
    }

    public PigtificateProfession.Career getCareer()
    {
        return this.career;
    }

    public InventoryBasic getInventory()
    {
        return this.inventory;
    }

    @Override
    public EntityPlayer getCustomer()
    {
        return this.customer;
    }

    @Override
    public MerchantRecipeList getRecipes(EntityPlayer player)
    {
        if(this.trades == null)
        {
            this.populateTradeList();
        }

        return this.trades;
    }

    @Override
    public String getName()
    {
        if(this.hasCustomName())
        {
            return this.getCustomNameTag();
        }
        else
        {
            String entityName = EntityList.getEntityString(this);
            return I18n.translateToLocal("entity." + entityName + "." + this.career.getName().toString().toLowerCase() + ".name");
        }
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return this.career.getLootTable();
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

    public void setProfession(PigtificateProfession profession)
    {
        this.profession = profession;
    }

    public void setCareer(PigtificateProfession.Career career)
    {
        this.career = career;
    }

    public void setCareerLevel(int i)
    {
        if(i < 0)
        {
            i = 0;
        }

        this.careerLevel = i;
    }

    private void setAdditionalAITasks()
    {
        if(!this.additionalTasksSet)
        {
            this.additionalTasksSet = true;

            if(this.isChild())
            {
                this.tasks.addTask(8, new EntityAIPigtificatePlay(this, 0.32D));
            }
        }
    }

    public void setLookingForHome()
    {
        this.lookingForHome = true;
    }

    @Override
    public void setCustomer(EntityPlayer player)
    {
        this.customer = player;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setRecipes(MerchantRecipeList tradeList)
    {

    }

    public void setWillingToMate(boolean willingToMate)
    {
        this.willingToMate = willingToMate;
    }

    public void setMating(boolean mating)
    {
        this.mating = mating;
    }

    public void setPlaying(boolean playing)
    {
        this.playing = playing;
    }

    @Override
    public void setRevengeTarget(EntityLivingBase entity)
    {
        super.setRevengeTarget(entity);

        if(this.village != null && entity != null)
        {
            this.village.setAggressor(entity);

            if(entity instanceof EntityPlayer)
            {
                int i = -1;

                if(this.isChild())
                {
                    i = -3;
                }

                this.village.modifyPlayerReputation(entity.getUniqueID(), i);

                if(this.isEntityAlive())
                {
                    this.world.setEntityState(this, (byte) 13);
                }
            }
        }
    }

    private class PathNavigatePigtificate extends PathNavigateGround
    {
        private PathNavigatePigtificate(EntityLiving entity, World world)
        {
            super(entity, world);
        }

        @Override
        protected PathFinder getPathFinder()
        {
            this.nodeProcessor = new NodeProcessorPigtificate();
            this.nodeProcessor.setCanOpenDoors(true);
            this.nodeProcessor.setCanEnterDoors(true);
            return new PathFinder(this.nodeProcessor);
        }

        private class NodeProcessorPigtificate extends WalkNodeProcessor
        {
            @Override
            protected PathNodeType getPathNodeTypeRaw(IBlockAccess world, int posX, int posY, int posZ)
            {
                BlockPos pos = new BlockPos(posX, posY, posZ);
                IBlockState state = world.getBlockState(pos);
                Block block = state.getBlock();
                Material material = state.getMaterial();

                PathNodeType type = block.getAiPathNodeType(state, world, pos);

                if(type != null)
                {
                    return type;
                }

                if(material == Material.AIR)
                {
                    return PathNodeType.OPEN;
                }
                else if(block != Blocks.TRAPDOOR && block != Blocks.IRON_TRAPDOOR && block != Blocks.WATERLILY)
                {
                    if(block == Blocks.FIRE)
                    {
                        return PathNodeType.DAMAGE_FIRE;
                    }
                    else if(block == Blocks.CACTUS)
                    {
                        return PathNodeType.DAMAGE_CACTUS;
                    }
                    else if(block instanceof BlockDoor && material == Material.WOOD && !state.getValue(BlockDoor.OPEN) || block instanceof BlockFenceGate && !state.getValue(BlockFenceGate.OPEN))
                    {
                        return PathNodeType.DOOR_WOOD_CLOSED;
                    }
                    else if(block instanceof BlockDoor && material == Material.IRON && !state.getValue(BlockDoor.OPEN))
                    {
                        return PathNodeType.DOOR_IRON_CLOSED;
                    }
                    else if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) || block instanceof BlockFenceGate && state.getValue(BlockFenceGate.OPEN))
                    {
                        return PathNodeType.DOOR_OPEN;
                    }
                    else if(block instanceof BlockRailBase)
                    {
                        return PathNodeType.RAIL;
                    }
                    else if(!(block instanceof BlockFence) && !(block instanceof BlockWall))
                    {
                        if(material == Material.WATER)
                        {
                            return PathNodeType.WATER;
                        }
                        else if(material == Material.LAVA)
                        {
                            return PathNodeType.LAVA;
                        }
                        else
                        {
                            return block.isPassable(world, pos) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
                        }
                    }
                    else
                    {
                        return PathNodeType.FENCE;
                    }
                }
                else
                {
                    return PathNodeType.TRAPDOOR;
                }
            }
        }
    }
}
