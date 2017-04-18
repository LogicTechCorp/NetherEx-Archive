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

package nex.entity.ai;

import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import nex.entity.passive.EntityPigtificate;
import nex.init.NetherExBlocks;
import nex.init.NetherExItems;

public class EntityAIPigtificateInteract extends EntityAIWatchClosest2
{
    private int interactionDelay;
    private final EntityPigtificate pigtificate;

    public EntityAIPigtificateInteract(EntityPigtificate pigtificateIn)
    {
        super(pigtificateIn, EntityPigtificate.class, 3.0F, 0.02F);
        pigtificate = pigtificateIn;
    }

    @Override
    public void startExecuting()
    {
        super.startExecuting();

        if(pigtificate.canAbandonItems() && closestEntity instanceof EntityPigtificate && ((EntityPigtificate) closestEntity).wantsMoreFood())
        {
            interactionDelay = 10;
        }
        else
        {
            interactionDelay = 0;
        }
    }

    @Override
    public void updateTask()
    {
        super.updateTask();

        if(interactionDelay > 0)
        {
            --interactionDelay;

            if(interactionDelay == 0)
            {
                InventoryBasic inventorybasic = pigtificate.getInventory();

                for(int i = 0; i < inventorybasic.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = inventorybasic.getStackInSlot(i);
                    ItemStack itemstack1 = ItemStack.EMPTY;

                    if(!itemstack.isEmpty())
                    {
                        Item item = itemstack.getItem();

                        if((item == Item.getItemFromBlock(NetherExBlocks.PLANT_MUSHROOM_ELDER) && itemstack.getCount() > 7) || (item == NetherExItems.FOOD_MUSHROOM_ENOKI && itemstack.getCount() > 63))
                        {
                            int l = itemstack.getCount() / 2;
                            itemstack.shrink(l);
                            itemstack1 = new ItemStack(item, l, itemstack.getMetadata());
                        }
                        if(itemstack.isEmpty())
                        {
                            inventorybasic.setInventorySlotContents(i, ItemStack.EMPTY);
                        }
                    }

                    if(!itemstack1.isEmpty())
                    {
                        double d0 = pigtificate.posY - 0.30000001192092896D + (double) pigtificate.getEyeHeight();
                        EntityItem entityitem = new EntityItem(pigtificate.world, pigtificate.posX, d0, pigtificate.posZ, itemstack1);
                        float f1 = pigtificate.rotationYawHead;
                        float f2 = pigtificate.rotationPitch;
                        entityitem.motionX = (double) (-MathHelper.sin(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F) * 0.3F);
                        entityitem.motionZ = (double) (MathHelper.cos(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F) * 0.3F);
                        entityitem.motionY = (double) (-MathHelper.sin(f2 * 0.017453292F) * 0.3F + 0.1F);
                        entityitem.setDefaultPickupDelay();
                        pigtificate.world.spawnEntity(entityitem);
                        break;
                    }
                }
            }
        }
    }
}
