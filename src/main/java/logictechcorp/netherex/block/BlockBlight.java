package logictechcorp.netherex.block;

import logictechcorp.libraryex.block.BlockFiniteFluidLibEx;
import logictechcorp.libraryex.util.BlockHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.init.NetherExFluids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.property.IExtendedBlockState;

import java.util.Random;

public class BlockBlight extends BlockFiniteFluidLibEx
{
    public BlockBlight()
    {
        super(NetherEx.instance, "blight", NetherExFluids.BLIGHT, Material.WATER);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!world.isRemote)
        {
            this.spread(world, pos, rand);
            this.takeBlock(world, pos, rand);
        }
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        this.takeBiome(world, pos);
        super.onBlockAdded(world, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        this.takeBiome(world, pos);
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public int getMaxRenderHeightMeta()
    {
        return 0;
    }

    @Override
    public float getFluidHeightForRender(IBlockAccess world, BlockPos pos, IBlockState state)
    {
        return 0.0625F;
    }

    @Override
    public float getFilledPercentage(IBlockAccess world, BlockPos pos)
    {
        return 0.0625F;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    public IBlockState getExtendedState(IBlockState oldState, IBlockAccess world, BlockPos pos)
    {
        IExtendedBlockState state = (IExtendedBlockState) oldState;

        for(int i = 0; i < 4; i++)
        {
            EnumFacing side = EnumFacing.byHorizontalIndex(i);
            BlockPos offset = pos.offset(side);
            boolean useOverlay = world.getBlockState(offset).getBlockFaceShape(world, offset, side.getOpposite()) == BlockFaceShape.SOLID;
            state = state.withProperty(SIDE_OVERLAYS[i], useOverlay);
        }

        state = state.withProperty(LEVEL_CORNERS[0], this.getFluidHeightForRender(world, pos, state));
        state = state.withProperty(LEVEL_CORNERS[1], this.getFluidHeightForRender(world, pos, state));
        state = state.withProperty(LEVEL_CORNERS[2], this.getFluidHeightForRender(world, pos, state));
        state = state.withProperty(LEVEL_CORNERS[3], this.getFluidHeightForRender(world, pos, state));
        return state;

    }

    private void spread(World world, BlockPos pos, Random rand)
    {
        BlockPos posTest = pos.add(rand.nextInt(3) - 1, rand.nextInt(3) - 1, rand.nextInt(3) - 1);

        if(world.getLight(posTest, false) <= 7)
        {

        }
    }

    private void takeBlock(World world, BlockPos pos, Random rand)
    {
        BlockPos posTest = pos.add(rand.nextInt(7) - 3, rand.nextInt(7) - 3, rand.nextInt(7) - 3);
        IBlockState stateTest = world.getBlockState(posTest);
        Block blockTest = stateTest.getBlock();

        if(!(blockTest instanceof ITakenBlock) && blockTest.isNormalCube(stateTest, world, posTest))
        {
            if(BlockHelper.isOreDict("grass", blockTest))
            {
                this.takeBiome(world, pos);
            }
            else if(BlockHelper.isOreDict("dirt", blockTest))
            {
                this.takeBiome(world, pos);
            }
            else if(BlockHelper.isOreDict("stone", blockTest))
            {
                this.takeBiome(world, pos);
            }
            else if(blockTest instanceof BlockOre)
            {
                world.setBlockState(posTest, NetherExBlocks.ARDITE_ORE.getDefaultState());
                this.takeBiome(world, pos);
            }
            else if(BlockHelper.isOreDict("gravel", blockTest))
            {
                this.takeBiome(world, pos);
            }
            else if(BlockHelper.isOreDict("sand", blockTest))
            {
                this.takeBiome(world, pos);
            }
            else if(BlockHelper.isOreDict("sandstone", blockTest))
            {
                this.takeBiome(world, pos);
            }
            else if(BlockHelper.isOreDict("logWood", blockTest))
            {
                this.takeBiome(world, pos);
            }
            else if(BlockHelper.isOreDict("treeLeaves", blockTest))
            {
                this.takeBiome(world, pos);
            }
            else if(BlockHelper.isOreDict("vine", blockTest))
            {
                this.takeBiome(world, pos);
            }
        }
    }

    private void takeBiome(World world, BlockPos pos)
    {
        if(world.getBiome(pos) != NetherExBiomes.BLIGHTS_ASCENSION)
        {
            int localChunkX = pos.getX() & 15;
            int localChunkZ = pos.getZ() & 15;
            Chunk chunk = world.getChunk(pos);
            byte[] biomeArray = chunk.getBiomeArray();
            biomeArray[localChunkZ << 4 | localChunkX] = (byte) Biome.getIdForBiome(NetherExBiomes.BLIGHTS_ASCENSION);
            chunk.setBiomeArray(biomeArray);
            chunk.markDirty();
        }
    }
}
