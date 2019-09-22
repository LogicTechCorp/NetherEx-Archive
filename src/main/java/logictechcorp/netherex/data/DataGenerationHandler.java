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

package logictechcorp.netherex.data;

import logictechcorp.libraryex.data.generator.loottable.BlockLootTableGenerator;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.block.NetherExBlocks;
import logictechcorp.netherex.item.NetherExItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(NetherEx.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerationHandler
{
    @SubscribeEvent
    public static void onDataGeneration(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();

        if(event.includeServer())
        {
            generator.addProvider(gatherBlockLootTables(generator));
        }
    }

    private static IDataProvider gatherBlockLootTables(DataGenerator generator)
    {
        BlockLootTableGenerator lootTableGenerator = new BlockLootTableGenerator(generator, NetherEx.MOD_ID);

        for(Block block : ForgeRegistries.BLOCKS)
        {
            if(block.getRegistryName().getNamespace().equals(NetherEx.MOD_ID))
            {
                if(block == NetherExBlocks.QUARTZ_ORE)
                {
                    continue;
                }

                if(block.getLootTable() != LootTables.EMPTY)
                {
                    lootTableGenerator.addBasicLootTable(block);
                }
            }
        }

        lootTableGenerator.addOreLootTable(NetherExBlocks.AMETHYST_ORE, NetherExItems.AMETHYST_CRYSTAL).addOreLootTable(NetherExBlocks.RIME_ORE, NetherExItems.RIME_CRYSTAL);
        return lootTableGenerator;
    }
}
