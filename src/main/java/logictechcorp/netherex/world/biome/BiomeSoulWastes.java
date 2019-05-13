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

import logictechcorp.libraryex.world.biome.data.impl.BiomeBlock;
import logictechcorp.libraryex.world.biome.data.impl.BiomeDataConfigurable;
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitBoulder;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitPatch;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitScatter;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitStructure;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.state.IBlockState;

import java.util.Arrays;

public class BiomeSoulWastes extends BiomeNetherEx
{
    private static final IBlockState SOUL_SANDSTONE = NetherExBlocks.SOUL_SANDSTONE.getDefaultState();
    private static final IBlockState CUT_SOUL_SANDSTONE = NetherExBlocks.CUT_SOUL_SANDSTONE.getDefaultState();
    private static final IBlockState POSSESSED_SOUL_SAND = NetherExBlocks.POSSESSED_SOUL_SAND.getDefaultState();

    public BiomeSoulWastes()
    {
        super(NetherEx.instance, new BiomeProperties("Soul Wastes").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "soul_wastes");
        this.topBlock = SOUL_SAND;
        this.fillerBlock = SOUL_SAND;
    }

    @Override
    public BiomeData getBiomeData()
    {
        return new BiomeData();
    }

    private class BiomeData extends BiomeDataConfigurable
    {
        private BiomeData()
        {
            super(NetherExBiomes.SOUL_WASTES, 8, false, true, true);
            this.getBiomeBlock(BiomeBlock.FLOOR_TOP_BLOCK, SOUL_SAND);
            this.getBiomeBlock(BiomeBlock.FLOOR_FILLER_BLOCK, SOUL_SAND);
            this.getBiomeBlock(BiomeBlock.WALL_BLOCK, SOUL_SANDSTONE);
            this.getBiomeBlock(BiomeBlock.CEILING_FILLER_BLOCK, SOUL_SANDSTONE);
            this.getBiomeBlock(BiomeBlock.CEILING_BOTTOM_BLOCK, CUT_SOUL_SANDSTONE);
            this.getBiomeBlock(BiomeBlock.OCEAN_BLOCK, SOUL_SAND);
            this.getBiomeTraits(GenerationStage.DECORATE).addAll(Arrays.asList(
                    new BiomeTraitBoulder(4, false, 0.75D, 32, 124, SOUL_SANDSTONE, SOUL_SAND, 0),
                    new BiomeTraitBoulder(2, false, 0.75D, 32, 124, SOUL_SANDSTONE, SOUL_SAND, 1),
                    new BiomeTraitStructure(40, true, 1.0F, 32, 108, Arrays.asList(
                            NetherEx.getResource("spoul_shroom/spoul_shroom_01"),
                            NetherEx.getResource("spoul_shroom/spoul_shroom_02"),
                            NetherEx.getResource("spoul_shroom/spoul_shroom_03"),
                            NetherEx.getResource("spoul_shroom/spoul_shroom_04"),
                            NetherEx.getResource("spoul_shroom/spoul_shroom_05"),
                            NetherEx.getResource("spoul_shroom/spoul_shroom_06"),
                            NetherEx.getResource("spoul_shroom/spoul_shroom_07"),
                            NetherEx.getResource("spoul_shroom/spoul_shroom_08"),
                            NetherEx.getResource("spoul_shroom/spoul_shroom_09"),
                            NetherEx.getResource("spoul_shroom/spoul_shroom_10"),
                            NetherEx.getResource("spoul_shroom/spoul_shroom_11"),
                            NetherEx.getResource("spoul_shroom/spoul_shroom_12")),
                            BiomeTraitStructure.Type.GROUND, AIR.getBlock(), 1.0F),
                    new BiomeTraitStructure(1, true, 0.5F, 32, 108, Arrays.asList(
                            NetherEx.getResource("soul_sandstone_arch/soul_sandstone_arch_01")),
                            BiomeTraitStructure.Type.GROUND, AIR.getBlock(), 1.0F),
                    new BiomeTraitPatch(4, false, 0.85D, 32, 124, SOUL_SANDSTONE, SOUL_SAND, 6),
                    new BiomeTraitPatch(2, false, 0.85D, 32, 124, POSSESSED_SOUL_SAND, SOUL_SAND, 3)
            ));
            this.getBiomeTraits(GenerationStage.POST_DECORATE).addAll(Arrays.asList(
                    new BiomeTraitScatter(36, false, 1.0D, 32, 120, NetherExBlocks.SPOUL_SHROOM.getDefaultState(), SOUL_SAND, BiomeTraitScatter.Placement.ON_GROUND),
                    new BiomeTraitScatter(128, false, 1.0D, 32, 120, NetherExBlocks.SPOUL_VINES.getDefaultState(), NetherExBlocks.SPOUL_SHROOM_CAP.getDefaultState(), BiomeTraitScatter.Placement.ON_ROOF)
            ));
        }
    }
}
