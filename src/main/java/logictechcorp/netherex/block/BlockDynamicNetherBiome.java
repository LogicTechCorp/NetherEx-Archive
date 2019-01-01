package logictechcorp.netherex.block;

import logictechcorp.libraryex.block.BlockDynamic;
import logictechcorp.libraryex.block.builder.BlockBuilder;
import logictechcorp.libraryex.world.biome.BiomeBlockType;
import logictechcorp.libraryex.world.biome.BiomeInfo;
import logictechcorp.netherex.world.biome.NetherBiomeManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;

public abstract class BlockDynamicNetherBiome extends BlockDynamic
{
    public BlockDynamicNetherBiome(ResourceLocation registryName, TexturePlacement texturePlacement, BlockBuilder builder)
    {
        super(registryName, texturePlacement, builder);
    }

    @Override
    public IBlockState getDynamicState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        Biome biome = world.getBiome(pos);
        BiomeInfo info = NetherBiomeManager.INSTANCE.getAllBiomeInfo(biome);

        if(info != null)
        {
            return info.getBiomeBlock(BiomeBlockType.WALL_BLOCK, null);
        }

        return Blocks.NETHERRACK.getDefaultState();
    }
}
