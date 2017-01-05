/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import nex.init.NetherExMaterials;

public class ItemBoneAxe extends ItemNetherExAxe
{
    public ItemBoneAxe()
    {
        super("tool_axe_bone", NetherExMaterials.BONE, 6.0F, -3.0F);

        addPropertyOverride(new ResourceLocation("withered"), (stack, worldIn, entityIn) -> isWithered(stack) ? 1.0F : 0.0F);
    }

    private static boolean isWithered(ItemStack stack)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("Withered");
    }
}

