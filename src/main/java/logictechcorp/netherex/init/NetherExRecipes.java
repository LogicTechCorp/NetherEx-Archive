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

package logictechcorp.netherex.init;

import logictechcorp.libraryex.utility.RecipeHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.handler.ConfigHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class NetherExRecipes
{
    public static void registerRecipes()
    {
        RecipeHelper.addSmelting(new ItemStack(NetherExItems.GLOOMY_NETHERBRICK), new ItemStack(NetherExBlocks.GLOOMY_NETHERRACK), 0.5F);
        RecipeHelper.addSmelting(new ItemStack(NetherExItems.LIVELY_NETHERBRICK), new ItemStack(NetherExBlocks.LIVELY_NETHERRACK), 0.5F);
        RecipeHelper.addSmelting(new ItemStack(NetherExItems.FIERY_NETHERBRICK), new ItemStack(NetherExBlocks.FIERY_NETHERRACK), 0.5F);
        RecipeHelper.addSmelting(new ItemStack(NetherExItems.ICY_NETHERBRICK), new ItemStack(NetherExBlocks.ICY_NETHERRACK), 0.5F);
        RecipeHelper.addSmelting(new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE), new ItemStack(NetherExBlocks.SOUL_SANDSTONE), 0.5F);
        RecipeHelper.addSmelting(new ItemStack(NetherExBlocks.SOUL_GLASS), new ItemStack(Blocks.SOUL_SAND), 0.5F);
        RecipeHelper.addSmelting(new ItemStack(Items.QUARTZ), new ItemStack(NetherExBlocks.QUARTZ_ORE), 0.0F);
        RecipeHelper.addSmelting(new ItemStack(NetherExItems.AMETHYST_CRYSTAL), new ItemStack(NetherExBlocks.AMETHYST_ORE), 0.0F);
        RecipeHelper.addSmelting(new ItemStack(NetherExItems.RIME_CRYSTAL), new ItemStack(NetherExBlocks.RIME_ORE), 0.0F);
        RecipeHelper.addSmelting(new ItemStack(NetherExItems.GHAST_MEAT_COOKED), new ItemStack(NetherExItems.GHAST_MEAT_RAW), 0.5F);
        RecipeHelper.addSmelting(new ItemStack(NetherExItems.CONGEALED_MAGMA_CREAM), new ItemStack(Items.MAGMA_CREAM), 0.5F);

        RecipeHelper.addBrewing(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(NetherExItems.RIME_CRYSTAL)), NetherExEffectTypes.NORMAL_FREEZE);
        RecipeHelper.addBrewing(PotionTypes.AWKWARD, NetherExItems.COOLMAR_SPIDER_FANG, NetherExEffectTypes.NORMAL_FROSTBITE);
        RecipeHelper.addBrewing(PotionTypes.AWKWARD, NetherExItems.SPORE, NetherExEffectTypes.NORMAL_SPORE);
        RecipeHelper.addBrewing(PotionTypes.AWKWARD, NetherExItems.GHAST_MEAT_RAW, NetherExEffectTypes.NORMAL_LOST);
    }

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterRecipes(RegistryEvent.Register<IRecipe> event)
        {
            //Replace the nether brick fence recipe as it conflict with our nether brick wall recipe
            GameRegistry.addShapedRecipe(new ResourceLocation("minecraft:nether_brick_fence"), null, new ItemStack(Blocks.NETHER_BRICK_FENCE, 6), "#*#", "#*#", '#', Blocks.NETHER_BRICK, '*', new ItemStack(Blocks.STONE_SLAB, 1, 6));

            event.getRegistry().registerAll(
                    //Gloomy Netherbrick recipes
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK, 4), NetherExItems.GLOOMY_NETHERBRICK),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB, 6), NetherExBlocks.GLOOMY_NETHER_BRICK),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK, 1), NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_WALL, 6), NetherExBlocks.GLOOMY_NETHER_BRICK),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK, 1), NetherExBlocks.GLOOMY_NETHER_BRICK_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_STAIRS, 8), NetherExBlocks.GLOOMY_NETHER_BRICK),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK, 3), NetherExBlocks.GLOOMY_NETHER_BRICK_STAIRS),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE, 6), NetherExBlocks.GLOOMY_NETHER_BRICK, NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE_GATE, 6), NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB, NetherExBlocks.GLOOMY_NETHER_BRICK),

                    //Lively Netherbrick recipes
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK, 4), NetherExItems.LIVELY_NETHERBRICK),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_SLAB, 6), NetherExBlocks.LIVELY_NETHER_BRICK),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK, 1), NetherExBlocks.LIVELY_NETHER_BRICK_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_WALL, 6), NetherExBlocks.LIVELY_NETHER_BRICK),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK, 1), NetherExBlocks.LIVELY_NETHER_BRICK_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_STAIRS, 8), NetherExBlocks.LIVELY_NETHER_BRICK),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK, 3), NetherExBlocks.LIVELY_NETHER_BRICK_STAIRS),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE, 6), NetherExBlocks.LIVELY_NETHER_BRICK, NetherExBlocks.LIVELY_NETHER_BRICK_SLAB),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE_GATE, 6), NetherExBlocks.LIVELY_NETHER_BRICK_SLAB, NetherExBlocks.LIVELY_NETHER_BRICK),

                    //Fiery Netherbrick recipes
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK, 4), NetherExItems.FIERY_NETHERBRICK),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_SLAB, 6), NetherExBlocks.FIERY_NETHER_BRICK),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK, 1), NetherExBlocks.FIERY_NETHER_BRICK_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_WALL, 6), NetherExBlocks.FIERY_NETHER_BRICK),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK, 1), NetherExBlocks.FIERY_NETHER_BRICK_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_STAIRS, 8), NetherExBlocks.FIERY_NETHER_BRICK),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK, 3), NetherExBlocks.FIERY_NETHER_BRICK_STAIRS),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_FENCE, 6), NetherExBlocks.FIERY_NETHER_BRICK, NetherExBlocks.FIERY_NETHER_BRICK_SLAB),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK_FENCE_GATE, 6), NetherExBlocks.FIERY_NETHER_BRICK_SLAB, NetherExBlocks.FIERY_NETHER_BRICK),

                    //Icy Netherbrick recipes
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.ICY_NETHER_BRICK, 4), NetherExItems.ICY_NETHERBRICK),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_SLAB, 6), NetherExBlocks.ICY_NETHER_BRICK),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.ICY_NETHER_BRICK, 1), NetherExBlocks.ICY_NETHER_BRICK_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_WALL, 6), NetherExBlocks.ICY_NETHER_BRICK),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.ICY_NETHER_BRICK, 1), NetherExBlocks.ICY_NETHER_BRICK_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_STAIRS, 8), NetherExBlocks.ICY_NETHER_BRICK),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.ICY_NETHER_BRICK, 3), NetherExBlocks.ICY_NETHER_BRICK_STAIRS),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_FENCE, 6), NetherExBlocks.ICY_NETHER_BRICK, NetherExBlocks.ICY_NETHER_BRICK_SLAB),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.ICY_NETHER_BRICK_FENCE_GATE, 6), NetherExBlocks.ICY_NETHER_BRICK_SLAB, NetherExBlocks.ICY_NETHER_BRICK),

                    //Soul Sandstone Recipes
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SOUL_SANDSTONE, 4), Blocks.SOUL_SAND),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SOUL_SANDSTONE_SLAB, 6), NetherExBlocks.SOUL_SANDSTONE),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SOUL_SANDSTONE, 1), NetherExBlocks.SOUL_SANDSTONE_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SOUL_SANDSTONE_WALL, 6), NetherExBlocks.SOUL_SANDSTONE),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.SOUL_SANDSTONE, 1), NetherExBlocks.SOUL_SANDSTONE_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.SOUL_SANDSTONE_STAIRS, 8), NetherExBlocks.SOUL_SANDSTONE),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SOUL_SANDSTONE, 3), NetherExBlocks.SOUL_SANDSTONE_STAIRS),

                    //Cut Soul Sandstone Recipes
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE, 4), NetherExBlocks.SOUL_SANDSTONE),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE_SLAB, 6), NetherExBlocks.CUT_SOUL_SANDSTONE),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE, 1), NetherExBlocks.CUT_SOUL_SANDSTONE_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE_WALL, 6), NetherExBlocks.CUT_SOUL_SANDSTONE),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE, 1), NetherExBlocks.CUT_SOUL_SANDSTONE_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE_STAIRS, 8), NetherExBlocks.CUT_SOUL_SANDSTONE),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.CUT_SOUL_SANDSTONE, 3), NetherExBlocks.CUT_SOUL_SANDSTONE_STAIRS),

                    //Chiseled Soul Sandstone Recipes
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE, 2), NetherExBlocks.SOUL_SANDSTONE_SLAB),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE_SLAB, 6), NetherExBlocks.CHISELED_SOUL_SANDSTONE),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE, 1), NetherExBlocks.CHISELED_SOUL_SANDSTONE_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE_WALL, 6), NetherExBlocks.CHISELED_SOUL_SANDSTONE),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE, 1), NetherExBlocks.CHISELED_SOUL_SANDSTONE_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE_STAIRS, 8), NetherExBlocks.CHISELED_SOUL_SANDSTONE),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.CHISELED_SOUL_SANDSTONE, 3), NetherExBlocks.CHISELED_SOUL_SANDSTONE_STAIRS),

                    //Smooth Soul Sandstone Recipes
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE_SLAB, 6), NetherExBlocks.SMOOTH_SOUL_SANDSTONE),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE, 1), NetherExBlocks.SMOOTH_SOUL_SANDSTONE_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE_WALL, 6), NetherExBlocks.SMOOTH_SOUL_SANDSTONE),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE, 1), NetherExBlocks.SMOOTH_SOUL_SANDSTONE_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE_STAIRS, 8), NetherExBlocks.SMOOTH_SOUL_SANDSTONE),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_SOUL_SANDSTONE, 3), NetherExBlocks.SMOOTH_SOUL_SANDSTONE_STAIRS),

                    //Basalt recipes
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT, 4), NetherExBlocks.SMOOTH_BASALT),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_SLAB, 6), NetherExBlocks.BASALT),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT, 1), NetherExBlocks.BASALT_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_WALL, 6), NetherExBlocks.BASALT),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT, 1), NetherExBlocks.BASALT_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_STAIRS, 8), NetherExBlocks.BASALT),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT, 3), NetherExBlocks.BASALT_STAIRS),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_FENCE, 6), NetherExBlocks.BASALT, NetherExBlocks.BASALT_SLAB),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_FENCE_GATE, 6), NetherExBlocks.BASALT_SLAB, NetherExBlocks.BASALT),

                    //Smooth Basalt recipes
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_BASALT, 4), NetherExBlocks.BASALT),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_BASALT, 4), NetherExBlocks.BASALT_BRICK),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_BASALT_SLAB, 6), NetherExBlocks.SMOOTH_BASALT),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_BASALT, 1), NetherExBlocks.SMOOTH_BASALT_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_BASALT_WALL, 6), NetherExBlocks.SMOOTH_BASALT),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_BASALT, 1), NetherExBlocks.SMOOTH_BASALT_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_BASALT_STAIRS, 8), NetherExBlocks.SMOOTH_BASALT),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_BASALT, 3), NetherExBlocks.SMOOTH_BASALT_STAIRS),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_BASALT_FENCE, 6), NetherExBlocks.SMOOTH_BASALT, NetherExBlocks.SMOOTH_BASALT_SLAB),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.SMOOTH_BASALT_FENCE_GATE, 6), NetherExBlocks.SMOOTH_BASALT_SLAB, NetherExBlocks.SMOOTH_BASALT),

                    //Basalt Brick recipes
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_BRICK, 4), NetherExBlocks.SMOOTH_BASALT),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_BRICK, 4), NetherExBlocks.BASALT_PILLAR),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_BRICK_SLAB, 6), NetherExBlocks.BASALT_BRICK),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_BRICK, 1), NetherExBlocks.BASALT_BRICK_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_BRICK_WALL, 6), NetherExBlocks.BASALT_BRICK),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_BRICK, 1), NetherExBlocks.BASALT_BRICK_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_BRICK_STAIRS, 8), NetherExBlocks.BASALT_BRICK),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_BRICK, 3), NetherExBlocks.BASALT_BRICK_STAIRS),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_BRICK_FENCE, 6), NetherExBlocks.BASALT_BRICK, NetherExBlocks.BASALT_BRICK_SLAB),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_BRICK_FENCE_GATE, 6), NetherExBlocks.BASALT_BRICK_SLAB, NetherExBlocks.BASALT_BRICK),

                    //Basalt Pillar recipes
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_PILLAR, 4), NetherExBlocks.BASALT_BRICK),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_PILLAR_SLAB, 6), NetherExBlocks.BASALT_PILLAR),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_PILLAR, 1), NetherExBlocks.BASALT_PILLAR_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_PILLAR_WALL, 6), NetherExBlocks.BASALT_PILLAR),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_PILLAR, 1), NetherExBlocks.BASALT_PILLAR_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_PILLAR_STAIRS, 8), NetherExBlocks.BASALT_PILLAR),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_PILLAR, 3), NetherExBlocks.BASALT_PILLAR_STAIRS),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_PILLAR_FENCE, 6), NetherExBlocks.BASALT_PILLAR, NetherExBlocks.BASALT_PILLAR_SLAB),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.BASALT_PILLAR_FENCE_GATE, 6), NetherExBlocks.BASALT_PILLAR_SLAB, NetherExBlocks.BASALT_PILLAR),

                    //Vanilla addition recipes
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.NETHER_BRICK_WALL, 6), Blocks.NETHER_BRICK),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(Blocks.NETHER_BRICK, 1), NetherExBlocks.NETHER_BRICK_WALL),
                    RecipeHelper.add3x1Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.RED_NETHER_BRICK_SLAB, 6), Blocks.RED_NETHER_BRICK),
                    RecipeHelper.add1x2Recipe(NetherEx.instance, new ItemStack(Blocks.RED_NETHER_BRICK, 1), NetherExBlocks.RED_NETHER_BRICK_SLAB),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.RED_NETHER_BRICK_WALL, 6), Blocks.RED_NETHER_BRICK),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(Blocks.RED_NETHER_BRICK, 1), NetherExBlocks.RED_NETHER_BRICK_WALL),
                    RecipeHelper.addStairRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.RED_NETHER_BRICK_STAIRS, 8), Blocks.RED_NETHER_BRICK),
                    RecipeHelper.add2x2Recipe(NetherEx.instance, new ItemStack(Blocks.RED_NETHER_BRICK, 3), NetherExBlocks.RED_NETHER_BRICK_STAIRS),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.RED_NETHER_BRICK_FENCE, 6), Blocks.RED_NETHER_BRICK, NetherExBlocks.RED_NETHER_BRICK_SLAB),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.RED_NETHER_BRICK_FENCE_GATE, 6), NetherExBlocks.RED_NETHER_BRICK_SLAB, Blocks.RED_NETHER_BRICK),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.QUARTZ_WALL, 6), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0)),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), NetherExBlocks.QUARTZ_WALL),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.QUARTZ_FENCE, 6), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), new ItemStack(Blocks.STONE_SLAB, 1, 7)),
                    RecipeHelper.addFenceRecipe(NetherEx.instance, new ItemStack(NetherExBlocks.QUARTZ_FENCE_GATE, 6), new ItemStack(Blocks.STONE_SLAB, 1, 7), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0)),

                    //Tool recipes
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.WITHERED_AMEDIAN_SWORD, 1), " # ", " * ", " @ ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.WITHERED_AMEDIAN_PICKAXE, 1), "#*#", " @ ", " @ ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.WITHERED_AMEDIAN_SHOVEL, 1), " @ ", " * ", " # ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.WITHERED_AMEDIAN_AXE, 1), " * ", "*@#", " @ ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.WITHERED_AMEDIAN_HOE, 1), "#* ", " @ ", " @ ", '#', NetherExBlocks.AMETHYST_BLOCK, '*', Blocks.OBSIDIAN, '@', NetherExItems.WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.WITHERED_AMEDIAN_HAMMER, 1), "#*#", "#@#", " @ ", '#', NetherExBlocks.AMETHYST_BLOCK, '*', Blocks.OBSIDIAN, '@', NetherExItems.WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.BLAZED_AMEDIAN_SWORD, 1), " # ", " * ", " @ ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.BLAZED_WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.BLAZED_AMEDIAN_PICKAXE, 1), "#*#", " @ ", " @ ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.BLAZED_WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.BLAZED_AMEDIAN_SHOVEL, 1), " @ ", " * ", " # ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.BLAZED_WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.BLAZED_AMEDIAN_AXE, 1), " * ", "*@#", " @ ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.BLAZED_WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.BLAZED_AMEDIAN_HOE, 1), "#* ", " @ ", " @ ", '#', NetherExBlocks.AMETHYST_BLOCK, '*', Blocks.OBSIDIAN, '@', NetherExItems.BLAZED_WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.BLAZED_AMEDIAN_HAMMER, 1), "#*#", "#@#", " @ ", '#', NetherExBlocks.AMETHYST_BLOCK, '*', Blocks.OBSIDIAN, '@', NetherExItems.BLAZED_WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.FROSTED_AMEDIAN_SWORD, 1), " # ", " * ", " @ ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.FROSTED_WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.FROSTED_AMEDIAN_PICKAXE, 1), "#*#", " @ ", " @ ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.FROSTED_WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.FROSTED_AMEDIAN_SHOVEL, 1), " @ ", " * ", " # ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.FROSTED_WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.FROSTED_AMEDIAN_AXE, 1), " * ", "*@#", " @ ", '#', Blocks.OBSIDIAN, '*', NetherExBlocks.AMETHYST_BLOCK, '@', NetherExItems.FROSTED_WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.FROSTED_AMEDIAN_HOE, 1), "#* ", " @ ", " @ ", '#', NetherExBlocks.AMETHYST_BLOCK, '*', Blocks.OBSIDIAN, '@', NetherExItems.FROSTED_WITHER_BONE),
                    RecipeHelper.addShapedRecipe(NetherEx.instance, new ItemStack(NetherExItems.FROSTED_AMEDIAN_HAMMER, 1), "#*#", "#@#", " @ ", '#', NetherExBlocks.AMETHYST_BLOCK, '*', Blocks.OBSIDIAN, '@', NetherExItems.FROSTED_WITHER_BONE),

                    //Armor recipes
                    RecipeHelper.addHelmetRecipe(NetherEx.instance, new ItemStack(NetherExItems.WITHER_BONE_HELMET, 1), NetherExItems.WITHER_BONE),
                    RecipeHelper.addChestplateRecipe(NetherEx.instance, new ItemStack(NetherExItems.WITHER_BONE_CHESTPLATE, 1), NetherExItems.WITHER_BONE),
                    RecipeHelper.addLeggingsRecipe(NetherEx.instance, new ItemStack(NetherExItems.WITHER_BONE_LEGGINGS, 1), NetherExItems.WITHER_BONE),
                    RecipeHelper.addBootsRecipe(NetherEx.instance, new ItemStack(NetherExItems.WITHER_BONE_BOOTS, 1), NetherExItems.WITHER_BONE),
                    RecipeHelper.addHelmetRecipe(NetherEx.instance, new ItemStack(NetherExItems.ORANGE_SALAMANDER_HIDE_HELMET, 1), NetherExItems.ORANGE_SALAMANDER_HIDE),
                    RecipeHelper.addChestplateRecipe(NetherEx.instance, new ItemStack(NetherExItems.ORANGE_SALAMANDER_HIDE_CHESTPLATE, 1), NetherExItems.ORANGE_SALAMANDER_HIDE),
                    RecipeHelper.addLeggingsRecipe(NetherEx.instance, new ItemStack(NetherExItems.ORANGE_SALAMANDER_HIDE_LEGGINGS, 1), NetherExItems.ORANGE_SALAMANDER_HIDE),
                    RecipeHelper.addBootsRecipe(NetherEx.instance, new ItemStack(NetherExItems.ORANGE_SALAMANDER_HIDE_BOOTS, 1), NetherExItems.ORANGE_SALAMANDER_HIDE),
                    RecipeHelper.addHelmetRecipe(NetherEx.instance, new ItemStack(NetherExItems.BLACK_SALAMANDER_HIDE_HELMET, 1), NetherExItems.BLACK_SALAMANDER_HIDE),
                    RecipeHelper.addChestplateRecipe(NetherEx.instance, new ItemStack(NetherExItems.BLACK_SALAMANDER_HIDE_CHESTPLATE, 1), NetherExItems.BLACK_SALAMANDER_HIDE),
                    RecipeHelper.addLeggingsRecipe(NetherEx.instance, new ItemStack(NetherExItems.BLACK_SALAMANDER_HIDE_LEGGINGS, 1), NetherExItems.BLACK_SALAMANDER_HIDE),
                    RecipeHelper.addBootsRecipe(NetherEx.instance, new ItemStack(NetherExItems.BLACK_SALAMANDER_HIDE_BOOTS, 1), NetherExItems.BLACK_SALAMANDER_HIDE),

                    //Repair Recipes
                    RecipeHelper.addRepairRecipe(NetherEx.instance, new ItemStack(NetherExItems.DULL_MIRROR), Items.GHAST_TEAR, -1),

                    //Misc recipes
                    RecipeHelper.addSurroundedRecipe(NetherEx.instance, new ItemStack(NetherExItems.DULL_MIRROR), Items.GHAST_TEAR, Items.GOLD_INGOT),
                    RecipeHelper.add3x2Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SOUL_GLASS_PANE, 18), NetherExBlocks.SOUL_GLASS),
                    RecipeHelper.add3x3Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.SOUL_GLASS, 3), NetherExBlocks.SOUL_GLASS_PANE),
                    RecipeHelper.add3x3Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.RIME_BLOCK, 1), NetherExItems.RIME_CRYSTAL),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExItems.RIME_CRYSTAL, 9), NetherExBlocks.RIME_BLOCK),
                    RecipeHelper.add3x3Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.AMETHYST_BLOCK, 1), NetherExItems.AMETHYST_CRYSTAL),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExItems.AMETHYST_CRYSTAL, 9), NetherExBlocks.AMETHYST_BLOCK),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(Items.DYE, 6, EnumDyeColor.WHITE.getDyeDamage()), NetherExBlocks.BONE_SLIVER),
                    RecipeHelper.add2x3Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BONE_SLIVER, 1), new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage())),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(Items.DYE, 3, EnumDyeColor.WHITE.getDyeDamage()), NetherExBlocks.BONE_CHUNK),
                    RecipeHelper.add1x3Recipe(NetherEx.instance, new ItemStack(NetherExBlocks.BONE_CHUNK, 1), new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage())),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExItems.WITHER_DUST, 3), NetherExItems.WITHER_BONE),
                    RecipeHelper.add1x3Recipe(NetherEx.instance, new ItemStack(NetherExItems.WITHER_BONE, 1), NetherExItems.WITHER_DUST),
                    RecipeHelper.addBoatRecipe(NetherEx.instance, new ItemStack(NetherExItems.OBSIDIAN_BOAT, 1), new ItemStack(Blocks.OBSIDIAN, 1)),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExItems.RIME_AND_STEEL, 1), Items.FLINT_AND_STEEL, NetherExItems.RIME_CRYSTAL),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExItems.RIME_AND_STEEL, 1), Items.IRON_INGOT, Items.FLINT, NetherExItems.RIME_CRYSTAL),
                    RecipeHelper.addShapelessRecipe(NetherEx.instance, new ItemStack(NetherExItems.FROST_POWDER, 3), NetherExItems.FROST_ROD),
                    RecipeHelper.addFilledCrossRecipe(NetherEx.instance, new ItemStack(NetherExItems.BLAZED_WITHER_BONE, 3), Items.BLAZE_POWDER, NetherExItems.WITHER_BONE),
                    RecipeHelper.addFilledCrossRecipe(NetherEx.instance, new ItemStack(NetherExItems.FROSTED_WITHER_BONE, 3), NetherExItems.FROST_ROD, NetherExItems.WITHER_BONE)
            );

            ConfigHandler.internalConfig.recipes.writeRecipesToGlobalConfigFolder = false;
        }
    }
}
