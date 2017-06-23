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
    private BiomeBlock topBlock;
    private BiomeBlock fillerBlock;
    private BiomeBlock oceanBlock;
    private List<BiomeEntity> entitySpawnList;
    private List<BiomeFeature> features;

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

    public BiomeBlock getTopBlock()
    {
        return topBlock;
    }

    public BiomeBlock getFillerBlock()
    {
        return fillerBlock;
    }

    public BiomeBlock getOceanBlock()
    {
        return oceanBlock;
    }

    public List<BiomeEntity> getEntitySpawnList()
    {
        return entitySpawnList;
    }

    public List<BiomeFeature> getFeatures()
    {
        return features;
    }

    public static class BiomeList
    {
        private String name;
        private List<Mod> mods;

        public String getName()
        {
            return name;
        }

        public List<Mod> getMods()
        {
            return mods;
        }
    }

    public static class Mod
    {
        private String modId;
        private List<NetherBiome> biomes;

        public String getId()
        {
            return modId;
        }

        public List<NetherBiome> getBiomes()
        {
            return biomes;
        }
    }

    public static class BiomeBlock
    {
        private String blockId;
        private int meta;

        public BiomeBlock()
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

    public static class BiomeEntity
    {
        private String entityId;
        private String creatureType;
        private int weight;
        private int minGroupCount;
        private int maxGroupCount;

        public String getId()
        {
            return entityId;
        }

        public String getCreatureType()
        {
            return creatureType;
        }

        public int getWeight()
        {
            return weight;
        }

        public int getMinGroupCount()
        {
            return minGroupCount;
        }

        public int getMaxGroupCount()
        {
            return maxGroupCount;
        }
    }

    public static class BiomeFeature
    {
        private String featureType;
        private BiomeBlock blockToSpawn;
        private BiomeBlock targetBlock;
        private int minHeight;
        private int maxHeight;
        private int size;
        private int rarity;

        public String getFeatureType()
        {
            return featureType;
        }

        public BiomeBlock getBlockToSpawn()
        {
            return blockToSpawn;
        }

        public BiomeBlock getTargetBlock()
        {
            return targetBlock;
        }

        public int getMinHeight()
        {
            return minHeight;
        }

        public int getMaxHeight()
        {
            return maxHeight;
        }

        public int getSize()
        {
            return size;
        }

        public int getRarity()
        {
            return rarity;
        }
    }
}
