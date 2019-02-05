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
import logictechcorp.netherex.world.biome.BiomeProviderNetherEx;
import logictechcorp.netherex.world.generation.ChunkGeneratorNetherEx;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldTypeNetherEx extends WorldType
{
    private BiomeProvider overworldBiomeProvider = null;
    private BiomeProvider netherBiomeProvider = null;
    private IChunkGenerator netherChunkGenerator = null;

    public WorldTypeNetherEx()
    {
        super(NetherEx.MOD_ID);
    }

    @Override
    public BiomeProvider getBiomeProvider(World world)
    {
        int dimension = world.provider.getDimension();

        if(dimension == DimensionType.OVERWORLD.getId())
        {
            if(this.overworldBiomeProvider == null)
            {
                if(NetherEx.BIOMES_O_PLENTY_LOADED)
                {
                    for(WorldType worldType : WORLD_TYPES)
                    {
                        String worldTypeName = worldType.getName();

                        WorldType originalWorldType;

                        if(NetherEx.LOST_CITIES_LOADED)
                        {
                            if(worldTypeName.equalsIgnoreCase("lostcities_bop"))
                            {
                                originalWorldType = world.getWorldInfo().getTerrainType();
                            }
                            else
                            {
                                continue;
                            }
                        }
                        else if(worldTypeName.equalsIgnoreCase("BIOMESOP"))
                        {
                            originalWorldType = world.getWorldInfo().getTerrainType();
                        }
                        else
                        {
                            continue;
                        }

                        world.getWorldInfo().setTerrainType(worldType);
                        this.overworldBiomeProvider = worldType.getBiomeProvider(world);
                        world.getWorldInfo().setTerrainType(originalWorldType);
                        break;
                    }
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
        if(world.provider.getDimension() == DimensionType.NETHER.getId())
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
