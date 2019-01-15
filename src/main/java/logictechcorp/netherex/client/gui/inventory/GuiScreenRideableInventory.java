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

package logictechcorp.netherex.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScreenRideableInventory extends GuiContainer
{
    private final IInventory playerInventory;
    private final IInventory horseInventory;
    private final AbstractHorse rideable;
    private final ResourceLocation guiTexture;
    private float mouseX;
    private float mouseY;

    public GuiScreenRideableInventory(Container container, IInventory playerInventory, IInventory horseInventory, AbstractHorse rideable, ResourceLocation guiTexture)
    {
        super(container);
        this.playerInventory = playerInventory;
        this.horseInventory = horseInventory;
        this.rideable = rideable;
        this.guiTexture = guiTexture;
        this.allowUserInput = false;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(this.horseInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.guiTexture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if(this.rideable instanceof AbstractChestHorse)
        {
            AbstractChestHorse chestHorse = (AbstractChestHorse) this.rideable;

            if(chestHorse.hasChest())
            {
                this.drawTexturedModalRect(i + 79, j + 17, 0, this.ySize, chestHorse.getInventoryColumns() * 18, 54);
            }
        }

        if(this.rideable.canBeSaddled())
        {
            this.drawTexturedModalRect(i + 7, j + 35 - 18, 18, this.ySize + 54, 18, 18);
        }

        if(this.rideable.wearsArmor())
        {
            if(this.rideable instanceof EntityLlama)
            {
                this.drawTexturedModalRect(i + 7, j + 35, 36, this.ySize + 54, 18, 18);
            }
            else
            {
                this.drawTexturedModalRect(i + 7, j + 35, 0, this.ySize + 54, 18, 18);
            }
        }

        GuiInventory.drawEntityOnScreen(i + 51, j + 60, 17, (float) (i + 51) - this.mouseX, (float) (j + 75 - 50) - this.mouseY, this.rideable);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.mouseX = (float) mouseX;
        this.mouseY = (float) mouseY;
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
