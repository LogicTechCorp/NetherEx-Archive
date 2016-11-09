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

package nex.handler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import nex.init.NetherExBiomes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IMCHandler
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|IMCHandler");

    public static void routeMessages(FMLInterModComms.IMCEvent event)
    {
        LOGGER.info("Message routing started.");

        for(FMLInterModComms.IMCMessage message : event.getMessages())
        {
            switch(message.key)
            {
                case "getBiomeId":
                    getBiomeId(message);
                    break;
                case "addBiome":
                    addBiome(message);
                    break;
                case "removeBiome":
                    removeBiome(message);
                    break;
            }
        }

        LOGGER.info("Message routing completed.");
    }

    private static void getBiomeId(FMLInterModComms.IMCMessage message)
    {
        if(!message.isNBTMessage())
        {
            return;
        }

        NBTTagCompound compound = message.getNBTValue();
        String biomeName = compound.getString("BiomeName");

        if(!biomeName.equals(""))
        {
            LOGGER.info(String.format("Attempting to get a biome id for %s.", message.getSender()));

            int biomeId = NetherExBiomes.getBiomeId(biomeName);

            if(biomeId != -1)
            {
                compound = new NBTTagCompound();
                compound.setInteger("BiomeId", biomeId);

                LOGGER.info(String.format("The attempt to get a biome id, for %s, was successful.", message.getSender()));
                FMLInterModComms.sendMessage(message.getSender(), "biomeId", compound);
            }
            else
            {
                LOGGER.info(String.format("The attempt to get a biome id, for %s, was unsuccessful. It is not a Nether biome.", message.getSender()));
            }
        }
        else
        {
            LOGGER.info(String.format("An attempt to get a biome id, for %s, was unsuccessful. The biome name was null.", message.getSender()));
        }
    }

    private static void addBiome(FMLInterModComms.IMCMessage message)
    {
        if(!message.isNBTMessage())
        {
            return;
        }

        NBTTagCompound compound = message.getNBTValue();
        int biomeId = compound.getInteger("BiomeId");
        int biomeWeight = compound.getInteger("BiomeWeight");

        Biome biome = Biome.getBiome(biomeId);
        BiomeManager.BiomeEntry entry = new BiomeManager.BiomeEntry(biome, biomeWeight);

        if(biome != null)
        {
            if(biomeWeight > 0)
            {
                LOGGER.info(String.format("Attempting to add the %s biome, for %s, to the Nether.", biome.getBiomeName(), message.getSender()));

                if(NetherExBiomes.addBiome(entry))
                {
                    LOGGER.info(String.format("The attempt to add the %s biome, for %s, to the Nether was successful.", biome.getBiomeName(), message.getSender()));
                }
                else
                {
                    LOGGER.info(String.format("The %s biome was already added to the Nether. Modified the biome's weight instead.", biome.getBiomeName()));
                }
            }
            else
            {
                LOGGER.info(String.format("An attempt to add the %s biome, for %s, to the Nether was unsuccessful. The biome weight was less than or equal to zero.", biome.getBiomeName(), message.getSender()));
            }
        }
        else
        {
            LOGGER.info(String.format("An attempt to add a biome, for %s, to the Nether was unsuccessful. The biome was null.", message.getSender()));
        }
    }

    private static void removeBiome(FMLInterModComms.IMCMessage message)
    {
        if(!message.isNBTMessage())
        {
            return;
        }

        NBTTagCompound compound = message.getNBTValue();
        int biomeId = compound.getInteger("BiomeId");

        Biome biome = Biome.getBiome(biomeId);

        if(biome != null)
        {
            LOGGER.info(String.format("Attempting to remove the %s biome, for %s, from the Nether.", biome.getBiomeName(), message.getSender()));

            if(NetherExBiomes.removeBiome(biome))
            {
                LOGGER.info(String.format("The attempt to remove the %s biome, for %s, from the Nether was successful.", biome.getBiomeName(), message.getSender()));
            }
            else
            {
                LOGGER.info(String.format("The attempt to remove the %s biome, for %s, from the Nether was unsuccessful. It is not a Nether biome.", biome.getBiomeName(), message.getSender()));
            }
        }
        else
        {
            LOGGER.info(String.format("An attempt to remove a biome, for %s, from the Nether was unsuccessful. The biome was null.", message.getSender()));
        }
    }
}