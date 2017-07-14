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
import net.minecraft.block.state.IBlockState;
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

        JsonObject topBlockJson = JsonUtils.getJsonObject(config, "topBlock");
        JsonObject fillerBlockJson = JsonUtils.getJsonObject(config, "fillerBlock");
        JsonObject oceanBlockJson = JsonUtils.getJsonObject(config, "oceanBlock");

        IBlockState topBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(topBlockJson, "block"))).getDefaultState();
        IBlockState fillerBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(fillerBlockJson, "block"))).getDefaultState();
        IBlockState oceanBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(oceanBlockJson, "block"))).getDefaultState();

        topBlock = BlockUtil.getBlockWithProperties(topBlock, JsonUtils.getJsonObject(topBlockJson, "properties"));
        fillerBlock = BlockUtil.getBlockWithProperties(fillerBlock, JsonUtils.getJsonObject(fillerBlockJson, "properties"));
        oceanBlock = BlockUtil.getBlockWithProperties(oceanBlock, JsonUtils.getJsonObject(oceanBlockJson, "properties"));

        return new NetherBiome(biome, weight, biomeClimate, topBlock, fillerBlock, oceanBlock);
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
