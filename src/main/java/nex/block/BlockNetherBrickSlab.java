/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex.block;

import com.google.common.base.CaseFormat;
import lex.block.BlockSlabLibEx;
import lex.client.model.item.ItemModelHandler;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.init.NetherExBlocks;

import java.util.Random;

public class BlockNetherBrickSlab extends BlockSlabLibEx
{
    public static final PropertyEnum<BlockNetherrack.EnumType> TYPE = PropertyEnum.create("type", BlockNetherrack.EnumType.class);

    public BlockNetherBrickSlab()
    {
        super(NetherEx.instance, "nether_brick_slab", Material.ROCK);
        this.setHardness(2.0F);
        this.setResistance(10F);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public String getTranslationKey(int meta)
    {
        return super.getTranslationKey() + "." + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, BlockNetherrack.EnumType.fromMeta(meta).getName());
    }

    @Override
    public boolean isDouble()
    {
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NetherExBlocks.NETHER_BRICK_SLAB);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockNetherrack.EnumType.fromMeta(stack.getMetadata() & 7);
    }

    @Override
    public IProperty<?> getVariantProperty()
    {
        return TYPE;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.getDefaultState().withProperty(TYPE, BlockNetherrack.EnumType.fromMeta(meta & 7));

        if(!this.isDouble())
        {
            state = state.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(TYPE).ordinal();

        if(!this.isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP)
        {
            meta |= 8;
        }

        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel()
    {
        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            ItemModelHandler.registerBlockModel(this, type.ordinal(), this.getRegistryName().toString(), String.format("half=bottom,type=%s", type.getName()));
        }
    }

    public static class Double extends BlockNetherBrickSlab
    {
        @Override
        public boolean isDouble()
        {
            return true;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void registerModel()
        {
            ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockSlab.HALF).build());

            for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
            {
                ItemModelHandler.registerBlockModel(this, type.ordinal(), this.getRegistryName().toString(), String.format("type=%s", type.getName()));
            }
        }
    }
}
