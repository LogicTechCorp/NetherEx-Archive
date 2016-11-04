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
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static nex.init.NetherExBlocks.*;

@SuppressWarnings("ConstantConditions")
public class NetherExRecipes
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExRecipes");

    public static void init()
    {
        removeRecipe(new ItemStack(Blocks.NETHER_BRICK_FENCE));

        addSlabRecipe(new ItemStack(VANILLA_STONE_SLAB, 6, 0), new ItemStack(Blocks.RED_NETHER_BRICK, 1, 0));
        addStairRecipe(new ItemStack(RED_NETHER_BRICK_STAIRS, 8, 0), new ItemStack(Blocks.RED_NETHER_BRICK, 3, 0));
        addWallRecipe(new ItemStack(VANILLA_STONE_WALL, 6, 0), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0));
        addWallRecipe(new ItemStack(VANILLA_STONE_WALL, 6, 1), new ItemStack(Blocks.NETHER_BRICK, 1, 0));
        addWallRecipe(new ItemStack(VANILLA_STONE_WALL, 6, 2), new ItemStack(Blocks.RED_NETHER_BRICK, 1, 0));
        addFenceRecipe(new ItemStack(VANILLA_STONE_FENCE, 4, 0), new ItemStack(QUARTZ_FENCE_GATE, 4, 0), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), new ItemStack(Blocks.STONE_SLAB, 1, 7));
        addFenceRecipe(new ItemStack(Blocks.NETHER_BRICK_FENCE, 4, 0), new ItemStack(NETHER_BRICK_FENCE_GATE, 4, 0), new ItemStack(Blocks.NETHER_BRICK, 1, 0), new ItemStack(Blocks.STONE_SLAB, 6, 0));
        addFenceRecipe(new ItemStack(VANILLA_STONE_FENCE, 4, 1), new ItemStack(RED_NETHER_BRICK_FENCE_GATE, 4, 0), new ItemStack(Blocks.RED_NETHER_BRICK, 1, 0), new ItemStack(VANILLA_STONE_SLAB, 1, 0));

        add4x4Recipe(new ItemStack(STONE, 4, 1), new ItemStack(STONE, 4, 0));
        add4x4Recipe(new ItemStack(STONE, 4, 2), new ItemStack(STONE, 4, 1));
        addSlabRecipe(new ItemStack(STONE_SLAB, 6, 0), new ItemStack(STONE, 1, 0));
        addSlabRecipe(new ItemStack(STONE_SLAB, 6, 1), new ItemStack(STONE, 1, 1));
        addSlabRecipe(new ItemStack(STONE_SLAB, 6, 2), new ItemStack(STONE, 1, 2));
        addStairRecipe(new ItemStack(BASALT_STAIRS, 8, 0), new ItemStack(STONE, 3, 0));
        addStairRecipe(new ItemStack(SMOOTH_BASALT_STAIRS, 8, 0), new ItemStack(STONE, 3, 1));
        addStairRecipe(new ItemStack(BASALT_BRICK_STAIRS, 8, 0), new ItemStack(STONE, 3, 2));
        addWallRecipe(new ItemStack(STONE_WALL, 6, 0), new ItemStack(STONE, 1, 0));
        addWallRecipe(new ItemStack(STONE_WALL, 6, 1), new ItemStack(STONE, 1, 1));
        addWallRecipe(new ItemStack(STONE_WALL, 6, 2), new ItemStack(STONE, 1, 2));
        addFenceRecipe(new ItemStack(STONE_FENCE, 4, 0), new ItemStack(BASALT_FENCE_GATE, 4, 0), new ItemStack(STONE, 1, 0), new ItemStack(STONE_SLAB, 1, 0));
        addFenceRecipe(new ItemStack(STONE_FENCE, 4, 1), new ItemStack(SMOOTH_BASALT_FENCE_GATE, 4, 0), new ItemStack(STONE, 1, 1), new ItemStack(STONE_SLAB, 1, 1));
        addFenceRecipe(new ItemStack(STONE_FENCE, 4, 2), new ItemStack(BASALT_BRICK_FENCE_GATE, 4, 0), new ItemStack(STONE, 1, 2), new ItemStack(STONE_SLAB, 1, 2));

        LOGGER.info("Recipes have been initialized.");
    }

    private static void addShaped(ItemStack result, Object... input)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(result, input));
    }

    private static void addSmelting(ItemStack output, ItemStack input, float xp)
    {
        FurnaceRecipes.instance().addSmeltingRecipe(input, output, xp);
    }

    private static void add4x4Recipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "##", "##", '#', input);
        addShaped(input, "##", "##", '#', result);
    }

    private static void addSlabRecipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "###", '#', input);
        addShaped(input, "##", '#', result);
    }

    private static void addStairRecipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "#  ", "## ", "###", '#', input);
        addShaped(input, "##", "##", '#', result);
    }

    private static void addFenceRecipe(ItemStack... stacks)
    {
        addShaped(stacks[0], "#X#", "#X#", '#', stacks[2], 'X', stacks[3]);
        addShaped(stacks[1], "#X#", "#X#", '#', stacks[3], 'X', stacks[2]);
    }

    private static void addWallRecipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "###", "###", '#', input);
        addShaped(input, "#", '#', result);
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
