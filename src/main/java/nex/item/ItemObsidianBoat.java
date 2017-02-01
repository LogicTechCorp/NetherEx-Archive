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

package nex.item;

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
import nex.entity.item.EntityObsidianBoat;

import java.util.List;

@SuppressWarnings("unchecked")
public class ItemObsidianBoat extends ItemNetherEx
{
    public ItemObsidianBoat()
    {
        super("item_boat_obsidian");

        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * 1.0F;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * 1.0F;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * 1.0D;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * 1.0D + (double) player.getEyeHeight();
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * 1.0D;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        Vec3d vec3d1 = vec3d.addVector((double) f7 * 5.0D, (double) f6 * 5.0D, (double) f8 * 5.0D);
        RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d1, true);

        if(raytraceresult == null)
        {
            return new ActionResult(EnumActionResult.PASS, stack);
        }
        else
        {
            Vec3d vec3d2 = player.getLook(1.0F);
            boolean flag = false;
            List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().addCoord(vec3d2.xCoord * 5.0D, vec3d2.yCoord * 5.0D, vec3d2.zCoord * 5.0D).expandXyz(1.0D));

            for(Entity entity : entities)
            {
                if(entity.canBeCollidedWith())
                {
                    AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expandXyz((double) entity.getCollisionBorderSize());

                    if(axisalignedbb.isVecInside(vec3d))
                    {
                        flag = true;
                    }
                }
            }

            if(flag)
            {
                return new ActionResult(EnumActionResult.PASS, stack);
            }
            else if(raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
            {
                return new ActionResult(EnumActionResult.PASS, stack);
            }
            else
            {
                Block block = world.getBlockState(raytraceresult.getBlockPos()).getBlock();
                boolean flag1 = block == Blocks.WATER || block == Blocks.FLOWING_WATER;
                EntityObsidianBoat boat = new EntityObsidianBoat(world, raytraceresult.hitVec.xCoord, flag1 ? raytraceresult.hitVec.yCoord - 0.12D : raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
                boat.rotationYaw = player.rotationYaw;

                if(!world.getCollisionBoxes(boat, boat.getEntityBoundingBox().expandXyz(-0.1D)).isEmpty())
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
