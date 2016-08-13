package nex.api.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public interface INetherBiome
{
    Biome getBiome();

    void decorate(World world, Random rand, BlockPos pos);

    void genDecorations(World world, Random rand, BlockPos pos);

    void generateOres(World world, Random rand, BlockPos pos);
}
