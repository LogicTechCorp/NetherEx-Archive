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

import logictechcorp.libraryex.sound.SoundEventLibEx;
import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExSoundEvents
{
    public static final SoundEvent PIGTIFICATE_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent PIGTIFICATE_HURT = InjectionHelper.nullValue();
    public static final SoundEvent PIGTIFICATE_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent MOGUS_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent MOGUS_HURT = InjectionHelper.nullValue();
    public static final SoundEvent MOGUS_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent SALAMANDER_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent SALAMANDER_HURT = InjectionHelper.nullValue();
    public static final SoundEvent SALAMANDER_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent WIGHT_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent WIGHT_HURT = InjectionHelper.nullValue();
    public static final SoundEvent WIGHT_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent EMBER_HURT = InjectionHelper.nullValue();
    public static final SoundEvent EMBER_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent NETHERMITE_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent NETHERMITE_HURT = InjectionHelper.nullValue();
    public static final SoundEvent NETHERMITE_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent SPINOUT_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent SPINOUT_HURT = InjectionHelper.nullValue();
    public static final SoundEvent SPINOUT_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent SPORE_HURT = InjectionHelper.nullValue();
    public static final SoundEvent SPORE_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent SPORE_WARN = InjectionHelper.nullValue();
    public static final SoundEvent SPORE_EXPLODE = InjectionHelper.nullValue();
    public static final SoundEvent GHASTLING_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent GHASTLING_HURT = InjectionHelper.nullValue();
    public static final SoundEvent GHASTLING_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent GHASTLING_WARN = InjectionHelper.nullValue();
    public static final SoundEvent GHASTLING_SHOOT = InjectionHelper.nullValue();
    public static final SoundEvent GHAST_QUEEN_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent GHAST_QUEEN_HURT = InjectionHelper.nullValue();
    public static final SoundEvent GHAST_QUEEN_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent GHAST_QUEEN_SHOOT = InjectionHelper.nullValue();
    public static final SoundEvent GHAST_QUEEN_SUMMON = InjectionHelper.nullValue();

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event)
        {
            event.getRegistry().registerAll(
                    new SoundEventLibEx(NetherEx.instance, "pigtificate_ambient"),
                    new SoundEventLibEx(NetherEx.instance, "pigtificate_hurt"),
                    new SoundEventLibEx(NetherEx.instance, "pigtificate_death"),
                    new SoundEventLibEx(NetherEx.instance, "mogus_ambient"),
                    new SoundEventLibEx(NetherEx.instance, "mogus_hurt"),
                    new SoundEventLibEx(NetherEx.instance, "mogus_death"),
                    new SoundEventLibEx(NetherEx.instance, "salamander_ambient"),
                    new SoundEventLibEx(NetherEx.instance, "salamander_hurt"),
                    new SoundEventLibEx(NetherEx.instance, "salamander_death"),
                    new SoundEventLibEx(NetherEx.instance, "wight_ambient"),
                    new SoundEventLibEx(NetherEx.instance, "wight_hurt"),
                    new SoundEventLibEx(NetherEx.instance, "wight_death"),
                    new SoundEventLibEx(NetherEx.instance, "ember_hurt"),
                    new SoundEventLibEx(NetherEx.instance, "ember_death"),
                    new SoundEventLibEx(NetherEx.instance, "nethermite_ambient"),
                    new SoundEventLibEx(NetherEx.instance, "nethermite_hurt"),
                    new SoundEventLibEx(NetherEx.instance, "nethermite_death"),
                    new SoundEventLibEx(NetherEx.instance, "spinout_ambient"),
                    new SoundEventLibEx(NetherEx.instance, "spinout_hurt"),
                    new SoundEventLibEx(NetherEx.instance, "spinout_death"),
                    new SoundEventLibEx(NetherEx.instance, "spore_hurt"),
                    new SoundEventLibEx(NetherEx.instance, "spore_death"),
                    new SoundEventLibEx(NetherEx.instance, "spore_warn"),
                    new SoundEventLibEx(NetherEx.instance, "spore_explode"),
                    new SoundEventLibEx(NetherEx.instance, "ghastling_ambient"),
                    new SoundEventLibEx(NetherEx.instance, "ghastling_hurt"),
                    new SoundEventLibEx(NetherEx.instance, "ghastling_death"),
                    new SoundEventLibEx(NetherEx.instance, "ghastling_warn"),
                    new SoundEventLibEx(NetherEx.instance, "ghastling_shoot"),
                    new SoundEventLibEx(NetherEx.instance, "ghast_queen_ambient"),
                    new SoundEventLibEx(NetherEx.instance, "ghast_queen_hurt"),
                    new SoundEventLibEx(NetherEx.instance, "ghast_queen_death"),
                    new SoundEventLibEx(NetherEx.instance, "ghast_queen_shoot"),
                    new SoundEventLibEx(NetherEx.instance, "ghast_queen_summon")
            );
        }
    }
}
