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

package nex.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import nex.block.BlockNetherrack;

import static net.minecraft.init.Blocks.SOUL_SAND;
import static net.minecraft.init.Items.MAGMA_CREAM;
import static net.minecraft.init.Items.QUARTZ;
import static net.minecraft.init.PotionTypes.AWKWARD;
import static nex.init.NetherExBlocks.*;
import static nex.init.NetherExEffectTypes.*;
import static nex.init.NetherExItems.*;

@SuppressWarnings("ConstantConditions")
public class NetherExRecipes
{
    public static void init()
    {
        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            addSmelting(new ItemStack(NetherExItems.NETHERBRICK, 1, type.ordinal()), new ItemStack(NETHERRACK, 1, type.ordinal()), 0.5F);
        }

        addSmelting(new ItemStack(SOUL_GLASS, 1, 0), new ItemStack(SOUL_SAND, 1, 0), 0.5F);
        addSmelting(new ItemStack(QUARTZ, 1, 0), new ItemStack(QUARTZ_ORE, 1, 0), 0.0F);
        addSmelting(new ItemStack(GEM, 1, 0), new ItemStack(GEM_ORE, 1, 0), 0.0F);
        addSmelting(new ItemStack(GEM, 1, 1), new ItemStack(GEM_ORE, 1, 1), 0.0F);
        addSmelting(new ItemStack(GHAST_MEAT_COOKED, 1, 0), new ItemStack(GHAST_MEAT_RAW, 1, 0), 0.5F);
        addSmelting(new ItemStack(CONGEALED_MAGMA_CREAM, 1, 0), new ItemStack(MAGMA_CREAM, 1, 0), 0.5F);

        addBrewing(AWKWARD, Ingredient.fromStacks(new ItemStack(GEM, 1, 1)), NORMAL_FREEZE);
        addBrewing(AWKWARD, BONE_SPIDER_FANG, NORMAL_FROSTBITE);
        addBrewing(AWKWARD, SPORE, NORMAL_SPORE);
        addBrewing(AWKWARD, GHAST_MEAT_RAW, NORMAL_LOST);
    }

    private static void addSmelting(ItemStack output, ItemStack input, float xp)
    {
        FurnaceRecipes.instance().addSmeltingRecipe(input, output, xp);
    }

    private static void addBrewing(PotionType input, Item reagent, PotionType output)
    {
        PotionHelper.addMix(input, reagent, output);
    }

    private static void addBrewing(PotionType input, Ingredient ingredient, PotionType output)
    {
        PotionHelper.addMix(input, ingredient, output);
    }
}
