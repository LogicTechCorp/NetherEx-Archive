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

package nex.item;

import nex.init.NetherExBlocks;

@SuppressWarnings("ConstantConditions")
public class ItemBlockNetherBrickSlab extends ItemBlockNetherExSlab
{
    public ItemBlockNetherBrickSlab(boolean isDouble)
    {
        super(!isDouble ? NetherExBlocks.SLAB_NETHER_BRICK : NetherExBlocks.SLAB_NETHER_BRICK_DOUBLE, NetherExBlocks.SLAB_NETHER_BRICK, NetherExBlocks.SLAB_NETHER_BRICK_DOUBLE);
    }
}
