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

import logictechcorp.libraryex.block.BlockTileEntity;
import logictechcorp.libraryex.block.builder.BlockProperties;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExMobEffects;
import logictechcorp.netherex.tileentity.TileEntityUrnOfSorrow;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class BlockUrnOfSorrow extends BlockTileEntity<TileEntityUrnOfSorrow>
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    private static final AxisAlignedBB URN_AABB = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.0D, 0.8125D);

    public BlockUrnOfSorrow()
    {
        super(NetherEx.getResource("urn_of_sorrow"), TileEntityUrnOfSorrow.class, new BlockProperties(Material.ROCK, MapColor.SNOW).hardness(2.0F).resistance(2.0F));
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntityUrnOfSorrow urn = (TileEntityUrnOfSorrow) world.getTileEntity(pos);

        if(urn == null)
        {
            return false;
        }

        ItemStackHandler inventory = urn.getInventory();
        ItemStack inventoryStack = inventory.getStackInSlot(0);
        ItemStack heldStack = player.getHeldItem(hand);

        if(!player.isSneaking())
        {
            if(heldStack.getItem() == Items.POTIONITEM && state.getValue(TYPE) == EnumType.EMPTY)
            {
                for(PotionEffect effect : PotionUtils.getEffectsFromStack(heldStack))
                {
                    if(effect.getPotion() == NetherExMobEffects.CRYING)
                    {
                        heldStack.shrink(1);
                        world.setBlockState(pos, state.withProperty(TYPE, EnumType.FULL));
                    }
                }
            }
            else
            {
                if(inventoryStack.isEmpty() && !heldStack.isEmpty() && urn.acceptsItemStack(heldStack))
                {
                    inventory.setStackInSlot(0, heldStack.splitStack(1));
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

        return true;
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
        TileEntityUrnOfSorrow urn = (TileEntityUrnOfSorrow) world.getTileEntity(pos);

        if(urn == null)
        {
            return 0.5F;
        }

        return urn.canBreak() ? 2.0F : -1.0F;
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
