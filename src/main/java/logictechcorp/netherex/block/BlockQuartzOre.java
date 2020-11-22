/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.block;

import logictechcorp.libraryex.block.BlockDynamic;
import logictechcorp.libraryex.block.HarvestLevel;
import logictechcorp.libraryex.block.HarvestTool;
import logictechcorp.libraryex.block.property.BlockProperties;
import logictechcorp.libraryex.utility.RandomHelper;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockQuartzOre extends BlockDynamicNetherBiome
{
    public BlockQuartzOre()
    {
        super(NetherEx.getResource("quartz_ore"), BlockDynamic.TexturePlacement.UNDER, new BlockProperties(Material.ROCK, MapColor.NETHERRACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.IRON).hardness(2.0F).resistance(5.0F));
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if(fortune > 0)
        {
            int i = random.nextInt(fortune + 2) - 1;

            if(i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.QUARTZ;
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Blocks.QUARTZ_ORE);
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        Random random = world instanceof World ? ((World) world).rand : RandomHelper.getRandom();
        return MathHelper.getInt(random, 2, 5);
    }
}
