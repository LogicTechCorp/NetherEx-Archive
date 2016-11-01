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
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import nex.NetherEx;

public abstract class BlockNetherExSlab extends BlockSlab
{
    private static boolean isDouble;

    public BlockNetherExSlab(String name, Material material, boolean isDoubleIn)
    {
        super(setDouble(material, isDoubleIn));

        isDouble = isDoubleIn;

        if(!isDoubleIn)
        {
            useNeighborBrightness = true;
            setCreativeTab(NetherEx.CREATIVE_TAB);
        }
        if(isDoubleIn)
        {
            name += "_double";
        }

        setSoundType(SoundType.STONE);
        setRegistryName(NetherEx.MOD_ID + ":" + name);
        setUnlocalizedName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().toString()));
    }

    @Override
    public boolean isDouble()
    {
        return isDouble;
    }

    private static Material setDouble(Material material, boolean isDoubleIn)
    {
        isDouble = isDoubleIn;
        return material;
    }
}
