package nex.api.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public interface INetherBiome
{
    void decorate(World world, Random rand, BlockPos pos);

    Biome getBiome();

    boolean generateFire();

    boolean generateGlowStone();

    boolean generateQuartz();

    boolean generateMagma();

    boolean generateLavaTrap();

    boolean generateLavaSpring();

    boolean generateMushrooms();
}
