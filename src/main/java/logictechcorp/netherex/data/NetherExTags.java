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

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class NetherExTags
{
    public static class Blocks
    {
        public static final Tag<Block> NETHERRACK = createForgeTag("netherrack");

        private static Tag<Block> createForgeTag(String name)
        {
            return new BlockTags.Wrapper(new ResourceLocation("forge", name));
        }
    }
}
