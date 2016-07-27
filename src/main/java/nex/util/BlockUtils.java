package nex.util;

import com.google.common.base.CaseFormat;
import net.minecraft.block.Block;
import nex.NetherEx;

public class BlockUtils
{
    public static void setBlockName(Block block, String name)
    {
        block.setRegistryName(NetherEx.MOD_ID + ":" + name);
        block.setUnlocalizedName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, block.getRegistryName().toString()));
    }
}
