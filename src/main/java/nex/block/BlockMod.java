package nex.block;

import com.google.common.base.CaseFormat;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import nex.NetherEx;

public class BlockMod extends Block
{
    public BlockMod(Material material, String name)
    {
        super(material);
        this.setName(this, name);
        this.setCreativeTab(NetherEx.creativeTab);
    }

    private void setName(Block block, String name)
    {
        block.setRegistryName(NetherEx.MOD_ID + ":" + name);
        block.setUnlocalizedName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, block.getRegistryName().toString()));
    }
}
