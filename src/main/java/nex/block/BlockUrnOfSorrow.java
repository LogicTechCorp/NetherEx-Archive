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

package nex.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import nex.init.NetherExEffectTypes;
import nex.init.NetherExEffects;
import nex.tileentity.TileEntitySummoningAltar;

import java.util.List;

public class BlockUrnOfSorrow extends BlockTileEntity<TileEntitySummoningAltar>
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    private static final AxisAlignedBB URN_AABB = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.0D, 0.8125D);

    public BlockUrnOfSorrow()
    {
        super("tile_urn_sorrow", Material.ROCK, TileEntitySummoningAltar.class);

        setHardness(0.5F);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return URN_AABB;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(EnumType type : EnumType.values())
        {
            list.add(new ItemStack(item, 1, type.ordinal()));
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntitySummoningAltar altar = (TileEntitySummoningAltar) world.getTileEntity(pos);

        if(altar == null)
        {
            return false;
        }

        ItemStackHandler inventory = altar.getInventory();
        ItemStack inventoryStack = inventory.getStackInSlot(0);
        ItemStack heldStack = player.getHeldItemMainhand();

        if(!player.isSneaking())
        {
            if(heldStack.getItem() == Items.POTIONITEM && state.getValue(TYPE) == EnumType.EMPTY)
            {
                for(PotionEffect effect : PotionUtils.getEffectsFromStack(heldStack))
                {
                    if(effect.getPotion() == NetherExEffects.LOST)
                    {
                        player.getHeldItemMainhand().shrink(1);
                        world.setBlockState(pos, state.withProperty(TYPE, EnumType.FULL));
                    }
                }
            }
            else
            {
                if(inventoryStack.isEmpty() && !player.getHeldItemMainhand().isEmpty())
                {
                    inventory.setStackInSlot(0, player.getHeldItemMainhand().splitStack(1));
                }
            }
        }
        else
        {
            if(!inventoryStack.isEmpty() && altar.getSummoningTime() == 0)
            {
                if(!world.isRemote)
                {
                    altar.spawnItemStack(world, pos.up(), inventory.getStackInSlot(0));
                }
                inventory.setStackInSlot(0, ItemStack.EMPTY);
            }
        }

        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
        TileEntitySummoningAltar altar = (TileEntitySummoningAltar) world.getTileEntity(pos);

        if(altar == null)
        {
            return 0.5F;
        }

        return altar.canBreak() ? 0.5F : -1.0F;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(TYPE, EnumType.fromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE);
    }

    public enum EnumType implements IStringSerializable
    {
        EMPTY,
        FULL;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }

        public static EnumType fromMeta(int meta)
        {
            if(meta < 0 || meta >= values().length)
            {
                meta = 0;
            }

            return values()[meta];
        }
    }
}
