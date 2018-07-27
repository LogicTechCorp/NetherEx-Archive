/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

package nex.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEmber extends ModelBase
{
    private ModelRenderer base;
    private ModelRenderer body;
    private ModelRenderer headBottom;
    private ModelRenderer headMiddle;
    private ModelRenderer headTop;

    public ModelEmber()
    {
        base = new ModelRenderer(this, 2, 27);
        base.setRotationPoint(-2.0F, 23.0F, -2.0F);
        base.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
        body = new ModelRenderer(this, 0, 17);
        body.setRotationPoint(-2.5F, 18.0F, -2.5F);
        body.addBox(0.0F, 0.0F, 0.0F, 5, 5, 5);
        headBottom = new ModelRenderer(this, 2, 11);
        headBottom.setRotationPoint(-2.0F, 16.0F, -2.0F);
        headBottom.addBox(0.0F, 0.0F, 0.0F, 4, 2, 4);
        headMiddle = new ModelRenderer(this, 4, 6);
        headMiddle.setRotationPoint(-1.5F, 14.5F, -1.5F);
        headMiddle.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
        headTop = new ModelRenderer(this, 6, 2);
        headTop.setRotationPoint(-1.0F, 13.5F, -1.0F);
        headTop.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        base.render(scale);
        body.render(scale);
        headBottom.render(scale);
        headMiddle.render(scale);
        headTop.render(scale);
    }
}
