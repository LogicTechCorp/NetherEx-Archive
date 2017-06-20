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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.handler.ConfigHandler;
import nex.init.NetherExBlocks;
import nex.init.NetherExLootTables;
import nex.world.gen.feature.*;
import nex.world.gen.structure.WorldGenGroundStructure;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BiomeHell extends BiomeNetherEx
{
    private final WorldGenerator lavaSpring = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), false);
    private final WorldGenerator fire = new WorldGenFire(Blocks.NETHERRACK.getDefaultState());
    private final WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private final WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private final WorldGenerator smallBrownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM, Blocks.NETHERRACK.getDefaultState());
    private final WorldGenerator smallRedMushroom = new WorldGenBush(Blocks.RED_MUSHROOM, Blocks.NETHERRACK.getDefaultState());
    private final WorldGenerator quartzOre = new WorldGenMinableMeta(Blocks.QUARTZ_ORE.getDefaultState(), 14, Blocks.NETHERRACK.getDefaultState());
    private final WorldGenerator amethystOre = new WorldGenMinableMeta(NetherExBlocks.ORE_AMETHYST.getDefaultState(), 3, Blocks.NETHERRACK.getDefaultState());
    private final WorldGenerator magma = new WorldGenMinableMeta(Blocks.MAGMA.getDefaultState(), 32, Blocks.NETHERRACK.getDefaultState());
    private final WorldGenerator lavaTrap = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), true);
    private final WorldGenerator crypt = new WorldGenGroundStructure("hell", "crypt", new String[]{""}, new String[]{""}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE, NetherExLootTables.CHEST_GRAVE_BASE});
    private final WorldGenerator grave = new WorldGenGroundStructure("hell", "grave", new String[]{"chest", "empty"}, new String[]{""}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE});
    private final WorldGenerator graveyard = new WorldGenGroundStructure("hell", "graveyard", new String[]{""}, new String[]{"zombie_pigman", "magma_cube"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_BASE, NetherExLootTables.CHEST_GRAVE_RARE});
    private final WorldGenerator sarcophagus = new WorldGenGroundStructure("hell", "sarcophagus", new String[]{""}, new String[]{"zombie_pigman", "magma_cube"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_RARE});
    private final WorldGenerator mausoleum = new WorldGenGroundStructure("hell", "mausoleum", new String[]{""}, new String[]{"zombie_pigman", "magma_cube"}, new ResourceLocation[]{NetherExLootTables.CHEST_GRAVE_RARE});
    private final WorldGenerator village = new WorldGenGroundStructure("hell", "village_pigtificate", new String[]{"huge", "large_variant", "large", "medium_variant_2", "medium_variant", "medium", "small_variant_2", "small_variant", "small", "tiny_variant_3", "tiny_variant_2", "tiny_variant", "tiny"}, new String[]{""}, new ResourceLocation[]{NetherExLootTables.CHEST_VILLAGE_BASE, NetherExLootTables.CHEST_TEMPLE_BASE, NetherExLootTables.CHEST_TEMPLE_RARE});

    public BiomeHell()
    {
        super(new BiomeProperties("Hell").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "hell");
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.biome.hell.generateLavaSprings)
        {
            for(int i = 0; i < ConfigHandler.biome.hell.lavaSpringRarity; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.hell.generateFire)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.biome.hell.fireRarity); i++)
            {
                fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.hell.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.biome.hell.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.hell.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigHandler.biome.hell.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.hell.generateBrownMushrooms)
        {
            if(rand.nextBoolean())
            {
                smallBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.hell.generateRedMushrooms)
        {
            if(rand.nextBoolean())
            {
                smallRedMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigHandler.biome.hell.generateQuartzOre)
        {
            for(int i = 0; i < ConfigHandler.biome.hell.quartzOreRarity; i++)
            {
                quartzOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        if(ConfigHandler.biome.hell.generateAmethystOre)
        {
            for(int i = 0; i < ConfigHandler.biome.hell.amethystOreRarity; i++)
            {
                amethystOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigHandler.biome.hell.generateMagma)
        {
            for(int i = 0; i < ConfigHandler.biome.hell.magmaRarity; i++)
            {
                magma.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(9) + 28, rand.nextInt(16)));
            }
        }

        if(ConfigHandler.biome.hell.generateLavaTraps)
        {
            for(int i = 0; i < ConfigHandler.biome.hell.lavaTrapRarity; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.hell.generateCrypts)
        {
            if(rand.nextInt(ConfigHandler.biome.hell.cryptRarity) == 0)
            {
                crypt.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.hell.generateGraves)
        {
            if(rand.nextInt(ConfigHandler.biome.hell.graveRarity) == 0)
            {
                grave.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.hell.generateGraveyards)
        {
            if(rand.nextInt(ConfigHandler.biome.hell.graveyardRarity) == 0)
            {
                graveyard.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.hell.generateSarcophagus)
        {
            if(rand.nextInt(ConfigHandler.biome.hell.sarcophagusRarity) == 0)
            {
                sarcophagus.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.hell.generateMausoleums)
        {
            if(rand.nextInt(ConfigHandler.biome.hell.mausoleumRarity) == 0)
            {
                mausoleum.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.biome.hell.generateVillages)
        {
            if(rand.nextInt(ConfigHandler.biome.hell.villageRarity) == 0)
            {
                village.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
