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

import logictechcorp.netherex.client.particle.SporeCreeperExplosionEmitterParticle;
import logictechcorp.netherex.client.particle.SporeCreeperExplosionParticle;
import logictechcorp.netherex.client.render.entity.*;
import logictechcorp.netherex.entity.hostile.*;
import logictechcorp.netherex.entity.neutral.MogusEntity;
import logictechcorp.netherex.entity.neutral.SalamanderEntity;
import logictechcorp.netherex.particle.NetherExParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
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
        RenderingRegistry.registerEntityRenderingHandler(MogusEntity.class, MogusRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SalamanderEntity.class, SalamanderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SpinoutEntity.class, SpinoutRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SporeEntity.class, SporeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SporeCreeperEntity.class, SporeCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WightEntity.class, WightRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CoolmarSpiderEntity.class, CoolmarSpiderRenderer::new);
    }

    private void onRegisterParticleFactory(ParticleFactoryRegisterEvent event)
    {
        ParticleManager particleManager = Minecraft.getInstance().particles;
        particleManager.registerFactory(NetherExParticles.SPORE_CREEPER_EXPLOSION.get(), SporeCreeperExplosionParticle.Factory::new);
        particleManager.registerFactory(NetherExParticles.SPORE_CREEPER_EXPLOSION_EMITTER.get(), new SporeCreeperExplosionEmitterParticle.Factory());
    }
}
