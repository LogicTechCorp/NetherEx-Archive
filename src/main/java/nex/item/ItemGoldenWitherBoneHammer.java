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

package nex.item;

import lex.item.ItemHammerLibEx;
import lex.util.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import nex.NetherEx;
import nex.init.NetherExMaterials;

public class ItemGoldenWitherBoneHammer extends ItemHammerLibEx
{
    public ItemGoldenWitherBoneHammer()
    {
        super(NetherEx.instance, "golden_wither_bone_hammer", NetherExMaterials.GOLDEN_WITHER_BONE);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        NBTTagCompound compound = new NBTTagCompound();

        if(entity.dimension == DimensionType.NETHER.getId())
        {
            compound.setBoolean("Nether", true);
        }
        else
        {
            compound.setBoolean("Nether", false);
        }

        NBTHelper.setTagCompound(stack, compound);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState state)
    {
        if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("Nether"))
        {
            if(stack.getTagCompound().getBoolean("Nether"))
            {
                return ToolMaterial.IRON.getHarvestLevel();
            }
        }

        return ToolMaterial.GOLD.getHarvestLevel();
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("Nether"))
        {
            if(!stack.getTagCompound().getBoolean("Nether"))
            {
                damage += 63;

                if(this.getDamage(stack) - 64 == 0)
                {
                    damage += 1;
                }
            }
        }

        super.setDamage(stack, damage);
    }
}
