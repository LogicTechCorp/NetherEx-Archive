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

import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(NetherEx.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetherExSounds
{
    public static final SoundEvent ENTITY_PIGTIFICATE_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_PIGTIFICATE_HURT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_PIGTIFICATE_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_MOGUS_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_MOGUS_HURT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_MOGUS_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_SALAMANDER_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_SALAMANDER_HURT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_SALAMANDER_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_WIGHT_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_WIGHT_HURT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_WIGHT_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_EMBER_HURT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_EMBER_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_NETHERMITE_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_NETHERMITE_HURT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_NETHERMITE_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_SPINOUT_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_SPINOUT_HURT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_SPINOUT_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_SPORE_HURT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_SPORE_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_SPORE_WARN = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_SPORE_EXPLODE = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_GHASTLING_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_GHASTLING_HURT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_GHASTLING_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_GHASTLING_WARN = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_GHASTLING_SHOOT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_GHAST_QUEEN_AMBIENT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_GHAST_QUEEN_HURT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_GHAST_QUEEN_DEATH = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_GHAST_QUEEN_SHOOT = InjectionHelper.nullValue();
    public static final SoundEvent ENTITY_GHAST_QUEEN_SUMMON = InjectionHelper.nullValue();

    @SubscribeEvent
    public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event)
    {
        registerSound("entity.pigtificate.ambient");
        registerSound("entity.pigtificate.hurt");
        registerSound("entity.pigtificate.death");
        registerSound("entity.mogus.ambient");
        registerSound("entity.mogus.hurt");
        registerSound("entity.mogus.death");
        registerSound("entity.salamander.ambient");
        registerSound("entity.salamander.hurt");
        registerSound("entity.salamander.death");
        registerSound("entity.wight.ambient");
        registerSound("entity.wight.hurt");
        registerSound("entity.wight.death");
        registerSound("entity.ember.hurt");
        registerSound("entity.ember.death");
        registerSound("entity.nethermite.ambient");
        registerSound("entity.nethermite.hurt");
        registerSound("entity.nethermite.death");
        registerSound("entity.spinout.ambient");
        registerSound("entity.spinout.hurt");
        registerSound("entity.spinout.death");
        registerSound("entity.spore.hurt");
        registerSound("entity.spore.death");
        registerSound("entity.spore.warn");
        registerSound("entity.spore.explode");
        registerSound("entity.ghastling.ambient");
        registerSound("entity.ghastling.hurt");
        registerSound("entity.ghastling.death");
        registerSound("entity.ghastling.warn");
        registerSound("entity.ghastling.shoot");
        registerSound("entity.ghast_queen.ambient");
        registerSound("entity.ghast_queen.hurt");
        registerSound("entity.ghast_queen.death");
        registerSound("entity.ghast_queen.shoot");
        registerSound("entity.ghast_queen.summon");
    }

    private static void registerSound(String name)
    {
        ForgeRegistries.SOUND_EVENTS.register(new SoundEvent(new ResourceLocation(NetherEx.MOD_ID, name)).setRegistryName(new ResourceLocation(NetherEx.MOD_ID, name.replace(".", "_"))));
    }
}