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

import logictechcorp.libraryex.client.gui.ScrollableButtonList;
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
    private CreateWorldScreen createWorldScreen;
    private List<WorldType> compatibleWorldTypes;
    private ScrollableButtonList guiScrollableButtonList;

    public CustomizeNetherExWorldTypeScreen(CreateWorldScreen createWorldScreen, List<WorldType> compatibleWorldTypes)
    {
        super(new TranslationTextComponent("selectWorld.customizeType"));
        this.createWorldScreen = createWorldScreen;
        this.compatibleWorldTypes = compatibleWorldTypes;
    }

    @Override
    public void init()
    {
        this.addButton(new Button((this.width / 2) - 45, (this.height - 27), 90, 20, I18n.format("gui.done"), (button) -> this.minecraft.displayGuiScreen(this.createWorldScreen)));

        List<ScrollableButtonList.ButtonData> buttonDataList = new ArrayList<>();

        for(int i = 0; i < this.compatibleWorldTypes.size(); i++)
        {
            WorldType worldType = this.compatibleWorldTypes.get(i);
            buttonDataList.add(new ScrollableButtonList.ButtonData(I18n.format("selectWorld.customizeType") + " " + I18n.format(worldType.getTranslationKey()), i * 160, button -> worldType.onCustomizeButton(this.minecraft, this.createWorldScreen)));
        }

        this.guiScrollableButtonList = new ScrollableButtonList(this.createWorldScreen, this.width, this.height, 32, this.height - 32, buttonDataList);
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
        if(this.guiScrollableButtonList.mouseClicked(mouseX, mouseY, mouseButton))
        {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state)
    {
        if(this.guiScrollableButtonList.mouseReleased(mouseX, mouseY, state))
        {
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, state);
    }

    public CreateWorldScreen getCreateWorldScreen()
    {
        return this.createWorldScreen;
    }
}