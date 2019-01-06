/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

package logictechcorp.netherex.world.gen;

import logictechcorp.libraryex.event.LibExEventFactory;
import logictechcorp.libraryex.world.biome.BiomeBlockType;
import logictechcorp.libraryex.world.biome.BiomeInfo;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.world.biome.NetherBiomeManager;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
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
    private final Random rand;

    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorOctaves soulSandGravelNoiseGen;
    private NoiseGeneratorOctaves netherrackNoiseGen;
    private NoiseGeneratorPerlin terrainNoiseGen;
    private NoiseGeneratorOctaves scaleNoiseGen;
    private NoiseGeneratorOctaves depthNoiseGen;

    private double[] heightmap;
    private double[] netherrackNoise = new double[256];
    private double[] soulSandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private double[] noiseData1;
    private double[] noiseData2;
    private double[] noiseData3;
    private double[] terrainNoise = new double[256];
    private double[] scaleNoise;
    private double[] depthNoise;

    private MapGenCavesHell netherCaves = new MapGenCavesHell();
    private MapGenNetherBridge netherBridge = new MapGenNetherBridge();

    public ChunkGeneratorNetherEx(World world)
    {
        super(world, true, world.getSeed());
        this.world = world;
        this.rand = new Random(this.world.getSeed());
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.soulSandGravelNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
        this.netherrackNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
        this.scaleNoiseGen = new NoiseGeneratorOctaves(this.rand, 10);
        this.depthNoiseGen = new NoiseGeneratorOctaves(this.rand, 16);
        this.terrainNoiseGen = new NoiseGeneratorPerlin(this.rand, 4);

        InitNoiseGensEvent.ContextHell ctx = new InitNoiseGensEvent.ContextHell(this.noiseGen1, this.noiseGen2, this.noiseGen3, this.soulSandGravelNoiseGen, this.netherrackNoiseGen, this.scaleNoiseGen, this.depthNoiseGen);
        ctx = TerrainGen.getModdedNoiseGenerators(this.world, this.rand, ctx);

        this.noiseGen1 = ctx.getLPerlin1();
        this.noiseGen2 = ctx.getLPerlin2();
        this.noiseGen3 = ctx.getPerlin();
        this.soulSandGravelNoiseGen = ctx.getPerlin2();
        this.netherrackNoiseGen = ctx.getPerlin3();
        this.scaleNoiseGen = ctx.getScale();
        this.depthNoiseGen = ctx.getDepth();

        this.netherCaves = (MapGenCavesHell) TerrainGen.getModdedMapGen(this.netherCaves, InitMapGenEvent.EventType.NETHER_CAVE);
        this.netherBridge = (MapGenNetherBridge) TerrainGen.getModdedMapGen(this.netherBridge, InitMapGenEvent.EventType.NETHER_BRIDGE);

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

        for(int x = 0; x < 16; x++)
        {
            for(int z = 0; z < 16; z++)
            {
                int l = (int) (this.netherrackNoise[x + z * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                int i1 = -1;
                boolean genSoulSand = this.soulSandNoise[x + z * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                boolean genGravel = this.gravelNoise[x + z * 16] + this.rand.nextDouble() * 0.2D > 0.0D;

                IBlockState floorTopBlock = Blocks.NETHERRACK.getDefaultState();
                IBlockState floorFillerBlock = Blocks.NETHERRACK.getDefaultState();

                for(int y = 127; y >= 0; y--)
                {
                    if(y < 124 && y > 3)
                    {
                        IBlockState checkBlock = primer.getBlockState(x, y, z);

                        if(checkBlock.getMaterial() != Material.AIR)
                        {
                            if(checkBlock == Blocks.NETHERRACK.getDefaultState())
                            {
                                if(i1 == -1)
                                {
                                    if(l <= 0)
                                    {
                                        floorTopBlock = Blocks.AIR.getDefaultState();
                                        floorFillerBlock = Blocks.NETHERRACK.getDefaultState();
                                    }
                                    else if(y >= 62 && y <= 66)
                                    {
                                        floorTopBlock = Blocks.NETHERRACK.getDefaultState();
                                        floorFillerBlock = Blocks.NETHERRACK.getDefaultState();

                                        if(ConfigHandler.dimensionConfig.nether.generateGravel && genGravel)
                                        {
                                            floorTopBlock = Blocks.GRAVEL.getDefaultState();
                                        }

                                        if(ConfigHandler.dimensionConfig.nether.generateSoulSand && genSoulSand)
                                        {
                                            floorTopBlock = Blocks.SOUL_SAND.getDefaultState();
                                            floorFillerBlock = Blocks.SOUL_SAND.getDefaultState();
                                        }
                                    }

                                    if(y <= 32 && (floorTopBlock == null || floorTopBlock.getMaterial() == Material.AIR))
                                    {
                                        floorTopBlock = Blocks.LAVA.getDefaultState();
                                    }

                                    i1 = l;

                                    if(y >= 64)
                                    {
                                        primer.setBlockState(x, y, z, floorTopBlock);
                                    }
                                    else
                                    {
                                        primer.setBlockState(x, y, z, floorFillerBlock);
                                    }
                                }
                                else if(i1 > 0)
                                {
                                    i1--;
                                    primer.setBlockState(x, y, z, floorFillerBlock);
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

        this.scaleNoise = this.scaleNoiseGen.generateNoiseOctaves(this.scaleNoise, posX, posY, posZ, xSize, 1, zSize, 1.0D, 0.0D, 1.0D);
        this.depthNoise = this.depthNoiseGen.generateNoiseOctaves(this.depthNoise, posX, posY, posZ, xSize, 1, zSize, 100.0D, 0.0D, 100.0D);
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, posX, posY, posZ, xSize, ySize, zSize, 8.555150000000001D, 34.2206D, 8.555150000000001D);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, posX, posY, posZ, xSize, ySize, zSize, 684.412D, 2053.236D, 684.412D);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, posX, posY, posZ, xSize, ySize, zSize, 684.412D, 2053.236D, 684.412D);

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
                    double d5 = this.noiseData2[index] / 512.0D;
                    double d6 = this.noiseData3[index] / 512.0D;
                    double d7 = (this.noiseData1[index] / 10.0D + 1.0D) / 2.0D;
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
                        d10 = MathHelper.clamp(d10, 0.0D, 1.0D);
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
    public Chunk generateChunk(int chunkX, int chunkZ)
    {
        ChunkPrimer primer = new ChunkPrimer();
        this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
        this.prepareHeights(chunkX, chunkZ, primer);
        this.buildSurfaces(chunkX, chunkZ, primer);
        this.netherCaves.generate(this.world, chunkX, chunkZ, primer);
        this.netherBridge.generate(this.world, chunkX, chunkZ, primer);

        Biome[] biomes = this.world.getBiomeProvider().getBiomes(null, chunkX * 16, chunkZ * 16, 16, 16);
        this.replaceBiomeBlocks(chunkX, chunkZ, primer, biomes);

        Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);
        byte[] biomeArray = chunk.getBiomeArray();

        for(int i = 0; i < biomeArray.length; i++)
        {
            Biome biome = biomes[i];
            biomeArray[i] = (byte) Biome.getIdForBiome(biome);
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
        BiomeInfo info = NetherBiomeManager.INSTANCE.getBiomeInfo(this.world.getBiome(blockPos.add(16, 0, 16)));

        BlockFalling.fallInstantly = true;
        this.netherBridge.generateStructure(this.world, this.rand, chunkPos);
        LibExEventFactory.onPreDecorateBiome(this.world, this.rand, chunkPos);
        LibExEventFactory.onDecorateBiome(this.world, this.rand, chunkPos, blockPos, DecorateBiomeEvent.Decorate.EventType.CUSTOM);

        if(info != null && info.genDefaultFeatures())
        {
            info.getBiome().decorate(this.world, this.rand, blockPos);
        }

        LibExEventFactory.onPostDecorateBiome(this.world, this.rand, chunkPos);
        LibExEventFactory.onPreOreGen(this.world, this.rand, blockPos);
        LibExEventFactory.onOreGen(this.world, this.rand, new WorldGenMinable(Blocks.AIR.getDefaultState(), 0, BlockMatcher.forBlock(Blocks.AIR)), blockPos, OreGenEvent.GenerateMinable.EventType.CUSTOM);
        LibExEventFactory.onPostOreGen(this.world, this.rand, blockPos);
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
            if(this.netherBridge.isInsideStructure(pos))
            {
                return this.netherBridge.getSpawnList();
            }
            if(this.netherBridge.isPositionInStructure(this.world, pos) && this.world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICK)
            {
                return this.netherBridge.getSpawnList();
            }
        }

        BiomeInfo info = NetherBiomeManager.INSTANCE.getBiomeInfo(this.world.getBiome(pos));
        return creatureType == null || info == null ? new ArrayList<>() : info.getEntitySpawnEntries(creatureType);
    }

    @Override
    public BlockPos getNearestStructurePos(World world, String structureName, BlockPos pos, boolean force)
    {
        if("Fortress".equalsIgnoreCase(structureName))
        {
            return this.netherBridge != null ? this.netherBridge.getNearestStructurePos(world, pos, force) : null;
        }

        return null;
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
        if("Fortress".equalsIgnoreCase(structureName))
        {
            return this.netherBridge != null && this.netherBridge.isInsideStructure(pos);
        }

        return false;
    }

    @Override
    public void recreateStructures(Chunk chunk, int chunkX, int chunkZ)
    {
        this.netherBridge.generate(this.world, chunkX, chunkZ, null);
    }

    /**
     * A method that replaces the default Nether blocks with ones designated for each biome
     * <p>
     * Written by the Biomes O' Plenty team here:
     * https://github.com/Glitchfiend/BiomesOPlenty/blob/9873b7ad56ab8f32e6073dea060c4b67aad8b77e/src/main/java/biomesoplenty/common/biome/nether/BOPHellBiome.java#L84
     *
     * @author Biomes O' Plenty team
     */
    private void replaceBiomeBlocks(int chunkX, int chunkZ, ChunkPrimer primer, Biome[] biomes)
    {
        if(!ForgeEventFactory.onReplaceBiomeBlocks(this, chunkX, chunkZ, primer, this.world))
        {
            return;
        }

        this.terrainNoise = this.terrainNoiseGen.getRegion(this.terrainNoise, (double) (chunkX * 16), (double) (chunkZ * 16), 16, 16, 0.03125D * 2.0D, 0.03125D * 2.0D, 1.0D);

        for(int x = 0; x < 16; x++)
        {
            for(int z = 0; z < 16; z++)
            {
                BiomeInfo info = NetherBiomeManager.INSTANCE.getBiomeInfo(biomes[x + z * 16]);

                if(info != null)
                {
                    Biome biome = info.getBiome();
                    ResourceLocation biomeName = biome.getRegistryName();

                    if(!biomeName.getNamespace().equalsIgnoreCase("biomesoplenty"))
                    {
                        IBlockState surfaceBlock = info.getBiomeBlock(BiomeBlockType.FLOOR_TOP_BLOCK, biome.topBlock);
                        IBlockState fillerBlock = info.getBiomeBlock(BiomeBlockType.FLOOR_FILLER_BLOCK, biome.fillerBlock);
                        IBlockState wallBlock = info.getBiomeBlock(BiomeBlockType.WALL_BLOCK, biome.fillerBlock);
                        IBlockState ceilingFillerBlock = info.getBiomeBlock(BiomeBlockType.CEILING_FILLER_BLOCK, biome.fillerBlock);
                        IBlockState ceilingBottomBlock = info.getBiomeBlock(BiomeBlockType.CEILING_BOTTOM_BLOCK, biome.fillerBlock);
                        IBlockState oceanBlock = info.getBiomeBlock(BiomeBlockType.OCEAN_BLOCK, Blocks.LAVA.getDefaultState());

                        int localX = ((chunkX * 16) + x) & 15;
                        int localZ = ((chunkZ * 16) + z) & 15;

                        boolean wasLastBlockSolid = true;

                        for(int localY = 127; localY >= 0; localY--)
                        {
                            int blocksToSkip = 0;

                            IBlockState checkState = primer.getBlockState(localX, localY, localZ);

                            if(checkState.getBlock() == Blocks.NETHERRACK)
                            {
                                primer.setBlockState(localX, localY, localZ, wallBlock);

                                if(!wasLastBlockSolid)
                                {
                                    primer.setBlockState(localX, localY, localZ, surfaceBlock);

                                    for(int floorOffset = 1; floorOffset <= 4 - 1 && localY - floorOffset >= 0; floorOffset++)
                                    {
                                        IBlockState state = primer.getBlockState(localX, localY - floorOffset, localZ);
                                        blocksToSkip = floorOffset;

                                        if(state.getBlock() == Blocks.NETHERRACK)
                                        {
                                            primer.setBlockState(localX, localY - floorOffset, localZ, fillerBlock);
                                        }
                                        else
                                        {
                                            break;
                                        }
                                    }
                                }

                                wasLastBlockSolid = true;
                            }
                            else if(checkState.getBlock() == Blocks.AIR)
                            {
                                if(wasLastBlockSolid)
                                {
                                    if(localY + 1 < 128)
                                    {
                                        primer.setBlockState(localX, localY + 1, localZ, ceilingBottomBlock);
                                    }

                                    for(int roofOffset = 2; roofOffset <= 4 && localY + roofOffset <= 127; roofOffset++)
                                    {
                                        IBlockState state = primer.getBlockState(localX, localY + roofOffset, localZ);

                                        if(state.getBlock() == Blocks.NETHERRACK || state == wallBlock)
                                        {
                                            primer.setBlockState(localX, localY + roofOffset, localZ, ceilingFillerBlock);
                                        }
                                        else
                                        {
                                            break;
                                        }
                                    }
                                }

                                wasLastBlockSolid = false;
                            }
                            else if(checkState == Blocks.LAVA.getDefaultState())
                            {
                                primer.setBlockState(localX, localY, localZ, oceanBlock);
                                wasLastBlockSolid = false;
                            }

                            localY -= blocksToSkip;
                        }
                    }
                    else
                    {
                        biome.genTerrainBlocks(this.world, this.rand, primer, chunkX * 16 + x, chunkZ * 16 + z, this.terrainNoise[x + z * 16]);
                    }
                }
            }
        }
    }
}
