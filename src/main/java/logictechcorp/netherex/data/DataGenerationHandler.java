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
                if(block == NetherExBlocks.QUARTZ_ORE.get())
                {
                    continue;
                }

                if(block.getLootTable() != LootTables.EMPTY)
                {
                    lootTableGenerator.addBasicLootTable(block);
                }
            }
        }

        lootTableGenerator.addOreLootTable(NetherExBlocks.RIME_ORE.get(), NetherExItems.RIME_CRYSTAL.get());
        lootTableGenerator.addFragileLootTable(NetherExBlocks.BROWN_ELDER_MUSHROOM_CAP.get()).addFragileLootTable(NetherExBlocks.RED_ELDER_MUSHROOM_CAP.get()).addFragileLootTable(NetherExBlocks.ELDER_MUSHROOM_STEM.get());
        lootTableGenerator.addFragileLootTable(NetherExBlocks.SOUL_GLASS.get()).addFragileLootTable(NetherExBlocks.SOUL_GLASS_PANE.get());
        return lootTableGenerator;
    }

    private static IDataProvider gatherRecipes(DataGenerator generator)
    {
        RecipeGenerator recipeGenerator = new RecipeGenerator(generator, NetherEx.MOD_ID);

        //Gloomy Netherbrick recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICKS.get(), 4), NetherExItems.GLOOMY_NETHER_BRICK.get());
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB.get(), 6), NetherExBlocks.GLOOMY_NETHER_BRICKS.get());
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICKS.get(), 1), NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB.get());
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_WALL.get(), 6), NetherExBlocks.GLOOMY_NETHER_BRICKS.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICKS.get(), 1), NetherExBlocks.GLOOMY_NETHER_BRICK_WALL.get());
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_STAIRS.get(), 8), NetherExBlocks.GLOOMY_NETHER_BRICKS.get());
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICKS.get(), 3), NetherExBlocks.GLOOMY_NETHER_BRICK_STAIRS.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE.get(), 6), NetherExBlocks.GLOOMY_NETHER_BRICKS.get(), NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE_GATE.get(), 6), NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB.get(), NetherExBlocks.GLOOMY_NETHER_BRICKS.get());

        //Lively Netherbrick recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICKS.get(), 4), NetherExItems.LIVELY_NETHER_BRICK.get());
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_SLAB.get(), 6), NetherExBlocks.LIVELY_NETHER_BRICKS.get());
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICKS.get(), 1), NetherExBlocks.LIVELY_NETHER_BRICK_SLAB.get());
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_WALL.get(), 6), NetherExBlocks.LIVELY_NETHER_BRICKS.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICKS.get(), 1), NetherExBlocks.LIVELY_NETHER_BRICK_WALL.get());
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_STAIRS.get(), 8), NetherExBlocks.LIVELY_NETHER_BRICKS.get());
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICKS.get(), 3), NetherExBlocks.LIVELY_NETHER_BRICK_STAIRS.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE.get(), 6), NetherExBlocks.LIVELY_NETHER_BRICKS.get(), NetherExBlocks.LIVELY_NETHER_BRICK_SLAB.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE_GATE.get(), 6), NetherExBlocks.LIVELY_NETHER_BRICK_SLAB.get(), NetherExBlocks.LIVELY_NETHER_BRICKS.get());

        //Fiery Netherbrick recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICKS.get(), 4), NetherExItems.FIERY_NETHER_BRICK.get());
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_SLAB.get(), 6), NetherExBlocks.FIERY_NETHER_BRICKS.get());
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICKS.get(), 1), NetherExBlocks.FIERY_NETHER_BRICK_SLAB.get());
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_WALL.get(), 6), NetherExBlocks.FIERY_NETHER_BRICKS.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICKS.get(), 1), NetherExBlocks.FIERY_NETHER_BRICK_WALL.get());
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_STAIRS.get(), 8), NetherExBlocks.FIERY_NETHER_BRICKS.get());
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICKS.get(), 3), NetherExBlocks.FIERY_NETHER_BRICK_STAIRS.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_FENCE.get(), 6), NetherExBlocks.FIERY_NETHER_BRICKS.get(), NetherExBlocks.FIERY_NETHER_BRICK_SLAB.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_FENCE_GATE.get(), 6), NetherExBlocks.FIERY_NETHER_BRICK_SLAB.get(), NetherExBlocks.FIERY_NETHER_BRICKS.get());

        //Icy Netherbrick recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICKS.get(), 4), NetherExItems.ICY_NETHER_BRICK.get());
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_SLAB.get(), 6), NetherExBlocks.ICY_NETHER_BRICKS.get());
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICKS.get(), 1), NetherExBlocks.ICY_NETHER_BRICK_SLAB.get());
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_WALL.get(), 6), NetherExBlocks.ICY_NETHER_BRICKS.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICKS.get(), 1), NetherExBlocks.ICY_NETHER_BRICK_WALL.get());
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_STAIRS.get(), 8), NetherExBlocks.ICY_NETHER_BRICKS.get());
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICKS.get(), 3), NetherExBlocks.ICY_NETHER_BRICK_STAIRS.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_FENCE.get(), 6), NetherExBlocks.ICY_NETHER_BRICKS.get(), NetherExBlocks.ICY_NETHER_BRICK_SLAB.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_FENCE_GATE.get(), 6), NetherExBlocks.ICY_NETHER_BRICK_SLAB.get(), NetherExBlocks.ICY_NETHER_BRICKS.get());

        //Soul Sandstone Recipes
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE.get(), 4), Blocks.SOUL_SAND);
        //recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE_SLAB.get(), 6), NetherExBlocks.SOUL_SANDSTONE.get());
        //recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE.get(), 1), NetherExBlocks.SOUL_SANDSTONE_SLAB.get());
        //recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE_WALL.get(), 6), NetherExBlocks.SOUL_SANDSTONE.get());
        //recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE.get(), 1), NetherExBlocks.SOUL_SANDSTONE_WALL.get());
        //recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE_STAIRS.get(), 8), NetherExBlocks.SOUL_SANDSTONE.get());
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.SOUL_SANDSTONE.get(), 3), NetherExBlocks.SOUL_SANDSTONE_STAIRS.get());

        //Cut Soul Sandstone Recipes
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE.get(), 4), NetherExBlocks.SOUL_SANDSTONE.get());
        //recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE_SLAB.get(), 6), NetherExBlocks.CUT_SOUL_SANDSTONE.get());
        //recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE.get(), 1), NetherExBlocks.CUT_SOUL_SANDSTONE_SLAB.get());
        //recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE_WALL.get(), 6), NetherExBlocks.CUT_SOUL_SANDSTONE.get());
        //recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE.get(), 1), NetherExBlocks.CUT_SOUL_SANDSTONE_WALL.get());
        //recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE_STAIRS.get(), 8), NetherExBlocks.CUT_SOUL_SANDSTONE.get());
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE.get(), 3), NetherExBlocks.CUT_SOUL_SANDSTONE_STAIRS.get());

        //Chiseled Soul Sandstone Recipes
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE.get(), 2), NetherExBlocks.SOUL_SANDSTONE_SLAB.get());
        //recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE_SLAB.get(), 6), NetherExBlocks.CHISELED_SOUL_SANDSTONE.get());
        //recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE.get(), 1), NetherExBlocks.CHISELED_SOUL_SANDSTONE_SLAB.get());
        //recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE_WALL.get(), 6), NetherExBlocks.CHISELED_SOUL_SANDSTONE.get());
        //recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE.get(), 1), NetherExBlocks.CHISELED_SOUL_SANDSTONE_WALL.get());
        //recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE_STAIRS.get(), 8), NetherExBlocks.CHISELED_SOUL_SANDSTONE.get());
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE.get(), 3), NetherExBlocks.CHISELED_SOUL_SANDSTONE_STAIRS.get());

        //Smooth Soul Sandstone Recipes
        //recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE_SLAB.get(), 6), NetherExBlocks.SMOOTH_SOUL_SANDSTONE.get());
        //recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE.get(), 1), NetherExBlocks.SMOOTH_SOUL_SANDSTONE_SLAB.get());
        //recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE_WALL.get(), 6), NetherExBlocks.SMOOTH_SOUL_SANDSTONE.get());
        //recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE.get(), 1), NetherExBlocks.SMOOTH_SOUL_SANDSTONE_WALL.get());
        //recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE_STAIRS.get(), 8), NetherExBlocks.SMOOTH_SOUL_SANDSTONE.get());
        //recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE.get(), 3), NetherExBlocks.SMOOTH_SOUL_SANDSTONE_STAIRS.get());

        //Basalt recipes
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.BASALT.get(), 4), NetherExBlocks.SMOOTH_BASALT.get());
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.BASALT_SLAB.get(), 6), NetherExBlocks.BASALT.get());
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.BASALT.get(), 1), NetherExBlocks.BASALT_SLAB.get());
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.BASALT_WALL.get(), 6), NetherExBlocks.BASALT.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.BASALT.get(), 1), NetherExBlocks.BASALT_WALL.get());
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.BASALT_STAIRS.get(), 8), NetherExBlocks.BASALT.get());
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.BASALT.get(), 3), NetherExBlocks.BASALT_STAIRS.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_FENCE.get(), 6), NetherExBlocks.BASALT.get(), NetherExBlocks.BASALT_SLAB.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_FENCE_GATE.get(), 6), NetherExBlocks.BASALT_SLAB.get(), NetherExBlocks.BASALT.get());

        //Smooth Basalt recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT.get(), 4), NetherExBlocks.BASALT.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT.get(), 4), NetherExBlocks.BASALT_BRICK.get());
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT_SLAB.get(), 6), NetherExBlocks.SMOOTH_BASALT.get());
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT.get(), 1), NetherExBlocks.SMOOTH_BASALT_SLAB.get());
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT_WALL.get(), 6), NetherExBlocks.SMOOTH_BASALT.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT.get(), 1), NetherExBlocks.SMOOTH_BASALT_WALL.get());
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT_STAIRS.get(), 8), NetherExBlocks.SMOOTH_BASALT.get());
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT.get(), 3), NetherExBlocks.SMOOTH_BASALT_STAIRS.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT_FENCE.get(), 6), NetherExBlocks.SMOOTH_BASALT.get(), NetherExBlocks.SMOOTH_BASALT_SLAB.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.SMOOTH_BASALT_FENCE_GATE.get(), 6), NetherExBlocks.SMOOTH_BASALT_SLAB.get(), NetherExBlocks.SMOOTH_BASALT.get());

        //Basalt Brick recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.BASALT_BRICK.get(), 4), NetherExBlocks.SMOOTH_BASALT.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK.get(), 4), NetherExBlocks.BASALT_PILLAR.get());
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.BASALT_BRICK_SLAB.get(), 6), NetherExBlocks.BASALT_BRICK.get());
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.BASALT_BRICK.get(), 1), NetherExBlocks.BASALT_BRICK_SLAB.get());
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK_WALL.get(), 6), NetherExBlocks.BASALT_BRICK.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK.get(), 1), NetherExBlocks.BASALT_BRICK_WALL.get());
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK_STAIRS.get(), 8), NetherExBlocks.BASALT_BRICK.get());
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.BASALT_BRICK.get(), 3), NetherExBlocks.BASALT_BRICK_STAIRS.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK_FENCE.get(), 6), NetherExBlocks.BASALT_BRICK.get(), NetherExBlocks.BASALT_BRICK_SLAB.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_BRICK_FENCE_GATE.get(), 6), NetherExBlocks.BASALT_BRICK_SLAB.get(), NetherExBlocks.BASALT_BRICK.get());

        //Basalt Pillar recipes
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.BASALT_PILLAR.get(), 4), NetherExBlocks.BASALT_BRICK.get());
        recipeGenerator.add3x1Recipe(new ItemStack(NetherExBlocks.BASALT_PILLAR_SLAB.get(), 6), NetherExBlocks.BASALT_PILLAR.get());
        recipeGenerator.add1x2Recipe(new ItemStack(NetherExBlocks.BASALT_PILLAR.get(), 1), NetherExBlocks.BASALT_PILLAR_SLAB.get());
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.BASALT_PILLAR_WALL.get(), 6), NetherExBlocks.BASALT_PILLAR.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExBlocks.BASALT_PILLAR.get(), 1), NetherExBlocks.BASALT_PILLAR_WALL.get());
        recipeGenerator.addStairRecipe(new ItemStack(NetherExBlocks.BASALT_PILLAR_STAIRS.get(), 8), NetherExBlocks.BASALT_PILLAR.get());
        recipeGenerator.add2x2Recipe(new ItemStack(NetherExBlocks.BASALT_PILLAR.get(), 3), NetherExBlocks.BASALT_PILLAR_STAIRS.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_PILLAR_FENCE.get(), 6), NetherExBlocks.BASALT_PILLAR.get(), NetherExBlocks.BASALT_PILLAR_SLAB.get());
        recipeGenerator.addFenceRecipe(new ItemStack(NetherExBlocks.BASALT_PILLAR_FENCE_GATE.get(), 6), NetherExBlocks.BASALT_PILLAR_SLAB.get(), NetherExBlocks.BASALT_PILLAR.get());

        //Misc recipes
        recipeGenerator.addSurroundedRecipe(new ItemStack(NetherExItems.DULL_MIRROR.get()), Items.GHAST_TEAR, Items.GOLD_INGOT);
        recipeGenerator.createRepairRecipe(new ItemStack(NetherExItems.DULL_MIRROR.get())).repairStack(new ItemStack(Items.GHAST_TEAR)).repairAmount(1).build();
        recipeGenerator.addWallRecipe(new ItemStack(NetherExBlocks.SOUL_GLASS_PANE.get(), 18), NetherExBlocks.SOUL_GLASS.get());
        recipeGenerator.add3x3Recipe(new ItemStack(NetherExBlocks.SOUL_GLASS.get(), 3), NetherExBlocks.SOUL_GLASS_PANE.get());
        recipeGenerator.add3x3Recipe(new ItemStack(NetherExBlocks.RIME_BLOCK.get(), 1), NetherExItems.RIME_CRYSTAL.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExItems.RIME_CRYSTAL.get(), 9), NetherExBlocks.RIME_BLOCK.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExItems.RIME_AND_STEEL.get(), 1), Items.FLINT_AND_STEEL, NetherExItems.RIME_CRYSTAL.get());
        recipeGenerator.addShapelessRecipe(new ItemStack(NetherExItems.RIME_AND_STEEL.get(), 1), Items.IRON_INGOT, Items.FLINT, NetherExItems.RIME_CRYSTAL.get());
        return recipeGenerator;
    }
}
