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

package logictechcorp.netherex.world;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.client.gui.screen.CustomizeNetherExWorldTypeScreen;
import logictechcorp.netherex.world.biome.provider.NetherBiomeProvider;
import logictechcorp.netherex.world.generation.NetherExChunkGenerators;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.NetherGenSettings;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public class NetherExWorldType extends WorldType
{
    public NetherExWorldType()
    {
        super(NetherEx.MOD_ID);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onCustomizeButton(Minecraft minecraft, CreateWorldScreen createWorldScreen)
    {
        boolean lcIsLoaded = ModList.get().isLoaded("lostcities");
        boolean bopIsLoaded = ModList.get().isLoaded("biomesoplenty");

        List<WorldType> compatibleWorldTypes = new ArrayList<>();

        for(WorldType worldType : WORLD_TYPES)
        {
            if(worldType == null)
            {
                continue;
            }

            String worldTypeName = worldType.getName();

            if(lcIsLoaded && bopIsLoaded)
            {
                if(worldTypeName.equals("lostcities_bop"))
                {
                    compatibleWorldTypes.add(worldType);
                }
            }
            else if(lcIsLoaded)
            {
                if(worldTypeName.equals("lostcities"))
                {
                    compatibleWorldTypes.add(worldType);
                }
            }

            if(bopIsLoaded)
            {
                if(worldTypeName.equals("biomesoplenty"))
                {
                    compatibleWorldTypes.add(worldType);
                }
            }
        }

        if(compatibleWorldTypes.size() > 0)
        {
            minecraft.displayGuiScreen(new CustomizeNetherExWorldTypeScreen(createWorldScreen, compatibleWorldTypes));
        }
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator(World world)
    {
        boolean lcIsLoaded = ModList.get().isLoaded("lostcities");
        boolean bopIsLoaded = ModList.get().isLoaded("biomesoplenty");
        DimensionType dimension = world.getDimension().getType();

        if(dimension == DimensionType.OVERWORLD)
        {
            ChunkGenerator overworldChunkGenerator;

            WorldType originalWorldType = world.getWorldInfo().getGenerator();
            WorldType compatibleWorldType = null;

            for(WorldType worldType : WORLD_TYPES)
            {
                if(worldType == null)
                {
                    continue;
                }

                String worldTypeName = worldType.getName();

                if(lcIsLoaded && bopIsLoaded)
                {
                    if(worldTypeName.equals("lostcities_bop"))
                    {
                        compatibleWorldType = worldType;
                        break;
                    }
                }
                else if(lcIsLoaded)
                {
                    if(worldTypeName.equals("lostcities"))
                    {
                        compatibleWorldType = worldType;
                        break;
                    }
                }
                else if(bopIsLoaded)
                {
                    if(worldTypeName.equals("BIOMESOP"))
                    {
                        compatibleWorldType = worldType;
                        break;
                    }
                }
            }

            if(compatibleWorldType != null)
            {
                world.getWorldInfo().setGenerator(compatibleWorldType);
                overworldChunkGenerator = compatibleWorldType.createChunkGenerator(world);
                world.getWorldInfo().setGenerator(originalWorldType);
            }
            else
            {
                overworldChunkGenerator = super.createChunkGenerator(world);
            }

            return overworldChunkGenerator;
        }
        else if(dimension == DimensionType.THE_NETHER)
        {
            NetherGenSettings netherGenSettings = ChunkGeneratorType.CAVES.createSettings();
            netherGenSettings.setDefaultBlock(Blocks.NETHERRACK.getDefaultState());
            netherGenSettings.setDefaultFluid(Blocks.LAVA.getDefaultState());

            OverworldBiomeProviderSettings biomeProviderSettings = new OverworldBiomeProviderSettings();
            biomeProviderSettings.setGeneratorSettings(new OverworldGenSettings());
            biomeProviderSettings.setWorldInfo(world.getWorldInfo());
            return NetherExChunkGenerators.NETHER.create(world, new NetherBiomeProvider(biomeProviderSettings), netherGenSettings);
        }

        return super.createChunkGenerator(world);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasCustomOptions()
    {
        return ModList.get().isLoaded("lostcities") || ModList.get().isLoaded("biomesoplenty");
    }
}