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

package logictechcorp.netherex.handler;

import logictechcorp.libraryex.item.ItemModHammer;
import logictechcorp.libraryex.utility.CollectionHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.entity.monster.EntityNethermite;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class BlockHandler
{
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.PlaceEvent event)
    {
        BlockPos pos = event.getPos();
        IBlockState state = event.getState();
        EntityPlayer player = event.getPlayer();
        EnumHand hand = event.getHand();

        if(hand != null)
        {
            NBTTagCompound compound = player.getHeldItem(hand).getTagCompound();

            if(state.getBlock() == Blocks.BEDROCK && compound != null && compound.getBoolean("AboveNether"))
            {
                if(player.dimension != DimensionType.NETHER.getId() || pos.getY() < 120)
                {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getState();

        if(!(event.getPlayer() instanceof FakePlayer))
        {
            EntityPlayer player = event.getPlayer();

            if(state.getBlock() == Blocks.MAGMA)
            {
                if(NetherExConfig.block.magma.turnIntoLavaWhenBroken)
                {
                    if(EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand()) == 0)
                    {
                        world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 3);
                        player.getHeldItemMainhand().damageItem(1, player);
                        event.setCanceled(true);
                    }
                }
            }
            if(player.dimension == DimensionType.NETHER.getId())
            {
                boolean canSpawnNethermites = CollectionHelper.contains(NetherExConfig.entity.nethermite.blockWhitelist, state.getBlock().getRegistryName().toString());

                if(NetherExConfig.entity.nethermite.spawnRarity > 0 && canSpawnNethermites && world.rand.nextInt(NetherExConfig.entity.nethermite.spawnRarity) == 0)
                {
                    EntityNethermite nethermite = new EntityNethermite(world);
                    nethermite.setPosition((double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D);
                    world.spawnEntity(nethermite);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onHarvestDrops(BlockEvent.HarvestDropsEvent event)
    {
        IBlockState state = event.getState();
        EntityPlayer player = event.getHarvester();

        if(player != null)
        {
            if(state.getBlock() == Blocks.BEDROCK)
            {
                if(player.getHeldItemMainhand().getItem() instanceof ItemModHammer)
                {
                    ItemStack stack = new ItemStack(Blocks.BEDROCK, 1, 0);
                    stack.setTagInfo("AboveNether", new NBTTagByte((byte) 1));
                    event.getDrops().add(stack);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCreateFluidSource(BlockEvent.CreateFluidSourceEvent event)
    {
        World world = event.getWorld();
        IBlockState state = event.getState();

        if(world.provider.getDimension() == DimensionType.NETHER.getId())
        {
            if(state.getBlock() == Blocks.LAVA || state.getBlock() == Blocks.FLOWING_LAVA)
            {
                event.setResult(NetherExConfig.dimension.nether.isLavaInfinite ? Event.Result.ALLOW : Event.Result.DEFAULT);
            }
        }
    }
}
