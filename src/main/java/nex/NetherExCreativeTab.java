package nex;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetherExCreativeTab extends CreativeTabs
{
    public NetherExCreativeTab()
    {
        super(NetherEx.MOD_ID);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
        return Item.getItemFromBlock(Blocks.NETHERRACK);
    }
}
