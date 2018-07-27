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
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.block.BlockNetherrack;
import nex.entity.monster.EntityBoneSpider;
import nex.entity.monster.EntityBrute;
import nex.entity.monster.EntityWight;
import nex.init.NetherExBlocks;

@SuppressWarnings("ConstantConditions")
public class BiomeArcticAbyss extends BiomeNetherEx
{
    public BiomeArcticAbyss()
    {
        super(NetherEx.instance, new BiomeProperties("Arctic Abyss").setTemperature(0.0F).setRainfall(0.0F).setRainDisabled(), "arctic_abyss");
        topBlock = NetherExBlocks.FROSTBURN_ICE.getDefaultState();
        fillerBlock = NetherExBlocks.NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY);
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGhast.class, 50, 1, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 25, 1, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityBoneSpider.class, 35, 1, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityWight.class, 100, 1, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityBrute.class, 15, 1, 1));
    }
}
