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

package nex.world.gen.feature;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.util.BlockUtil;
import nex.world.gen.GenerationStage;

import java.util.Random;

public class BiomeFeatureScattered extends BiomeFeature
{
    private final IBlockState blockToSpawn;
    private final IBlockState blockToSpawnOn;

    public BiomeFeatureScattered(Biome biome, GenerationStage generationStage, int generationAttempts, int minHeight, int maxHeight, IBlockState blockToSpawnIn, IBlockState blockToSpawnOnIn)
    {
        super(biome, generationStage, generationAttempts, minHeight, maxHeight);

        blockToSpawn = blockToSpawnIn;
        blockToSpawnOn = blockToSpawnOnIn;
    }

    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < 64; ++i)
        {
            BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if(world.isAirBlock(newPos) && world.getBlockState(newPos.down()) == blockToSpawnOn)
            {
                if(blockToSpawn instanceof BlockBush)
                {
                    if(((BlockBush) blockToSpawn).canBlockStay(world, newPos,  blockToSpawn))
                    {
                        world.setBlockState(newPos, blockToSpawn, 2);
                    }
                }
                else
                {
                    world.setBlockState(newPos, blockToSpawn, 2);
                }
            }
        }

        return true;
    }

    @Override
    public BiomeFeature deserializeFeature(JsonObject config)
    {
        Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(JsonUtils.getString(config, "biome")));
        GenerationStage generationStage = GenerationStage.getFromString(JsonUtils.getString(config, "stage"));
        int generationAttempts = JsonUtils.getInt(config, "generationAttempts", 10);
        int minHeight = JsonUtils.getInt(config, "minHeight", 32);
        int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

        if(biome != null)
        {
            IBlockState blockToSpawn = null;
            IBlockState blockToSpawnOn = null;

            JsonObject blockToSpawnJson = JsonUtils.getJsonObject(config, "blockToSpawn", new JsonObject());
            JsonObject blockToSpawnOnJson = JsonUtils.getJsonObject(config, "blockToSpawnOn", new JsonObject());

            if(blockToSpawnJson.entrySet().size() > 0)
            {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(blockToSpawnJson, "block")));

                if(block != null)
                {
                    blockToSpawn = block.getDefaultState();
                }
            }
            if(blockToSpawnOnJson.entrySet().size() > 0)
            {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(blockToSpawnOnJson, "block")));

                if(block != null)
                {
                    blockToSpawnOn = block.getDefaultState();
                }
            }

            JsonObject blockToSpawnProperties = JsonUtils.getJsonObject(blockToSpawnJson, "properties", new JsonObject());
            JsonObject blockToSpawnOnProperties = JsonUtils.getJsonObject(blockToSpawnOnJson, "properties", new JsonObject());

            if(blockToSpawnProperties.entrySet().size() > 0)
            {
                blockToSpawn = BlockUtil.getBlockWithProperties(blockToSpawn, JsonUtils.getJsonObject(blockToSpawnJson, "properties"));
            }
            if(blockToSpawnOnProperties.entrySet().size() > 0)
            {
                blockToSpawnOn = BlockUtil.getBlockWithProperties(blockToSpawnOn, JsonUtils.getJsonObject(blockToSpawnOnJson, "properties"));
            }

            if(blockToSpawn != null && blockToSpawnOn != null)
            {
                return new BiomeFeatureScattered(biome, generationStage, generationAttempts, minHeight, maxHeight, blockToSpawn, blockToSpawnOn);
            }
        }
        
        return null;
    }
}
