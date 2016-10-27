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

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class NetherExRecipes
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExRecipes");

    public static void init()
    {
        removeRecipe(new ItemStack(Blocks.BONE_BLOCK));

        GameRegistry.addShapedRecipe(new ItemStack(NetherExBlocks.BLOCK_BONE, 1, 0), "###", "###", "###", '#', new ItemStack(Items.DYE, 9, EnumDyeColor.WHITE.getDyeDamage()));
        GameRegistry.addShapedRecipe(new ItemStack(NetherExBlocks.BLOCK_BONE, 1, 1), "##", "##", "##", '#', new ItemStack(Items.DYE, 6, EnumDyeColor.WHITE.getDyeDamage()));
        GameRegistry.addShapedRecipe(new ItemStack(NetherExBlocks.BLOCK_BONE, 1, 2), "#", "#", "#", '#', new ItemStack(Items.DYE, 3, EnumDyeColor.WHITE.getDyeDamage()));

        GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE, 9, EnumDyeColor.WHITE.getDyeDamage()), new ItemStack(NetherExBlocks.BLOCK_BONE, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE, 6, EnumDyeColor.WHITE.getDyeDamage()), new ItemStack(NetherExBlocks.BLOCK_BONE, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE, 3, EnumDyeColor.WHITE.getDyeDamage()), new ItemStack(NetherExBlocks.BLOCK_BONE, 1, 2));

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
