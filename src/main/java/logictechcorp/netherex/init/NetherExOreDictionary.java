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

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class NetherExOreDictionary
{
    public static void registerOres()
    {
        OreDictionary.registerOre("grass", new ItemStack(NetherExBlocks.GENESIS_GRASS));
        OreDictionary.registerOre("logWood", new ItemStack(NetherExBlocks.GENESIS_LOG));
        OreDictionary.registerOre("plankWood", new ItemStack(NetherExBlocks.GENESIS_PLANK));
        OreDictionary.registerOre("slabWood", new ItemStack(NetherExBlocks.GENESIS_PLANK_SLAB));
        OreDictionary.registerOre("stairWood", new ItemStack(NetherExBlocks.GENESIS_PLANK_STAIRS));
        OreDictionary.registerOre("fenceWood", new ItemStack(NetherExBlocks.GENESIS_PLANK_FENCE));
        OreDictionary.registerOre("fenceGateWood", new ItemStack(NetherExBlocks.GENESIS_PLANK_FENCE_GATE));
        OreDictionary.registerOre("fenceGateWood", new ItemStack(NetherExBlocks.GENESIS_PLANK_SLAB));
        OreDictionary.registerOre("treeSapling", new ItemStack(NetherExBlocks.GENESIS_SAPLING));
        OreDictionary.registerOre("treeLeaves", new ItemStack(NetherExBlocks.GENESIS_LEAF));
        OreDictionary.registerOre("ore", Blocks.GOLD_ORE);
        OreDictionary.registerOre("ore", Blocks.IRON_ORE);
        OreDictionary.registerOre("ore", Blocks.LAPIS_ORE);
        OreDictionary.registerOre("ore", Blocks.DIAMOND_ORE);
        OreDictionary.registerOre("ore", Blocks.REDSTONE_ORE);
        OreDictionary.registerOre("ore", Blocks.EMERALD_ORE);
        OreDictionary.registerOre("ore", Blocks.COAL_ORE);
        OreDictionary.registerOre("netherrack", new ItemStack(NetherExBlocks.GLOOMY_NETHERRACK));
        OreDictionary.registerOre("netherrack", new ItemStack(NetherExBlocks.LIVELY_NETHERRACK));
        OreDictionary.registerOre("netherrack", new ItemStack(NetherExBlocks.FIERY_NETHERRACK));
        OreDictionary.registerOre("netherrack", new ItemStack(NetherExBlocks.ICY_NETHERRACK));
        OreDictionary.registerOre("stoneBasalt", new ItemStack(NetherExBlocks.BASALT));
        OreDictionary.registerOre("stoneBasalt", new ItemStack(NetherExBlocks.SMOOTH_BASALT));
        OreDictionary.registerOre("stoneBasaltPolished", new ItemStack(NetherExBlocks.SMOOTH_BASALT));
        OreDictionary.registerOre("stoneBasalt", new ItemStack(NetherExBlocks.BASALT_BRICK));
        OreDictionary.registerOre("stoneBasalt", new ItemStack(NetherExBlocks.BASALT_PILLAR));
        OreDictionary.registerOre("stoneBasaltBrick", new ItemStack(NetherExBlocks.BASALT_BRICK));
        OreDictionary.registerOre("stoneBasaltBrick", new ItemStack(NetherExBlocks.BASALT_PILLAR));
        OreDictionary.registerOre("blockBrickNether", new ItemStack(Blocks.NETHER_BRICK));
        OreDictionary.registerOre("blockBrickNether", new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK));
        OreDictionary.registerOre("blockBrickNether", new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK));
        OreDictionary.registerOre("blockBrickNether", new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK));
        OreDictionary.registerOre("blockBrickNether", new ItemStack(NetherExBlocks.ICY_NETHER_BRICK));
        OreDictionary.registerOre("blockNetherBrick", new ItemStack(Blocks.NETHER_BRICK));
        OreDictionary.registerOre("blockNetherBrick", new ItemStack(NetherExBlocks.GLOOMY_NETHER_BRICK));
        OreDictionary.registerOre("blockNetherBrick", new ItemStack(NetherExBlocks.LIVELY_NETHER_BRICK));
        OreDictionary.registerOre("blockNetherBrick", new ItemStack(NetherExBlocks.FIERY_NETHER_BRICK));
        OreDictionary.registerOre("blockNetherBrick", new ItemStack(NetherExBlocks.ICY_NETHER_BRICK));
        OreDictionary.registerOre("blockGlass", new ItemStack(NetherExBlocks.SOUL_GLASS));
        OreDictionary.registerOre("paneGlass", new ItemStack(NetherExBlocks.SOUL_GLASS_PANE));
        OreDictionary.registerOre("ingotBrickNether", new ItemStack(NetherExItems.GLOOMY_NETHERBRICK));
        OreDictionary.registerOre("ingotBrickNether", new ItemStack(NetherExItems.LIVELY_NETHERBRICK));
        OreDictionary.registerOre("ingotBrickNether", new ItemStack(NetherExItems.FIERY_NETHERBRICK));
        OreDictionary.registerOre("ingotBrickNether", new ItemStack(NetherExItems.ICY_NETHERBRICK));
        OreDictionary.registerOre("oreQuartz", new ItemStack(NetherExBlocks.QUARTZ_ORE));
        OreDictionary.registerOre("obsidian", new ItemStack(NetherExBlocks.GLOWING_OBSIDIAN));
        OreDictionary.registerOre("obsidian", new ItemStack(NetherExBlocks.CRYING_OBSIDIAN));
        OreDictionary.registerOre("oreAmethyst", new ItemStack(NetherExBlocks.AMETHYST_ORE));
        OreDictionary.registerOre("blockAmethyst", new ItemStack(NetherExBlocks.AMETHYST_BLOCK));
        OreDictionary.registerOre("gemAmethyst", new ItemStack(NetherExItems.AMETHYST_CRYSTAL));
        OreDictionary.registerOre("oreRime", new ItemStack(NetherExBlocks.RIME_ORE));
        OreDictionary.registerOre("blockRime", new ItemStack(NetherExBlocks.RIME_BLOCK));
        OreDictionary.registerOre("gemRime", new ItemStack(NetherExItems.RIME_CRYSTAL));
        OreDictionary.registerOre("oreCobalt", new ItemStack(NetherExBlocks.COBALT_ORE));
        OreDictionary.registerOre("blockCobalt", new ItemStack(NetherExBlocks.COBALT_BLOCK));
        OreDictionary.registerOre("ingotCobalt", new ItemStack(NetherExItems.COBALT_INGOT));
        OreDictionary.registerOre("oreArdite", new ItemStack(NetherExBlocks.ARDITE_ORE));
        OreDictionary.registerOre("blockArdite", new ItemStack(NetherExBlocks.ARDITE_BLOCK));
        OreDictionary.registerOre("ingotArdite", new ItemStack(NetherExItems.ARDITE_INGOT));
        OreDictionary.registerOre("boneWithered", new ItemStack(NetherExItems.WITHER_BONE));
        OreDictionary.registerOre("boneWitheredBlazed", new ItemStack(NetherExItems.BLAZED_WITHER_BONE));
        OreDictionary.registerOre("boneWitheredFrosted", new ItemStack(NetherExItems.FROSTED_WITHER_BONE));
        OreDictionary.registerOre("dyeBlack", new ItemStack(NetherExItems.WITHER_DUST));
        OreDictionary.registerOre("listAllmeatraw", new ItemStack(NetherExItems.GHAST_MEAT_RAW));
        OreDictionary.registerOre("listAllghastraw", new ItemStack(NetherExItems.GHAST_MEAT_RAW));
        OreDictionary.registerOre("foodMeats", new ItemStack(NetherExItems.GHAST_MEAT_RAW));
        OreDictionary.registerOre("listAllmeatcooked", new ItemStack(NetherExItems.GHAST_MEAT_COOKED));
        OreDictionary.registerOre("listAllghastcooked", new ItemStack(NetherExItems.GHAST_MEAT_COOKED));
        OreDictionary.registerOre("foodMeats", new ItemStack(NetherExItems.GHAST_MEAT_COOKED));
        OreDictionary.registerOre("foodCongealedmagmacream", new ItemStack(NetherExItems.CONGEALED_MAGMA_CREAM));
        OreDictionary.registerOre("listAllmushroom", new ItemStack(NetherExItems.ENOKI_MUSHROOM));
        OreDictionary.registerOre("mushroomAny", new ItemStack(NetherExItems.ENOKI_MUSHROOM));
        OreDictionary.registerOre("cropEnokimushroom", new ItemStack(NetherExItems.ENOKI_MUSHROOM));
        OreDictionary.registerOre("listAllveggie", new ItemStack(NetherExItems.ENOKI_MUSHROOM));
    }
}
