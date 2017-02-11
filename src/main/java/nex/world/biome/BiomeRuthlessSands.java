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

import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.entity.monster.EntityEmber;
import nex.handler.ConfigHandler;
import nex.init.NetherExBiomes;
import nex.init.NetherExBlocks;
import nex.world.gen.feature.WorldGenGlowStone;
import nex.world.gen.feature.WorldGenLava;
import nex.world.gen.feature.WorldGenMinableMeta;
import nex.world.gen.feature.WorldGenThornstalk;
import nex.world.gen.structure.WorldGenAncientAltar;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BiomeRuthlessSands extends BiomeNetherEx
{
    private WorldGenerator lavaSpring = new WorldGenLava(NetherExBlocks.BLOCK_NETHERRACK.getStateFromMeta(3), false);
    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator quartzOre = new WorldGenMinableMeta(NetherExBlocks.ORE_QUARTZ.getStateFromMeta(3), 14, NetherExBlocks.BLOCK_NETHERRACK.getStateFromMeta(3));
    private WorldGenerator lavaTrap = new WorldGenLava(NetherExBlocks.BLOCK_NETHERRACK.getStateFromMeta(3), true);
    private WorldGenerator thornstalk = new WorldGenThornstalk();
    private WorldGenerator ancientAltar = new WorldGenAncientAltar();

    public BiomeRuthlessSands()
    {
        super(new BiomeProperties("Ruthless Sands").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "ruthless_sands");

        topBlock = Blocks.SOUL_SAND.getDefaultState();
        fillerBlock = NetherExBlocks.BLOCK_NETHERRACK.getStateFromMeta(3);

        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityWitherSkeleton.class, 45, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityEmber.class, 25, 4, 6));

        if(ConfigHandler.Biome.RuthlessSands.generateBiome)
        {
            NetherExBiomes.addBiome(this, ConfigHandler.Biome.RuthlessSands.biomeRarity, new ItemStack(Blocks.LAVA, 1, 0));
        }
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.RuthlessSands.generateLavaSprings)
        {
            for(int i = 0; i < ConfigHandler.Biome.RuthlessSands.lavaSpringRarity; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.RuthlessSands.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.Biome.RuthlessSands.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.RuthlessSands.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigHandler.Biome.RuthlessSands.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.RuthlessSands.generateQuartzOre)
        {
            for(int i = 0; i < ConfigHandler.Biome.RuthlessSands.quartzOreRarity; i++)
            {
                quartzOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigHandler.Biome.RuthlessSands.generateLavaTraps)
        {
            for(int i = 0; i < ConfigHandler.Biome.RuthlessSands.lavaTrapRarity; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.RuthlessSands.generateThornstalk)
        {
            for(int i = 0; i < ConfigHandler.Biome.RuthlessSands.thornstalkRarity; i++)
            {
                thornstalk.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.RuthlessSands.generateAncientAltars)
        {
            int rarity = ConfigHandler.Biome.RuthlessSands.ancientAltarRarity;

            if(rarity <= 0)
            {
                rarity = 1;
            }

            if(rand.nextInt(rarity) == 0)
            {
                ancientAltar.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
