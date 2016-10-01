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
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import nex.Settings;
import nex.world.gen.feature.WorldGenBush;
import nex.world.gen.feature.WorldGenGlowStone;
import nex.world.gen.feature.WorldGenLava;
import nex.world.gen.feature.WorldGenMinableMeta;

import java.util.Random;

public class BiomeDecoratorNether extends BiomeDecorator
{
    public WorldGenerator lavaSpring;
    public WorldGenerator fire;
    public WorldGenerator glowstone;
    public WorldGenerator smallBrownMushroom;
    public WorldGenerator smallRedMushroom;
    public WorldGenerator quartz;
    public WorldGenerator magma;
    public WorldGenerator lavaTrap;

    @Override
    public void decorate(World world, Random rand, Biome biome, BlockPos pos)
    {
        BiomeNetherEx biomeNetherEx = (BiomeNetherEx) biome;

        lavaSpring = new WorldGenLava(biomeNetherEx.lavaSpringTargetBlock, false);
        fire = new WorldGenFire();
        glowstone = new WorldGenGlowStone();
        smallBrownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM, biomeNetherEx.smallBrownMushroomTargetBlock);
        smallRedMushroom = new WorldGenBush(Blocks.RED_MUSHROOM, biomeNetherEx.smallRedMushroomTargetBlock);
        quartz = new WorldGenMinableMeta(biomeNetherEx.quartzOreBlock, 14, biomeNetherEx.quartzTargetBlock);
        magma = new WorldGenMinableMeta(Blocks.MAGMA.getDefaultState(), 32, biomeNetherEx.magmaTargetBlock);
        lavaTrap = new WorldGenLava(biomeNetherEx.lavaTrapTargetBlock, true);

        genDecorations(world, rand, biomeNetherEx, pos);
        genOre(world, rand, biomeNetherEx, pos);
    }

    private void genDecorations(World world, Random rand, BiomeNetherEx biome, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(Settings.generateLavaSprings(biome.settingCategory))
        {
            for(int i = 0; i < Settings.lavaSpringRarity(biome.settingCategory); i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 4, rand.nextInt(16)));
            }
        }

        if(Settings.generateGlowstone(biome.settingCategory))
        {
            for(int i = 0; i < rand.nextInt(Settings.glowstoneRarity(biome.settingCategory)); i++)
            {
                glowstone.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 4, rand.nextInt(16)));
            }

            for(int i = 0; i < Settings.glowstoneRarity(biome.settingCategory); i++)
            {
                glowstone.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
            }
        }

        if(Settings.generateFire(biome.settingCategory))
        {
            for(int i = 0; i < rand.nextInt(Settings.fireRarity(biome.settingCategory)); i++)
            {
                fire.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 4, rand.nextInt(16)));
            }
        }

        if(Settings.generateMushrooms(biome.settingCategory))
        {
            if(rand.nextBoolean())
            {
                smallBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
            }

            if(rand.nextBoolean())
            {
                smallRedMushroom.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
            }
        }

        if(Settings.generateLavaTraps(biome.settingCategory))
        {
            for(int i = 0; i < Settings.lavaTrapRarity(biome.settingCategory); i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }

    private void genOre(World world, Random rand, BiomeNetherEx biome, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(Settings.generateQuartzOre(biome.settingCategory))
        {
            for(int i = 0; i < Settings.quartzOreRarity(biome.settingCategory); i++)
            {
                quartz.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
            }
        }

        if(Settings.generateMagma(biome.settingCategory))
        {
            for(int i = 0; i < Settings.magmaRarity(biome.settingCategory); i++)
            {
                magma.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(9) + 28, rand.nextInt(16)));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
