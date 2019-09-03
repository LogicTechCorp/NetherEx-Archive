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

import logictechcorp.libraryex.utilities.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.NetherGenSettings;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(NetherEx.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetherExChunkGenerators
{
    public static final ChunkGeneratorType<NetherGenSettings, CaveChunkGenerator> NETHER = InjectionHelper.nullValue();

    @SubscribeEvent
    public static void onChunkGeneratorRegister(RegistryEvent.Register<ChunkGeneratorType<?, ?>> event)
    {
        registerChunkGenerator("nether", new ChunkGeneratorType<>(CaveChunkGenerator::new, true, NetherGenSettings::new));
    }

    private static void registerChunkGenerator(String name, ChunkGeneratorType<?, ?> chunkGeneratorType)
    {
        ForgeRegistries.CHUNK_GENERATOR_TYPES.register(chunkGeneratorType.setRegistryName(name));
    }
}
