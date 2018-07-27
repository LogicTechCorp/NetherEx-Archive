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

import lex.util.BlockHelper;
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
import net.minecraft.item.ItemHoe;
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
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.block.BlockNetherrackPath;
import nex.init.NetherExBlocks;
import nex.init.NetherExEffects;
import nex.init.NetherExItems;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
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

        if(stack.getItem() == NetherExItems.GOLDEN_WITHER_BONE_SHOVEL || ConfigHandler.blockConfig.netherrack.allowAllShovelsToFlatten && stack.getItem() instanceof ItemHoe)
        {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if(block == Blocks.NETHERRACK || block == NetherExBlocks.NETHERRACK || block == NetherExBlocks.HYPHAE)
            {
                for(EnumHand hand : EnumHand.values())
                {
                    if(player.getHeldItem(hand).getItem() instanceof ItemSpade)
                    {
                        player.swingArm(hand);
                    }
                }

                int meta = block == Blocks.NETHERRACK ? 0 : block == NetherExBlocks.HYPHAE ? 3 : NetherExBlocks.NETHERRACK.getMetaFromState(state) + 1;

                world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, NetherExBlocks.NETHERRACK_PATH.getDefaultState().withProperty(BlockNetherrackPath.TYPE, BlockNetherrackPath.EnumType.fromMeta(meta)), 11);
                stack.damageItem(1, player);
            }

        }
        if(stack.getItem() == NetherExItems.GOLDEN_WITHER_BONE_HOE || ConfigHandler.blockConfig.soulSand.allowAllHoesToTill && stack.getItem() instanceof ItemHoe)
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
                world.setBlockState(pos, NetherExBlocks.TILLED_SOUL_SAND.getDefaultState(), 11);
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

        if(originalState.getBlock() == Blocks.BEDROCK && player.getHeldItemMainhand().getItem() == NetherExItems.GOLDEN_WITHER_BONE_HAMMER)
        {
            BlockHelper.mine3x3(world, player.getActiveItemStack(), originalPos, player);
        }
    }

    @SubscribeEvent
    public static void onBoneMealUse(BonemealEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getBlock();
        EntityPlayer player = event.getEntityPlayer();

        if(player.getHeldItem(EnumHand.MAIN_HAND).getItem() == NetherExItems.WITHER_DUST)
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
            ItemStack stack = player.getHeldItem(player.swingingHand);

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
                    zombiePigman.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, conversionTime, Math.min(world.getDifficulty().getDifficultyId() - 1, 0)));
                }
            }
        }
    }
}
