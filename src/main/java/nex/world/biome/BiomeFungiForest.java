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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.NetherEx;
import nex.block.BlockNetherrack;
import nex.entity.monster.EntitySpore;
import nex.entity.monster.EntitySporeCreeper;
import nex.entity.neutral.EntityMogus;
import nex.handler.ConfigHandler;
import nex.init.NetherExBlocks;
import nex.init.NetherExLootTables;
import nex.world.gen.feature.WorldGenElderMushroom;
import nex.world.gen.feature.WorldGenEnokiMushroom;
import nex.world.gen.feature.WorldGenGlowStone;
import nex.world.gen.feature.WorldGenMinableMeta;
import nex.world.gen.structure.WorldGenAirStructure;
import nex.world.gen.structure.WorldGenGroundStructure;

import java.util.Random;
import java.util.Set;

@SuppressWarnings("ConstantConditions")
public class BiomeFungiForest extends BiomeNetherEx
{
    private final Set<IBlockState> allowedBlocks = Sets.newHashSet(
            NetherExBlocks.BLOCK_HYPHAE.getDefaultState(),
            NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.LIVELY),
            NetherExBlocks.ORE_QUARTZ.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.LIVELY)
    );

    private final WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private final WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private final WorldGenerator quartzOre = new WorldGenMinableMeta(NetherExBlocks.ORE_QUARTZ.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.LIVELY), 14, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.LIVELY));
    private final WorldGenerator elderMushroom = new WorldGenElderMushroom(WorldGenElderMushroom.allVariants, true);
    private final WorldGenerator enokiMushroom = new WorldGenEnokiMushroom();
    private final WorldGenerator crypt = new WorldGenGroundStructure("fungi_forest", "crypt", new String[]{""}, allowedBlocks, new String[]{""}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE, NetherExLootTables.CHEST_GRAVE_FUNGI_FOREST});
    private final WorldGenerator grave = new WorldGenGroundStructure("fungi_forest", "grave", new String[]{"chest", "empty"}, allowedBlocks, new String[]{""}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE});
    private final WorldGenerator graveyard = new WorldGenGroundStructure("fungi_forest", "graveyard", new String[]{""}, allowedBlocks, new String[]{NetherEx.MOD_ID + ":monster_spore_creeper"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE, NetherExLootTables.CHEST_GRAVE_FUNGI_FOREST, NetherExLootTables.CHEST_GRAVE_RARE});
    private final WorldGenerator sarcophagus = new WorldGenGroundStructure("fungi_forest", "sarcophagus", new String[]{""}, allowedBlocks, new String[]{NetherEx.MOD_ID + ":monster_spore_creeper"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_RARE});
    private final WorldGenerator temple = new WorldGenGroundStructure("fungi_forest", "temple", new String[]{"hard", "medium", "easy"}, allowedBlocks, new String[]{NetherEx.MOD_ID + ":monster_spore_creeper"}, new ResourceLocation[]{NetherExLootTables.CHEST_TEMPLE_FUNGI_FOREST, NetherExLootTables.CHEST_TEMPLE_RARE});
    private final WorldGenerator castle = new WorldGenAirStructure("fungi_forest", "castle", new String[]{""}, new String[]{"ghast"}, new ResourceLocation[]{NetherExLootTables.CHEST_TEMPLE_RARE});

    public BiomeFungiForest()
    {
        super(new BiomeProperties("Fungi Forest").setTemperature(1.1F).setRainfall(0.0F).setRainDisabled(), "fungi_forest");

        spawnableMonsterList.add(new SpawnListEntry(EntityMogus.class, 100, 4, 6));
        spawnableMonsterList.add(new SpawnListEntry(EntitySporeCreeper.class, 50, 1, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntitySpore.class, 25, 1, 4));

        topBlock = NetherExBlocks.BLOCK_HYPHAE.getDefaultState();
        fillerBlock = NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.LIVELY);
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.biome.fungiForest.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.biome.fungiForest.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.fungiForest.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigHandler.biome.fungiForest.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigHandler.biome.fungiForest.generateQuartzOre)
        {
            for(int i = 0; i < ConfigHandler.biome.fungiForest.quartzOreRarity; i++)
            {
                quartzOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigHandler.biome.fungiForest.generateCrypts)
        {
            if(rand.nextInt(ConfigHandler.biome.fungiForest.cryptRarity) == 0)
            {
                crypt.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.fungiForest.generateGraves)
        {
            if(rand.nextInt(ConfigHandler.biome.fungiForest.graveRarity) == 0)
            {
                grave.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.fungiForest.generateGraveyards)
        {
            if(rand.nextInt(ConfigHandler.biome.fungiForest.graveyardRarity) == 0)
            {
                graveyard.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.fungiForest.generateSarcophagus)
        {
            if(rand.nextInt(ConfigHandler.biome.fungiForest.sarcophagusRarity) == 0)
            {
                sarcophagus.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.fungiForest.generateTemples)
        {
            if(rand.nextInt(ConfigHandler.biome.fungiForest.templeRarity) == 0)
            {
                temple.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.fungiForest.generateCastles)
        {
            if(rand.nextInt(ConfigHandler.biome.fungiForest.castleRarity) == 0)
            {
                castle.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.fungiForest.generateElderMushrooms)
        {
            for(int i = 0; i < ConfigHandler.biome.fungiForest.elderMushroomRarity * 16; i++)
            {
                elderMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(80) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.fungiForest.generateEnokiMushrooms)
        {
            for(int i = 0; i < ConfigHandler.biome.fungiForest.enokiMushroomRarity * 16; i++)
            {
                enokiMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(80) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
