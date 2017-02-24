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
import net.minecraft.item.ItemStack;
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
import nex.init.NetherExBiomes;
import nex.init.NetherExBlocks;
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
    private WorldGenerator crypt = new WorldGenGroundStructure("ruthless_sands", "crypt", new String[]{""}, allowedBlocks, new String[]{""}, true, LootTableList.CHESTS_NETHER_BRIDGE);
    private WorldGenerator grave = new WorldGenGroundStructure("ruthless_sands", "grave", new String[]{"chest", "empty"}, allowedBlocks, new String[]{""}, true, LootTableList.CHESTS_NETHER_BRIDGE);
    private WorldGenerator graveyard = new WorldGenGroundStructure("ruthless_sands", "graveyard", new String[]{""}, allowedBlocks, new String[]{"wither_skeleton", NetherEx.MOD_ID + ":monster_spinout"}, true, LootTableList.CHESTS_NETHER_BRIDGE);
    private WorldGenerator sarcophagus = new WorldGenGroundStructure("ruthless_sands", "sarcophagus", new String[]{""}, allowedBlocks, new String[]{"wither_skeleton", NetherEx.MOD_ID + ":monster_spinout"}, true, LootTableList.CHESTS_NETHER_BRIDGE);
    private WorldGenerator altar = new WorldGenGroundStructure("ruthless_sands", "altar", new String[]{"intact", "ruined", "destroyed"}, allowedBlocks, new String[]{""}, false, LootTableList.EMPTY);
    private WorldGenerator throne = new WorldGenGroundStructure("ruthless_sands", "throne", new String[]{""}, allowedBlocks, new String[]{""}, false, LootTableList.EMPTY);
    private WorldGenerator waypoint = new WorldGenGroundStructure("ruthless_sands", "waypoint", new String[]{""}, allowedBlocks, new String[]{""}, false, LootTableList.EMPTY);

    public BiomeRuthlessSands()
    {
        super(new BiomeProperties("Ruthless Sands").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "ruthless_sands");

        topBlock = Blocks.SOUL_SAND.getDefaultState();
        fillerBlock = NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.GLOOMY);

        spawnableMonsterList.add(new SpawnListEntry(EntitySpinout.class, 100, 1, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityWitherSkeleton.class, 65, 1, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 45, 1, 4));

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

        if(ConfigHandler.Biome.RuthlessSands.generateCrypts)
        {
            if(rand.nextInt(ConfigHandler.Biome.RuthlessSands.cryptRarity) == 0)
            {
                crypt.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.RuthlessSands.generateGraves)
        {
            if(rand.nextInt(ConfigHandler.Biome.RuthlessSands.graveRarity) == 0)
            {
                grave.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.RuthlessSands.generateGraveyards)
        {
            if(rand.nextInt(ConfigHandler.Biome.RuthlessSands.graveyardRarity) == 0)
            {
                graveyard.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.RuthlessSands.generateSarcophagus)
        {
            if(rand.nextInt(ConfigHandler.Biome.RuthlessSands.sarcophagusRarity) == 0)
            {
                sarcophagus.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.RuthlessSands.generateAltars)
        {
            if(rand.nextInt(ConfigHandler.Biome.RuthlessSands.altarRarity) == 0)
            {
                altar.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.RuthlessSands.generateThrones)
        {
            if(rand.nextInt(ConfigHandler.Biome.RuthlessSands.throneRarity) == 0)
            {
                throne.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.RuthlessSands.generateWaypoints)
        {
            if(rand.nextInt(ConfigHandler.Biome.RuthlessSands.waypointRarity) == 0)
            {
                waypoint.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
