/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.handler;

import logictechcorp.libraryex.util.BlockHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.init.NetherExEffects;
import logictechcorp.netherex.init.NetherExItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class ClickHandler
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onMouse(MouseEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;

        if(player.isPotionActive(NetherExEffects.FREEZE))
        {
            KeyBinding.unPressAllKeys();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = event.getItemStack();

        if(stack.getItem() instanceof ItemSpade)
        {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if(block == Blocks.NETHERRACK)
            {
                for(EnumHand hand : EnumHand.values())
                {
                    if(player.getHeldItem(hand).getItem() instanceof ItemSpade)
                    {
                        player.swingArm(hand);
                    }
                }

                world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, NetherExBlocks.NETHERRACK_PATH.getDefaultState(), 11);
                stack.damageItem(1, player);
            }
        }
    }

    @SubscribeEvent
    public static void useHoeEvent(UseHoeEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = event.getCurrent();

        if(world.getBlockState(pos).getBlock() == Blocks.SOUL_SAND)
        {
            for(EnumHand hand : EnumHand.values())
            {
                if(player.getHeldItem(hand).equals(stack))
                {
                    player.swingArm(hand);
                }
            }

            world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos, NetherExBlocks.TILLED_SOUL_SAND.getDefaultState(), 0b1011);

            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event)
    {
        World world = event.getWorld();
        EnumFacing facing = event.getFace();
        BlockPos originalPos = event.getPos();
        BlockPos offsetPos = originalPos.offset(facing);
        IBlockState originalState = world.getBlockState(originalPos);
        IBlockState offsetState = world.getBlockState(offsetPos);
        EntityPlayer player = event.getEntityPlayer();

        if(offsetState.getBlock() == NetherExBlocks.BLUE_FIRE)
        {
            world.playEvent(player, 1009, offsetPos, 0);
            world.setBlockToAir(offsetPos);
            event.setCanceled(true);
        }

        if(originalState.getBlock() == Blocks.BEDROCK && player.getHeldItemMainhand().getItem() == NetherExItems.WITHERED_AMEDIAN_HAMMER)
        {
            BlockHelper.mine3x3(world, player.getHeldItemMainhand(), originalPos, player);
        }
    }

    @SubscribeEvent
    public static void onBoneMealUse(BonemealEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getBlock();
        EntityPlayer player = event.getEntityPlayer();

        if(player.getHeldItemMainhand().getItem() == NetherExItems.WITHER_DUST)
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
    public static void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract event)
    {
        World world = event.getWorld();
        EntityPlayer player = event.getEntityPlayer();
        Entity entity = event.getTarget();
        Random rand = world.rand;

        if(entity instanceof EntityPigZombie)
        {
            EntityPigZombie zombiePigman = (EntityPigZombie) entity;
            ItemStack stack = player.getHeldItemMainhand();

            if(stack.getItem() == NetherExItems.GHAST_QUEEN_TEAR && stack.getMetadata() == 0 && zombiePigman.isPotionActive(MobEffects.WEAKNESS))
            {
                if(!player.capabilities.isCreativeMode)
                {
                    stack.shrink(1);
                }

                if(!world.isRemote)
                {
                    int conversionTime = rand.nextInt(2401) + 3600;
                    zombiePigman.getEntityData().setInteger("ConversionTime", conversionTime);
                    zombiePigman.removePotionEffect(MobEffects.WEAKNESS);
                    zombiePigman.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, conversionTime, Math.min(world.getDifficulty().getId() - 1, 0)));
                }
            }
        }
    }
}
