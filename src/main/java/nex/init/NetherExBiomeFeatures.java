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

import nex.world.gen.BiomeFeatureManager;
import nex.world.gen.feature.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetherExBiomeFeatures
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExBiomeFeatures");

    public static void init()
    {
        LOGGER.info("Biome Feature registration started.");

        BiomeFeatureManager.registerBiomeFeature("fluid", BiomeFeatureFluid.class);
        BiomeFeatureManager.registerBiomeFeature("scatter", BiomeFeatureScatter.class);
        BiomeFeatureManager.registerBiomeFeature("cluster", BiomeFeatureCluster.class);
        BiomeFeatureManager.registerBiomeFeature("ore", BiomeFeatureOre.class);
        BiomeFeatureManager.registerBiomeFeature("pool", BiomeFeaturePool.class);
        BiomeFeatureManager.registerBiomeFeature("thornstalk", BiomeFeatureThornstalk.class);
        BiomeFeatureManager.registerBiomeFeature("enoki", BiomeFeatureEnoki.class);

        LOGGER.info("Biome Feature registration completed.");
    }
}
