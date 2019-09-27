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

package logictechcorp.netherex.world.generation;

import logictechcorp.netherex.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.*;

import java.util.function.Supplier;

public class CaveChunkGeneratorTypeOverride extends ChunkGeneratorType<NetherGenSettings, NetherChunkGenerator>
{
    private final IChunkGeneratorFactory<NetherGenSettings, NetherChunkGenerator> originalFactory;

    public CaveChunkGeneratorTypeOverride(IChunkGeneratorFactory<NetherGenSettings, NetherChunkGenerator> chunkGeneratorFactory, boolean isOptionForBuffetWorld, Supplier<NetherGenSettings> genSettings)
    {
        super(chunkGeneratorFactory, isOptionForBuffetWorld, genSettings);
        this.originalFactory = NetherChunkGenerator::new;
    }

    @Override
    public NetherChunkGenerator create(World world, BiomeProvider biomeProvider, NetherGenSettings genSettings)
    {
        if(world.getDimension().getType() == DimensionType.THE_NETHER)
        {
            if(!(biomeProvider instanceof NetherBiomeProvider))
            {
                OverworldBiomeProviderSettings biomeProviderSettings = new OverworldBiomeProviderSettings();
                biomeProviderSettings.setGeneratorSettings(new OverworldGenSettings());
                biomeProviderSettings.setWorldInfo(world.getWorldInfo());
                biomeProvider = new NetherBiomeProvider(biomeProviderSettings);
            }

            return super.create(world, biomeProvider, genSettings);
        }

        return this.originalFactory.create(world, biomeProvider, genSettings);
    }
}
