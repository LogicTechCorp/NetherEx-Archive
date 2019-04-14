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

package logictechcorp.netherex.init;

import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.libraryex.world.biome.IBiomeDataRegistry;
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.feature.FeatureOre;
import logictechcorp.libraryex.world.generation.feature.FeatureStructure;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.api.NetherExAPI;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.world.biome.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExBiomes
{
    public static final BiomeRuthlessSands RUTHLESS_SANDS = InjectionHelper.nullValue();
    public static final BiomeFungiForest FUNGI_FOREST = InjectionHelper.nullValue();
    public static final BiomeTorridWasteland TORRID_WASTELAND = InjectionHelper.nullValue();
    public static final BiomeArcticAbyss ARCTIC_ABYSS = InjectionHelper.nullValue();

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBiomes(RegistryEvent.Register<Biome> event)
        {
            event.getRegistry().registerAll(
                    new BiomeRuthlessSands(),
                    new BiomeFungiForest(),
                    new BiomeTorridWasteland(),
                    new BiomeArcticAbyss()
            );
        }
    }

    public static void registerBiomes()
    {
        BiomeDictionary.addTypes(RUTHLESS_SANDS, NETHER, HOT, DRY, SANDY);
        BiomeDictionary.addTypes(FUNGI_FOREST, NETHER, HOT, DRY, MUSHROOM);
        BiomeDictionary.addTypes(TORRID_WASTELAND, NETHER, HOT, DRY, WASTELAND);
        BiomeDictionary.addTypes(ARCTIC_ABYSS, NETHER, WET, COLD);

        NetherBiomeDataHell.INSTANCE.getFeatures(GenerationStage.PRE_DECORATE).add(new FeatureStructure(1, 1.0D, false, 32, 116, NetherEx.getResource("village/tiny_hell_pigtificate_village"), FeatureStructure.Type.GROUNDED, Blocks.STRUCTURE_VOID, 0.75D));
        NetherBiomeDataHell.INSTANCE.getFeatures(GenerationStage.ORE).add(new FeatureOre(16, 1.0D, false, 10, 108, NetherExBlocks.AMETHYST_ORE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), 3));

        IBiomeDataRegistry biomeDataRegistry = NetherExAPI.getInstance().getNetherBiomeDataRegistry();
        biomeDataRegistry.registerBiomeData(NetherBiomeDataHell.INSTANCE);
        biomeDataRegistry.registerBiomeData(NetherExBiomes.RUTHLESS_SANDS.getBiomeData());
        biomeDataRegistry.registerBiomeData(NetherExBiomes.FUNGI_FOREST.getBiomeData());
        biomeDataRegistry.registerBiomeData(NetherExBiomes.TORRID_WASTELAND.getBiomeData());
        biomeDataRegistry.registerBiomeData(NetherExBiomes.ARCTIC_ABYSS.getBiomeData());

        if(NetherEx.BIOMES_O_PLENTY_LOADED && ConfigHandler.compatibilityConfig.biomesOPlenty.enableCompatibility)
        {
            biomeDataRegistry.registerBiomeData(new NetherBiomeDataBOP(new ResourceLocation("biomesoplenty:corrupted_sands"), 8, true, true));
            biomeDataRegistry.registerBiomeData(new NetherBiomeDataBOP(new ResourceLocation("biomesoplenty:fungi_forest"), 4, true, true));
            biomeDataRegistry.registerBiomeData(new NetherBiomeDataBOP(new ResourceLocation("biomesoplenty:phantasmagoric_inferno"), 6, true, true));
            biomeDataRegistry.registerBiomeData(new NetherBiomeDataBOP(new ResourceLocation("biomesoplenty:undergarden"), 4, true, true));
            biomeDataRegistry.registerBiomeData(new NetherBiomeDataBOP(new ResourceLocation("biomesoplenty:visceral_heap"), 4, true, true));
        }
    }
}
