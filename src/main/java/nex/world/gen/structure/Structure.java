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

package nex.world.gen.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Structure implements IWorldGenerator
{
    public String name;
    public Map<String, IBlockState> blocks = Maps.newHashMap();
    public List<String[]> layers = Lists.newArrayList();

    public int width;
    public int length;
    public int minY;
    public int maxY;

    public boolean setAirBlock;

    public float ratio;

    public List<ResourceLocation> lootTables;
    public List<ResourceLocation> mobs;

    public Structure(String nameIn, int widthIn, int lengthIn, int minYIn, int maxYIn, boolean setAirBlockIn, float ratioIn, List<ResourceLocation> lootTablesIn, List<ResourceLocation> mobsIn)
    {
        name = nameIn;
        width = widthIn;
        length = lengthIn;
        minY = minYIn;
        maxY = maxYIn;
        setAirBlock = setAirBlockIn;
        ratio = ratioIn;
        lootTables = lootTablesIn;
        mobs = mobsIn;
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.getDimension() == DimensionType.NETHER.getId())
        {
            int x = (chunkX * 16) + rand.nextInt(16);
            int y = rand.nextInt((maxY - minY) + 1) + minY;
            int z = (chunkZ * 16) + rand.nextInt(16);

            generateStructure(world, new BlockPos(x, y, z));
        }
    }

    private void generateStructure(World world, BlockPos pos)
    {
        Rotation rotation = Rotation.values()[world.rand.nextInt(Rotation.values().length)];

        boolean canGenerate = canGenerate(world, pos, rotation);

        if(!canGenerate)
        {
            return;
        }

        for(int y = 0; y < layers.size(); y++)
        {
            for(int z = 0; z < layers.get(y).length; z++)
            {
                for(int x = 0; x < layers.get(y)[z].length(); x++)
                {
                    if(rotation == Rotation.NONE)
                    {
                        setBlock(world, new BlockPos((pos.getX() + x) - (width / 2), pos.getY() + y, (pos.getZ() + z) - (length / 2)), blocks.get(layers.get(z)[x].substring(x, x + 1)), rotation);
                    }
                    else if(rotation == Rotation.CLOCKWISE_90)
                    {
                        setBlock(world, new BlockPos((pos.getX() + z) - (length / 2), pos.getY() + y, (pos.getZ() + x) - (width / 2)), blocks.get(layers.get(z)[x].substring(x, x + 1)), rotation);
                    }
                    else if(rotation == Rotation.CLOCKWISE_180)
                    {
                        setBlock(world, new BlockPos((pos.getX() - x) + (width / 2), pos.getY() + y, (pos.getZ() - z) + (length / 2)), blocks.get(layers.get(z)[x].substring(x, x + 1)), rotation);
                    }
                    else if(rotation == Rotation.COUNTERCLOCKWISE_90)
                    {
                        setBlock(world, new BlockPos((pos.getX() - z) + (length / 2), pos.getY() + y, (pos.getZ() - x) + (width / 2)), blocks.get(layers.get(z)[x].substring(x, x + 1)), rotation);
                    }
                }
            }
        }
    }

    private boolean canGenerate(World world, BlockPos pos, Rotation rotation)
    {
        BlockPos from;
        BlockPos to;

        if(rotation == Rotation.NONE)
        {
            from = new BlockPos((pos.getX()) - (width / 2), pos.getY() + layers.size(), (pos.getZ()) - (length / 2));
            to = new BlockPos((pos.getX() + width) - (width / 2), pos.getY() + layers.size(), (pos.getZ() + length) - (length / 2));
        }
        else if(rotation == Rotation.CLOCKWISE_90)
        {
            from = new BlockPos((pos.getX()) - (length / 2), pos.getY() + layers.size(), (pos.getZ()) - (width / 2));
            to = new BlockPos((pos.getX() + length) - (length / 2), pos.getY() + layers.size(), (pos.getZ() + width) - (width / 2));
        }
        else if(rotation == Rotation.CLOCKWISE_180)
        {
            from = new BlockPos((pos.getX()) + (width / 2), pos.getY() + layers.size(), (pos.getZ()) + (length / 2));
            to = new BlockPos((pos.getX() - width) + (width / 2), pos.getY() + layers.size(), (pos.getZ() - length) + (length / 2));
        }
        else
        {
            from = new BlockPos((pos.getX()) + (length / 2), pos.getY() + layers.size(), (pos.getZ()) + (width / 2));
            to = new BlockPos((pos.getX() - length) + (length / 2), pos.getY() + layers.size(), (pos.getZ() - width) + (width / 2));
        }

        Iterable<BlockPos> positionsToCheck = BlockPos.getAllInBox(from, to);

        float totalBlocks = 0.0F;
        float replaceableBlocks = 0.0F;

        for(BlockPos posToCheck : positionsToCheck)
        {
            totalBlocks++;

            if(world.getBlockState(posToCheck).getMaterial().isReplaceable())
            {
                replaceableBlocks++;
            }
        }

        return replaceableBlocks / totalBlocks >= ratio;
    }

    private void setBlock(World world, BlockPos pos, IBlockState state, Rotation rotation)
    {
        if(state.getBlock() != Blocks.AIR || state.getBlock() == Blocks.AIR && setAirBlock)
        {
            world.setBlockState(pos, state.withRotation(rotation));
        }

        if(state.getBlock() instanceof BlockChest)
        {
            setChestContents(world.rand, (TileEntityChest) world.getTileEntity(pos), lootTables, world.getSeed());
        }
        else if(state.getBlock() instanceof BlockMobSpawner)
        {
            setSpawnerMob(world.rand, (TileEntityMobSpawner) world.getTileEntity(pos), mobs);
        }
    }

    private void setChestContents(Random rand, TileEntityChest chest, List<ResourceLocation> lootTable, long seed)
    {
        chest.setLootTable(lootTable.get(rand.nextInt(lootTable.size())), seed);
    }

    private void setSpawnerMob(Random rand, TileEntityMobSpawner spawner, List<ResourceLocation> mob)
    {
        spawner.getSpawnerBaseLogic().setEntityId(mob.get(rand.nextInt(mob.size())));
    }

    public void addBlockMapping(String identifier, IBlockState state)
    {
        blocks.put(identifier, state);
    }

    public void addLayer(String[] layer)
    {
        layers.add(layer);
    }
}
