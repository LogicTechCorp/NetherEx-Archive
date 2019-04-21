/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
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

package logictechcorp.netherex.client.gui;

import logictechcorp.libraryex.client.gui.GuiScrollableButtonList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.WorldType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiCustomizeWorld extends GuiScreen
{
    private GuiCreateWorld guiCreateWorld;
    private List<WorldType> compatibleWorldTypes;
    private GuiScrollableButtonList guiScrollableButtonList;

    public GuiCustomizeWorld(GuiCreateWorld guiCreateWorld, List<WorldType> compatibleWorldTypes)
    {
        this.guiCreateWorld = guiCreateWorld;
        this.compatibleWorldTypes = compatibleWorldTypes;
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(300, (this.width / 2) - 45, (this.height - 27), 90, 20, I18n.format("gui.done")));

        List<GuiScrollableButtonList.GuiScrollableButtonData> guiScrollableButtonData = new ArrayList<>();

        for(int i = 0; i < this.compatibleWorldTypes.size(); i++)
        {
            guiScrollableButtonData.add(new GuiScrollableButtonList.GuiScrollableButtonData(i, I18n.format("selectWorld.customizeType") + " " + I18n.format(this.compatibleWorldTypes.get(i).getTranslationKey())));
        }

        this.guiScrollableButtonList = new GuiScrollableButtonList(this.mc, this.width, this.height, 32, this.height - 32, guiScrollableButtonData, this::actionPerformed);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.guiScrollableButtonList.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.guiScrollableButtonList.handleMouseInput();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.guiScrollableButtonList.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
        this.guiScrollableButtonList.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        int buttonId = button.id;

        if(buttonId == 300)
        {
            this.mc.displayGuiScreen(this.guiCreateWorld);
        }
        else
        {
            this.compatibleWorldTypes.get(buttonId).onCustomizeButton(this.mc, this.guiCreateWorld);
        }
    }
}
