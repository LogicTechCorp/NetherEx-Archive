/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.world.biome;

import logictechcorp.libraryex.IModData;
import logictechcorp.libraryex.world.biome.BiomeInfo;
import logictechcorp.libraryex.world.biome.BiomeMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;

public abstract class BiomeNetherEx extends BiomeMod<BiomeInfo>
{
    protected static final IBlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
    protected static final IBlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
    protected static final IBlockState GLOWSTONE = Blocks.GLOWSTONE.getDefaultState();
    protected static final IBlockState FIRE = Blocks.FIRE.getDefaultState();
    protected static final IBlockState LAVA = Blocks.LAVA.getDefaultState();
    protected static final IBlockState FLOWING_LAVA = Blocks.FLOWING_LAVA.getDefaultState();
    protected static final IBlockState MAGMA = Blocks.MAGMA.getDefaultState();

    public BiomeNetherEx(IModData data, BiomeProperties properties, String name)
    {
        super(data, properties, name);
    }

    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return this.getModdedBiomeDecorator(new BiomeDecoratorNetherEx());
    }

    @Override
    public BiomeInfo getInfo()
    {
        return null;
    }
}
