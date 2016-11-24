/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.world;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.handler.ConfigurationHandler;
import nex.world.biome.BiomeProviderNether;
import nex.world.gen.ChunkProviderNether;

public class WorldProviderNether extends WorldProviderHell
{
    @Override
    public void init()
    {
        biomeProvider = new BiomeProviderNether(world.getSeed());
        hasNoSky = true;
        setDimension(-1);
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkProviderNether(world);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int chunkX, int chunkZ)
    {
        return !ConfigurationHandler.Client.disableNetherFog;
    }
}