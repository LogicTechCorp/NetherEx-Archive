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

package nex.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

public class BlockHelper
{
    public static boolean mine3x3(World world, ItemStack stack, BlockPos pos, EntityPlayer player)
    {
        RayTraceResult traceResult = WorldHelper.rayTraceFromEntity(world, player, false, 4.5D);

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

        boolean canHarvestBedrock = false;

        if(originalState.getBlock() == Blocks.BEDROCK)
        {
            if(player.dimension == DimensionType.NETHER.getId() && pos.getY() >= 120)
            {
                canHarvestBedrock = true;
            }
        }

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

            if(originalState.getMaterial() == testState.getMaterial() && testStrength > 0.0F && (originalStrength / testStrength) <= 10.0F || canHarvestBedrock)
            {
                if(canBeHarvested && stack.canHarvestBlock(originalState) || canBeHarvested)
                {
                    BlockHelper.tryToHarvest(world, testState, testPos, player, sideHit);
                }
            }
        }

        if(canHarvestBedrock)
        {
            BlockHelper.tryToHarvest(world, originalState, pos, player, sideHit);
        }

        return false;
    }

    /**
     * A method that harvests blocks when they aren't able to normally
     * <p>
     * Written by VapourDrive here:
     * https://github.com/VapourDrive/Hammerz/blob/55d31b8f8fd463d127110de04b2562605604e85c/src/main/java/vapourdrive/hammerz/utils/BlockUtils.java#L21
     *
     * @author VapourDrive
     */
    public static boolean tryToHarvest(World world, IBlockState state, BlockPos pos, EntityPlayer player, EnumFacing side)
    {
        Block block = state.getBlock();

        if(world.isAirBlock(pos))
        {
            return false;
        }

        EntityPlayerMP playerMP = null;

        if(player instanceof EntityPlayerMP)
        {
            playerMP = (EntityPlayerMP) player;
        }

        ItemStack stack = player.getHeldItemMainhand();

        if(stack == null || stack.getItem() == null)
        {
            return false;
        }
        if(!(stack.getItem().getToolClasses(stack).contains(block.getHarvestTool(state)) || stack.getItem().getDestroySpeed(stack, state) > 1.0F))
        {
            return false;
        }
        if(!ForgeHooks.canHarvestBlock(block, player, world, pos))
        {

            return false;
        }

        int event = 0;

        if(playerMP != null)
        {
            event = ForgeHooks.onBlockBreakEvent(world, world.getWorldInfo().getGameType(), playerMP, pos);

            if(event == -1)
            {
                return false;
            }
        }

        world.playEvent(playerMP, 2001, pos, Block.getStateId(state));


        if(player.capabilities.isCreativeMode)
        {
            if(!world.isRemote)
            {
                block.onBlockHarvested(world, pos, state, player);
            }
            if(block.removedByPlayer(state, world, pos, player, false))
            {
                block.onBlockDestroyedByPlayer(world, pos, state);
            }
            if(!world.isRemote)
            {
                playerMP.connection.sendPacket(new SPacketBlockChange(world, pos));
            }
            else
            {
                Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
            }
            return true;
        }
        if(!world.isRemote)
        {
            block.onBlockHarvested(world, pos, state, player);

            if(block.removedByPlayer(state, world, pos, player, true))
            {
                block.onBlockDestroyedByPlayer(world, pos, state);
                block.harvestBlock(world, player, pos, state, null, stack);
                block.dropXpOnBlockBreak(world, pos, event);
            }

            playerMP.connection.sendPacket(new SPacketBlockChange(world, pos));
        }
        else
        {
            if(block.removedByPlayer(state, world, pos, player, true))
            {
                block.onBlockDestroyedByPlayer(world, pos, state);
            }

            Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
        }
        return true;
    }


    public static boolean isOreDict(String id, Block block)
    {
        for(ItemStack stack : OreDictionary.getOres(id))
        {
            if(stack.getItem() instanceof ItemBlock)
            {
                if(((ItemBlock) stack.getItem()).getBlock() == block)
                {
                    return true;
                }
            }
        }

        return false;
    }
}
