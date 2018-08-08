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
import nex.world.WorldProviderNetherEx;
import nex.world.biome.BiomeArcticAbyss;
import nex.world.biome.BiomeFungiForest;
import nex.world.biome.BiomeRuthlessSands;
import nex.world.biome.BiomeTorridWasteland;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@SuppressWarnings("ConstantConditions")
@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExBiomes
{
    public static final BiomeRuthlessSands RUTHLESS_SANDS = null;
    public static final BiomeFungiForest FUNGI_FOREST = null;
    public static final BiomeTorridWasteland TORRID_WASTELAND = null;
    public static final BiomeArcticAbyss ARCTIC_ABYSS = null;

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBiomes(RegistryEvent.Register<Biome> event)
        {
            event.getRegistry().registerAll(
                    new BiomeRuthlessSands(),
                    new BiomeFungiForest(),
                    new BiomeTorridWasteland(),
                    new BiomeArcticAbyss()
            );
        }
    }

    public static void init()
    {
        BiomeDictionary.addTypes(RUTHLESS_SANDS, NETHER, HOT, DRY, SANDY);
        BiomeDictionary.addTypes(FUNGI_FOREST, NETHER, HOT, DRY, MUSHROOM);
        BiomeDictionary.addTypes(TORRID_WASTELAND, NETHER, HOT, DRY, WASTELAND);
        BiomeDictionary.addTypes(ARCTIC_ABYSS, NETHER, WET, COLD);
    }
}
