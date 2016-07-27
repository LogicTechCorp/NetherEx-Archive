package nex.world.biome;

import net.minecraft.init.Blocks;
import nex.api.biome.feature.BiomeFeatureGlowStone;
import nex.api.biome.feature.BiomeFeatureMinable;
import nex.registry.ModBlocks;

public class BiomeFreezingBlizzard extends NetherBiomeBase
{
    public BiomeFreezingBlizzard(int id, String name, int weight, BiomeProperties properties)
    {
        super(id, name, weight, properties);

        topBlock = Blocks.PACKED_ICE.getDefaultState();
        fillerBlock = ModBlocks.NETHERRACK.getStateFromMeta(2);
        oceanBlock = Blocks.MAGMA.getDefaultState();

        biomeFeatures.add(new BiomeFeatureGlowStone(10, 4, 120));
        biomeFeatures.add(new BiomeFeatureMinable(16, 10, 108, ModBlocks.QUARTZ_ORE.getStateFromMeta(2), 14, ModBlocks.NETHERRACK.getStateFromMeta(2)));
    }
}
