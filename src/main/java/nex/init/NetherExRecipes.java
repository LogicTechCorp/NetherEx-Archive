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

package nex.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;
import nex.block.BlockBasalt;
import nex.block.BlockNetherrack;
import nex.util.NBTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static nex.init.NetherExBlocks.*;
import static nex.init.NetherExItems.*;

@SuppressWarnings("ConstantConditions")
public class NetherExRecipes
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExRecipes");

    public static void init()
    {
        LOGGER.info("Recipe registration started.");

        removeRecipe(new ItemStack(Blocks.NETHER_BRICK_FENCE));

        NBTTagCompound compound = new NBTTagCompound();

        addSlabRecipe(new ItemStack(SLAB_VANILLA, 6, 0), new ItemStack(Blocks.RED_NETHER_BRICK, 1, 0));
        addStairRecipe(new ItemStack(STAIRS_RED_NETHER_BRICK, 8, 0), new ItemStack(Blocks.RED_NETHER_BRICK, 3, 0));
        addWallRecipe(new ItemStack(WALL_VANILLA, 6, 0), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0));
        addWallRecipe(new ItemStack(WALL_VANILLA, 6, 1), new ItemStack(Blocks.NETHER_BRICK, 1, 0));
        addWallRecipe(new ItemStack(WALL_VANILLA, 6, 2), new ItemStack(Blocks.RED_NETHER_BRICK, 1, 0));
        addFenceRecipe(new ItemStack(FENCE_VANILLA, 4, 0), new ItemStack(FENCE_GATE_QUARTZ, 4, 0), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), new ItemStack(Blocks.STONE_SLAB, 1, 7));
        addFenceRecipe(new ItemStack(Blocks.NETHER_BRICK_FENCE, 4, 0), new ItemStack(FENCE_GATE_NETHER_BRICK, 4, 0), new ItemStack(Blocks.NETHER_BRICK, 1, 0), new ItemStack(Blocks.STONE_SLAB, 6, 0));
        addFenceRecipe(new ItemStack(FENCE_VANILLA, 4, 1), new ItemStack(FENCE_GATE_RED_NETHER_BRICK, 4, 0), new ItemStack(Blocks.RED_NETHER_BRICK, 1, 0), new ItemStack(SLAB_VANILLA, 1, 0));

        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            addSlabRecipe(new ItemStack(SLAB_BASALT, 6, type.ordinal()), new ItemStack(BLOCK_BASALT, 1, type.ordinal()));
            addWallRecipe(new ItemStack(WALL_BASALT, 6, type.ordinal()), new ItemStack(BLOCK_BASALT, 1, type.ordinal()));
        }

        add4x4Recipe(new ItemStack(BLOCK_BASALT, 4, 1), new ItemStack(BLOCK_BASALT, 4, 0));
        add4x4Recipe(new ItemStack(BLOCK_BASALT, 4, 2), new ItemStack(BLOCK_BASALT, 4, 1));
        add4x4Recipe(new ItemStack(BLOCK_BASALT, 4, 3), new ItemStack(BLOCK_BASALT, 4, 2));
        addStairRecipe(new ItemStack(STAIRS_BASALT_NORMAL, 8, 0), new ItemStack(BLOCK_BASALT, 3, 0));
        addStairRecipe(new ItemStack(STAIRS_BASALT_SMOOTH, 8, 0), new ItemStack(BLOCK_BASALT, 3, 1));
        addStairRecipe(new ItemStack(STAIRS_BASALT_BRICK, 8, 0), new ItemStack(BLOCK_BASALT, 3, 2));
        addStairRecipe(new ItemStack(STAIRS_BASALT_PILLAR, 8, 0), new ItemStack(BLOCK_BASALT, 3, 3));
        addFenceRecipe(new ItemStack(FENCE_BASALT, 4, 0), new ItemStack(FENCE_GATE_BASALT, 4, 0), new ItemStack(BLOCK_BASALT, 1, 0), new ItemStack(SLAB_BASALT, 1, 0));
        addFenceRecipe(new ItemStack(FENCE_BASALT, 4, 1), new ItemStack(FENCE_GATE_BASALT_SMOOTH, 4, 0), new ItemStack(BLOCK_BASALT, 1, 1), new ItemStack(SLAB_BASALT, 1, 1));
        addFenceRecipe(new ItemStack(FENCE_BASALT, 4, 2), new ItemStack(FENCE_GATE_BASALT_BRICK, 4, 0), new ItemStack(BLOCK_BASALT, 1, 2), new ItemStack(SLAB_BASALT, 1, 2));
        addFenceRecipe(new ItemStack(FENCE_BASALT, 4, 3), new ItemStack(FENCE_GATE_BASALT_PILLAR, 4, 0), new ItemStack(BLOCK_BASALT, 1, 3), new ItemStack(SLAB_BASALT, 1, 3));

        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            addSmelting(new ItemStack(ITEM_NETHER_BRICK, 1, type.ordinal()), new ItemStack(BLOCK_NETHERRACK, 1, type.ordinal()), 0.5F);
            addShaped(new ItemStack(BLOCK_NETHER_BRICK, 4, type.ordinal()), "##", "##", '#', new ItemStack(ITEM_NETHER_BRICK, 1, type.ordinal()));
            addSlabRecipe(new ItemStack(SLAB_NETHER_BRICK, 6, type.ordinal()), new ItemStack(BLOCK_NETHER_BRICK, 1, type.ordinal()));
            addWallRecipe(new ItemStack(WALL_NETHER_BRICK, 6, type.ordinal()), new ItemStack(BLOCK_NETHER_BRICK, 1, type.ordinal()));
        }

        addStairRecipe(new ItemStack(STAIRS_NETHER_BRICK_FIERY, 8, 0), new ItemStack(BLOCK_NETHER_BRICK, 3, 0));
        addStairRecipe(new ItemStack(STAIRS_NETHER_BRICK_ICY, 8, 0), new ItemStack(BLOCK_NETHER_BRICK, 3, 1));
        addStairRecipe(new ItemStack(STAIRS_NETHER_BRICK_LIVELY, 8, 0), new ItemStack(BLOCK_NETHER_BRICK, 3, 2));
        addStairRecipe(new ItemStack(STAIRS_NETHER_BRICK_GLOOMY, 8, 0), new ItemStack(BLOCK_NETHER_BRICK, 3, 3));
        addStairRecipe(new ItemStack(STAIRS_NETHER_BRICK_HOLY, 8, 0), new ItemStack(BLOCK_NETHER_BRICK, 3, 4));
        addFenceRecipe(new ItemStack(FENCE_NETHER_BRICK, 4, 0), new ItemStack(FENCE_GATE_NETHER_BRICK_FIERY, 4, 0), new ItemStack(BLOCK_NETHER_BRICK, 1, 0), new ItemStack(SLAB_NETHER_BRICK, 1, 0));
        addFenceRecipe(new ItemStack(FENCE_NETHER_BRICK, 4, 1), new ItemStack(FENCE_GATE_NETHER_BRICK_ICY, 4, 0), new ItemStack(BLOCK_NETHER_BRICK, 1, 1), new ItemStack(SLAB_NETHER_BRICK, 1, 1));
        addFenceRecipe(new ItemStack(FENCE_NETHER_BRICK, 4, 2), new ItemStack(FENCE_GATE_NETHER_BRICK_LIVELY, 4, 0), new ItemStack(BLOCK_NETHER_BRICK, 1, 2), new ItemStack(SLAB_NETHER_BRICK, 1, 2));
        addFenceRecipe(new ItemStack(FENCE_NETHER_BRICK, 4, 3), new ItemStack(FENCE_GATE_NETHER_BRICK_GLOOMY, 4, 0), new ItemStack(BLOCK_NETHER_BRICK, 1, 3), new ItemStack(SLAB_NETHER_BRICK, 1, 3));
        addFenceRecipe(new ItemStack(FENCE_NETHER_BRICK, 4, 4), new ItemStack(FENCE_GATE_NETHER_BRICK_HOLY, 4, 0), new ItemStack(BLOCK_NETHER_BRICK, 1, 4), new ItemStack(SLAB_NETHER_BRICK, 1, 4));

        addShaped(new ItemStack(ITEM_BONE_MEAL_WITHERED, 3, 0), "#", '#', ITEM_BONE_WITHERED);

        addSmelting(new ItemStack(FOOD_MEAT_GHAST_COOKED, 1, 0), new ItemStack(FOOD_MEAT_GHAST_RAW, 1, 0), 0.5F);
        addSmelting(new ItemStack(FOOD_MAGMA_CREAM_CONGEALED, 1, 0), new ItemStack(Items.MAGMA_CREAM, 1, 0), 0.5F);

        compound.setBoolean("Variant", true);

        addSwordRecipe(new ItemStack(TOOL_SWORD_BONE, 1, 0), new ItemStack(Items.GOLD_INGOT, 1, 0), new ItemStack(Items.BONE, 1, 0));
        addPickaxeRecipe(new ItemStack(TOOL_PICKAXE_BONE, 1, 0), new ItemStack(Items.GOLD_INGOT, 1, 0), new ItemStack(Items.BONE, 1, 0));
        addShovelRecipe(new ItemStack(TOOL_SHOVEL_BONE, 1, 0), new ItemStack(Items.GOLD_INGOT, 1, 0), new ItemStack(Items.BONE, 1, 0));
        addAxeRecipe(new ItemStack(TOOL_AXE_BONE, 1, 0), new ItemStack(Items.GOLD_INGOT, 1, 0), new ItemStack(Items.BONE, 1, 0));
        addHoeRecipe(new ItemStack(TOOL_HOE_BONE, 1, 0), new ItemStack(Items.GOLD_INGOT, 1, 0), new ItemStack(Items.BONE, 1, 0));
        addSwordRecipe(NBTUtil.setTag(new ItemStack(TOOL_SWORD_BONE, 1, 0), compound), new ItemStack(Items.GOLD_INGOT, 1, 0), new ItemStack(ITEM_BONE_WITHERED, 1, 0));
        addPickaxeRecipe(NBTUtil.setTag(new ItemStack(TOOL_PICKAXE_BONE, 1, 0), compound), new ItemStack(Items.GOLD_INGOT, 1, 0), new ItemStack(ITEM_BONE_WITHERED, 1, 0));
        addShovelRecipe(NBTUtil.setTag(new ItemStack(TOOL_SHOVEL_BONE, 1, 0), compound), new ItemStack(Items.GOLD_INGOT, 1, 0), new ItemStack(ITEM_BONE_WITHERED, 1, 0));
        addAxeRecipe(NBTUtil.setTag(new ItemStack(TOOL_AXE_BONE, 1, 0), compound), new ItemStack(Items.GOLD_INGOT, 1, 0), new ItemStack(ITEM_BONE_WITHERED, 1, 0));
        addHoeRecipe(NBTUtil.setTag(new ItemStack(TOOL_HOE_BONE, 1, 0), compound), new ItemStack(Items.GOLD_INGOT, 1, 0), new ItemStack(ITEM_BONE_WITHERED, 1, 0));

        addHelmetRecipe(new ItemStack(ARMOR_HELMET_BONE, 1, 0), new ItemStack(Items.BONE, 1, 0));
        addChestplateRecipe(new ItemStack(ARMOR_CHESTPLATE_BONE, 1, 0), new ItemStack(Items.BONE, 1, 0));
        addLeggingsRecipe(new ItemStack(ARMOR_LEGGINGS_BONE, 1, 0), new ItemStack(Items.BONE, 1, 0));
        addBootsRecipe(new ItemStack(ARMOR_BOOTS_BONE, 1, 0), new ItemStack(Items.BONE, 1, 0));
        addHelmetRecipe(NBTUtil.setTag(new ItemStack(ARMOR_HELMET_BONE, 1, 0), compound), new ItemStack(ITEM_BONE_WITHERED, 1, 0));
        addChestplateRecipe(NBTUtil.setTag(new ItemStack(ARMOR_CHESTPLATE_BONE, 1, 0), compound), new ItemStack(ITEM_BONE_WITHERED, 1, 0));
        addLeggingsRecipe(NBTUtil.setTag(new ItemStack(ARMOR_LEGGINGS_BONE, 1, 0), compound), new ItemStack(ITEM_BONE_WITHERED, 1, 0));
        addBootsRecipe(NBTUtil.setTag(new ItemStack(ARMOR_BOOTS_BONE, 1, 0), compound), new ItemStack(ITEM_BONE_WITHERED, 1, 0));
        addHelmetRecipe(new ItemStack(ARMOR_HELMET_HIDE_SALAMANDER, 1, 0), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 0));
        addChestplateRecipe(new ItemStack(ARMOR_CHESTPLATE_HIDE_SALAMANDER, 1, 0), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 0));
        addLeggingsRecipe(new ItemStack(ARMOR_LEGGINGS_HIDE_SALAMANDER, 1, 0), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 0));
        addBootsRecipe(new ItemStack(ARMOR_BOOTS_HIDE_SALAMANDER, 1, 0), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 0));
        addHelmetRecipe(NBTUtil.setTag(new ItemStack(ARMOR_HELMET_HIDE_SALAMANDER, 1, 0), compound), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 1));
        addChestplateRecipe(NBTUtil.setTag(new ItemStack(ARMOR_CHESTPLATE_HIDE_SALAMANDER, 1, 0), compound), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 1));
        addLeggingsRecipe(NBTUtil.setTag(new ItemStack(ARMOR_LEGGINGS_HIDE_SALAMANDER, 1, 0), compound), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 1));
        addBootsRecipe(NBTUtil.setTag(new ItemStack(ARMOR_BOOTS_HIDE_SALAMANDER, 1, 0), compound), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 1));

        LOGGER.info("Recipe registration completed.");
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

    private static void addSurroundedRecipe(ItemStack result, ItemStack surrounding, ItemStack core)
    {
        addShaped(result, "###", "#X#", "###", '#', surrounding, 'X', core);
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

    private static void addSwordRecipe(ItemStack result, ItemStack core, ItemStack handle)
    {
        addShaped(result, " # ", " # ", " X ", '#', core, 'X', handle);
    }

    private static void addPickaxeRecipe(ItemStack result, ItemStack core, ItemStack handle)
    {
        addShaped(result, "###", " X ", " X ", '#', core, 'X', handle);
    }

    private static void addShovelRecipe(ItemStack result, ItemStack core, ItemStack handle)
    {
        addShaped(result, " # ", " X ", " X ", '#', core, 'X', handle);
    }

    private static void addAxeRecipe(ItemStack result, ItemStack core, ItemStack handle)
    {
        addShaped(result, "##", "#X", " X", '#', core, 'X', handle);
    }

    private static void addHoeRecipe(ItemStack result, ItemStack core, ItemStack handle)
    {
        addShaped(result, "##", " X", " X", '#', core, 'X', handle);
    }

    private static void addHelmetRecipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "###", "# #", '#', input);
    }

    private static void addChestplateRecipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "# #", "###", "###", '#', input);
    }

    private static void addLeggingsRecipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "###", "# #", "# #", '#', input);
    }

    private static void addBootsRecipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "# #", "# #", '#', input);
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
