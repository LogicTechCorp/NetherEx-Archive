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

package logictechcorp.netherex.handler;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class WorldHandler
{
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event)
    {
        NetherEx.BIOME_DATA_MANAGER.onWorldLoad(event);
        NetherEx.PIGTIFICATE_VILLAGE_MANAGER.onWorldLoad(event);
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        NetherEx.PIGTIFICATE_VILLAGE_MANAGER.onWorldTick(event);
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event)
    {
        NetherEx.BIOME_DATA_MANAGER.onWorldUnload(event);
        NetherEx.PIGTIFICATE_VILLAGE_MANAGER.onWorldUnload(event);
    }

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();

        if(entity instanceof EntityAreaEffectCloud)
        {
            EntityAreaEffectCloud areaEffectCloud = (EntityAreaEffectCloud) entity;
            areaEffectCloud.effects.removeIf(effect -> effect.getPotion() == NetherExMobEffects.FIRE_BURNING);
        }
    }
}
