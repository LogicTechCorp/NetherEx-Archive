package logictechcorp.netherex.block;

import logictechcorp.libraryex.block.BlockMod;
import logictechcorp.libraryex.block.builder.BlockBuilder;
import logictechcorp.libraryex.util.BlockHelper;
import logictechcorp.netherex.capability.CapabilityBlightChunkData;
import logictechcorp.netherex.capability.IBlightChunkData;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

public abstract class BlockBlight extends BlockMod
{
    public BlockBlight(ResourceLocation registryName, BlockBuilder builder)
    {
        super(registryName, builder);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!world.isRemote && world.isAreaLoaded(pos, 2))
        {
            spread(world, pos, rand);
        }
    }

    public static boolean spread(World world, BlockPos pos, Random rand)
    {
        BlockPos newPos = pos.add(rand.nextInt(7) - 3, rand.nextInt(7) - 3, rand.nextInt(7) - 3);
        BlockPos upPos = newPos.up();

        if(world.getLightFromNeighbors(upPos) <= 7)
        {
            IBlockState state = world.getBlockState(newPos);
            IBlockState upState = world.getBlockState(upPos);

            if(!state.getMaterial().isReplaceable() && !state.getMaterial().isLiquid() && state.isFullBlock() && state.isFullCube() && state.isOpaqueCube() && upState.getMaterial().isReplaceable() && !upState.getMaterial().isLiquid())
            {
                return takeBlock(world, newPos, rand);
            }
        }

        return false;
    }

    public static boolean takeBlock(World world, BlockPos pos, Random rand)
    {
        boolean tookChunk = false;

        for(int i = 0; i < 4; i++)
        {
            BlockPos newPos = pos.add(rand.nextInt(7) - 3, rand.nextInt(7) - 3, rand.nextInt(7) - 3);
            IBlockState state = world.getBlockState(newPos);
            Block block = state.getBlock();

            if(!(block instanceof BlockBlight) && !state.getMaterial().isReplaceable() && !state.getMaterial().isLiquid() && state.isFullBlock() && state.isFullCube() && state.isOpaqueCube())
            {
                if(BlockHelper.isOreDict("grass", block))
                {
                    world.setBlockState(newPos, NetherExBlocks.HYPHAE.getDefaultState());
                    tookChunk = takeChunk(world, newPos);
                }
                else if(BlockHelper.isOreDict("dirt", block))
                {
                    world.setBlockState(newPos, NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState());
                    tookChunk = takeChunk(world, newPos);
                }
                else if(BlockHelper.isOreDict("stone", block))
                {
                    world.setBlockState(newPos, NetherExBlocks.BASALT.getDefaultState());
                    tookChunk = takeChunk(world, newPos);
                }
                else if(block instanceof BlockOre || BlockHelper.oreDictNameContains(state, "ore"))
                {
                    world.setBlockState(newPos, NetherExBlocks.ARDITE_ORE.getDefaultState());
                    tookChunk = takeChunk(world, newPos);
                }
                else if(BlockHelper.isOreDict("logWood", block))
                {
                    world.setBlockState(newPos, Blocks.HARDENED_CLAY.getDefaultState());
                    tookChunk = takeChunk(world, newPos);
                }
                else if(BlockHelper.isOreDict("treeLeaves", block))
                {
                    world.setBlockState(newPos, Blocks.MAGMA.getDefaultState());
                    tookChunk = takeChunk(world, newPos);
                }
            }
        }

        return tookChunk;
    }

    public static boolean takeChunk(World world, BlockPos pos)
    {
        if(!world.isRemote)
        {
            if(world.provider.getDimension() == DimensionType.OVERWORLD.getId() && world.getBiome(pos) != NetherExBiomes.BLIGHTS_ASCENSION)
            {
                IBlightChunkData data = world.getCapability(CapabilityBlightChunkData.INSTANCE, null);

                if(data != null)
                {
                    Chunk chunk = world.getChunk(pos);
                    data.addChunk(new ChunkPos((chunk.x * 8), (chunk.z * 8)));

                    return true;
                }
            }
        }

        return false;
    }
}
