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

package nex.world.biome.additional;

public class AdditionalBiome
{
    private String name;
    private int weight;
    private OceanBlock oceanBlock;
    private String type;

    public String getName()
    {
        return name;
    }

    public int getWeight()
    {
        return weight;
    }

    public OceanBlock getOceanBlock()
    {
        return oceanBlock;
    }

    public String getType()
    {
        return type;
    }

    public static class OceanBlock
    {
        private String name;
        private int meta;

        public String getName()
        {
            return name;
        }

        public int getMeta()
        {
            return meta;
        }
    }
}
