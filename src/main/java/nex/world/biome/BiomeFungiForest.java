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

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.entity.neutral.EntityMogus;
import nex.handler.ConfigurationHandler;
import nex.init.NetherExBlocks;
import nex.world.gen.feature.WorldGenElderMushroom;
import nex.world.gen.feature.WorldGenEnokiMushroom;
import nex.world.gen.feature.WorldGenGlowStone;
import nex.world.gen.feature.WorldGenMinableMeta;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BiomeFungiForest extends BiomeNetherEx
{
    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator quartz = new WorldGenMinableMeta(NetherExBlocks.ORE_QUARTZ.getStateFromMeta(2), 14, NetherExBlocks.BLOCK_NETHERRACK.getStateFromMeta(2));
    private WorldGenerator elderMushroom = new WorldGenElderMushroom();
    private WorldGenerator enokiMushroom = new WorldGenEnokiMushroom();

    public BiomeFungiForest()
    {
        super(new BiomeProperties("Fungi Forest").setTemperature(1.1F).setRainfall(0.0F).setRainDisabled(), "fungi_forest", ConfigurationHandler.BiomeFungiForest.biomeRarity, new ItemStack(Blocks.LAVA));

        spawnableMonsterList.add(new SpawnListEntry(EntityMogus.class, 100, 4, 8));

        topBlock = NetherExBlocks.BLOCK_HYPHAE.getDefaultState();
        fillerBlock = NetherExBlocks.BLOCK_NETHERRACK.getStateFromMeta(2);
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigurationHandler.BiomeFungiForest.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigurationHandler.BiomeFungiForest.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeFungiForest.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeFungiForest.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigurationHandler.BiomeFungiForest.generateQuartzOre)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeFungiForest.quartzOreRarity; i++)
            {
                quartz.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }
        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigurationHandler.BiomeFungiForest.generateElderMushrooms)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeFungiForest.elderMushroomRarity * 16; i++)
            {
                elderMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(80) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigurationHandler.BiomeFungiForest.generateEnokiMushrooms)
        {
            for(int i = 0; i < ConfigurationHandler.BiomeFungiForest.enokiMushroomRarity * 16; i++)
            {
                enokiMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(80) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
