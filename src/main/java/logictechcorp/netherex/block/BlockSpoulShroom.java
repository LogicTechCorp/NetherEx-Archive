/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
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

import logictechcorp.libraryex.block.BlockModSmallMushroom;
import logictechcorp.libraryex.block.builder.BlockProperties;
import logictechcorp.libraryex.world.generation.trait.iface.IBiomeTrait;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitStructure;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.Arrays;
import java.util.Random;

public class BlockSpoulShroom extends BlockModSmallMushroom
{
    public static final PropertyEnum<EnumPart> PART = PropertyEnum.create("part", EnumPart.class);
    protected static final AxisAlignedBB MUSHROOM_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public BlockSpoulShroom()
    {
        super(NetherEx.getResource("spoul_shroom"), new BlockProperties(Material.PLANTS, MapColor.BROWN), EnumPlantType.Nether);
        this.setDefaultState(this.blockState.getBaseState().withProperty(PART, EnumPart.SINGLE));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return MUSHROOM_AABB;
    }

    @Override
    public void grow(World world, Random random, BlockPos pos, IBlockState state)
    {
        BlockPos posUp = pos.up();
        IBlockState stateDown = world.getBlockState(pos.down());
        Block blockDown = stateDown.getBlock();

        if(state.getValue(PART) == EnumPart.SINGLE)
        {
            if(world.isAirBlock(posUp) && blockDown != this)
            {
                world.setBlockState(pos, state.withProperty(PART, EnumPart.DOUBLE_BOTTOM));
                world.setBlockState(posUp, state.withProperty(PART, EnumPart.DOUBLE_TOP));
            }
        }
        else
        {
            world.setBlockToAir(posUp);
            world.setBlockToAir(pos);

            IBiomeTrait spoulShroom = BiomeTraitStructure.create(trait -> {
                trait.generationAttempts(1);
                trait.randomizeGenerationAttempts(true);
                trait.minimumGenerationHeight(32);
                trait.maximumGenerationHeight(108);
                trait.structures(Arrays.asList(
                        NetherEx.getResource("spoul_shroom/spoul_shroom_01"),
                        NetherEx.getResource("spoul_shroom/spoul_shroom_02"),
                        NetherEx.getResource("spoul_shroom/spoul_shroom_03"),
                        NetherEx.getResource("spoul_shroom/spoul_shroom_04"),
                        NetherEx.getResource("spoul_shroom/spoul_shroom_05"),
                        NetherEx.getResource("spoul_shroom/spoul_shroom_06"),
                        NetherEx.getResource("spoul_shroom/spoul_shroom_07"),
                        NetherEx.getResource("spoul_shroom/spoul_shroom_08"),
                        NetherEx.getResource("spoul_shroom/spoul_shroom_09"),
                        NetherEx.getResource("spoul_shroom/spoul_shroom_10"),
                        NetherEx.getResource("spoul_shroom/spoul_shroom_11"),
                        NetherEx.getResource("spoul_shroom/spoul_shroom_12")));
                trait.structureType(BiomeTraitStructure.StructureType.GROUND);
                trait.ignoredBlock(Blocks.AIR);
                trait.clearancePercentage(1.0D);
                trait.orientRandomly(true);
            });

            if(!spoulShroom.generate(world, pos, random))
            {
                world.setBlockState(pos, state.withProperty(PART, EnumPart.DOUBLE_BOTTOM));
                world.setBlockState(posUp, state.withProperty(PART, EnumPart.DOUBLE_TOP));
            }
        }
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
    {
        IBlockState soil = world.getBlockState(pos.down());

        if(state.getValue(PART) == EnumPart.DOUBLE_TOP && soil.getBlock() == this && soil.getValue(PART) == EnumPart.DOUBLE_BOTTOM)
        {
            return false;
        }

        return !isClient && soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
    {
        IBlockState plant = plantable.getPlant(world, pos.offset(direction));

        if(plant.getBlock() == this)
        {
            if(plant.getValue(PART) == EnumPart.DOUBLE_TOP && state.getValue(PART) == EnumPart.DOUBLE_TOP)
            {
                return false;
            }
            else
            {
                return plant.getValue(PART) == EnumPart.DOUBLE_TOP && state.getValue(PART) == EnumPart.DOUBLE_BOTTOM;
            }
        }

        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(PART, EnumPart.fromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(PART).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, PART);
    }

    public enum EnumPart implements IStringSerializable
    {
        SINGLE,
        DOUBLE_TOP,
        DOUBLE_BOTTOM;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase();
        }

        public static EnumPart fromMeta(int meta)
        {
            if(meta < 0 || meta >= values().length)
            {
                meta = 0;
            }

            return values()[meta];
        }
    }
}
