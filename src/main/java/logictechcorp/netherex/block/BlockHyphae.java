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
import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockHyphae extends BlockMod
{
    public BlockHyphae()
    {
        super(NetherEx.getResource("hyphae"), new BlockProperties(Material.ROCK, MapColor.GRAY).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(0.5F).resistance(2.0F));

        if(NetherExConfig.block.hyphae.shouldSpread)
        {
            this.setTickRandomly(true);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random random)
    {
        if(random.nextInt(10) == 0)
        {
            world.spawnParticle(EnumParticleTypes.TOWN_AURA, ((float) pos.getX() + random.nextFloat()), ((float) pos.getY() + 1.1F), ((float) pos.getZ() + random.nextFloat()), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        if(!world.isRemote && NetherExConfig.block.hyphae.shouldSpread)
        {
            if(world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getLightOpacity(world, pos.up()) > 2)
            {
                world.setBlockState(pos, NetherExBlocks.LIVELY_NETHERRACK.getDefaultState());
            }
            else
            {
                if(world.getLightFromNeighbors(pos.up()) >= 9)
                {
                    for(int i = 0; i < 4; i++)
                    {
                        BlockPos newPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                        IBlockState stateNew = world.getBlockState(newPos);
                        IBlockState stateUp = world.getBlockState(newPos.up());

                        if(stateNew.getBlock() == NetherExBlocks.LIVELY_NETHERRACK && world.getLightFromNeighbors(newPos.up()) >= 4 && stateUp.getLightOpacity(world, newPos.up()) <= 2)
                        {
                            world.setBlockState(newPos, this.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
    {

        for(EnumHand hand : EnumHand.values())
        {
            if(player.getHeldItem(hand).getItem() instanceof ItemSpade)
            {
                ItemStack stack = player.getHeldItem(hand);
                player.swingArm(hand);
                world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, NetherExBlocks.LIVELY_NETHERRACK_PATH.getDefaultState(), 11);
                stack.damageItem(1, player);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune)
    {
        return Item.getItemFromBlock(NetherExBlocks.LIVELY_NETHERRACK);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing, IPlantable plantable)
    {
        return plantable.getPlantType(world, pos.offset(facing)) == EnumPlantType.Nether;
    }
}
