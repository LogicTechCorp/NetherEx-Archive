package nex.item;

import com.google.common.base.CaseFormat;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import nex.block.BlockGem;

public class ItemGem extends ItemNetherEx
{
    public ItemGem()
    {
        super("gem");
        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if(isInCreativeTab(tab))
        {
            for(BlockGem.EnumType type : BlockGem.EnumType.values())
            {
                list.add(new ItemStack(this, 1, type.ordinal()));
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, BlockGem.EnumType.fromMeta(stack.getItemDamage()).getName());
    }
}
