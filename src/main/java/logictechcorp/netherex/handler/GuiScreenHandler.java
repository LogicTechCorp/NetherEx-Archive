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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Comparator;
import java.util.List;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Side.CLIENT)
public class GuiScreenHandler
{
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    private static GuiButton resetNetherButton;

    @SubscribeEvent
    public static void onInitGuiPost(GuiScreenEvent.InitGuiEvent.Post event)
    {
        GuiScreen guiScreen = event.getGui();
        List<GuiButton> guiButtons = event.getButtonList();

        if(guiScreen instanceof GuiWorldSelection)
        {
            resetNetherButton = new GuiButton(getButtonId(guiButtons), guiScreen.width / 2 - 154, 6, 90, 20, I18n.format("gui." + NetherEx.MOD_ID + ":select_world.reset_nether"));
            resetNetherButton.enabled = false;
            guiButtons.add(resetNetherButton);
        }
    }

    @SubscribeEvent
    public static void onMousePressed(GuiScreenEvent.MouseInputEvent.Post event)
    {
        GuiScreen guiScreen = event.getGui();

        if(guiScreen instanceof GuiWorldSelection)
        {
            GuiListWorldSelection guiWorldList = ReflectionHelper.getPrivateValue(GuiWorldSelection.class, (GuiWorldSelection) guiScreen, "field_184866_u", "selectionList");

            if(guiWorldList.getSelectedWorld() != null)
            {
                resetNetherButton.enabled = true;
            }
        }
    }

    @SubscribeEvent
    public static void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event)
    {
        GuiScreen guiScreen = event.getGui();
        List<GuiButton> guiButtons = event.getButtonList();
        GuiButton guiButton = event.getButton();

        if(guiScreen instanceof GuiWorldSelection)
        {
            if(guiButton == resetNetherButton)
            {
                GuiListWorldSelection guiWorldList = ReflectionHelper.getPrivateValue(GuiWorldSelection.class, (GuiWorldSelection) guiScreen, "field_184866_u", "selectionList");
                GuiListWorldSelectionEntry guiWorld = guiWorldList.getSelectedWorld();

                if(guiWorld != null)
                {
                    WorldSummary worldSummary = ReflectionHelper.getPrivateValue(GuiListWorldSelectionEntry.class, guiWorld, "field_186786_g", "worldSummary");
                    String worldDisplayName = worldSummary.getDisplayName();

                    MINECRAFT.displayGuiScreen(new GuiYesNo((result, id) ->
                    {
                        if(result)
                        {
                            MINECRAFT.displayGuiScreen(new GuiScreenWorking());
                            ISaveFormat saveFormat = MINECRAFT.getSaveLoader();
                            saveFormat.flushCache();
                            saveFormat.deleteWorldDirectory(worldSummary.getFileName() + "/DIM-1");
                            guiWorldList.refreshList();
                        }

                        MINECRAFT.displayGuiScreen(guiWorldList.getGuiWorldSelection());
                    }, I18n.format("gui." + NetherEx.MOD_ID + ":select_world.reset_nether_question", worldDisplayName), I18n.format("gui." + NetherEx.MOD_ID + ":select_world.reset_nether_warning", worldDisplayName, worldDisplayName), I18n.format("gui." + NetherEx.MOD_ID + ":select_world.reset"), I18n.format("gui.cancel"), 0));
                }
            }
        }
        else if(guiScreen instanceof GuiCreateWorld)
        {
            GuiCreateWorld guiCreateWorld = (GuiCreateWorld) guiScreen;
            int selectedWorldType = ReflectionHelper.getPrivateValue(GuiCreateWorld.class, guiCreateWorld, "field_146331_K", "selectedIndex");

            GuiButton button = guiButtons.get(8);

            if(button != null)
            {
                if(WorldType.WORLD_TYPES[selectedWorldType] == NetherEx.WORLD_TYPE)
                {
                    if(NetherEx.LOST_CITIES_LOADED)
                    {
                        button.displayString = I18n.format("gui.netherex:create_world.customize.lost_cities");
                    }
                    else if(NetherEx.BIOMES_O_PLENTY_LOADED)
                    {
                        button.displayString = I18n.format("gui.netherex:create_world.customize.biomes_o_plenty");
                    }
                }
                else
                {
                    button.displayString = I18n.format("selectWorld.customizeType");
                }
            }
        }
    }

    private static int getButtonId(List<GuiButton> buttons)
    {
        int buttonId = 0;

        buttons.sort(Comparator.comparingInt(button -> button.id));

        for(GuiButton button : buttons)
        {
            if(buttonId == button.id)
            {
                buttonId++;
            }
        }

        return buttonId;
    }
}
