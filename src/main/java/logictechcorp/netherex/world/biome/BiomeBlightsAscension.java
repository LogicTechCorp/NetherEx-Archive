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
import logictechcorp.netherex.NetherEx;

public class BiomeBlightsAscension extends BiomeNetherEx
{
    public BiomeBlightsAscension()
    {
        super(NetherEx.instance, new BiomeProperties("Blight's Ascension").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "blights_ascension");
    }

    @Override
    public BiomeInfo getInfo()
    {
        return null;
    }
}
