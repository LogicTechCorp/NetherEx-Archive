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
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.NetherEx;
import nex.block.BlockNetherrack;
import nex.entity.monster.EntityBoneSpider;
import nex.entity.monster.EntityBrute;
import nex.entity.monster.EntityWight;
import nex.handler.ConfigHandler;
import nex.init.NetherExBiomes;
import nex.init.NetherExBlocks;
import nex.init.NetherExLootTables;
import nex.world.gen.feature.WorldGenBlueFire;
import nex.world.gen.feature.WorldGenGlowStone;
import nex.world.gen.feature.WorldGenMinableMeta;
import nex.world.gen.feature.WorldGenPit;
import nex.world.gen.structure.WorldGenCeilingStructure;
import nex.world.gen.structure.WorldGenGroundStructure;
import nex.world.gen.structure.WorldGenWallStructure;

import java.util.Random;
import java.util.Set;

@SuppressWarnings("ConstantConditions")
public class BiomeArcticAbyss extends BiomeNetherEx
{
    private final Set<IBlockState> allowedBlocks = Sets.newHashSet(
            NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY),
            NetherExBlocks.BLOCK_ICE_FROSTBURN.getDefaultState(),
            NetherExBlocks.ORE_QUARTZ.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY)
    );

    private WorldGenerator fire = new WorldGenBlueFire(NetherExBlocks.BLOCK_ICE_FROSTBURN.getDefaultState());
    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator quartzOre = new WorldGenMinableMeta(NetherExBlocks.ORE_QUARTZ.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY), 14, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY));
    private WorldGenerator rimeOre = new WorldGenMinableMeta(NetherExBlocks.ORE_RIME.getDefaultState(), 7, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY));
    private WorldGenerator ichorPit = new WorldGenPit(NetherExBlocks.FLUID_ICHOR, NetherExBlocks.BLOCK_ICE_FROSTBURN.getDefaultState(), Blocks.MAGMA.getDefaultState());
    private WorldGenerator crypt = new WorldGenGroundStructure("arctic_abyss", "crypt", new String[]{""}, allowedBlocks, new String[]{""}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE, NetherExLootTables.CHEST_GRAVE_ARCTIC_ABYSS});
    private WorldGenerator grave = new WorldGenGroundStructure("arctic_abyss", "grave", new String[]{"chest", "empty"}, allowedBlocks, new String[]{""}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE});
    private WorldGenerator graveyard = new WorldGenGroundStructure("arctic_abyss", "graveyard", new String[]{""}, allowedBlocks, new String[]{"ghast", NetherEx.MOD_ID + ":monster_wight"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE, NetherExLootTables.CHEST_GRAVE_ARCTIC_ABYSS, NetherExLootTables.CHEST_GRAVE_RARE});
    private WorldGenerator sarcophagus = new WorldGenGroundStructure("arctic_abyss", "sarcophagus", new String[]{""}, allowedBlocks, new String[]{"ghast", NetherEx.MOD_ID + ":monster_wight"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_RARE});
    private WorldGenerator fossil = new WorldGenWallStructure("arctic_abyss", "fossil", new String[]{"skull_huge_extinct", "skull_creeper_variant", "skull_creeper", "skull_extinct_variant", "skull_extinct", "jaw_extinct", "rib_huge_extinct", "rib_creeper", "rib_extinct", "leg_creeper_variant_2", "leg_creeper_variant", "leg_creeper", "leg_extinct_variant", "leg_extinct", "tail_huge_extinct", "tail_extinct", "fin_extinct"}, new String[]{""}, new ResourceLocation[]{LootTableList.EMPTY});
    private WorldGenerator prison = new WorldGenGroundStructure("arctic_abyss", "prison", new String[]{"large", "medium_variant", "medium", "small"}, allowedBlocks, new String[]{"blaze", NetherEx.MOD_ID + ":monster_wight", NetherEx.MOD_ID + ":monster_bone_spider"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_RARE, NetherExLootTables.CHEST_TEMPLE_ARCTIC_ABYSS, NetherExLootTables.CHEST_TEMPLE_RARE});
    private WorldGenerator chainIsland = new WorldGenCeilingStructure("arctic_abyss", "chain", new String[]{"island_large", "island_medium", "island_small"}, new String[]{""}, new ResourceLocation[]{LootTableList.EMPTY});
    private WorldGenerator hangingChain = new WorldGenCeilingStructure("arctic_abyss", "chain", new String[]{"hanging_long", "hanging_medium", "hanging_short"}, new String[]{""}, new ResourceLocation[]{LootTableList.EMPTY});
    private WorldGenerator fallenChain = new WorldGenGroundStructure("arctic_abyss", "chain", new String[]{"fallen_long", "fallen_medium", "fallen_short_variant", "fallen_short"}, allowedBlocks, new String[]{""}, new ResourceLocation[]{LootTableList.EMPTY});

    public BiomeArcticAbyss()
    {
        super(new BiomeProperties("Arctic Abyss").setTemperature(0.0F).setRainfall(0.0F).setRainDisabled(), "arctic_abyss");

        spawnableMonsterList.add(new SpawnListEntry(EntityWight.class, 100, 1, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 1, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityBoneSpider.class, 35, 1, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 25, 1, 1));
        spawnableMonsterList.add(new SpawnListEntry(EntityBrute.class, 15, 1, 1));

        topBlock = NetherExBlocks.BLOCK_ICE_FROSTBURN.getDefaultState();
        fillerBlock = NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.ICY);

        if(ConfigHandler.Biome.ArcticAbyss.generateBiome)
        {
            NetherExBiomes.addBiome(this, ConfigHandler.Biome.ArcticAbyss.biomeRarity, Blocks.MAGMA.getDefaultState());
        }
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.ArcticAbyss.generateChainIslands)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.chainIslandRarity) == 0)
            {
                chainIsland.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateFire)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.Biome.ArcticAbyss.fireRarity); i++)
            {
                fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.Biome.ArcticAbyss.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigHandler.Biome.ArcticAbyss.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.ArcticAbyss.generateQuartzOre)
        {
            for(int i = 0; i < ConfigHandler.Biome.ArcticAbyss.quartzOreRarity; i++)
            {
                quartzOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateRimeOre)
        {
            for(int i = 0; i < ConfigHandler.Biome.ArcticAbyss.rimeOreRarity; i++)
            {
                rimeOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigHandler.Biome.ArcticAbyss.generateIchorPits)
        {
            BlockPos newPos = pos.add(rand.nextInt(16) + 8, rand.nextInt(64) + 32, rand.nextInt(16) + 8);

            if(world.getBiomeForCoordsBody(newPos) == this)
            {
                if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.ichorPitRarity) == 0)
                {
                    ichorPit.generate(world, rand, newPos);
                }
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateCrypts)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.cryptRarity) == 0)
            {
                crypt.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateGraves)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.graveRarity) == 0)
            {
                grave.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateGraveyards)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.graveyardRarity) == 0)
            {
                graveyard.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateSarcophagus)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.sarcophagusRarity) == 0)
            {
                sarcophagus.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateFossils)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.fossilRarity) == 0)
            {
                fossil.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generatePrisons)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.prisonRarity) == 0)
            {
                prison.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateHangingChains)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.hangingChainRarity) == 0)
            {
                hangingChain.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.ArcticAbyss.generateFallenChains)
        {
            if(rand.nextInt(ConfigHandler.Biome.ArcticAbyss.fallenChainRarity) == 0)
            {
                fallenChain.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
