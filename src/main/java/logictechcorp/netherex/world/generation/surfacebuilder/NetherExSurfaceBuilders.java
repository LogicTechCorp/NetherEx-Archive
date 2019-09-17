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

package logictechcorp.netherex.world.generation.surfacebuilder;

import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(NetherEx.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetherExSurfaceBuilders
{
    public static final SurfaceBuilder<NetherSurfaceBuilderWrapper.Config> NETHER_SURFACE_BUILDER_WRAPPER = InjectionHelper.nullValue();

    @SubscribeEvent
    public static void onSurfaceBuilderRegister(RegistryEvent.Register<SurfaceBuilder<?>> event)
    {
        registerSurfaceBuilder("nether_surface_builder_wrapper", new NetherSurfaceBuilderWrapper(NetherSurfaceBuilderWrapper.Config::deserialize));
    }

    private static void registerSurfaceBuilder(String name, SurfaceBuilder<?> feature)
    {
        ForgeRegistries.SURFACE_BUILDERS.register(feature.setRegistryName(name));
    }
}
