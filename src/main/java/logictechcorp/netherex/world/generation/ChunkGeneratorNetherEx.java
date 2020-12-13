/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.world.generation;

import logictechcorp.libraryex.event.LibExEventFactory;
import logictechcorp.libraryex.world.biome.data.BiomeData;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.BlockFalling;
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
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.*;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorNetherEx extends ChunkGeneratorHell
{
    private final World world;
    private final Random random;
    private final boolean generateStructures;

    private NoiseGeneratorOctaves lowerNoiseGen;
    private NoiseGeneratorOctaves upperNoiseGen;
    private NoiseGeneratorOctaves noiseLevelsGen;
    private NoiseGeneratorOctaves soulSandGravelNoiseGen;
    private NoiseGeneratorOctaves netherrackNoiseGen;
    private NoiseGeneratorOctaves scaleNoiseGen;
    private NoiseGeneratorOctaves depthNoiseGen;
    private final NoiseGeneratorPerlin terrainNoiseGen;

    private double[] heightmap;
    private double[] netherrackNoise = new double[256];
    private double[] soulSandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private double[] terrainNoise = new double[256];
    private double[] noiseLevels;
    private double[] lowerNoiseData;
    private double[] upperNoiseData;
    private double[] scaleNoise;
    private double[] depthNoise;
    private Biome[] biomes;

    private MapGenBase netherCaves = new MapGenCavesHell();
    private MapGenNetherBridge netherFortress = new MapGenNetherBridge();

    public ChunkGeneratorNetherEx(World world, boolean generateStructures, long seed)
    {
        super(world, generateStructures, seed);
        this.world = world;
        this.random = new Random(seed);
        this.generateStructures = generateStructures;
        this.lowerNoiseGen = new NoiseGeneratorOctaves(this.random, 16);
        this.upperNoiseGen = new NoiseGeneratorOctaves(this.random, 16);
        this.noiseLevelsGen = new NoiseGeneratorOctaves(this.random, 8);
        this.soulSandGravelNoiseGen = new NoiseGeneratorOctaves(this.random, 4);
        this.netherrackNoiseGen = new NoiseGeneratorOctaves(this.random, 4);
        this.scaleNoiseGen = new NoiseGeneratorOctaves(this.random, 10);
        this.depthNoiseGen = new NoiseGeneratorOctaves(this.random, 16);
        this.terrainNoiseGen = new NoiseGeneratorPerlin(this.random, 4);

        InitNoiseGensEvent.ContextHell ctx = new InitNoiseGensEvent.ContextHell(this.lowerNoiseGen, this.upperNoiseGen, this.noiseLevelsGen, this.soulSandGravelNoiseGen, this.netherrackNoiseGen, this.scaleNoiseGen, this.depthNoiseGen);
        ctx = TerrainGen.getModdedNoiseGenerators(this.world, this.random, ctx);

        this.lowerNoiseGen = ctx.getLPerlin1();
        this.upperNoiseGen = ctx.getLPerlin2();
        this.noiseLevelsGen = ctx.getPerlin();
        this.soulSandGravelNoiseGen = ctx.getPerlin2();
        this.netherrackNoiseGen = ctx.getPerlin3();
        this.scaleNoiseGen = ctx.getScale();
        this.depthNoiseGen = ctx.getDepth();

        this.netherCaves = TerrainGen.getModdedMapGen(this.netherCaves, InitMapGenEvent.EventType.NETHER_CAVE);

        MapGenBase replacementNetherFortress = TerrainGen.getModdedMapGen(this.netherFortress, InitMapGenEvent.EventType.NETHER_BRIDGE);

        if(replacementNetherFortress instanceof MapGenNetherBridge)
        {
            this.netherFortress = (MapGenNetherBridge) replacementNetherFortress;
        }

        world.setSeaLevel(31);
    }

    @Override
    public void prepareHeights(int chunkX, int chunkZ, ChunkPrimer primer)
    {
        this.heightmap = this.generateHeightMap(this.heightmap, chunkX * 4, 0, chunkZ * 4, 5, 17, 5);

        for(int x = 0; x < 4; x++)
        {
            for(int z = 0; z < 4; z++)
            {
                for(int y = 0; y < 16; y++)
                {
                    double d1 = this.heightmap[(x * 5 + z) * 17 + y];
                    double d2 = this.heightmap[(((x * 5) + z + 1) * 17) + y];
                    double d3 = this.heightmap[((((x + 1) * 5) + z) * 17) + y];
                    double d4 = this.heightmap[((x + 1) * 5 + z + 1) * 17 + y];
                    double d5 = (this.heightmap[(x * 5 + z) * 17 + y + 1] - d1) * 0.125D;
                    double d6 = (this.heightmap[(x * 5 + z + 1) * 17 + y + 1] - d2) * 0.125D;
                    double d7 = (this.heightmap[((x + 1) * 5 + z) * 17 + y + 1] - d3) * 0.125D;
                    double d8 = (this.heightmap[((x + 1) * 5 + z + 1) * 17 + y + 1] - d4) * 0.125D;

                    for(int y2 = 0; y2 < 8; y2++)
                    {
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for(int x2 = 0; x2 < 4; x2++)
                        {
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.25D;

                            for(int z2 = 0; z2 < 4; z2++)
                            {
                                int posX = x2 + x * 4;
                                int posY = y2 + y * 8;
                                int posZ = z2 + z * 4;

                                IBlockState state = null;

                                if(posY < 32)
                                {
                                    state = Blocks.LAVA.getDefaultState();
                                }

                                if(d15 > 0.0D)
                                {
                                    state = Blocks.NETHERRACK.getDefaultState();
                                }

                                primer.setBlockState(posX, posY, posZ, state);
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

    @Override
    public void buildSurfaces(int chunkX, int chunkZ, ChunkPrimer primer)
    {
        if(!ForgeEventFactory.onReplaceBiomeBlocks(this, chunkX, chunkZ, primer, this.world))
        {
            return;
        }

        this.soulSandNoise = this.soulSandGravelNoiseGen.generateNoiseOctaves(this.soulSandNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, 0.03125D, 0.03125D, 1.0D);
        this.gravelNoise = this.soulSandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, chunkX * 16, 109, chunkZ * 16, 16, 1, 16, 0.03125D, 1.0D, 0.03125D);
        this.netherrackNoise = this.netherrackNoiseGen.generateNoiseOctaves(this.netherrackNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, 0.0625D, 0.0625D, 0.0625D);

        for(int posZ = 0; posZ < 16; posZ++)
        {
            for(int posX = 0; posX < 16; posX++)
            {
                for(int posY = 127; posY >= 0; posY--)
                {
                    if(posY >= (127 - this.random.nextInt(5)) || posY <= this.random.nextInt(5))
                    {
                        primer.setBlockState(posZ, posY, posX, Blocks.BEDROCK.getDefaultState());
                    }
                }

                BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData(this.biomes[posX + (posZ * 16)]);

                if(biomeData != BiomeData.EMPTY)
                {
                    this.terrainNoise = this.terrainNoiseGen.getRegion(this.terrainNoise, (chunkX * 16), (chunkZ * 16), 16, 16, 0.03125D * 2.0D, 0.03125D * 2.0D, 1.0D);
                    biomeData.generateTerrain(this.world, this.random, primer, (chunkX * 16) + posX, (chunkZ * 16) + posZ, this.terrainNoise[posX + (posZ * 16)]);
                }
            }
        }
    }

    private double[] generateHeightMap(double[] heightMap, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ)
    {
        if(heightMap == null)
        {
            heightMap = new double[sizeX * sizeY * sizeZ];
        }

        ChunkGeneratorEvent.InitNoiseField event = new ChunkGeneratorEvent.InitNoiseField(this, heightMap, posX, posY, posZ, sizeX, sizeY, sizeZ);
        MinecraftForge.EVENT_BUS.post(event);

        if(event.getResult() == Event.Result.DENY)
        {
            return event.getNoisefield();
        }

        this.scaleNoise = this.scaleNoiseGen.generateNoiseOctaves(this.scaleNoise, posX, posY, posZ, sizeX, 1, sizeZ, 1.0D, 0.0D, 1.0D);
        this.depthNoise = this.depthNoiseGen.generateNoiseOctaves(this.depthNoise, posX, posY, posZ, sizeX, 1, sizeZ, 100.0D, 0.0D, 100.0D);
        this.noiseLevels = this.noiseLevelsGen.generateNoiseOctaves(this.noiseLevels, posX, posY, posZ, sizeX, sizeY, sizeZ, 8.55515D, 34.2206D, 8.55515D);
        this.lowerNoiseData = this.lowerNoiseGen.generateNoiseOctaves(this.lowerNoiseData, posX, posY, posZ, sizeX, sizeY, sizeZ, 684.412D, 2053.236D, 684.412D);
        this.upperNoiseData = this.upperNoiseGen.generateNoiseOctaves(this.upperNoiseData, posX, posY, posZ, sizeX, sizeY, sizeZ, 684.412D, 2053.236D, 684.412D);

        double[] heightAdjustments = new double[sizeY];

        for(int y = 0; y < sizeY; y++)
        {
            heightAdjustments[y] = Math.cos((double) y * Math.PI * 6.0D / (double) sizeY) * 2.0D;
            double adjustedY = y;

            if(y > sizeY / 2)
            {
                adjustedY = (sizeY - 1 - y);
            }

            if(adjustedY < 4.0D)
            {
                adjustedY = 4.0D - adjustedY;
                heightAdjustments[y] -= adjustedY * adjustedY * adjustedY * 10.0D;
            }
        }

        int posCounter = 0;

        for(int x = 0; x < sizeX; x++)
        {
            for(int z = 0; z < sizeZ; z++)
            {
                for(int y = 0; y < sizeY; y++)
                {
                    double heightAdjustment = heightAdjustments[y];
                    double lowerNoise = this.lowerNoiseData[posCounter] / 512.0D;
                    double upperNoise = this.upperNoiseData[posCounter] / 512.0D;
                    double noiseLevel = (this.noiseLevels[posCounter] / 10.0D + 1.0D) / 2.0D;
                    double height;

                    if(noiseLevel < 0.0D)
                    {
                        height = lowerNoise;
                    }
                    else if(noiseLevel > 1.0D)
                    {
                        height = upperNoise;
                    }
                    else
                    {
                        height = lowerNoise + (upperNoise - lowerNoise) * noiseLevel;
                    }

                    height = height - heightAdjustment;

                    if(y > sizeY - 4)
                    {
                        double distance = ((float) (y - (sizeY - 4)) / 3.0F);
                        height = height * (1.0D - distance) + -10.0D * distance;
                    }

                    if((double) y < 0.0D)
                    {
                        double distance = (0.0D - (double) y) / 4.0D;
                        distance = MathHelper.clamp(distance, 0.0D, 1.0D);
                        height = height * (1.0D - distance) + -10.0D * distance;
                    }

                    heightMap[posCounter] = height;
                    posCounter++;
                }
            }
        }

        return heightMap;
    }

    @Override
    public Chunk generateChunk(int chunkX, int chunkZ)
    {
        this.random.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
        this.biomes = this.world.getBiomeProvider().getBiomes(null, chunkX * 16, chunkZ * 16, 16, 16);

        ChunkPrimer primer = new ChunkPrimer();
        this.prepareHeights(chunkX, chunkZ, primer);
        this.buildSurfaces(chunkX, chunkZ, primer);
        this.netherCaves.generate(this.world, chunkX, chunkZ, primer);

        if(this.generateStructures)
        {
            this.netherFortress.generate(this.world, chunkX, chunkZ, primer);
        }

        Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);
        byte[] biomeIds = chunk.getBiomeArray();

        for(int i = 0; i < biomeIds.length; i++)
        {
            Biome biome = this.biomes[i];
            biomeIds[i] = (byte) Biome.getIdForBiome(biome);
        }

        chunk.resetRelightChecks();
        LibExEventFactory.onChunkGenerate(chunk);
        return chunk;
    }

    @Override
    public void populate(int chunkX, int chunkZ)
    {
        ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
        BlockPos blockPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
        BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData(this.world.getBiome(blockPos.add(16, 0, 16)));

        BlockFalling.fallInstantly = true;
        this.netherFortress.generateStructure(this.world, this.random, chunkPos);
        ForgeEventFactory.onChunkPopulate(true, this, this.world, this.random, chunkX, chunkZ, false);
        TerrainGen.populate(this, this.world, this.random, chunkX, chunkZ, false, PopulateChunkEvent.Populate.EventType.CUSTOM);
        ForgeEventFactory.onChunkPopulate(false, this, this.world, this.random, chunkX, chunkZ, false);
        LibExEventFactory.onPreDecorateBiome(this.world, this.random, chunkPos);
        LibExEventFactory.onDecorateBiome(this.world, this.random, chunkPos, blockPos, DecorateBiomeEvent.Decorate.EventType.CUSTOM);

        if(biomeData != BiomeData.EMPTY && biomeData.useDefaultBiomeDecorations())
        {
            biomeData.getBiome().decorate(this.world, this.random, blockPos);
        }

        LibExEventFactory.onPostDecorateBiome(this.world, this.random, chunkPos);
        LibExEventFactory.onPreOreGen(this.world, this.random, blockPos);
        LibExEventFactory.onOreGen(this.world, this.random, new WorldGenMinable(Blocks.AIR.getDefaultState(), 0, BlockMatcher.forBlock(Blocks.AIR)), blockPos, OreGenEvent.GenerateMinable.EventType.CUSTOM);
        LibExEventFactory.onPostOreGen(this.world, this.random, blockPos);
        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean generateStructures(Chunk chunk, int chunkX, int chunkZ)
    {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        if(creatureType == EnumCreatureType.MONSTER)
        {
            if(this.netherFortress.isInsideStructure(pos))
            {
                return this.netherFortress.getSpawnList();
            }
            if(this.netherFortress.isPositionInStructure(this.world, pos) && this.world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICK)
            {
                return this.netherFortress.getSpawnList();
            }
        }

        Biome biome = this.world.getBiome(pos);
        BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData(biome);
        List<Biome.SpawnListEntry> spawns = new ArrayList<>(biome.getSpawnableList(creatureType));

        if(biomeData != BiomeData.EMPTY)
        {
            spawns.addAll(biomeData.getEntitySpawns(creatureType));
        }

        return spawns;
    }

    @Override
    public BlockPos getNearestStructurePos(World world, String structureName, BlockPos pos, boolean force)
    {
        if(this.netherFortress != null && structureName.equals("Fortress"))
        {
            return this.netherFortress.getNearestStructurePos(world, pos, force);
        }

        return null;
    }

    @Override
    public boolean isInsideStructure(World world, String structureName, BlockPos pos)
    {
        if(this.netherFortress != null && structureName.equals("Fortress"))
        {
            return this.netherFortress.isInsideStructure(pos);
        }

        return false;
    }

    @Override
    public void recreateStructures(Chunk chunk, int chunkX, int chunkZ)
    {
        if(this.generateStructures && this.netherFortress != null)
        {
            this.netherFortress.generate(this.world, chunkX, chunkZ, null);
        }
    }
}
