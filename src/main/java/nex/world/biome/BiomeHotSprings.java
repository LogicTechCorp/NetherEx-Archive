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

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.NetherEx;
import nex.Settings;
import nex.init.ModBlocks;
import nex.world.gen.feature.WorldGenPool;

import java.util.Random;

public class BiomeHotSprings extends BiomeNetherEx
{
    private WorldGenerator lavaPools = new WorldGenPool(Blocks.LAVA, ModBlocks.NETHERRACK.getStateFromMeta(2));

    public BiomeHotSprings()
    {
        super(new BiomeProperties("Hot Springs"));

        topBlock = ModBlocks.NETHERRACK.getStateFromMeta(2);
        fillerBlock = ModBlocks.NETHERRACK.getStateFromMeta(2);

        spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 50, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityBlaze.class, 100, 4, 4));

        quartzOreBlock = ModBlocks.QUARTZ_ORE.getStateFromMeta(2);

        settingCategory = Settings.CATEGORY_BIOME_HELL;

        setRegistryName(NetherEx.MOD_ID + ":hot_springs");
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        super.decorate(world, rand, pos);

        if(Settings.generateLavaPools)
        {
            for(int i = 0; i < Settings.lavaPoolRarity; i++)
            {
                lavaPools.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(108) + 10, rand.nextInt(16) + 8));
            }
        }
    }
}
