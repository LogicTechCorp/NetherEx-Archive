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

package logictechcorp.netherex.world.biome;

import logictechcorp.libraryex.world.biome.BiomeInfo;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;

public class NetherBiomeInfo extends BiomeInfo
{
    public NetherBiomeInfo(ResourceLocation biomeRegistryName, int weight, boolean enabled, boolean genDefaultFeatures)
    {
        super(biomeRegistryName, weight, enabled, genDefaultFeatures);
    }

    public NetherBiomeInfo()
    {
        super();
        this.biome = Biomes.HELL;
    }
}
