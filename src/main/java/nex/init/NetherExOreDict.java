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

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import nex.block.BlockBasalt;
import nex.block.BlockNetherrack;

import static nex.init.NetherExBlocks.*;
import static nex.init.NetherExItems.*;

@SuppressWarnings("ConstantConditions")
public class NetherExOreDict
{
    public static void init()
    {
        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            registerWithOreDictionary("stoneBasalt", new ItemStack(BASALT, 1, type.ordinal()));

            if(type.ordinal() == 1)
            {
                registerWithOreDictionary("stoneBasaltPolished", new ItemStack(BASALT, 1, type.ordinal()));
            }
            else if(type.ordinal() == 2 || type.ordinal() == 3)
            {
                registerWithOreDictionary("stoneBasaltBrick", new ItemStack(BASALT, 1, type.ordinal()));
            }
        }

        registerWithOreDictionary("blockGlass", new ItemStack(SOUL_GLASS));
        registerWithOreDictionary("paneGlass", new ItemStack(SOUL_GLASS_PANE));

        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            registerWithOreDictionary("netherrack", new ItemStack(NETHERRACK, 1, type.ordinal()));
            registerWithOreDictionary("ingotBrickNether", new ItemStack(NetherExItems.NETHERBRICK, 1, type.ordinal()));
            registerWithOreDictionary("oreQuartz", new ItemStack(QUARTZ_ORE, 1, type.ordinal()));
        }

        registerWithOreDictionary("oreAmethyst", new ItemStack(GEM_ORE));
        registerWithOreDictionary("blockAmethyst", new ItemStack(GEM_BLOCK));
        registerWithOreDictionary("gemAmethyst", new ItemStack(GEM));
        registerWithOreDictionary("oreRime", new ItemStack(GEM, 1, 1));
        registerWithOreDictionary("blockRime", new ItemStack(GEM, 1, 1));
        registerWithOreDictionary("gemRime", new ItemStack(GEM, 1, 1));
        registerWithOreDictionary("boneWithered", new ItemStack(WITHER_BONE));
        registerWithOreDictionary("dyeBlack", new ItemStack(WITHER_DUST));
        registerWithOreDictionary("listAllmeatraw", new ItemStack(GHAST_MEAT_RAW));
        registerWithOreDictionary("listAllghastraw", new ItemStack(GHAST_MEAT_RAW));
        registerWithOreDictionary("foodMeats", new ItemStack(GHAST_MEAT_RAW));
        registerWithOreDictionary("listAllmeatcooked", new ItemStack(GHAST_MEAT_COOKED));
        registerWithOreDictionary("listAllghastcooked", new ItemStack(GHAST_MEAT_COOKED));
        registerWithOreDictionary("foodMeats", new ItemStack(GHAST_MEAT_COOKED));
        registerWithOreDictionary("foodCongealedmagmacream", new ItemStack(CONGEALED_MAGMA_CREAM));
        registerWithOreDictionary("listAllmushroom", new ItemStack(ENOKI_MUSHROOM));
        registerWithOreDictionary("mushroomAny", new ItemStack(ENOKI_MUSHROOM));
        registerWithOreDictionary("cropEnokimushroom", new ItemStack(ENOKI_MUSHROOM));
        registerWithOreDictionary("listAllveggie", new ItemStack(ENOKI_MUSHROOM));
    }

    private static void registerWithOreDictionary(String name, ItemStack stack)
    {
        OreDictionary.registerOre(name, stack);
    }
}
