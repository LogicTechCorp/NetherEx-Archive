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

package logictechcorp.netherex.world.biome;

import com.electronwill.nightconfig.core.Config;
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.feature.FeatureCluster;
import logictechcorp.libraryex.world.generation.feature.FeatureFluid;
import logictechcorp.libraryex.world.generation.feature.FeatureOre;
import logictechcorp.libraryex.world.generation.feature.FeatureScatter;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;

public final class NetherBiomeInfoHell extends NetherBiomeInfo
{
    public static final NetherBiomeInfo INSTANCE = new NetherBiomeInfoHell();

    private NetherBiomeInfoHell()
    {
        super(Biomes.HELL.getRegistryName(), 10, true, true);
    }

    @Override
    public Config getAsConfig()
    {
        this.getBiomeBlock(BlockType.FLOOR_TOP_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BlockType.FLOOR_FILLER_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BlockType.WALL_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BlockType.CEILING_FILLER_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BlockType.CEILING_BOTTOM_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BlockType.OCEAN_BLOCK, Blocks.LAVA.getDefaultState());
        this.getEntities(EnumCreatureType.MONSTER).addAll(Arrays.asList(
                new Biome.SpawnListEntry(EntityGhast.class, 50, 1, 4),
                new Biome.SpawnListEntry(EntityPigZombie.class, 100, 1, 4),
                new Biome.SpawnListEntry(EntityMagmaCube.class, 3, 1, 4),
                new Biome.SpawnListEntry(EntityEnderman.class, 1, 1, 4),
                new Biome.SpawnListEntry(EntityBlaze.class, 2, 1, 4)
        ));
        this.getFeatures(GenerationStage.PRE_DECORATE).addAll(Arrays.asList(
                new FeatureFluid(8, 1.0D, false, 4, 124, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), false),
                new FeatureScatter(10, 1.0D, true, 4, 124, Blocks.FIRE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), FeatureScatter.Placement.ON_GROUND),
                new FeatureCluster(10, 1.0D, true, 4, 124, Blocks.GLOWSTONE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), EnumFacing.DOWN),
                new FeatureCluster(10, 1.0D, false, 1, 128, Blocks.GLOWSTONE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), EnumFacing.DOWN),
                new FeatureFluid(16, 1.0D, false, 10, 118, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), true)
        ));
        this.getFeatures(GenerationStage.DECORATE).addAll(Arrays.asList(
                new FeatureScatter(1, 0.25D, false, 1, 128, Blocks.RED_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), FeatureScatter.Placement.ON_GROUND),
                new FeatureScatter(1, 0.25D, false, 1, 128, Blocks.BROWN_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), FeatureScatter.Placement.ON_GROUND)
        ));
        this.getFeatures(GenerationStage.ORE).addAll(Arrays.asList(
                new FeatureOre(16, 1.0D, false, 10, 108, Blocks.QUARTZ_ORE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), 14),
                new FeatureOre(4, 1.0D, false, 28, 38, Blocks.MAGMA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), 32)
        ));
        return super.getAsConfig();
    }
}
