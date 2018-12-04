package logictechcorp.netherex.potion;

import logictechcorp.libraryex.potion.PotionLibEx;
import logictechcorp.libraryex.util.CollectionHelper;
import logictechcorp.libraryex.util.EntityHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntitySpore;
import logictechcorp.netherex.handler.ConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionSpore extends PotionLibEx
{
    public PotionSpore()
    {
        super(NetherEx.instance, "spore", true, 142, 96, 40);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();

        if(this.canSpreadSpores(entity) && world.rand.nextInt(ConfigHandler.potionEffectConfig.spore.sporeSpawnRarity) == 0)
        {
            if(world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).expand(1, 1, 1)).size() < 3)
            {
                BlockPos newPos = pos.offset(EnumFacing.Plane.HORIZONTAL.random(world.rand));

                if(!world.isRemote && world.isAirBlock(newPos) && world.getBlockState(newPos.down()).isSideSolid(world, newPos.down(), EnumFacing.UP))
                {
                    EntitySpore spore = new EntitySpore(world, 0);
                    spore.setLocationAndAngles(newPos.getX(), newPos.getY(), newPos.getZ(), entity.rotationYaw, entity.rotationPitch);
                    world.spawnEntity(spore);
                }
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

    public boolean canSpreadSpores(EntityLivingBase entity)
    {
        if(entity instanceof EntityPlayer)
        {
            return true;
        }

        String entityRegistryName = EntityHelper.getEntityLocation(entity);
        return (entityRegistryName != null && !CollectionHelper.contains(ConfigHandler.potionEffectConfig.spore.mobBlacklist, entityRegistryName));
    }
}
