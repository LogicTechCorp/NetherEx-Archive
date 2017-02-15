/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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
import nex.entity.monster.EntitySpore;

public class ModelSpore extends ModelBase
{
    private ModelRenderer stageOne;
    private ModelRenderer stageTwo;
    private ModelRenderer stageThreeInner;
    private ModelRenderer stageThreeOuter;
    private ModelRenderer stageFour;
    private ModelRenderer stageFive;

    public ModelSpore()
    {
        textureWidth = 128;
        textureHeight = 128;

        stageOne = new ModelRenderer(this, 0, 0);
        stageOne.setRotationPoint(0.0F, 12.0F, 0.0F);
        stageOne.addBox(-5.0F, 0.0F, -5.0F, 10, 12, 10);
        stageTwo = new ModelRenderer(this, 0, 22);
        stageTwo.setRotationPoint(0.0F, 11.0F, 0.0F);
        stageTwo.addBox(-5.5F, 0.0F, -5.5F, 11, 13, 11);
        stageThreeInner = new ModelRenderer(this, 0, 46);
        stageThreeInner.setRotationPoint(0.0F, 10.0F, 0.0F);
        stageThreeInner.addBox(-6.0F, 0.0F, -6.0F, 12, 14, 12);
        stageThreeOuter = new ModelRenderer(this, 48, 45);
        stageThreeOuter.setRotationPoint(0.0F, 9.0F, 0.0F);
        stageThreeOuter.addBox(-6.5F, 0.0F, -6.5F, 13, 15, 13);
        stageFour = new ModelRenderer(this, 0, 72);
        stageFour.setRotationPoint(0.0F, 2.0F, 0.0F);
        stageFour.addBox(-5.0F, 0.0F, -5.0F, 10, 8, 10);
        stageFive = new ModelRenderer(this, 0, 90);
        stageFive.setRotationPoint(0.0F, 1.0F, 0.0F);
        stageFive.addBox(-5.5F, 0.0F, -5.5F, 11, 9, 11);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        EntitySpore spore = (EntitySpore) entity;

        if(spore.getStage() == 0)
        {
            stageOne.render(scale);
        }
        else if(spore.getStage() == 1)
        {
            stageOne.render(scale);
            stageTwo.render(scale);
        }
        else if(spore.getStage() == 2)
        {
            stageOne.render(scale);
            stageTwo.render(scale);
            stageThreeInner.render(scale);
            stageThreeOuter.render(scale);
        }
        else if(spore.getStage() == 3)
        {
            stageOne.render(scale);
            stageTwo.render(scale);
            stageThreeInner.render(scale);
            stageThreeOuter.render(scale);
            stageFour.render(scale);
        }
        else
        {
            stageOne.render(scale);
            stageTwo.render(scale);
            stageThreeInner.render(scale);
            stageThreeOuter.render(scale);
            stageFour.render(scale);
            stageFive.render(scale);
        }
    }
}
