package nex.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class BlockModNetherrack extends BlockMod
{
    private static final IProperty<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);

    public BlockModNetherrack()
    {
        super(Material.ROCK, "netherrack");
        this.setHardness(0.4F);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(VARIANT).getMeta();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(EnumType enumType : EnumType.values())
        {
            list.add(new ItemStack(this, 1, enumType.getMeta()));
        }
    }

    public enum EnumType implements IStringSerializable
    {
        CHILLED(0, "chilled");

        private static final EnumType[] META_LOOKUP = Stream.of(values()).sorted(Comparator.comparing(EnumType::getMeta)).toArray(EnumType[]::new);

        private final int meta;
        private final String name;

        EnumType(int meta, String name)
        {
            this.meta = meta;
            this.name = name;
        }

        public static EnumType byMetadata(int meta)
        {
            if(meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        public static String[] getNames()
        {
            return Stream.of(META_LOOKUP).map(EnumType::getName).toArray(String[]::new);
        }

        public int getMeta()
        {
            return meta;
        }

        @Override
        public String getName()
        {
            return name;
        }
    }
}
