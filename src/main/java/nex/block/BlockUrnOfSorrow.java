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

import lex.block.BlockTileEntity;
import lex.client.model.item.ItemModelHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import nex.NetherEx;
import nex.init.NetherExEffects;
import nex.tileentity.TileEntityUrnOfSorrow;

public class BlockUrnOfSorrow extends BlockTileEntity<TileEntityUrnOfSorrow>
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    private static final AxisAlignedBB URN_AABB = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.0D, 0.8125D);

    public BlockUrnOfSorrow()
    {
        super(NetherEx.instance, "urn_of_sorrow", Material.ROCK, TileEntityUrnOfSorrow.class);
        this.setHardness(0.5F);
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
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(EnumType type : EnumType.values())
        {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntityUrnOfSorrow urn = (TileEntityUrnOfSorrow) world.getTileEntity(pos);

        if(urn == null)
        {
            return false;
        }

        ItemStackHandler inventory = urn.getInventory();
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
            if(!inventoryStack.isEmpty() && urn.getSummoningTime() == 0)
            {
                if(!world.isRemote)
                {
                    urn.spawnItemStack(world, pos.up(), inventory.getStackInSlot(0));
                }
                inventory.setStackInSlot(0, ItemStack.EMPTY);
            }
        }

        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
        TileEntityUrnOfSorrow urn = (TileEntityUrnOfSorrow) world.getTileEntity(pos);

        if(urn == null)
        {
            return 0.5F;
        }

        return urn.canBreak() ? 0.5F : -1.0F;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(TYPE, EnumType.fromMeta(meta));
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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel()
    {
        for(BlockUrnOfSorrow.EnumType type : BlockUrnOfSorrow.EnumType.values())
        {
            ItemModelHandler.registerBlockModel(this, type.ordinal(), this.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }
    }

    public enum EnumType implements IStringSerializable
    {
        EMPTY,
        FULL;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase();
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
