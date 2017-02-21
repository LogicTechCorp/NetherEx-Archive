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

package nex.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import nex.NetherEx;

import java.util.List;
import java.util.Random;

public class WeightedUtil
{
    public static ResourceLocation getRandomStructure(Random rand, List<NamedItem> variants)
    {
        WeightedUtil.NamedItem randomItem = WeightedRandom.getRandomItem(rand, variants);
        return new ResourceLocation(NetherEx.MOD_ID + ":" + randomItem.name);
    }

    public static class NamedItem extends WeightedRandom.Item
    {
        public String name;

        public NamedItem(String nameIn, int weight)
        {
            super(weight);
            name = nameIn;
        }
    }
}
