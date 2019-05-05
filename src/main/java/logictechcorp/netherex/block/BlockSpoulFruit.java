package logictechcorp.netherex.block;

import logictechcorp.libraryex.block.BlockMod;
import logictechcorp.libraryex.block.builder.BlockProperties;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSpoulFruit extends BlockMod
{
    public static final PropertyBool HANGING = PropertyBool.create("hanging");
    protected static final AxisAlignedBB HANGING_AABB = new AxisAlignedBB(0.3125D, 0.0625D, 0.3125D, 0.6875D, 1.0D, 0.6875D);
    protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.6875D, 0.6875D);

    public BlockSpoulFruit()
    {
        super(NetherEx.getResource("spoul_fruit"), new BlockProperties(Material.PLANTS, MapColor.SNOW).lightLevel(1.0F).hardness(3.5F).resistance(3.5F));
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(HANGING, true));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.getValue(HANGING) ? HANGING_AABB : STANDING_AABB;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        BlockPos posUp = pos.up();
        boolean hanging = false;

        if(world.getBlockState(posUp).isSideSolid(world, posUp, EnumFacing.DOWN))
        {
            hanging = true;
        }

        world.setBlockState(pos, state.withProperty(HANGING, hanging));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        BlockPos offsetPos = pos.offset(facing.getOpposite());
        boolean hanging = false;

        if(facing == EnumFacing.DOWN && world.getBlockState(offsetPos).isSideSolid(world, offsetPos, EnumFacing.DOWN))
        {
            hanging = true;
        }

        return this.getDefaultState().withProperty(HANGING, hanging);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(HANGING, meta != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(HANGING) ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, HANGING);
    }
}
