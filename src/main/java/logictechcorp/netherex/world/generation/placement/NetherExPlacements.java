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

package logictechcorp.netherex.world.generation.placement;

import logictechcorp.netherex.NetherEx;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NetherExPlacements
{
    public static final DeferredRegister<Placement<?>> PLACEMENTS = new DeferredRegister<>(ForgeRegistries.DECORATORS, NetherEx.MOD_ID);

    public static final RegistryObject<Placement<NoPlacementConfig>> ENOKI_MUSHROOM = PLACEMENTS.register("enoki_mushroom", () -> new EnokiMushroomPlacement(NoPlacementConfig::deserialize));
}
