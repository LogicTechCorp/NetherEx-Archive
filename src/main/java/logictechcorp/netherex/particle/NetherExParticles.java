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

import logictechcorp.netherex.NetherEx;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NetherExParticles
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, NetherEx.MOD_ID);

    public static final RegistryObject<BasicParticleType> SPORE_CREEPER_EXPLOSION = PARTICLE_TYPES.register("spore_creeper_explosion", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SPORE_CREEPER_EXPLOSION_EMITTER = PARTICLE_TYPES.register("spore_creeper_explosion_emitter", () -> new BasicParticleType(true));
}
