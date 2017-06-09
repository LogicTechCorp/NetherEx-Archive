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

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import nex.init.NetherExMaterials;
import nex.util.BlockUtil;
import nex.util.NBTUtil;
import nex.util.WorldUtil;

public class ItemBoneHammer extends ItemNetherExPickaxe
{
    public ItemBoneHammer()
    {
        super("tool_hammer_bone", NetherExMaterials.TOOL_BONE_WITHERED_GOLD);
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


    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player)
    {
        World world = player.getEntityWorld();

        RayTraceResult traceResult = WorldUtil.raytraceFromEntity(world, player, false, 4.5D);

        if(traceResult == null)
        {
            return true;
        }

        EnumFacing sideHit = traceResult.sideHit;

        BlockPos startPos;
        BlockPos endPos;

        if(sideHit.getAxis() == EnumFacing.Axis.X)
        {
            startPos = new BlockPos(0, 1, 1);
            endPos = new BlockPos(0, -1, -1);
        }
        else if(sideHit.getAxis() == EnumFacing.Axis.Y)
        {
            startPos = new BlockPos(1, 0, 1);
            endPos = new BlockPos(-1, 0, -1);
        }
        else
        {
            startPos = new BlockPos(1, 1, 0);
            endPos = new BlockPos(-1, -1, 0);
        }

        Iterable<BlockPos> posIter = BlockPos.getAllInBox(startPos, endPos);
        IBlockState originalState = world.getBlockState(pos);
        float originalStrength = ForgeHooks.blockStrength(originalState, player, world, pos);

        for(BlockPos testPos : posIter)
        {
            testPos = testPos.add(pos);

            if(testPos.equals(pos))
            {
                continue;
            }

            IBlockState testState = world.getBlockState(testPos);
            float testStrength = ForgeHooks.blockStrength(testState, player, world, testPos);
            boolean canBeHarvested = ForgeHooks.canHarvestBlock(testState.getBlock(), player, world, testPos);

            if(originalState.getMaterial() == testState.getMaterial() && testStrength > 0.0F && (originalStrength / testStrength) <= 10.0F)
            {
                if(canBeHarvested && stack.canHarvestBlock(originalState))
                {
                    BlockUtil.tryToHarvest(world, testState, testPos, player, sideHit);
                }
            }
        }

        return false;
    }
}
