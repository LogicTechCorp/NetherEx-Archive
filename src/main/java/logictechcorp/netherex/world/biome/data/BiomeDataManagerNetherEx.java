package logictechcorp.netherex.world.biome.data;

import logictechcorp.libraryex.LibraryEx;
import logictechcorp.libraryex.utility.WorldHelper;
import logictechcorp.libraryex.world.biome.data.BiomeData;
import logictechcorp.libraryex.world.biome.data.BiomeDataManager;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.NetherExConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.WorldEvent;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BiomeDataManagerNetherEx extends BiomeDataManager
{
    public BiomeDataManagerNetherEx(String loggerName)
    {
        super(loggerName);
    }

    @Override
    public void setup()
    {
        super.setup();

        if(NetherExConfig.biome.general.useGlobalBiomeConfigs)
        {
            Path globalBiomeConfigDirectoryPath = LibraryEx.CONFIG_DIRECTORY.toPath().resolve(NetherEx.MOD_ID).resolve("nether_biomes");
            this.createBiomeDataConfigs(globalBiomeConfigDirectoryPath);
        }
    }

    @Override
    public void onWorldLoad(WorldEvent.Load event)
    {
        World world = event.getWorld();

        if(!world.isRemote)
        {
            if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
            {
                if(NetherExConfig.dimension.nether.overrideNether)
                {
                    if(NetherExConfig.biome.general.useGlobalBiomeConfigs)
                    {
                        Path globalBiomeConfigDirectoryPath = LibraryEx.CONFIG_DIRECTORY.toPath().resolve(NetherEx.MOD_ID).resolve("nether_biomes");
                        this.readBiomeDataConfigs(globalBiomeConfigDirectoryPath);
                    }

                    if(NetherExConfig.biome.general.usePerWorldBiomeConfigs)
                    {
                        Path perWorldBiomeConfigDirectoryPath = Paths.get(WorldHelper.getSaveDirectory(event.getWorld()), "config", NetherEx.MOD_ID, "nether_biomes");
                        this.createBiomeDataConfigs(perWorldBiomeConfigDirectoryPath);
                        this.readBiomeDataConfigs(perWorldBiomeConfigDirectoryPath);
                    }

                    if(NetherEx.BIOMES_O_PLENTY_LOADED && !world.getWorldType().getName().equals("BIOMESOP"))
                    {
                        List<Biome> biomes = new ArrayList<>();
                        this.getCurrentBiomeData().forEach((resourceLocation, biomeData) ->
                        {
                            if(resourceLocation.getNamespace().equals("biomesoplenty"))
                            {
                                biomes.add(biomeData.getBiome());
                            }
                        });
                        biomes.forEach(this::unregisterBiomeData);
                    }
                }
            }
        }
    }

    @Override
    public void onWorldUnload(WorldEvent.Unload event)
    {
        World world = event.getWorld();

        if(!world.isRemote)
        {
            if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
            {
                this.currentBiomeData.clear();
                this.currentBiomeEntries.clear();
            }
        }
    }

    @Override
    public BiomeData createBiomeData(ResourceLocation biomeRegistryName, int generationWeight, boolean useDefaultDecorations, boolean isSubBiome)
    {
        if(biomeRegistryName.getNamespace().equals("biomesoplenty"))
        {
            return new BiomeDataBOP(biomeRegistryName, generationWeight, useDefaultDecorations, isSubBiome);
        }

        return new BiomeDataNetherEx(biomeRegistryName, generationWeight, useDefaultDecorations, isSubBiome);
    }
}
