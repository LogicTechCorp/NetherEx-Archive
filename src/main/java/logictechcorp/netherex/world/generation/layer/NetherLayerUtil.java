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

package logictechcorp.netherex.world.generation.layer;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.LongFunction;

public class NetherLayerUtil
{

    public static Layer[] createLayers(long seed, WorldType worldType, OverworldGenSettings generatorSettings)
    {
        List<IAreaFactory<LazyArea>> areas = createAreas(worldType, generatorSettings, (seedModifier) -> new LazyAreaLayerContext(25, seed, seedModifier));
        return new Layer[]{new Layer(areas.get(0)), new Layer(areas.get(1)), new Layer(areas.get(2))};
    }

    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> List<IAreaFactory<T>> createAreas(WorldType worldType, OverworldGenSettings generatorSettings, LongFunction<C> contextFactory)
    {
        IAreaFactory<T> baseAreaFactory = IslandLayer.INSTANCE.apply(contextFactory.apply(1L));
        IAreaFactory<T> biomeAreaFactory = NetherBiomeLayer.INSTANCE.apply(contextFactory.apply(200L), baseAreaFactory);
        IAreaFactory<T> subBiomeAreaFactory = StartRiverLayer.INSTANCE.apply(contextFactory.apply(200L), biomeAreaFactory);
        IAreaFactory<T> zoomedAreaFactory;

        biomeAreaFactory = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, biomeAreaFactory, 2, contextFactory);
        subBiomeAreaFactory = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, subBiomeAreaFactory, 2, contextFactory);
        baseAreaFactory = NetherSubBiomeLayer.INSTANCE.apply(contextFactory.apply(1000L), biomeAreaFactory, subBiomeAreaFactory);
        baseAreaFactory = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, baseAreaFactory, LayerUtil.getModdedBiomeSize(worldType, generatorSettings.getBiomeSize()), contextFactory);
        baseAreaFactory = SmoothLayer.INSTANCE.apply(contextFactory.apply(1000L), baseAreaFactory);
        zoomedAreaFactory = VoroniZoomLayer.INSTANCE.apply(contextFactory.apply(10L), baseAreaFactory);
        return Arrays.asList(baseAreaFactory, zoomedAreaFactory, baseAreaFactory);
    }
}
