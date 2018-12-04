package logictechcorp.netherex.world.biome.wrapper;

import com.electronwill.nightconfig.core.file.FileConfig;
import logictechcorp.libraryex.world.biome.wrapper.BiomeBlockType;
import logictechcorp.libraryex.world.gen.GenerationStage;
import logictechcorp.libraryex.world.gen.feature.FeatureCluster;
import logictechcorp.libraryex.world.gen.feature.FeatureFluid;
import logictechcorp.libraryex.world.gen.feature.FeatureOre;
import logictechcorp.libraryex.world.gen.feature.FeatureScatter;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;

public class NetherBiomeWrapperHell extends NetherBiomeWrapper
{
    public NetherBiomeWrapperHell()
    {
        super(Biomes.HELL.getRegistryName(), 10, true, true);
    }

    @Override
    public FileConfig serialize()
    {
        this.getBiomeBlock(BiomeBlockType.FLOOR_TOP_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlockType.FLOOR_FILLER_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlockType.WALL_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlockType.CEILING_FILLER_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlockType.CEILING_BOTTOM_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlockType.OCEAN_BLOCK, Blocks.LAVA.getDefaultState());
        this.getEntitySpawnEntries(EnumCreatureType.MONSTER).addAll(Arrays.asList(
                new Biome.SpawnListEntry(EntityGhast.class, 50, 1, 4),
                new Biome.SpawnListEntry(EntityPigZombie.class, 100, 1, 4),
                new Biome.SpawnListEntry(EntityMagmaCube.class, 3, 1, 4),
                new Biome.SpawnListEntry(EntityEnderman.class, 1, 1, 4),
                new Biome.SpawnListEntry(EntityBlaze.class, 2, 1, 4)
        ));
        this.getFeatures(GenerationStage.PRE_DECORATE).addAll(Arrays.asList(
                new FeatureFluid(8, 1.0D, false, 4, 124, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), false),
                new FeatureScatter(10, 1.0D, true, 4, 124, Blocks.FIRE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), FeatureScatter.Placement.ON_GROUND),
                new FeatureCluster(10, 1.0D, true, 4, 124, Blocks.GLOWSTONE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), EnumFacing.DOWN),
                new FeatureCluster(10, 1.0D, false, 1, 128, Blocks.GLOWSTONE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), EnumFacing.DOWN),
                new FeatureFluid(16, 1.0D, false, 10, 118, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), true)
        ));
        this.getFeatures(GenerationStage.DECORATE).addAll(Arrays.asList(
                new FeatureScatter(1, 0.25D, false, 1, 128, Blocks.RED_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), FeatureScatter.Placement.ON_GROUND),
                new FeatureScatter(1, 0.25D, false, 1, 128, Blocks.BROWN_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), FeatureScatter.Placement.ON_GROUND)
        ));
        this.getFeatures(GenerationStage.ORE).addAll(Arrays.asList(
                new FeatureOre(16, 1.0D, false, 10, 108, Blocks.QUARTZ_ORE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), 14),
                new FeatureOre(4, 1.0D, false, 28, 38, Blocks.MAGMA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), 32)
        ));
        return super.serialize();
    }
}
