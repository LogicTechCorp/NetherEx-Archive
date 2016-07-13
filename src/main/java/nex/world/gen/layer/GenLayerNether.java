package nex.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerNether extends GenLayer
{
    public GenLayerNether(long seed)
    {
        super(seed);
    }

    public static GenLayer[] initializeAllBiomeGenerators(long seed)
    {
        GenLayer genLayerNBiomes = new GenLayerNetherBiome(1L);
        genLayerNBiomes = new GenLayerZoom(1000L, genLayerNBiomes);
        genLayerNBiomes = new GenLayerZoom(1001L, genLayerNBiomes);
        genLayerNBiomes = new GenLayerZoom(1002L, genLayerNBiomes);
        genLayerNBiomes = new GenLayerZoom(1003L, genLayerNBiomes);
        GenLayer genLayerVZoom = new GenLayerVoronoiZoom(10L, genLayerNBiomes);
        genLayerNBiomes.initWorldGenSeed(seed);
        genLayerVZoom.initWorldGenSeed(seed);
        return new GenLayer[]{genLayerNBiomes, genLayerVZoom};
    }
}
