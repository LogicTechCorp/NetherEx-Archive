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

import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.awt.*;

@ObjectHolder(NetherEx.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetherExEffects
{
    public static final Effect INFESTED = InjectionHelper.nullValue();
    public static final Effect FROZEN = InjectionHelper.nullValue();
    public static final Effect FROSTBITTEN = InjectionHelper.nullValue();
    public static final Effect FIRE_BURNING = InjectionHelper.nullValue();
    public static final Effect SOUL_SUCKED = InjectionHelper.nullValue();

    @SubscribeEvent
    public static void onRegisterEffect(RegistryEvent.Register<Effect> event)
    {
        registerEffect("infested", new InfestedEffect());
        registerEffect("frozen", new FrozenEffect().addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "C1372E45-9DB2-4E2E-BA2C-E3C5F6977891", -1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        registerEffect("frostbitten", new Effect(EffectType.HARMFUL, new Color(19, 226, 255).getRGB()));
        registerEffect("fire_burning", new FireBurningEffect());
        registerEffect("soul_sucked", new SoulSuckedEffect());
    }

    private static void registerEffect(String name, Effect effect)
    {
        ForgeRegistries.POTIONS.register(effect.setRegistryName(name));
    }
}
