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

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import nex.block.BlockNetherrack;
import nex.handler.ConfigHandler;
import nex.init.NetherExBlocks;
import nex.util.RandomUtil;
import nex.world.gen.feature.WorldGenThornstalk;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BiomeRuthlessSands extends BiomeNetherEx
{
    private final WorldGenerator thornstalk = new WorldGenThornstalk();

    public BiomeRuthlessSands()
    {
        super(new BiomeProperties("Ruthless Sands").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "ruthless_sands");

        topBlock = Blocks.SOUL_SAND.getDefaultState();
        fillerBlock = NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.GLOOMY);
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.biome.ruthlessSands.generateThornstalk)
        {
            for(int i = 0; i < ConfigHandler.biome.ruthlessSands.thornstalkRarity; i++)
            {
                thornstalk.generate(world, rand, pos.add(rand.nextInt(16) + 8, RandomUtil.getNumberInRange(32, 112, rand), rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
