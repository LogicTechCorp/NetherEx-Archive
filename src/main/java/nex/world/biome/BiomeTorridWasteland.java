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
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.NetherEx;
import nex.block.BlockBasalt;
import nex.block.BlockNetherrack;
import nex.entity.monster.EntityEmber;
import nex.entity.neutral.EntitySalamander;
import nex.handler.ConfigHandler;
import nex.init.NetherExBiomes;
import nex.init.NetherExBlocks;
import nex.init.NetherExLootTables;
import nex.world.gen.feature.*;
import nex.world.gen.structure.WorldGenGroundStructure;

import java.util.Random;
import java.util.Set;

@SuppressWarnings("ConstantConditions")
public class BiomeTorridWasteland extends BiomeNetherEx
{
    private final Set<IBlockState> allowedBlocks = Sets.newHashSet(
            Blocks.MAGMA.getDefaultState(),
            NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.FIERY),
            NetherExBlocks.BLOCK_BASALT.getDefaultState().withProperty(BlockBasalt.TYPE, BlockBasalt.EnumType.NORMAL),
            NetherExBlocks.ORE_QUARTZ.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.FIERY)
    );

    private WorldGenerator lavaSpring = new WorldGenLava(NetherExBlocks.BLOCK_NETHERRACK.getDefaultState(), false);
    private WorldGenerator fire = new WorldGenFire(NetherExBlocks.BLOCK_NETHERRACK.getDefaultState());
    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator quartzOre = new WorldGenMinableMeta(NetherExBlocks.ORE_QUARTZ.getDefaultState(), 14, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState());
    private WorldGenerator basalt = new WorldGenMinableMeta(NetherExBlocks.BLOCK_BASALT.getDefaultState(), 24, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState());
    private WorldGenerator magma = new WorldGenMinableMeta(Blocks.MAGMA.getDefaultState(), 32, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState());
    private WorldGenerator lavaTrap = new WorldGenLava(NetherExBlocks.BLOCK_NETHERRACK.getDefaultState(), true);
    private WorldGenerator lavaPit = new WorldGenPit(Blocks.LAVA, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState(), Blocks.AIR.getDefaultState());
    private WorldGenerator crypt = new WorldGenGroundStructure("torrid_wasteland", "crypt", new String[]{""}, allowedBlocks, new String[]{""}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE, NetherExLootTables.CHEST_GRAVE_TORRID_WASTELAND});
    private WorldGenerator grave = new WorldGenGroundStructure("torrid_wasteland", "grave", new String[]{"chest", "empty"}, allowedBlocks, new String[]{""}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE});
    private WorldGenerator graveyard = new WorldGenGroundStructure("torrid_wasteland", "graveyard", new String[]{""}, allowedBlocks, new String[]{"blaze", NetherEx.MOD_ID + ":monster_ember"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE, NetherExLootTables.CHEST_GRAVE_TORRID_WASTELAND, NetherExLootTables.CHEST_GRAVE_RARE});
    private WorldGenerator sarcophagus = new WorldGenGroundStructure("torrid_wasteland", "sarcophagus", new String[]{""}, allowedBlocks, new String[]{"blaze", NetherEx.MOD_ID + ":monster_ember"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_RARE});
    private WorldGenerator pyramid = new WorldGenGroundStructure("torrid_wasteland", "pyramid", new String[]{"advanced", "hard", "medium", "easy"}, allowedBlocks, new String[]{"blaze", NetherEx.MOD_ID + ":monster_ember"}, new ResourceLocation[]{NetherExLootTables.CHEST_TEMPLE_TORRID_WASTELAND, NetherExLootTables.CHEST_TEMPLE_RARE});

    public BiomeTorridWasteland()
    {
        super(new BiomeProperties("Torrid Wasteland").setTemperature(4.0F).setRainfall(0.0F).setRainDisabled(), "torrid_wasteland");

        spawnableMonsterList.add(new SpawnListEntry(EntitySalamander.class, 100, 3, 6));
        spawnableMonsterList.add(new SpawnListEntry(EntityEmber.class, 50, 4, 6));
        spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 25, 4, 4));

        topBlock = NetherExBlocks.BLOCK_NETHERRACK.getDefaultState();
        fillerBlock = NetherExBlocks.BLOCK_NETHERRACK.getDefaultState();

        if(ConfigHandler.biome.torridWasteland.generateBiome)
        {
            NetherExBiomes.addBiome(this, ConfigHandler.biome.torridWasteland.biomeRarity, Blocks.LAVA.getDefaultState());
        }
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.biome.torridWasteland.generateLavaSprings)
        {
            for(int i = 0; i < ConfigHandler.biome.torridWasteland.lavaSpringRarity; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.torridWasteland.generateFire)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.biome.torridWasteland.fireRarity); i++)
            {
                fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.torridWasteland.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.biome.torridWasteland.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.torridWasteland.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigHandler.biome.torridWasteland.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigHandler.biome.torridWasteland.generateQuartzOre)
        {
            for(int i = 0; i < ConfigHandler.biome.torridWasteland.quartzOreRarity; i++)
            {
                quartzOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigHandler.biome.torridWasteland.generateBasalt)
        {
            for(int i = 0; i < ConfigHandler.biome.torridWasteland.basaltRarity; i++)
            {
                basalt.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        if(ConfigHandler.biome.torridWasteland.generateMagma)
        {
            for(int i = 0; i < ConfigHandler.biome.torridWasteland.magmaRarity; i++)
            {
                magma.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(9) + 28, rand.nextInt(16)));
            }
        }

        if(ConfigHandler.biome.torridWasteland.generateLavaTraps)
        {
            for(int i = 0; i < ConfigHandler.biome.torridWasteland.lavaTrapRarity; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.torridWasteland.generateLavaPits)
        {
            BlockPos newPos = pos.add(rand.nextInt(16) + 8, rand.nextInt(64) + 32, rand.nextInt(16) + 8);

            if(world.getBiomeForCoordsBody(newPos) == this)
            {
                for(int i = 0; i < ConfigHandler.biome.torridWasteland.lavaPitRarity; i++)
                {
                    lavaPit.generate(world, rand, newPos);
                }
            }
        }

        if(ConfigHandler.biome.torridWasteland.generateCrypts)
        {
            if(rand.nextInt(ConfigHandler.biome.torridWasteland.cryptRarity) == 0)
            {
                crypt.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.torridWasteland.generateGraves)
        {
            if(rand.nextInt(ConfigHandler.biome.torridWasteland.graveRarity) == 0)
            {
                grave.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.torridWasteland.generateGraveyards)
        {
            if(rand.nextInt(ConfigHandler.biome.torridWasteland.graveyardRarity) == 0)
            {
                graveyard.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.torridWasteland.generateSarcophagus)
        {
            if(rand.nextInt(ConfigHandler.biome.torridWasteland.sarcophagusRarity) == 0)
            {
                sarcophagus.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.torridWasteland.generatePyramids)
        {
            if(rand.nextInt(ConfigHandler.biome.torridWasteland.pyramidRarity) == 0)
            {
                pyramid.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
