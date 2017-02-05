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

package nex.handler;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.entity.item.EntityObsidianBoat;
import nex.entity.neutral.EntityMogus;
import nex.init.NetherExAchievements;
import nex.init.NetherExBlocks;
import nex.init.NetherExItems;
import nex.init.NetherExMaterials;
import nex.util.ArmorUtil;
import nex.world.NetherExTeleporter;

import java.util.ListIterator;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
public class EventHandler
{

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event)
    {
        if(!Loader.isModLoaded("netherportalfix") && ConfigHandler.Misc.enableNetherPortalFix && event.getWorld() instanceof WorldServer)
        {
            if(event.getWorld().provider.getDimension() == 0 || event.getWorld().provider.getDimension() == -1)
            {
                ((WorldServer) event.getWorld()).worldTeleporter = new NetherExTeleporter((WorldServer) event.getWorld());
            }
        }
    }

    @SubscribeEvent
    public static void onRenderBlockOverlay(RenderBlockOverlayEvent event)
    {
        RenderBlockOverlayEvent.OverlayType type = event.getOverlayType();
        EntityPlayer player = event.getPlayer();
        World world = player.getEntityWorld();
        BlockPos pos = player.getPosition();

        if(type == RenderBlockOverlayEvent.OverlayType.FIRE)
        {
            if(player.isRiding() && player.getRidingEntity() instanceof EntityObsidianBoat || ArmorUtil.isWearingFullArmorSet(player, NetherExMaterials.ARMOR_HIDE_SALAMANDER))
            {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onHoeUse(UseHoeEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = event.getCurrent();

        if(stack.getItem() == Items.GOLDEN_HOE || ConfigHandler.Misc.allowAllHoesToTillSoulSand)
        {
            if(world.getBlockState(pos).getBlock() == Blocks.SOUL_SAND)
            {
                for(EnumHand hand : EnumHand.values())
                {
                    if(player.getHeldItem(hand).getItem() instanceof ItemHoe)
                    {
                        player.swingArm(hand);
                    }
                }

                world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState(), 11);
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    @SubscribeEvent
    public static void onBoneMealUse(BonemealEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getBlock();
        EntityPlayer player = event.getEntityPlayer();

        if(player.getHeldItem(EnumHand.MAIN_HAND).getItem() == NetherExItems.ITEM_BONE_MEAL_WITHERED)
        {
            if(state.getBlock() == Blocks.NETHER_WART)
            {
                int age = state.getValue(BlockNetherWart.AGE);

                if(age < 3)
                {
                    state = state.withProperty(BlockNetherWart.AGE, age + 1);
                    world.setBlockState(pos, state);
                    event.setResult(Event.Result.ALLOW);
                }
            }
            else if(state.getBlock() instanceof IGrowable)
            {
                IGrowable growable = (IGrowable) state.getBlock();

                if(growable.canGrow(world, pos, state, world.isRemote))
                {
                    growable.grow(world, world.rand, pos, state);
                }
            }
            else
            {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onCropPreGrow(BlockEvent.CropGrowEvent.Pre event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getState();

        if(ConfigHandler.Misc.doesNetherwartUseNewGrowthSystem && state.getBlock() == Blocks.NETHER_WART)
        {
            if(world.getBlockState(pos.down()) == NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getStateFromMeta(7))
            {
                event.setResult(Event.Result.ALLOW);
            }
            else
            {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getState();

        if(!(event.getPlayer() instanceof FakePlayer))
        {
            EntityPlayer player = event.getPlayer();

            if(state.getBlock() == Blocks.MAGMA)
            {
                if(ConfigHandler.Misc.turnMagmaIntoLava)
                {
                    if(EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand()) == 0)
                    {
                        world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 3);
                        player.getHeldItemMainhand().damageItem(1, player);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCreateFluidSource(BlockEvent.CreateFluidSourceEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getState();

        if(world.provider.getDimension() == -1)
        {
            if(state.getBlock() == Blocks.LAVA || state.getBlock() == Blocks.FLOWING_LAVA)
            {
                event.setResult(ConfigHandler.Misc.isLavaInfiniteInTheNether ? Event.Result.ALLOW : Event.Result.DEFAULT);
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
                if(ArmorUtil.isWearingFullArmorSet(player, NetherExMaterials.ARMOR_BONE_WITHERED))
                {
                    ((AbstractSkeleton) attacker).setAttackTarget(null);
                    player.addStat(NetherExAchievements.IN_PLAIN_SIGHT);
                }
            }
            if(attacker instanceof EntityPigZombie)
            {
                player.addStat(NetherExAchievements.UH_OH);
            }
            if(attacker instanceof EntityMogus)
            {
                player.addStat(NetherExAchievements.CUTE_BUT_DEADLY);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event)
    {
        EntityLivingBase entity = (EntityLivingBase) event.getEntity();
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();
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
                if(ArmorUtil.isWearingFullArmorSet(player, NetherExMaterials.ARMOR_HIDE_SALAMANDER))
                {
                    event.setCanceled(true);
                }
            }
            if(player.dimension == -1)
            {
                if(source == DamageSource.LAVA && player.isPotionActive(MobEffects.FIRE_RESISTANCE))
                {
                    player.addStat(NetherExAchievements.STAYIN_FROSTY);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();
        DamageSource source = event.getSource();

        if(entity instanceof AbstractSkeleton)
        {
            if(source.getSourceOfDamage() instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) source.getSourceOfDamage();
                player.addStat(NetherExAchievements.FROM_WITHIN);
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
            event.getDrops().add(new EntityItem(event.getEntity().getEntityWorld(), deathPoint.getX(), deathPoint.getY(), deathPoint.getZ(), new ItemStack(NetherExItems.FOOD_MEAT_GHAST_RAW, rand.nextInt(3) + 1, 0)));
        }
        else if(event.getEntity() instanceof EntityWitherSkeleton)
        {
            ListIterator<EntityItem> iter = event.getDrops().listIterator();

            while(iter.hasNext())
            {
                EntityItem entityItem = iter.next();
                ItemStack stack = entityItem.getEntityItem();

                if(stack.getItem() == Items.BONE || stack.getItem() == Items.COAL)
                {
                    iter.remove();
                }
            }

            event.getDrops().add(new EntityItem(event.getEntity().world, deathPoint.getX(), deathPoint.getY(), deathPoint.getZ(), new ItemStack(NetherExItems.ITEM_BONE_WITHERED, rand.nextInt(4), 0)));
        }
    }
}
