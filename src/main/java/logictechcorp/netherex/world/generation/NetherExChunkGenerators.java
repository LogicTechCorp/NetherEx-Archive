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

package logictechcorp.netherex.world.generation;

import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.NetherChunkGenerator;
import net.minecraft.world.gen.NetherGenSettings;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NetherExChunkGenerators
{
    private static final DeferredRegister<ChunkGeneratorType<?, ?>> CHUNK_GENERATOR_OVERRIDES = new DeferredRegister<>(ForgeRegistries.CHUNK_GENERATOR_TYPES, "minecraft");

    public static final RegistryObject<ChunkGeneratorType<NetherGenSettings, NetherChunkGenerator>> CAVES = CHUNK_GENERATOR_OVERRIDES.register("caves", () -> new CaveChunkGeneratorTypeOverride(CaveChunkGenerator::new, true, NetherGenSettings::new));

    public static void registerOverrides(IEventBus modEventBus)
    {
        CHUNK_GENERATOR_OVERRIDES.register(modEventBus);
    }
}
