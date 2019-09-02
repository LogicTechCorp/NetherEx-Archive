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

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.init.NetherExItems;
import logictechcorp.netherex.init.NetherExMobEffects;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class InputHandler
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onMouse(MouseEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;

        if(event.isButtonstate() && player.isPotionActive(NetherExMobEffects.FROZEN))
        {
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
        EnumHand hand = event.getHand();

        if(stack.getItem() instanceof ItemSpade)
        {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            IBlockState flattenedBlock = null;

            if(block == Blocks.NETHERRACK)
            {
                flattenedBlock = NetherExBlocks.NETHERRACK_PATH.getDefaultState();
            }
            else if(block == NetherExBlocks.GLOOMY_NETHERRACK)
            {
                flattenedBlock = NetherExBlocks.GLOOMY_NETHERRACK_PATH.getDefaultState();
            }
            else if(block == NetherExBlocks.LIVELY_NETHERRACK)
            {
                flattenedBlock = NetherExBlocks.LIVELY_NETHERRACK_PATH.getDefaultState();
            }
            else if(block == NetherExBlocks.FIERY_NETHERRACK)
            {
                flattenedBlock = NetherExBlocks.FIERY_NETHERRACK_PATH.getDefaultState();
            }
            else if(block == NetherExBlocks.ICY_NETHERRACK)
            {
                flattenedBlock = NetherExBlocks.ICY_NETHERRACK_PATH.getDefaultState();
            }

            if(flattenedBlock != null)
            {
                player.swingArm(hand);
                world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, flattenedBlock, 11);
                stack.damageItem(1, player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event)
    {
        World world = event.getWorld();
        EnumFacing facing = event.getFace();
        BlockPos originalPos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();

        if(facing != null)
        {
            BlockPos offsetPos = originalPos.offset(facing);
            IBlockState offsetState = world.getBlockState(offsetPos);

            if(offsetState.getBlock() == NetherExBlocks.BLUE_FIRE)
            {
                world.playEvent(player, 1009, offsetPos, 0);
                world.setBlockToAir(offsetPos);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onBoneMealUse(BonemealEvent event)
    {
        World world = event.getWorld();
        EntityPlayer player = event.getEntityPlayer();
        BlockPos pos = event.getPos();
        IBlockState state = event.getBlock();
        Block block = state.getBlock();

        if(player.getHeldItemMainhand().getItem() == NetherExItems.WITHER_DUST)
        {
            if(block == NetherExBlocks.BROWN_ELDER_MUSHROOM || block == NetherExBlocks.RED_ELDER_MUSHROOM)
            {
                if(block instanceof IGrowable)
                {
                    IGrowable growable = (IGrowable) state.getBlock();

                    if(growable.canGrow(world, pos, state, world.isRemote))
                    {
                        growable.grow(world, world.rand, pos, state);
                    }
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
        Random random = world.rand;

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
                    int conversionTime = random.nextInt(2401) + 3600;
                    zombiePigman.getEntityData().setInteger("ConversionTime", conversionTime);
                    zombiePigman.removePotionEffect(MobEffects.WEAKNESS);
                    zombiePigman.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, conversionTime, Math.min(world.getDifficulty().getId() - 1, 0)));
                }
            }
        }
    }
}
