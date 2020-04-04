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

package logictechcorp.netherex.utility;

import logictechcorp.netherex.NetherEx;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class NetherExSoundEvents
{
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, NetherEx.MOD_ID);

    // @formatter:off
    public static final RegistryObject<SoundEvent> ENTITY_MOGUS_AMBIENT      = registerSound("entity_mogus_ambient");
    public static final RegistryObject<SoundEvent> ENTITY_MOGUS_HURT         = registerSound("entity_mogus_hurt");
    public static final RegistryObject<SoundEvent> ENTITY_MOGUS_DEATH        = registerSound("entity_mogus_death");
    public static final RegistryObject<SoundEvent> ENTITY_SALAMANDER_AMBIENT = registerSound("entity_salamander_ambient");
    public static final RegistryObject<SoundEvent> ENTITY_SALAMANDER_HURT    = registerSound("entity_salamander_hurt");
    public static final RegistryObject<SoundEvent> ENTITY_SALAMANDER_DEATH   = registerSound("entity_salamander_death");
    public static final RegistryObject<SoundEvent> ENTITY_WIGHT_AMBIENT      = registerSound("entity_wight_ambient");
    public static final RegistryObject<SoundEvent> ENTITY_WIGHT_HURT         = registerSound("entity_wight_hurt");
    public static final RegistryObject<SoundEvent> ENTITY_WIGHT_DEATH        = registerSound("entity_wight_death");
    public static final RegistryObject<SoundEvent> ENTITY_SPINOUT_AMBIENT    = registerSound("entity_spinout_ambient");
    public static final RegistryObject<SoundEvent> ENTITY_SPINOUT_HURT       = registerSound("entity_spinout_hurt");
    public static final RegistryObject<SoundEvent> ENTITY_SPINOUT_DEATH      = registerSound("entity_spinout_death");
    public static final RegistryObject<SoundEvent> ENTITY_SPORE_HURT         = registerSound("entity_spore_hurt");
    public static final RegistryObject<SoundEvent> ENTITY_SPORE_DEATH        = registerSound("entity_spore_death");
    public static final RegistryObject<SoundEvent> ENTITY_SPORE_WARN         = registerSound("entity_spore_warn");
    public static final RegistryObject<SoundEvent> ENTITY_SPORE_EXPLODE      = registerSound("entity_spore_explode");
    // @formatter:on

    public static void register(IEventBus modEventBus)
    {
        SOUND_EVENTS.register(modEventBus);
    }

    private static <SE extends SoundEvent> RegistryObject<SE> registerSound(String name, Supplier<SE> supplier)
    {
        return SOUND_EVENTS.register(name, supplier);
    }

    private static RegistryObject<SoundEvent> registerSound(String name)
    {
        return registerSound(name, () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, name.replace("_", "."))));
    }
}