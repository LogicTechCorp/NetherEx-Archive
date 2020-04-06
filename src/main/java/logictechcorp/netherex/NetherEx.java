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

package logictechcorp.netherex;

import com.mojang.brigadier.CommandDispatcher;
import logictechcorp.libraryex.world.biome.BiomeDataManager;
import logictechcorp.netherex.block.NetherExBlocks;
import logictechcorp.netherex.command.NetherExCommand;
import logictechcorp.netherex.entity.NetherExEntityTypes;
import logictechcorp.netherex.item.NetherExItems;
import logictechcorp.netherex.particle.NetherExParticles;
import logictechcorp.netherex.potion.NetherExEffects;
import logictechcorp.netherex.potion.NetherExPotions;
import logictechcorp.netherex.proxy.ClientProxy;
import logictechcorp.netherex.proxy.ServerProxy;
import logictechcorp.netherex.tileentity.NetherExTileEntityTypes;
import logictechcorp.netherex.utility.NetherExSoundEvents;
import logictechcorp.netherex.world.biome.NetherBiomeDataManager;
import logictechcorp.netherex.world.biome.NetherExBiomes;
import logictechcorp.netherex.world.generation.NetherExChunkGenerators;
import logictechcorp.netherex.world.generation.carver.NetherExCarvers;
import logictechcorp.netherex.world.generation.feature.NetherExFeatures;
import logictechcorp.netherex.world.generation.placement.NetherExPlacements;
import logictechcorp.netherex.world.generation.surfacebuilder.NetherExSurfaceBuilders;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

@Mod(NetherEx.MOD_ID)
public class NetherEx
{
    public static final String MOD_ID = "netherex";
    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID)
    {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon()
        {
            return new ItemStack(Blocks.NETHERRACK);
        }
    };
    public static final BiomeDataManager BIOME_DATA_MANAGER = new NetherBiomeDataManager();

    public static final Logger LOGGER = LogManager.getLogger("NetherEx");

    public NetherEx()
    {
        DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onCommonSetup);
        NetherExBlocks.register(modEventBus);
        NetherExItems.register(modEventBus);
        NetherExTileEntityTypes.register(modEventBus);
        NetherExBiomes.register(modEventBus);
        NetherExEntityTypes.register(modEventBus);
        NetherExChunkGenerators.registerOverrides(modEventBus);
        NetherExSurfaceBuilders.register(modEventBus);
        NetherExCarvers.registerOverrides(modEventBus);
        NetherExFeatures.register(modEventBus);
        NetherExFeatures.registerOverrides(modEventBus);
        NetherExPlacements.register(modEventBus);
        NetherExParticles.register(modEventBus);
        NetherExEffects.register(modEventBus);
        NetherExPotions.register(modEventBus);
        NetherExSoundEvents.register(modEventBus);

        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.addListener(this::onServerAboutToStart);
        forgeEventBus.addListener(this::onServerStarting);
        forgeEventBus.addListener(this::onServerStopping);
        NetherExConfig.registerConfigs();
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        //Temporary workaround
        try
        {
            Field cavesGeneratorField = ObfuscationReflectionHelper.findField(ChunkGeneratorType.class, "field_206912_c");
            FieldUtils.removeFinalModifier(cavesGeneratorField, true);
            FieldUtils.writeStaticField(cavesGeneratorField, NetherExChunkGenerators.CAVES.get(), true);

            Field netherCarverField = ObfuscationReflectionHelper.findField(WorldCarver.class, "field_222710_b");
            FieldUtils.removeFinalModifier(netherCarverField, true);
            FieldUtils.writeStaticField(netherCarverField, NetherExCarvers.NETHER_CAVE.get(), true);
        }
        catch(ReflectiveOperationException e)
        {
            e.printStackTrace();
        }

        NetherExEntityTypes.registerSpawnPlacements();
    }

    private void onServerAboutToStart(FMLServerAboutToStartEvent event)
    {
        MinecraftServer server = event.getServer();
        server.getResourceManager().addReloadListener(BIOME_DATA_MANAGER);
        NetherExBiomes.registerBiomePacks(server);
    }

    private void onServerStarting(FMLServerStartingEvent event)
    {
        CommandDispatcher<CommandSource> dispatcher = event.getCommandDispatcher();
        NetherExCommand.register(dispatcher);
    }

    private void onServerStopping(FMLServerStoppingEvent event)
    {
        BIOME_DATA_MANAGER.cleanup();
    }
}
