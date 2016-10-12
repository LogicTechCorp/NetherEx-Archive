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

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import nex.NetherEx;
import nex.Settings;
import nex.world.gen.feature.WorldGenThornBush;

import java.util.Random;

public class BiomeRuthlessSands extends BiomeNetherEx
{
    private final WorldGenerator thornBush = new WorldGenThornBush();

    public BiomeRuthlessSands()
    {
        super(new BiomeProperties("Ruthless Sands"));

        topBlock = Blocks.SOUL_SAND.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 10, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));

        settingCategory = Settings.CATEGORY_BIOME_RUTHLESS_SANDS;

        setRegistryName(NetherEx.MOD_ID + ":ruthless_sands");
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        super.decorate(world, rand, pos);

        if(Settings.generateThornBushes)
        {
            for(int i = 0; i < rand.nextInt(Settings.thornBushRarity); i++)
            {
                thornBush.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }
    }
}
