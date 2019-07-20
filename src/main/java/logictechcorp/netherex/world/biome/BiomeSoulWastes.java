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

import logictechcorp.libraryex.api.world.biome.data.IBiomeData;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntityLostSoul;
import logictechcorp.netherex.entity.monster.EntityRipper;

public class BiomeSoulWastes extends BiomeNetherEx
{
    //private static final IBlockState SOUL_SANDSTONE = NetherExBlocks.SOUL_SANDSTONE.getDefaultState();
    //private static final IBlockState CUT_SOUL_SANDSTONE = NetherExBlocks.CUT_SOUL_SANDSTONE.getDefaultState();
    //private static final IBlockState POSSESSED_SOUL_SAND = NetherExBlocks.POSSESSED_SOUL_SAND.getDefaultState();

    public BiomeSoulWastes()
    {
        super(NetherEx.instance, new BiomeProperties("Soul Wastes").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "soul_wastes");
        this.topBlock = SOUL_SAND;
        this.fillerBlock = SOUL_SAND;
        this.spawnableMonsterList.add(new SpawnListEntry(EntityLostSoul.class, 25, 2, 8));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityRipper.class, 2, 1, 1));
    }

    @Override
    public IBiomeData getBiomeData()
    {
        //IBiomeData biomeData = new BiomeData(NetherExBiomes.SOUL_WASTES, 8, false, true, true);
        //biomeData.getBiomeBlock(BiomeBlock.FLOOR_TOP_BLOCK, SOUL_SAND);
        //biomeData.getBiomeBlock(BiomeBlock.FLOOR_FILLER_BLOCK, SOUL_SAND);
        //biomeData.getBiomeBlock(BiomeBlock.WALL_BLOCK, SOUL_SANDSTONE);
        //biomeData.getBiomeBlock(BiomeBlock.CEILING_FILLER_BLOCK, SOUL_SANDSTONE);
        //biomeData.getBiomeBlock(BiomeBlock.CEILING_BOTTOM_BLOCK, CUT_SOUL_SANDSTONE);
        //biomeData.getBiomeBlock(BiomeBlock.OCEAN_BLOCK, SOUL_SAND);
        //biomeData.getBiomeTraits(GenerationStage.DECORATION).addAll(Arrays.asList(
        //        BiomeTraitBoulder.create(trait ->
        //        {
        //            trait.generationAttempts(4);
        //            trait.generationProbability(0.75D);
        //            trait.minimumGenerationHeight(32);
        //            trait.maximumGenerationHeight(124);
        //            trait.blockToSpawn(SOUL_SANDSTONE);
        //            trait.blockToTarget(SOUL_SAND);
        //            trait.boulderRadius(0);
        //        }),
        //        BiomeTraitBoulder.create(trait ->
        //        {
        //            trait.generationAttempts(2);
        //            trait.generationProbability(0.75D);
        //            trait.minimumGenerationHeight(32);
        //            trait.maximumGenerationHeight(124);
        //            trait.blockToSpawn(SOUL_SANDSTONE);
        //            trait.blockToTarget(SOUL_SAND);
        //            trait.boulderRadius(1);
        //        }),
        //        BiomeTraitStructure.create(trait ->
        //        {
        //            trait.generationAttempts(40);
        //            trait.randomizeGenerationAttempts(true);
        //            trait.minimumGenerationHeight(32);
        //            trait.maximumGenerationHeight(108);
        //            trait.structures(Arrays.asList(
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_01"),
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_02"),
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_03"),
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_04"),
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_05"),
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_06"),
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_07"),
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_08"),
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_09"),
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_10"),
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_11"),
        //                    NetherEx.getResource("spoul_shroom/spoul_shroom_12")));
        //            trait.structureType(BiomeTraitStructure.StructureType.GROUND);
        //            trait.ignoredBlock(AIR.getBlock());
        //            trait.clearancePercentage(1.0D);
        //            trait.orientRandomly(true);
        //        }),
        //        BiomeTraitStructure.create(trait ->
        //        {
        //            trait.generationAttempts(1);
        //            trait.generationProbability(0.5D);
        //            trait.minimumGenerationHeight(32);
        //            trait.maximumGenerationHeight(108);
        //            trait.structures(Collections.singletonList(NetherEx.getResource("soul_sandstone_arch/soul_sandstone_arch_01")));
        //            trait.structureType(BiomeTraitStructure.StructureType.GROUND);
        //            trait.ignoredBlock(AIR.getBlock());
        //            trait.clearancePercentage(1.0D);
        //            trait.orientRandomly(true);
        //        }),
        //        BiomeTraitPatch.create(trait ->
        //        {
        //            trait.generationAttempts(4);
        //            trait.generationProbability(0.85D);
        //            trait.minimumGenerationHeight(32);
        //            trait.maximumGenerationHeight(124);
        //            trait.blockToSpawn(SOUL_SANDSTONE);
        //            trait.blockToTarget(SOUL_SAND);
        //            trait.patchWidth(6);
        //        }),
        //        BiomeTraitPatch.create(trait ->
        //        {
        //            trait.generationAttempts(2);
        //            trait.generationProbability(0.85D);
        //            trait.minimumGenerationHeight(32);
        //            trait.maximumGenerationHeight(124);
        //            trait.blockToSpawn(POSSESSED_SOUL_SAND);
        //            trait.blockToTarget(SOUL_SAND);
        //            trait.patchWidth(3);
        //        })
        //));
        //biomeData.getBiomeTraits(GenerationStage.PLANT_DECORATION).addAll(Arrays.asList(
        //        BiomeTraitScatter.create(trait ->
        //        {
        //            trait.generationAttempts(36);
        //            trait.minimumGenerationHeight(32);
        //            trait.maximumGenerationHeight(120);
        //            trait.blockToSpawn(NetherExBlocks.SPOUL_SHROOM.getDefaultState());
        //            trait.blockToTarget(SOUL_SAND);
        //            trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
        //        }),
        //        BiomeTraitScatter.create(trait ->
        //        {
        //            trait.generationAttempts(128);
        //            trait.minimumGenerationHeight(32);
        //            trait.maximumGenerationHeight(120);
        //            trait.blockToSpawn(NetherExBlocks.SPOUL_VINES.getDefaultState());
        //            trait.blockToTarget(NetherExBlocks.SPOUL_SHROOM_CAP.getDefaultState());
        //            trait.placement(BiomeTraitScatter.Placement.ON_ROOF);
        //        })
        //));
        //return biomeData;
        return null;
    }
}
