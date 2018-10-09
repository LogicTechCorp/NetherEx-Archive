package nex.block;

import lex.block.BlockLibEx;
import net.minecraft.block.material.Material;
import nex.NetherEx;

public class BlockMutatedObsidian extends BlockLibEx
{
    public BlockMutatedObsidian(String name, float lightLevel)
    {
        super(NetherEx.instance, name, "pickaxe", 3, 50.0F, 2000.0F, Material.ROCK);
        this.setLightLevel(lightLevel);
    }
}
