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

package logictechcorp.netherex.proxy;

import logictechcorp.libraryex.proxy.IProxy;
import logictechcorp.netherex.client.render.entity.*;
import logictechcorp.netherex.client.render.tileentity.RenderNetherReactorCore;
import logictechcorp.netherex.client.render.tileentity.RenderUrnOfSorrow;
import logictechcorp.netherex.entity.boss.EntityGhastQueen;
import logictechcorp.netherex.entity.item.EntityObsidianBoat;
import logictechcorp.netherex.entity.monster.*;
import logictechcorp.netherex.entity.neutral.EntityGoldGolem;
import logictechcorp.netherex.entity.neutral.EntityMogus;
import logictechcorp.netherex.entity.neutral.EntitySalamander;
import logictechcorp.netherex.entity.passive.EntityBonspider;
import logictechcorp.netherex.entity.passive.EntityPigtificate;
import logictechcorp.netherex.entity.passive.EntityPigtificateLeader;
import logictechcorp.netherex.entity.projectile.EntityBlueFireball;
import logictechcorp.netherex.entity.projectile.EntityGhastQueenFireball;
import logictechcorp.netherex.entity.projectile.EntityGhastlingFireball;
import logictechcorp.netherex.init.NetherExParticleTypes;
import logictechcorp.netherex.tileentity.TileEntityNetherReactorCore;
import logictechcorp.netherex.tileentity.TileEntityUrnOfSorrow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy
{
    private final Minecraft minecraft = Minecraft.getMinecraft();

    @Override
    public void preInit()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityUrnOfSorrow.class, new RenderUrnOfSorrow());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNetherReactorCore.class, new RenderNetherReactorCore());

        RenderingRegistry.registerEntityRenderingHandler(EntityGhastQueenFireball.class, RenderGhastQueenFireball::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhastlingFireball.class, RenderGhastlingFireball::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlueFireball.class, (RenderManager renderManager) -> new RenderBlueFireball(renderManager, 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityObsidianBoat.class, RenderObsidianBoat::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPigtificateLeader.class, RenderPigtificateLeader::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPigtificate.class, RenderPigtificate::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBonspider.class, RenderBonspider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGoldGolem.class, RenderGoldGolem::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMogus.class, RenderMogus::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySalamander.class, RenderSalamander::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWight.class, RenderWight::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityEmber.class, RenderEmber::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityNethermite.class, RenderNethermite::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpinout.class, RenderSpinout::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySporeCreeper.class, RenderSporeCreeper::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpore.class, RenderSpore::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhastling.class, RenderGhastling::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCoolmarSpider.class, RenderCoolmarSpider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBrute.class, RenderBrute::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFrost.class, RenderFrost::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHellhound.class, RenderHellhound::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhastQueen.class, RenderGhastQueen::new);
    }

    @Override
    public void init()
    {

    }

    @Override
    public void postInit()
    {

    }

    @Override
    public void spawnParticle(World world, int particleId, double posX, double posY, double posZ, double speedX, double speedY, double speedZ)
    {
        this.minecraft.effectRenderer.addEffect(NetherExParticleTypes.getFactory(particleId).createParticle(0, world, posX, posY, posZ, speedX, speedY, speedZ));
    }
}
