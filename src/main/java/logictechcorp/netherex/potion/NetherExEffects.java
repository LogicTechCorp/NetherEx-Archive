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

import logictechcorp.libraryex.potion.ModEffect;
import logictechcorp.netherex.NetherEx;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;

public class NetherExEffects
{
    public static final DeferredRegister<Effect> EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS, NetherEx.MOD_ID);

    public static final RegistryObject<Effect> INFESTED = EFFECTS.register("infested", InfestedEffect::new);
    public static final RegistryObject<Effect> FROZEN = EFFECTS.register("frozen", () -> new FrozenEffect().addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "C1372E45-9DB2-4E2E-BA2C-E3C5F6977891", -1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<Effect> FROSTBITTEN = EFFECTS.register("frostbitten", () -> new ModEffect(EffectType.HARMFUL, new Color(19, 226, 255).getRGB()));
    public static final RegistryObject<Effect> FIRE_BURNING = EFFECTS.register("fire_burning", FireBurningEffect::new);
    public static final RegistryObject<Effect> SOUL_SUCKED = EFFECTS.register("soul_sucked", SoulSuckedEffect::new);
}
