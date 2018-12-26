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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class NetherExLootTables
{
    public static final ResourceLocation PIGTIFICATE_CHIEF = new ResourceLocation(NetherEx.MOD_ID + ":entity/pigtificate/pigtificate_chief");
    public static final ResourceLocation PIGTIFICATE_HUNTER = new ResourceLocation(NetherEx.MOD_ID + ":entity/pigtificate/pigtificate_hunter");
    public static final ResourceLocation PIGTIFICATE_GATHERER = new ResourceLocation(NetherEx.MOD_ID + ":entity/pigtificate/pigtificate_gatherer");
    public static final ResourceLocation PIGTIFICATE_SCAVENGER = new ResourceLocation(NetherEx.MOD_ID + ":entity/pigtificate/pigtificate_scavenger");
    public static final ResourceLocation PIGTIFICATE_ARMORSMITH = new ResourceLocation(NetherEx.MOD_ID + ":entity/pigtificate/pigtificate_armorsmith");
    public static final ResourceLocation PIGTIFICATE_TOOLSMITH = new ResourceLocation(NetherEx.MOD_ID + ":entity/pigtificate/pigtificate_toolsmith");
    public static final ResourceLocation PIGTIFICATE_ENCHANTER = new ResourceLocation(NetherEx.MOD_ID + ":entity/pigtificate/pigtificate_enchanter");
    public static final ResourceLocation PIGTIFICATE_BREWER = new ResourceLocation(NetherEx.MOD_ID + ":entity/pigtificate/pigtificate_brewer");
    public static final ResourceLocation BONSPIDER = new ResourceLocation(NetherEx.MOD_ID + ":entity/bonspider");
    public static final ResourceLocation GOLD_GOLEM = new ResourceLocation(NetherEx.MOD_ID + ":entity/gold_golem");
    public static final ResourceLocation BROWN_MOGUS = new ResourceLocation(NetherEx.MOD_ID + ":entity/mogus/mogus_brown");
    public static final ResourceLocation RED_MOGUS = new ResourceLocation(NetherEx.MOD_ID + ":entity/mogus/mogus_red");
    public static final ResourceLocation WHITE_MOGUS = new ResourceLocation(NetherEx.MOD_ID + ":entity/mogus/mogus_white");
    public static final ResourceLocation ORANGE_SALAMANDER = new ResourceLocation(NetherEx.MOD_ID + ":entity/salamander/salamander_orange");
    public static final ResourceLocation BLACK_SALAMANDER = new ResourceLocation(NetherEx.MOD_ID + ":entity/salamander/salamander_black");
    public static final ResourceLocation WIGHT = new ResourceLocation(NetherEx.MOD_ID + ":entity/wight");
    public static final ResourceLocation EMBER = new ResourceLocation(NetherEx.MOD_ID + ":entity/ember");
    public static final ResourceLocation NETHERMITE = new ResourceLocation(NetherEx.MOD_ID + ":entity/nethermite");
    public static final ResourceLocation SPINOUT = new ResourceLocation(NetherEx.MOD_ID + ":entity/spinout");
    public static final ResourceLocation SPORE_CREEPER = new ResourceLocation(NetherEx.MOD_ID + ":entity/spore_creeper");
    public static final ResourceLocation SPORE = new ResourceLocation(NetherEx.MOD_ID + ":entity/spore");
    public static final ResourceLocation GHASTLING = new ResourceLocation(NetherEx.MOD_ID + ":entity/ghast/ghastling");
    public static final ResourceLocation COOLMAR_SPIDER = new ResourceLocation(NetherEx.MOD_ID + ":entity/bone_spider");
    public static final ResourceLocation FROST = new ResourceLocation(NetherEx.MOD_ID + ":entity/frost");
    public static final ResourceLocation BRUTE = new ResourceLocation(NetherEx.MOD_ID + ":entity/brute");
    public static final ResourceLocation GHAST_QUEEN = new ResourceLocation(NetherEx.MOD_ID + ":entity/ghast/ghast_queen");
    
    public static void registerLootTables()
    {
        LootTableList.register(PIGTIFICATE_CHIEF);
        LootTableList.register(PIGTIFICATE_HUNTER);
        LootTableList.register(PIGTIFICATE_GATHERER);
        LootTableList.register(PIGTIFICATE_SCAVENGER);
        LootTableList.register(PIGTIFICATE_ARMORSMITH);
        LootTableList.register(PIGTIFICATE_TOOLSMITH);
        LootTableList.register(PIGTIFICATE_ENCHANTER);
        LootTableList.register(PIGTIFICATE_BREWER);
        LootTableList.register(BONSPIDER);
        LootTableList.register(GOLD_GOLEM);
        LootTableList.register(BROWN_MOGUS);
        LootTableList.register(RED_MOGUS);
        LootTableList.register(WHITE_MOGUS);
        LootTableList.register(ORANGE_SALAMANDER);
        LootTableList.register(BLACK_SALAMANDER);
        LootTableList.register(WIGHT);
        LootTableList.register(EMBER);
        LootTableList.register(NETHERMITE);
        LootTableList.register(SPINOUT);
        LootTableList.register(SPORE_CREEPER);
        LootTableList.register(SPORE);
        LootTableList.register(GHASTLING);
        LootTableList.register(COOLMAR_SPIDER);
        LootTableList.register(FROST);
        LootTableList.register(BRUTE);
        LootTableList.register(GHAST_QUEEN);
    }
}
