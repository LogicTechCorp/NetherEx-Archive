package nex.world.biome;

import com.electronwill.nightconfig.core.file.FileConfig;
import lex.world.gen.GenerationStage;
import lex.world.gen.feature.FeatureCluster;
import lex.world.gen.feature.FeatureFluid;
import lex.world.gen.feature.FeatureOre;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.entity.passive.EntityBonspider;
import nex.init.NetherExBiomes;
import nex.init.NetherExBlocks;

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
    public INetherBiomeWrapper getWrapper()
    {
        return new Wrapper();
    }

    private class Wrapper extends NetherBiomeWrapper
    {
        public Wrapper()
        {
            super(NetherExBiomes.REGROWTHS_COLLAPSE.getRegistryName(), 1, true, true);
        }

        @Override
        public FileConfig serialize()
        {
            this.getBiomeBlock("topBlock", Blocks.GRASS.getDefaultState());
            this.getBiomeBlock("fillerBlock", Blocks.DIRT.getDefaultState());
            this.getBiomeBlock("wallBlock", Blocks.STONE.getDefaultState());
            this.getBiomeBlock("ceilingBottomBlock", Blocks.STONE.getDefaultState());
            this.getBiomeBlock("ceilingFillerBlock", Blocks.STONE.getDefaultState());
            this.getBiomeBlock("oceanBlock", Blocks.WATER.getDefaultState());
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
            return super.serialize();
        }
    }
}
