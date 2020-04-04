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

import logictechcorp.netherex.NetherEx;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NetherExSurfaceBuilders
{
    private static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = new DeferredRegister<>(ForgeRegistries.SURFACE_BUILDERS, NetherEx.MOD_ID);

    public static final RegistryObject<SurfaceBuilder<NetherSurfaceBuilderWrapper.Config>> NETHER_SURFACE_BUILDER_WRAPPER = SURFACE_BUILDERS.register("nether_surface_builder_wrapper", () -> new NetherSurfaceBuilderWrapper(NetherSurfaceBuilderWrapper.Config::deserialize));

    public static void register(IEventBus modEventBus)
    {
        SURFACE_BUILDERS.register(modEventBus);
    }
}
