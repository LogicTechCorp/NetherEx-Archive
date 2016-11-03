/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.block;

import net.minecraft.util.IStringSerializable;

public class BlockVanillaStone
{
    public enum EnumTypeWith implements IStringSerializable
    {
        NETHER_BRICK,
        NETHER_BRICK_RED;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }
    }

    public enum EnumTypeWithOut implements IStringSerializable
    {
        NETHER_BRICK_RED;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }
    }
}
