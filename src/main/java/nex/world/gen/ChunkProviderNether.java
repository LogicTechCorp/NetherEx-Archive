package nex.world.gen;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import nex.api.biome.NetherBiome;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkProviderNether extends ChunkProviderHell
{
    private World world;
    private Random rand;

    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorOctaves noiseGenSoulSandGravel;
    private NoiseGeneratorOctaves noiseGenNetherrack;
    private NoiseGeneratorOctaves noiseGenScale;
    private NoiseGeneratorOctaves noiseGenDepth;

    private Biome[] biomesForGen;

    private double[] buffer;
    private double[] depthBuffer = new double[256];
    private double[] soulSandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private double[] noiseData1;
    private double[] noiseData2;
    private double[] noiseData3;
    private double[] noiseData4;
    private double[] noiseData5;

    private MapGenNetherBridge netherBridge = new MapGenNetherBridge();
    private MapGenCavesHell netherCaves = new MapGenCavesHell();

    public ChunkProviderNether(World worldIn)
    {
        super(worldIn, false, worldIn.getSeed());
        world = worldIn;
        rand = new Random(world.getSeed());
        noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
        noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
        noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
        noiseGenSoulSandGravel = new NoiseGeneratorOctaves(rand, 4);
        noiseGenNetherrack = new NoiseGeneratorOctaves(rand, 4);
        noiseGenScale = new NoiseGeneratorOctaves(rand, 10);
        noiseGenDepth = new NoiseGeneratorOctaves(rand, 16);
    }

    private void setBlocksInChunk(int chunkX, int chunkZ, ChunkPrimer primer, Biome[] biomes)
    {
        buffer = generateHeightMap(buffer, chunkX * 4, 0, chunkZ * 4, 5, 17, 5);

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                for(int k = 0; k < 16; k++)
                {
                    double d1 = buffer[(i * 5 + j) * 17 + k];
                    double d2 = buffer[(((i * 5) + j + 1) * 17) + k];
                    double d3 = buffer[((((i + 1) * 5) + j) * 17) + k];
                    double d4 = buffer[((i + 1) * 5 + j + 1) * 17 + k];
                    double d5 = (buffer[(i * 5 + j) * 17 + k + 1] - d1) * 0.125D;
                    double d6 = (buffer[(i * 5 + j + 1) * 17 + k + 1] - d2) * 0.125D;
                    double d7 = (buffer[((i + 1) * 5 + j) * 17 + k + 1] - d3) * 0.125D;
                    double d8 = (buffer[((i + 1) * 5 + j + 1) * 17 + k + 1] - d4) * 0.125D;

                    for(int i2 = 0; i2 < 8; ++i2)
                    {
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for(int j2 = 0; j2 < 4; ++j2)
                        {
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.25D;

                            for(int k2 = 0; k2 < 4; ++k2)
                            {
                                IBlockState state = null;

                                int l2 = j2 + i * 4;
                                int i3 = i2 + k * 8;
                                int j3 = k2 + j * 4;

                                Biome biome = biomes[l2 + j3 * 16];

                                if(k * 8 + i2 < 32)
                                {
                                    IBlockState oceanBlock = ((NetherBiome) biome).oceanBlock;

                                    if(oceanBlock.getMaterial() == Material.WATER)
                                    {
                                        state = Blocks.LAVA.getDefaultState();
                                    }
                                    else
                                    {
                                        state = oceanBlock;
                                    }
                                }

                                if(d15 > 0.0D)
                                {
                                    state = Blocks.NETHERRACK.getDefaultState();
                                }

                                primer.setBlockState(l2, i3, j3, state);
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    private void replaceBiomeBlocks(int chunkX, int chunkZ, ChunkPrimer primer, Biome[] biomes)
    {
        soulSandNoise = noiseGenSoulSandGravel.generateNoiseOctaves(soulSandNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, 0.03125D, 0.03125D, 1.0D);
        gravelNoise = noiseGenSoulSandGravel.generateNoiseOctaves(gravelNoise, chunkX * 16, 109, chunkZ * 16, 16, 1, 16, 0.03125D, 1.0D, 0.03125D);
        depthBuffer = noiseGenNetherrack.generateNoiseOctaves(depthBuffer, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, 0.0625D, 0.0625D, 0.0625D);

        for(int x = 0; x < 16; x++)
        {
            for(int z = 0; z < 16; z++)
            {
                int l = (int) (depthBuffer[x + z * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
                int i1 = -1;
                boolean genSoulSand = soulSandNoise[x + z * 16] + rand.nextDouble() * 0.2D > 0.0D;
                boolean genGravel = gravelNoise[x + z * 16] + rand.nextDouble() * 0.2D > 0.0D;
                Biome biome = biomes[x + z * 16];

                IBlockState topState = biome.topBlock;
                IBlockState fillerState = biome.fillerBlock;

                for(int y = 127; y >= 0; y--)
                {
                    if(y < 127 && y > 0)
                    {
                        IBlockState checkState = primer.getBlockState(x, y, z);

                        if(checkState.getMaterial() != Material.AIR)
                        {
                            if(checkState.getBlock() == Blocks.NETHERRACK)
                            {
                                if(i1 == -1)
                                {
                                    if(l <= 0)
                                    {
                                        topState = Blocks.AIR.getDefaultState();
                                        fillerState = Blocks.NETHERRACK.getDefaultState();
                                    }
                                    else if(y >= 62 && y <= 66)
                                    {
                                        topState = biome.topBlock;
                                        fillerState = biome.fillerBlock;

                                        if(genGravel)
                                        {
                                            topState = Blocks.GRAVEL.getDefaultState();
                                        }

                                        if(genSoulSand)
                                        {
                                            topState = Blocks.SOUL_SAND.getDefaultState();
                                            fillerState = Blocks.SOUL_SAND.getDefaultState();
                                        }
                                    }

                                    if(y <= 32 && (topState == null || topState.getMaterial() == Material.AIR))
                                    {
                                        topState = ((NetherBiome) biome).oceanBlock;

                                        if(topState.getMaterial() == Material.WATER)
                                        {
                                            topState = Blocks.LAVA.getDefaultState();
                                        }
                                    }

                                    i1 = l;

                                    if(topState == biome.topBlock && fillerState == biome.fillerBlock)
                                    {
                                        primer.setBlockState(x, y, z, topState);
                                    }
                                    else
                                    {
                                        if(y > 64)
                                        {
                                            primer.setBlockState(x, y, z, topState);
                                        }
                                        else
                                        {
                                            primer.setBlockState(x, y, z, fillerState);
                                        }
                                    }
                                }
                                else if(i1 > 0)
                                {
                                    --i1;
                                    primer.setBlockState(x, y, z, fillerState);
                                }
                            }
                        }
                        else
                        {
                            i1 = -1;
                        }
                    }
                    else
                    {
                        primer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
                    }
                }
            }
        }
    }

    private double[] generateHeightMap(double[] heightMap, int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize)
    {
        if(heightMap == null)
        {
            heightMap = new double[xSize * ySize * zSize];
        }

        noiseData4 = noiseGenScale.generateNoiseOctaves(noiseData4, xOffset, yOffset, zOffset, xSize, 1, zSize, 1.0D, 0.0D, 1.0D);
        noiseData5 = noiseGenDepth.generateNoiseOctaves(noiseData5, xOffset, yOffset, zOffset, xSize, 1, zSize, 100.0D, 0.0D, 100.0D);
        noiseData1 = noiseGen3.generateNoiseOctaves(noiseData1, xOffset, yOffset, zOffset, xSize, ySize, zSize, 8.555150000000001D, 34.2206D, 8.555150000000001D);
        noiseData2 = noiseGen1.generateNoiseOctaves(noiseData2, xOffset, yOffset, zOffset, xSize, ySize, zSize, 684.412D, 2053.236D, 684.412D);
        noiseData3 = noiseGen2.generateNoiseOctaves(noiseData3, xOffset, yOffset, zOffset, xSize, ySize, zSize, 684.412D, 2053.236D, 684.412D);
        int i = 0;
        double[] adouble = new double[ySize];

        for(int j = 0; j < ySize; ++j)
        {
            adouble[j] = Math.cos((double) j * Math.PI * 6.0D / (double) ySize) * 2.0D;
            double d2 = (double) j;

            if(j > ySize / 2)
            {
                d2 = (double) (ySize - 1 - j);
            }

            if(d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble[j] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for(int l = 0; l < xSize; ++l)
        {
            for(int i1 = 0; i1 < zSize; ++i1)
            {
                for(int k = 0; k < ySize; ++k)
                {
                    double d4 = adouble[k];
                    double d5 = noiseData2[i] / 512.0D;
                    double d6 = noiseData3[i] / 512.0D;
                    double d7 = (noiseData1[i] / 10.0D + 1.0D) / 2.0D;
                    double d8;

                    if(d7 < 0.0D)
                    {
                        d8 = d5;
                    }
                    else if(d7 > 1.0D)
                    {
                        d8 = d6;
                    }
                    else
                    {
                        d8 = d5 + (d6 - d5) * d7;
                    }

                    d8 = d8 - d4;

                    if(k > ySize - 4)
                    {
                        double d9 = (double) ((float) (k - (ySize - 4)) / 3.0F);
                        d8 = d8 * (1.0D - d9) + -10.0D * d9;
                    }

                    if((double) k < 0.0D)
                    {
                        double d10 = (0.0D - (double) k) / 4.0D;
                        d10 = MathHelper.clamp_double(d10, 0.0D, 1.0D);
                        d8 = d8 * (1.0D - d10) + -10.0D * d10;
                    }

                    heightMap[i] = d8;
                    ++i;
                }
            }
        }

        return heightMap;
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ)
    {
        ChunkPrimer primer = new ChunkPrimer();
        rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
        biomesForGen = world.getBiomeProvider().getBiomes(biomesForGen, chunkX * 16, chunkZ * 16, 16, 16);
        setBlocksInChunk(chunkX, chunkZ, primer, biomesForGen);
        replaceBiomeBlocks(chunkX, chunkZ, primer, biomesForGen);
        netherCaves.generate(world, chunkX, chunkZ, primer);
        netherBridge.generate(world, chunkX, chunkZ, primer);

        Chunk chunk = new Chunk(world, primer, chunkX, chunkZ);
        Biome[] biomes = world.getBiomeProvider().getBiomes(null, chunkX * 16, chunkZ * 16, 16, 16);
        byte[] biomeArray = chunk.getBiomeArray();

        for(int i = 0; i < biomeArray.length; ++i)
        {
            biomeArray[i] = (byte) Biome.getIdForBiome(biomes[i]);
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    @Override
    public void populate(int chunkX, int chunkZ)
    {
        ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
        BlockPos blockPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
        NetherBiome biome = (NetherBiome) world.getBiome(blockPos.add(16, 0, 16));

        BlockFalling.fallInstantly = true;

        netherBridge.generateStructure(world, rand, chunkPos);
        biome.decorate(world, rand, blockPos);

        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z)
    {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        if(creatureType == EnumCreatureType.MONSTER)
        {
            if(netherBridge.isInsideStructure(pos))
            {
                return netherBridge.getSpawnList();
            }

            if(netherBridge.isPositionInStructure(world, pos) && world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICK)
            {
                return netherBridge.getSpawnList();
            }
        }

        return world.getBiome(pos).getSpawnableList(creatureType);
    }

    @Nullable
    @Override
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position)
    {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int chunkX, int chunkZ)
    {
        netherBridge.generate(world, chunkX, chunkZ, null);
    }
}
