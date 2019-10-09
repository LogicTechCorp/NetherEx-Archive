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

package logictechcorp.netherex.potion;

import logictechcorp.netherex.NetherEx;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NetherExPotions
{
    public static final DeferredRegister<Potion> POTIONS = new DeferredRegister<>(ForgeRegistries.POTION_TYPES, NetherEx.MOD_ID);

    public static final RegistryObject<Potion> DISPERSAL = POTIONS.register("dispersal", () -> new Potion(new EffectInstance(NetherExEffects.INFESTED.get(), 600)));
    public static final RegistryObject<Potion> FREEZING = POTIONS.register("freezing", () -> new Potion(new EffectInstance(NetherExEffects.FROZEN.get(), 600)));
    public static final RegistryObject<Potion> FRIGID_HEALTH = POTIONS.register("frigid_health", () -> new Potion(new EffectInstance(NetherExEffects.FROSTBITTEN.get(), 600)));
}
