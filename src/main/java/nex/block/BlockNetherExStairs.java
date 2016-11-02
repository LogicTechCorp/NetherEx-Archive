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

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import nex.NetherEx;
import org.apache.commons.lang3.StringUtils;

public class BlockNetherExStairs extends BlockStairs
{
    public BlockNetherExStairs(String name, IBlockState state)
    {
        super(state);

        String[] nameParts = name.split("_");
        String stairName = nameParts[0] + StringUtils.capitalize(nameParts[1]);
        String stairType = nameParts[2];

        for(int i = 3; i < nameParts.length; i++)
        {
            stairType += StringUtils.capitalize(nameParts[i]);
        }

        useNeighborBrightness = true;

        setCreativeTab(NetherEx.CREATIVE_TAB);
        setSoundType(SoundType.STONE);
        setRegistryName(NetherEx.MOD_ID + ":" + name);
        setUnlocalizedName(NetherEx.MOD_ID + ":" + stairName + "." + stairType);
    }
}
