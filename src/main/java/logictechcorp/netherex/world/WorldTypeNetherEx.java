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
 * along with this program.  If not, see <http:www.gnu.org/licenses/>.
 */

package logictechcorp.netherex.world;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.client.gui.GuiCustomizeWorld;
import logictechcorp.netherex.world.biome.design.BiomeProviderNetherEx;
import logictechcorp.netherex.world.generation.ChunkGeneratorNetherEx;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class WorldTypeNetherEx extends WorldType
{
    private BiomeProvider overworldBiomeProvider = null;
    private BiomeProvider netherBiomeProvider = null;
    private IChunkGenerator overworldChunkGenerator = null;
    private IChunkGenerator netherChunkGenerator = null;

    public WorldTypeNetherEx()
    {
        super(NetherEx.MOD_ID);
    }

    @Override
    public void onCustomizeButton(Minecraft minecraft, GuiCreateWorld guiCreateWorld)
    {
        List<WorldType> compatibleWorldTypes = new ArrayList<>();

        for(WorldType worldType : WORLD_TYPES)
        {
            if(worldType == null)
            {
                continue;
            }

            String worldTypeName = worldType.getName();

            if(NetherEx.LOST_CITIES_LOADED && NetherEx.BIOMES_O_PLENTY_LOADED)
            {
                if(worldTypeName.equals("lostcities_bop"))
                {
                    compatibleWorldTypes.add(worldType);
                }
            }
            else if(NetherEx.LOST_CITIES_LOADED)
            {
                if(worldTypeName.equals("lostcities"))
                {
                    compatibleWorldTypes.add(worldType);
                }
            }

            if(NetherEx.BIOMES_O_PLENTY_LOADED)
            {
                if(worldTypeName.equals("BIOMESOP"))
                {
                    compatibleWorldTypes.add(worldType);
                }
            }
        }

        if(compatibleWorldTypes.size() > 0)
        {
            minecraft.displayGuiScreen(new GuiCustomizeWorld(guiCreateWorld, compatibleWorldTypes));
        }
    }

    @Override
    public boolean isCustomizable()
    {
        return NetherEx.LOST_CITIES_LOADED || NetherEx.BIOMES_O_PLENTY_LOADED;
    }

    @Override
    public BiomeProvider getBiomeProvider(World world)
    {
        int dimension = world.provider.getDimension();

        if(dimension == DimensionType.OVERWORLD.getId())
        {
            if(this.overworldBiomeProvider == null)
            {
                WorldType originalWorldType = world.getWorldInfo().getTerrainType();
                WorldType compatibleWorldType = null;

                for(WorldType worldType : WORLD_TYPES)
                {
                    String worldTypeName = worldType.getName();

                    if(NetherEx.LOST_CITIES_LOADED && NetherEx.BIOMES_O_PLENTY_LOADED)
                    {
                        if(worldTypeName.equals("lostcities_bop"))
                        {
                            compatibleWorldType = worldType;
                            break;
                        }
                    }
                    else if(NetherEx.LOST_CITIES_LOADED)
                    {
                        if(worldTypeName.equals("lostcities"))
                        {
                            compatibleWorldType = worldType;
                            break;
                        }
                    }
                    else if(NetherEx.BIOMES_O_PLENTY_LOADED)
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
                    world.getWorldInfo().setTerrainType(compatibleWorldType);
                    this.overworldBiomeProvider = compatibleWorldType.getBiomeProvider(world);
                    world.getWorldInfo().setTerrainType(originalWorldType);
                }
                else
                {
                    this.overworldBiomeProvider = super.getBiomeProvider(world);
                }
            }

            return this.overworldBiomeProvider;
        }
        else if(dimension == DimensionType.NETHER.getId())
        {
            if(this.netherBiomeProvider == null)
            {
                this.netherBiomeProvider = new BiomeProviderNetherEx(world);
            }

            return this.netherBiomeProvider;
        }

        return super.getBiomeProvider(world);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslationKey()
    {
        return "generator." + NetherEx.MOD_ID + ":" + this.getName();
    }

    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions)
    {
        int dimension = world.provider.getDimension();

        if(dimension == DimensionType.OVERWORLD.getId())
        {
            if(this.overworldChunkGenerator == null)
            {
                WorldType originalWorldType = world.getWorldInfo().getTerrainType();
                WorldType compatibleWorldType = null;

                for(WorldType worldType : WORLD_TYPES)
                {
                    String worldTypeName = worldType.getName();

                    if(NetherEx.LOST_CITIES_LOADED && NetherEx.BIOMES_O_PLENTY_LOADED)
                    {
                        if(worldTypeName.equals("lostcities_bop"))
                        {
                            compatibleWorldType = worldType;
                            break;
                        }
                    }
                    else if(NetherEx.LOST_CITIES_LOADED)
                    {
                        if(worldTypeName.equals("lostcities"))
                        {
                            compatibleWorldType = worldType;
                            break;
                        }
                    }
                    else if(NetherEx.BIOMES_O_PLENTY_LOADED)
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
                    world.getWorldInfo().setTerrainType(compatibleWorldType);
                    this.overworldChunkGenerator = compatibleWorldType.getChunkGenerator(world, generatorOptions);
                    world.getWorldInfo().setTerrainType(originalWorldType);
                }
                else
                {
                    this.overworldChunkGenerator = super.getChunkGenerator(world, generatorOptions);
                }
            }

            return this.overworldChunkGenerator;
        }
        else if(dimension == DimensionType.NETHER.getId())
        {
            if(this.netherChunkGenerator == null)
            {
                this.netherChunkGenerator = new ChunkGeneratorNetherEx(world);
            }

            return this.netherChunkGenerator;
        }

        return super.getChunkGenerator(world, generatorOptions);
    }
}
