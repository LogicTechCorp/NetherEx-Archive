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

import com.electronwill.nightconfig.core.file.FileConfig;
import lex.world.gen.GenerationStage;
import lex.world.gen.feature.FeatureBigMushroom;
import lex.world.gen.feature.FeatureCluster;
import lex.world.gen.feature.FeatureOre;
import lex.world.gen.feature.FeatureStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.block.BlockElderMushroom;
import nex.block.BlockElderMushroomCap;
import nex.block.BlockNetherrack;
import nex.entity.monster.EntitySpore;
import nex.entity.monster.EntitySporeCreeper;
import nex.entity.neutral.EntityMogus;
import nex.init.NetherExBiomes;
import nex.init.NetherExBlocks;
import nex.world.gen.feature.FeatureEnoki;

import java.util.ArrayList;
import java.util.Arrays;

public class BiomeFungiForest extends BiomeNetherEx
{
    private static final IBlockState HYPHAE = NetherExBlocks.HYPHAE.getDefaultState();
    private static final IBlockState LIVELY_NETHERRACK = NEX_NETHERRACK.withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.LIVELY);

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
    public INetherBiomeWrapper getWrapper()
    {
        return new Wrapper();
    }

    private class Wrapper extends NetherBiomeWrapper
    {
        public Wrapper()
        {
            super(NetherExBiomes.FUNGI_FOREST.getRegistryName(), 4, true, true);
        }

        @Override
        public FileConfig serialize()
        {
            this.getBiomeBlock("topBlock", NetherExBlocks.HYPHAE.getDefaultState());
            this.getBiomeBlock("fillerBlock", LIVELY_NETHERRACK);
            this.getBiomeBlock("wallBlock", LIVELY_NETHERRACK);
            this.getBiomeBlock("ceilingBottomBlock", LIVELY_NETHERRACK);
            this.getBiomeBlock("ceilingFillerBlock", LIVELY_NETHERRACK);
            this.getBiomeBlock("oceanBlock", LAVA);
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
                    new FeatureBigMushroom(256, 1.0D, false, 32, 108, NetherExBlocks.ELDER_MUSHROOM_CAP.getDefaultState().withProperty(BlockElderMushroomCap.TYPE, BlockElderMushroom.EnumType.BROWN), NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState(), HYPHAE, FeatureBigMushroom.Shape.FLAT),
                    new FeatureBigMushroom(256, 1.0D, false, 32, 108, NetherExBlocks.ELDER_MUSHROOM_CAP.getDefaultState().withProperty(BlockElderMushroomCap.TYPE, BlockElderMushroom.EnumType.RED), NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState(), HYPHAE, FeatureBigMushroom.Shape.BULB),
                    new FeatureEnoki(32, 1.0D, false, 48, 118)
            )));
            this.getFeatures(GenerationStage.ORE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureOre(16, 1.0D, false, 10, 108, NetherExBlocks.QUARTZ_ORE.getDefaultState(), LIVELY_NETHERRACK, 14)
            )));
            return super.serialize();
        }
    }
}
