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

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ConstantConditions")
public class NetherExOreDict
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExOreDict");

    public static void init()
    {
        LOGGER.info("Ore Dictionary registration started.");

        addOreDictEntry("stoneBasalt", new ItemStack(NetherExBlocks.BLOCK_BASALT, 1, 0));
        addOreDictEntry("stoneBasaltPolished", new ItemStack(NetherExBlocks.BLOCK_BASALT, 1, 1));
        addOreDictEntry("stoneBasaltBrick", new ItemStack(NetherExBlocks.BLOCK_BASALT, 1, 2));
        addOreDictEntry("stoneBasaltBrick", new ItemStack(NetherExBlocks.BLOCK_BASALT, 1, 3));
        addOreDictEntry("netherrack", new ItemStack(NetherExBlocks.BLOCK_NETHERRACK, 1, OreDictionary.WILDCARD_VALUE));
        addOreDictEntry("blockGlass", new ItemStack(NetherExBlocks.BLOCK_GLASS_SOUL, 1, OreDictionary.WILDCARD_VALUE));
        addOreDictEntry("paneGlass", new ItemStack(NetherExBlocks.BLOCK_GLASS_PANE_SOUL, 1, OreDictionary.WILDCARD_VALUE));

        addOreDictEntry("oreQuartz", new ItemStack(NetherExBlocks.ORE_QUARTZ, 1, OreDictionary.WILDCARD_VALUE));
        addOreDictEntry("dyeBlack", new ItemStack(NetherExItems.ITEM_BONE_MEAL_WITHERED, 1, OreDictionary.WILDCARD_VALUE));

        LOGGER.info("Ore Dictionary registration completed.");
    }

    private static void addOreDictEntry(String name, ItemStack stack)
    {
        OreDictionary.registerOre(name, stack);
    }
}
