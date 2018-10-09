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

import com.electronwill.nightconfig.core.file.FileConfig;
import lex.world.biome.BiomeConfigurations;
import lex.world.gen.GenerationStage;
import lex.world.gen.feature.FeatureCluster;
import lex.world.gen.feature.FeatureFluid;
import lex.world.gen.feature.FeatureOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.block.BlockNetherrack;
import nex.entity.monster.EntitySpinout;
import nex.init.NetherExBiomes;
import nex.init.NetherExBlocks;
import nex.world.gen.feature.FeatureThornstalk;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BiomeRuthlessSands extends BiomeNetherEx
{
    private static final IBlockState GLOOMY_NETHERRACK = NEX_NETHERRACK.withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.GLOOMY);

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
    public BiomeConfigurations getConfigurations()
    {
        return new Configurations();
    }

    private class Configurations extends BiomeConfigurations
    {
        public Configurations()
        {
            super(NetherExBiomes.RUTHLESS_SANDS.getRegistryName(), 8, true, true, new HashMap<>(), new HashMap<>(), new HashMap<>());
        }

        @Override
        public FileConfig serialize(File configFile)
        {
            this.getBlock("topBlock", SOUL_SAND);
            this.getBlock("fillerBlock", GLOOMY_NETHERRACK);
            this.getBlock("wallBlock", GLOOMY_NETHERRACK);
            this.getBlock("ceilingBottomBlock", GLOOMY_NETHERRACK);
            this.getBlock("ceilingFillerBlock", GLOOMY_NETHERRACK);
            this.getBlock("oceanBlock", LAVA);
            this.getEntities(EnumCreatureType.MONSTER).addAll(new ArrayList<>(Arrays.asList(
                    new Biome.SpawnListEntry(EntityWitherSkeleton.class, 50, 1, 4),
                    new Biome.SpawnListEntry(EntityPigZombie.class, 3, 1, 4),
                    new Biome.SpawnListEntry(EntitySpinout.class, 100, 1, 4)
            )));
            this.getFeatures(GenerationStage.POPULATE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureFluid(8, 1.0D, false, 4, 124, FLOWING_LAVA, GLOOMY_NETHERRACK, false),
                    new FeatureCluster(10, 1.0D, true, 4, 124, GLOWSTONE, GLOOMY_NETHERRACK, EnumFacing.DOWN),
                    new FeatureCluster(10, 1.0D, false, 1, 128, GLOWSTONE, GLOOMY_NETHERRACK, EnumFacing.DOWN),
                    new FeatureFluid(16, 1.0D, false, 10, 118, FLOWING_LAVA, GLOOMY_NETHERRACK, true)
            )));
            this.getFeatures(GenerationStage.DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureThornstalk(16, 1.0D, false, 32, 108)
            )));
            this.getFeatures(GenerationStage.ORE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureOre(16, 1.0D, false, 10, 108, NetherExBlocks.QUARTZ_ORE.getDefaultState(), GLOOMY_NETHERRACK, 14)
            )));
            return super.serialize(configFile);
        }
    }
}
