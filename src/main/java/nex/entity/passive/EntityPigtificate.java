/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.trade.TradeCareer;
import nex.trade.TradeListManager;
import nex.trade.TradeProfession;

import java.util.List;

public class EntityPigtificate extends EntityAgeable implements INpc, IMerchant
{
    private static final DataParameter<Integer> PROFESSION = EntityDataManager.createKey(EntityPigtificate.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> CAREER = EntityDataManager.createKey(EntityPigtificate.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> CAREER_LEVEL = EntityDataManager.createKey(EntityPigtificate.class, DataSerializers.VARINT);

    private boolean needsInitialization;

    private int timeUntilRestock;

    private EntityPlayer customer;
    private MerchantRecipeList tradeList;
    private String lastCustomer;

    public EntityPigtificate(World world)
    {
        super(world);

        setSize(0.6F, 2.2F);
        setRandomProfession();
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        tasks.addTask(2, new EntityAIWander(this, 0.6D));
        tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.6D));
        tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
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
        dataManager.register(PROFESSION, 0);
        dataManager.register(CAREER, 0);
        dataManager.register(CAREER_LEVEL, 0);
    }

    @Override
    protected void updateAITasks()
    {
        if(!hasHome())
        {
            setHomePosAndDistance(new BlockPos(this), 48);
        }

        if(!isTrading() && timeUntilRestock > 0)
        {
            --timeUntilRestock;

            if(timeUntilRestock <= 0)
            {
                if(needsInitialization)
                {
                    for(MerchantRecipe trade : tradeList)
                    {
                        if(trade.isRecipeDisabled())
                        {
                            trade.increaseMaxTradeUses(rand.nextInt(6) + rand.nextInt(6) + 2);
                        }
                    }

                    populateTradeList();
                    needsInitialization = false;
                }

                addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0));
            }
        }

        super.updateAITasks();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Profession", getProfession());
        compound.setInteger("Career", getCareer());
        compound.setInteger("CareerLevel", getCareerLevel());

        if(tradeList != null)
        {
            compound.setTag("Trades", tradeList.getRecipiesAsTags());
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setProfession(compound.getInteger("Profession"));
        setCareer(compound.getInteger("Career"));
        setCareerLevel(compound.getInteger("CareerLevel"));

        if(compound.hasKey("Trades", 10))
        {
            NBTTagCompound trades = compound.getCompoundTag("Trades");
            tradeList = new MerchantRecipeList(trades);
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
    public String getName()
    {
        if(hasCustomName())
        {
            return getCustomNameTag();
        }
        else
        {
            String entityName = EntityList.getEntityString(this);
            return I18n.format("entity." + entityName + "." + TradeCareer.EnumType.fromIndex(getCareer()).name().toLowerCase() + ".name");
        }
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return TradeCareer.EnumType.fromIndex(getCareer()).getLootTable();
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

            if(getCustomer() != null)
            {
                lastCustomer = getCustomer().getName();
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
        if(getCareer() != 0 && getCareerLevel() != 0)
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

        List<MerchantRecipe> trades = TradeListManager.getTrades(TradeCareer.EnumType.fromIndex(getCareer()), getCareerLevel());

        if(trades != null)
        {
            tradeList.addAll(trades);
        }
    }

    public boolean isTrading()
    {
        return customer != null;
    }

    public int getProfession()
    {
        return dataManager.get(PROFESSION);
    }

    public int getCareer()
    {
        return dataManager.get(CAREER);
    }

    public int getCareerLevel()
    {
        return dataManager.get(CAREER_LEVEL);
    }

    private void setRandomProfession()
    {
        setProfession(TradeProfession.EnumType.fromIndex(rand.nextInt(TradeProfession.EnumType.values().length)).ordinal());
        setRandomCareer();
    }

    private void setRandomCareer()
    {
        List<TradeCareer.Weighted> careers = Lists.newArrayList();

        for(TradeCareer.EnumType type : TradeCareer.EnumType.values())
        {
            if(type.getProfession() == TradeProfession.EnumType.fromIndex(getProfession()))
            {
                careers.add(new TradeCareer.Weighted(type));
            }
        }

        TradeCareer.Weighted career = WeightedRandom.getRandomItem(rand, careers);
        setCareer(career.getType().ordinal());

    }

    public void setProfession(int profession)
    {
        if(profession < 0)
        {
            profession = 0;
        }
        else if(profession > TradeProfession.EnumType.values().length)
        {
            profession = TradeProfession.EnumType.values().length;
        }

        dataManager.set(PROFESSION, profession);
    }

    public void setCareer(int career)
    {
        if(career < 0)
        {
            career = 0;
        }

        dataManager.set(CAREER, career);
    }

    public void setCareerLevel(int level)
    {
        if(level < 0)
        {
            level = 0;
        }

        dataManager.set(CAREER_LEVEL, level);
    }
}
