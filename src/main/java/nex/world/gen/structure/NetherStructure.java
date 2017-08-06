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

package nex.world.gen.structure;

import net.minecraft.block.Block;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import nex.util.WeightedUtil;

import java.util.List;

public class NetherStructure extends WeightedUtil.NamedItem
{
    private final int weight;
    private final Rotation rotation;
    private final Mirror mirror;
    private final Block replacedBlock;
    private final List<ResourceLocation> lootTables;
    private final List<ResourceLocation> spawnerMobs;

    public NetherStructure(String identifierIn, int weightIn, Rotation rotationIn, Mirror mirrorIn, Block replacedBlockIn, List<ResourceLocation> lootTablesIn, List<ResourceLocation> spawnerMobsIn)
    {
        super(identifierIn, weightIn);

        weight = weightIn;
        rotation = rotationIn;
        mirror = mirrorIn;
        replacedBlock = replacedBlockIn;
        lootTables = lootTablesIn;
        spawnerMobs = spawnerMobsIn;
    }

    public int getWeight()
    {
        return weight;
    }

    public Rotation getRotation()
    {
        return rotation;
    }

    public Mirror getMirror()
    {
        return mirror;
    }

    public Block getReplacedBlock()
    {
        return replacedBlock;
    }

    public List<ResourceLocation> getLootTables()
    {
        return lootTables;
    }

    public List<ResourceLocation> getSpawnerMobs()
    {
        return spawnerMobs;
    }
}
