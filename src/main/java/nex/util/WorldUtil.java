package nex.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WorldUtil
{
    public static RayTraceResult rayTraceFromEntity(World world, Entity entity, boolean countNonSolidBlock, double range)
    {
        float f = 1.0F;
        float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f;
        float f2 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f;
        double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double) f;
        double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double) f;

        if(!world.isRemote && entity instanceof EntityPlayer)
        {
            d1 += 1.62D;
        }

        double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) f;
        Vec3d vec3 = new Vec3d(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = range;

        if(entity instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP) entity).interactionManager.getBlockReachDistance();
        }

        Vec3d vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        return world.rayTraceBlocks(vec3, vec31, countNonSolidBlock, !countNonSolidBlock, countNonSolidBlock);
    }
}
