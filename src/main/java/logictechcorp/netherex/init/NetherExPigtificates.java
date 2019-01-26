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

package logictechcorp.netherex.init;

import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.village.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExPigtificates
{
    public static final PigtificateProfessionLeader LEADER = InjectionHelper.nullValue();
    public static final PigtificateProfessionDimwit DIMWIT = InjectionHelper.nullValue();
    public static final PigtificateProfessionForager FORAGER = InjectionHelper.nullValue();
    public static final PigtificateProfessionSmith SMITH = InjectionHelper.nullValue();
    public static final PigtificateProfessionSorcerer SORCERER = InjectionHelper.nullValue();

    public static void registerPigtificateCareers()
    {
        LEADER.registerDefaultCareers();
        DIMWIT.registerDefaultCareers();
        FORAGER.registerDefaultCareers();
        SMITH.registerDefaultCareers();
        SORCERER.registerDefaultCareers();
    }

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterPigtificateProfessions(RegistryEvent.Register<PigtificateProfession> event)
        {
            event.getRegistry().registerAll(
                    new PigtificateProfessionLeader(),
                    new PigtificateProfessionDimwit(),
                    new PigtificateProfessionForager(),
                    new PigtificateProfessionSmith(),
                    new PigtificateProfessionSorcerer()
            );
        }
    }
}
