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

import logictechcorp.libraryex.world.biome.data.impl.BiomeBlock;
import logictechcorp.libraryex.world.biome.data.impl.BiomeDataConfigurable;
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitCluster;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitOre;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitPool;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitScatter;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntityBrute;
import logictechcorp.netherex.entity.monster.EntityCoolmarSpider;
import logictechcorp.netherex.entity.monster.EntityWight;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Arrays;

public class BiomeArcticAbyss extends BiomeNetherEx
{
    private static final IBlockState FROSTBURN_ICE = NetherExBlocks.FROSTBURN_ICE.getDefaultState();
    private static final IBlockState ICY_NETHERRACK = NetherExBlocks.ICY_NETHERRACK.getDefaultState();

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
    public BiomeData getBiomeData()
    {
        return new BiomeData();
    }

    private class BiomeData extends BiomeDataConfigurable
    {
        BiomeData()
        {
            super(NetherExBiomes.ARCTIC_ABYSS, 2, false, true, true);
            this.getBiomeBlock(BiomeBlock.FLOOR_TOP_BLOCK, FROSTBURN_ICE);
            this.getBiomeBlock(BiomeBlock.FLOOR_FILLER_BLOCK, ICY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.WALL_BLOCK, ICY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.CEILING_FILLER_BLOCK, ICY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.CEILING_BOTTOM_BLOCK, ICY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.OCEAN_BLOCK, MAGMA);
            this.getBiomeEntities(EnumCreatureType.MONSTER).addAll(new ArrayList<>(Arrays.asList(
                    new Biome.SpawnListEntry(EntityGhast.class, 50, 1, 4),
                    new Biome.SpawnListEntry(EntityPigZombie.class, 25, 1, 4),
                    new Biome.SpawnListEntry(EntityCoolmarSpider.class, 35, 1, 4),
                    new Biome.SpawnListEntry(EntityWight.class, 100, 1, 4),
                    new Biome.SpawnListEntry(EntityBrute.class, 15, 1, 1)
            )));
            this.getBiomeTraits(GenerationStage.PRE_DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new BiomeTraitScatter(5, true, 1.0D, 4, 124, NetherExBlocks.BLUE_FIRE.getDefaultState(), FROSTBURN_ICE, BiomeTraitScatter.Placement.ON_GROUND),
                    new BiomeTraitCluster(10, true, 1.0D, 4, 124, Blocks.GLOWSTONE.getDefaultState(), ICY_NETHERRACK, EnumFacing.DOWN),
                    new BiomeTraitCluster(10, false, 1.0D, 1, 128, Blocks.GLOWSTONE.getDefaultState(), ICY_NETHERRACK, EnumFacing.DOWN),
                    new BiomeTraitPool(2, false, 0.125, 36, 96, NetherExBlocks.ICHOR.getDefaultState(), FROSTBURN_ICE)
            )));
            this.getBiomeTraits(GenerationStage.ORE).addAll(new ArrayList<>(Arrays.asList(
                    new BiomeTraitOre(16, false, 1.0D, 10, 108, NetherExBlocks.QUARTZ_ORE.getDefaultState(), ICY_NETHERRACK, 14),
                    new BiomeTraitOre(16, false, 1.0D, 10, 108, NetherExBlocks.RIME_ORE.getDefaultState(), ICY_NETHERRACK, 7)
            )));
        }
    }
}
