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

import logictechcorp.libraryex.utility.ArmorHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.item.EntityObsidianBoat;
import logictechcorp.netherex.init.NetherExEffects;
import logictechcorp.netherex.init.NetherExMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Side.CLIENT)
public class RenderHandler
{
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    @SubscribeEvent
    public static void onRenderBlockOverlay(RenderBlockOverlayEvent event)
    {
        RenderBlockOverlayEvent.OverlayType type = event.getOverlayType();
        EntityPlayer player = event.getPlayer();

        if(type == RenderBlockOverlayEvent.OverlayType.FIRE)
        {
            if(player.isRiding() && player.getRidingEntity() instanceof EntityObsidianBoat || ArmorHelper.isWearingFullArmorSet(player, NetherExMaterials.SALAMANDER_HIDE))
            {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLivingSpecialPost(RenderLivingEvent.Specials.Post event)
    {
        EntityLivingBase entity = event.getEntity();
        float partialTicks = event.getPartialRenderTick();

        double posX = event.getX() + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
        double posY = event.getY() + (entity.posY - entity.lastTickPosY + 1) * (double) partialTicks;
        double posZ = event.getZ() + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;

        if(entity.isPotionActive(NetherExEffects.BLUE_FIRE))
        {
            renderEntityOnBlueFire(entity, posX, posY, posZ);
        }
    }

    private static void renderBlueFireInFirstPerson()
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        for(int i = 0; i < 2; i++)
        {
            GlStateManager.pushMatrix();
            TextureAtlasSprite sprite = MINECRAFT.getTextureMapBlocks().getAtlasSprite(NetherEx.MOD_ID + ":blocks/blue_fire_layer_1");
            MINECRAFT.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            float minU = sprite.getMinU();
            float maxU = sprite.getMaxU();
            float minV = sprite.getMinV();
            float maxV = sprite.getMaxV();
            GlStateManager.translate((float) (-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            GlStateManager.rotate((float) (i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
            builder.begin(7, DefaultVertexFormats.POSITION_TEX);
            builder.pos(-0.5D, -0.5D, -0.5D).tex((double) maxU, (double) maxV).endVertex();
            builder.pos(0.5D, -0.5D, -0.5D).tex((double) minU, (double) maxV).endVertex();
            builder.pos(0.5D, 0.5D, -0.5D).tex((double) minU, (double) minV).endVertex();
            builder.pos(-0.5D, 0.5D, -0.5D).tex((double) maxU, (double) minV).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }

    public static void renderEntityOnBlueFire(Entity entity, double posX, double posY, double posZ)
    {
        GlStateManager.disableLighting();
        TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
        TextureAtlasSprite blueFireLayer1 = textureMap.getAtlasSprite(NetherEx.MOD_ID + ":blocks/blue_fire_layer_0");
        TextureAtlasSprite blueFireLayer2 = textureMap.getAtlasSprite(NetherEx.MOD_ID + ":blocks/blue_fire_layer_1");
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, (float) posZ);
        float scale = entity.width * 1.4F;
        GlStateManager.scale(scale, scale, scale);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        float renderX = 0.5F;
        float height = entity.height / scale;
        float renderY = (float) (entity.posY - entity.getEntityBoundingBox().minY);
        GlStateManager.rotate(-MINECRAFT.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, -0.3F + (float) ((int) height) * 0.02F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float renderZ = 0.0F;
        int stage = 0;
        builder.begin(7, DefaultVertexFormats.POSITION_TEX);

        while(height > 0.0F)
        {
            TextureAtlasSprite sprite = stage % 2 == 0 ? blueFireLayer1 : blueFireLayer2;
            MINECRAFT.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
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

            builder.pos((double) (renderX - 0.0F), (double) (0.0F - renderY), (double) renderZ).tex((double) maxU, (double) maxV).endVertex();
            builder.pos((double) (-renderX - 0.0F), (double) (0.0F - renderY), (double) renderZ).tex((double) minU, (double) maxV).endVertex();
            builder.pos((double) (-renderX - 0.0F), (double) (1.4F - renderY), (double) renderZ).tex((double) minU, (double) minV).endVertex();
            builder.pos((double) (renderX - 0.0F), (double) (1.4F - renderY), (double) renderZ).tex((double) maxU, (double) minV).endVertex();
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
