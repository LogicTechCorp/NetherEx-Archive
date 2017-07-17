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

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.util.BlockUtil;

public class NetherBiome
{
    private Biome biome;
    private int weight;
    private NetherBiomeClimate biomeClimate;
    private IBlockState topBlock;
    private IBlockState fillerBlock;
    private IBlockState oceanBlock;

    private NetherBiome(Biome biomeIn, int weightIn, NetherBiomeClimate biomeClimateIn, IBlockState topBlockIn, IBlockState fillerBlockIn, IBlockState oceanBlockIn)
    {
        biome = biomeIn;
        weight = weightIn;
        biomeClimate = biomeClimateIn;
        topBlock = topBlockIn;
        fillerBlock = fillerBlockIn;
        oceanBlock = oceanBlockIn;
    }

    public static NetherBiome deserialize(JsonObject config)
    {
        Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(JsonUtils.getString(config, "biome")));
        int weight = JsonUtils.getInt(config, "weight", 10);
        NetherBiomeClimate biomeClimate = NetherBiomeClimate.getFromString(JsonUtils.getString(config, "climate"));

        if(biome != null)
        {
            IBlockState topBlock = null;
            IBlockState fillerBlock = null;
            IBlockState oceanBlock = null;

            JsonObject topBlockJson = JsonUtils.getJsonObject(config, "topBlock", new JsonObject());
            JsonObject fillerBlockJson = JsonUtils.getJsonObject(config, "fillerBlock", new JsonObject());
            JsonObject oceanBlockJson = JsonUtils.getJsonObject(config, "oceanBlock", new JsonObject());

            if(topBlockJson.entrySet().size() > 0)
            {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(topBlockJson, "block")));

                if(block != null)
                {
                    topBlock = block.getDefaultState();
                }
            }
            if(fillerBlockJson.entrySet().size() > 0)
            {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(fillerBlockJson, "block")));

                if(block != null)
                {
                    fillerBlock = block.getDefaultState();
                }
            }
            if(oceanBlockJson.entrySet().size() > 0)
            {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(oceanBlockJson, "block")));

                if(block != null)
                {
                    oceanBlock = block.getDefaultState();
                }
            }

            if(topBlock == null)
            {
                topBlock = biome.topBlock;
            }
            if(fillerBlock == null)
            {
                fillerBlock = biome.fillerBlock;
            }
            if(oceanBlock == null)
            {
                oceanBlock = Blocks.LAVA.getDefaultState();
            }

            JsonObject topBlockProperties = JsonUtils.getJsonObject(topBlockJson, "properties", new JsonObject());
            JsonObject fillerBlockProperties = JsonUtils.getJsonObject(fillerBlockJson, "properties", new JsonObject());
            JsonObject oceanBlockProperties = JsonUtils.getJsonObject(oceanBlockJson, "properties", new JsonObject());

            if(topBlockProperties.entrySet().size() > 0)
            {
                topBlock = BlockUtil.getBlockWithProperties(topBlock, JsonUtils.getJsonObject(topBlockJson, "properties"));
            }
            if(fillerBlockProperties.entrySet().size() > 0)
            {
                fillerBlock = BlockUtil.getBlockWithProperties(fillerBlock, JsonUtils.getJsonObject(fillerBlockJson, "properties"));
            }
            if(oceanBlockProperties.entrySet().size() > 0)
            {
                oceanBlock = BlockUtil.getBlockWithProperties(oceanBlock, JsonUtils.getJsonObject(oceanBlockJson, "properties"));
            }

            return new NetherBiome(biome, weight, biomeClimate, topBlock, fillerBlock, oceanBlock);
        }

        return null;
    }

    public Biome getBiome()
    {
        return biome;
    }

    public int getWeight()
    {
        return weight;
    }

    public NetherBiomeClimate getBiomeClimate()
    {
        return biomeClimate;
    }

    public IBlockState getTopBlock()
    {
        return topBlock;
    }

    public IBlockState getFillerBlock()
    {
        return fillerBlock;
    }

    public IBlockState getOceanBlock()
    {
        return oceanBlock;
    }
}
