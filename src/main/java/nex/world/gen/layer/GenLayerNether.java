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
        GenLayer genLayer = new GenLayerNetherBiome(1L);
        genLayer = new GenLayerZoom(1000L, genLayer);
        genLayer = new GenLayerZoom(1001L, genLayer);
        genLayer = new GenLayerZoom(1002L, genLayer);
        genLayer = new GenLayerZoom(1003L, genLayer);
        GenLayer genLayerVZoom = new GenLayerVoronoiZoom(10L, genLayer);

        genLayer.initWorldGenSeed(seed);
        genLayerVZoom.initWorldGenSeed(seed);

        return new GenLayer[]{genLayer, genLayerVZoom};
    }
}