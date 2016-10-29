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

package nex.init;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class NetherExRecipes
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExRecipes");

    public static void init()
    {
        LOGGER.info("Recipes have been initialized.");
    }

    private static void removeRecipe(ItemStack output)
    {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();

        for(int i = 0; i < recipes.size(); i++)
        {
            IRecipe recipe = recipes.get(i);
            ItemStack recipeOutput = recipe.getRecipeOutput();

            if(ItemStack.areItemStacksEqual(output, recipeOutput))
            {
                recipes.remove(i--);
            }
        }
    }
}
