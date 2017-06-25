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

package nex.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.oredict.OreDictionary;
import nex.world.biome.NetherBiome;

public class BlockUtil
{
    private static final NetHandlerPlayClient HANDLER = (NetHandlerPlayClient) FMLClientHandler.instance().getClientPlayHandler();

    public static IBlockState getBlock(NetherBiome.BiomeBlock block, String fallback)
    {
        return Block.getBlockFromName(block.getId() == null || block.getId().isEmpty() || Block.getBlockFromName(block.getId()) == null ? fallback : block.getId()).getStateFromMeta(block.getMeta());
    }

    public static Block getBlock(String blockId, String fallback)
    {
        return Block.getBlockFromName(blockId == null || blockId.isEmpty() || Block.getBlockFromName(blockId) == null ? fallback : blockId);
    }

    /**
     * A method that harvests blocks when they aren't able to normally
     * <p>
     * Written by VapourDrive here:
     * https://github.com/VapourDrive/Hammerz/blob/55d31b8f8fd463d127110de04b2562605604e85c/src/main/java/vapourdrive/hammerz/utils/BlockUtils.java#L21
     *
     * @author VapourDrive
     */
    public static void tryToHarvest(World world, IBlockState state, BlockPos pos, EntityPlayer player, EnumFacing sideHit)
    {
        Block block = state.getBlock();

        if(block.isAir(state, world, pos))
        {
            return;
        }

        EntityPlayerMP playerMP = null;

        if(player instanceof EntityPlayerMP)
        {
            playerMP = (EntityPlayerMP) player;
        }

        ItemStack stack = player.getHeldItemMainhand();

        if(stack == null || stack.getItem() == null)
        {
            return;
        }

        if(!(stack.getItem().getToolClasses(stack).contains(block.getHarvestTool(state)) || stack.getItem().getStrVsBlock(stack, state) > 1.0f))
        {
            return;
        }

        if(!ForgeHooks.canHarvestBlock(block, player, world, pos))
        {
            return;
        }

        int event = 0;

        if(playerMP != null)
        {
            event = ForgeHooks.onBlockBreakEvent(world, world.getWorldInfo().getGameType(), playerMP, pos);

            if(event == -1)
            {
                return;
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
                if(playerMP != null)
                {
                    playerMP.connection.sendPacket(new SPacketBlockChange(world, pos));
                }
            }
            else
            {
                if(HANDLER != null)
                {
                    HANDLER.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, sideHit));
                }
            }

            return;
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

            if(playerMP != null)
            {
                playerMP.connection.sendPacket(new SPacketBlockChange(world, pos));
            }
        }
        else
        {
            if(block.removedByPlayer(state, world, pos, player, true))
            {
                block.onBlockDestroyedByPlayer(world, pos, state);
            }

            if(HANDLER != null)
            {
                HANDLER.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, sideHit));
            }
        }
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
