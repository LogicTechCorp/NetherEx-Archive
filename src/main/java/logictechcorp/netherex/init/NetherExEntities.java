/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.boss.EntityGhastQueen;
import logictechcorp.netherex.entity.item.EntityObsidianBoat;
import logictechcorp.netherex.entity.monster.*;
import logictechcorp.netherex.entity.neutral.EntityGoldGolem;
import logictechcorp.netherex.entity.neutral.EntityMogus;
import logictechcorp.netherex.entity.neutral.EntitySalamander;
import logictechcorp.netherex.entity.passive.EntityBonspider;
import logictechcorp.netherex.entity.passive.EntityPigtificate;
import logictechcorp.netherex.entity.passive.EntityPigtificateLeader;
import logictechcorp.netherex.entity.projectile.EntityBlueFireball;
import logictechcorp.netherex.entity.projectile.EntityGhastQueenFireball;
import logictechcorp.netherex.entity.projectile.EntityGhastlingFireball;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public class NetherExEntities
{
    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        private static final EntityEntryBuilder<Entity> ENTITY_BUILDER = EntityEntryBuilder.create();
        private static int entityId = -1;

        @SubscribeEvent
        public static void onRegisterEntities(RegistryEvent.Register<EntityEntry> event)
        {
            event.getRegistry().registerAll(
                    ENTITY_BUILDER.entity(EntityGhastQueenFireball.class).id("ghast_queen_fireball", entityId++).name("ghast_queen_fireball").build(),
                    ENTITY_BUILDER.entity(EntityGhastlingFireball.class).id("ghastling_fireball", entityId++).name("ghastling_fireball").build(),
                    ENTITY_BUILDER.entity(EntityBlueFireball.class).id("blue_fireball", entityId++).name("blue_fireball").build(),
                    ENTITY_BUILDER.entity(EntityObsidianBoat.class).id("obsidian_boat", entityId++).name("obsidian_boat").build(),
                    ENTITY_BUILDER.entity(EntityPigtificateLeader.class).id("pigtificate_leader", entityId++).name("pigtificate_leader").egg(12422002, 15771042).build(),
                    ENTITY_BUILDER.entity(EntityPigtificate.class).id("pigtificate", entityId++).name("pigtificate").egg(15771042, 12422002).build(),
                    ENTITY_BUILDER.entity(EntityBonspider.class).id("bonspider", entityId++).name("bonspider").egg(394758, 16711680).build(),
                    ENTITY_BUILDER.entity(EntityGoldGolem.class).id("gold_golem", entityId++).name("gold_golem").egg(16773448, 14520344).build(),
                    ENTITY_BUILDER.entity(EntityMogus.class).id("mogus", entityId++).name("mogus").egg(6770482, 5114119).build(),
                    ENTITY_BUILDER.entity(EntitySalamander.class).id("salamander", entityId++).name("salamander").egg(15949838, 394758).build(),
                    ENTITY_BUILDER.entity(EntityWight.class).id("wight", entityId++).name("wight").egg(16382457, 9484735).build(),
                    ENTITY_BUILDER.entity(EntityEmber.class).id("ember", entityId++).name("ember").egg(16711680, 16762880).build(),
                    ENTITY_BUILDER.entity(EntityNethermite.class).id("nethermite", entityId++).name("nethermite").egg(3344642, 8992279).build(),
                    ENTITY_BUILDER.entity(EntitySpinout.class).id("spinout", entityId++).name("spinout").egg(3279879, 15724527).build(),
                    ENTITY_BUILDER.entity(EntitySporeCreeper.class).id("spore_creeper", entityId++).name("spore_creeper").egg(5576214, 15721297).build(),
                    ENTITY_BUILDER.entity(EntitySpore.class).id("spore", entityId++).name("spore").egg(15721297, 5576214).build(),
                    ENTITY_BUILDER.entity(EntityGhastling.class).id("ghastling", entityId++).name("ghastling").egg(16447992, 14077883).build(),
                    ENTITY_BUILDER.entity(EntityCoolmarSpider.class).id("coolmar_spider", entityId++).name("coolmar_spider").egg(13750221, 13369344).build(),
                    ENTITY_BUILDER.entity(EntityBrute.class).id("brute", entityId++).name("brute").egg(11045525, 4793626).build(),
                    ENTITY_BUILDER.entity(EntityFrost.class).id("frost", entityId++).name("frost").egg(16382457, 6143186).build(),
                    ENTITY_BUILDER.entity(EntityHellhound.class).id("hellhound", entityId++).name("hellhound").egg(15724527, 5576214).build(),
                    ENTITY_BUILDER.entity(EntityGhastQueen.class).id("ghast_queen", entityId++).name("ghast_queen").egg(15790320, 13546448).build()
            );
        }
    }
}
