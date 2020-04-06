/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

import logictechcorp.libraryex.utility.RandomHelper;
import logictechcorp.libraryex.world.biome.data.BiomeData;
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.BiomeTrait;
import logictechcorp.netherex.NetherEx;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class BiomeTraitGenerationHandler
{
    @SubscribeEvent
    public static void onPreBiomeDecorate(DecorateBiomeEvent.Pre event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateBiomeTraits(event.getWorld(), event.getChunkPos().getBlock(0, 0, 0), event.getRand(), GenerationStage.TERRAIN_ALTERATION);
        }
    }

    @SubscribeEvent
    public static void onBiomeDecorate(DecorateBiomeEvent.Decorate event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.NETHER.getId() && event.getType() == DecorateBiomeEvent.Decorate.EventType.CUSTOM)
        {
            generateBiomeTraits(event.getWorld(), event.getChunkPos().getBlock(0, 0, 0), event.getRand(), GenerationStage.DECORATION);
            generateBiomeTraits(event.getWorld(), event.getChunkPos().getBlock(0, 0, 0), event.getRand(), GenerationStage.PLANT_DECORATION);
        }
    }

    @SubscribeEvent
    public static void onPostBiomeDecorate(DecorateBiomeEvent.Post event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateBiomeTraits(event.getWorld(), event.getChunkPos().getBlock(0, 0, 0), event.getRand(), GenerationStage.STRUCTURE);
        }
    }

    @SubscribeEvent
    public static void onGenerateOres(OreGenEvent.GenerateMinable event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.NETHER.getId() && event.getType() == OreGenEvent.GenerateMinable.EventType.CUSTOM)
        {
            generateBiomeTraits(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.ORE);
        }
    }

    private static void generateBiomeTraits(World world, BlockPos pos, Random random, GenerationStage generationStage)
    {
        BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData(world.getBiome(pos.add(16, 0, 16)));

        if(ConfigHandler.dimensionConfig.nether.overrideNether && biomeData != BiomeData.EMPTY)
        {
            for(BiomeTrait trait : biomeData.getBiomeTraits(generationStage))
            {
                for(int generationAttempts = 0; generationAttempts < trait.getGenerationAttempts(world, pos, random); generationAttempts++)
                {
                    trait.generate(world, pos.add(random.nextInt(16) + 8, RandomHelper.getNumberInRange(trait.getMinimumGenerationHeight(world, pos, random), trait.getMaximumGenerationHeight(world, pos, random), random), random.nextInt(16) + 8), random);
                }
            }
        }
    }
}
