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
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitBigMushroom;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitCluster;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitOre;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitStructure;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntitySpore;
import logictechcorp.netherex.entity.monster.EntitySporeCreeper;
import logictechcorp.netherex.entity.neutral.EntityMogus;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.world.biome.data.BiomeDataNetherEx;
import logictechcorp.netherex.world.generation.trait.BiomeTraitEnoki;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Arrays;

public class BiomeFungiForest extends BiomeNetherEx
{
    private static final IBlockState HYPHAE = NetherExBlocks.HYPHAE.getDefaultState();
    private static final IBlockState LIVELY_NETHERRACK = NetherExBlocks.LIVELY_NETHERRACK.getDefaultState();

    public BiomeFungiForest()
    {
        super(NetherEx.instance, new BiomeProperties("Fungi Forest").setTemperature(1.1F).setRainfall(0.0F).setRainDisabled(), "fungi_forest");
        this.topBlock = HYPHAE;
        this.fillerBlock = LIVELY_NETHERRACK;
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMogus.class, 100, 4, 6));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpore.class, 25, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySporeCreeper.class, 50, 1, 4));
    }

    @Override
    public BiomeData getBiomeData()
    {
        return new BiomeData();
    }

    private class BiomeData extends BiomeDataNetherEx
    {
        BiomeData()
        {
            super(NetherExBiomes.FUNGI_FOREST, 4, true, true);
            this.getBiomeBlock(BiomeBlock.FLOOR_TOP_BLOCK, NetherExBlocks.HYPHAE.getDefaultState());
            this.getBiomeBlock(BiomeBlock.FLOOR_FILLER_BLOCK, LIVELY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.WALL_BLOCK, LIVELY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.CEILING_FILLER_BLOCK, LIVELY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.CEILING_BOTTOM_BLOCK, LIVELY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.OCEAN_BLOCK, LAVA);
            this.getBiomeEntities(EnumCreatureType.MONSTER).addAll(new ArrayList<>(Arrays.asList(
                    new Biome.SpawnListEntry(EntityMogus.class, 100, 4, 6),
                    new Biome.SpawnListEntry(EntitySpore.class, 25, 1, 4),
                    new Biome.SpawnListEntry(EntitySporeCreeper.class, 50, 1, 4)
            )));
            this.getBiomeTraits(GenerationStage.PRE_DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new BiomeTraitCluster(10, true, 1.0D, 4, 124, GLOWSTONE, LIVELY_NETHERRACK, EnumFacing.DOWN),
                    new BiomeTraitCluster(10, false, 1.0D, 1, 128, GLOWSTONE, LIVELY_NETHERRACK, EnumFacing.DOWN),
                    new BiomeTraitStructure(1, false, 0.0125D, 32, 116, NetherEx.getResource("ghast_queen_shrine"), BiomeTraitStructure.Type.GROUNDED, Blocks.STRUCTURE_VOID, 0.75D)
            )));
            this.getBiomeTraits(GenerationStage.DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new BiomeTraitBigMushroom(256, false, 1.0D, 32, 108, NetherExBlocks.BROWN_ELDER_MUSHROOM_CAP.getDefaultState(), NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState(), HYPHAE, BiomeTraitBigMushroom.Shape.FLAT),
                    new BiomeTraitBigMushroom(256, false, 1.0D, 32, 108, NetherExBlocks.RED_ELDER_MUSHROOM_CAP.getDefaultState(), NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState(), HYPHAE, BiomeTraitBigMushroom.Shape.BULB),
                    new BiomeTraitEnoki(32, false, 1.0D, 48, 118)
            )));
            this.getBiomeTraits(GenerationStage.ORE).addAll(new ArrayList<>(Arrays.asList(
                    new BiomeTraitOre(16, false, 1.0D, 10, 108, NetherExBlocks.QUARTZ_ORE.getDefaultState(), LIVELY_NETHERRACK, 14)
            )));
        }
    }
}
