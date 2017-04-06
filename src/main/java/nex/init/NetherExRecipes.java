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

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.oredict.ShapedOreRecipe;
import nex.block.BlockBasalt;
import nex.block.BlockNetherrack;
import nex.util.NBTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

import static net.minecraft.init.Blocks.*;
import static net.minecraft.init.Items.*;
import static net.minecraft.init.PotionTypes.AWKWARD;
import static nex.init.NetherExBlocks.*;
import static nex.init.NetherExEffectTypes.*;
import static nex.init.NetherExItems.*;

@SuppressWarnings("ConstantConditions")
public class NetherExRecipes
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExRecipes");

    public static void init()
    {
        LOGGER.info("Recipe registration started.");

        NBTTagCompound variant = new NBTTagCompound();
        variant.setBoolean("Variant", true);

        removeRecipe(Item.getItemFromBlock(NETHER_BRICK_FENCE));

        add1x1Recipe(new ItemStack(BLOCK_BASALT, 1, 0), new ItemStack(BLOCK_BASALT, 1, 1));
        add1x1Recipe(new ItemStack(BLOCK_BASALT, 1, 1), new ItemStack(BLOCK_BASALT, 1, 2));
        add1x1Recipe(new ItemStack(BLOCK_BASALT, 1, 2), new ItemStack(BLOCK_BASALT, 1, 3));

        add2x2Recipe(new ItemStack(BLOCK_BASALT, 4, 1), new ItemStack(BLOCK_BASALT, 1, 0));
        add2x2Recipe(new ItemStack(BLOCK_BASALT, 4, 2), new ItemStack(BLOCK_BASALT, 1, 1));
        add2x2Recipe(new ItemStack(BLOCK_BASALT, 4, 3), new ItemStack(BLOCK_BASALT, 1, 2));

        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            addSmelting(new ItemStack(ITEM_BRICK_NETHER, 1, type.ordinal()), new ItemStack(BLOCK_NETHERRACK, 1, type.ordinal()), 0.5F);
            add2x2Recipe(new ItemStack(BLOCK_BRICK_NETHER, 4, type.ordinal()), new ItemStack(ITEM_BRICK_NETHER, 1, type.ordinal()));
            addSlabRecipe(new ItemStack(SLAB_BRICK_NETHER, 6, type.ordinal()), new ItemStack(BLOCK_BRICK_NETHER, 1, type.ordinal()));
            addWallRecipe(new ItemStack(WALL_BRICK_NETHER, 6, type.ordinal()), new ItemStack(BLOCK_BRICK_NETHER, 1, type.ordinal()));
        }

        addSmelting(new ItemStack(BLOCK_GLASS_SOUL, 1, 0), new ItemStack(SOUL_SAND, 1, 0), 0.5F);
        addPaneRecipe(new ItemStack(BLOCK_GLASS_PANE_SOUL, 16, 0), new ItemStack(BLOCK_GLASS_SOUL, 1, 0));
        add3x3Recipe(new ItemStack(BLOCK_RIME, 1, 0), new ItemStack(ITEM_CRYSTAL_RIME, 1, 0));
        add2x3Recipe(new ItemStack(BLOCK_BONE_SLIVER, 1, 0), new ItemStack(DYE, 6, EnumDyeColor.WHITE.getDyeDamage()));
        add1x3Recipe(new ItemStack(BLOCK_BONE_CHUNK, 1, 0), new ItemStack(DYE, 3, EnumDyeColor.WHITE.getDyeDamage()));

        addSlabRecipe(new ItemStack(SLAB_VANILLA, 6, 0), new ItemStack(RED_NETHER_BRICK, 1, 0));

        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            addSlabRecipe(new ItemStack(SLAB_BASALT, 6, type.ordinal()), new ItemStack(BLOCK_BASALT, 1, type.ordinal()));
            addWallRecipe(new ItemStack(WALL_BASALT, 6, type.ordinal()), new ItemStack(BLOCK_BASALT, 1, type.ordinal()));
        }

        addStairRecipe(new ItemStack(STAIRS_RED_BRICK_NETHER, 8, 0), new ItemStack(RED_NETHER_BRICK, 3, 0));
        addStairRecipe(new ItemStack(STAIRS_BASALT_NORMAL, 8, 0), new ItemStack(BLOCK_BASALT, 3, 0));
        addStairRecipe(new ItemStack(STAIRS_BASALT_SMOOTH, 8, 0), new ItemStack(BLOCK_BASALT, 3, 1));
        addStairRecipe(new ItemStack(STAIRS_BASALT_BRICK, 8, 0), new ItemStack(BLOCK_BASALT, 3, 2));
        addStairRecipe(new ItemStack(STAIRS_BASALT_PILLAR, 8, 0), new ItemStack(BLOCK_BASALT, 3, 3));
        addStairRecipe(new ItemStack(STAIRS_BRICK_NETHER_FIERY, 8, 0), new ItemStack(BLOCK_BRICK_NETHER, 3, 0));
        addStairRecipe(new ItemStack(STAIRS_BRICK_NETHER_ICY, 8, 0), new ItemStack(BLOCK_BRICK_NETHER, 3, 1));
        addStairRecipe(new ItemStack(STAIRS_BRICK_NETHER_LIVELY, 8, 0), new ItemStack(BLOCK_BRICK_NETHER, 3, 2));
        addStairRecipe(new ItemStack(STAIRS_BRICK_NETHER_GLOOMY, 8, 0), new ItemStack(BLOCK_BRICK_NETHER, 3, 3));

        addWallRecipe(new ItemStack(WALL_VANILLA, 6, 0), new ItemStack(QUARTZ_BLOCK, 1, 0));
        addWallRecipe(new ItemStack(WALL_VANILLA, 6, 1), new ItemStack(NETHER_BRICK, 1, 0));
        addWallRecipe(new ItemStack(WALL_VANILLA, 6, 2), new ItemStack(RED_NETHER_BRICK, 1, 0));

        addFenceRecipe(new ItemStack(FENCE_VANILLA, 4, 0), new ItemStack(FENCE_GATE_QUARTZ, 4, 0), new ItemStack(QUARTZ_BLOCK, 1, 0), new ItemStack(STONE_SLAB, 1, 7));
        addFenceRecipe(new ItemStack(NETHER_BRICK_FENCE, 4, 0), new ItemStack(FENCE_GATE_BRICK_NETHER, 4, 0), new ItemStack(NETHER_BRICK, 1, 0), new ItemStack(STONE_SLAB, 1, 6));
        addFenceRecipe(new ItemStack(FENCE_VANILLA, 4, 1), new ItemStack(FENCE_GATE_RED_BRICK_NETHER, 4, 0), new ItemStack(RED_NETHER_BRICK, 1, 0), new ItemStack(SLAB_VANILLA, 1, 0));
        addFenceRecipe(new ItemStack(FENCE_BASALT, 4, 0), new ItemStack(FENCE_GATE_BASALT, 4, 0), new ItemStack(BLOCK_BASALT, 1, 0), new ItemStack(SLAB_BASALT, 1, 0));
        addFenceRecipe(new ItemStack(FENCE_BASALT, 4, 1), new ItemStack(FENCE_GATE_BASALT_SMOOTH, 4, 0), new ItemStack(BLOCK_BASALT, 1, 1), new ItemStack(SLAB_BASALT, 1, 1));
        addFenceRecipe(new ItemStack(FENCE_BASALT, 4, 2), new ItemStack(FENCE_GATE_BASALT_BRICK, 4, 0), new ItemStack(BLOCK_BASALT, 1, 2), new ItemStack(SLAB_BASALT, 1, 2));
        addFenceRecipe(new ItemStack(FENCE_BASALT, 4, 3), new ItemStack(FENCE_GATE_BASALT_PILLAR, 4, 0), new ItemStack(BLOCK_BASALT, 1, 3), new ItemStack(SLAB_BASALT, 1, 3));
        addFenceRecipe(new ItemStack(FENCE_BRICK_NETHER, 4, 0), new ItemStack(FENCE_GATE_BRICK_NETHER_FIERY, 4, 0), new ItemStack(BLOCK_BRICK_NETHER, 1, 0), new ItemStack(SLAB_BRICK_NETHER, 1, 0));
        addFenceRecipe(new ItemStack(FENCE_BRICK_NETHER, 4, 1), new ItemStack(FENCE_GATE_BRICK_NETHER_ICY, 4, 0), new ItemStack(BLOCK_BRICK_NETHER, 1, 1), new ItemStack(SLAB_BRICK_NETHER, 1, 1));
        addFenceRecipe(new ItemStack(FENCE_BRICK_NETHER, 4, 2), new ItemStack(FENCE_GATE_BRICK_NETHER_LIVELY, 4, 0), new ItemStack(BLOCK_BRICK_NETHER, 1, 2), new ItemStack(SLAB_BRICK_NETHER, 1, 2));
        addFenceRecipe(new ItemStack(FENCE_BRICK_NETHER, 4, 3), new ItemStack(FENCE_GATE_BRICK_NETHER_GLOOMY, 4, 0), new ItemStack(BLOCK_BRICK_NETHER, 1, 3), new ItemStack(SLAB_BRICK_NETHER, 1, 3));

        addShapeless(new ItemStack(DYE, 6, EnumDyeColor.WHITE.getDyeDamage()), new ItemStack(BLOCK_BONE_SLIVER, 1, 0));
        addShapeless(new ItemStack(DYE, 3, EnumDyeColor.WHITE.getDyeDamage()), new ItemStack(BLOCK_BONE_CHUNK, 1, 0));

        add1x1Recipe(new ItemStack(ITEM_DUST_WITHER, 3, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        add1x1Recipe(new ItemStack(ITEM_CRYSTAL_RIME, 9, 0), new ItemStack(BLOCK_RIME, 1, 0));
        addBoatRecipe(new ItemStack(ITEM_BOAT_OBSIDIAN, 1, 0), new ItemStack(OBSIDIAN, 1, 0));

        addSmelting(new ItemStack(FOOD_MEAT_GHAST_COOKED, 1, 0), new ItemStack(FOOD_MEAT_GHAST_RAW, 1, 0), 0.5F);
        addSmelting(new ItemStack(FOOD_MAGMA_CREAM_CONGEALED, 1, 0), new ItemStack(MAGMA_CREAM, 1, 0), 0.5F);

        addSwordRecipe(new ItemStack(TOOL_SWORD_BONE, 1, 0), new ItemStack(GOLD_INGOT, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addPickaxeRecipe(new ItemStack(TOOL_PICKAXE_BONE, 1, 0), new ItemStack(GOLD_INGOT, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addShovelRecipe(new ItemStack(TOOL_SHOVEL_BONE, 1, 0), new ItemStack(GOLD_INGOT, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addAxeRecipe(new ItemStack(TOOL_AXE_BONE, 1, 0), new ItemStack(GOLD_INGOT, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addHoeRecipe(new ItemStack(TOOL_HOE_BONE, 1, 0), new ItemStack(GOLD_INGOT, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addShovelRecipe(new ItemStack(TOOL_SWORD_BONE, 1, 0), new ItemStack(GOLDEN_SWORD, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addShovelRecipe(new ItemStack(TOOL_PICKAXE_BONE, 1, 0), new ItemStack(GOLDEN_PICKAXE, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addShovelRecipe(new ItemStack(TOOL_SHOVEL_BONE, 1, 0), new ItemStack(GOLDEN_SHOVEL, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addShovelRecipe(new ItemStack(TOOL_AXE_BONE, 1, 0), new ItemStack(GOLDEN_AXE, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addShovelRecipe(new ItemStack(TOOL_HOE_BONE, 1, 0), new ItemStack(GOLDEN_HOE, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));

        addHelmetRecipe(new ItemStack(ARMOR_HELMET_BONE, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addChestplateRecipe(new ItemStack(ARMOR_CHESTPLATE_BONE, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addLeggingsRecipe(new ItemStack(ARMOR_LEGGINGS_BONE, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addBootsRecipe(new ItemStack(ARMOR_BOOTS_BONE, 1, 0), new ItemStack(ITEM_BONE_WITHER, 1, 0));
        addHelmetRecipe(new ItemStack(ARMOR_HELMET_HIDE_SALAMANDER, 1, 0), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 0));
        addChestplateRecipe(new ItemStack(ARMOR_CHESTPLATE_HIDE_SALAMANDER, 1, 0), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 0));
        addLeggingsRecipe(new ItemStack(ARMOR_LEGGINGS_HIDE_SALAMANDER, 1, 0), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 0));
        addBootsRecipe(new ItemStack(ARMOR_BOOTS_HIDE_SALAMANDER, 1, 0), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 0));
        addHelmetRecipe(NBTUtil.setTag(new ItemStack(ARMOR_HELMET_HIDE_SALAMANDER, 1, 0), variant), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 1));
        addChestplateRecipe(NBTUtil.setTag(new ItemStack(ARMOR_CHESTPLATE_HIDE_SALAMANDER, 1, 0), variant), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 1));
        addLeggingsRecipe(NBTUtil.setTag(new ItemStack(ARMOR_LEGGINGS_HIDE_SALAMANDER, 1, 0), variant), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 1));
        addBootsRecipe(NBTUtil.setTag(new ItemStack(ARMOR_BOOTS_HIDE_SALAMANDER, 1, 0), variant), new ItemStack(ITEM_HIDE_SALAMANDER, 1, 1));

        addBrewing(AWKWARD, ITEM_CRYSTAL_RIME, NORMAL_FREEZE);
        addBrewing(AWKWARD, ITEM_SPORE, NORMAL_SPORE);
        addBrewing(AWKWARD, FOOD_MEAT_GHAST_RAW, NORMAL_LOST);

        LOGGER.info("Recipe registration completed.");
    }

    private static void addShaped(ItemStack result, Object... input)
    {
        CraftingManager.getInstance().addRecipe(new ShapedOreRecipe(result, input));
    }

    private static void addShapeless(ItemStack result, Object... input)
    {
        CraftingManager.getInstance().addShapelessRecipe(result, input);
    }

    private static void addSmelting(ItemStack output, ItemStack input, float xp)
    {
        FurnaceRecipes.instance().addSmeltingRecipe(input, output, xp);
    }

    private static void addBrewing(PotionType input, Item reagent, PotionType output)
    {
        PotionHelper.registerPotionTypeConversion(input, new PotionHelper.ItemPredicateInstance(reagent), output);
    }

    private static void add1x1Recipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "#", '#', input);
    }

    private static void add1x3Recipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "#", "#", "#", '#', input);
    }

    private static void add2x2Recipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "##", "##", '#', input);
    }

    private static void add2x3Recipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "##", "##", "##", '#', input);
    }

    private static void add3x3Recipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "###", "###", "###", '#', input);
    }

    private static void addPaneRecipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "###", "###", '#', input);
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

    private static void addBoatRecipe(ItemStack result, ItemStack input)
    {
        addShaped(result, "# #", "###", '#', input);
    }

    private static void removeRecipe(Item output)
    {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        Iterator<IRecipe> iter = recipes.iterator();

        while(iter.hasNext())
        {
            ItemStack stack = iter.next().getRecipeOutput();

            if(!stack.isEmpty() && stack.getItem() == output)
            {
                iter.remove();
            }
        }

        LOGGER.info("Removed original recipe for %s", output.getRegistryName());
    }
}
