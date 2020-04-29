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
import logictechcorp.netherex.village.PigtificateVillageData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
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
        World world = event.getWorld();

        if(!world.isRemote)
        {
            if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
            {
                if(ConfigHandler.dimensionConfig.nether.overrideNether)
                {
                    NetherEx.BIOME_DATA_MANAGER.createBiomeDataConfigs(event);
                    NetherEx.BIOME_DATA_MANAGER.readBiomeDataConfigs(event);
                }

                NetherEx.PIGTIFICATE_VILLAGE_MANAGER.createPigtificateTradeConfigs(event);
                NetherEx.PIGTIFICATE_VILLAGE_MANAGER.readPigtificateTradeConfigs(event);
            }

            NetherEx.PIGTIFICATE_VILLAGE_MANAGER.loadVillageData(event);
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        World world = event.world;

        if(event.phase == TickEvent.Phase.END)
        {
            if(!world.isRemote)
            {
                PigtificateVillageData data = NetherEx.PIGTIFICATE_VILLAGE_MANAGER.getVillageData(world, false);

                if(data != null)
                {
                    data.tick(world);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event)
    {
        World world = event.getWorld();

        if(!world.isRemote)
        {
            NetherEx.PIGTIFICATE_VILLAGE_MANAGER.unloadVillageData(event);

            if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
            {
                NetherEx.BIOME_DATA_MANAGER.cleanup(event);
                NetherEx.PIGTIFICATE_VILLAGE_MANAGER.cleanup(event);
            }
        }
    }

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent event)
    {
        World world = event.getWorld();
        Entity entity = event.getEntity();

        if(entity instanceof EntityAreaEffectCloud)
        {
            EntityAreaEffectCloud areaEffectCloud = (EntityAreaEffectCloud) entity;
            areaEffectCloud.effects.removeIf(effect -> effect.getPotion() == NetherExMobEffects.FIRE_BURNING);

            if(areaEffectCloud.effects.isEmpty())
            {
                world.removeEntity(entity);
            }
        }
    }
}
