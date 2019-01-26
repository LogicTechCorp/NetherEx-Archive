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
import logictechcorp.libraryex.world.biome.BiomeBlockType;
import logictechcorp.libraryex.world.biome.BiomeInfo;
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.feature.ConfigurableFeatureCluster;
import logictechcorp.libraryex.world.generation.feature.ConfigurableFeatureFluid;
import logictechcorp.libraryex.world.generation.feature.ConfigurableFeatureOre;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntitySpinout;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.world.biome.info.NetherBiomeInfo;
import logictechcorp.netherex.world.generation.feature.ConfigurableFeatureThornstalk;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Arrays;

public class BiomeRuthlessSands extends BiomeNetherEx
{
    private static final IBlockState GLOOMY_NETHERRACK = NetherExBlocks.GLOOMY_NETHERRACK.getDefaultState();

    public BiomeRuthlessSands()
    {
        super(NetherEx.instance, new BiomeProperties("Ruthless Sands").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "ruthless_sands");
        this.topBlock = SOUL_SAND;
        this.fillerBlock = GLOOMY_NETHERRACK;
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityWitherSkeleton.class, 65, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 45, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpinout.class, 100, 1, 4));
    }

    @Override
    public BiomeInfo getInfo()
    {
        return new Info();
    }

    private class Info extends NetherBiomeInfo
    {
        public Info()
        {
            super(NetherExBiomes.RUTHLESS_SANDS.getRegistryName(), 8, true, true);
        }

        @Override
        public Config getAsConfig()
        {
            this.getBiomeBlock(BiomeBlockType.FLOOR_TOP_BLOCK, SOUL_SAND);
            this.getBiomeBlock(BiomeBlockType.FLOOR_FILLER_BLOCK, GLOOMY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.WALL_BLOCK, GLOOMY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.CEILING_FILLER_BLOCK, GLOOMY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.CEILING_BOTTOM_BLOCK, GLOOMY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.OCEAN_BLOCK, LAVA);
            this.getEntities(EnumCreatureType.MONSTER).addAll(new ArrayList<>(Arrays.asList(
                    new Biome.SpawnListEntry(EntityWitherSkeleton.class, 50, 1, 4),
                    new Biome.SpawnListEntry(EntityPigZombie.class, 3, 1, 4),
                    new Biome.SpawnListEntry(EntitySpinout.class, 100, 1, 4)
            )));
            this.getFeatures(GenerationStage.PRE_DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new ConfigurableFeatureFluid(8, 1.0D, false, 4, 124, FLOWING_LAVA, GLOOMY_NETHERRACK, false),
                    new ConfigurableFeatureCluster(10, 1.0D, true, 4, 124, GLOWSTONE, GLOOMY_NETHERRACK, EnumFacing.DOWN),
                    new ConfigurableFeatureCluster(10, 1.0D, false, 1, 128, GLOWSTONE, GLOOMY_NETHERRACK, EnumFacing.DOWN),
                    new ConfigurableFeatureFluid(16, 1.0D, false, 10, 118, FLOWING_LAVA, GLOOMY_NETHERRACK, true)
            )));
            this.getFeatures(GenerationStage.DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new ConfigurableFeatureThornstalk(16, 1.0D, false, 32, 108)
            )));
            this.getFeatures(GenerationStage.ORE).addAll(new ArrayList<>(Arrays.asList(
                    new ConfigurableFeatureOre(16, 1.0D, false, 10, 108, NetherExBlocks.QUARTZ_ORE.getDefaultState(), GLOOMY_NETHERRACK, 14)
            )));
            return super.getAsConfig();
        }
    }
}
