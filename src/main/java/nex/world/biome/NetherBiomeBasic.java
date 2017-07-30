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
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.util.BlockUtil;

public class NetherBiomeBasic extends NetherBiomeNetherEx
{
    public NetherBiomeBasic()
    {
        this(Biomes.HELL, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState());
    }

    private NetherBiomeBasic(Biome biomeIn, int weightIn, IBlockState floorTopBlockIn, IBlockState floorFillerBlockIn, IBlockState wallBlockIn, IBlockState roofBottomBlockIn, IBlockState roofFillerBlockIn, IBlockState oceanBlockIn)
    {
        super("basic", biomeIn, weightIn, floorTopBlockIn, floorFillerBlockIn, wallBlockIn, roofBottomBlockIn, roofFillerBlockIn, oceanBlockIn);
    }

    @Override
    public NetherBiome deserialize(JsonObject config)
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
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(floorTopBlockJson, "block")));

                if(block != null)
                {
                    floorTopBlock = block.getDefaultState();
                }
            }
            if(floorFillerBlockJson.entrySet().size() > 0)
            {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(floorFillerBlockJson, "block")));

                if(block != null)
                {
                    floorFillerBlock = block.getDefaultState();
                }
            }
            if(wallBlockJson.entrySet().size() > 0)
            {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(wallBlockJson, "block")));

                if(block != null)
                {
                    wallBlock = block.getDefaultState();
                }
            }
            if(roofBottomBlockJson.entrySet().size() > 0)
            {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(roofBottomBlockJson, "block")));

                if(block != null)
                {
                    roofBottomBlock = block.getDefaultState();
                }
            }
            if(roofFillerBlockJson.entrySet().size() > 0)
            {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(roofFillerBlockJson, "block")));

                if(block != null)
                {
                    roofFillerBlock = block.getDefaultState();
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

            return new NetherBiomeBasic(biome, weight, floorTopBlock, floorFillerBlock, wallBlock, roofBottomBlock, roofFillerBlock, oceanBlock);
        }

        return null;
    }
}
