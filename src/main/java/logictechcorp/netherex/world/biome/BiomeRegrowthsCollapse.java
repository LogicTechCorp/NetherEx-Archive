package logictechcorp.netherex.world.biome;

import com.electronwill.nightconfig.core.Config;
import logictechcorp.libraryex.world.biome.BiomeBlockType;
import logictechcorp.libraryex.world.biome.BiomeInfo;
import logictechcorp.libraryex.world.gen.GenerationStage;
import logictechcorp.libraryex.world.gen.feature.FeatureCluster;
import logictechcorp.libraryex.world.gen.feature.FeatureFluid;
import logictechcorp.libraryex.world.gen.feature.FeatureOre;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.passive.EntityBonspider;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.world.biome.info.NetherBiomeInfo;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Arrays;

public class BiomeRegrowthsCollapse extends BiomeNetherEx
{
    public BiomeRegrowthsCollapse()
    {
        super(NetherEx.instance, new BiomeProperties("Regrowth's Collapse").setTemperature(1.1F).setRainfall(0.0F).setRainDisabled(), "regrowths_collapse");
        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityBonspider.class, 65, 2, 4));

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
            super(NetherExBiomes.REGROWTHS_COLLAPSE.getRegistryName(), 2, true, true);
        }

        @Override
        public Config getAsConfig()
        {
            this.getBiomeBlock(BiomeBlockType.FLOOR_TOP_BLOCK, Blocks.GRASS.getDefaultState());
            this.getBiomeBlock(BiomeBlockType.FLOOR_FILLER_BLOCK, Blocks.DIRT.getDefaultState());
            this.getBiomeBlock(BiomeBlockType.WALL_BLOCK, Blocks.STONE.getDefaultState());
            this.getBiomeBlock(BiomeBlockType.CEILING_BOTTOM_BLOCK, Blocks.STONE.getDefaultState());
            this.getBiomeBlock(BiomeBlockType.CEILING_FILLER_BLOCK, Blocks.STONE.getDefaultState());
            this.getBiomeBlock(BiomeBlockType.OCEAN_BLOCK, Blocks.WATER.getDefaultState());
            this.getEntitySpawnEntries(EnumCreatureType.CREATURE).addAll(new ArrayList<>(Arrays.asList(
                    new Biome.SpawnListEntry(EntityBonspider.class, 65, 2, 4)
            )));
            this.getFeatures(GenerationStage.PRE_DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureFluid(8, 1.0D, false, 4, 124, Blocks.WATER.getDefaultState(), Blocks.STONE.getDefaultState(), false),
                    new FeatureCluster(10, 1.0D, true, 4, 124, GLOWSTONE, Blocks.STONE.getDefaultState(), EnumFacing.DOWN),
                    new FeatureCluster(10, 1.0D, false, 1, 128, GLOWSTONE, Blocks.STONE.getDefaultState(), EnumFacing.DOWN),
                    new FeatureFluid(16, 1.0D, false, 10, 118, Blocks.FLOWING_WATER.getDefaultState(), Blocks.STONE.getDefaultState(), true)
            )));
            this.getFeatures(GenerationStage.ORE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureOre(1, 0.125D, false, 10, 108, NetherExBlocks.COBALT_ORE.getDefaultState(), Blocks.STONE.getDefaultState(), 8)
            )));
            return super.getAsConfig();
        }
    }
}
