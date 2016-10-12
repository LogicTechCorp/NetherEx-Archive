/*
 * Copyright (C) 2016.  LogicTechCorp
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

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.NetherEx;
import nex.Settings;
import nex.init.ModBlocks;
import nex.world.gen.feature.WorldGenBigMushroom;
import nex.world.gen.feature.WorldGenEnoki;
import nex.world.gen.feature.WorldGenFungalRoots;

import java.util.Random;

public class BiomeMushroomGrove extends BiomeNetherEx
{
    private WorldGenerator fungalRoots = new WorldGenFungalRoots();
    private WorldGenerator enoki = new WorldGenEnoki();
    private WorldGenerator bigBrownMushroom = new WorldGenBigMushroom(Blocks.BROWN_MUSHROOM_BLOCK, ModBlocks.MYCELIUM.getDefaultState());
    private WorldGenerator bigRedMushroom = new WorldGenBigMushroom(Blocks.RED_MUSHROOM_BLOCK, ModBlocks.MYCELIUM.getDefaultState());

    public BiomeMushroomGrove()
    {
        super(new BiomeProperties("Mushroom Grove"));

        topBlock = ModBlocks.MYCELIUM.getDefaultState();
        fillerBlock = ModBlocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 1, 4, 4));

        quartzOreBlock = ModBlocks.QUARTZ_ORE.getDefaultState();

        settingCategory = Settings.CATEGORY_BIOME_MUSHROOM_GROVE;

        setRegistryName(NetherEx.MOD_ID + ":mushroom_grove");
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        super.decorate(world, rand, pos);

        if(Settings.generateFungalRoots)
        {
            for(int i = 0; i < Settings.fungalRootRarity; i++)
            {
                fungalRoots.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(108) + 10, rand.nextInt(16) + 8));
            }
        }

        if(Settings.generateEnoki)
        {
            for(int i = 0; i < Settings.enokiRarity; i++)
            {
                enoki.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(108) + 10, rand.nextInt(16) + 8));
            }
        }

        if(Settings.generateBigMushrooms)
        {
            for(int i = 0; i < Settings.bigMushroomRarity; i++)
            {
                bigBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
            }

            for(int i = 0; i < Settings.bigMushroomRarity; i++)
            {
                bigRedMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
            }
        }
    }
}
