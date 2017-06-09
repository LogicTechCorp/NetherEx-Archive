/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import nex.init.NetherExBlocks;
import nex.init.NetherExMaterials;
import nex.util.NBTUtil;

@SuppressWarnings("ConstantConditions")
public class ItemBoneHoe extends ItemNetherExHoe
{
    public ItemBoneHoe()
    {
        super("tool_hoe_bone", NetherExMaterials.TOOL_BONE_WITHERED_GOLD);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        NBTTagCompound compound = new NBTTagCompound();

        if(entity.dimension == -1)
        {
            compound.setBoolean("Nether", true);
        }
        else
        {
            compound.setBoolean("Nether", false);
        }

        NBTUtil.setTag(stack, compound);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(!player.canPlayerEdit(pos.offset(facing), facing, stack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            int hook = ForgeEventFactory.onHoeUse(stack, player, world, pos);

            if(hook != 0)
            {
                return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
            }

            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if(facing != EnumFacing.DOWN && world.isAirBlock(pos.up()))
            {
                if(block == Blocks.GRASS || block == Blocks.GRASS_PATH)
                {
                    setBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
                    return EnumActionResult.SUCCESS;
                }

                if(block == Blocks.DIRT)
                {
                    switch(state.getValue(BlockDirt.VARIANT))
                    {
                        case DIRT:
                            setBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
                            return EnumActionResult.SUCCESS;
                        case COARSE_DIRT:
                            setBlock(stack, player, world, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                            return EnumActionResult.SUCCESS;
                    }
                }

                if(block == Blocks.SOUL_SAND)
                {
                    setBlock(stack, player, world, pos, NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState());
                    return EnumActionResult.SUCCESS;
                }
            }

            return EnumActionResult.PASS;
        }
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState state)
    {
        if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("Nether"))
        {
            if(stack.getTagCompound().getBoolean("Nether"))
            {
                return ToolMaterial.IRON.getHarvestLevel();
            }
        }

        return ToolMaterial.GOLD.getHarvestLevel();
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("Nether"))
        {
            if(!stack.getTagCompound().getBoolean("Nether"))
            {
                damage += 15;

                if(getDamage(stack) - 16 == 0)
                {
                    damage += 1;
                }
            }
        }

        super.setDamage(stack, damage);
    }
}
