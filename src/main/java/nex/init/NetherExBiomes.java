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

import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.world.WorldProviderNether;
import nex.world.biome.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@SuppressWarnings("ConstantConditions")
@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExBiomes
{
    @GameRegistry.ObjectHolder("hell")
    public static final BiomeHell HELL = null;

    @GameRegistry.ObjectHolder("ruthless_sands")
    public static final BiomeRuthlessSands RUTHLESS_SANDS = null;

    @GameRegistry.ObjectHolder("fungi_forest")
    public static final BiomeFungiForest FUNGI_FOREST = null;

    @GameRegistry.ObjectHolder("torrid_wasteland")
    public static final BiomeTorridWasteland TORRID_WASTELAND = null;

    @GameRegistry.ObjectHolder("arctic_abyss")
    public static final BiomeArcticAbyss ARCTIC_ABYSS = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExBiomes");

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBiomes(RegistryEvent.Register<Biome> event)
        {
            LOGGER.info("Biome registration started.");

            event.getRegistry().registerAll(
                    new BiomeHell(),
                    new BiomeRuthlessSands(),
                    new BiomeFungiForest(),
                    new BiomeTorridWasteland(),
                    new BiomeArcticAbyss()
            );

            LOGGER.info("Biome registration completed.");
        }
    }

    public static void init()
    {
        BiomeDictionary.addTypes(HELL, NETHER, HOT, DRY);
        BiomeDictionary.addTypes(RUTHLESS_SANDS, NETHER, HOT, DRY, SANDY);
        BiomeDictionary.addTypes(FUNGI_FOREST, NETHER, HOT, DRY, MUSHROOM);
        BiomeDictionary.addTypes(TORRID_WASTELAND, NETHER, HOT, DRY, WASTELAND);
        BiomeDictionary.addTypes(ARCTIC_ABYSS, NETHER, WET, COLD);

        DimensionManager.unregisterDimension(-1);
        DimensionType nether = DimensionType.register("Nether", "_nether", -1, WorldProviderNether.class, false);
        DimensionManager.registerDimension(-1, nether);

        LOGGER.info("The Nether has been overridden.");
    }
}
