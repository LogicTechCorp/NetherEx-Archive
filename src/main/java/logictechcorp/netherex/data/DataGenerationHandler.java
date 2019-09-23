/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.data;

import logictechcorp.libraryex.data.generator.loottable.BlockLootTableGenerator;
import logictechcorp.libraryex.data.generator.recipe.RecipeGenerator;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.block.NetherExBlocks;
import logictechcorp.netherex.item.NetherExItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(NetherEx.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerationHandler
{
    @SubscribeEvent
    public static void onDataGeneration(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();

        if(event.includeServer())
        {
            generator.addProvider(gatherBlockLootTables(generator));
            generator.addProvider(gatherRecipes(generator));
        }
    }

    private static IDataProvider gatherBlockLootTables(DataGenerator generator)
    {
        BlockLootTableGenerator lootTableGenerator = new BlockLootTableGenerator(generator, NetherEx.MOD_ID);

        for(Block block : ForgeRegistries.BLOCKS)
        {
            if(block.getRegistryName().getNamespace().equals(NetherEx.MOD_ID))
            {
                if(block == NetherExBlocks.QUARTZ_ORE)
                {
                    continue;
                }

                if(block.getLootTable() != LootTables.EMPTY)
                {
                    lootTableGenerator.addBasicLootTable(block);
                }
            }
        }

        lootTableGenerator.addOreLootTable(NetherExBlocks.AMETHYST_ORE, NetherExItems.AMETHYST_CRYSTAL).addOreLootTable(NetherExBlocks.RIME_ORE, NetherExItems.RIME_CRYSTAL);
        lootTableGenerator.addFragileLootTable(NetherExBlocks.SOUL_GLASS).addFragileLootTable(NetherExBlocks.SOUL_GLASS_PANE);
        return lootTableGenerator;
    }

    private static IDataProvider gatherRecipes(DataGenerator generator)
    {
        RecipeGenerator recipeGenerator = new RecipeGenerator(generator, NetherEx.MOD_ID);

        //Gloomy Netherbrick recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICKS, 4), NetherExItems.GLOOMY_NETHER_BRICK);
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB, 6), NetherExBlocks.GLOOMY_NETHER_BRICKS);
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICKS, 1), NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB);
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_WALL, 6), NetherExBlocks.GLOOMY_NETHER_BRICKS);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICKS, 1), NetherExBlocks.GLOOMY_NETHER_BRICK_WALL);
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_STAIRS, 8), NetherExBlocks.GLOOMY_NETHER_BRICKS);
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICKS, 3), NetherExBlocks.GLOOMY_NETHER_BRICK_STAIRS);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE, 6), NetherExBlocks.GLOOMY_NETHER_BRICKS, NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE_GATE, 6), NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB, NetherExBlocks.GLOOMY_NETHER_BRICKS);

        //Lively Netherbrick recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICKS, 4), NetherExItems.LIVELY_NETHER_BRICK);
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_SLAB, 6), NetherExBlocks.LIVELY_NETHER_BRICKS);
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICKS, 1), NetherExBlocks.LIVELY_NETHER_BRICK_SLAB);
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_WALL, 6), NetherExBlocks.LIVELY_NETHER_BRICKS);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICKS, 1), NetherExBlocks.LIVELY_NETHER_BRICK_WALL);
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_STAIRS, 8), NetherExBlocks.LIVELY_NETHER_BRICKS);
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICKS, 3), NetherExBlocks.LIVELY_NETHER_BRICK_STAIRS);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE, 6), NetherExBlocks.LIVELY_NETHER_BRICKS, NetherExBlocks.LIVELY_NETHER_BRICK_SLAB);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE_GATE, 6), NetherExBlocks.LIVELY_NETHER_BRICK_SLAB, NetherExBlocks.LIVELY_NETHER_BRICKS);

        //Fiery Netherbrick recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICKS, 4), NetherExItems.FIERY_NETHER_BRICK);
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_SLAB, 6), NetherExBlocks.FIERY_NETHER_BRICKS);
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICKS, 1), NetherExBlocks.FIERY_NETHER_BRICK_SLAB);
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_WALL, 6), NetherExBlocks.FIERY_NETHER_BRICKS);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICKS, 1), NetherExBlocks.FIERY_NETHER_BRICK_WALL);
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_STAIRS, 8), NetherExBlocks.FIERY_NETHER_BRICKS);
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICKS, 3), NetherExBlocks.FIERY_NETHER_BRICK_STAIRS);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_FENCE, 6), NetherExBlocks.FIERY_NETHER_BRICKS, NetherExBlocks.FIERY_NETHER_BRICK_SLAB);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_FENCE_GATE, 6), NetherExBlocks.FIERY_NETHER_BRICK_SLAB, NetherExBlocks.FIERY_NETHER_BRICKS);

        //Icy Netherbrick recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICKS, 4), NetherExItems.ICY_NETHER_BRICK);
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_SLAB, 6), NetherExBlocks.ICY_NETHER_BRICKS);
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICKS, 1), NetherExBlocks.ICY_NETHER_BRICK_SLAB);
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_WALL, 6), NetherExBlocks.ICY_NETHER_BRICKS);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICKS, 1), NetherExBlocks.ICY_NETHER_BRICK_WALL);
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_STAIRS, 8), NetherExBlocks.ICY_NETHER_BRICKS);
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICKS, 3), NetherExBlocks.ICY_NETHER_BRICK_STAIRS);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_FENCE, 6), NetherExBlocks.ICY_NETHER_BRICKS, NetherExBlocks.ICY_NETHER_BRICK_SLAB);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_FENCE_GATE, 6), NetherExBlocks.ICY_NETHER_BRICK_SLAB, NetherExBlocks.ICY_NETHER_BRICKS);

        //Soul Sandstone Recipes
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE, 4), Blocks.SOUL_SAND);
        //recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE_SLAB, 6), NetherExBlocks.SOUL_SANDSTONE);
        //recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE, 1), NetherExBlocks.SOUL_SANDSTONE_SLAB);
        //recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE_WALL, 6), NetherExBlocks.SOUL_SANDSTONE);
        //recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE, 1), NetherExBlocks.SOUL_SANDSTONE_WALL);
        //recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE_STAIRS, 8), NetherExBlocks.SOUL_SANDSTONE);
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE, 3), NetherExBlocks.SOUL_SANDSTONE_STAIRS);

        //Cut Soul Sandstone Recipes
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE, 4), NetherExBlocks.SOUL_SANDSTONE);
        //recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE_SLAB, 6), NetherExBlocks.CUT_SOUL_SANDSTONE);
        //recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE, 1), NetherExBlocks.CUT_SOUL_SANDSTONE_SLAB);
        //recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE_WALL, 6), NetherExBlocks.CUT_SOUL_SANDSTONE);
        //recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE, 1), NetherExBlocks.CUT_SOUL_SANDSTONE_WALL);
        //recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE_STAIRS, 8), NetherExBlocks.CUT_SOUL_SANDSTONE);
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE, 3), NetherExBlocks.CUT_SOUL_SANDSTONE_STAIRS);

        //Chiseled Soul Sandstone Recipes
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE, 2), NetherExBlocks.SOUL_SANDSTONE_SLAB);
        //recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE_SLAB, 6), NetherExBlocks.CHISELED_SOUL_SANDSTONE);
        //recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE, 1), NetherExBlocks.CHISELED_SOUL_SANDSTONE_SLAB);
        //recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE_WALL, 6), NetherExBlocks.CHISELED_SOUL_SANDSTONE);
        //recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE, 1), NetherExBlocks.CHISELED_SOUL_SANDSTONE_WALL);
        //recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE_STAIRS, 8), NetherExBlocks.CHISELED_SOUL_SANDSTONE);
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE, 3), NetherExBlocks.CHISELED_SOUL_SANDSTONE_STAIRS);

        //Smooth Soul Sandstone Recipes
        //recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE_SLAB, 6), NetherExBlocks.SMOOTH_SOUL_SANDSTONE);
        //recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE, 1), NetherExBlocks.SMOOTH_SOUL_SANDSTONE_SLAB);
        //recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE_WALL, 6), NetherExBlocks.SMOOTH_SOUL_SANDSTONE);
        //recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE, 1), NetherExBlocks.SMOOTH_SOUL_SANDSTONE_WALL);
        //recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE_STAIRS, 8), NetherExBlocks.SMOOTH_SOUL_SANDSTONE);
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE, 3), NetherExBlocks.SMOOTH_SOUL_SANDSTONE_STAIRS);

        //Basalt recipes
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.BASALT, 4), NetherExBlocks.SMOOTH_BASALT);
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.BASALT_SLAB, 6), NetherExBlocks.BASALT);
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.BASALT, 1), NetherExBlocks.BASALT_SLAB);
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.BASALT_WALL, 6), NetherExBlocks.BASALT);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.BASALT, 1), NetherExBlocks.BASALT_WALL);
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.BASALT_STAIRS, 8), NetherExBlocks.BASALT);
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.BASALT, 3), NetherExBlocks.BASALT_STAIRS);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_FENCE, 6), NetherExBlocks.BASALT, NetherExBlocks.BASALT_SLAB);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_FENCE_GATE, 6), NetherExBlocks.BASALT_SLAB, NetherExBlocks.BASALT);

        //Smooth Basalt recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT, 4), NetherExBlocks.BASALT);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT, 4), NetherExBlocks.BASALT_BRICK);
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT_SLAB, 6), NetherExBlocks.SMOOTH_BASALT);
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT, 1), NetherExBlocks.SMOOTH_BASALT_SLAB);
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT_WALL, 6), NetherExBlocks.SMOOTH_BASALT);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT, 1), NetherExBlocks.SMOOTH_BASALT_WALL);
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT_STAIRS, 8), NetherExBlocks.SMOOTH_BASALT);
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT, 3), NetherExBlocks.SMOOTH_BASALT_STAIRS);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT_FENCE, 6), NetherExBlocks.SMOOTH_BASALT, NetherExBlocks.SMOOTH_BASALT_SLAB);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT_FENCE_GATE, 6), NetherExBlocks.SMOOTH_BASALT_SLAB, NetherExBlocks.SMOOTH_BASALT);

        //Basalt Brick recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.BASALT_BRICK, 4), NetherExBlocks.SMOOTH_BASALT);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK, 4), NetherExBlocks.BASALT_PILLAR);
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.BASALT_BRICK_SLAB, 6), NetherExBlocks.BASALT_BRICK);
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.BASALT_BRICK, 1), NetherExBlocks.BASALT_BRICK_SLAB);
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK_WALL, 6), NetherExBlocks.BASALT_BRICK);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK, 1), NetherExBlocks.BASALT_BRICK_WALL);
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK_STAIRS, 8), NetherExBlocks.BASALT_BRICK);
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.BASALT_BRICK, 3), NetherExBlocks.BASALT_BRICK_STAIRS);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK_FENCE, 6), NetherExBlocks.BASALT_BRICK, NetherExBlocks.BASALT_BRICK_SLAB);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK_FENCE_GATE, 6), NetherExBlocks.BASALT_BRICK_SLAB, NetherExBlocks.BASALT_BRICK);

        //Basalt Pillar recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.BASALT_PILLAR, 4), NetherExBlocks.BASALT_BRICK);
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.BASALT_PILLAR_SLAB, 6), NetherExBlocks.BASALT_PILLAR);
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.BASALT_PILLAR, 1), NetherExBlocks.BASALT_PILLAR_SLAB);
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.BASALT_PILLAR_WALL, 6), NetherExBlocks.BASALT_PILLAR);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.BASALT_PILLAR, 1), NetherExBlocks.BASALT_PILLAR_WALL);
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.BASALT_PILLAR_STAIRS, 8), NetherExBlocks.BASALT_PILLAR);
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.BASALT_PILLAR, 3), NetherExBlocks.BASALT_PILLAR_STAIRS);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_PILLAR_FENCE, 6), NetherExBlocks.BASALT_PILLAR, NetherExBlocks.BASALT_PILLAR_SLAB);
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_PILLAR_FENCE_GATE, 6), NetherExBlocks.BASALT_PILLAR_SLAB, NetherExBlocks.BASALT_PILLAR);

        //Misc recipes
        recipeGenerator.addSurroundedRecipe(new ItemStack(NetherExItems.DULL_MIRROR), Items.GHAST_TEAR, Items.GOLD_INGOT);
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.SOUL_GLASS_PANE, 18), NetherExBlocks.SOUL_GLASS);
        recipeGenerator.add3x3Recipe(new ItemStack(NetherExBlocks.SOUL_GLASS, 3), NetherExBlocks.SOUL_GLASS_PANE);
        recipeGenerator.add3x3Recipe(new ItemStack(NetherExBlocks.RIME_BLOCK, 1), NetherExItems.RIME_CRYSTAL);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExItems.RIME_CRYSTAL, 9), NetherExBlocks.RIME_BLOCK);
        recipeGenerator.add3x3Recipe(new ItemStack(NetherExBlocks.AMETHYST_BLOCK, 1), NetherExItems.AMETHYST_CRYSTAL);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExItems.AMETHYST_CRYSTAL, 9), NetherExBlocks.AMETHYST_BLOCK);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExItems.RIME_AND_STEEL, 1), Items.FLINT_AND_STEEL, NetherExItems.RIME_CRYSTAL);
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExItems.RIME_AND_STEEL, 1), Items.IRON_INGOT, Items.FLINT, NetherExItems.RIME_CRYSTAL);

        return recipeGenerator;
    }
}
