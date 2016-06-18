package nex.world.gen.layer;

import net.minecraft.world.gen.layer.*;

public abstract class GenLayerNether extends GenLayer
{
    public GenLayerNether(long seed)
    {
        super(seed);
    }

    public static GenLayer[] initBiomeGen(long seed)
    {
        GenLayer genLayer = new GenLayerIsland(1L);
        genLayer = new GenLayerFuzzyZoom(2000L, genLayer);
        genLayer = new GenLayerZoom(2001L, genLayer);
        genLayer = new GenLayerZoom(2002L, genLayer);
        genLayer = new GenLayerNetherBiome(200L, genLayer);
        genLayer = new GenLayerZoom(1000L, genLayer);
        genLayer = new GenLayerZoom(1001L, genLayer);
        genLayer = new GenLayerZoom(1002L, genLayer);
        GenLayerVoronoiZoom genLayerVZoom = new GenLayerVoronoiZoom(10L, genLayer);
        genLayer.initWorldGenSeed(seed);
        genLayerVZoom.initWorldGenSeed(seed);
        return new GenLayer[]{genLayer, genLayerVZoom};
    }
}
