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

package logictechcorp.netherex.handler;

import logictechcorp.libraryex.util.RandomHelper;
import logictechcorp.libraryex.world.biome.BiomeInfo;
import logictechcorp.libraryex.world.gen.GenerationStage;
import logictechcorp.libraryex.world.gen.feature.Feature;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.world.biome.NetherBiomeManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class FeatureGenHandler
{
    @SubscribeEvent
    public static void onPreBiomeDecorate(DecorateBiomeEvent.Pre event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateFeature(event.getWorld(), event.getChunkPos().getBlock(0, 0, 0), event.getRand(), GenerationStage.PRE_DECORATE);
        }
    }

    @SubscribeEvent
    public static void onBiomeDecorate(DecorateBiomeEvent.Decorate event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId() && event.getType() == DecorateBiomeEvent.Decorate.EventType.CUSTOM)
        {
            generateFeature(event.getWorld(), event.getChunkPos().getBlock(0, 0, 0), event.getRand(), GenerationStage.DECORATE);
        }
    }

    @SubscribeEvent
    public static void onPostBiomeDecorate(DecorateBiomeEvent.Post event)
    {
        if(event.getWorld().provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateFeature(event.getWorld(), event.getChunkPos().getBlock(0, 0, 0), event.getRand(), GenerationStage.POST_DECORATE);
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

    private static void generateFeature(World world, BlockPos pos, Random rand, GenerationStage generationStage)
    {
        BiomeInfo info = NetherBiomeManager.INSTANCE.getAllBiomeInfo(world.getBiome(pos.add(16, 0, 16)));

        if(info != null)
        {
            for(Feature feature : info.getFeatures(generationStage))
            {
                for(int generationAttempts = 0; generationAttempts < feature.getGenAttempts(rand); generationAttempts++)
                {
                    feature.generate(world, rand, pos.add(rand.nextInt(16) + 8, RandomHelper.getNumberInRange(feature.getMinHeight(), feature.getMaxHeight(), rand), rand.nextInt(16) + 8));
                }
            }
        }
    }
}
