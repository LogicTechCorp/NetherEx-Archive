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

import logictechcorp.libraryex.world.gen.feature.FeatureRegistry;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.world.gen.feature.FeatureEnoki;
import logictechcorp.netherex.world.gen.feature.FeatureThornstalk;
import net.minecraft.util.ResourceLocation;

public class NetherExFeatures
{
    public static void registerFeatures()
    {
        FeatureRegistry.registerFeature(new ResourceLocation(NetherEx.MOD_ID + ":thornstalk"), FeatureThornstalk.class);
        FeatureRegistry.registerFeature(new ResourceLocation(NetherEx.MOD_ID + ":enoki"), FeatureEnoki.class);
    }
}
