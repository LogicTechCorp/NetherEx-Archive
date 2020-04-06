/*
 * NetherEx
 * Copyright (c) 2016-2020 by LogicTechCorp
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

package logictechcorp.netherex.client.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.potion.NetherExEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Dist.CLIENT)
public class RenderEventHandler
{
    private static final Minecraft MINECRAFT = Minecraft.getInstance();

    @SubscribeEvent
    public static void onFOVChange(FOVUpdateEvent event)
    {
        PlayerEntity player = event.getEntity();

        if(player.isPotionActive(NetherExEffects.FROZEN.get()))
        {
            float fov = 1.0F;

            if(player.abilities.isFlying)
            {
                fov *= 1.1F;
            }

            if(player.isHandActive() && player.getActiveItemStack().getItem() instanceof BowItem)
            {
                float useCount = (float) player.getItemInUseMaxCount() / 20.0F;

                if(useCount > 1.0F)
                {
                    useCount = 1.0F;
                }
                else
                {
                    useCount = useCount * useCount;
                }

                fov *= 1.0F - useCount * 0.15F;
            }

            event.setNewfov(fov);
        }
    }

    @SubscribeEvent
    public static void onRenderSpecificHand(RenderHandEvent event)
    {
        PlayerEntity player = MINECRAFT.player;
        MatrixStack matrixStack = event.getMatrixStack();

        if(player != null && player.isPotionActive(NetherExEffects.FIRE_BURNING.get()))
        {
            renderFirstPersonBlueFire(matrixStack);
        }
    }

    @SubscribeEvent
    public static void onRenderLivingPost(RenderLivingEvent.Post<?, ?> event)
    {
        MatrixStack matrixStack = event.getMatrixStack();
        IRenderTypeBuffer renderTypeBuffer = event.getBuffers();
        LivingEntity entity = event.getEntity();

        if(entity.isPotionActive(NetherExEffects.FIRE_BURNING.get()))
        {
            renderThirdPersonBlueFire(matrixStack, renderTypeBuffer, entity);
        }
    }

    public static final Material BLUE_FIRE_LAYER_O = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(NetherEx.MOD_ID, "block/blue_fire_layer_0"));
    public static final Material BLUE_FIRE_LAYER_1 = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(NetherEx.MOD_ID, "block/blue_fire_layer_1"));

    private static void renderThirdPersonBlueFire(MatrixStack matrixStack, IRenderTypeBuffer buffer, Entity entity)
    {
        TextureAtlasSprite blueFireLayerOSprite = BLUE_FIRE_LAYER_O.getSprite();
        TextureAtlasSprite blueFireLayer1Sprite = BLUE_FIRE_LAYER_1.getSprite();
        matrixStack.push();
        float f = entity.getWidth() * 1.4F;
        matrixStack.scale(f, f, f);
        float f1 = 0.5F;
        float f3 = entity.getHeight() / f;
        float f4 = 0.0F;
        matrixStack.rotate(Vector3f.YP.rotationDegrees(-MINECRAFT.getRenderManager().info.getYaw()));
        matrixStack.translate(0.0D, 0.0D, (-0.3F + (float) ((int) f3) * 0.02F));
        float f5 = 0.0F;
        int i = 0;
        IVertexBuilder builder = buffer.getBuffer(Atlases.getCutoutBlockType());

        for(MatrixStack.Entry matrixEntry = matrixStack.getLast(); f3 > 0.0F; i++)
        {
            TextureAtlasSprite sprite = i % 2 == 0 ? blueFireLayerOSprite : blueFireLayer1Sprite;
            float minU = sprite.getMinU();
            float minV = sprite.getMinV();
            float maxU = sprite.getMaxU();
            float maxV = sprite.getMaxV();

            if(i / 2 % 2 == 0)
            {
                float f10 = maxU;
                maxU = minU;
                minU = f10;
            }

            fireVertex(matrixEntry, builder, f1 - 0.0F, 0.0F - f4, f5, maxU, maxV);
            fireVertex(matrixEntry, builder, -f1 - 0.0F, 0.0F - f4, f5, minU, maxV);
            fireVertex(matrixEntry, builder, -f1 - 0.0F, 1.4F - f4, f5, minU, minV);
            fireVertex(matrixEntry, builder, f1 - 0.0F, 1.4F - f4, f5, maxU, minV);
            f3 -= 0.45F;
            f4 -= 0.45F;
            f1 *= 0.9F;
            f5 += 0.03F;
        }

        matrixStack.pop();
    }

    private static void fireVertex(MatrixStack.Entry matrixEntry, IVertexBuilder builder, float x, float y, float z, float texU, float texV)
    {
        builder.pos(matrixEntry.getMatrix(), x, y, z).color(255, 255, 255, 255).tex(texU, texV).overlay(0, 10).lightmap(240).normal(matrixEntry.getNormal(), 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void renderFirstPersonBlueFire(MatrixStack matrixStack)
    {
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        RenderSystem.depthFunc(519);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        TextureAtlasSprite sprite = BLUE_FIRE_LAYER_1.getSprite();
        MINECRAFT.getTextureManager().bindTexture(sprite.getAtlasTexture().getTextureLocation());
        float f = sprite.getMinU();
        float f1 = sprite.getMaxU();
        float f2 = (f + f1) / 2.0F;
        float f3 = sprite.getMinV();
        float f4 = sprite.getMaxV();
        float f5 = (f3 + f4) / 2.0F;
        float f6 = sprite.getUvShrinkRatio();
        float f7 = MathHelper.lerp(f6, f, f2);
        float f8 = MathHelper.lerp(f6, f1, f2);
        float f9 = MathHelper.lerp(f6, f3, f5);
        float f10 = MathHelper.lerp(f6, f4, f5);

        for(int i = 0; i < 2; i++)
        {
            matrixStack.push();
            matrixStack.translate(((float) (-(i * 2 - 1)) * 0.24F), -0.3F, 0.0D);
            matrixStack.rotate(Vector3f.YP.rotationDegrees((float) (i * 2 - 1) * 10.0F));
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            builder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
            builder.pos(matrix4f, -0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).tex(f8, f10).endVertex();
            builder.pos(matrix4f, 0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).tex(f7, f10).endVertex();
            builder.pos(matrix4f, 0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).tex(f7, f9).endVertex();
            builder.pos(matrix4f, -0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).tex(f8, f9).endVertex();
            builder.finishDrawing();
            WorldVertexBufferUploader.draw(builder);
            matrixStack.pop();
        }

        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(515);
    }
}
