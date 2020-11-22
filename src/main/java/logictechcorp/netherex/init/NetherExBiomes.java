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
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitOre;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitRegistry;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitStructure;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.world.biome.BiomeArcticAbyss;
import logictechcorp.netherex.world.biome.BiomeFungiForest;
import logictechcorp.netherex.world.biome.BiomeRuthlessSands;
import logictechcorp.netherex.world.biome.BiomeTorridWasteland;
import logictechcorp.netherex.world.biome.data.BiomeDataBOP;
import logictechcorp.netherex.world.biome.data.BiomeDataHell;
import logictechcorp.netherex.world.generation.trait.BiomeTraitEnoki;
import logictechcorp.netherex.world.generation.trait.BiomeTraitThornstalk;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Collections;

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

    public static void registerBiomeTypes()
    {
        BiomeDictionary.addTypes(RUTHLESS_SANDS, NETHER, HOT, DRY, SANDY);
        BiomeDictionary.addTypes(FUNGI_FOREST, NETHER, COLD, WET, MUSHROOM);
        BiomeDictionary.addTypes(TORRID_WASTELAND, NETHER, HOT, DRY, WASTELAND);
        BiomeDictionary.addTypes(ARCTIC_ABYSS, NETHER, COLD, WET);
    }

    public static void registerBiomeData()
    {
        BiomeTraitRegistry.INSTANCE.registerBiomeTrait(NetherEx.getResource("thornstalk"), new BiomeTraitThornstalk.Builder(), BiomeTraitThornstalk.class);
        BiomeTraitRegistry.INSTANCE.registerBiomeTrait(NetherEx.getResource("enoki"), new BiomeTraitEnoki.Builder(), BiomeTraitEnoki.class);

        NetherEx.BIOME_DATA_MANAGER.registerBiomeData(BiomeDataHell.INSTANCE);
        NetherEx.BIOME_DATA_MANAGER.registerBiomeData(RUTHLESS_SANDS.getBiomeData());
        NetherEx.BIOME_DATA_MANAGER.registerBiomeData(FUNGI_FOREST.getBiomeData());
        NetherEx.BIOME_DATA_MANAGER.registerBiomeData(TORRID_WASTELAND.getBiomeData());
        NetherEx.BIOME_DATA_MANAGER.registerBiomeData(ARCTIC_ABYSS.getBiomeData());

        BiomeDataHell.INSTANCE.getBiomeTraits(GenerationStage.STRUCTURE).add(
                BiomeTraitStructure.create(trait ->
                {
                    trait.generationAttempts(1);
                    trait.generationProbability(0.25D);
                    trait.minimumGenerationHeight(32);
                    trait.maximumGenerationHeight(118);
                    trait.structures(Collections.singletonList(NetherEx.getResource("pigtificate_village/tiny_pigtificate_village")));
                    trait.structureType(BiomeTraitStructure.StructureType.GROUND);
                }));
        BiomeDataHell.INSTANCE.getBiomeTraits(GenerationStage.ORE).add(
                BiomeTraitOre.create(trait ->
                {
                    trait.generationAttempts(8);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(108);
                    trait.blockToSpawn(NetherExBlocks.AMETHYST_ORE.getDefaultState());
                    trait.blockToReplace(Blocks.NETHERRACK.getDefaultState());
                    trait.veinSize(7);
                })
        );

        if(NetherEx.BIOMES_O_PLENTY_LOADED && NetherExConfig.compatibility.biomesOPlenty.enableCompatibility)
        {
            NetherEx.BIOME_DATA_MANAGER.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:corrupted_sands"), 8, true, false));
            NetherEx.BIOME_DATA_MANAGER.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:fungi_forest"), 4, true, false));
            NetherEx.BIOME_DATA_MANAGER.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:phantasmagoric_inferno"), 6, true, false));
            NetherEx.BIOME_DATA_MANAGER.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:undergarden"), 4, true, false));
            NetherEx.BIOME_DATA_MANAGER.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:visceral_heap"), 4, true, false));
        }
    }
}
