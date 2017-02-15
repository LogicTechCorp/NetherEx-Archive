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
import nex.entity.item.EntityObsidianBoat;
import nex.entity.monster.*;
import nex.entity.neutral.EntityMogus;
import nex.entity.neutral.EntitySalamander;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetherExEntities
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExEntities");

    public static void init()
    {
        LOGGER.info("Entity registration started.");

        registerEntity("item_boat_obsidian", EntityObsidianBoat.class, 0);

        registerEntity("neutral_mogus", EntityMogus.class, 20, 16711680, 16762880);
        registerEntity("neutral_salamander", EntitySalamander.class, 21, 16711680, 16762880);

        registerEntity("monster_wight", EntityWight.class, 30, 16711680, 16762880);
        registerEntity("monster_ember", EntityEmber.class, 31, 16711680, 16762880);
        registerEntity("monster_nethermite", EntityNethermite.class, 32, 16711680, 16762880);
        registerEntity("monster_spinout", EntitySpinout.class, 33, 16711680, 16762880);
        registerEntity("monster_spore_creeper", EntitySporeCreeper.class, 34, 16711680, 16762880);
        registerEntity("monster_spore", EntitySpore.class, 35, 16711680, 16762880);
        registerEntity("monster_ghast_queen", EntityGhastQueen.class, 36, 16711680, 16762880);

        LOGGER.info("Entity registration Ended.");
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
