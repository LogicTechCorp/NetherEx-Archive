package nex.world.biome.vanilla;

import com.electronwill.nightconfig.core.file.FileConfig;
import lex.world.biome.BiomeConfigurations;
import lex.world.gen.GenerationStage;
import lex.world.gen.feature.*;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.init.NetherExBlocks;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class BiomeConfigurationsHell extends BiomeConfigurations
{
    public BiomeConfigurationsHell()
    {
        super(Biomes.HELL.getRegistryName(), 10, true, true, new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    @Override
    public FileConfig serialize(File configFile)
    {
        this.getBlock("topBlock", Blocks.NETHERRACK.getDefaultState());
        this.getBlock("fillerBlock", Blocks.NETHERRACK.getDefaultState());
        this.getBlock("wallBlock", Blocks.NETHERRACK.getDefaultState());
        this.getBlock("ceilingBottomBlock", Blocks.NETHERRACK.getDefaultState());
        this.getBlock("ceilingFillerBlock", Blocks.NETHERRACK.getDefaultState());
        this.getBlock("oceanBlock", Blocks.LAVA.getDefaultState());
        this.getEntities(EnumCreatureType.MONSTER).addAll(Arrays.asList(
                new Biome.SpawnListEntry(EntityGhast.class, 50, 1, 4),
                new Biome.SpawnListEntry(EntityPigZombie.class, 100, 1, 4),
                new Biome.SpawnListEntry(EntityMagmaCube.class, 3, 1, 4),
                new Biome.SpawnListEntry(EntityEnderman.class, 1, 1, 4),
                new Biome.SpawnListEntry(EntityBlaze.class, 2, 1, 4)
        ));
        this.getFeatures(GenerationStage.POPULATE).addAll(Arrays.asList(
                new FeatureFluid(8, 1.0D, false, 4, 124, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), false),
                new FeatureScatter(10, 1.0D, true, 4, 124, Blocks.FIRE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), FeatureScatter.Placement.ON_GROUND),
                new FeatureCluster(10, 1.0D, true, 4, 124, Blocks.GLOWSTONE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), EnumFacing.DOWN),
                new FeatureCluster(10, 1.0D, false, 1, 128, Blocks.GLOWSTONE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), EnumFacing.DOWN),
                new FeatureFluid(16, 1.0D, false, 10, 118, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), true),
                new FeatureStructure(1, 1.0D, false, 32, 116, new ResourceLocation(NetherEx.MOD_ID + ":village/tiny_hell_pigtificate_village"), FeatureStructure.Type.GROUNDED, Blocks.STRUCTURE_VOID, 0.75D)
        ));
        this.getFeatures(GenerationStage.DECORATE).addAll(Arrays.asList(
                new FeatureScatter(1, 0.25D, false, 1, 128, Blocks.RED_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), FeatureScatter.Placement.ON_GROUND),
                new FeatureScatter(1, 0.25D, false, 1, 128, Blocks.BROWN_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), FeatureScatter.Placement.ON_GROUND)
        ));
        this.getFeatures(GenerationStage.ORE).addAll(Arrays.asList(
                new FeatureOre(16, 1.0D, false, 10, 108, Blocks.QUARTZ_ORE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), 14),
                new FeatureOre(16, 1.0D, false, 10, 108, NetherExBlocks.AMETHYST_ORE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), 3),
                new FeatureOre(4, 1.0D, false, 28, 38, Blocks.MAGMA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), 32)
        ));
        return super.serialize(configFile);
    }
}
