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

package logictechcorp.netherex.entity;

import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.hostile.*;
import logictechcorp.netherex.entity.neutral.MogusEntity;
import logictechcorp.netherex.entity.neutral.SalamanderEntity;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(NetherEx.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetherExEntities
{
    public static final EntityType<MogusEntity> MOGUS = InjectionHelper.nullValue();
    public static final EntityType<SalamanderEntity> SALAMANDER = InjectionHelper.nullValue();
    public static final EntityType<SpinoutEntity> SPINOUT = InjectionHelper.nullValue();
    public static final EntityType<SporeEntity> SPORE = InjectionHelper.nullValue();
    public static final EntityType<SporeCreeperEntity> SPORE_CREEPER = InjectionHelper.nullValue();
    public static final EntityType<WightEntity> WIGHT = InjectionHelper.nullValue();
    public static final EntityType<CoolmarSpiderEntity> COOLMAR_SPIDER = InjectionHelper.nullValue();

    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register<EntityType<?>> event)
    {
        registerEntity("mogus", EntityType.Builder.create(MogusEntity::new, EntityClassification.MONSTER).size(0.35F, 0.55F).immuneToFire());
        registerEntity("salamander", EntityType.Builder.create(SalamanderEntity::new, EntityClassification.MONSTER).size(1.25F, 0.5F).immuneToFire());
        registerEntity("spinout", EntityType.Builder.create(SpinoutEntity::new, EntityClassification.MONSTER).size(0.55F, 1.95F).immuneToFire());
        registerEntity("spore", EntityType.Builder.create(SporeEntity::new, EntityClassification.MONSTER).size(0.55F, 1.95F).immuneToFire());
        registerEntity("spore_creeper", EntityType.Builder.create(SporeCreeperEntity::new, EntityClassification.MONSTER).size(0.55F, 1.95F).immuneToFire());
        registerEntity("wight", EntityType.Builder.create(WightEntity::new, EntityClassification.MONSTER).size(0.55F, 1.5F).immuneToFire());
        registerEntity("coolmar_spider", EntityType.Builder.create(CoolmarSpiderEntity::new, EntityClassification.MONSTER).size(1.5F, 1.0F).immuneToFire());
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event)
    {
        registerEntitySpawn(MOGUS, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MogusEntity::canSpawn);
        registerEntitySpawn(SALAMANDER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SalamanderEntity::canSpawn);
        registerEntitySpawn(SPINOUT, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SpinoutEntity::canSpawn);
        registerEntitySpawn(SPORE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SporeEntity::canSpawn);
        registerEntitySpawn(SPORE_CREEPER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SporeCreeperEntity::canSpawn);
        registerEntitySpawn(WIGHT, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WightEntity::canSpawn);
        registerEntitySpawn(COOLMAR_SPIDER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CoolmarSpiderEntity::canSpawn);
    }

    private static <T extends Entity> void registerEntity(String name, EntityType.Builder<T> builder)
    {
        ResourceLocation registryName = new ResourceLocation(NetherEx.MOD_ID, name);
        ForgeRegistries.ENTITIES.register(builder.build(registryName.toString()).setRegistryName(registryName));
    }

    private static <T extends MobEntity> void registerEntitySpawn(EntityType<T> entityType, EntitySpawnPlacementRegistry.PlacementType placementType, Heightmap.Type heightMapType, EntitySpawnPlacementRegistry.IPlacementPredicate<T> placementPredicate)
    {
        EntitySpawnPlacementRegistry.register(entityType, placementType, heightMapType, placementPredicate);
    }
}
