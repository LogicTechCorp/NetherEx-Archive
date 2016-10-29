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
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.handler.ConfigurationHandler;
import nex.world.gen.feature.*;

import java.util.Random;

public class BiomeBoneyard extends BiomeNetherEx
{
    private WorldGenerator lavaSpring = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), false);
    private WorldGenerator fire = new WorldGenFire(Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator smallBrownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator smallRedMushroom = new WorldGenBush(Blocks.RED_MUSHROOM, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator quartz = new WorldGenMinableMeta(Blocks.QUARTZ_ORE.getDefaultState(), 14, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator magma = new WorldGenMinableMeta(Blocks.MAGMA.getDefaultState(), 32, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator lavaTrap = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), true);
    private WorldGenerator boneSpireUp = new WorldGenBoneSpire(false);
    private WorldGenerator boneSpireDown = new WorldGenBoneSpire(true);

    public BiomeBoneyard()
    {
        super(new BiomeProperties("Boneyard"));

        topBlock = Blocks.NETHERRACK.getDefaultState();
        fillerBlock = Blocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 2, 4, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 1, 4, 4));

        setNameAndRegister("boneyard", ConfigurationHandler.BiomeBoneyard.biomeRarity);
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigurationHandler.BiomeBoneyard.generateLavaSprings)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeBoneyard.lavaSpringRarity; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeBoneyard.generateFire)
        {
            for(int i = 0; i < rand.nextInt(ConfigurationHandler.BiomeBoneyard.fireRarity); i++)
            {
                fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeBoneyard.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigurationHandler.BiomeBoneyard.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeBoneyard.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeBoneyard.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeBoneyard.generateBrownMushrooms)
        {
            if(rand.nextBoolean())
            {
                smallBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeBoneyard.generateRedMushrooms)
        {
            if(rand.nextBoolean())
            {
                smallRedMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigurationHandler.BiomeBoneyard.generateQuartzOre)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeBoneyard.quartzOreRarity; i++)
            {
                quartz.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigurationHandler.BiomeBoneyard.generateMagma)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeBoneyard.magmaRarity; i++)
            {
                magma.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(9) + 28, rand.nextInt(16) + 8));
            }

        }

        if(ConfigurationHandler.BiomeBoneyard.generateLavaTraps)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeBoneyard.lavaTrapRarity; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeBoneyard.generateBoneSpiresUp)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeBoneyard.boneSpireUpRarity; i++)
            {
                boneSpireUp.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 22, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeBoneyard.generateBoneSpiresDown)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeBoneyard.boneSpireDownRarity; i++)
            {
                boneSpireDown.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 22, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
