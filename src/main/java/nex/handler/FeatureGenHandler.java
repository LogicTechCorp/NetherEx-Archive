/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

package nex.handler;

import lex.util.RandomHelper;
import lex.world.biome.BiomeConfigurations;
import lex.world.gen.GenerationStage;
import lex.world.gen.feature.Feature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;
import nex.world.biome.NetherExBiomeManager;

import java.util.Random;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class FeatureGenHandler
{
    @SubscribeEvent
    public static void onPrePopulateChunk(PopulateChunkEvent.Pre event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateFeature(event.getWorld(), event.getChunkX(), event.getChunkZ(), event.getRand(), GenerationStage.PRE_POPULATE);
        }
    }

    @SubscribeEvent
    public static void onPopulateChunk(PopulateChunkEvent.Populate event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId() && event.getType() == PopulateChunkEvent.Populate.EventType.CUSTOM)
        {
            generateFeature(event.getWorld(), event.getChunkX(), event.getChunkZ(), event.getRand(), GenerationStage.POPULATE);
        }
    }

    @SubscribeEvent
    public static void onPostPopulateChunk(PopulateChunkEvent.Post event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateFeature(event.getWorld(), event.getChunkX(), event.getChunkZ(), event.getRand(), GenerationStage.POST_POPULATE);
        }
    }

    @SubscribeEvent
    public static void onPreBiomeDecorate(DecorateBiomeEvent.Pre event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.PRE_DECORATE);
        }
    }

    @SubscribeEvent
    public static void onBiomeDecorate(DecorateBiomeEvent.Decorate event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId() && event.getType() == DecorateBiomeEvent.Decorate.EventType.CUSTOM)
        {
            generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.DECORATE);
        }
    }

    @SubscribeEvent
    public static void onPostBiomeDecorate(DecorateBiomeEvent.Post event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.POST_DECORATE);
        }
    }

    @SubscribeEvent
    public static void onPreGenerateOres(OreGenEvent.Pre event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.PRE_ORE);
        }
    }

    @SubscribeEvent
    public static void onGenerateOres(OreGenEvent.GenerateMinable event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId() && event.getType() == OreGenEvent.GenerateMinable.EventType.CUSTOM)
        {
            generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.ORE);
        }
    }

    @SubscribeEvent
    public static void onPostGenerateOres(OreGenEvent.Post event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.POST_ORE);
        }
    }

    private static void generateFeature(World world, int chunkX, int chunkZ, Random rand, GenerationStage generationStage)
    {
        BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
        generateFeature(world, pos, rand, generationStage);
    }

    private static void generateFeature(World world, BlockPos pos, Random rand, GenerationStage generationStage)
    {
        BiomeConfigurations configurations = NetherExBiomeManager.getBiomeConfigurations(world.getBiome(pos.add(16, 0, 16)));

        if(configurations != null)
        {
            for(Feature feature : configurations.getFeatures(generationStage))
            {
                for(int generationAttempts = 0; generationAttempts < feature.getGenAttempts(rand); generationAttempts++)
                {
                    feature.generate(world, rand, pos.add(rand.nextInt(16) + 8, RandomHelper.getRandomNumberInRange(feature.getMinHeight(), feature.getMaxHeight(), rand), rand.nextInt(16) + 8));
                }
            }
        }
    }
}
