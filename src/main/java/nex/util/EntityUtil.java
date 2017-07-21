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

package nex.util;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import nex.handler.ConfigHandler;
import nex.init.NetherExEffects;

import java.util.Arrays;

public class EntityUtil
{
    public static boolean canFreeze(EntityLivingBase entity)
    {
        ResourceLocation entityRegistryName = EntityList.getKey(entity);
        return !(entity instanceof EntityPlayer) && entityRegistryName != null && !Arrays.asList(ConfigHandler.potionEffect.freeze.blacklist).contains(entityRegistryName.toString());
    }

    public static boolean canSpreadSpores(EntityLivingBase entity)
    {
        ResourceLocation entityRegistryName = EntityList.getKey(entity);
        return entity instanceof EntityPlayer || (entityRegistryName != null && !Arrays.asList(ConfigHandler.potionEffect.spore.blacklist).contains(entityRegistryName.toString()));
    }

    public static boolean canSpawnGhastling(EntityLivingBase entity)
    {
        return entity instanceof EntityPlayer && entity.getEntityWorld().provider.getDimension() == DimensionType.NETHER.getId();
    }

    public static boolean isFrozen(EntityLivingBase entity)
    {
        return entity.isPotionActive(NetherExEffects.FREEZE);
    }

    public static boolean isSporeInfested(EntityLivingBase entity)
    {
        return entity.isPotionActive(NetherExEffects.SPORE);
    }

    public static boolean isLostAfflicted(EntityLivingBase entity)
    {
        return entity.isPotionActive(NetherExEffects.LOST);
    }
}
