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

package logictechcorp.netherex.proxy;

import logictechcorp.libraryex.proxy.IProxy;
import logictechcorp.libraryex.resource.OptionalResourcePack;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.world.generation.NetherExChunkGenerators;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

public class CommonProxy implements IProxy
{
    @Override
    public void registerHandlers()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onCommonSetup);

        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.addListener(this::onServerAboutToStart);
        forgeEventBus.addListener(this::onServerStopping);
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        //Temporary workaround
        try
        {
            Field cavesField = ObfuscationReflectionHelper.findField(ChunkGeneratorType.class, "field_206912_c");
            FieldUtils.removeFinalModifier(cavesField, true);
            FieldUtils.writeStaticField(cavesField, NetherExChunkGenerators.CAVES, true);
        }
        catch(ReflectiveOperationException e)
        {
            e.printStackTrace();
        }
    }

    private void onServerAboutToStart(FMLServerAboutToStartEvent event)
    {
        MinecraftServer server = event.getServer();
        ResourcePackList<ResourcePackInfo> resourcePacks = server.getResourcePacks();
        ModFile modFile = ModList.get().getModFileById(NetherEx.MOD_ID).getFile();

        if(NetherExConfig.NETHER.biomePackUseDefaultBiomePack.get())
        {
            resourcePacks.addPackFinder(new OptionalResourcePack(modFile, "biome_pack", true));
        }

        if(NetherExConfig.NETHER.biomePackUseLegacyBiomePack.get())
        {
            resourcePacks.addPackFinder(new OptionalResourcePack(modFile, "legacy_biome_pack", true));
        }

        if(NetherExConfig.NETHER.biomePackUseBOPBiomePack.get())
        {
            resourcePacks.addPackFinder(new OptionalResourcePack(modFile, "bop_biome_pack", true));
        }

        server.getResourceManager().addReloadListener(NetherEx.BIOME_DATA_MANAGER);
    }

    private void onServerStopping(FMLServerStoppingEvent event)
    {
        NetherEx.BIOME_DATA_MANAGER.cleanup();
    }
}
