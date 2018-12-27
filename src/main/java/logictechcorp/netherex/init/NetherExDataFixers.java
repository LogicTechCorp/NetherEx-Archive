/*
 * LibraryEx
 * Copyright (c) 2017-2018 by MineEx
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
 *
 */

package logictechcorp.netherex.init;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.fixer.BlockFlatteningFixer;
import logictechcorp.netherex.fixer.ItemFlatteningFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class NetherExDataFixers
{
    public static void registerFixes()
    {
        ModFixs fixer = FMLCommonHandler.instance().getDataFixer().init(NetherEx.MOD_ID, 209);
        fixer.registerFix(FixTypes.CHUNK, new BlockFlatteningFixer());
        fixer.registerFix(FixTypes.ITEM_INSTANCE, new ItemFlatteningFixer());
    }
}
