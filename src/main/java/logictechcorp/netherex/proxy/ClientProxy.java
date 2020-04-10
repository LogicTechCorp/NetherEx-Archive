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

import logictechcorp.netherex.block.NetherExBlocks;
import logictechcorp.netherex.client.particle.SporeCreeperExplosionEmitterParticle;
import logictechcorp.netherex.client.particle.SporeCreeperExplosionParticle;
import logictechcorp.netherex.client.render.entity.*;
import logictechcorp.netherex.entity.NetherExEntityTypes;
import logictechcorp.netherex.particle.NetherExParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy
{
    public ClientProxy()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::onRegisterParticleFactory);
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
        RenderType cutoutMipped = RenderType.getCutoutMipped();
        RenderType cutout = RenderType.getCutout();
        RenderTypeLookup.setRenderLayer(NetherExBlocks.QUARTZ_ORE.get(), cutout);
        RenderTypeLookup.setRenderLayer(NetherExBlocks.THORNSTALK.get(), cutout);
        RenderTypeLookup.setRenderLayer(NetherExBlocks.BROWN_ELDER_MUSHROOM.get(), cutout);
        RenderTypeLookup.setRenderLayer(NetherExBlocks.RED_ELDER_MUSHROOM.get(), cutout);
        RenderTypeLookup.setRenderLayer(NetherExBlocks.ENOKI_MUSHROOM_CAP.get(), cutout);
        RenderTypeLookup.setRenderLayer(NetherExBlocks.ENOKI_MUSHROOM_STEM.get(), cutout);
        RenderTypeLookup.setRenderLayer(NetherExBlocks.BLUE_FIRE.get(), cutout);
        RenderTypeLookup.setRenderLayer(NetherExBlocks.ENOKI_MUSHROOM_CAP.get(), cutout);
        RenderTypeLookup.setRenderLayer(NetherExBlocks.ENOKI_MUSHROOM_STEM.get(), cutout);
        RenderType translucent = RenderType.getTranslucent();
        RenderTypeLookup.setRenderLayer(NetherExBlocks.FROSTBURN_ICE.get(), translucent);
        RenderTypeLookup.setRenderLayer(NetherExBlocks.SOUL_GLASS.get(), translucent);
        RenderTypeLookup.setRenderLayer(NetherExBlocks.SOUL_GLASS_PANE.get(), translucent);

        RenderingRegistry.registerEntityRenderingHandler(NetherExEntityTypes.MOGUS.get(), MogusRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NetherExEntityTypes.SALAMANDER.get(), SalamanderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NetherExEntityTypes.SPINOUT.get(), SpinoutRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NetherExEntityTypes.SPORE.get(), SporeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NetherExEntityTypes.SPORE_CREEPER.get(), SporeCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NetherExEntityTypes.WIGHT.get(), WightRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NetherExEntityTypes.COOLMAR_SPIDER.get(), CoolmarSpiderRenderer::new);
    }

    private void onRegisterParticleFactory(ParticleFactoryRegisterEvent event)
    {
        ParticleManager particleManager = Minecraft.getInstance().particles;
        particleManager.registerFactory(NetherExParticles.SPORE_CREEPER_EXPLOSION.get(), SporeCreeperExplosionParticle.Factory::new);
        particleManager.registerFactory(NetherExParticles.SPORE_CREEPER_EXPLOSION_EMITTER.get(), new SporeCreeperExplosionEmitterParticle.Factory());
    }
}
