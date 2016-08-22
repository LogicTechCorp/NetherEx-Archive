package nex.api;

import net.minecraft.block.state.IBlockState;

/**
 * An interface that will allow you to enhance different aspects of
 * a Nether biome
 * <p>
 * Do not implement this interface unless you want to change a specific
 * aspect of your Nether biome
 */
public interface IEnhancedNetherBiome
{
    /**
     * Return an IBlockState that will replace the default ocean block
     * The IBlockState's material must not be water
     *
     * @return An IBlockState that will replace the default ocean block
     */
    IBlockState getOceanBlock();
}
