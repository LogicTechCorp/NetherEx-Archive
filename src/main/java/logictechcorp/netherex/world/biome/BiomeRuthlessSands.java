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
import logictechcorp.libraryex.world.generation.trait.BiomeTraitCluster;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitFluid;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitOre;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntitySpinout;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.world.generation.trait.BiomeTraitThornstalk;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.world.biome.Biome;

public class BiomeRuthlessSands extends BiomeNetherEx
{
    private static final IBlockState GLOOMY_NETHERRACK = NetherExBlocks.GLOOMY_NETHERRACK.getDefaultState();

    public BiomeRuthlessSands()
    {
        super(NetherEx.instance, new BiomeProperties("Ruthless Sands").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "ruthless_sands");
        this.topBlock = SOUL_SAND;
        this.fillerBlock = GLOOMY_NETHERRACK;
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityWitherSkeleton.class, 65, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 45, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpinout.class, 100, 1, 4));
    }

    @Override
    public BiomeData getBiomeData()
    {
        BiomeData biomeData = new BiomeData(NetherExBiomes.RUTHLESS_SANDS, 8, true, false);
        biomeData.addBiomeBlock(BiomeData.BlockType.SURFACE_BLOCK, SOUL_SAND);
        biomeData.addBiomeBlock(BiomeData.BlockType.SUBSURFACE_BLOCK, GLOOMY_NETHERRACK);
        biomeData.addBiomeBlock(BiomeData.BlockType.LIQUID_BLOCK, LAVA);
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitFluid.create(trait ->
        {
            trait.generationAttempts(8);
            trait.generationProbability(1.0D);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToSpawn(FLOWING_LAVA);
            trait.blockToTarget(GLOOMY_NETHERRACK);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitCluster.create(trait ->
        {
            trait.generationAttempts(10);
            trait.randomizeGenerationAttempts(true);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToAttachTo(GLOOMY_NETHERRACK);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitCluster.create(trait ->
        {
            trait.generationAttempts(10);
            trait.minimumGenerationHeight(1);
            trait.maximumGenerationHeight(128);
            trait.blockToAttachTo(GLOOMY_NETHERRACK);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitFluid.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(10);
            trait.maximumGenerationHeight(118);
            trait.blockToSpawn(FLOWING_LAVA);
            trait.blockToTarget(GLOOMY_NETHERRACK);
            trait.generateFalling(true);
        }));
        biomeData.addBiomeTrait(GenerationStage.PLANT_DECORATION, BiomeTraitThornstalk.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(32);
            trait.maximumGenerationHeight(108);
        }));
        biomeData.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(10);
            trait.maximumGenerationHeight(108);
            trait.blockToSpawn(NetherExBlocks.QUARTZ_ORE.getDefaultState());
            trait.blockToReplace(GLOOMY_NETHERRACK);
            trait.veinSize(14);
        }));
        return biomeData;
    }
}