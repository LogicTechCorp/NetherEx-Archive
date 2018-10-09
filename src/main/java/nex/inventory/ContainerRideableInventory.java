package nex.inventory;

import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerRideableInventory extends Container
{
    private final IInventory rideableInventory;
    private final AbstractHorse rideable;

    public ContainerRideableInventory(IInventory playerInventory, IInventory rideableInventory, AbstractHorse rideable, EntityPlayer player, ItemStack saddleStack)
    {
        this.rideableInventory = rideableInventory;
        this.rideable = rideable;
        rideableInventory.openInventory(player);
        this.addSlotToContainer(new Slot(rideableInventory, 0, 8, 18)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return ItemStack.areItemStacksEqual(stack, saddleStack) && !this.getHasStack() && rideable.canBeSaddled();
            }

            @Override
            @SideOnly(Side.CLIENT)
            public boolean isEnabled()
            {
                return rideable.canBeSaddled();
            }
        });
        this.addSlotToContainer(new Slot(rideableInventory, 1, 8, 36)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return rideable.isArmor(stack);
            }

            @Override
            public int getSlotStackLimit()
            {
                return 1;
            }

            @Override
            @SideOnly(Side.CLIENT)
            public boolean isEnabled()
            {
                return rideable.wearsArmor();
            }
        });

        if(rideable instanceof AbstractChestHorse && ((AbstractChestHorse) rideable).hasChest())
        {
            for(int y = 0; y < 3; y++)
            {
                for(int x = 0; x < ((AbstractChestHorse) rideable).getInventoryColumns(); x++)
                {
                    this.addSlotToContainer(new Slot(rideableInventory, 2 + x + y * ((AbstractChestHorse) rideable).getInventoryColumns(), 80 + x * 18, 18 + y * 18));
                }
            }
        }

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 102 + y * 18 + -18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.rideableInventory.isUsableByPlayer(playerIn) && this.rideable.isEntityAlive() && this.rideable.getDistance(playerIn) < 8.0F;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack emptyStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            emptyStack = slotStack.copy();

            if(index < this.rideableInventory.getSizeInventory())
            {
                if(!this.mergeItemStack(slotStack, this.rideableInventory.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(this.getSlot(1).isItemValid(slotStack) && !this.getSlot(1).getHasStack())
            {
                if(!this.mergeItemStack(slotStack, 1, 2, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(this.getSlot(0).isItemValid(slotStack))
            {
                if(!this.mergeItemStack(slotStack, 0, 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(this.rideableInventory.getSizeInventory() <= 2 || !this.mergeItemStack(slotStack, 2, this.rideableInventory.getSizeInventory(), false))
            {
                return ItemStack.EMPTY;
            }

            if(slotStack.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return emptyStack;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        this.rideableInventory.closeInventory(playerIn);
    }
}
