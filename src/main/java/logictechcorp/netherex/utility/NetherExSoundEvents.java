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
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NetherExSoundEvents
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, NetherEx.MOD_ID);

    public static final RegistryObject<SoundEvent> ENTITY_MOGUS_AMBIENT = SOUND_EVENTS.register("entity_mogus_ambient", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.mogus.ambient")));
    public static final RegistryObject<SoundEvent> ENTITY_MOGUS_HURT = SOUND_EVENTS.register("entity_mogus_hurt", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.mogus.hurt")));
    public static final RegistryObject<SoundEvent> ENTITY_MOGUS_DEATH = SOUND_EVENTS.register("entity_mogus_death", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.mogus.death")));
    public static final RegistryObject<SoundEvent> ENTITY_SALAMANDER_AMBIENT = SOUND_EVENTS.register("entity_salamander_ambient", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.salamander.ambient")));
    public static final RegistryObject<SoundEvent> ENTITY_SALAMANDER_HURT = SOUND_EVENTS.register("entity_salamander_hurt", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.salamander.hurt")));
    public static final RegistryObject<SoundEvent> ENTITY_SALAMANDER_DEATH = SOUND_EVENTS.register("entity_salamander_death", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.salamander.death")));
    public static final RegistryObject<SoundEvent> ENTITY_WIGHT_AMBIENT = SOUND_EVENTS.register("entity_wight_ambient", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.wight.ambient")));
    public static final RegistryObject<SoundEvent> ENTITY_WIGHT_HURT = SOUND_EVENTS.register("entity_wight_hurt", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.wight.hurt")));
    public static final RegistryObject<SoundEvent> ENTITY_WIGHT_DEATH = SOUND_EVENTS.register("entity_wight_death", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.wight.death")));
    public static final RegistryObject<SoundEvent> ENTITY_SPINOUT_AMBIENT = SOUND_EVENTS.register("entity_spinout_ambient", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.spinout.ambient")));
    public static final RegistryObject<SoundEvent> ENTITY_SPINOUT_HURT = SOUND_EVENTS.register("entity_spinout_hurt", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.spinout.hurt")));
    public static final RegistryObject<SoundEvent> ENTITY_SPINOUT_DEATH = SOUND_EVENTS.register("entity_spinout_death", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.spinout.death")));
    public static final RegistryObject<SoundEvent> ENTITY_SPORE_HURT = SOUND_EVENTS.register("entity_spore_hurt", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.spore.hurt")));
    public static final RegistryObject<SoundEvent> ENTITY_SPORE_DEATH = SOUND_EVENTS.register("entity_spore_death", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.spore.death")));
    public static final RegistryObject<SoundEvent> ENTITY_SPORE_WARN = SOUND_EVENTS.register("entity_spore_warn", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.spore.warn")));
    public static final RegistryObject<SoundEvent> ENTITY_SPORE_EXPLODE = SOUND_EVENTS.register("entity_spore_explode", () -> new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, "entity.spore.explode")));
}