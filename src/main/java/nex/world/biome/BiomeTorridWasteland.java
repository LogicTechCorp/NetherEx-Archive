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
import lex.world.gen.feature.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.block.BlockNetherrack;
import nex.entity.monster.EntityEmber;
import nex.entity.neutral.EntitySalamander;
import nex.init.NetherExBiomes;
import nex.init.NetherExBlocks;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BiomeTorridWasteland extends BiomeNetherEx
{
    private static final IBlockState FIERY_NETHERRACK = NEX_NETHERRACK.withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.FIERY);

    public BiomeTorridWasteland()
    {
        super(NetherEx.instance, new BiomeProperties("Torrid Wasteland").setTemperature(4.0F).setRainfall(0.0F).setRainDisabled(), "torrid_wasteland");
        this.topBlock = NetherExBlocks.NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.FIERY);
        this.fillerBlock = NetherExBlocks.NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.FIERY);
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 25, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEmber.class, 50, 4, 6));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySalamander.class, 100, 1, 4));
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
            super(NetherExBiomes.TORRID_WASTELAND.getRegistryName(), 6, true, true, new HashMap<>(), new HashMap<>(), new HashMap<>());
        }

        @Override
        public FileConfig serialize(File configFile)
        {
            this.getBlock("topBlock", FIERY_NETHERRACK);
            this.getBlock("fillerBlock", FIERY_NETHERRACK);
            this.getBlock("wallBlock", FIERY_NETHERRACK);
            this.getBlock("ceilingBottomBlock", FIERY_NETHERRACK);
            this.getBlock("ceilingFillerBlock", FIERY_NETHERRACK);
            this.getBlock("oceanBlock", LAVA);
            this.getEntities(EnumCreatureType.MONSTER).addAll(new ArrayList<>(Arrays.asList(
                    new Biome.SpawnListEntry(EntitySalamander.class, 100, 1, 4),
                    new Biome.SpawnListEntry(EntityEmber.class, 50, 4, 6),
                    new Biome.SpawnListEntry(EntityMagmaCube.class, 25, 1, 4),
                    new Biome.SpawnListEntry(EntityBlaze.class, 3, 1, 4)
            )));
            this.getFeatures(GenerationStage.POPULATE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureFluid(16, 1.0D, false, 4, 124, FLOWING_LAVA, FIERY_NETHERRACK, false),
                    new FeatureScatter(20, 1.0D, true, 4, 124, FIRE, FIERY_NETHERRACK, FeatureScatter.Placement.ON_GROUND),
                    new FeatureCluster(10, 1.0D, true, 4, 124, GLOWSTONE, FIERY_NETHERRACK, EnumFacing.DOWN),
                    new FeatureCluster(10, 1.0D, false, 1, 128, GLOWSTONE, FIERY_NETHERRACK, EnumFacing.DOWN),
                    new FeatureFluid(32, 1.0D, false, 10, 118, FLOWING_LAVA, FIERY_NETHERRACK, true),
                    new FeaturePool(8, 1.0, false, 10, 108, LAVA, FIERY_NETHERRACK)
            )));
            this.getFeatures(GenerationStage.ORE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureOre(16, 1.0D, false, 10, 108, NetherExBlocks.QUARTZ_ORE.getDefaultState(), FIERY_NETHERRACK, 14),
                    new FeatureOre(14, 1.0D, false, 10, 108, NetherExBlocks.BASALT.getDefaultState(), FIERY_NETHERRACK, 24),
                    new FeatureOre(8, 1.0D, false, 28, 38, Blocks.MAGMA.getDefaultState(), FIERY_NETHERRACK, 32)
            )));
            return super.serialize(configFile);
        }
    }
}
