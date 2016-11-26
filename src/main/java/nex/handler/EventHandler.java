/*
 * Copyright (C) 2016.  LogicTechCorp
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

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.init.NetherExItems;
import util.NBTUtil;

import java.util.ListIterator;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
public class EventHandler
{
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        BlockPos deathPoint = event.getEntity().getPosition();

        if(event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            InventoryPlayer inventory = player.inventory;

            for(int i = 0; i < inventory.getSizeInventory(); i++)
            {
                ItemStack stack = inventory.getStackInSlot(i);

                if(stack.getItem() == NetherExItems.ITEM_MIRROR)
                {
                    NBTTagCompound compound = NBTUtil.setTag(stack, stack.getTagCompound());
                    compound.setIntArray("DeathPoint", new int[]{deathPoint.getX(), deathPoint.getY(), deathPoint.getZ()});
                    stack.setTagCompound(compound);

                    if(stack.getTagCompound().hasKey("SpawnPointMirror"))
                    {
                        int[] spawnPoint = stack.getTagCompound().getIntArray("SpawnPointMirror");
                        player.setSpawnPoint(new BlockPos(spawnPoint[0], spawnPoint[1], spawnPoint[2]), true);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerDrop(PlayerDropsEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();

        ListIterator<EntityItem> iter = event.getDrops().listIterator();

        while(iter.hasNext())
        {
            EntityItem entityItem = iter.next();
            ItemStack stack = entityItem.getEntityItem();

            if(stack.getItem() == NetherExItems.ITEM_MIRROR)
            {
                if(addToPlayerInventory(player, stack))
                {
                    iter.remove();
                }
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
            event.getDrops().add(new EntityItem(event.getEntity().world, deathPoint.getX(), deathPoint.getY(), deathPoint.getZ(), new ItemStack(NetherExItems.FOOD_MEAT_GHAST_RAW, rand.nextInt(3) + 1, 0)));
        }
        else if(event.getEntity() instanceof EntityWitherSkeleton)
        {
            event.getDrops().add(new EntityItem(event.getEntity().world, deathPoint.getX(), deathPoint.getY(), deathPoint.getZ(), new ItemStack(NetherExItems.ITEM_BONE_WITHERED, rand.nextInt(4), 0)));
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        EntityPlayer oldPlayer = event.getOriginal();
        EntityPlayer newPlayer = event.getEntityPlayer();

        if(oldPlayer.inventory.hasItemStack(new ItemStack(NetherExItems.ITEM_MIRROR, 1, 0)))
        {
            newPlayer.inventory.copyInventory(oldPlayer.inventory);
        }
    }

    private static boolean addToPlayerInventory(EntityPlayer player, ItemStack stack)
    {
        if(stack == null || player == null)
        {
            return false;
        }

        InventoryPlayer inventory = player.inventory;

        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            if(inventory.getStackInSlot(i) == ItemStack.EMPTY)
            {
                inventory.setInventorySlotContents(i, stack.copy());
                inventory.getStackInSlot(i).setTagCompound(stack.getTagCompound());
                return true;
            }
        }

        return false;
    }
}
