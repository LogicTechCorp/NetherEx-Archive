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
import logictechcorp.netherex.client.render.entity.MogusRenderer;
import logictechcorp.netherex.client.render.entity.SpinoutRenderer;
import logictechcorp.netherex.client.render.entity.SporeCreeperRenderer;
import logictechcorp.netherex.client.render.entity.SporeRenderer;
import logictechcorp.netherex.entity.hostile.SpinoutEntity;
import logictechcorp.netherex.entity.hostile.SporeCreeperEntity;
import logictechcorp.netherex.entity.hostile.SporeEntity;
import logictechcorp.netherex.entity.neutral.MogusEntity;
import logictechcorp.netherex.particle.NetherExParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerHandlers()
    {
        super.registerHandlers();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onClientSetup);
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.addListener(this::onRegisterParticleFactory);
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(MogusEntity.class, MogusRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SpinoutEntity.class, SpinoutRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SporeEntity.class, SporeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SporeCreeperEntity.class, SporeCreeperRenderer::new);
    }

    private void onRegisterParticleFactory(ParticleFactoryRegisterEvent event)
    {
        ParticleManager particleManager = Minecraft.getInstance().particles;
        particleManager.registerFactory(NetherExParticles.SPORE_CREEPER_EXPLOSION, SporeCreeperExplosionParticle.Factory::new);
        particleManager.registerFactory(NetherExParticles.SPORE_CREEPER_EXPLOSION_EMITTER, new SporeCreeperExplosionEmitterParticle.Factory());
    }
}
