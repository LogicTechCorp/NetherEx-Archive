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

import logictechcorp.libraryex.data.loottable.ModLootTableProvider;
import logictechcorp.libraryex.data.recipe.ModRecipeProvider;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.block.NetherExBlocks;
import logictechcorp.netherex.entity.NetherExEntities;
import logictechcorp.netherex.entity.neutral.MogusEntity;
import logictechcorp.netherex.entity.neutral.SalamanderEntity;
import logictechcorp.netherex.item.NetherExItems;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
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
    public static void onDataGathered(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();

        if(event.includeServer())
        {
            generator.addProvider(gatherLootTables(generator));
            generator.addProvider(gatherRecipes(generator));
        }
    }

    private static IDataProvider gatherLootTables(DataGenerator generator)
    {
        ModLootTableProvider provider = new ModLootTableProvider(NetherEx.MOD_ID, generator);

        //Block loot tables
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
                    if(block instanceof SlabBlock)
                    {
                        provider.addSlabBlockLootTable(block);
                    }
                    else
                    {
                        provider.addBasicBlockLootTable(block);
                    }
                }
            }
        }

        provider.addOreBlockLootTable(NetherExBlocks.RIME_ORE.get(), NetherExItems.RIME_CRYSTAL.get());
        provider.addSilkBlockLootTable(NetherExBlocks.BROWN_ELDER_MUSHROOM_CAP.get());
        provider.addSilkBlockLootTable(NetherExBlocks.RED_ELDER_MUSHROOM_CAP.get());
        provider.addSilkBlockLootTable(NetherExBlocks.ELDER_MUSHROOM_STEM.get());
        provider.addSilkBlockLootTable(NetherExBlocks.SOUL_GLASS.get());
        provider.addSilkBlockLootTable(NetherExBlocks.SOUL_GLASS_PANE.get());

        //Entity loot tables
        provider.addEmptyEntityLootTable(NetherExEntities.MOGUS.get());
        provider.addBasicEntityLootTable(MogusEntity.Variant.BROWN.getLootTable(), NetherExItems.BROWN_ELDER_MUSHROOM.get());
        provider.addBasicEntityLootTable(MogusEntity.Variant.RED.getLootTable(), NetherExItems.RED_ELDER_MUSHROOM.get());
        provider.addBasicEntityLootTable(MogusEntity.Variant.WHITE.getLootTable(), NetherExItems.ENOKI_MUSHROOM.get());
        provider.addEmptyEntityLootTable(NetherExEntities.SALAMANDER.get());
        provider.addBasicEntityLootTable(SalamanderEntity.Variant.ORANGE.getLootTable(), NetherExItems.ORANGE_SALAMANDER_HIDE.get());
        provider.addBasicEntityLootTable(SalamanderEntity.Variant.BLACK.getLootTable(), NetherExItems.BLACK_SALAMANDER_HIDE.get());
        provider.addBasicEntityLootTable(NetherExEntities.SPINOUT.get(), Items.QUARTZ);
        provider.addEmptyEntityLootTable(NetherExEntities.SPORE.get());
        provider.addBasicEntityLootTable(NetherExEntities.SPORE_CREEPER.get(), NetherExItems.SPORE.get());
        provider.addBasicEntityLootTable(NetherExEntities.WIGHT.get(), NetherExItems.RIME_CRYSTAL.get());
        provider.addBasicEntityLootTable(NetherExEntities.COOLMAR_SPIDER.get(), NetherExItems.COOLMAR_SPIDER_FANG.get());
        return provider;
    }

    private static IDataProvider gatherRecipes(DataGenerator generator)
    {
        ModRecipeProvider provider = new ModRecipeProvider(NetherEx.MOD_ID, generator);

        //Gloomy Netherbrick recipes
        provider.addShapedRecipe(NetherExItems.GLOOMY_NETHER_BRICKS.get(), 4).pattern("##").pattern("##").key('#', NetherExItems.GLOOMY_NETHER_BRICK.get()).build("gloomy_nether_bricks");
        provider.addShapedRecipe(NetherExItems.GLOOMY_NETHER_BRICK_SLAB.get(), 6).pattern("###").key('#', NetherExItems.GLOOMY_NETHER_BRICKS.get()).build("gloomy_nether_brick_slab");
        provider.addShapedRecipe(NetherExItems.GLOOMY_NETHER_BRICK_STAIRS.get(), 4).pattern("#  ").pattern("## ").pattern("###").key('#', NetherExItems.GLOOMY_NETHER_BRICKS.get()).build("gloomy_nether_brick_stairs");
        provider.addShapedRecipe(NetherExItems.GLOOMY_NETHER_BRICK_WALL.get(), 6).pattern("###").pattern("###").key('#', NetherExItems.GLOOMY_NETHER_BRICKS.get()).build("gloomy_nether_brick_wall");
        provider.addShapedRecipe(NetherExItems.GLOOMY_NETHER_BRICK_FENCE.get(), 4).pattern("#*#").pattern("#*#").key('#', NetherExItems.GLOOMY_NETHER_BRICKS.get()).key('*', NetherExItems.GLOOMY_NETHER_BRICK_SLAB.get()).build("gloomy_nether_brick_fence");
        provider.addShapedRecipe(NetherExItems.GLOOMY_NETHER_BRICK_FENCE_GATE.get(), 2).pattern("#*#").pattern("#*#").key('#', NetherExItems.GLOOMY_NETHER_BRICK_SLAB.get()).key('*', NetherExItems.GLOOMY_NETHER_BRICKS.get()).build("gloomy_nether_brick_fence_gate");
        provider.addStonecutterRecipe(NetherExItems.GLOOMY_NETHER_BRICK_SLAB.get(), 2).ingredient(NetherExItems.GLOOMY_NETHER_BRICKS.get()).build("gloomy_nether_brick_slab_from_gloomy_nether_brick_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.GLOOMY_NETHER_BRICK_STAIRS.get(), 1).ingredient(NetherExItems.GLOOMY_NETHER_BRICKS.get()).build("gloomy_nether_brick_stairs_from_gloomy_nether_brick_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.GLOOMY_NETHER_BRICK_WALL.get(), 1).ingredient(NetherExItems.GLOOMY_NETHER_BRICKS.get()).build("gloomy_nether_brick_wall_from_gloomy_nether_brick_stonecutting");

        //Lively Netherbrick recipes
        provider.addShapedRecipe(NetherExItems.LIVELY_NETHER_BRICKS.get(), 4).pattern("##").pattern("##").key('#', NetherExItems.LIVELY_NETHER_BRICK.get()).build("lively_nether_bricks");
        provider.addShapedRecipe(NetherExItems.LIVELY_NETHER_BRICK_SLAB.get(), 6).pattern("###").key('#', NetherExItems.LIVELY_NETHER_BRICKS.get()).build("lively_nether_brick_slab");
        provider.addShapedRecipe(NetherExItems.LIVELY_NETHER_BRICK_STAIRS.get(), 4).pattern("#  ").pattern("## ").pattern("###").key('#', NetherExItems.LIVELY_NETHER_BRICKS.get()).build("lively_nether_brick_stairs");
        provider.addShapedRecipe(NetherExItems.LIVELY_NETHER_BRICK_WALL.get(), 6).pattern("###").pattern("###").key('#', NetherExItems.LIVELY_NETHER_BRICKS.get()).build("lively_nether_brick_wall");
        provider.addShapedRecipe(NetherExItems.LIVELY_NETHER_BRICK_FENCE.get(), 4).pattern("#*#").pattern("#*#").key('#', NetherExItems.LIVELY_NETHER_BRICKS.get()).key('*', NetherExItems.LIVELY_NETHER_BRICK_SLAB.get()).build("lively_nether_brick_fence");
        provider.addShapedRecipe(NetherExItems.LIVELY_NETHER_BRICK_FENCE_GATE.get(), 2).pattern("#*#").pattern("#*#").key('#', NetherExItems.LIVELY_NETHER_BRICK_SLAB.get()).key('*', NetherExItems.LIVELY_NETHER_BRICKS.get()).build("lively_nether_brick_fence_gate");
        provider.addStonecutterRecipe(NetherExItems.LIVELY_NETHER_BRICK_SLAB.get(), 2).ingredient(NetherExItems.LIVELY_NETHER_BRICKS.get()).build("lively_nether_brick_slab_from_lively_nether_brick_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.LIVELY_NETHER_BRICK_STAIRS.get(), 1).ingredient(NetherExItems.LIVELY_NETHER_BRICKS.get()).build("lively_nether_brick_stairs_from_lively_nether_brick_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.LIVELY_NETHER_BRICK_WALL.get(), 1).ingredient(NetherExItems.LIVELY_NETHER_BRICKS.get()).build("lively_nether_brick_wall_from_lively_nether_brick_stonecutting");

        //Fiery Netherbrick recipes
        provider.addShapedRecipe(NetherExItems.FIERY_NETHER_BRICKS.get(), 4).pattern("##").pattern("##").key('#', NetherExItems.FIERY_NETHER_BRICK.get()).build("fiery_nether_bricks");
        provider.addShapedRecipe(NetherExItems.FIERY_NETHER_BRICK_SLAB.get(), 6).pattern("###").key('#', NetherExItems.FIERY_NETHER_BRICKS.get()).build("fiery_nether_brick_slab");
        provider.addShapedRecipe(NetherExItems.FIERY_NETHER_BRICK_STAIRS.get(), 4).pattern("#  ").pattern("## ").pattern("###").key('#', NetherExItems.FIERY_NETHER_BRICKS.get()).build("fiery_nether_brick_stairs");
        provider.addShapedRecipe(NetherExItems.FIERY_NETHER_BRICK_WALL.get(), 6).pattern("###").pattern("###").key('#', NetherExItems.FIERY_NETHER_BRICKS.get()).build("fiery_nether_brick_wall");
        provider.addShapedRecipe(NetherExItems.FIERY_NETHER_BRICK_FENCE.get(), 4).pattern("#*#").pattern("#*#").key('#', NetherExItems.FIERY_NETHER_BRICKS.get()).key('*', NetherExItems.FIERY_NETHER_BRICK_SLAB.get()).build("fiery_nether_brick_fence");
        provider.addShapedRecipe(NetherExItems.FIERY_NETHER_BRICK_FENCE_GATE.get(), 2).pattern("#*#").pattern("#*#").key('#', NetherExItems.FIERY_NETHER_BRICK_SLAB.get()).key('*', NetherExItems.FIERY_NETHER_BRICKS.get()).build("fiery_nether_brick_fence_gate");
        provider.addStonecutterRecipe(NetherExItems.FIERY_NETHER_BRICK_SLAB.get(), 2).ingredient(NetherExItems.FIERY_NETHER_BRICKS.get()).build("fiery_nether_brick_slab_from_fiery_nether_brick_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.FIERY_NETHER_BRICK_STAIRS.get(), 1).ingredient(NetherExItems.FIERY_NETHER_BRICKS.get()).build("fiery_nether_brick_stairs_from_fiery_nether_brick_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.FIERY_NETHER_BRICK_WALL.get(), 1).ingredient(NetherExItems.FIERY_NETHER_BRICKS.get()).build("fiery_nether_brick_wall_from_fiery_nether_brick_stonecutting");

        //Icy Netherbrick recipes
        provider.addShapedRecipe(NetherExItems.ICY_NETHER_BRICKS.get(), 4).pattern("##").pattern("##").key('#', NetherExItems.ICY_NETHER_BRICK.get()).build("icy_nether_bricks");
        provider.addShapedRecipe(NetherExItems.ICY_NETHER_BRICK_SLAB.get(), 6).pattern("###").key('#', NetherExItems.ICY_NETHER_BRICKS.get()).build("icy_nether_brick_slab");
        provider.addShapedRecipe(NetherExItems.ICY_NETHER_BRICK_STAIRS.get(), 4).pattern("#  ").pattern("## ").pattern("###").key('#', NetherExItems.ICY_NETHER_BRICKS.get()).build("icy_nether_brick_stairs");
        provider.addShapedRecipe(NetherExItems.ICY_NETHER_BRICK_WALL.get(), 6).pattern("###").pattern("###").key('#', NetherExItems.ICY_NETHER_BRICKS.get()).build("icy_nether_brick_wall");
        provider.addShapedRecipe(NetherExItems.ICY_NETHER_BRICK_FENCE.get(), 4).pattern("#*#").pattern("#*#").key('#', NetherExItems.ICY_NETHER_BRICKS.get()).key('*', NetherExItems.ICY_NETHER_BRICK_SLAB.get()).build("icy_nether_brick_fence");
        provider.addShapedRecipe(NetherExItems.ICY_NETHER_BRICK_FENCE_GATE.get(), 2).pattern("#*#").pattern("#*#").key('#', NetherExItems.ICY_NETHER_BRICK_SLAB.get()).key('*', NetherExItems.ICY_NETHER_BRICKS.get()).build("icy_nether_brick_fence_gate");
        provider.addStonecutterRecipe(NetherExItems.ICY_NETHER_BRICK_SLAB.get(), 2).ingredient(NetherExItems.ICY_NETHER_BRICKS.get()).build("icy_nether_brick_slab_from_icy_nether_brick_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.ICY_NETHER_BRICK_STAIRS.get(), 1).ingredient(NetherExItems.ICY_NETHER_BRICKS.get()).build("icy_nether_brick_stairs_from_icy_nether_brick_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.ICY_NETHER_BRICK_WALL.get(), 1).ingredient(NetherExItems.ICY_NETHER_BRICKS.get()).build("icy_nether_brick_wall_from_icy_nether_brick_stonecutting");

        //Basalt recipes
        provider.addShapedRecipe(NetherExItems.BASALT_SLAB.get(), 6).pattern("###").key('#', NetherExItems.BASALT.get()).build("basalt_slab");
        provider.addShapedRecipe(NetherExItems.BASALT_STAIRS.get(), 4).pattern("#  ").pattern("## ").pattern("###").key('#', NetherExItems.BASALT.get()).build("basalt_stairs");
        provider.addShapedRecipe(NetherExItems.BASALT_WALL.get(), 6).pattern("###").pattern("###").key('#', NetherExItems.BASALT.get()).build("basalt_wall");
        provider.addShapedRecipe(NetherExItems.BASALT_FENCE.get(), 4).pattern("#*#").pattern("#*#").key('#', NetherExItems.BASALT.get()).key('*', NetherExItems.BASALT_SLAB.get()).build("basalt_fence");
        provider.addShapedRecipe(NetherExItems.BASALT_FENCE_GATE.get(), 2).pattern("#*#").pattern("#*#").key('#', NetherExItems.BASALT_SLAB.get()).key('*', NetherExItems.BASALT.get()).build("basalt_fence_gate");
        provider.addStonecutterRecipe(NetherExItems.BASALT_SLAB.get(), 2).ingredient(NetherExItems.BASALT.get()).build("basalt_slab_from_basalt_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.BASALT_STAIRS.get(), 1).ingredient(NetherExItems.BASALT.get()).build("basalt_stairs_from_basalt_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.BASALT_WALL.get(), 1).ingredient(NetherExItems.BASALT.get()).build("basalt_wall_from_basalt_stonecutting");

        //Smooth Basalt recipes
        provider.addShapedRecipe(NetherExItems.SMOOTH_BASALT.get(), 4).pattern("##").pattern("##").key('#', NetherExItems.BASALT.get()).build("smooth_basalt");
        provider.addShapedRecipe(NetherExItems.SMOOTH_BASALT_SLAB.get(), 6).pattern("###").key('#', NetherExItems.SMOOTH_BASALT.get()).build("smooth_basalt_slab");
        provider.addShapedRecipe(NetherExItems.SMOOTH_BASALT_STAIRS.get(), 4).pattern("#  ").pattern("## ").pattern("###").key('#', NetherExItems.SMOOTH_BASALT.get()).build("smooth_basalt_stairs");
        provider.addShapedRecipe(NetherExItems.SMOOTH_BASALT_WALL.get(), 6).pattern("###").pattern("###").key('#', NetherExItems.SMOOTH_BASALT.get()).build("smooth_basalt_wall");
        provider.addShapedRecipe(NetherExItems.SMOOTH_BASALT_FENCE.get(), 4).pattern("#*#").pattern("#*#").key('#', NetherExItems.SMOOTH_BASALT.get()).key('*', NetherExItems.SMOOTH_BASALT_SLAB.get()).build("smooth_basalt_fence");
        provider.addShapedRecipe(NetherExItems.SMOOTH_BASALT_FENCE_GATE.get(), 2).pattern("#*#").pattern("#*#").key('#', NetherExItems.SMOOTH_BASALT_SLAB.get()).key('*', NetherExItems.SMOOTH_BASALT.get()).build("smooth_basalt_fence_gate");
        provider.addStonecutterRecipe(NetherExItems.SMOOTH_BASALT_SLAB.get(), 2).ingredient(NetherExItems.SMOOTH_BASALT.get()).build("smooth_basalt_slab_from_smooth_basalt_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.SMOOTH_BASALT_STAIRS.get(), 1).ingredient(NetherExItems.SMOOTH_BASALT.get()).build("smooth_basalt_stairs_from_smooth_basalt_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.SMOOTH_BASALT_WALL.get(), 1).ingredient(NetherExItems.SMOOTH_BASALT.get()).build("smooth_basalt_wall_from_smooth_basalt_stonecutting");

        //Basalt Brick recipes
        provider.addShapedRecipe(NetherExItems.BASALT_BRICK.get(), 4).pattern("##").pattern("##").key('#', NetherExItems.SMOOTH_BASALT.get()).build("basalt_brick");
        provider.addShapedRecipe(NetherExItems.BASALT_BRICK_SLAB.get(), 6).pattern("###").key('#', NetherExItems.BASALT_BRICK.get()).build("basalt_brick_slab");
        provider.addShapedRecipe(NetherExItems.BASALT_BRICK_STAIRS.get(), 4).pattern("#  ").pattern("## ").pattern("###").key('#', NetherExItems.BASALT_BRICK.get()).build("basalt_brick_stairs");
        provider.addShapedRecipe(NetherExItems.BASALT_BRICK_WALL.get(), 6).pattern("###").pattern("###").key('#', NetherExItems.BASALT_BRICK.get()).build("basalt_brick_wall");
        provider.addShapedRecipe(NetherExItems.BASALT_BRICK_FENCE.get(), 4).pattern("#*#").pattern("#*#").key('#', NetherExItems.BASALT_BRICK.get()).key('*', NetherExItems.BASALT_BRICK_SLAB.get()).build("basalt_brick_fence");
        provider.addShapedRecipe(NetherExItems.BASALT_BRICK_FENCE_GATE.get(), 2).pattern("#*#").pattern("#*#").key('#', NetherExItems.BASALT_BRICK_SLAB.get()).key('*', NetherExItems.BASALT_BRICK.get()).build("basalt_brick_fence_gate");
        provider.addStonecutterRecipe(NetherExItems.BASALT_BRICK_SLAB.get(), 2).ingredient(NetherExItems.BASALT_BRICK.get()).build("basalt_brick_slab_from_basalt_brick_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.BASALT_BRICK_STAIRS.get(), 1).ingredient(NetherExItems.BASALT_BRICK.get()).build("basalt_brick_stairs_from_basalt_brick_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.BASALT_BRICK_WALL.get(), 1).ingredient(NetherExItems.BASALT_BRICK.get()).build("basalt_brick_wall_from_basalt_brick_stonecutting");

        //Basalt Pillar recipes
        provider.addShapedRecipe(NetherExItems.BASALT_PILLAR.get(), 4).pattern("##").pattern("##").key('#', NetherExItems.BASALT_BRICK.get()).build("basalt_pillar");
        provider.addShapedRecipe(NetherExItems.BASALT_PILLAR_SLAB.get(), 6).pattern("###").key('#', NetherExItems.BASALT_PILLAR.get()).build("basalt_pillar_slab");
        provider.addShapedRecipe(NetherExItems.BASALT_PILLAR_STAIRS.get(), 4).pattern("#  ").pattern("## ").pattern("###").key('#', NetherExItems.BASALT_PILLAR.get()).build("basalt_pillar_stairs");
        provider.addShapedRecipe(NetherExItems.BASALT_PILLAR_WALL.get(), 6).pattern("###").pattern("###").key('#', NetherExItems.BASALT_PILLAR.get()).build("basalt_pillar_wall");
        provider.addShapedRecipe(NetherExItems.BASALT_PILLAR_FENCE.get(), 4).pattern("#*#").pattern("#*#").key('#', NetherExItems.BASALT_PILLAR.get()).key('*', NetherExItems.BASALT_PILLAR_SLAB.get()).build("basalt_pillar_fence");
        provider.addShapedRecipe(NetherExItems.BASALT_PILLAR_FENCE_GATE.get(), 2).pattern("#*#").pattern("#*#").key('#', NetherExItems.BASALT_PILLAR_SLAB.get()).key('*', NetherExItems.BASALT_PILLAR.get()).build("basalt_pillar_fence_gate");
        provider.addStonecutterRecipe(NetherExItems.BASALT_PILLAR_SLAB.get(), 2).ingredient(NetherExItems.BASALT_PILLAR.get()).build("basalt_pillar_slab_from_basalt_pillar_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.BASALT_PILLAR_STAIRS.get(), 1).ingredient(NetherExItems.BASALT_PILLAR.get()).build("basalt_pillar_stairs_from_basalt_pillar_stonecutting");
        provider.addStonecutterRecipe(NetherExItems.BASALT_PILLAR_WALL.get(), 1).ingredient(NetherExItems.BASALT_PILLAR.get()).build("basalt_pillar_wall_from_basalt_pillar_stonecutting");

        //Misc Furnace recipes
        provider.addFurnaceRecipe(NetherExItems.SOUL_GLASS.get()).ingredient(Items.SOUL_SAND).cookTime(200).experience(0.1F).build("soul_glass");
        provider.addFurnaceRecipe(NetherExItems.COOKED_GHAST_MEAT.get()).ingredient(NetherExItems.GHAST_MEAT.get()).cookTime(200).experience(0.35F).build("cooked_ghast_meat");
        provider.addSmokerRecipe(NetherExItems.COOKED_GHAST_MEAT.get()).ingredient(NetherExItems.GHAST_MEAT.get()).cookTime(100).experience(0.35F).build("cooked_ghast_meat_from_smoking");
        provider.addCampfireRecipe(NetherExItems.COOKED_GHAST_MEAT.get()).ingredient(NetherExItems.GHAST_MEAT.get()).cookTime(600).experience(0.35F).build("cooked_ghast_meat_from_campfire_cooking");

        //Misc recipes
        provider.addShapedRecipe(NetherExItems.DULL_MIRROR.get(), 1).pattern("###").pattern("#*#").pattern("###").key('#', Items.GOLD_INGOT).key('*', Items.GHAST_TEAR).build("dull_mirror");
        provider.addRepairRecipe(NetherExItems.DULL_MIRROR.get(), 1).ingredient(Items.GHAST_TEAR).build("dull_mirror_repair");
        provider.addShapedRecipe(NetherExItems.SOUL_GLASS_PANE.get(), 6).pattern("###").pattern("###").key('#', NetherExItems.SOUL_GLASS.get()).build("soul_glass_pane");
        provider.addShapedRecipe(NetherExItems.RIME_BLOCK.get(), 1).pattern("###").pattern("###").pattern("###").key('#', NetherExItems.RIME_CRYSTAL.get()).build("rime_block");
        provider.addShapelessRecipe(NetherExItems.RIME_CRYSTAL.get(), 9).ingredient(NetherExItems.RIME_BLOCK.get(), 1).build("rime_crystal_from_rime_block");
        provider.addShapelessRecipe(NetherExItems.RIME_AND_STEEL.get(), 1).ingredient(Items.FLINT, 1).ingredient(Items.IRON_INGOT, 1).ingredient(NetherExItems.RIME_CRYSTAL.get(), 1).build("rime_and_steel");
        provider.addShapelessRecipe(NetherExItems.RIME_AND_STEEL.get(), 1).ingredient(Items.FLINT_AND_STEEL, 1).ingredient(NetherExItems.RIME_CRYSTAL.get(), 1).build("rime_and_steel_from_flint_and_steel");
        return provider;
    }
}
