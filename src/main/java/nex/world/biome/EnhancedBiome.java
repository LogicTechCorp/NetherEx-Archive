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

public class EnhancedBiome
{
    private final Biome biome;
    private final int weight;
    private final IBlockState floorTopBlock;
    private final IBlockState floorFillerBlock;
    private final IBlockState wallBlock;
    private final IBlockState roofBottomBlock;
    private final IBlockState roofFillerBlock;
    private final IBlockState oceanBlock;

    private EnhancedBiome(Biome biomeIn, int weightIn, IBlockState floorTopBlockIn, IBlockState floorFillerBlockIn, IBlockState wallBlockIn, IBlockState roofBottomBlockIn, IBlockState roofFillerBlockIn, IBlockState oceanBlockIn)
    {
        biome = biomeIn;
        weight = weightIn;
        floorTopBlock = floorTopBlockIn;
        floorFillerBlock = floorFillerBlockIn;
        wallBlock = wallBlockIn;
        roofBottomBlock = roofBottomBlockIn;
        roofFillerBlock = roofFillerBlockIn;
        oceanBlock = oceanBlockIn;
    }

    public static EnhancedBiome deserialize(JsonObject config)
    {
        Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(JsonUtils.getString(config, "biome")));
        int weight = JsonUtils.getInt(config, "weight", 10);

