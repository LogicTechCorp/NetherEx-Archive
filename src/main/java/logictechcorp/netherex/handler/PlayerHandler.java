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

import logictechcorp.libraryex.utility.NBTHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.block.NetherExBlocks;
import logictechcorp.netherex.item.NetherExItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class PlayerHandler
{
	
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        TickEvent.Phase phase = event.phase;
        World world = event.player.getEntityWorld();
        PlayerEntity player = event.player;

        if(phase == TickEvent.Phase.END)
        {
            if(!world.isRemote)
            {
                IInventory inventory = player.inventory;
                int mirrorCount = 0;

                for(int i = 0; i < inventory.getSizeInventory(); i++)
                {
                    ItemStack stack = inventory.getStackInSlot(i);

                    if(stack.getItem() == NetherExItems.DULL_MIRROR.get())
                    {
                        mirrorCount++;

                        if(mirrorCount > 1)
                        {
                            ItemEntity item = new ItemEntity(world, player.getPosX(), player.getPosY() + 0.5F, player.getPosZ(), stack);
                            item.setPickupDelay(50);
                            world.addEntity(item);
                            inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                            break;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event)
    {
        World world = event.getWorld();
        Direction direction = event.getFace();
        BlockPos pos = event.getPos();
        PlayerEntity player = event.getPlayer();

        if(direction != null)
        {
            BlockPos offsetPos = pos.offset(direction);
            Block offsetBlock = world.getBlockState(offsetPos).getBlock();

            if(offsetBlock == NetherExBlocks.BLUE_FIRE.get())
            {
                world.playEvent(player, 1009, offsetPos, 0);
                world.setBlockState(offsetPos, Blocks.AIR.getDefaultState());
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event)
    {
        PlayerEntity player = event.getPlayer();
        ItemStack stack = event.getCrafting();

        if(stack.getItem() == NetherExItems.DULL_MIRROR.get())
        {
            if(stack.getDamage() < stack.getMaxDamage())
            {
                CompoundNBT compound = NBTHelper.ensureTagExists(stack);
                compound.putBoolean("RemovedSpawn", false);

                if(compound.contains("SpawnDimension") && compound.contains("SpawnPoint"))
                {
                    DimensionType spawnDimension = DimensionType.byName(new ResourceLocation(compound.getString("SpawnDimension")));
                    BlockPos spawnPoint = NBTUtil.readBlockPos(compound.getCompound("SpawnPoint"));

                    if(spawnDimension != null)
                    {
                        player.setSpawnDimenion(spawnDimension);
                        player.setSpawnPoint(spawnPoint, true, false, spawnDimension);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDrop(LivingDropsEvent event)
    {
        LivingEntity livingEntity = event.getEntityLiving();
        Iterator<ItemEntity> iter = event.getDrops().iterator();

        if(livingEntity instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) livingEntity;

            while(iter.hasNext())
            {
                ItemEntity entityItem = iter.next();
                ItemStack stack = entityItem.getItem();

                if(stack.getItem() == NetherExItems.DULL_MIRROR.get())
                {
                    player.setItemStackToSlot(EquipmentSlotType.MAINHAND, stack);
                    iter.remove();
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        PlayerEntity oldPlayer = event.getOriginal();
        PlayerEntity newPlayer = event.getPlayer();
        IInventory oldInventory = oldPlayer.inventory;
        IInventory newInventory = newPlayer.inventory;

        if(event.isWasDeath())
        {
            ItemStack mirrorStack = ItemStack.EMPTY;

            for(int i = 0; i < oldInventory.getSizeInventory(); i++)
            {
                ItemStack stack = oldInventory.getStackInSlot(i);

                if(stack.getItem() == NetherExItems.DULL_MIRROR.get())
                {
                    NBTHelper.ensureTagExists(stack);

                    if(stack.getTag().contains("SpawnPoint") && (stack.getDamage() < (stack.getMaxDamage() - 1)))
                    {
                        stack.damageItem(1, newPlayer, entity ->
                        {
                        });
                    }

                    newInventory.setInventorySlotContents(i, stack);
                    mirrorStack = stack;
                    break;
                }
            }

            if(!mirrorStack.isEmpty())
            {
                if(mirrorStack.getDamage() < mirrorStack.getMaxDamage() - 1)
                {
                    CompoundNBT compound = NBTHelper.ensureTagExists(mirrorStack);

                    if(compound.contains("SpawnDimension") && compound.contains("SpawnPoint"))
                    {
                        DimensionType spawnDimension = DimensionType.byName(new ResourceLocation(compound.getString("SpawnDimension")));
                        BlockPos spawnPoint = NBTUtil.readBlockPos(compound.getCompound("SpawnPoint"));

                        if(spawnDimension != null)
                        {
                            newPlayer.setSpawnDimenion(spawnDimension);
                            newPlayer.setSpawnPoint(spawnPoint, true, false, spawnDimension);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        World world = event.getPlayer().getEntityWorld();
        PlayerEntity player = event.getPlayer();
        PlayerInventory playerInventory = player.inventory;

        if(!world.isRemote)
        {
            ItemStack mirrorStack = ItemStack.EMPTY;

            for(int i = 0; i < playerInventory.getSizeInventory(); i++)
            {
                ItemStack stack = playerInventory.getStackInSlot(i);

                if(stack.getItem() == NetherExItems.DULL_MIRROR.get())
                {
                    mirrorStack = stack;
                    break;
                }
            }

            if(!mirrorStack.isEmpty())
            {
                if(mirrorStack.getDamage() < mirrorStack.getMaxDamage() - 1)
                {
                    CompoundNBT compound = NBTHelper.ensureTagExists(mirrorStack);

                    if(compound.contains("SpawnDimension") && compound.contains("SpawnPoint"))
                    {
                        DimensionType spawnDimension = DimensionType.byName(new ResourceLocation(compound.getString("SpawnDimension")));

                        if(spawnDimension != null && player.dimension != spawnDimension)
                        {
                            MinecraftServer server = world.getServer();

                            if(server != null)
                            {
                                BlockPos spawnPoint = NBTUtil.readBlockPos(compound.getCompound("SpawnPoint"));
                                ((ServerPlayerEntity) player).teleport(server.getWorld(spawnDimension), spawnPoint.getX() + 0.5D, spawnPoint.getY(), spawnPoint.getZ() + 0.5D, player.rotationYaw, player.rotationPitch);
                            }
                        }
                    }
                }
            }
        }
    }
}