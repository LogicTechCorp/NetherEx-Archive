package nex;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import nex.init.ModBiomes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Compatibility
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|Compatibility");

    public static void init()
    {
        for(FMLInterModComms.IMCMessage message : FMLInterModComms.fetchRuntimeMessages(NetherEx.instance))
        {
            if(message.key.equals("getBiomeId"))
            {
                getBiomeId(message);
            }
        }
    }

    public static void postInit()
    {
        for(FMLInterModComms.IMCMessage message : FMLInterModComms.fetchRuntimeMessages(NetherEx.instance))
        {
            if(message.key.equals("addBiome"))
            {
                addBiome(message);
            }
            else if(message.key.equals("removeBiome"))
            {
                removeBiome(message);
            }
        }
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
            LOGGER.info(String.format("Attempting to get the a biome id for %s.", message.getSender()));

            int biomeId = ModBiomes.getBiomeId(biomeName);

            if(biomeId != -1)
            {
                compound = new NBTTagCompound();
                compound.setInteger("BiomeId", biomeId);

                LOGGER.info(String.format("The attempt to get a biome id, for %s, was successful.", message.getSender()));
                FMLInterModComms.sendMessage(message.getSender(), "biomeId", compound);
            }
            else
            {
                LOGGER.info(String.format("The attempt to get a biome id, for %s, was unsuccessful. It is not a nether biome.", message.getSender()));
            }
        }
        else
        {
            LOGGER.warn(String.format("An attempt to get a biome id for, %s, was unsuccessful. The biome name was null.", message.getSender()));
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
                LOGGER.info(String.format("Attempting to add the %s biome, from %s, to the Nether.", biome.getBiomeName(), message.getSender()));

                if(ModBiomes.addBiome(entry))
                {
                    LOGGER.info(String.format("The attempt to add the %s biome, from %s, to the Nether was successful.", biome.getBiomeName(), message.getSender()));
                }
                else
                {
                    LOGGER.info(String.format("The attempt to modify the %s biome weight was successful.", biome.getBiomeName()));
                }
            }
            else
            {
                LOGGER.warn(String.format("An attempt to add the %s biome, from %s, to the Nether was unsuccessful. The biome weight was less than or equal to zero.", biome.getBiomeName(), message.getSender()));
            }
        }
        else
        {
            LOGGER.warn(String.format("An attempt to add a biome, from %s, to the Nether was unsuccessful. The biome was null.", message.getSender()));
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
            LOGGER.info(String.format("Attempting to remove the %s biome, from %s, from the Nether.", biome.getBiomeName(), message.getSender()));

            if(ModBiomes.removeBiome(biome))
            {
                LOGGER.info(String.format("The attempt to remove the %s biome, from %s, from the Nether was successful.", biome.getBiomeName(), message.getSender()));
            }
            else
            {
                LOGGER.info(String.format("The attempt to remove the %s biome, from %s, was unsuccessful. It is not a Nether biome.", biome.getBiomeName(), message.getSender()));
            }
        }
        else
        {
            LOGGER.warn(String.format("An attempt to remove a biome, from %s, from the Nether was unsuccessful. The biome was null.", message.getSender()));
        }
    }
}
