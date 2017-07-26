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

import net.minecraft.init.Blocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;
import nex.world.gen.feature.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetherExBiomeFeatures
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExBiomeFeatures");

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBiomeFeatures(RegistryEvent.Register<BiomeFeature> event)
        {
            LOGGER.info("Biome Feature registration started.");

            event.getRegistry().registerAll(
                    new BiomeFeatureFluid(0.0F, 0, 0, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false),
                    new BiomeFeatureScatter(0.0F, 0, 0, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), BiomeFeatureScatter.Placement.ON_GROUND),
                    new BiomeFeatureCluster(0.0F, 0, 0, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState()),
                    new BiomeFeatureOre(0.0F, 0, 0, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 0),
                    new BiomeFeaturePool(0.0F, 0, 0, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState()),
                    new BiomeFeatureThornstalk(0.0F, 0, 0),
                    new BiomeFeatureEnoki(0.0F, 0, 0)
            );

            LOGGER.info("Biome Feature registration completed.");
        }
    }
}
