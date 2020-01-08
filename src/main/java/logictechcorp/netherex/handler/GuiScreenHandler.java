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
import logictechcorp.netherex.client.gui.GuiBreakingChanges;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Comparator;
import java.util.List;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Side.CLIENT)
public class GuiScreenHandler
{
    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event)
    {
        GuiScreen gui = event.getGui();

        if(ConfigHandler.internalConfig.doNotChange.warnBreakingChanges && gui instanceof GuiMainMenu)
        {
            event.setGui(new GuiBreakingChanges((GuiMainMenu) gui));
            ConfigHandler.internalConfig.doNotChange.warnBreakingChanges = false;
            MinecraftForge.EVENT_BUS.post(new ConfigChangedEvent.OnConfigChangedEvent(NetherEx.MOD_ID, NetherEx.NAME, false, false));
        }
    }
}