        if(biome != null)
        {
            IBlockState floorTopBlock = null;
            IBlockState floorFillerBlock = null;
            IBlockState wallBlock = null;
            IBlockState roofBottomBlock = null;
            IBlockState roofFillerBlock = null;

            IBlockState oceanBlock = null;

            JsonObject floorTopBlockJson = JsonUtils.getJsonObject(config, "floorTopBlock", new JsonObject());
            JsonObject floorFillerBlockJson = JsonUtils.getJsonObject(config, "floorFillerBlock", new JsonObject());
            JsonObject wallBlockJson = JsonUtils.getJsonObject(config, "wallBlock", new JsonObject());
            JsonObject roofBottomBlockJson = JsonUtils.getJsonObject(config, "roofBottomBlock", new JsonObject());
            JsonObject roofFillerBlockJson = JsonUtils.getJsonObject(config, "roofFillerBlock", new JsonObject());

            JsonObject oceanBlockJson = JsonUtils.getJsonObject(config, "oceanBlock", new JsonObject());

            if(floorTopBlockJson.entrySet().size() > 0)
            {
                ResourceLocation block = new ResourceLocation(JsonUtils.getString(floorTopBlockJson, "block"));

                if(ForgeRegistries.BLOCKS.containsKey(block))
                {
                    floorTopBlock = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
                }
            }
            if(floorFillerBlockJson.entrySet().size() > 0)
            {
                ResourceLocation block = new ResourceLocation(JsonUtils.getString(floorFillerBlockJson, "block"));

                if(ForgeRegistries.BLOCKS.containsKey(block))
                {
                    floorFillerBlock = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
                }
            }
            if(wallBlockJson.entrySet().size() > 0)
            {
                ResourceLocation block = new ResourceLocation(JsonUtils.getString(wallBlockJson, "block"));

                if(ForgeRegistries.BLOCKS.containsKey(block))
                {
                    wallBlock = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
                }
            }
            if(roofBottomBlockJson.entrySet().size() > 0)
            {
                ResourceLocation block = new ResourceLocation(JsonUtils.getString(roofBottomBlockJson, "block"));

                if(ForgeRegistries.BLOCKS.containsKey(block))
                {
                    roofBottomBlock = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
                }
            }
            if(roofFillerBlockJson.entrySet().size() > 0)
            {
                ResourceLocation block = new ResourceLocation(JsonUtils.getString(roofFillerBlockJson, "block"));

                if(ForgeRegistries.BLOCKS.containsKey(block))
                {
                    roofFillerBlock = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
                }
            }
            if(oceanBlockJson.entrySet().size() > 0)
            {
                ResourceLocation block = new ResourceLocation(JsonUtils.getString(oceanBlockJson, "block"));

                if(ForgeRegistries.BLOCKS.containsKey(block))
                {
                    oceanBlock = ForgeRegistries.BLOCKS.getValue(block).getDefaultState();
                }
            }

            if(floorTopBlock == null)
            {
                floorTopBlock = biome.topBlock;
            }
            if(floorFillerBlock == null)
            {
                floorFillerBlock = biome.fillerBlock;
            }
            if(oceanBlock == null)
            {
                oceanBlock = Blocks.LAVA.getDefaultState();
            }

            JsonObject floorTopBlockProperties = JsonUtils.getJsonObject(floorTopBlockJson, "properties", new JsonObject());
            JsonObject floorFillerBlockProperties = JsonUtils.getJsonObject(floorFillerBlockJson, "properties", new JsonObject());
            JsonObject oceanBlockProperties = JsonUtils.getJsonObject(oceanBlockJson, "properties", new JsonObject());

            if(floorTopBlockProperties.entrySet().size() > 0)
            {
                floorTopBlock = BlockUtil.getBlockWithProperties(floorTopBlock, JsonUtils.getJsonObject(floorTopBlockJson, "properties"));
            }
            if(floorFillerBlockProperties.entrySet().size() > 0)
            {
                floorFillerBlock = BlockUtil.getBlockWithProperties(floorFillerBlock, JsonUtils.getJsonObject(floorFillerBlockJson, "properties"));
            }
            if(oceanBlockProperties.entrySet().size() > 0)
            {
                oceanBlock = BlockUtil.getBlockWithProperties(oceanBlock, JsonUtils.getJsonObject(oceanBlockJson, "properties"));
            }

            if(wallBlock == null)
            {
                wallBlock = floorFillerBlock;
            }
            if(roofBottomBlock == null)
            {
                roofBottomBlock = floorFillerBlock;
            }
            if(roofFillerBlock == null)
            {
                roofFillerBlock = floorFillerBlock;
            }

            JsonObject wallBlockProperties = JsonUtils.getJsonObject(wallBlockJson, "properties", new JsonObject());
            JsonObject roofBottomBlockProperties = JsonUtils.getJsonObject(roofBottomBlockJson, "properties", new JsonObject());
            JsonObject roofFillerBlockProperties = JsonUtils.getJsonObject(roofFillerBlockJson, "properties", new JsonObject());

            if(wallBlockProperties.entrySet().size() > 0)
            {
                wallBlock = BlockUtil.getBlockWithProperties(wallBlock, JsonUtils.getJsonObject(wallBlockJson, "properties"));
            }
            if(roofBottomBlockProperties.entrySet().size() > 0)
            {
                roofBottomBlock = BlockUtil.getBlockWithProperties(floorFillerBlock, JsonUtils.getJsonObject(roofBottomBlockJson, "properties"));
            }
            if(roofFillerBlockProperties.entrySet().size() > 0)
            {
                roofFillerBlock = BlockUtil.getBlockWithProperties(floorFillerBlock, JsonUtils.getJsonObject(roofFillerBlockJson, "properties"));
            }

            return new EnhancedBiome(biome, weight, floorTopBlock, floorFillerBlock, wallBlock, roofBottomBlock, roofFillerBlock, oceanBlock);
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

    public IBlockState getFloorTopBlock()
    {
        return floorTopBlock;
    }

    public IBlockState getFloorFillerBlock()
    {
        return floorFillerBlock;
    }

    public IBlockState getWallBlock()
    {
        return wallBlock;
    }

    public IBlockState getRoofBottomBlock()
    {
        return roofBottomBlock;
    }

    public IBlockState getRoofFillerBlock()
    {
        return roofFillerBlock;
    }

    public IBlockState getOceanBlock()
    {
        return oceanBlock;
    }
}
