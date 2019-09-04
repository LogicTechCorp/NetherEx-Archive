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

package logictechcorp.netherex.particle;

import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(NetherEx.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetherExParticles
{
    public static final BasicParticleType SPORE_CREEPER_EXPLOSION = InjectionHelper.nullValue();
    public static final BasicParticleType SPORE_CREEPER_EXPLOSION_EMITTER = InjectionHelper.nullValue();

    @SubscribeEvent
    public static void onRegisterParticle(RegistryEvent.Register<ParticleType<?>> event)
    {
        registerParticle("spore_creeper_explosion", new BasicParticleType(true));
        registerParticle("spore_creeper_explosion_emitter", new BasicParticleType(true));
    }

    private static void registerParticle(String name, ParticleType<?> particleType)
    {
        ForgeRegistries.PARTICLE_TYPES.register(particleType.setRegistryName(name));
    }
}
