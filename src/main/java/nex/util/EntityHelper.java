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

package nex.util;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import nex.handler.ConfigHandler;
import nex.init.NetherExEffects;

import java.util.HashMap;
import java.util.Map;

public class EntityHelper
{
    private static Map<Class<? extends EntityLivingBase>, String> RESOURCE_LOCATION_CACHE = new HashMap<>();
    //cached version of EntityList.getKey
    private static String getEntityLocation(EntityLivingBase entity)
    {
        Class<? extends EntityLivingBase> clazz = entity.getClass();
        return RESOURCE_LOCATION_CACHE.computeIfAbsent(clazz, k->{
            ResourceLocation loc = EntityList.getKey(k);
            return loc != null ? loc.toString() : null;
        });
    }

    private static boolean contains(String[] haystack, String needle)
    {
        if (needle == null) {
            for (String hay : haystack)
                if (hay == null)
                    return true;
        } else {
            for (String hay : haystack)
                if (needle.equals(hay))
                    return true;
        }
        return false;
    }

    public static boolean canFreeze(EntityLivingBase entity)
    {
        if (entity instanceof EntityPlayer){
            return false;
        }
        String entityRegistryName = getEntityLocation(entity);
        return entityRegistryName != null && !contains(ConfigHandler.potionEffectConfig.freeze.blacklist, entityRegistryName);
    }

    public static boolean canSpreadSpores(EntityLivingBase entity)
    {
        if (entity instanceof EntityPlayer)
            return true;
        String entityRegistryName = getEntityLocation(entity);
        return (entityRegistryName != null && !contains(ConfigHandler.potionEffectConfig.spore.blacklist, entityRegistryName));
    }

    public static boolean canSpawnGhastling(EntityLivingBase entity)
    {
        return entity instanceof EntityPlayer && entity.getEntityWorld().provider.getDimensionType() == DimensionType.NETHER;
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
