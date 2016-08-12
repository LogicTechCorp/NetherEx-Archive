package nex.item;

import com.google.common.base.CaseFormat;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import nex.NetherEx;
import nex.block.IVariantContainer;
import nex.registry.ItemRegistry;

import java.util.List;

/**
 * This is a base implementation for itemblocks that use an enum to add variants
 * <p>
 * Inspired by Vazkii:
 * https://github.com/Vazkii/Psi/blob/master/src/main/java/vazkii/psi/common/item/base/ItemModBlock.java
 */
public class ItemBlockVariantContainer extends ItemBlock implements IVariantContainer
{
    private IVariantContainer container;

    public ItemBlockVariantContainer(Block block)
    {
        super(block);

        container = (IVariantContainer) block;

        if(getVariants().length > 1)
        {
            setHasSubtypes(true);
        }

        ItemRegistry.variantContainers.add(this);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for(int i = 0; i < getVariants().length; i++)
        {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if(getVariants().length == 1)
        {
            return "tile." + NetherEx.MOD_ID + ":" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().getResourcePath());
        }

        return "tile." + NetherEx.MOD_ID + ":" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().getResourcePath()) + "." + getVariants()[stack.getItemDamage()];
    }

    @Override
    public String[] getVariants()
    {
        return container.getVariants();
    }
}
