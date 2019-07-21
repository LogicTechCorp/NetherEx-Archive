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

package logictechcorp.netherex.client.gui;

import logictechcorp.netherex.NetherEx;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBreakingChanges extends GuiScreen
{
    private final GuiMainMenu guiMainMenu;

    public GuiBreakingChanges(GuiMainMenu guiMainMenu)
    {
        this.guiMainMenu = guiMainMenu;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 30, I18n.format("gui.done")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();

        int x = this.width / 2;
        int y = this.height / 4;

        for(int i = 0; i < 8; i++)
        {
            String s = I18n.format("information." + NetherEx.MOD_ID + ":breaking_changes." + (i + 1));
            this.drawCenteredString(this.fontRenderer, s, x, y, 0xFFFFFF);

            if(i == 2 || i == 5)
            {
                y += 10;
            }

            y += 10;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if(button.id == 0)
        {
            Minecraft.getMinecraft().displayGuiScreen(this.guiMainMenu);
        }
    }
}
