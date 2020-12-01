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

import logictechcorp.libraryex.world.biome.data.BiomeData;
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitBigMushroom;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitCluster;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitOre;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitStructure;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntitySpore;
import logictechcorp.netherex.entity.monster.EntitySporeCreeper;
import logictechcorp.netherex.entity.neutral.EntityMogus;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.world.generation.trait.BiomeTraitEnoki;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;

import java.util.Collections;

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
        BiomeData biomeData = new BiomeData(this, 4, true, false);
        biomeData.addBiomeBlock(BiomeData.BlockType.SURFACE_BLOCK, HYPHAE);
        biomeData.addBiomeBlock(BiomeData.BlockType.SUBSURFACE_BLOCK, LIVELY_NETHERRACK);
        biomeData.addBiomeBlock(BiomeData.BlockType.LIQUID_BLOCK, LAVA);
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitCluster.create(trait ->
        {
            trait.generationAttempts(10);
            trait.randomizeGenerationAttempts(true);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToAttachTo(LIVELY_NETHERRACK);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitCluster.create(trait ->
        {
            trait.generationAttempts(10);
            trait.minimumGenerationHeight(1);
            trait.maximumGenerationHeight(128);
            trait.blockToAttachTo(LIVELY_NETHERRACK);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitBigMushroom.create(trait ->
        {
            trait.generationAttempts(256);
            trait.minimumGenerationHeight(32);
            trait.maximumGenerationHeight(108);
            trait.mushroomCap(NetherExBlocks.BROWN_ELDER_MUSHROOM_CAP.getDefaultState());
            trait.mushroomStem(NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState());
            trait.blockToPlaceOn(HYPHAE);
            trait.shape(BiomeTraitBigMushroom.Shape.FLAT);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitBigMushroom.create(trait ->
        {
            trait.generationAttempts(256);
            trait.minimumGenerationHeight(32);
            trait.maximumGenerationHeight(108);
            trait.mushroomCap(NetherExBlocks.RED_ELDER_MUSHROOM_CAP.getDefaultState());
            trait.mushroomStem(NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState());
            trait.blockToPlaceOn(HYPHAE);
            trait.shape(BiomeTraitBigMushroom.Shape.BULB);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitEnoki.create(trait ->
        {
            trait.generationAttempts(32);
            trait.minimumGenerationHeight(48);
            trait.maximumGenerationHeight(118);
        }));
        biomeData.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(10);
            trait.maximumGenerationHeight(108);
            trait.blockToSpawn(QUARTZ_ORE);
            trait.blockToReplace(LIVELY_NETHERRACK);
            trait.veinSize(14);
        }));
        biomeData.addBiomeTrait(GenerationStage.STRUCTURE, BiomeTraitStructure.create(trait ->
        {
            trait.generationAttempts(1);
            trait.generationProbability(0.0125D);
            trait.minimumGenerationHeight(32);
            trait.maximumGenerationHeight(118);
            trait.structures(Collections.singletonList(NetherEx.getResource("ghast_queen_shrine")));
            trait.structureType(BiomeTraitStructure.StructureType.AIR);
            trait.clearancePercentage(1.0D);
        }));
        return biomeData;
    }
}
