package nex.block;

import lex.IModData;
import lex.block.BlockDynamic;
import lex.world.biome.BiomeConfigurations;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;
import nex.world.biome.NetherExBiomeManager;

public abstract class BlockDynamicNetherBiome extends BlockDynamic
{
    public BlockDynamicNetherBiome(IModData data, String name, Material material, TexturePlacement texturePlacement)
    {
        super(data, name, material, texturePlacement);
    }

    @Override
    public IBlockState getDynamicState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        Biome biome = world.getBiome(pos);
        BiomeConfigurations configurations = NetherExBiomeManager.getBiomeConfigurations(biome);

        if(configurations != null)
        {
            IBlockState fillerBlock = configurations.getBlock("fillerBlock", null);
            IBlockState wallBlock = configurations.getBlock("wallBlock", null);

            if(fillerBlock == wallBlock)
            {
                return fillerBlock;
            }

            return wallBlock;
        }

        return Blocks.NETHERRACK.getDefaultState();
    }
}
