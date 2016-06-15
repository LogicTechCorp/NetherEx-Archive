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

    public static GenLayer[] initBiomeGen(long seed)
    {
        GenLayerNetherBiome biomes = new GenLayerNetherBiome(1L);
        GenLayerZoom genLayerZoom = new GenLayerZoom(1000L, biomes);
        genLayerZoom = new GenLayerZoom(1001L, genLayerZoom);
        genLayerZoom = new GenLayerZoom(1002L, genLayerZoom);
        genLayerZoom = new GenLayerZoom(1003L, genLayerZoom);
        genLayerZoom = new GenLayerZoom(1004L, genLayerZoom);
        genLayerZoom = new GenLayerZoom(1005L, genLayerZoom);
        GenLayerVoronoiZoom genLayerVZoom = new GenLayerVoronoiZoom(1L, genLayerZoom);
        genLayerZoom.initWorldGenSeed(seed);
        genLayerVZoom.initWorldGenSeed(seed);
        return new GenLayer[]{genLayerZoom, genLayerVZoom};
    }
}
