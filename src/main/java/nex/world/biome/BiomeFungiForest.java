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

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import nex.block.BlockNetherrack;
import nex.handler.ConfigHandler;
import nex.init.NetherExBlocks;
import nex.util.RandomUtil;
import nex.world.gen.feature.WorldGenElderMushroom;
import nex.world.gen.feature.WorldGenEnokiMushroom;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BiomeFungiForest extends BiomeNetherEx
{
    private final WorldGenerator elderMushroom = new WorldGenElderMushroom(WorldGenElderMushroom.allVariants, true);
    private final WorldGenerator enokiMushroom = new WorldGenEnokiMushroom();

    public BiomeFungiForest()
    {
        super(new BiomeProperties("Fungi Forest").setTemperature(1.1F).setRainfall(0.0F).setRainDisabled(), "fungi_forest");

        topBlock = NetherExBlocks.BLOCK_HYPHAE.getDefaultState();
        fillerBlock = NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.LIVELY);
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.biome.fungiForest.generateElderMushrooms)
        {
            for(int i = 0; i < ConfigHandler.biome.fungiForest.elderMushroomRarity * 16; i++)
            {
                elderMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, RandomUtil.getNumberInRange(32, 112, rand), rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.fungiForest.generateEnokiMushrooms)
        {
            for(int i = 0; i < ConfigHandler.biome.fungiForest.enokiMushroomRarity * 16; i++)
            {
                enokiMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, RandomUtil.getNumberInRange(32, 128, rand), rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
