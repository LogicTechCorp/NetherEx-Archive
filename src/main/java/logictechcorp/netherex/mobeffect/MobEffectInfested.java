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

package logictechcorp.netherex.mobeffect;

import logictechcorp.libraryex.potion.MobEffectMod;
import logictechcorp.libraryex.utility.CollectionHelper;
import logictechcorp.libraryex.utility.EntityHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.entity.monster.EntitySpore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MobEffectInfested extends MobEffectMod
{
    public MobEffectInfested()
    {
        super(NetherEx.instance, "infested", true, 142, 96, 40);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();

        if(this.canSpreadSpores(entity) && world.rand.nextInt(NetherExConfig.mobEffect.spore.sporeSpawnRarity) == 0)
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
        return (entityRegistryName != null && !CollectionHelper.contains(NetherExConfig.mobEffect.spore.mobBlacklist, entityRegistryName));
    }
}
