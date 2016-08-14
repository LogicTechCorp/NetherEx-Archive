package nex.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.api.world.biome.INetherBiome;

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
            genDecorations(world, random, (INetherBiome) biome, pos);
            decorating = false;
        }
    }

    private void genDecorations(World world, Random rand, INetherBiome biome, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));
        generateOres(world, rand, biome, pos);
        biome.genDecorations(world, rand, pos);
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }

    private void generateOres(World world, Random rand, INetherBiome biome, BlockPos pos)
    {
        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));
        biome.generateOres(world, rand, pos);
        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));
    }
}
