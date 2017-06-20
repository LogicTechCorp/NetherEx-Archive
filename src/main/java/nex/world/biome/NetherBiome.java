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

package nex.world.biome;

import java.util.List;

public class NetherBiome
{
    private String biomeId;
    private int weight;
    private String climateType;
    private Block topBlock;
    private Block fillerBlock;
    private Block oceanBlock;
    private List<NetherBiomeEntity> entitySpawnList;

    public String getId()
    {
        return biomeId;
    }

    public int getWeight()
    {
        return weight;
    }

    public String getClimateType()
    {
        return climateType;
    }

    public Block getTopBlock()
    {
        return topBlock;
    }

    public Block getFillerBlock()
    {
        return fillerBlock;
    }

    public Block getOceanBlock()
    {
        return oceanBlock;
    }

    public List<NetherBiomeEntity> getEntitySpawnList()
    {
        return entitySpawnList;
    }

    public static class Block
    {
        private String blockId;
        private int meta;

        public Block()
        {
            blockId = "";
            meta = 0;
        }

        public String getId()
        {
            return blockId;
        }

        public int getMeta()
        {
            return meta;
        }
    }
}
