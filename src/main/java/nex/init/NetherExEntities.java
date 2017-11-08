/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.init;

import com.google.common.base.CaseFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import nex.NetherEx;
import nex.entity.boss.EntityGhastQueen;
import nex.entity.item.EntityObsidianBoat;
import nex.entity.monster.*;
import nex.entity.neutral.EntityGoldGolem;
import nex.entity.neutral.EntityMogus;
import nex.entity.neutral.EntitySalamander;
import nex.entity.passive.EntityPigtificate;
import nex.entity.passive.EntityPigtificateLeader;
import nex.entity.projectile.EntityGhastQueenFireball;
import nex.entity.projectile.EntityGhastlingFireball;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetherExEntities
{
    private static int entityId = -1;
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExEntities");

    public static void init()
    {
        LOGGER.info("EntityConfig registration started.");

        registerEntity("projectile_ghast_queen_fireball", EntityGhastQueenFireball.class, entityId++);
        registerEntity("projectile_ghastling_fireball", EntityGhastlingFireball.class, entityId++);

        registerEntity("item_boat_obsidian", EntityObsidianBoat.class, entityId++);

        registerEntity("passive_pigtificate_leader", EntityPigtificateLeader.class, entityId++, 12422002, 15771042);
        registerEntity("passive_pigtificate", EntityPigtificate.class, entityId++, 15771042, 12422002);

        registerEntity("neutral_gold_golem", EntityGoldGolem.class, entityId++, 16773448, 14520344);
        registerEntity("neutral_mogus", EntityMogus.class, entityId++, 6770482, 5114119);
        registerEntity("neutral_salamander", EntitySalamander.class, entityId++, 15949838, 394758);

        registerEntity("monster_wight", EntityWight.class, entityId++, 16382457, 9484735);
        registerEntity("monster_ember", EntityEmber.class, entityId++, 16711680, 16762880);
        registerEntity("monster_nethermite", EntityNethermite.class, entityId++, 3344642, 8992279);
        registerEntity("monster_spinout", EntitySpinout.class, entityId++, 3279879, 15724527);
        registerEntity("monster_spore_creeper", EntitySporeCreeper.class, entityId++, 5576214, 15721297);
        registerEntity("monster_spore", EntitySpore.class, entityId++, 15721297, 5576214);
        registerEntity("monster_ghastling", EntityGhastling.class, entityId++, 16447992, 14077883);
        registerEntity("monster_bone_spider", EntityBoneSpider.class, entityId++, 13750221, 13369344);
        registerEntity("monster_brute", EntityBrute.class, entityId++, 11045525, 4793626);

        registerEntity("boss_ghast_queen", EntityGhastQueen.class, entityId++, 15790320, 13546448);

        LOGGER.info("EntityConfig registration Ended.");
    }

    private static void registerEntity(String name, Class<? extends Entity> cls, int id)
    {
        ResourceLocation registryName = new ResourceLocation(NetherEx.MOD_ID + ":" + name);
        EntityRegistry.registerModEntity(registryName, cls, CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, registryName.toString()), id, NetherEx.instance, 64, 1, true);
    }

    private static void registerEntity(String name, Class<? extends Entity> cls, int id, int primaryEggColor, int secondaryEggColor)
    {
        ResourceLocation registryName = new ResourceLocation(NetherEx.MOD_ID + ":" + name);
        EntityRegistry.registerModEntity(registryName, cls, CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, registryName.toString()), id, NetherEx.instance, 64, 1, true, primaryEggColor, secondaryEggColor);
    }
}
