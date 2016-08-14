package nex.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockModNetherrack extends BlockVariantContainer
{
    public BlockModNetherrack()
    {
        super("netherrack", Material.ROCK, SoundType.STONE, EnumType.class);

        setHardness(0.4F);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP;
    }

    public enum EnumType implements IStringSerializable
    {
        BIOTIC,
        BURNT,
        FROZEN;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }
    }
}
