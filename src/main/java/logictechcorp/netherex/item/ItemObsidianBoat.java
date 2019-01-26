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

package logictechcorp.netherex.item;

import logictechcorp.libraryex.item.ItemMod;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.item.EntityObsidianBoat;
import logictechcorp.netherex.init.NetherExItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings("unchecked")
public class ItemObsidianBoat extends ItemMod
{
    public ItemObsidianBoat()
    {
        super(NetherEx.getResource("obsidian_boat"), NetherExItems.getDefaultItemBuilder().copy().maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * 1.0F;
        float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * 1.0F;
        double posX = player.prevPosX + (player.posX - player.prevPosX) * 1.0D;
        double posY = player.prevPosY + (player.posY - player.prevPosY) * 1.0D + (double) player.getEyeHeight();
        double posZ = player.prevPosZ + (player.posZ - player.prevPosZ) * 1.0D;
        Vec3d pos = new Vec3d(posX, posY, posZ);
        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
        float f6 = MathHelper.sin(-pitch * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        Vec3d vec3d1 = pos.add((double) f7 * 5.0D, (double) f6 * 5.0D, (double) f8 * 5.0D);
        RayTraceResult rayTraceResult = world.rayTraceBlocks(pos, vec3d1, true);

        if(rayTraceResult == null)
        {
            return new ActionResult(EnumActionResult.PASS, stack);
        }
        else
        {
            Vec3d vec3d2 = player.getLook(1.0F);
            boolean flag = false;
            List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(vec3d2.x * 5.0D, vec3d2.y * 5.0D, vec3d2.z * 5.0D).grow(1.0D));

            for(Entity entity : entities)
            {
                if(entity.canBeCollidedWith())
                {
                    AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow((double) entity.getCollisionBorderSize());

                    if(axisalignedbb.contains(pos))
                    {
                        flag = true;
                    }
                }
            }

            if(flag)
            {
                return new ActionResult(EnumActionResult.PASS, stack);
            }
            else if(rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK)
            {
                return new ActionResult(EnumActionResult.PASS, stack);
            }
            else
            {
                Block block = world.getBlockState(rayTraceResult.getBlockPos()).getBlock();
                boolean flag1 = block == Blocks.WATER || block == Blocks.FLOWING_WATER;
                EntityObsidianBoat boat = new EntityObsidianBoat(world, rayTraceResult.hitVec.x, flag1 ? rayTraceResult.hitVec.y - 0.12D : rayTraceResult.hitVec.y, rayTraceResult.hitVec.z);
                boat.rotationYaw = player.rotationYaw;

                if(!world.getCollisionBoxes(boat, boat.getEntityBoundingBox().grow(-0.1D)).isEmpty())
                {
                    return new ActionResult(EnumActionResult.FAIL, stack);
                }
                else
                {
                    if(!world.isRemote)
                    {
                        world.spawnEntity(boat);
                    }

                    if(!player.capabilities.isCreativeMode)
                    {
                        stack.shrink(1);
                    }

                    return new ActionResult(EnumActionResult.SUCCESS, stack);
                }
            }
        }
    }

}
