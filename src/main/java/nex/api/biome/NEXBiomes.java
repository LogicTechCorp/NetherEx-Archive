package nex.api.biome;

import com.google.common.base.Optional;
import net.minecraft.world.biome.Biome;

public class NEXBiomes
{
    public static Optional<Biome> hell = Optional.absent();

    public static final Registry REGISTRY = createRegistry();

    private static Registry createRegistry()
    {
        Registry registry = null;

        try
        {
            registry = (Registry) Class.forName("nex.registry.BiomeRegistry").newInstance();
        }
        catch(ClassNotFoundException | IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace();
        }

        return registry;
    }

    public interface Registry
    {
        Biome addBiome(INetherBiome biome);

        Biome addBiome(INetherBiome biome, int weight);
    }
}
