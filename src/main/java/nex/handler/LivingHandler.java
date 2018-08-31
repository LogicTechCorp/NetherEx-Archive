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

package nex.handler;

import lex.util.ArmorHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;
import nex.entity.item.EntityObsidianBoat;
import nex.entity.passive.EntityPigtificate;
import nex.init.NetherExEffects;
import nex.init.NetherExItems;
import nex.init.NetherExMaterials;

import java.util.ListIterator;
import java.util.Random;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class LivingHandler
{
    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        World world = event.getEntityLiving().getEntityWorld();
        BlockPos pos = new BlockPos(event.getEntityLiving());
        EntityLivingBase entity = event.getEntityLiving();
        Random rand = world.rand;

        NetherExEffects.FREEZE.performEffect(entity, 0);

        if(entity instanceof EntityPigZombie)
        {
            EntityPigZombie zombiePigman = (EntityPigZombie) entity;
            NBTTagCompound entityData = zombiePigman.getEntityData();

            if(entityData.hasKey("ConversionTime"))
            {
                int conversionTime = entityData.getInteger("ConversionTime");
                int conversionProgress = 1;

                if(rand.nextFloat() < 0.01F)
                {
                    int conversionBoosters = 0;
                    BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

                    for(int x = (int) zombiePigman.posX - 4; x < (int) zombiePigman.posX + 4 && conversionBoosters < 14; ++x)
                    {
                        for(int y = (int) zombiePigman.posY - 4; y < (int) zombiePigman.posY + 4 && conversionBoosters < 14; ++y)
                        {
                            for(int z = (int) zombiePigman.posZ - 4; z < (int) zombiePigman.posZ + 4 && conversionBoosters < 14; ++z)
                            {
                                Block block = world.getBlockState(mutableBlockPos.setPos(x, y, z)).getBlock();

                                if(block == Blocks.IRON_BARS)
                                {
                                    if(rand.nextFloat() < 0.3F)
                                    {
                                        conversionProgress++;
                                    }

                                    conversionBoosters++;
                                }
                            }
                        }
                    }
                }

                conversionTime -= conversionProgress;

                if(conversionTime <= 0)
                {
                    EntityPigtificate pigtificate = new EntityPigtificate(world);
                    pigtificate.copyLocationAndAnglesFrom(zombiePigman);
                    pigtificate.setLookingForHome();

                    if(zombiePigman.isChild())
                    {
                        pigtificate.setGrowingAge(-24000);
                    }

                    world.removeEntity(zombiePigman);
                    pigtificate.setNoAI(zombiePigman.isAIDisabled());

                    if(zombiePigman.hasCustomName())
                    {
                        pigtificate.setCustomNameTag(zombiePigman.getCustomNameTag());
                        pigtificate.setAlwaysRenderNameTag(zombiePigman.getAlwaysRenderNameTag());
                    }

                    world.spawnEntity(pigtificate);
                    pigtificate.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
                    world.playEvent(null, 1027, new BlockPos((int) zombiePigman.posX, (int) zombiePigman.posY, (int) zombiePigman.posZ), 0);
                }
                else
                {
                    entityData.setInteger("ConversionTime", conversionTime);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSetAttackTarget(LivingSetAttackTargetEvent event)
    {
        EntityLivingBase attacker = (EntityLivingBase) event.getEntity();
        EntityLivingBase attackee = event.getTarget();

        if(attackee instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) attackee;

            if(attacker instanceof AbstractSkeleton)
            {
                if(ArmorHelper.isWearingFullArmorSet(player, NetherExMaterials.WITHER_BONE))
                {
                    ((AbstractSkeleton) attacker).setAttackTarget(null);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event)
    {
        EntityLivingBase entity = (EntityLivingBase) event.getEntity();
        DamageSource source = event.getSource();

        if(source.isFireDamage())
        {
            if(!entity.isImmuneToFire() && entity.isRiding() && entity.getRidingEntity() instanceof EntityObsidianBoat)
            {
                event.setCanceled(true);
            }
        }
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;

            if(source.isFireDamage())
            {
                if(ArmorHelper.isWearingFullArmorSet(player, NetherExMaterials.SALAMANDER_HIDE))
                {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event)
    {
        Entity entity = event.getEntity();

        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;

            if(!player.isPotionActive(MobEffects.REGENERATION) && !player.isPotionActive(MobEffects.ABSORPTION) && player.isPotionActive(NetherExEffects.FROSTBITE))
            {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event)
    {
        Random rand = new Random();
        BlockPos deathPoint = event.getEntity().getPosition();

        if(event.getEntity() instanceof EntityGhast)
        {
            if(ConfigHandler.entityConfig.ghast.meatDropRarity > 0 && rand.nextInt(ConfigHandler.entityConfig.ghast.meatDropRarity) == 0)
            {
                event.getDrops().add(new EntityItem(event.getEntity().getEntityWorld(), deathPoint.getX(), deathPoint.getY(), deathPoint.getZ(), new ItemStack(NetherExItems.GHAST_MEAT_RAW, rand.nextInt(3) + 1, 0)));
            }
        }
        else if(event.getEntity() instanceof EntityWitherSkeleton)
        {
            if(ConfigHandler.entityConfig.witherSkeleton.boneDropRarity > 0 && rand.nextInt(ConfigHandler.entityConfig.witherSkeleton.boneDropRarity) == 0)
            {
                ListIterator<EntityItem> iter = event.getDrops().listIterator();

                while(iter.hasNext())
                {
                    EntityItem entityItem = iter.next();
                    ItemStack stack = entityItem.getItem();

                    if(stack.getItem() == Items.BONE || stack.getItem() == Items.COAL)
                    {
                        iter.remove();
                    }
                }

                event.getDrops().add(new EntityItem(event.getEntity().world, deathPoint.getX(), deathPoint.getY(), deathPoint.getZ(), new ItemStack(NetherExItems.WITHER_BONE, rand.nextInt(3), 0)));
            }
        }
    }
}
