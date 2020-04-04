/*
 * NetherEx
 * Copyright (c) 2016-2020 by LogicTechCorp
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

package logictechcorp.netherex.client.handler;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.item.NetherExItems;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorHandler
{
    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event)
    {
        ItemColors colors = event.getItemColors();
        NetherExItems.getItems().forEach(registryObject ->
        {
            Item item = registryObject.get();

            if(item instanceof SpawnEggItem)
            {
                colors.register((color, index) -> ((SpawnEggItem) item).getColor(index), item);
            }
        });
    }
}
