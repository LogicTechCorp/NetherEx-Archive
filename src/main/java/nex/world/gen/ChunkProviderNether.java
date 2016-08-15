package nex.world.gen;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.*;
import net.minecraftforge.fml.common.eventhandler.Event;

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

    private final WorldGenFire fire = new WorldGenFire();
    private final WorldGenGlowStone1 glowStone1 = new WorldGenGlowStone1();
    private final WorldGenGlowStone2 glowStone2 = new WorldGenGlowStone2();
    private final WorldGenerator quartz = new WorldGenMinable(Blocks.QUARTZ_ORE.getDefaultState(), 14, BlockMatcher.forBlock(Blocks.NETHERRACK));
    private final WorldGenerator magma = new WorldGenMinable(Blocks.MAGMA.getDefaultState(), 33, BlockMatcher.forBlock(Blocks.NETHERRACK));
    private final WorldGenHellLava lavaTrap = new WorldGenHellLava(Blocks.FLOWING_LAVA, true);
    private final WorldGenHellLava lavaSpring = new WorldGenHellLava(Blocks.FLOWING_LAVA, false);
    private final WorldGenBush brownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM);
    private final WorldGenBush redMushroom = new WorldGenBush(Blocks.RED_MUSHROOM);

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

        InitNoiseGensEvent.ContextHell ctx = new InitNoiseGensEvent.ContextHell(noiseGen1, noiseGen2, noiseGen3, noiseGenSoulSandGravel, noiseGenNetherrack, noiseGenScale, noiseGenDepth);

        noiseGen1 = ctx.getLPerlin1();
        noiseGen2 = ctx.getLPerlin2();
        noiseGen3 = ctx.getPerlin();
        noiseGenSoulSandGravel = ctx.getPerlin2();
        noiseGenNetherrack = ctx.getPerlin3();
        noiseGenScale = ctx.getScale();
        noiseGenDepth = ctx.getDepth();

        netherBridge = (MapGenNetherBridge) TerrainGen.getModdedMapGen(netherBridge, InitMapGenEvent.EventType.NETHER_BRIDGE);
        netherCaves = (MapGenCavesHell) TerrainGen.getModdedMapGen(netherCaves, InitMapGenEvent.EventType.NETHER_CAVE);
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
                                    state = Blocks.LAVA.getDefaultState();
                                }

                                if(d15 > 0.0D)
                                {
                                    state = biome.fillerBlock;
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
        if(!ForgeEventFactory.onReplaceBiomeBlocks(this, chunkX, chunkZ, primer, world))
        {
            return;
        }

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
                            if(checkState == biome.fillerBlock)
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
                                        topState = Blocks.LAVA.getDefaultState();
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

    private double[] generateHeightMap(double[] heightMap, int posX, int posY, int posZ, int xSize, int ySize, int zSize)
    {
        if(heightMap == null)
        {
            heightMap = new double[xSize * ySize * zSize];
        }

        ChunkGeneratorEvent.InitNoiseField event = new ChunkGeneratorEvent.InitNoiseField(this, heightMap, posX, posY, posZ, xSize, ySize, zSize);
        MinecraftForge.EVENT_BUS.post(event);

        if(event.getResult() == Event.Result.DENY)
        {
            return event.getNoisefield();
        }

        noiseData4 = noiseGenScale.generateNoiseOctaves(noiseData4, posX, posY, posZ, xSize, 1, zSize, 1.0D, 0.0D, 1.0D);
        noiseData5 = noiseGenDepth.generateNoiseOctaves(noiseData5, posX, posY, posZ, xSize, 1, zSize, 100.0D, 0.0D, 100.0D);
        noiseData1 = noiseGen3.generateNoiseOctaves(noiseData1, posX, posY, posZ, xSize, ySize, zSize, 8.555150000000001D, 34.2206D, 8.555150000000001D);
        noiseData2 = noiseGen1.generateNoiseOctaves(noiseData2, posX, posY, posZ, xSize, ySize, zSize, 684.412D, 2053.236D, 684.412D);
        noiseData3 = noiseGen2.generateNoiseOctaves(noiseData3, posX, posY, posZ, xSize, ySize, zSize, 684.412D, 2053.236D, 684.412D);

        double[] newYSize = new double[ySize];

        for(int j = 0; j < ySize; ++j)
        {
            newYSize[j] = Math.cos((double) j * Math.PI * 6.0D / (double) ySize) * 2.0D;
            double d2 = (double) j;

            if(j > ySize / 2)
            {
                d2 = (double) (ySize - 1 - j);
            }

            if(d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                newYSize[j] -= d2 * d2 * d2 * 10.0D;
            }
        }

        int index = 0;

        for(int l = 0; l < xSize; ++l)
        {
            for(int i1 = 0; i1 < zSize; ++i1)
            {
                for(int k = 0; k < ySize; ++k)
                {
                    double d4 = newYSize[k];
                    double d5 = noiseData2[index] / 512.0D;
                    double d6 = noiseData3[index] / 512.0D;
                    double d7 = (noiseData1[index] / 10.0D + 1.0D) / 2.0D;
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

                    heightMap[index] = d8;
                    index++;
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
        Biome biome = world.getBiomeForCoordsBody(blockPos.add(16, 0, 16));

        BlockFalling.fallInstantly = true;
        ForgeEventFactory.onChunkPopulate(true, this, world, rand, chunkX, chunkZ, false);

        netherBridge.generateStructure(world, rand, chunkPos);

        if(TerrainGen.populate(this, this.world, this.rand, chunkX, chunkZ, false, PopulateChunkEvent.Populate.EventType.NETHER_LAVA))
        {
            for(int k = 0; k < 8; ++k)
            {
                this.lavaSpring.generate(this.world, this.rand, blockPos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
            }
        }

        if(TerrainGen.populate(this, this.world, this.rand, chunkX, chunkZ, false, PopulateChunkEvent.Populate.EventType.FIRE))
        {
            for(int i1 = 0; i1 < this.rand.nextInt(this.rand.nextInt(10) + 1) + 1; ++i1)
            {
                this.fire.generate(this.world, this.rand, blockPos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
            }
        }

        if(TerrainGen.populate(this, this.world, this.rand, chunkX, chunkZ, false, PopulateChunkEvent.Populate.EventType.GLOWSTONE))
        {
            for(int j1 = 0; j1 < this.rand.nextInt(this.rand.nextInt(10) + 1); ++j1)
            {
                this.glowStone1.generate(this.world, this.rand, blockPos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
            }

            for(int k1 = 0; k1 < 10; ++k1)
            {
                this.glowStone2.generate(this.world, this.rand, blockPos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
            }
        }

        ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, chunkX, chunkZ, false);
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(this.world, this.rand, blockPos));

        if(TerrainGen.decorate(this.world, this.rand, blockPos, DecorateBiomeEvent.Decorate.EventType.SHROOM))
        {
            if(this.rand.nextBoolean())
            {
                this.brownMushroom.generate(this.world, this.rand, blockPos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
            }

            if(this.rand.nextBoolean())
            {
                this.redMushroom.generate(this.world, this.rand, blockPos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
            }
        }

        if(TerrainGen.generateOre(this.world, this.rand, quartz, blockPos, OreGenEvent.GenerateMinable.EventType.QUARTZ))
        {
            for(int l1 = 0; l1 < 16; ++l1)
            {
                this.quartz.generate(this.world, this.rand, blockPos.add(this.rand.nextInt(16), this.rand.nextInt(108) + 10, this.rand.nextInt(16)));
            }
        }

        if(TerrainGen.populate(this, this.world, this.rand, chunkX, chunkZ, false, PopulateChunkEvent.Populate.EventType.NETHER_MAGMA))
        {
            for(int l = 0; l < 4; ++l)
            {
                this.magma.generate(this.world, this.rand, blockPos.add(this.rand.nextInt(16), this.rand.nextInt(9) + 28, this.rand.nextInt(16)));
            }
        }

        if(TerrainGen.populate(this, this.world, this.rand, chunkX, chunkZ, false, PopulateChunkEvent.Populate.EventType.NETHER_LAVA2))
        {
            for(int j2 = 0; j2 < 16; ++j2)
            {
                this.lavaTrap.generate(this.world, this.rand, blockPos.add(this.rand.nextInt(16), this.rand.nextInt(108) + 10, this.rand.nextInt(16)));
            }
        }

        biome.decorate(this.world, this.rand, blockPos);

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(this.world, this.rand, blockPos));
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

        return world.getBiomeForCoordsBody(pos).getSpawnableList(creatureType);
    }

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
