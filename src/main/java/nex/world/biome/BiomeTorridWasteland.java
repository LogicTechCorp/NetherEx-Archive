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

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.entity.monster.EntityEmber;
import nex.entity.neutral.EntitySalamander;
import nex.handler.ConfigHandler;
import nex.init.NetherExBiomes;
import nex.init.NetherExBlocks;
import nex.world.gen.feature.*;
import nex.world.gen.structure.WorldGenBlazingPyramid;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BiomeTorridWasteland extends BiomeNetherEx
{
    private WorldGenerator lavaSpring = new WorldGenLava(NetherExBlocks.BLOCK_NETHERRACK.getDefaultState(), false);
    private WorldGenerator fire = new WorldGenFire(NetherExBlocks.BLOCK_NETHERRACK.getDefaultState());
    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator quartzOre = new WorldGenMinableMeta(NetherExBlocks.ORE_QUARTZ.getDefaultState(), 14, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState());
    private WorldGenerator basalt = new WorldGenMinableMeta(NetherExBlocks.BLOCK_BASALT.getDefaultState(), 24, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState());
    private WorldGenerator magma = new WorldGenMinableMeta(Blocks.MAGMA.getDefaultState(), 32, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState());
    private WorldGenerator lavaTrap = new WorldGenLava(NetherExBlocks.BLOCK_NETHERRACK.getDefaultState(), true);
    private WorldGenerator lavaPit = new WorldGenPit(Blocks.LAVA, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState(), Blocks.AIR.getDefaultState());
    private WorldGenerator blazingPyramid = new WorldGenBlazingPyramid();

    public BiomeTorridWasteland()
    {
        super(new BiomeProperties("Torrid Wasteland").setTemperature(4.0F).setRainfall(0.0F).setRainDisabled(), "torrid_wasteland");

        spawnableMonsterList.add(new SpawnListEntry(EntitySalamander.class, 100, 3, 6));
        spawnableMonsterList.add(new SpawnListEntry(EntityEmber.class, 25, 4, 6));

        topBlock = NetherExBlocks.BLOCK_NETHERRACK.getDefaultState();
        fillerBlock = NetherExBlocks.BLOCK_NETHERRACK.getDefaultState();

        if(ConfigHandler.Biome.TorridWasteland.generateBiome)
        {
            NetherExBiomes.addBiome(this, ConfigHandler.Biome.TorridWasteland.biomeRarity, new ItemStack(Blocks.LAVA, 1, 0));
        }
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.TorridWasteland.generateLavaSprings)
        {
            for(int i = 0; i < ConfigHandler.Biome.TorridWasteland.lavaSpringRarity; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.TorridWasteland.generateFire)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.Biome.TorridWasteland.fireRarity); i++)
            {
                fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.TorridWasteland.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.Biome.TorridWasteland.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.TorridWasteland.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigHandler.Biome.TorridWasteland.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.TorridWasteland.generateQuartzOre)
        {
            for(int i = 0; i < ConfigHandler.Biome.TorridWasteland.quartzOreRarity; i++)
            {
                quartzOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigHandler.Biome.TorridWasteland.generateBasalt)
        {
            for(int i = 0; i < ConfigHandler.Biome.TorridWasteland.basaltRarity; i++)
            {
                basalt.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        if(ConfigHandler.Biome.TorridWasteland.generateMagma)
        {
            for(int i = 0; i < ConfigHandler.Biome.TorridWasteland.magmaRarity; i++)
            {
                magma.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(9) + 28, rand.nextInt(16)));
            }
        }

        if(ConfigHandler.Biome.TorridWasteland.generateLavaTraps)
        {
            for(int i = 0; i < ConfigHandler.Biome.TorridWasteland.lavaTrapRarity; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.TorridWasteland.generateLavaPits)
        {
            BlockPos newPos = pos.add(rand.nextInt(16) + 8, rand.nextInt(64) + 32, rand.nextInt(16) + 8);

            if(world.getBiomeForCoordsBody(newPos) == this)
            {
                for(int i = 0; i < ConfigHandler.Biome.TorridWasteland.lavaPitRarity; i++)
                {
                    lavaPit.generate(world, rand, newPos);
                }
            }
        }

        if(ConfigHandler.Biome.TorridWasteland.generateBlazingPyramids)
        {
            int rarity = ConfigHandler.Biome.TorridWasteland.blazingPyramidRarity;

            if(rarity <= 0)
            {
                rarity = 1;
            }

            if(rand.nextInt(rarity) == 0)
            {
                blazingPyramid.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
