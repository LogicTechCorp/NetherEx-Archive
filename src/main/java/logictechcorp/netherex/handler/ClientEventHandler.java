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

package logictechcorp.netherex.handler;

import com.mojang.blaze3d.platform.GlStateManager;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.potion.NetherExEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler
{
    private static final Minecraft MINECRAFT = Minecraft.getInstance();

    @SubscribeEvent
    public static void onFOVChange(FOVUpdateEvent event)
    {
        PlayerEntity player = event.getEntity();

        if(player.isPotionActive(NetherExEffects.FROZEN))
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
    public static void onRenderLivingSpecialPost(RenderLivingEvent.Specials.Post event)
    {
        LivingEntity entity = event.getEntity();
        float partialTicks = event.getPartialRenderTick();

        if(entity.isPotionActive(NetherExEffects.FIRE_BURNING))
        {
            double posX = event.getX() + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
            double posY = event.getY() + (entity.posY - entity.lastTickPosY + 1) * (double) partialTicks;
            double posZ = event.getZ() + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
            renderEntityOnBlueFire(entity, posX, posY, posZ);
        }
    }

    private static void renderBlueFireInFirstPerson()
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.9F);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        for(int i = 0; i < 2; i++)
        {
            GlStateManager.pushMatrix();
            TextureAtlasSprite sprite = MINECRAFT.getTextureMap().getSprite(new ResourceLocation(NetherEx.MOD_ID, "block/blue_fire_layer_1"));
            MINECRAFT.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            float minU = sprite.getMinU();
            float maxU = sprite.getMaxU();
            float minV = sprite.getMinV();
            float maxV = sprite.getMaxV();
            GlStateManager.translatef((float) (-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            GlStateManager.rotatef((float) (i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
            builder.begin(7, DefaultVertexFormats.POSITION_TEX);
            builder.pos(-0.5D, -0.5D, -0.5D).tex(maxU, maxV).endVertex();
            builder.pos(0.5D, -0.5D, -0.5D).tex(minU, maxV).endVertex();
            builder.pos(0.5D, 0.5D, -0.5D).tex(minU, minV).endVertex();
            builder.pos(-0.5D, 0.5D, -0.5D).tex(maxU, minV).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
        }

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }

    private static void renderEntityOnBlueFire(Entity entity, double posX, double posY, double posZ)
    {
        GlStateManager.disableLighting();
        AtlasTexture textureMap = MINECRAFT.getTextureMap();
        TextureAtlasSprite blueFireLayer1 = textureMap.getSprite(new ResourceLocation(NetherEx.MOD_ID, "block/blue_fire_layer_0"));
        TextureAtlasSprite blueFireLayer2 = textureMap.getSprite(new ResourceLocation(NetherEx.MOD_ID, "block/blue_fire_layer_1"));
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float) posX, (float) posY, (float) posZ);
        float scale = entity.getSize(Pose.STANDING).width * 1.4F;
        GlStateManager.scalef(scale, scale, scale);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        float renderX = 0.5F;
        float height = entity.getSize(Pose.STANDING).height / scale;
        float renderY = (float) (entity.posY - entity.getBoundingBox().minY);
        GlStateManager.rotatef(-MINECRAFT.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.translatef(0.0F, 0.0F, -0.3F + (float) ((int) height) * 0.02F);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        float renderZ = 0.0F;
        int stage = 0;
        builder.begin(7, DefaultVertexFormats.POSITION_TEX);

        while(height > 0.0F)
        {
            TextureAtlasSprite sprite = stage % 2 == 0 ? blueFireLayer1 : blueFireLayer2;
            MINECRAFT.textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            float minU = sprite.getMinU();
            float minV = sprite.getMinV();
            float maxU = sprite.getMaxU();
            float maxV = sprite.getMaxV();

            if(stage / 2 % 2 == 0)
            {
                float tempU = maxU;
                maxU = minU;
                minU = tempU;
            }

            builder.pos((renderX - 0.0F), (0.0F - renderY), renderZ).tex(maxU, maxV).endVertex();
            builder.pos((-renderX - 0.0F), (0.0F - renderY), renderZ).tex(minU, maxV).endVertex();
            builder.pos((-renderX - 0.0F), (1.4F - renderY), renderZ).tex(minU, minV).endVertex();
            builder.pos((renderX - 0.0F), (1.4F - renderY), renderZ).tex(maxU, minV).endVertex();
            height -= 0.45F;
            renderY -= 0.45F;
            renderX *= 0.9F;
            renderZ += 0.03F;
            stage++;
        }

        tessellator.draw();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
    }
}
