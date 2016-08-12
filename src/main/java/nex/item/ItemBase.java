package nex.item;

import com.google.common.base.CaseFormat;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.block.IVariantContainer;
import nex.registry.ItemRegistry;

import java.util.List;

/**
 * This is a base implementation for items
 * <p>
 * Inspired by Vazkii:
 * https://github.com/Vazkii/Psi/blob/master/src/main/java/vazkii/psi/common/item/base/ItemMod.java
 */
public abstract class ItemBase extends Item implements IVariantContainer
{
    private final String[] VARIANTS;

    public ItemBase(String name, String... variants)
    {
        if(variants.length > 1)
        {
            setHasSubtypes(true);
        }

        if(variants.length == 0)
        {
            variants = new String[]{name};
        }

        VARIANTS = variants;

        setCreativeTab(NetherEx.CREATIVE_TAB);
        registerAndSetName(name);

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
    public String getUnlocalizedName(ItemStack stack)
    {
        return "item." + NetherEx.MOD_ID + ":" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().getResourcePath()) + "." + VARIANTS[stack.getItemDamage()];
    }

    @Override
    public String[] getVariants()
    {
        return VARIANTS;
    }

    private void registerAndSetName(String name)
    {
        setRegistryName(NetherEx.MOD_ID + ":" + name);
        setUnlocalizedName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().toString()));
        GameRegistry.register(this);
    }
}
