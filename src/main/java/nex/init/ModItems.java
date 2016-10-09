/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.init;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.block.*;
import nex.item.ItemBlockVariantContainer;
import nex.item.ItemNetherEx;
import nex.item.ItemRime;

import java.util.List;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class ModItems
{
    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":rime")
    public static final ItemRime RIME = null;

    public static List<IVariantContainer> variantContainers = Lists.newArrayList();

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event)
        {
            event.getRegistry().registerAll(
                    new ItemRime()
            );
        }
    }
}