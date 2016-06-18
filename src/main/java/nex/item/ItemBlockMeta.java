package nex.item;

import com.google.common.base.CaseFormat;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemBlockMeta extends ItemMultiTexture
{
    public ItemBlockMeta(Block block, String[] names, Boolean dummy)
    {
        super(block, block, names);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.nameFunction.apply(stack));
    }
}
