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
import nex.NetherEx;
import nex.block.BlockBasalt;
import nex.block.BlockNetherrack;

import static nex.init.NetherExBlocks.*;
import static nex.init.NetherExItems.*;

@SuppressWarnings("ConstantConditions")
public class NetherExOreDict
{
    public static void init()
    {
        NetherEx.LOGGER.info("Ore Dictionary registration started.");

        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            addOreDictEntry("stoneBasalt", new ItemStack(BASALT, 1, type.ordinal()));

            if(type.ordinal() == 1)
            {
                addOreDictEntry("stoneBasaltPolished", new ItemStack(BASALT, 1, type.ordinal()));
            }
            else if(type.ordinal() == 2 || type.ordinal() == 3)
            {
                addOreDictEntry("stoneBasaltBrick", new ItemStack(BASALT, 1, type.ordinal()));
            }
        }

        addOreDictEntry("blockGlass", new ItemStack(SOUL_GLASS, 1, 0));
        addOreDictEntry("paneGlass", new ItemStack(SOUL_GLASS_PANE, 1, 0));

        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            addOreDictEntry("netherrack", new ItemStack(NETHERRACK, 1, type.ordinal()));
            addOreDictEntry("ingotBrickNether", new ItemStack(NetherExItems.NETHERBRICK, 1, type.ordinal()));
            addOreDictEntry("oreQuartz", new ItemStack(QUARTZ_ORE, 1, type.ordinal()));
        }

        addOreDictEntry("oreAmethyst", new ItemStack(GEM_ORE, 1, 0));
        addOreDictEntry("blockAmethyst", new ItemStack(GEM_BLOCK, 1, 0));
        addOreDictEntry("gemAmethyst", new ItemStack(GEM, 1, 0));
        addOreDictEntry("oreRime", new ItemStack(GEM, 1, 1));
        addOreDictEntry("blockRime", new ItemStack(GEM, 1, 1));
        addOreDictEntry("gemRime", new ItemStack(GEM, 1, 1));
        addOreDictEntry("boneWithered", new ItemStack(WITHER_BONE, 1, 0));
        addOreDictEntry("dyeBlack", new ItemStack(WITHER_DUST, 1, 0));

        NetherEx.LOGGER.info("Ore Dictionary registration completed.");
    }

    private static void addOreDictEntry(String name, ItemStack stack)
    {
        OreDictionary.registerOre(name, stack);
    }
}
