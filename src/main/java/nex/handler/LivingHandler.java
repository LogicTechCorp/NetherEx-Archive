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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;
import nex.entity.item.EntityObsidianBoat;
import nex.entity.monster.EntityGhastling;
import nex.entity.monster.EntitySpore;
import nex.entity.passive.EntityPigtificate;
import nex.init.NetherExBiomes;
import nex.init.NetherExEffects;
import nex.init.NetherExItems;
import nex.init.NetherExMaterials;
import nex.util.ArmorHelper;
import nex.util.EntityHelper;

import java.util.ListIterator;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class LivingHandler
{
    @SubscribeEvent
    public static void onLivingSpecialSpawn(LivingSpawnEvent.SpecialSpawn event)
    {
        World world = event.getWorld();
        BlockPos pos = new BlockPos(event.getX(), event.getY(), event.getZ());
        EntityLivingBase entity = event.getEntityLiving();

        if(world.getBiome(pos) == NetherExBiomes.ARCTIC_ABYSS)
        {
            if(EntityHelper.canFreeze(entity))
            {
                entity.addPotionEffect(new PotionEffect(NetherExEffects.FREEZE, 300, 0));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        World world = event.getEntityLiving().getEntityWorld();
        BlockPos pos = new BlockPos(event.getEntityLiving());
        EntityLivingBase entity = event.getEntityLiving();
        Random rand = world.rand;

        boolean canEntityFreeze = (entity instanceof EntityPlayer && !((EntityPlayer) entity).isCreative() && ConfigHandler.biomeConfig.arcticAbyss.canPlayersFreeze) || EntityHelper.canFreeze(entity);

        if(canEntityFreeze)
        {
            boolean entityFrozen = EntityHelper.isFrozen(entity);

            if(!entityFrozen && world.rand.nextInt(ConfigHandler.biomeConfig.arcticAbyss.chanceOfFreezing) == 0 && world.getBiome(pos) == NetherExBiomes.ARCTIC_ABYSS)
            {
                entity.addPotionEffect(new PotionEffect(NetherExEffects.FREEZE, 300, 0));
            }
            if(entityFrozen)
            {
                if(entity instanceof EntityPlayer)
                {
                    entity.setPosition(entity.prevPosX, entity.posY, entity.prevPosZ);
                }
                if(world.rand.nextInt(ConfigHandler.potionEffectConfig.freeze.chanceOfThawing) == 0)
                {
                    entity.removePotionEffect(NetherExEffects.FREEZE);
                }
            }
        }
        if(EntityHelper.isSporeInfested(entity) && EntityHelper.canSpreadSpores(entity) && world.rand.nextInt(ConfigHandler.potionEffectConfig.spore.chanceOfSporeSpawning) == 0)
        {
            if(world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).expand(1, 1, 1)).size() < 3)
            {
                BlockPos newPos = pos.offset(EnumFacing.Plane.HORIZONTAL.random(world.rand));

                if(!world.isRemote && world.isAirBlock(newPos) && world.getBlockState(newPos.down()).isSideSolid(world, newPos.down(), EnumFacing.UP))
                {
                    EntitySpore spore = new EntitySpore(world, 0);
                    spore.setPosition(newPos.getX(), newPos.getY(), newPos.getZ());
                    world.spawnEntity(spore);
                }
            }
        }
        if(EntityHelper.isLostAfflicted(entity) && EntityHelper.canSpawnGhastling(entity) && world.rand.nextInt(ConfigHandler.potionEffectConfig.lost.chanceOfGhastlingSpawning) == 0)
        {
            BlockPos newPos = pos.add(0, 5, 0).offset(entity.getHorizontalFacing().getOpposite(), 5);

            if(!world.isRemote && world.isAirBlock(newPos))
            {
                EntityGhastling ghastling = new EntityGhastling(world);
                ghastling.setPosition(newPos.getX(), newPos.getY(), newPos.getZ());
                ghastling.setAttackTarget(entity);
                world.spawnEntity(ghastling);
            }
        }
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
            event.getDrops().add(new EntityItem(event.getEntity().getEntityWorld(), deathPoint.getX(), deathPoint.getY(), deathPoint.getZ(), new ItemStack(NetherExItems.GHAST_MEAT_RAW, rand.nextInt(3) + 1, 0)));
        }
        else if(event.getEntity() instanceof EntityWitherSkeleton)
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
