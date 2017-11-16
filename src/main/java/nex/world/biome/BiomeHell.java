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

import net.minecraft.entity.monster.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import nex.entity.monster.EntityEmber;

@SuppressWarnings("ConstantConditions")
public class BiomeHell extends BiomeNetherEx
{
    public BiomeHell()
    {
        super(new BiomeProperties("Hell").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "hell");

        topBlock = Blocks.NETHERRACK.getDefaultState();
        fillerBlock = Blocks.NETHERRACK.getDefaultState();
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGhast.class, 50, 1, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 100, 1, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 2, 1, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 1, 1, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityBlaze.class, 3, 1, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEmber.class, 25, 1, 4));
    }
}
