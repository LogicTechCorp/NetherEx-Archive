/*
 * NetherEx
 * Copyright (c) 2016-2017 by MineEx
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

import lex.world.biome.BiomeWrapperBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.world.biome.BiomeWrapperNether;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExBiomeWrapperBuilders
{
    public static final BiomeWrapperNether.Builder NETHER = null;

    @SubscribeEvent
    public static void onRegisterBiomeWrapperBuilders(RegistryEvent.Register<BiomeWrapperBuilder> event)
    {
        event.getRegistry().registerAll(
                new BiomeWrapperNether.Builder()
        );
    }
}
