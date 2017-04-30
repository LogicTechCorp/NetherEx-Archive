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
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
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
import nex.block.BlockThornstalk;
import nex.entity.monster.EntitySpinout;
import nex.handler.ConfigHandler;
import nex.init.NetherExBlocks;
import nex.init.NetherExLootTables;
import nex.world.gen.feature.WorldGenGlowStone;
import nex.world.gen.feature.WorldGenLava;
import nex.world.gen.feature.WorldGenMinableMeta;
import nex.world.gen.feature.WorldGenThornstalk;
import nex.world.gen.structure.WorldGenGroundStructure;

import java.util.Random;
import java.util.Set;

@SuppressWarnings("ConstantConditions")
public class BiomeRuthlessSands extends BiomeNetherEx
{
    private final Set<IBlockState> allowedBlocks = Sets.newHashSet(
            Blocks.SOUL_SAND.getDefaultState(),
            NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.GLOOMY),
            NetherExBlocks.ORE_QUARTZ.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.GLOOMY),
            NetherExBlocks.PLANT_THORNSTALK.getDefaultState().withProperty(BlockThornstalk.PART, BlockThornstalk.EnumPart.TOP),
            NetherExBlocks.PLANT_THORNSTALK.getDefaultState().withProperty(BlockThornstalk.PART, BlockThornstalk.EnumPart.MIDDLE),
            NetherExBlocks.PLANT_THORNSTALK.getDefaultState().withProperty(BlockThornstalk.PART, BlockThornstalk.EnumPart.BOTTOM)
    );

    private WorldGenerator lavaSpring = new WorldGenLava(NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.GLOOMY), false);
    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator quartzOre = new WorldGenMinableMeta(NetherExBlocks.ORE_QUARTZ.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.GLOOMY), 14, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.GLOOMY));
    private WorldGenerator lavaTrap = new WorldGenLava(NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.GLOOMY), true);
    private WorldGenerator thornstalk = new WorldGenThornstalk();
    private WorldGenerator crypt = new WorldGenGroundStructure("ruthless_sands", "crypt", new String[]{""}, allowedBlocks, new String[]{""}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE, NetherExLootTables.CHEST_GRAVE_RUTHLESS_SANDS});
    private WorldGenerator grave = new WorldGenGroundStructure("ruthless_sands", "grave", new String[]{"chest", "empty"}, allowedBlocks, new String[]{""}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE});
    private WorldGenerator graveyard = new WorldGenGroundStructure("ruthless_sands", "graveyard", new String[]{""}, allowedBlocks, new String[]{"wither_skeleton", NetherEx.MOD_ID + ":monster_spinout"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE, NetherExLootTables.CHEST_GRAVE_RUTHLESS_SANDS, NetherExLootTables.CHEST_GRAVE_RARE});
    private WorldGenerator sarcophagus = new WorldGenGroundStructure("ruthless_sands", "sarcophagus", new String[]{""}, allowedBlocks, new String[]{"wither_skeleton", NetherEx.MOD_ID + ":monster_spinout"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_RARE});
    private WorldGenerator altar = new WorldGenGroundStructure("ruthless_sands", "altar", new String[]{"intact", "ruined", "destroyed"}, allowedBlocks, new String[]{""}, new ResourceLocation[]{LootTableList.EMPTY});
    private WorldGenerator waypoint = new WorldGenGroundStructure("ruthless_sands", "waypoint", new String[]{""}, allowedBlocks, new String[]{""}, new ResourceLocation[]{LootTableList.EMPTY});

    public BiomeRuthlessSands()
    {
        super(new BiomeProperties("Ruthless Sands").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "ruthless_sands");

        topBlock = Blocks.SOUL_SAND.getDefaultState();
        fillerBlock = NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.GLOOMY);

        spawnableMonsterList.add(new SpawnListEntry(EntitySpinout.class, 100, 1, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityWitherSkeleton.class, 65, 1, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 45, 1, 4));
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.biome.ruthlessSands.generateLavaSprings)
        {
            for(int i = 0; i < ConfigHandler.biome.ruthlessSands.lavaSpringRarity; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.ruthlessSands.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.biome.ruthlessSands.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.ruthlessSands.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigHandler.biome.ruthlessSands.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigHandler.biome.ruthlessSands.generateQuartzOre)
        {
            for(int i = 0; i < ConfigHandler.biome.ruthlessSands.quartzOreRarity; i++)
            {
                quartzOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigHandler.biome.ruthlessSands.generateLavaTraps)
        {
            for(int i = 0; i < ConfigHandler.biome.ruthlessSands.lavaTrapRarity; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.ruthlessSands.generateThornstalk)
        {
            for(int i = 0; i < ConfigHandler.biome.ruthlessSands.thornstalkRarity; i++)
            {
                thornstalk.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.ruthlessSands.generateCrypts)
        {
            if(rand.nextInt(ConfigHandler.biome.ruthlessSands.cryptRarity) == 0)
            {
                crypt.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.ruthlessSands.generateGraves)
        {
            if(rand.nextInt(ConfigHandler.biome.ruthlessSands.graveRarity) == 0)
            {
                grave.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.ruthlessSands.generateGraveyards)
        {
            if(rand.nextInt(ConfigHandler.biome.ruthlessSands.graveyardRarity) == 0)
            {
                graveyard.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.ruthlessSands.generateSarcophagus)
        {
            if(rand.nextInt(ConfigHandler.biome.ruthlessSands.sarcophagusRarity) == 0)
            {
                sarcophagus.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.ruthlessSands.generateAltars)
        {
            if(rand.nextInt(ConfigHandler.biome.ruthlessSands.altarRarity) == 0)
            {
                altar.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.ruthlessSands.generateWaypoints)
        {
            if(rand.nextInt(ConfigHandler.biome.ruthlessSands.waypointRarity) == 0)
            {
                waypoint.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
