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

package logictechcorp.netherex.client.model.entity;

import logictechcorp.netherex.entity.monster.EntitySpore;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSpore extends ModelBase
{
    private final ModelRenderer stageOne;
    private final ModelRenderer stageTwo;
    private final ModelRenderer stageThreeInner;
    private final ModelRenderer stageThreeOuter;
    private final ModelRenderer stageFour;
    private final ModelRenderer stageFive;

    public ModelSpore()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.stageOne = new ModelRenderer(this, 0, 0);
        this.stageOne.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.stageOne.addBox(-5.0F, 0.0F, -5.0F, 10, 12, 10);
        this.stageTwo = new ModelRenderer(this, 0, 22);
        this.stageTwo.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.stageTwo.addBox(-5.5F, 0.0F, -5.5F, 11, 13, 11);
        this.stageThreeInner = new ModelRenderer(this, 0, 46);
        this.stageThreeInner.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.stageThreeInner.addBox(-6.0F, 0.0F, -6.0F, 12, 14, 12);
        this.stageThreeOuter = new ModelRenderer(this, 48, 45);
        this.stageThreeOuter.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.stageThreeOuter.addBox(-6.5F, 0.0F, -6.5F, 13, 15, 13);
        this.stageFour = new ModelRenderer(this, 0, 72);
        this.stageFour.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.stageFour.addBox(-5.0F, 0.0F, -5.0F, 10, 8, 10);
        this.stageFive = new ModelRenderer(this, 0, 90);
        this.stageFive.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.stageFive.addBox(-5.5F, 0.0F, -5.5F, 11, 9, 11);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        EntitySpore spore = (EntitySpore) entity;

        if(spore.getStage() == 0)
        {
            this.stageOne.render(scale);
        }
        else if(spore.getStage() == 1)
        {
            this.stageOne.render(scale);
            this.stageTwo.render(scale);
        }
        else if(spore.getStage() == 2)
        {
            this.stageOne.render(scale);
            this.stageTwo.render(scale);
            this.stageThreeInner.render(scale);
            this.stageThreeOuter.render(scale);
        }
        else if(spore.getStage() == 3)
        {
            this.stageOne.render(scale);
            this.stageTwo.render(scale);
            this.stageThreeInner.render(scale);
            this.stageThreeOuter.render(scale);
            this.stageFour.render(scale);
        }
        else
        {
            this.stageOne.render(scale);
            this.stageTwo.render(scale);
            this.stageThreeInner.render(scale);
            this.stageThreeOuter.render(scale);
            this.stageFour.render(scale);
            this.stageFive.render(scale);
        }
    }
}
