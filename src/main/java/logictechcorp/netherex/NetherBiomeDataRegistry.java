/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
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

package logictechcorp.netherex;

import logictechcorp.libraryex.world.biome.data.iface.IBiomeData;
import logictechcorp.libraryex.world.biome.data.iface.IBiomeDataRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class NetherBiomeDataRegistry implements IBiomeDataRegistry
{
    static final IBiomeDataRegistry INSTANCE = new NetherBiomeDataRegistry();

    private final Map<ResourceLocation, IBiomeData> biomeData = new HashMap<>();
    private final Map<IBiomeData, BiomeManager.BiomeEntry> biomeEntries = new ConcurrentHashMap<>();

    private NetherBiomeDataRegistry()
    {
    }

    @Override
    public void registerBiomeData(IBiomeData biomeData)
    {
        if(biomeData == null || biomeData.getBiome() == null)
        {
            return;
        }

        Biome biome = biomeData.getBiome();
        ResourceLocation biomeRegistryName = biome.getRegistryName();

        if(!this.biomeData.containsKey(biomeRegistryName))
        {
            this.biomeData.put(biomeRegistryName, biomeData);
            this.biomeEntries.put(biomeData, new BiomeManager.BiomeEntry(biome, biomeData.getBiomeGenerationWeight()));
        }
        else
        {
            this.biomeEntries.get(biomeData).itemWeight = biomeData.getBiomeGenerationWeight();
        }
    }

    @Override
    public void unregisterBiomeData(Biome biome)
    {
        ResourceLocation biomeRegistryName = biome.getRegistryName();
        IBiomeData biomeData = this.biomeData.get(biomeRegistryName);

        if(biomeData != null)
        {
            this.biomeEntries.remove(biomeData);
            this.biomeData.remove(biomeRegistryName);
        }
    }

    @Override
    public boolean hasBiomeData(Biome biome)
    {
        return this.biomeData.containsKey(biome.getRegistryName());
    }

    @Override
    public IBiomeData getBiomeData(Biome biome)
    {
        return this.biomeData.get(biome.getRegistryName());
    }

    @Override
    public Map<ResourceLocation, IBiomeData> getBiomeData()
    {
        return Collections.unmodifiableMap(this.biomeData);
    }

    @Override
    public Map<IBiomeData, BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return Collections.unmodifiableMap(this.biomeEntries);
    }
}
