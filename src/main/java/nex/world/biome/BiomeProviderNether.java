/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

import java.lang.reflect.Field;

public class BiomeProviderNether extends BiomeProvider
{
    private static final Field FIELD_GEN_BIOMES = ReflectionHelper.findField(BiomeProvider.class, "field_76944_d", "genBiomes");
    private static final Field FIELD_BIOME_INDEX_LAYER = ReflectionHelper.findField(BiomeProvider.class, "field_76945_e", "biomeIndexLayer");

    public BiomeProviderNether(World world)
    {
        super();

        GenLayer[] genLayers = GenLayerNetherEx.initializeAllBiomeGenerators(world.getSeed(), world.getWorldType());

        try
        {
            FIELD_GEN_BIOMES.set(this, genLayers[0]);
            FIELD_BIOME_INDEX_LAYER.set(this, genLayers[1]);
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}
