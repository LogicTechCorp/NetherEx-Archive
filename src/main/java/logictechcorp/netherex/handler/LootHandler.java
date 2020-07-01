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

package logictechcorp.netherex.handler;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExLootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class LootHandler
{
    @SubscribeEvent
    public static void onLoot(LootTableLoadEvent event)
    {
        ResourceLocation name = event.getName();
        LootTable lootTable = event.getTable();

        if(lootTable != null)
        {
            LootPool mainPool = lootTable.getPool("main");

            if(mainPool != null)
            {
                if(name.equals(LootTableList.ENTITIES_GHAST))
                {
                    mainPool.addEntry(new LootEntryTable(NetherExLootTables.GHAST, 1, 0, new LootCondition[0], NetherEx.MOD_ID + ":ghast_meat_loot_pool"));
                }
                else if(name.equals(LootTableList.ENTITIES_WITHER_SKELETON))
                {
                    mainPool.addEntry(new LootEntryTable(NetherExLootTables.WITHER_SKELETON, 1, 0, new LootCondition[0], NetherEx.MOD_ID + ":wither_bone_loot_pool"));
                }
            }
        }
    }
}
