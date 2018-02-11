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

import com.google.common.base.CaseFormat;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import nex.NetherEx;

public class BlockNetherExWall extends BlockWall
{
    public BlockNetherExWall(String name, Material material)
    {
        super(Blocks.PLANKS);
        ReflectionHelper.setPrivateValue(Block.class, this, material.getMaterialMapColor(), "field_181083_K", "blockMapColor");
        useNeighborBrightness = true;
        setCreativeTab(NetherEx.CREATIVE_TAB);
        setSoundType(SoundType.STONE);
        setRegistryName(NetherEx.MOD_ID + ":" + name);
        setUnlocalizedName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().toString()));
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }
}
