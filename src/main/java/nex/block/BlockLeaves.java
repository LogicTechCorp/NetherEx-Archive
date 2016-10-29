/*
 * Copyright (C) 2016.  LogicTechCorp
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

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.init.NetherExBlocks;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockLeaves extends BlockNetherEx implements IShearable
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);
    public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
    public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");

    int[] surroundings;

    public BlockLeaves()
    {
        super("leaves", Material.LEAVES);

        setSoundType(SoundType.PLANT);
        setLightOpacity(1);
        setTickRandomly(true);
        setHardness(0.2F);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(EnumType type : EnumType.values())
        {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return Blocks.LEAVES.getBlockLayer();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return Blocks.LEAVES.shouldSideBeRendered(state, world, pos, side);
    }

    @Override
    public boolean isVisuallyOpaque()
    {
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        int k = pos.getX();
        int l = pos.getY();
        int i1 = pos.getZ();

        if(world.isAreaLoaded(new BlockPos(k - 2, l - 2, i1 - 2), new BlockPos(k + 2, l + 2, i1 + 2)))
        {
            for(int j1 = -1; j1 <= 1; ++j1)
            {
                for(int k1 = -1; k1 <= 1; ++k1)
                {
                    for(int l1 = -1; l1 <= 1; ++l1)
                    {
                        BlockPos blockpos = pos.add(j1, k1, l1);
                        IBlockState iblockstate = world.getBlockState(blockpos);

                        if(iblockstate.getBlock().isLeaves(iblockstate, world, blockpos))
                        {
                            iblockstate.getBlock().beginLeavesDecay(iblockstate, world, blockpos);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if(!worldIn.isRemote)
        {
            if(state.getValue(CHECK_DECAY) && state.getValue(DECAYABLE))
            {
                int k = pos.getX();
                int l = pos.getY();
                int i1 = pos.getZ();

                if(surroundings == null)
                {
                    surroundings = new int[32768];
                }

                if(worldIn.isAreaLoaded(new BlockPos(k - 5, l - 5, i1 - 5), new BlockPos(k + 5, l + 5, i1 + 5)))
                {
                    BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                    for(int i2 = -4; i2 <= 4; ++i2)
                    {
                        for(int j2 = -4; j2 <= 4; ++j2)
                        {
                            for(int k2 = -4; k2 <= 4; ++k2)
                            {
                                IBlockState iblockstate = worldIn.getBlockState(mutablePos.setPos(k + i2, l + j2, i1 + k2));
                                Block block = iblockstate.getBlock();

                                if(!block.canSustainLeaves(iblockstate, worldIn, mutablePos.setPos(k + i2, l + j2, i1 + k2)))
                                {
                                    if(block.isLeaves(iblockstate, worldIn, mutablePos.setPos(k + i2, l + j2, i1 + k2)))
                                    {
                                        surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -2;
                                    }
                                    else
                                    {
                                        surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -1;
                                    }
                                }
                                else
                                {
                                    surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = 0;
                                }
                            }
                        }
                    }

                    for(int i3 = 1; i3 <= 4; ++i3)
                    {
                        for(int j3 = -4; j3 <= 4; ++j3)
                        {
                            for(int k3 = -4; k3 <= 4; ++k3)
                            {
                                for(int l3 = -4; l3 <= 4; ++l3)
                                {
                                    if(surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16] == i3 - 1)
                                    {
                                        if(surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2)
                                        {
                                            surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                                        }

                                        if(surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2)
                                        {
                                            surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                                        }

                                        if(surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] == -2)
                                        {
                                            surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] = i3;
                                        }

                                        if(surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] == -2)
                                        {
                                            surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] = i3;
                                        }

                                        if(surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] == -2)
                                        {
                                            surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] = i3;
                                        }

                                        if(surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] == -2)
                                        {
                                            surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] = i3;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                int l2 = surroundings[16912];

                if(l2 >= 0)
                {
                    worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY, Boolean.FALSE), 4);
                }
                else
                {
                    destroy(worldIn, pos);
                }
            }
        }
    }

    private void destroy(World worldIn, BlockPos pos)
    {
        dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public int quantityDropped(Random random)
    {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NetherExBlocks.SAPLING);
    }

    protected int getSaplingDropChance(IBlockState state)
    {
        return 20;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos)
    {
        if(!state.getValue(CHECK_DECAY))
        {
            world.setBlockState(pos, state.withProperty(CHECK_DECAY, true), 4);
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> drops = Lists.newArrayList();
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        int chance = getSaplingDropChance(state);

        if(fortune > 0)
        {
            chance -= 2 << fortune;
            if(chance < 10) chance = 10;
        }
        if(rand.nextInt(chance) == 0)
        {
            drops.add(new ItemStack(getItemDropped(state, rand, fortune), 1, damageDropped(state)));
        }

        captureDrops(true);
        drops.addAll(captureDrops(false));
        return drops;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(TYPE, getWoodType(meta)).withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(TYPE).ordinal();

        if(!state.getValue(DECAYABLE))
        {
            i |= 4;
        }

        if(state.getValue(CHECK_DECAY))
        {
            i |= 8;
        }

        return i;
    }

    public EnumType getWoodType(int meta)
    {
        return EnumType.values()[((meta & 3) % 4)];
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE, CHECK_DECAY, DECAYABLE);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return Arrays.asList(new ItemStack(this, 1, world.getBlockState(pos).getValue(TYPE).ordinal()));
    }

    public enum EnumType implements IStringSerializable
    {
        TAINTED;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }
    }
}
