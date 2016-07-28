package nex.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.enums.EnumNetherrackType;

public class BlockModNetherrack extends BlockVariantContainer
{
    public BlockModNetherrack()
    {
        super("netherrack", Material.ROCK, EnumNetherrackType.class);

        setHardness(0.4F);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP;
    }
}
