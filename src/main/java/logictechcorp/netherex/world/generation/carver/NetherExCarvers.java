/*
 * NetherEx
 * Copyright (c) 2016-2020 by LogicTechCorp
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

package logictechcorp.netherex.world.generation.carver;

import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NetherExCarvers
{
    private static final DeferredRegister<WorldCarver<?>> CARVER_OVERRIDES = new DeferredRegister<>(ForgeRegistries.WORLD_CARVERS, "minecraft");

    public static final RegistryObject<WorldCarver<ProbabilityConfig>> NETHER_CAVE = CARVER_OVERRIDES.register("hell_cave", () -> new NetherCaveCarverOverride(ProbabilityConfig::deserialize));

    public static void registerOverrides(IEventBus modEventBus)
    {
        CARVER_OVERRIDES.register(modEventBus);
    }
}
