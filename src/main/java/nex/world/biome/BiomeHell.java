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

import com.google.common.collect.Sets;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.entity.monster.EntityEmber;
import nex.handler.ConfigHandler;
import nex.init.NetherExBiomes;
import nex.world.gen.feature.*;
import nex.world.gen.structure.WorldGenGroundStructure;

import java.util.Random;
import java.util.Set;

public class BiomeHell extends BiomeNetherEx
{
    private final Set<IBlockState> allowedBlocks = Sets.newHashSet(
            Blocks.NETHERRACK.getDefaultState(),
            Blocks.QUARTZ_ORE.getDefaultState(),
            Blocks.MAGMA.getDefaultState()
    );

    private WorldGenerator lavaSpring = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), false);
    private WorldGenerator fire = new WorldGenFire(Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator smallBrownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator smallRedMushroom = new WorldGenBush(Blocks.RED_MUSHROOM, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator quartzOre = new WorldGenMinableMeta(Blocks.QUARTZ_ORE.getDefaultState(), 14, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator magma = new WorldGenMinableMeta(Blocks.MAGMA.getDefaultState(), 32, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator lavaTrap = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), true);
    private WorldGenerator crypt = new WorldGenGroundStructure("hell", "crypt", new String[]{""}, allowedBlocks, new String[]{""}, true, LootTableList.CHESTS_NETHER_BRIDGE);
    private WorldGenerator grave = new WorldGenGroundStructure("hell", "grave", new String[]{"chest", "empty"}, allowedBlocks, new String[]{""}, true, LootTableList.CHESTS_NETHER_BRIDGE);
    private WorldGenerator graveyard = new WorldGenGroundStructure("hell", "graveyard", new String[]{""}, allowedBlocks, new String[]{"zombie_pigman", "magma_cube"}, true, LootTableList.CHESTS_NETHER_BRIDGE);
    private WorldGenerator sarcophagus = new WorldGenGroundStructure("hell", "sarcophagus", new String[]{""}, allowedBlocks, new String[]{"zombie_pigman", "magma_cube"}, true, LootTableList.CHESTS_NETHER_BRIDGE);
    private WorldGenerator mausoleum = new WorldGenGroundStructure("hell", "mausoleum", new String[]{""}, allowedBlocks, new String[]{"zombie_pigman", "magma_cube"}, true, LootTableList.CHESTS_NETHER_BRIDGE);
    private WorldGenerator prison = new WorldGenGroundStructure("hell", "prison", new String[]{""}, allowedBlocks, new String[]{"zombie_pigman", "magma_cube"}, true, LootTableList.CHESTS_NETHER_BRIDGE);
    private WorldGenerator village = new WorldGenGroundStructure("hell", "village", new String[]{"huge", "large_variant", "large", "medium_variant_2", "medium_variant", "medium", "small_variant_2", "small_variant", "small", "tiny_variant_3", "tiny_variant_2", "tiny_variant", "tiny"}, allowedBlocks, new String[]{""}, true, LootTableList.CHESTS_NETHER_BRIDGE);

    public BiomeHell()
    {
        super(new BiomeProperties("Hell").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "hell");

        topBlock = Blocks.NETHERRACK.getDefaultState();
        fillerBlock = Blocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityEmber.class, 25, 4, 6));
        spawnableMonsterList.add(new SpawnListEntry(EntityBlaze.class, 3, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 2, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 4, 4));

        if(ConfigHandler.Biome.Hell.generateBiome)
        {
            NetherExBiomes.addBiome(this, ConfigHandler.Biome.Hell.biomeRarity, new ItemStack(Blocks.LAVA, 1, 0));
        }
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.Hell.generateLavaSprings)
        {
            for(int i = 0; i < ConfigHandler.Biome.Hell.lavaSpringRarity; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateFire)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.Biome.Hell.fireRarity); i++)
            {
                fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.Biome.Hell.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigHandler.Biome.Hell.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateBrownMushrooms)
        {
            if(rand.nextBoolean())
            {
                smallBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateRedMushrooms)
        {
            if(rand.nextBoolean())
            {
                smallRedMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.Hell.generateQuartzOre)
        {
            for(int i = 0; i < ConfigHandler.Biome.Hell.quartzOreRarity; i++)
            {
                quartzOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigHandler.Biome.Hell.generateMagma)
        {
            for(int i = 0; i < ConfigHandler.Biome.Hell.magmaRarity; i++)
            {
                magma.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(9) + 28, rand.nextInt(16)));
            }
        }

        if(ConfigHandler.Biome.Hell.generateLavaTraps)
        {
            for(int i = 0; i < ConfigHandler.Biome.Hell.lavaTrapRarity; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateCrypts)
        {
            if(rand.nextInt(ConfigHandler.Biome.Hell.cryptRarity) == 0)
            {
                crypt.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateGraves)
        {
            if(rand.nextInt(ConfigHandler.Biome.Hell.graveRarity) == 0)
            {
                grave.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateGraveyards)
        {
            if(rand.nextInt(ConfigHandler.Biome.Hell.graveyardRarity) == 0)
            {
                graveyard.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateSarcophagus)
        {
            if(rand.nextInt(ConfigHandler.Biome.Hell.sarcophagusRarity) == 0)
            {
                sarcophagus.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateMausoleums)
        {
            if(rand.nextInt(ConfigHandler.Biome.Hell.mausoleumRarity) == 0)
            {
                mausoleum.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generatePrisons)
        {
            if(rand.nextInt(ConfigHandler.Biome.Hell.prisonRarity) == 0)
            {
                prison.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateVillages)
        {
            if(rand.nextInt(ConfigHandler.Biome.Hell.villageRarity) == 0)
            {
                for(int i = 0; i < 4; i++)
                {
                    village.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
                }
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
