/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import nex.NetherEx;
import nex.world.biome.NetherBiome;
import nex.world.gen.feature.BiomeFeature;

public class NetherExRegistries
{
    public static final IForgeRegistry<NetherBiome> NETHER_BIOMES = GameRegistry.findRegistry(NetherBiome.class);
    public static final IForgeRegistry<BiomeFeature> BIOME_FEATURES = GameRegistry.findRegistry(BiomeFeature.class);

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBlocks(RegistryEvent.NewRegistry event)
        {
            new RegistryBuilder<NetherBiome>().setName(new ResourceLocation(NetherEx.MOD_ID + ":nether_biomes")).setType(NetherBiome.class).setMaxID(255).disableSaving().create();
            new RegistryBuilder<BiomeFeature>().setName(new ResourceLocation(NetherEx.MOD_ID + ":biome_features")).setType(BiomeFeature.class).setMaxID(255).disableSaving().create();
        }
    }

    public static NetherBiome getNetherBiome(ResourceLocation identifier)
    {
        if(NetherExRegistries.NETHER_BIOMES.containsKey(identifier))
        {
            return NetherExRegistries.NETHER_BIOMES.getValue(identifier);
        }

        return null;
    }

    public static BiomeFeature getBiomeFeature(ResourceLocation identifier)
    {
        if(NetherExRegistries.BIOME_FEATURES.containsKey(identifier))
        {
            return NetherExRegistries.BIOME_FEATURES.getValue(identifier);
        }

        return null;
    }
}
