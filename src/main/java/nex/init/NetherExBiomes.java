/*
 * Copyright (C) 2016.  LogicTechCorp
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
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.world.WorldProviderNether;
import nex.world.biome.BiomeBoneyard;
import nex.world.biome.BiomeHell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExBiomes
{
    private static Set<BiomeManager.BiomeEntry> biomeEntries = Sets.newHashSet();

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":hell")
    public static final BiomeHell HELL = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":boneyard")
    public static final BiomeBoneyard BONEYARD = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExBiomes");

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBiomes(RegistryEvent.Register<Biome> event)
        {
            event.getRegistry().registerAll(
                    new BiomeHell(),
                    new BiomeBoneyard()
            );

            LOGGER.info("Biome registration has been completed.");
        }
    }

    public static void replaceNether()
    {
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

    public static boolean addBiome(BiomeManager.BiomeEntry entry)
    {
        for(BiomeManager.BiomeEntry e : biomeEntries)
        {
            if(entry.biome == e.biome)
            {
                e.itemWeight += entry.itemWeight;
                return false;
            }
        }

        return biomeEntries.add(entry);
    }

    public static boolean removeBiome(Biome biome)
    {
        for(BiomeManager.BiomeEntry e : biomeEntries)
        {
            if(biome == e.biome)
            {
                return biomeEntries.remove(e);
            }
        }

        return false;
    }

    public static ImmutableList<BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return ImmutableList.copyOf(biomeEntries);
    }
}