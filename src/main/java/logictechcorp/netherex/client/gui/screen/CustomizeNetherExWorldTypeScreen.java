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

package logictechcorp.netherex.client.gui.screen;

import logictechcorp.libraryex.client.gui.GuiScrollableButtonList;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.WorldType;

import java.util.ArrayList;
import java.util.List;

public class CustomizeNetherExWorldTypeScreen extends Screen
{
    private CreateWorldScreen guiCreateWorld;
    private List<WorldType> compatibleWorldTypes;
    private GuiScrollableButtonList guiScrollableButtonList;

    public CustomizeNetherExWorldTypeScreen(CreateWorldScreen guiCreateWorld, List<WorldType> compatibleWorldTypes)
    {
        super(new TranslationTextComponent("selectWorld.customizeType"));
        this.guiCreateWorld = guiCreateWorld;
        this.compatibleWorldTypes = compatibleWorldTypes;
    }

    @Override
    public void init()
    {
        this.buttons.clear();
        this.buttons.add(new Button((this.width / 2) - 45, (this.height - 27), 90, 20, I18n.format("gui.done"), (button) -> this.minecraft.displayGuiScreen(this.guiCreateWorld)));

        List<GuiScrollableButtonList.GuiScrollableButtonData> guiScrollableButtonData = new ArrayList<>();

        for(int i = 0; i < this.compatibleWorldTypes.size(); i++)
        {
            guiScrollableButtonData.add(new GuiScrollableButtonList.GuiScrollableButtonData(i, I18n.format("selectWorld.customizeType") + " " + I18n.format(this.compatibleWorldTypes.get(i).getTranslationKey())));
        }

        this.guiScrollableButtonList = new GuiScrollableButtonList(this.minecraft, this.width, this.height, 32, this.height - 32, guiScrollableButtonData, (button) -> this.compatibleWorldTypes.get(this.buttons.indexOf(button)).onCustomizeButton(this.minecraft, this.guiCreateWorld));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground();
        this.guiScrollableButtonList.render(mouseX, mouseY, partialTicks);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton)
    {
        this.guiScrollableButtonList.mouseClicked(mouseX, mouseY, mouseButton);
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state)
    {
        this.guiScrollableButtonList.mouseReleased(mouseX, mouseY, state);
        return super.mouseReleased(mouseX, mouseY, state);
    }
}