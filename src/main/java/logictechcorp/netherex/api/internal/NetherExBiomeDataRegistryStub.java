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

package logictechcorp.netherex.api.internal;

import logictechcorp.libraryex.api.world.biome.data.IBiomeData;
import logictechcorp.libraryex.api.world.biome.data.IBiomeDataRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

import java.util.HashMap;
import java.util.Map;

public final class NetherExBiomeDataRegistryStub implements IBiomeDataRegistry
{
    public static final IBiomeDataRegistry INSTANCE = new NetherExBiomeDataRegistryStub();

    private NetherExBiomeDataRegistryStub()
    {
    }

    @Override
    public void registerBiomeData(IBiomeData biomeData)
    {

    }

    @Override
    public void unregisterBiomeData(Biome biome)
    {

    }

    @Override
    public boolean hasBiomeData(Biome biome)
    {
        return false;
    }

    @Override
    public IBiomeData getBiomeData(Biome biome)
    {
        return null;
    }

    @Override
    public Map<ResourceLocation, IBiomeData> getBiomeData()
    {
        return new HashMap<>();
    }

    @Override
    public Map<ResourceLocation, BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return new HashMap<>();
    }
}
