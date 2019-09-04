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

package logictechcorp.netherex.client.render.entity;

import logictechcorp.netherex.entity.hostile.SporeEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SporeModel extends EntityModel<SporeEntity>
{
    private RendererModel stageOne;
    private RendererModel stageTwo;
    private RendererModel stageThreeInner;
    private RendererModel stageThreeOuter;
    private RendererModel stageFour;
    private RendererModel stageFive;

    public SporeModel()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.stageOne = new RendererModel(this, 0, 0);
        this.stageOne.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.stageOne.addBox(-5.0F, 0.0F, -5.0F, 10, 12, 10);
        this.stageTwo = new RendererModel(this, 0, 22);
        this.stageTwo.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.stageTwo.addBox(-5.5F, 0.0F, -5.5F, 11, 13, 11);
        this.stageThreeInner = new RendererModel(this, 0, 46);
        this.stageThreeInner.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.stageThreeInner.addBox(-6.0F, 0.0F, -6.0F, 12, 14, 12);
        this.stageThreeOuter = new RendererModel(this, 48, 45);
        this.stageThreeOuter.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.stageThreeOuter.addBox(-6.5F, 0.0F, -6.5F, 13, 15, 13);
        this.stageFour = new RendererModel(this, 0, 72);
        this.stageFour.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.stageFour.addBox(-5.0F, 0.0F, -5.0F, 10, 8, 10);
        this.stageFive = new RendererModel(this, 0, 90);
        this.stageFive.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.stageFive.addBox(-5.5F, 0.0F, -5.5F, 11, 9, 11);
    }

    @Override
    public void render(SporeEntity spore, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
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
