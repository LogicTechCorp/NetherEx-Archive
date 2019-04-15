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
import logictechcorp.libraryex.world.biome.BiomeBlock;
import logictechcorp.libraryex.world.biome.BiomeDataConfigurable;
import logictechcorp.libraryex.world.biome.IBiomeData;
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitCluster;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitFluid;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitOre;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitScatter;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;

public final class NetherBiomeDataHell extends BiomeDataConfigurable
{
    public static final IBiomeData INSTANCE = new NetherBiomeDataHell();

    private NetherBiomeDataHell()
    {
        super(Biomes.HELL, 10, true, true);
    }

    @Override
    public void writeToConfig(Config config)
    {
        this.getBiomeBlock(BiomeBlock.FLOOR_TOP_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlock.FLOOR_FILLER_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlock.WALL_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlock.CEILING_FILLER_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlock.CEILING_BOTTOM_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlock.OCEAN_BLOCK, Blocks.LAVA.getDefaultState());
        this.getBiomeEntities(EnumCreatureType.MONSTER).addAll(Arrays.asList(
                new Biome.SpawnListEntry(EntityGhast.class, 50, 1, 4),
                new Biome.SpawnListEntry(EntityPigZombie.class, 100, 1, 4),
                new Biome.SpawnListEntry(EntityMagmaCube.class, 3, 1, 4),
                new Biome.SpawnListEntry(EntityEnderman.class, 1, 1, 4)
        ));
        this.getBiomeTraits(GenerationStage.PRE_DECORATE).addAll(Arrays.asList(
                new BiomeTraitFluid(8, false, 1.0D, 4, 124, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), false),
                new BiomeTraitScatter(10, true, 1.0D, 4, 124, Blocks.FIRE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), BiomeTraitScatter.Placement.ON_GROUND),
                new BiomeTraitCluster(10, true, 1.0D, 4, 124, Blocks.GLOWSTONE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), EnumFacing.DOWN),
                new BiomeTraitCluster(10, false, 1.0D, 1, 128, Blocks.GLOWSTONE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), EnumFacing.DOWN),
                new BiomeTraitFluid(16, false, 1.0D, 10, 118, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), true)
        ));
        this.getBiomeTraits(GenerationStage.DECORATE).addAll(Arrays.asList(
                new BiomeTraitScatter(1, false, 0.25D, 1, 128, Blocks.RED_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), BiomeTraitScatter.Placement.ON_GROUND),
                new BiomeTraitScatter(1, false, 0.25D, 1, 128, Blocks.BROWN_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), BiomeTraitScatter.Placement.ON_GROUND)
        ));
        this.getBiomeTraits(GenerationStage.ORE).addAll(Arrays.asList(
                new BiomeTraitOre(16, false, 1.0D, 10, 108, Blocks.QUARTZ_ORE.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), 14),
                new BiomeTraitOre(4, false, 1.0D, 28, 38, Blocks.MAGMA.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), 32)
        ));
        super.writeToConfig(config);
    }
}
