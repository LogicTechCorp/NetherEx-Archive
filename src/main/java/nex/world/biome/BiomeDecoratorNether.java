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

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.Settings;
import nex.world.gen.feature.*;

import java.util.Random;

public class BiomeDecoratorNether extends BiomeDecorator
{
    private WorldGenerator lavaSpring;
    private WorldGenerator fire;
    private WorldGenerator glowstone;
    private WorldGenerator smallBrownMushroom;
    private WorldGenerator smallRedMushroom;
    private WorldGenerator quartz;
    private WorldGenerator magma;
    private WorldGenerator lavaTrap;

    @Override
    public void decorate(World world, Random rand, Biome biome, BlockPos pos)
    {
        BiomeNetherEx biomeNetherEx = (BiomeNetherEx) biome;

        lavaSpring = new WorldGenLava(biome.fillerBlock, false);
        fire = new WorldGenFire(biome.topBlock);
        glowstone = new WorldGenGlowStone();
        smallBrownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM, biome.topBlock);
        smallRedMushroom = new WorldGenBush(Blocks.RED_MUSHROOM, biome.topBlock);
        quartz = new WorldGenMinableMeta(biomeNetherEx.quartzOreBlock, 14, biome.fillerBlock);
        magma = new WorldGenMinableMeta(Blocks.MAGMA.getDefaultState(), 32, biome.topBlock);
        lavaTrap = new WorldGenLava(biome.fillerBlock, true);

        genFeatures(world, rand, biomeNetherEx, pos);
    }

    private void genFeatures(World world, Random rand, BiomeNetherEx biome, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(Settings.generateLavaSprings(biome.settingCategory))
        {
            if(Settings.generateTallNether)
            {
                for(int i = 0; i < Settings.lavaSpringRarity(biome.settingCategory); i++)
                {
                    lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 64, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.lavaSpringRarity(biome.settingCategory); i++)
                {
                    lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 148, rand.nextInt(16) + 8));
                }
            }
            else
            {
                for(int i = 0; i < Settings.lavaSpringRarity(biome.settingCategory); i++)
                {
                    lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
                }
            }
        }

        if(Settings.generateFire(biome.settingCategory))
        {
            if(Settings.generateTallNether)
            {
                for(int i = 0; i < rand.nextInt(Settings.fireRarity(biome.settingCategory)); i++)
                {
                    fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 64, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < rand.nextInt(Settings.fireRarity(biome.settingCategory)); i++)
                {
                    fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 148, rand.nextInt(16) + 8));
                }
            }
            else
            {
                for(int i = 0; i < rand.nextInt(Settings.fireRarity(biome.settingCategory)); i++)
                {
                    fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
                }
            }
        }

        if(Settings.generateGlowstone(biome.settingCategory))
        {
            if(Settings.generateTallNether)
            {
                for(int i = 0; i < rand.nextInt(Settings.glowstoneRarity(biome.settingCategory)); i++)
                {
                    glowstone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 64, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.glowstoneRarity(biome.settingCategory); i++)
                {
                    glowstone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 64, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < rand.nextInt(Settings.glowstoneRarity(biome.settingCategory)); i++)
                {
                    glowstone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 148, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.glowstoneRarity(biome.settingCategory); i++)
                {
                    glowstone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 148, rand.nextInt(16) + 8));
                }
            }
            else
            {
                for(int i = 0; i < rand.nextInt(Settings.glowstoneRarity(biome.settingCategory)); i++)
                {
                    glowstone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.glowstoneRarity(biome.settingCategory); i++)
                {
                    glowstone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
                }
            }
        }

        if(Settings.generateMushrooms(biome.settingCategory))
        {
            if(Settings.generateTallNether)
            {
                for(int i = 0; i < Settings.mushroomRarity(biome.settingCategory); i++)
                {
                    smallBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 64, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.mushroomRarity(biome.settingCategory); i++)
                {
                    smallRedMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 64, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.mushroomRarity(biome.settingCategory); i++)
                {
                    smallBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 148, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.mushroomRarity(biome.settingCategory); i++)
                {
                    smallRedMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 148, rand.nextInt(16) + 8));
                }
            }
            else
            {
                for(int i = 0; i < Settings.mushroomRarity(biome.settingCategory); i++)
                {
                    smallBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.mushroomRarity(biome.settingCategory); i++)
                {
                    smallRedMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
                }
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(Settings.generateQuartzOre(biome.settingCategory))
        {
            if(Settings.generateTallNether)
            {
                for(int i = 0; i < Settings.quartzOreRarity(biome.settingCategory); i++)
                {
                    quartz.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 64, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.quartzOreRarity(biome.settingCategory); i++)
                {
                    quartz.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 148, rand.nextInt(16) + 8));
                }
            }
            else
            {
                for(int i = 0; i < Settings.quartzOreRarity(biome.settingCategory); i++)
                {
                    quartz.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
                }
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(Settings.generateMagma(biome.settingCategory))
        {
            if(Settings.generateTallNether)
            {
                for(int i = 0; i < Settings.magmaRarity(biome.settingCategory); i++)
                {
                    magma.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(9) + 60, rand.nextInt(16) + 8));
                }
            }
            else
            {
                for(int i = 0; i < Settings.magmaRarity(biome.settingCategory); i++)
                {
                    magma.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(9) + 28, rand.nextInt(16) + 8));
                }
            }
        }

        if(Settings.generateLavaTraps(biome.settingCategory))
        {
            if(Settings.generateTallNether)
            {
                for(int i = 0; i < Settings.lavaTrapRarity(biome.settingCategory); i++)
                {
                    lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 64, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.lavaTrapRarity(biome.settingCategory); i++)
                {
                    lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 148, rand.nextInt(16) + 8));
                }
            }
            else
            {
                for(int i = 0; i < Settings.lavaTrapRarity(biome.settingCategory); i++)
                {
                    lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
                }
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
