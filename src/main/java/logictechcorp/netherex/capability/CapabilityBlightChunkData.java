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
            blightChunkCompound.setInteger("ChunkX", chunk.getChunkX());
            blightChunkCompound.setInteger("ChunkZ", chunk.getChunkZ());

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
            blightChunkCompound.setBoolean("FullyTransformed", chunk.hasFullyTransformed());
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
            chunk.setStartedTransformation(blightChunkCompound.getBoolean("StartedTransformation"));
            chunk.setFullyTransformed(blightChunkCompound.getBoolean("FullyTransformed"));
            this.blightChunks.put(ChunkPos.asLong(chunk.getChunkX(), chunk.getChunkZ()), chunk);
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

    public class BlightChunk
    {
        private ChunkPos chunkPos;
        private List<BlockPos> dormantPositions = new ArrayList<>();
        private List<BlockPos> activePositions = new ArrayList<>();
        private int tickCounter = 0;
        private boolean startedTransformation = false;
        private boolean fullyTransformed = false;

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

            if(!this.fullyTransformed && this.startedTransformation && (this.tickCounter % 40) == 0)
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

            if(!this.fullyTransformed && this.activePositions.size() == 256)
            {
                this.fullyTransformed = true;
            }
        }

        public ChunkPos getPos()
        {
            return this.chunkPos;
        }

        public int getChunkX()
        {
            return this.chunkPos.x;
        }

        public int getChunkZ()
        {
            return this.chunkPos.z;
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

        public boolean hasFullyTransformed()
        {
            return this.fullyTransformed;
        }

        void setStartedTransformation(boolean startedTransformation)
        {
            this.startedTransformation = startedTransformation;
        }

        void setFullyTransformed(boolean fullyTransformed)
        {
            this.fullyTransformed = fullyTransformed;
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
