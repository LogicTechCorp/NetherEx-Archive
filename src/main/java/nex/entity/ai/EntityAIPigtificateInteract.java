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

package nex.entity.ai;

import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import nex.entity.passive.EntityPigtificate;
import nex.init.NetherExBlocks;
import nex.init.NetherExItems;

public class EntityAIPigtificateInteract extends EntityAIWatchClosest2
{
    private int interactionDelay;
    private final EntityPigtificate pigtificate;

    public EntityAIPigtificateInteract(EntityPigtificate pigtificate)
    {
        super(pigtificate, EntityPigtificate.class, 3.0F, 0.02F);
        this.pigtificate = pigtificate;
    }

    @Override
    public void startExecuting()
    {
        super.startExecuting();

        if(this.pigtificate.canAbandonItems() && this.closestEntity instanceof EntityPigtificate && ((EntityPigtificate) this.closestEntity).wantsMoreFood())
        {
            this.interactionDelay = 10;
        }
        else
        {
            this.interactionDelay = 0;
        }
    }

    @Override
    public void updateTask()
    {
        super.updateTask();

        if(this.interactionDelay > 0)
        {
            this.interactionDelay--;

            if(this.interactionDelay == 0)
            {
                InventoryBasic inventory = this.pigtificate.getInventory();

                for(int i = 0; i < inventory.getSizeInventory(); i++)
                {
                    ItemStack stackInSlot = inventory.getStackInSlot(i);
                    ItemStack stack = ItemStack.EMPTY;

                    if(!stackInSlot.isEmpty())
                    {
                        Item item = stackInSlot.getItem();

                        if((item == Item.getItemFromBlock(NetherExBlocks.ELDER_MUSHROOM) && stackInSlot.getCount() > 7) || (item == NetherExItems.ENOKI_MUSHROOM && stackInSlot.getCount() > 63))
                        {
                            int amount = stackInSlot.getCount() / 2;
                            stackInSlot.shrink(amount);
                            stack = new ItemStack(item, amount, stackInSlot.getMetadata());
                        }
                        if(stackInSlot.isEmpty())
                        {
                            inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                        }
                    }

                    if(!stack.isEmpty())
                    {
                        World world = this.pigtificate.getEntityWorld();
                        double height = this.pigtificate.posY - 0.30000001192092896D + (double) this.pigtificate.getEyeHeight();
                        EntityItem item = new EntityItem(world, this.pigtificate.posX, height, this.pigtificate.posZ, stack);
                        float yaw = this.pigtificate.rotationYawHead;
                        float pitch = this.pigtificate.rotationPitch;
                        item.motionX = (double) (-MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F) * 0.3F);
                        item.motionZ = (double) (MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F) * 0.3F);
                        item.motionY = (double) (-MathHelper.sin(pitch * 0.017453292F) * 0.3F + 0.1F);
                        item.setDefaultPickupDelay();
                        world.spawnEntity(item);
                        break;
                    }
                }
            }
        }
    }
}
