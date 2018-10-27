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
import lex.world.biome.BiomeBlockType;
import lex.world.gen.GenerationStage;
import lex.world.gen.feature.FeatureCluster;
import lex.world.gen.feature.FeatureOre;
import lex.world.gen.feature.FeaturePool;
import lex.world.gen.feature.FeatureScatter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.block.BlockNetherrack;
import nex.entity.monster.EntityBrute;
import nex.entity.monster.EntityCoolmarSpider;
import nex.entity.monster.EntityWight;
import nex.init.NetherExBiomes;
import nex.init.NetherExBlocks;

import java.util.ArrayList;
import java.util.Arrays;

public class BiomeArcticAbyss extends BiomeNetherEx
{
    private static final IBlockState FROSTBURN_ICE = NetherExBlocks.FROSTBURN_ICE.getDefaultState();
    private static final IBlockState ICY_NETHERRACK = NEX_NETHERRACK.withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY);

    public BiomeArcticAbyss()
    {
        super(NetherEx.instance, new BiomeProperties("Arctic Abyss").setTemperature(0.0F).setRainfall(0.0F).setRainDisabled(), "arctic_abyss");
        this.topBlock = FROSTBURN_ICE;
        this.fillerBlock = ICY_NETHERRACK;
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGhast.class, 50, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 25, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityCoolmarSpider.class, 35, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityWight.class, 100, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityBrute.class, 15, 1, 1));
    }

    @Override
    public INetherBiomeWrapper getWrapper()
    {
        return new Wrapper();
    }

    private class Wrapper extends NetherBiomeWrapper
    {
        public Wrapper()
        {
            super(NetherExBiomes.ARCTIC_ABYSS.getRegistryName(), 2, true, true);
        }

        @Override
        public FileConfig serialize()
        {
            this.getBiomeBlock(BiomeBlockType.FLOOR_TOP_BLOCK, FROSTBURN_ICE);
            this.getBiomeBlock(BiomeBlockType.FLOOR_FILLER_BLOCK, ICY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.WALL_BLOCK, ICY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.CEILING_FILLER_BLOCK, ICY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.CEILING_BOTTOM_BLOCK, ICY_NETHERRACK);
            this.getBiomeBlock(BiomeBlockType.OCEAN_BLOCK, MAGMA);
            this.getEntitySpawnEntries(EnumCreatureType.MONSTER).addAll(new ArrayList<>(Arrays.asList(
                    new Biome.SpawnListEntry(EntityGhast.class, 50, 1, 4),
                    new Biome.SpawnListEntry(EntityPigZombie.class, 25, 1, 4),
                    new Biome.SpawnListEntry(EntityCoolmarSpider.class, 35, 1, 4),
                    new Biome.SpawnListEntry(EntityWight.class, 100, 1, 4),
                    new Biome.SpawnListEntry(EntityBrute.class, 15, 1, 1)
            )));
            this.getFeatures(GenerationStage.PRE_DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureScatter(5, 1.0D, true, 4, 124, NetherExBlocks.BLUE_FIRE.getDefaultState(), ICY_NETHERRACK, FeatureScatter.Placement.ON_GROUND),
                    new FeatureCluster(10, 1.0D, true, 4, 124, Blocks.GLOWSTONE.getDefaultState(), ICY_NETHERRACK, EnumFacing.DOWN),
                    new FeatureCluster(10, 1.0D, false, 1, 128, Blocks.GLOWSTONE.getDefaultState(), ICY_NETHERRACK, EnumFacing.DOWN),
                    new FeaturePool(2, 0.125, false, 36, 96, NetherExBlocks.ICHOR.getDefaultState(), FROSTBURN_ICE)
            )));
            this.getFeatures(GenerationStage.ORE).addAll(new ArrayList<>(Arrays.asList(
                    new FeatureOre(16, 1.0D, false, 10, 108, NetherExBlocks.QUARTZ_ORE.getDefaultState(), ICY_NETHERRACK, 14),
                    new FeatureOre(16, 1.0D, false, 10, 108, NetherExBlocks.RIME_ORE.getDefaultState(), ICY_NETHERRACK, 7)
            )));
            return super.serialize();
        }
    }
}
