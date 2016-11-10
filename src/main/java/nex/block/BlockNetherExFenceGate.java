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

import com.google.common.base.CaseFormat;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import nex.NetherEx;
import org.apache.commons.lang3.StringUtils;

public class BlockNetherExFenceGate extends BlockFenceGate
{
    public BlockNetherExFenceGate(String name, Material material)
    {
        super(BlockPlanks.EnumType.OAK);

        ReflectionHelper.setPrivateValue(Block.class, this, material, "field_149764_J", "blockMaterial");
        ReflectionHelper.setPrivateValue(Block.class, this, material.getMaterialMapColor(), "field_181083_K", "blockMapColor");

        String[] nameParts = name.split("_");

        String gateName = ":fenceGate" + StringUtils.capitalize(nameParts[0]) + ".";
        String gateType = nameParts[1];

        for(int i = 2; i < nameParts.length; i++)
        {
            gateType += StringUtils.capitalize(nameParts[i]);
        }

        useNeighborBrightness = true;

        setCreativeTab(NetherEx.CREATIVE_TAB);
        setSoundType(SoundType.STONE);
        setRegistryName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, NetherEx.MOD_ID + ":fence_gate_" + (name.contains("vanilla_") ? name.replace("vanilla_", "") : name)));
        setUnlocalizedName(NetherEx.MOD_ID + gateName + gateType);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        EnumFacing.Axis facing = state.getValue(FACING).getAxis();

        Block northBlock = worldIn.getBlockState(pos.north()).getBlock();
        Block eastBlock = worldIn.getBlockState(pos.east()).getBlock();
        Block southBlock = worldIn.getBlockState(pos.south()).getBlock();
        Block westBlock = worldIn.getBlockState(pos.west()).getBlock();

        if(facing == EnumFacing.Axis.Z && ((westBlock instanceof BlockWall) || (eastBlock instanceof BlockWall)) || facing == EnumFacing.Axis.X && ((northBlock instanceof BlockWall) || (southBlock instanceof BlockWall)))
        {
            return state.withProperty(IN_WALL, true);
        }

        return state.withProperty(IN_WALL, false);
    }
}
