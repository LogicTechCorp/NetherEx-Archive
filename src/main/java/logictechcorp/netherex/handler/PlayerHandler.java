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
import logictechcorp.netherex.init.NetherExItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ListIterator;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class PlayerHandler
{
    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event)
    {
        EntityPlayer player = event.player;
        ItemStack stack = event.crafting;

        if(stack.getItem() == NetherExItems.DULL_MIRROR)
        {
            if(stack.getItemDamage() < stack.getMaxDamage())
            {
                NBTTagCompound compound = NBTHelper.ensureTagExists(stack);
                compound.setBoolean("RemovedSpawn", false);

                if(compound.hasKey("SpawnDimension") && compound.hasKey("SpawnPoint"))
                {
                    int spawnDimension = compound.getInteger("SpawnDimension");
                    BlockPos spawnPoint = NBTUtil.getPosFromTag(compound.getCompoundTag("SpawnPoint"));
                    player.setSpawnDimension(spawnDimension);
                    player.setSpawnChunk(spawnPoint, true, spawnDimension);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        TickEvent.Phase phase = event.phase;
        EntityPlayer player = event.player;
        World world = player.getEntityWorld();

        if(phase == TickEvent.Phase.END)
        {
            if(!world.isRemote)
            {
                IInventory inventory = player.inventory;
                int mirrorCount = 0;

                for(int i = 0; i < inventory.getSizeInventory(); i++)
                {
                    ItemStack stack = inventory.getStackInSlot(i);

                    if(stack.getItem() == NetherExItems.DULL_MIRROR)
                    {
                        mirrorCount++;

                        if(mirrorCount > 1)
                        {
                            EntityItem item = new EntityItem(world, player.posX, player.posY + 0.5F, player.posZ, stack);
                            item.setPickupDelay(50);
                            world.spawnEntity(item);
                            inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                            break;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDrop(PlayerDropsEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        ListIterator<EntityItem> iter = event.getDrops().listIterator();

        while(iter.hasNext())
        {
            EntityItem entityItem = iter.next();
            ItemStack stack = entityItem.getItem();

            if(stack.getItem() == NetherExItems.DULL_MIRROR)
            {
                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
                iter.remove();
                break;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(Clone event)
    {
        if(event.isWasDeath())
        {
            EntityPlayer oldPlayer = event.getOriginal();
            EntityPlayer newPlayer = event.getEntityPlayer();
            World world = newPlayer.getEntityWorld();
            IInventory oldInventory = oldPlayer.inventory;
            IInventory newInventory = newPlayer.inventory;

            ItemStack mirrorStack = ItemStack.EMPTY;

            for(int i = 0; i < oldInventory.getSizeInventory(); i++)
            {
                ItemStack stack = oldInventory.getStackInSlot(i);

                if(stack.getItem() == NetherExItems.DULL_MIRROR)
                {
                    if(stack.getItemDamage() < (stack.getMaxDamage() - 1))
                    {
                        stack.damageItem(1, newPlayer);
                    }

                    newInventory.setInventorySlotContents(i, stack);
                    mirrorStack = stack;
                    break;
                }
            }

            if(!mirrorStack.isEmpty())
            {
                if(mirrorStack.getItemDamage() < mirrorStack.getMaxDamage() - 1)
                {
                    NBTTagCompound compound = NBTHelper.ensureTagExists(mirrorStack);

                    if(compound.hasKey("SpawnDimension") && compound.hasKey("SpawnPoint"))
                    {
                        int spawnDimension = compound.getInteger("SpawnDimension");

                        BlockPos spawnPoint = NBTUtil.getPosFromTag(compound.getCompoundTag("SpawnPoint"));
                        newPlayer.setSpawnDimension(spawnDimension);
                        newPlayer.setSpawnChunk(spawnPoint, true, spawnDimension);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if(!event.isEndConquered())
        {

        }
    }
}
