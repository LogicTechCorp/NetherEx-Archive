package nex.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.api.biome.INetherBiome;

import java.util.Random;

public class BiomeDecoratorHell extends BiomeDecorator
{
    @Override
    public void decorate(World world, Random random, Biome biome, BlockPos pos)
    {
        if(decorating)
        {
            throw new RuntimeException("Already decorating");
        }
        else
        {
            genDecorations(world, random, biome, pos);
            decorating = false;
        }
    }

    private void genDecorations(World world, Random rand, Biome biome, BlockPos pos)
    {
        INetherBiome netherBiome = (INetherBiome) biome;

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));
        generateOres(world, rand, netherBiome, pos);
        netherBiome.genDecorations(world, rand, pos);
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }

    private void generateOres(World world, Random rand, INetherBiome netherBiome, BlockPos pos)
    {
        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));
        netherBiome.generateOres(world, rand, pos);
        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));
    }
}
