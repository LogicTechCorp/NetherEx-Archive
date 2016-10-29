/*
 * Copyright (C) 2016.  LogicTechCorp
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

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.block.BlockTallGrass;
import nex.handler.ConfigurationHandler;
import nex.init.NetherExBlocks;
import nex.world.gen.feature.*;

import java.util.Random;

public class BiomeRegrowthsEnd extends BiomeNetherEx
{
    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator quartz = new WorldGenMinableMeta(NetherExBlocks.QUARTZ_ORE.getDefaultState(), 14, NetherExBlocks.NETHERRACK.getDefaultState());
    private WorldGenerator pool = new WorldGenPool(NetherExBlocks.TAINT, NetherExBlocks.NETHERRACK.getDefaultState());
    private WorldGenerator tree = new WorldGenTrees(false);
    private WorldGenerator tallGrass = new WorldGenTallGrass(BlockTallGrass.EnumType.TAINTED);

    public BiomeRegrowthsEnd()
    {
        super(new BiomeProperties("Regrowth's End"));

        topBlock = NetherExBlocks.OVERGROWN_NETHERRACK.getDefaultState();
        fillerBlock = NetherExBlocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 1, 4, 4));

        setNameAndRegister("regrowths_end", ConfigurationHandler.BiomeRegrowthsEnd.biomeRarity);
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigurationHandler.BiomeRegrowthsEnd.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigurationHandler.BiomeRegrowthsEnd.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeRegrowthsEnd.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeRegrowthsEnd.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigurationHandler.BiomeRegrowthsEnd.generateQuartzOre)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeRegrowthsEnd.quartzOreRarity; i++)
            {
                quartz.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigurationHandler.BiomeRegrowthsEnd.generateTaintPools)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeRegrowthsEnd.taintPoolRarity; i++)
            {
                pool.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeRegrowthsEnd.generateWitheredTrees)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeRegrowthsEnd.witheredTreeRarity; i++)
            {
                tree.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeRegrowthsEnd.generateTaintedTallGrass)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeRegrowthsEnd.taintedTallGrassRarity; i++)
            {
                tallGrass.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
