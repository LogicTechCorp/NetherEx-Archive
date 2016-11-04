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

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import nex.NetherEx;
import org.apache.commons.lang3.StringUtils;

public class BlockNetherExFenceGate extends BlockFenceGate
{
    public BlockNetherExFenceGate(String name, int prefixLength, Material material)
    {
        super(BlockPlanks.EnumType.OAK);

        ReflectionHelper.setPrivateValue(Block.class, this, material, "field_149764_J", "blockMaterial");
        ReflectionHelper.setPrivateValue(Block.class, this, material.getMaterialMapColor(), "field_181083_K", "blockMapColor");

        String[] nameParts = name.split("_");
        String gateName = nameParts[0];

        for(int i = 1; i <= prefixLength; i++)
        {
            gateName += StringUtils.capitalize(nameParts[i]);
        }

        String gateType = nameParts[prefixLength + 1];

        for(int i = prefixLength + 2; i < nameParts.length; i++)
        {
            gateType += StringUtils.capitalize(nameParts[i]);
        }

        useNeighborBrightness = true;

        setCreativeTab(NetherEx.CREATIVE_TAB);
        setSoundType(SoundType.STONE);
        setRegistryName(NetherEx.MOD_ID + ":" + name);
        setUnlocalizedName(NetherEx.MOD_ID + ":" + gateName + "." + gateType);
    }
}
