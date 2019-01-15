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

package logictechcorp.netherex.capability;

import logictechcorp.libraryex.capability.CapabilitySerializable;
import logictechcorp.libraryex.util.WorldHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.util.BlightHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class CapabilityBlightChunkData implements IBlightChunkData
{
    @CapabilityInject(IBlightChunkData.class)
    public static final Capability<IBlightChunkData> INSTANCE = null;
    private final Map<Long, BlightChunk> blightChunks = new ConcurrentHashMap<>();

    private CapabilityBlightChunkData()
    {
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList blightChunkData = new NBTTagList();

        for(Map.Entry<Long, BlightChunk> entry : this.blightChunks.entrySet())
        {
            BlightChunk chunk = entry.getValue();
            NBTTagCompound blightChunkCompound = new NBTTagCompound();
            blightChunkCompound.setInteger("ChunkX", chunk.getXPos());
            blightChunkCompound.setInteger("ChunkZ", chunk.getZPos());
            blightChunkCompound.setInteger("TickCounter", chunk.getTickCounter());

            NBTTagList dormantList = new NBTTagList();

            for(BlockPos blockPos : chunk.getDormantPositions())
            {
                dormantList.appendTag(NBTUtil.createPosTag(blockPos));
            }

            blightChunkCompound.setTag("DormantPositions", dormantList);
            NBTTagList activeList = new NBTTagList();

            for(BlockPos blockPos : chunk.getActivePositions())
            {
                dormantList.appendTag(NBTUtil.createPosTag(blockPos));
            }

            blightChunkCompound.setTag("ActivePositions", activeList);
            blightChunkCompound.setBoolean("StartedTransformation", chunk.startedTransformation());
            blightChunkCompound.setBoolean("FinishedTransformation", chunk.finishedTransformation());

            BlightChunk parentChunk = chunk.getParentChunk();

            if(parentChunk != null)
            {
                blightChunkCompound.setLong("ParentChunk", ChunkPos.asLong(parentChunk.getXPos(), parentChunk.getZPos()));
            }

            blightChunkData.appendTag(blightChunkCompound);
        }

        compound.setTag("BlightChunkData", blightChunkData);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound)
    {
        for(NBTBase base : compound.getTagList("BlightChunkData", Constants.NBT.TAG_COMPOUND))
        {
            NBTTagCompound blightChunkCompound = (NBTTagCompound) base;
            int chunkX = blightChunkCompound.getInteger("ChunkX");
            int chunkZ = blightChunkCompound.getInteger("ChunkZ");
            NBTTagList dormantList = blightChunkCompound.getTagList("DormantPositions", Constants.NBT.TAG_COMPOUND);
            NBTTagList activeList = blightChunkCompound.getTagList("ActivePositions", Constants.NBT.TAG_COMPOUND);

            List<BlockPos> dormantPositions = new ArrayList<>();
            List<BlockPos> activePositions = new ArrayList<>();

            for(NBTBase blockPosCompound : dormantList)
            {
                dormantPositions.add(NBTUtil.getPosFromTag((NBTTagCompound) blockPosCompound));
            }

            for(NBTBase blockPosCompound : activeList)
            {
                activePositions.add(NBTUtil.getPosFromTag((NBTTagCompound) blockPosCompound));
            }

            BlightChunk chunk = new BlightChunk(chunkX, chunkZ, dormantPositions, activePositions);
            chunk.setTickCounter(blightChunkCompound.getInteger("TickCounter"));
            chunk.setStartedTransformation(blightChunkCompound.getBoolean("StartedTransformation"));
            chunk.setFinishedTransformation(blightChunkCompound.getBoolean("FinishedTransformation"));

            if(blightChunkCompound.getLong("ParentChunk") != 0L)
            {
                chunk.setParentChunk(this.blightChunks.get(blightChunkCompound.getLong("ParentChunk")));
            }

            this.blightChunks.put(ChunkPos.asLong(chunk.getXPos(), chunk.getZPos()), chunk);
        }
    }

    @Override
    public void tick(World world)
    {
        for(BlightChunk chunk : this.blightChunks.values())
        {
            if(WorldHelper.isChunkLoaded(world, chunk.getPos()))
            {
                chunk.tick(world);
            }
        }
    }

    @Override
    public void addChunk(ChunkPos chunkPos)
    {
        this.blightChunks.put(ChunkPos.asLong(chunkPos.x, chunkPos.z), new BlightChunk(chunkPos.x, chunkPos.z));
    }

    @Override
    public void removeChunk(ChunkPos chunkPos)
    {
        this.blightChunks.remove(ChunkPos.asLong(chunkPos.x, chunkPos.z));
    }

    @Override
    public boolean hasChunk(ChunkPos chunkPos)
    {
        return this.blightChunks.containsKey(ChunkPos.asLong(chunkPos.x, chunkPos.z));
    }

    @Override
    public BlightChunk getChunk(ChunkPos chunkPos)
    {
        return this.blightChunks.get(ChunkPos.asLong(chunkPos.x, chunkPos.z));
    }

    @Override
    public Collection<BlightChunk> getChunks()
    {
        return this.blightChunks.values();
    }

    public static class Factory implements Callable<IBlightChunkData>
    {
        @Override
        public IBlightChunkData call()
        {
            return new CapabilityBlightChunkData();
        }
    }

    public static class Storage implements Capability.IStorage<IBlightChunkData>
    {
        @Override
        public NBTBase writeNBT(Capability<IBlightChunkData> capability, IBlightChunkData chunk, EnumFacing side)
        {
            return chunk.serializeNBT();
        }

        @Override
        public void readNBT(Capability<IBlightChunkData> capability, IBlightChunkData chunk, EnumFacing side, NBTBase base)
        {
            if(base instanceof NBTTagCompound)
            {
                chunk.deserializeNBT((NBTTagCompound) base);
            }
        }
    }

    public static class BlightChunk
    {
        private ChunkPos chunkPos;
        private List<BlockPos> dormantPositions = new ArrayList<>();
        private List<BlockPos> activePositions = new ArrayList<>();
        private int tickCounter = 0;
        private boolean startedTransformation = false;
        private boolean finishedTransformation = false;
        private BlightChunk parentChunk = null;

        BlightChunk(int chunkX, int chunkZ)
        {
            this.chunkPos = new ChunkPos(chunkX, chunkZ);

            for(int x = this.chunkPos.getXStart(); x <= this.chunkPos.getXEnd(); x++)
            {
                for(int z = this.chunkPos.getZStart(); z <= this.chunkPos.getZEnd(); z++)
                {
                    this.dormantPositions.add(new BlockPos(x, 0, z));
                }
            }
        }

        BlightChunk(int chunkX, int chunkZ, List<BlockPos> dormantPositions, List<BlockPos> activePositions)
        {
            this.chunkPos = new ChunkPos(chunkX, chunkZ);
            this.dormantPositions = dormantPositions;
            this.activePositions = activePositions;
        }

        private void tick(World world)
        {
            Random rand = world.rand;
            this.tickCounter++;

            if(!this.startedTransformation && BlightHelper.addBlightToChunk(world, this.dormantPositions.get(0)))
            {
                this.startedTransformation = true;
            }

            if(!this.finishedTransformation && this.startedTransformation && (this.tickCounter % 40) == 0)
            {
                BlockPos originalBlockPos = this.dormantPositions.get(rand.nextInt(this.dormantPositions.size()));
                BlockPos adjustedBlockPos = world.getTopSolidOrLiquidBlock(originalBlockPos);

                if((world.getLightFromNeighbors(adjustedBlockPos.up()) <= 7 && BlightHelper.convertBlock(world, adjustedBlockPos)) || (world.getLightFromNeighbors(adjustedBlockPos) <= 7 && BlightHelper.convertBlock(world, adjustedBlockPos.down())))
                {
                    BlockPos activePos = this.dormantPositions.remove(this.dormantPositions.indexOf(originalBlockPos));
                    BlightHelper.convertBiome(world, activePos);
                    this.activePositions.add(activePos);
                }
                else if(world.getLightFromNeighbors(adjustedBlockPos.up()) <= 7)
                {
                    BlockPos activePos = this.dormantPositions.remove(this.dormantPositions.indexOf(originalBlockPos));
                    BlightHelper.convertBiome(world, activePos);
                    this.activePositions.add(activePos);
                }
            }

            if(!this.finishedTransformation && this.activePositions.size() == 256)
            {
                this.finishedTransformation = true;
            }

            if(this.finishedTransformation)
            {
                if(this.parentChunk == null && (this.tickCounter % 36000) == 0)
                {
                    chunk_label:
                    for(int x = this.getXPos() - 2; x <= this.getXPos() + 2; x++)
                    {
                        for(int z = this.getZPos() - 2; z <= this.getZPos() + 2; z++)
                        {
                            IBlightChunkData data = world.getCapability(CapabilityBlightChunkData.INSTANCE, null);

                            if(data != null)
                            {
                                ChunkPos childChunkPos = new ChunkPos(x, z);

                                if(!data.hasChunk(childChunkPos))
                                {
                                    data.addChunk(childChunkPos);
                                    data.getChunk(childChunkPos).setParentChunk(this);
                                    break chunk_label;
                                }
                            }
                        }
                    }
                }
            }
        }

        public ChunkPos getPos()
        {
            return this.chunkPos;
        }

        public int getXPos()
        {
            return this.chunkPos.x;
        }

        public int getZPos()
        {
            return this.chunkPos.z;
        }

        public int getTickCounter()
        {
            return this.tickCounter;
        }

        public List<BlockPos> getDormantPositions()
        {
            return this.dormantPositions;
        }

        public List<BlockPos> getActivePositions()
        {
            return this.activePositions;
        }

        public boolean startedTransformation()
        {
            return this.startedTransformation;
        }

        public boolean finishedTransformation()
        {
            return this.finishedTransformation;
        }

        public BlightChunk getParentChunk()
        {
            return this.parentChunk;
        }

        void setTickCounter(int tickCounter)
        {
            this.tickCounter = tickCounter;
        }

        void setStartedTransformation(boolean startedTransformation)
        {
            this.startedTransformation = startedTransformation;
        }

        void setFinishedTransformation(boolean finishedTransformation)
        {
            this.finishedTransformation = finishedTransformation;
        }

        void setParentChunk(BlightChunk parentChunk)
        {
            this.parentChunk = parentChunk;
        }
    }

    @Mod.EventBusSubscriber
    public static class Handler
    {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<World> event)
        {
            if(event.getObject().provider.getDimension() == DimensionType.OVERWORLD.getId())
            {
                event.addCapability(new ResourceLocation(NetherEx.MOD_ID + ":blight_chunk_data"), new CapabilitySerializable(INSTANCE));
            }
        }
    }
}
