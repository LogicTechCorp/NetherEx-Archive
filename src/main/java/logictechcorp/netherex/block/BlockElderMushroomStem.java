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

import logictechcorp.libraryex.block.BlockModBigMushroom;
import logictechcorp.libraryex.block.HarvestLevel;
import logictechcorp.libraryex.block.HarvestTool;
import logictechcorp.libraryex.block.property.BlockProperties;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockElderMushroomStem extends BlockModBigMushroom
{
    public static final PropertyEnum<EnumType> AXIS = PropertyEnum.create("axis", EnumType.class);

    public BlockElderMushroomStem()
    {
        super(NetherEx.getResource("elder_mushroom_stem"), new BlockProperties(Material.WOOD, MapColor.SNOW).sound(SoundType.WOOD).harvestLevel(HarvestTool.AXE, HarvestLevel.WOOD).hardness(0.5F).hardness(2.0F));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(AXIS, EnumType.fromAxis(facing.getAxis()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AXIS, EnumType.fromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(AXIS).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AXIS);
    }

    public enum EnumType implements IStringSerializable
    {
        Y,
        X,
        Z,
        NONE;

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

        public static EnumType fromAxis(EnumFacing.Axis axis)
        {
            switch(axis)
            {
                case X:
                    return X;
                case Y:
                    return Y;
                case Z:
                    return Z;
                default:
                    return NONE;
            }
        }
    }
}
