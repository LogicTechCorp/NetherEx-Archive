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

import logictechcorp.libraryex.api.world.biome.data.IBiomeData;
import logictechcorp.libraryex.api.world.biome.data.IBiomeDataRegistry;
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

    private final Map<ResourceLocation, IBiomeData> biomeData;
    private final Map<ResourceLocation, BiomeManager.BiomeEntry> biomeEntries;

    private NetherBiomeDataRegistry()
    {
        this.biomeData = new HashMap<>();
        this.biomeEntries = new ConcurrentHashMap<>();
    }

    @Override
    public void registerBiomeData(IBiomeData biomeData)
    {
        if(biomeData != null && biomeData.getBiome() != null)
        {
            Biome biome = biomeData.getBiome();
            ResourceLocation biomeRegistryName = biome.getRegistryName();

            if(!this.biomeData.containsKey(biomeRegistryName))
            {
                this.biomeData.put(biomeRegistryName, biomeData);
            }
            if(!biomeData.isSubBiome())
            {
                if(biomeData.isEnabled())
                {
                    this.biomeEntries.put(biomeRegistryName, new BiomeManager.BiomeEntry(biome, biomeData.getGenerationWeight()));
                }
                else
                {
                    this.biomeEntries.remove(biomeRegistryName);
                }
            }
        }
    }

    @Override
    public void unregisterBiomeData(Biome biome)
    {
        if(biome != null)
        {
            ResourceLocation biomeRegistryName = biome.getRegistryName();
            this.biomeData.remove(biomeRegistryName);
            this.biomeEntries.remove(biomeRegistryName);
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
    public Map<ResourceLocation, BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return Collections.unmodifiableMap(this.biomeEntries);
    }
}
