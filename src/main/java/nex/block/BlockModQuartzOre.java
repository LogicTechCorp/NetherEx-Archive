package nex.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import nex.enums.EnumNetherrackType;

import java.util.Random;

public class BlockModQuartzOre extends BlockVariantContainer
{
    public BlockModQuartzOre()
    {
        super("quartz_ore", Material.ROCK, EnumNetherrackType.class);

        setHardness(3.0F);
        setResistance(5.0F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.QUARTZ;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if(fortune > 0)
        {
            int i = random.nextInt(fortune + 2) - 1;

            if(i < 0)
            {
                i = 0;
            }

            return quantityDropped(random) * (i + 1);
        }
        else
        {
            return quantityDropped(random);
        }
    }

    @Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        return MathHelper.getRandomIntegerInRange(rand, 2, 5);
    }
}
