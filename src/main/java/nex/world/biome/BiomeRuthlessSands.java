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

import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
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

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class BiomeRuthlessSands extends BiomeNetherEx
{
    private WorldGenerator lavaSpring = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), false);
    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator quartz = new WorldGenMinableMeta(Blocks.QUARTZ_ORE.getDefaultState(), 14, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator lavaTrap = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), true);
    private WorldGenerator thornstalk = new WorldGenThornstalk();
    private WorldGenAncientAltar ancientAltar = new WorldGenAncientAltar();

    public BiomeRuthlessSands()
    {
        super(new BiomeProperties("Ruthless Sands").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());

        topBlock = Blocks.SOUL_SAND.getDefaultState();
        fillerBlock = Blocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityWitherSkeleton.class, 20, 4, 4));

        setNameAndRegister("ruthless_sands", ConfigurationHandler.BiomeRuthlessSands.biomeRarity, NETHER, HOT, DRY, SANDY);
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigurationHandler.BiomeRuthlessSands.generateLavaSprings)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeRuthlessSands.lavaSpringRarity; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeRuthlessSands.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigurationHandler.BiomeRuthlessSands.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeRuthlessSands.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeRuthlessSands.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigurationHandler.BiomeRuthlessSands.generateQuartzOre)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeRuthlessSands.quartzOreRarity; i++)
            {
                quartz.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigurationHandler.BiomeRuthlessSands.generateLavaTraps)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeRuthlessSands.lavaTrapRarity; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeRuthlessSands.generateThornstalk)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeRuthlessSands.thornstalkRarity; i++)
            {
                thornstalk.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeRuthlessSands.generateAltars)
        {
            if(rand.nextInt(ConfigurationHandler.BiomeRuthlessSands.altarRarity) == 0)
            {
                ancientAltar.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(80) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
