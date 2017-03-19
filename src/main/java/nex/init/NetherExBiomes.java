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

package nex.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.world.WorldProviderNether;
import nex.world.biome.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@SuppressWarnings("ConstantConditions")
@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExBiomes
{
    private static Set<BiomeManager.BiomeEntry> biomeEntries = Sets.newHashSet();
    private static Set<BiomeOceanEntry> oceanEntries = Sets.newHashSet();

    @GameRegistry.ObjectHolder("hell")
    public static final BiomeHell HELL = null;

    @GameRegistry.ObjectHolder("ruthless_sands")
    public static final BiomeRuthlessSands RUTHLESS_SANDS = null;

    @GameRegistry.ObjectHolder("fungi_forest")
    public static final BiomeFungiForest FUNGI_FOREST = null;

    @GameRegistry.ObjectHolder("torrid_wasteland")
    public static final BiomeTorridWasteland TORRID_WASTELAND = null;

    @GameRegistry.ObjectHolder("arctic_abyss")
    public static final BiomeArcticAbyss ARCTIC_ABYSS = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExBiomes");

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBiomes(RegistryEvent.Register<Biome> event)
        {
            LOGGER.info("Biome registration started.");

            event.getRegistry().registerAll(
                    new BiomeHell(),
                    new BiomeRuthlessSands(),
                    new BiomeFungiForest(),
                    new BiomeTorridWasteland(),
                    new BiomeArcticAbyss()
            );

            LOGGER.info("Biome registration completed.");
        }
    }

    public static void init()
    {
        BiomeDictionary.addTypes(HELL, NETHER, HOT, DRY);
        BiomeDictionary.addTypes(RUTHLESS_SANDS, NETHER, HOT, DRY, SANDY);
        BiomeDictionary.addTypes(FUNGI_FOREST, NETHER, HOT, DRY, MUSHROOM);
        BiomeDictionary.addTypes(TORRID_WASTELAND, NETHER, HOT, DRY, WASTELAND);
        BiomeDictionary.addTypes(ARCTIC_ABYSS, NETHER, WET, COLD);

        DimensionManager.unregisterDimension(-1);
        DimensionType nether = DimensionType.register("Nether", "_nether", -1, WorldProviderNether.class, false);
        DimensionManager.registerDimension(-1, nether);

        LOGGER.info("The Nether has been overridden.");
    }

    public static int getBiomeId(String name)
    {
        for(BiomeManager.BiomeEntry entry : biomeEntries)
        {
            if(entry.biome.getRegistryName().getResourcePath().equals(name))
            {
                return Biome.getIdForBiome(entry.biome);
            }
        }

        return -1;
    }

    public static boolean addBiome(Biome biome, int weight, ItemStack stack)
    {
        if(weight <= 0)
        {
            weight = 1;
        }

        BiomeManager.BiomeEntry biomeEntry = new BiomeManager.BiomeEntry(biome, weight);

        for(BiomeManager.BiomeEntry entry : biomeEntries)
        {
            if(biomeEntry.biome == entry.biome)
            {
                entry.itemWeight += biomeEntry.itemWeight;

                if(stack != ItemStack.EMPTY)
                {
                    break;
                }
                return false;
            }
        }

        BiomeOceanEntry oceanEntry;

        if(Block.getBlockFromItem(stack.getItem()) != Blocks.AIR)
        {
            oceanEntry = new BiomeOceanEntry(biome, Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getItemDamage()));
        }
        else
        {
            oceanEntry = new BiomeOceanEntry(biome, Blocks.LAVA.getDefaultState());
        }

        if(!oceanEntries.contains(oceanEntry))
        {
            oceanEntries.add(oceanEntry);
        }

        return biomeEntries.add(biomeEntry);
    }

    public static boolean removeBiome(Biome biome)
    {
        for(BiomeManager.BiomeEntry biomeEntry : biomeEntries)
        {
            if(biome == biomeEntry.biome)
            {
                for(BiomeOceanEntry oceanEntry : oceanEntries)
                {
                    if(biomeEntry.biome == oceanEntry.biome)
                    {
                        oceanEntries.remove(oceanEntry);
                    }
                }
                return biomeEntries.remove(biomeEntry);
            }
        }

        return false;
    }

    public static ImmutableList<BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return ImmutableList.copyOf(biomeEntries);
    }

    public static IBlockState getBiomeOceanBlockState(Biome biome)
    {
        for(BiomeOceanEntry oceanEntry : oceanEntries)
        {
            if(biome == oceanEntry.biome)
            {
                return oceanEntry.state;
            }
        }

        return Blocks.LAVA.getDefaultState();
    }

    public static class BiomeOceanEntry
    {
        public final Biome biome;
        public final IBlockState state;

        public BiomeOceanEntry(Biome biomeIn, IBlockState stateIn)
        {
            biome = biomeIn;
            state = stateIn;
        }
    }
}
