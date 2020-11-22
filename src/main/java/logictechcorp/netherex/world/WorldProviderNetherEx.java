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

import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.world.biome.design.BiomeProviderNetherEx;
import logictechcorp.netherex.world.generation.ChunkGeneratorNetherEx;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderNetherEx extends WorldProviderHell
{
    @Override
    public void init()
    {
        super.init();
        this.biomeProvider = new BiomeProviderNetherEx(this.world);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return !NetherExConfig.client.visual.disableNetherFog;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorNetherEx(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getSeed());
    }
}
