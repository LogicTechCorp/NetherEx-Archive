package logictechcorp.netherex.item;

import logictechcorp.libraryex.item.ItemShovelLibEx;
import logictechcorp.libraryex.util.NBTHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExMaterials;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ItemAmedianShovel extends ItemShovelLibEx
{
    public ItemAmedianShovel()
    {
        super(NetherEx.instance, "amedian_shovel", NetherExMaterials.AMEDIAN);
        this.addPropertyOverride(new ResourceLocation("type"), (stack, world, entity) -> this.getType(stack));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if(this.isInCreativeTab(tab))
        {
            list.add(new ItemStack(this));

            ItemStack blazedStack = new ItemStack(this);
            NBTTagCompound blazedCompound = new NBTTagCompound();
            blazedCompound.setFloat("Type", 0.5F);
            NBTHelper.setTagCompound(blazedStack, blazedCompound);
            list.add(blazedStack);

            ItemStack frostedStack = new ItemStack(this);
            NBTTagCompound frostedCompound = new NBTTagCompound();
            frostedCompound.setFloat("Type", 1.0F);
            NBTHelper.setTagCompound(frostedStack, frostedCompound);
            list.add(frostedStack);
        }
    }

    private float getType(ItemStack stack)
    {
        NBTTagCompound compound = NBTHelper.setTagCompound(stack);
        return compound.hasKey("Type") ? compound.getFloat("Type") : 0.0F;
    }
}
