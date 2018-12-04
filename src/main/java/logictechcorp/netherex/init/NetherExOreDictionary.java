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

package logictechcorp.netherex.init;

import logictechcorp.netherex.block.BlockBasalt;
import logictechcorp.netherex.block.BlockNetherrack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static logictechcorp.netherex.init.NetherExBlocks.*;
import static logictechcorp.netherex.init.NetherExItems.*;

public class NetherExOreDictionary
{
    public static void registerOres()
    {
        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            OreDictionary.registerOre("stoneBasalt", new ItemStack(BASALT, 1, type.ordinal()));

            if(type.ordinal() == 1)
            {
                OreDictionary.registerOre("stoneBasaltPolished", new ItemStack(BASALT, 1, type.ordinal()));
            }
            else if(type.ordinal() == 2 || type.ordinal() == 3)
            {
                OreDictionary.registerOre("stoneBasaltBrick", new ItemStack(BASALT, 1, type.ordinal()));
            }
        }

        OreDictionary.registerOre("blockGlass", new ItemStack(SOUL_GLASS));
        OreDictionary.registerOre("paneGlass", new ItemStack(SOUL_GLASS_PANE));

        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            OreDictionary.registerOre("netherrack", new ItemStack(NETHERRACK, 1, type.ordinal()));
            OreDictionary.registerOre("ingotBrickNether", new ItemStack(NetherExItems.NETHERBRICK, 1, type.ordinal()));
            OreDictionary.registerOre("oreQuartz", new ItemStack(QUARTZ_ORE, 1, type.ordinal()));
        }

        OreDictionary.registerOre("obsidian", new ItemStack(GLOWING_OBSIDIAN));
        OreDictionary.registerOre("obsidian", new ItemStack(CRYING_OBSIDIAN));
        OreDictionary.registerOre("oreAmethyst", new ItemStack(AMETHYST_ORE));
        OreDictionary.registerOre("blockAmethyst", new ItemStack(AMETHYST_BLOCK));
        OreDictionary.registerOre("gemAmethyst", new ItemStack(AMETHYST_CRYSTAL));
        OreDictionary.registerOre("oreRime", new ItemStack(RIME_ORE));
        OreDictionary.registerOre("blockRime", new ItemStack(RIME_BLOCK));
        OreDictionary.registerOre("gemRime", new ItemStack(RIME_CRYSTAL));
        OreDictionary.registerOre("oreCobalt", new ItemStack(COBALT_ORE));
        OreDictionary.registerOre("blockCobalt", new ItemStack(COBALT_BLOCK));
        OreDictionary.registerOre("ingotCobalt", new ItemStack(COBALT_INGOT));
        OreDictionary.registerOre("oreArdite", new ItemStack(ARDITE_ORE));
        OreDictionary.registerOre("blockArdite", new ItemStack(ARDITE_BLOCK));
        OreDictionary.registerOre("ingotArdite", new ItemStack(ARDITE_INGOT));
        OreDictionary.registerOre("boneWithered", new ItemStack(WITHER_BONE));
        OreDictionary.registerOre("boneWitheredBlazed", new ItemStack(BLAZED_WITHER_BONE));
        OreDictionary.registerOre("boneWitheredFrosted", new ItemStack(FROSTED_WITHER_BONE));
        OreDictionary.registerOre("dyeBlack", new ItemStack(WITHER_DUST));
        OreDictionary.registerOre("listAllmeatraw", new ItemStack(GHAST_MEAT_RAW));
        OreDictionary.registerOre("listAllghastraw", new ItemStack(GHAST_MEAT_RAW));
        OreDictionary.registerOre("foodMeats", new ItemStack(GHAST_MEAT_RAW));
        OreDictionary.registerOre("listAllmeatcooked", new ItemStack(GHAST_MEAT_COOKED));
        OreDictionary.registerOre("listAllghastcooked", new ItemStack(GHAST_MEAT_COOKED));
        OreDictionary.registerOre("foodMeats", new ItemStack(GHAST_MEAT_COOKED));
        OreDictionary.registerOre("foodCongealedmagmacream", new ItemStack(CONGEALED_MAGMA_CREAM));
        OreDictionary.registerOre("listAllmushroom", new ItemStack(ENOKI_MUSHROOM));
        OreDictionary.registerOre("mushroomAny", new ItemStack(ENOKI_MUSHROOM));
        OreDictionary.registerOre("cropEnokimushroom", new ItemStack(ENOKI_MUSHROOM));
        OreDictionary.registerOre("listAllveggie", new ItemStack(ENOKI_MUSHROOM));
    }
}
