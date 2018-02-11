/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

package nex.world.biome;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import nex.world.gen.layer.GenLayerNetherEx;

public class BiomeProviderNether extends BiomeProvider
{
    public BiomeProviderNether(World world)
    {
        super();

        GenLayer[] genLayers = GenLayerNetherEx.initializeAllBiomeGenerators(world.getSeed(), world.getWorldType());

        ReflectionHelper.setPrivateValue(BiomeProvider.class, this, genLayers[0], "field_76944_d", "genBiomes");
        ReflectionHelper.setPrivateValue(BiomeProvider.class, this, genLayers[1], "field_76945_e", "biomeIndexLayer");
    }
}
