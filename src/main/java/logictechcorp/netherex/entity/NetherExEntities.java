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

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.hostile.*;
import logictechcorp.netherex.entity.neutral.MogusEntity;
import logictechcorp.netherex.entity.neutral.SalamanderEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NetherExEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, NetherEx.MOD_ID);

    public static final RegistryObject<EntityType<MogusEntity>> MOGUS = ENTITY_TYPES.register("mogus", () -> EntityType.Builder.create(MogusEntity::new, EntityClassification.MONSTER).size(0.35F, 0.55F).immuneToFire().build(NetherEx.MOD_ID + ":mogus"));
    public static final RegistryObject<EntityType<SalamanderEntity>> SALAMANDER = ENTITY_TYPES.register("salamander", () -> EntityType.Builder.create(SalamanderEntity::new, EntityClassification.MONSTER).size(1.25F, 0.5F).immuneToFire().build(NetherEx.MOD_ID + ":salamander"));
    public static final RegistryObject<EntityType<SpinoutEntity>> SPINOUT = ENTITY_TYPES.register("spinout", () -> EntityType.Builder.create(SpinoutEntity::new, EntityClassification.MONSTER).size(0.55F, 1.95F).immuneToFire().build(NetherEx.MOD_ID + ":spinout"));
    public static final RegistryObject<EntityType<SporeEntity>> SPORE = ENTITY_TYPES.register("spore", () -> EntityType.Builder.create(SporeEntity::new, EntityClassification.MONSTER).size(0.55F, 1.95F).immuneToFire().build(NetherEx.MOD_ID + ":spore"));
    public static final RegistryObject<EntityType<SporeCreeperEntity>> SPORE_CREEPER = ENTITY_TYPES.register("spore_creeper", () -> EntityType.Builder.create(SporeCreeperEntity::new, EntityClassification.MONSTER).size(0.55F, 1.95F).immuneToFire().build(NetherEx.MOD_ID + ":spore_creeper"));
    public static final RegistryObject<EntityType<WightEntity>> WIGHT = ENTITY_TYPES.register("wight", () -> EntityType.Builder.create(WightEntity::new, EntityClassification.MONSTER).size(0.55F, 1.5F).immuneToFire().build(NetherEx.MOD_ID + ":wight"));
    public static final RegistryObject<EntityType<CoolmarSpiderEntity>> COOLMAR_SPIDER = ENTITY_TYPES.register("coolmar_spider", () -> EntityType.Builder.create(CoolmarSpiderEntity::new, EntityClassification.MONSTER).size(1.5F, 1.0F).immuneToFire().build(NetherEx.MOD_ID + ":coolmar_spider"));

    public static void registerSpawnPlacements()
    {
        EntitySpawnPlacementRegistry.register(MOGUS.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MogusEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(SALAMANDER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SalamanderEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(SPINOUT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SpinoutEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(SPORE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SporeEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(SPORE_CREEPER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SporeCreeperEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(WIGHT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WightEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(COOLMAR_SPIDER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CoolmarSpiderEntity::canSpawn);
    }
}
