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

import com.electronwill.nightconfig.core.Config;
import logictechcorp.libraryex.world.biome.BiomeBlockType;
import logictechcorp.libraryex.world.biome.BiomeInfo;
import logictechcorp.libraryex.world.gen.GenerationStage;
import logictechcorp.libraryex.world.gen.feature.FeatureBigMushroom;
import logictechcorp.libraryex.world.gen.feature.FeatureCluster;
import logictechcorp.libraryex.world.gen.feature.FeatureOre;
import logictechcorp.libraryex.world.gen.feature.FeatureStructure;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntitySpore;
import logictechcorp.netherex.entity.monster.EntitySporeCreeper;
import logictechcorp.netherex.entity.neutral.EntityMogus;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.world.biome.info.NetherBiomeInfo;
import logictechcorp.netherex.world.gen.feature.FeatureEnoki;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
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
    public BiomeInfo getInfo()
    {
        return new Info();
    }

    private class Info extends NetherBiomeInfo
    {
        public Info()
        {
            super(NetherExBiomes.FUNGI_FOREST.getRegistryName(), 4, true, true);
        }

        @Override
        public Config getAsConfig()
        {
            this.getBiomeBlock(BiomeBlockType.FLOOR_TOP_BLOCK, NetherExBlocks.HYPHAE.getDefaultState());
            this.getBiomeBlock(BiomeBlockType.FLOOR_FILLER_BLOCK, LIVELY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.WALL_BLOCK, LIVELY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.CEILING_FILLER_BLOCK, LIVELY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.CEILING_BOTTOM_BLOCK, LIVELY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.OCEAN_BLOCK, LAVA);
            this.getEntitySpawnEntries(EnumCreatureType.MONSTER).addAll(new ArrayList<>(Arrays.asList(
                    new Biome.SpawnListEntry(EntityMogus.class, 100, 4, 6),
                    new Biome.SpawnListEntry(EntitySpore.class, 25, 1, 4),
                    new Biome.SpawnListEntry(EntitySporeCreeper.class, 50, 1, 4)
            )));
            this.getFeatures(GenerationStage.PRE_DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureCluster(10, 1.0D, true, 4, 124, GLOWSTONE, LIVELY_NETHERRACK, EnumFacing.DOWN),
                    new FeatureCluster(10, 1.0D, false, 1, 128, GLOWSTONE, LIVELY_NETHERRACK, EnumFacing.DOWN),
                    new FeatureStructure(1, 0.0125D, false, 32, 116, new ResourceLocation(NetherEx.MOD_ID + ":ghast_queen_shrine"), FeatureStructure.Type.GROUNDED, Blocks.STRUCTURE_VOID, 0.75D)
            )));
            this.getFeatures(GenerationStage.DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureBigMushroom(256, 1.0D, false, 32, 108, NetherExBlocks.BROWN_ELDER_MUSHROOM_CAP.getDefaultState(), NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState(), HYPHAE, FeatureBigMushroom.Shape.FLAT),
                    new FeatureBigMushroom(256, 1.0D, false, 32, 108, NetherExBlocks.RED_ELDER_MUSHROOM_CAP.getDefaultState(), NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState(), HYPHAE, FeatureBigMushroom.Shape.BULB),
                    new FeatureEnoki(32, 1.0D, false, 48, 118)
            )));
            this.getFeatures(GenerationStage.ORE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureOre(16, 1.0D, false, 10, 108, NetherExBlocks.QUARTZ_ORE.getDefaultState(), LIVELY_NETHERRACK, 14)
            )));
            return super.getAsConfig();
        }
    }
}
