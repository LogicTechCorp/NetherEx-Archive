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

import logictechcorp.libraryex.block.BlockMod;
import logictechcorp.libraryex.block.HarvestLevel;
import logictechcorp.libraryex.block.HarvestTool;
import logictechcorp.libraryex.block.property.BlockProperties;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockWornIron extends BlockMod
{
    public BlockWornIron()
    {
        super(NetherEx.getResource("worn_iron"), new BlockProperties(Material.IRON, MapColor.GRAY).sound(SoundType.METAL).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(5.0F).resistance(10.0F));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune)
    {
        return Items.IRON_NUGGET;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return random.nextInt(4) + 1;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return true;
    }
}
