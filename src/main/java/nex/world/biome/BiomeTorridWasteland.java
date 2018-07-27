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
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.block.BlockNetherrack;
import nex.entity.monster.EntityEmber;
import nex.entity.neutral.EntitySalamander;
import nex.init.NetherExBlocks;

@SuppressWarnings("ConstantConditions")
public class BiomeTorridWasteland extends BiomeLibEx
{
    public BiomeTorridWasteland()
    {
        super(NetherEx.instance, new BiomeProperties("Torrid Wasteland").setTemperature(4.0F).setRainfall(0.0F).setRainDisabled(), "torrid_wasteland");

        topBlock = NetherExBlocks.NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.FIERY);
        fillerBlock = NetherExBlocks.NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.FIERY);
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 25, 1, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEmber.class, 50, 4, 6));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySalamander.class, 100, 1, 4));
    }
}
