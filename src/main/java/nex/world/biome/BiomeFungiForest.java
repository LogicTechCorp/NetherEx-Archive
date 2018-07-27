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

package nex.world.biome;

import lex.world.biome.BiomeLibEx;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.block.BlockNetherrack;
import nex.entity.monster.EntitySpore;
import nex.entity.monster.EntitySporeCreeper;
import nex.entity.neutral.EntityMogus;
import nex.init.NetherExBlocks;

@SuppressWarnings("ConstantConditions")
public class BiomeFungiForest extends BiomeNetherEx
{
    public BiomeFungiForest()
    {
        super(NetherEx.instance, new BiomeProperties("Fungi Forest").setTemperature(1.1F).setRainfall(0.0F).setRainDisabled(), "fungi_forest");
        topBlock = NetherExBlocks.HYPHAE.getDefaultState();
        fillerBlock = NetherExBlocks.NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.LIVELY);
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMogus.class, 100, 4, 6));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpore.class, 25, 1, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySporeCreeper.class, 50, 1, 4));
    }
}
