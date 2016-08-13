package nex.api.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public interface INetherBiome
{
    /**
     * Returns the Biome this is implemented in
     *
     * @return The biome this is implemented in
     */
    Biome getBiome();

    /**
     * Call super.decorate(world, rand, pos);
     *
     * @param world The current world
     * @param rand  The random number generator
     * @param pos   The block position in world
     */
    void decorate(World world, Random rand, BlockPos pos);

    /**
     * Used to decorate the biome
     *
     * @param world The current world
     * @param rand  The random number generator
     * @param pos   The block position in world
     */
    void genDecorations(World world, Random rand, BlockPos pos);

    /**
     * Used to generate ore in the biome
     *
     * @param world The current world
     * @param rand  The random number generator
     * @param pos   The block position in world
     */
    void generateOres(World world, Random rand, BlockPos pos);
}
